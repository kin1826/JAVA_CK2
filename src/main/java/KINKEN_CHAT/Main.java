package KINKEN_CHAT;

import Hibernate.Group;
import Hibernate.User;
import INCHAT.UserInfor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Main extends JFrame implements ActionChange {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    Bar bar;
    ChatList chatList;
    MainChat mainChat;
    MoreInfor moreInfor;
    Contacts contacts;
    UserInfor userInfor;

    static ColorTheme colorTheme = new ColorTheme();

    private NoInternet noInternetPanel;

    JSONObject window;
    String filePath = PATHICON.getPathResource("whenStart.json");
    String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

    private String theme;
    private String language;
    private String account;
    private String password;
    private String logscreen;
    private boolean isInforOpen = false;
    private String section = "chat";
    private Socket socket;
    private String inchat;
    private ArrayList<String> inGroup;
    private String inchatName;
    private boolean isMes = false;

    private DataOutputStream out;
    private DataInputStream in;

    private boolean isShowNotify = false;

    public Main() throws IOException {
        try {
            this.socket = new Socket("localhost", 8888);

            window = new JSONObject(content);
            this.theme = window.optString("theme");
            this.language = window.optString("language");
            this.account = window.optString("account");
            this.password = window.optString("password");
            this.logscreen = window.optString("logscreen");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (this.account.isEmpty()) {
            new LoginDisplay(this.theme, this.language, this.socket);
        } else {
            display(this.account);
        }
    }

    public void display(String mail) throws IOException {
        this.account = mail;
        setTitle("KinKen - Chat");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(this.window.optInt("width"), this.window.optInt("height"));
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 800));

        getContentPane().setBackground(colorTheme.blackLight50);

        getUserNow(mail);

        bar = new Bar(this, theme, this, language, sessionFactory,userInfor.getId());
        bar.setBounds(0, 0, 70, getHeight());
        bar.setSize(70, getHeight());
        add(bar);
        bar.setVisible(false);
        chatList = new ChatList(theme, this, language, sessionFactory, mail, userInfor.getId(), new ChoiceUserChat() {
            @Override
            public void choice(User user, String mail, String name, String status, byte[] userAVT) {
                inchat = mail;
                inchatName = name;
                isMes = true;
                mainChat.setNameInChat(name, status, user.getId(), isMes, userAVT, mail);
                moreInfor.updateUserCur(name, user, isMes, userAVT);
            }

            @Override
            public void choiceGroup(Group group, String name, ArrayList<String> memingroup, byte[] grAVT) {
                isMes = false;
                inGroup = memingroup;
                mainChat.setNameInChat(name, "", group.getId(), isMes, grAVT, null);
                moreInfor.updateUserCur(name, group, isMes, grAVT);
            }
        });
        chatList.setBounds(80, 10, 300, getHeight() - 40);
        chatList.setSize(400, getHeight() - 40);
        add(chatList);
        chatList.setVisible(false);
        mainChat = new MainChat(theme, this, language, userInfor.getId(), sessionFactory, userInfor.getAvatarImg(), this, userInfor.getName());
        mainChat.setBounds(390, 10, getWidth() - 390 - 25, getHeight() - 60);
        mainChat.setSize(getWidth() - 390 - 25, getHeight() - 60);
        add(mainChat);
        mainChat.setVisible(false);
        moreInfor = new MoreInfor(theme, this, language, userInfor.getId(), sessionFactory);
        moreInfor.setBounds(getWidth() - 425, 10, 415, mainChat.getHeight());
        add(moreInfor);
        moreInfor.setVisible(false);

        contacts = new Contacts(theme, this, language, sessionFactory, mail, userInfor.getId());
        contacts.setBounds(70, 0, getWidth() - 25, mainChat.getHeight() + 25);
        contacts.setSize(getWidth() - 25, getHeight() + 25);
        add(contacts);
        contacts.setVisible(false);

        boolean checkCon = NetworkMonitor.isInternetAvailable();

        if (!checkCon) {
            showNoInternet();
            chatList.setVisible(false);
        } else initUI();

        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        out.writeUTF(mail);

        new Thread(() -> {
            try {
                while (true) {
                    String type = in.readUTF();

                    if (type.equals("message")) {
                        String message = in.readUTF();
                        int idmes = in.readInt();
                        SwingUtilities.invokeLater(() -> {
                            mainChat.showMesIn(message, idmes);
                            displayTray(inchatName, message);
                        });
                    } else if (type.equals("file")) {
                        int idmes = in.readInt();
                        String filename = in.readUTF();
                        long fileSize = in.readLong();
                        System.out.println(filename + " " + fileSize);
                        byte[] data = new byte[(int) fileSize];
                        in.readFully(data);
                        SwingUtilities.invokeLater(() -> {
                            mainChat.showMesFileIn(idmes, filename, data);
                            displayTray(inchatName, "Đã gửi một tin nhắn");
                        });
                    } else if (type.equals("update")) {
                        int idMes = in.readInt();
                        String textUpdate = in.readUTF();
                        SwingUtilities.invokeLater(() -> {
                            mainChat.editMesInterface(idMes, textUpdate, "another");
                        });
                    } else if (type.equals("delete")) {
                        int idMes = in.readInt();
                        SwingUtilities.invokeLater(() -> {
                            mainChat.deleteMesInterface(idMes);
                        });
                    } else if (type.equals("emojione")) {
                        int idMes = in.readInt();
                        String emojiUpdate = in.readUTF();
                        SwingUtilities.invokeLater(() -> {
                            mainChat.updateMesImojiInterface(idMes, emojiUpdate);
                        });
                    } else if (type.equals("emojigroup")) {
                        String emojiUpdate = in.readUTF();
                        int idMes = in.readInt();
                        SwingUtilities.invokeLater(() -> {
                            mainChat.updateMesImojiInterface(idMes, emojiUpdate);
                        });
                    } else if (type.equals("sendrequestgame")) {
                        if (!isShowNotify) {
                            isShowNotify = true;

                            int result = JOptionPane.showConfirmDialog(this, "Bạn muốn chơi game?", "Chơi game", JOptionPane.YES_NO_OPTION);
                            if (result == JOptionPane.YES_OPTION) {
                                try {
                                    String text = "@" + inchat + " " + "ok";

                                    out.writeUTF("repRequestGame");
                                    out.writeUTF(text);
                                    out.flush();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                mainChat.openGameWhenAccept();
                                mainChat.gameInterface.tfMe.setEditable(false);
                                mainChat.gameInterface.btnSend.setEnabled(false);
                            } else {
                                try {
                                    String text = "@" + inchat + " " + "no";

                                    out.writeUTF("repRequestGame");
                                    out.writeUTF(text);
                                    out.flush();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }

                            isShowNotify = false;
                        }

                    } else if (type.equals("repRequestGame")) {
                        String text = in.readUTF();
                        if (text.endsWith("ok")) {
                            mainChat.setRepGame(true);
                        } else {
                            mainChat.setDeniedGame(true);
                        }
                    } else if (type.equals("sendGameText")) {
                        String textGame = in.readUTF();
                        mainChat.gameInterface.tfOther.setText(textGame);
                        mainChat.gameInterface.tfMe.setEditable(true);
                        mainChat.gameInterface.btnSend.setEnabled(true);
                        mainChat.gameInterface.tfMe.setText("");
                        mainChat.gameInterface.startYourTurn();
                        mainChat.gameInterface.repaint();
                        mainChat.gameInterface.revalidate();
                    } else if (type.equals("quitGame")) {
                        JOptionPane.showMessageDialog(this, "Đối phương đã thoát game", "Chơi game", JOptionPane.INFORMATION_MESSAGE);
                        mainChat.gameInterface.dispose();
                        mainChat.gameInterface = null;
                    } else if (type.equals("sendWin")) {
                        mainChat.gameInterface.showWin();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        mainChat.btnSend.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String msg = mainChat.taChat.getText(); // Lấy nội dung từ text area
                if (!msg.trim().isEmpty()) {
                    if (isMes) {
                        String text = "@" + inchat + " " + msg;

                        try {
                            out.writeUTF("message");
                            out.writeUTF(text);
                            int idmes = mainChat.saveMes(msg, isMes);
                            out.writeInt(idmes);

                            mainChat.showMesOut(msg, idmes);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            out.writeUTF("groupmessage");
                            out.writeUTF(userInfor.getName() + ": " + msg);
                            int idmes = mainChat.saveMes(msg, isMes);
                            out.writeInt(idmes);
                            out.writeUTF(String.join(",", inGroup)); // Gửi danh sách email

                            mainChat.showMesOut(userInfor.getName() + ": " + msg, idmes);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    mainChat.taChat.setText("");
                }
            }
        });
        mainChat.btnfile.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JFileChooser chooser = new JFileChooser();
                int result = chooser.showOpenDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();

                    try {
                        byte[] filebyte = Function.convertFileToBytes(file);
                        String namefile = file.getName();

                        int idMes = mainChat.saveFile(namefile, filebyte, isMes);
                        mainChat.showMesFileOut(idMes, namefile, filebyte);

                        String text = "@" + inchat;
                        if (isMes) {
                            out.writeUTF("sendfileone");
                            out.writeInt(idMes);
                            out.writeUTF(text);
                            out.writeUTF(namefile);
                            out.writeLong(filebyte.length);
                            out.write(filebyte);
                            out.flush();
                        } else {
                            out.writeUTF("sendfilemore");
                            out.writeInt(idMes);
                            out.writeUTF(namefile);
                            out.writeLong(filebyte.length);
                            out.write(filebyte);
                            out.writeUTF(String.join(",", inGroup)); // Gửi danh sách email
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        mainChat.btnEmoji.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JPopupMenu emojiPopup = new JPopupMenu();
                JPanel emojiPanel = new JPanel(new KINKEN_CHAT.WrapLayout(FlowLayout.LEFT, 2, 2));

                File resourceDir = new File(PATHICON.getPath(""));
                File[] files = resourceDir.listFiles((dir, name) ->
                        name.startsWith("emoji_") && name.endsWith(".chat.imoji.png"));

                assert files != null;
                for (File file : files) {
                    ImageIcon icon = new ImageIcon(file.getPath());
                    Image scaledImage = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                    JLabel emojiLabel = new JLabel(new ImageIcon(scaledImage));
                    emojiLabel.setPreferredSize(new Dimension(30, 30));

                    emojiLabel.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            emojiPopup.setVisible(false);

                            try {
                                byte[] emojiByte = Function.convertFileToBytes(file);
                                String namefile = file.getName();

                                int idMes = mainChat.saveFile(namefile, emojiByte, isMes);
                                mainChat.showMesFileOut(idMes, namefile, emojiByte);

                                if (isMes) {
                                    out.writeUTF("sendfileone");
                                    out.writeInt(idMes);
                                    out.writeUTF("@" + inchat);
                                    out.writeUTF(namefile);
                                    out.writeLong(emojiByte.length);
                                    out.write(emojiByte);
                                    out.flush();
                                } else {
                                    out.writeUTF("sendfilemore");
                                    out.writeInt(idMes);
                                    out.writeUTF(namefile);
                                    out.writeLong(emojiByte.length);
                                    out.write(emojiByte);
                                    out.writeUTF(String.join(",", inGroup));
                                    out.flush();
                                }
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            emojiLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                            emojiLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                    });

                    emojiPanel.add(emojiLabel);
                }

                JScrollPane scrollPane = new JScrollPane(emojiPanel);
                scrollPane.setPreferredSize(new Dimension(185, 200));
                emojiPopup.add(scrollPane);
                emojiPopup.show(mainChat.btnEmoji, 0, -emojiPopup.getPreferredSize().height);
            }
        });
        mainChat.btnmic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                VoiceRecordDialog voice = new VoiceRecordDialog(null);
                voice.btnSend.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        byte[] audioRec = voice.getRecordedData();
                        try {
                            if (audioRec != null) {
                                String namefile = ".audio";

                                int idMes = mainChat.saveFile(namefile, audioRec, isMes);
                                mainChat.showMesFileOut(idMes, namefile, audioRec);

                                String text = "@" + inchat;
                                if (isMes) {
                                    out.writeUTF("sendfileone");
                                    out.writeInt(idMes);
                                    out.writeUTF(text);
                                    out.writeUTF(namefile);
                                    out.writeLong(audioRec.length);
                                    out.write(audioRec);
                                    out.flush();
                                } else {
                                    out.writeUTF("sendfilemore");
                                    out.writeInt(idMes);
                                    out.writeUTF(namefile);
                                    out.writeLong(audioRec.length);
                                    out.write(audioRec);
                                    out.writeUTF(String.join(",", inGroup)); // Gửi danh sách email
                                }
                                voice.dispose();
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                });
                voice.setVisible(true);
            }
        });

        mainChat.btnInfor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!isInforOpen) {
                    moreInfor.setVisible(true);
                    mainChat.setBounds(390, 10, getWidth() - 435 - 340, getHeight() - 60);
                    moreInfor.setBounds(getWidth() - 375, 10, 350, mainChat.getHeight());
                } else {
                    moreInfor.setVisible(false);
                    mainChat.setBounds(390, 10, getWidth() - 390 - 25, getHeight() - 60);
                }
                if (isInforOpen && !chatList.isVisible() && getWidth() > 1300) {
                    chatList.setVisible(true);
                    repaint();
                    revalidate();
                } else {
                    chatList.setVisible(true);
                    repaint();
                    revalidate();
                }
                if (getWidth() <= 900 && !isInforOpen) {
                    mainChat.setBounds(80, 10, getWidth() - 105, getHeight() - 60);
//                    mainChat.setVisible(false);
                    moreInfor.setBounds(getWidth() - 375, 10, getWidth() - 105, mainChat.getHeight());
                    moreInfor.setVisible(true);
                }
                if (chatList.isVisible() && getWidth() <= 1300) {
                    mainChat.setBounds(80, 10, getWidth() - 400 - 65, getHeight() - 60);
                    chatList.setVisible(false);
                    moreInfor.setVisible(true);
                }
                mainChat.repaint();
                mainChat.revalidate();
                mainChat.panelChat.repaint();
                mainChat.panelChat.revalidate();
                isInforOpen = !isInforOpen;
                repaint();
                revalidate();
            }
        });

        bar.btnChat.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                mainChat.setBounds(390, 10, getWidth() - 390 - 25, getHeight() - 60);
                mainChat.setVisible(true);
                chatList.setVisible(true);
                contacts.setVisible(false);
                isInforOpen = false;
                mainChat.changeBtnMoreStatus();
                System.out.println(isInforOpen);
                section = "chat";
                chatList.getAcc();
                repaint();
                revalidate();
            }
        });

        bar.btnContacts.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                contacts.setBounds(70, 0, getWidth() - 40, getHeight() - 35);
                chatList.setVisible(false);
                mainChat.setVisible(false);
                moreInfor.setVisible(false);
                contacts.setVisible(true);
                repaint();
                revalidate();
                section = "contacts";
                contacts.mainNewFriend.showAcp();
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setUserStatusByMail(mail, "off");
                try {
                    window.put("width", getWidth());
                    window.put("height", getHeight());
                    Files.writeString(Paths.get(filePath), window.toString(4), StandardOpenOption.TRUNCATE_EXISTING);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int windowWidth = getWidth();
                int windowHeight = getHeight();

                bar.setBounds(0, 0, 70, windowHeight);
                bar.updateSettingLocatation();
                contacts.setBounds(70, 0, getWidth() - 25, mainChat.getHeight() + 30);

                if (section.equals("chat")) {
                    if (isInforOpen) {
                        if (windowWidth > 1300) {
                            // 🔹 Cửa sổ lớn -> Hiển thị tất cả
                            chatList.setVisible(true);
                            mainChat.setBounds(390, 10, getWidth() - 435 - 340, getHeight() - 60);
                            moreInfor.setBounds(getWidth() - 375, 10, 350, mainChat.getHeight());
                            moreInfor.setVisible(true);
                        } else if (windowWidth > 900) {
                            // 🔹 Cửa sổ trung bình -> Ẩn `chatList`, giữ `moreInfor`
                            chatList.setVisible(false);
                            mainChat.setBounds(80, 10, windowWidth - 400 - 65, windowHeight - 60);
                            moreInfor.setBounds(getWidth() - 375, 10, 350, mainChat.getHeight());
                            moreInfor.setVisible(true);
                        } else {
                            // 🔹 Cửa sổ nhỏ -> Ẩn cả `moreInfor` và `chatList`
                            chatList.setVisible(false);
                            moreInfor.setVisible(false);
                            mainChat.setBounds(80, 10, windowWidth - 105, windowHeight - 60);
                        }
                    } else {
                        if (windowWidth > 900) {
                            // 🔹 `isInforOpen = false` -> Hiển thị `chatList`
                            chatList.setVisible(true);
                            chatList.setBounds(80, 10, 300, getHeight() - 60);
                            mainChat.setBounds(390, 10, windowWidth - 390 - 25, windowHeight - 60);
                            moreInfor.setVisible(false);
                        } else {
                            // 🔹 Cửa sổ nhỏ -> Ẩn `chatList`, chỉ giữ `mainChat`
                            chatList.setVisible(false);
                            mainChat.setBounds(80, 10, windowWidth - 105, windowHeight - 60);
                        }
                    }
                    chatList.setBounds(80, 10, 300, mainChat.getHeight());
                    repaint();
                } else if (section.equals("contacts")) {
                    contacts.setBounds(70, 0, getWidth() - 40, getHeight() - 35);
                }
            }
        });

        setVisible(true);
    }

    @Override
    public void logout() {
        try {
            window.put("account", "");
            Files.writeString(Paths.get(filePath), window.toString(4), StandardOpenOption.TRUNCATE_EXISTING);
            setUserStatusByMail(this.userInfor.getMail(), "off");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
        new Main();
    }

    public void displayTray(String name, String text) {
        try {
            SystemTray tray = SystemTray.getSystemTray();

            // Tạo icon giả (16x16)
            Image image = Toolkit.getDefaultToolkit().createImage("E:\\IT3\\Java\\DACS1\\IMAGE\\logo.png");
            TrayIcon trayIcon = new TrayIcon(image, "KinKen Chat");
            trayIcon.setImageAutoSize(true);
            trayIcon.setToolTip("KinKen Chat Notification");
            tray.add(trayIcon);

            // Gửi thông báo
            trayIcon.displayMessage(name, text, TrayIcon.MessageType.INFO);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserNow(String email) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            User user = session.createQuery("FROM User WHERE mail = :mail", User.class)
                    .setParameter("mail", email)
                    .uniqueResult();

            if (user != null) {
                user.setStatus("online");
                session.merge(user);

                userInfor = new UserInfor(user.getId(),
                        user.getName(),
                        user.getNickname(),
                        user.getBirthday(),
                        user.getMail(),
                        user.getAvatarImg(),
                        user.getStatus(),
                        user.getCreateAt(),
                        user.getCoverImg(),
                        user.getSex(),
                        user.getBio(),
                        user.getSizeAtv(),
                        user.getSizeCover());

            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close(); // CHỈ đóng session, KHÔNG đóng sessionFactory
        }

    }

    private void setUserStatusByMail(String mail, String status) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            User user = session.createQuery("FROM User WHERE mail = :mail", User.class)
                    .setParameter("mail", mail)
                    .uniqueResult();

            if (user != null) {
                user.setStatus(status); // "online" hoặc "offline"
                session.update(user);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void changeTheme(String theme) {
        this.theme = theme;

        if (theme.equals("light")) {
            getContentPane().setBackground(colorTheme.blackLight50);
        } else {
            getContentPane().setBackground(colorTheme.blackLight15);
        }

        try {
            window.put("theme", this.theme);
            Files.writeString(Paths.get(filePath), window.toString(4), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        bar.changeTheme(theme);
        chatList.changeTheme(theme);
        mainChat.changeTheme(theme);
        moreInfor.changeTheme(theme);
        contacts.changeTheme(theme);
        revalidate();
        repaint();
    }

    @Override
    public void changeLanguage(String language) {
        this.language = language;

        try {
            window.put("language", this.language);
            Files.writeString(Paths.get(filePath), window.toString(4), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        bar.changLanguage(this.language);
        chatList.changeLang(this.language);
        mainChat.changeLanguage(this.language);
        moreInfor.changeLanguage(this.language);
        contacts.changeLanguage(this.language);
    }

    @Override
    public void changeBackground(String backCode) {

    }

    public void updateMes(int idMes, String newMes) {
        try {
            String text = "@" + inchat + " " + newMes;

            out.writeUTF("update");
            out.writeUTF(text);
            out.writeInt(idMes);
            out.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void deleteMes(int idMes) {
        try {
            String text = "@" + inchat + " " + "change";

            out.writeUTF("delete");
            out.writeUTF(text);
            out.writeInt(idMes);
            out.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void imoji(int idMes, String imoji) {
        try {
            String text = "@" + inchat + " " + imoji;

            out.writeUTF("emojione");
            out.writeUTF(text);
            out.writeInt(idMes);
            out.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void imojiGr(int idMes, String imoji) {
        try {
            out.writeUTF("emojigroup");
            out.writeUTF(imoji);
            out.writeInt(idMes);
            out.writeUTF(String.join(",", inGroup));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteChatReload() {
        mainChat.repaint();
        mainChat.revalidate();
    }

    @Override
    public void sendRequestGame() {
        try {
            String text = "@" + inchat + " " + "game";

            out.writeUTF("sendrequestgame");
            out.writeUTF(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void repRequestGame(int idRe, String mail, String status) {

    }
    @Override
    public void sendGameText(String text) {
        try {
            String mesGame = "@" + inchat + " " + text;

            out.writeUTF("sendGameText");
            out.writeUTF(mesGame);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void reGameText(String text) {

    }
    public void quitGame() {
        try {
            String mesGame = "@" + inchat + " " + "quit";

            out.writeUTF("quitGame");
            out.writeUTF(mesGame);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendWin() {
        try {
            String mesGame = "@" + inchat + " " + "win";

            out.writeUTF("sendWin");
            out.writeUTF(mesGame);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showNoInternet() {
        noInternetPanel = new NoInternet(this.theme, this.language);
        noInternetPanel.setBounds(0, 0, getWidth(), getHeight());
        add(noInternetPanel);
        noInternetPanel.setVisible(true);
        repaint();
        revalidate();
    }

    private void initUI() {
        bar.setVisible(true);
        chatList.setVisible(true);
        mainChat.setVisible(true);
    }
}

class NoInternet extends JPanel {
    JLabel lbNoti = new JLabel("");
    JButton btnReset;

    private String fontName = "Meiryo";
    private String theme;
    private String language;
    private Language lang;
    private ColorTheme colorTheme = new ColorTheme();

    public NoInternet(String theme, String language) {
        this.theme = theme;
        this.language = language;
        setLayout(null);

        lang = new Language(this.language);
        lang.loadLanguage();

        lbNoti.setText("<html>" +lang.getString("checkInternet.lbNoti" +"</html>"));
        lbNoti.setHorizontalAlignment(SwingConstants.CENTER);
        lbNoti.setFont(new Font(fontName, Font.BOLD, 20));
        lbNoti.setOpaque(false);
        Dimension titleSize = lbNoti.getPreferredSize();
        lbNoti.setBounds((getWidth() - titleSize.width) / 2, 10, titleSize.width, 40);

        add(lbNoti);
    }
}

class NetworkMonitor {
    public static boolean isInternetAvailable() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (Exception e) {
            return false;
        }
    }
}