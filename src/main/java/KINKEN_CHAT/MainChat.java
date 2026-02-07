package KINKEN_CHAT;

import Hibernate.Mes;
import Hibernate.MesImoji;
import Hibernate.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.sound.sampled.TargetDataLine;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.util.Timer;
import java.util.TimerTask;

public class MainChat extends JPanel {
    JLabel lbName;
    JLabel lbStatus;
    JButton btnCall;
    JButton btnCallVideo;
    JButton btnSearch;
    JButton btnInfor;
    JButton btnmore;
    JButton btnmic;
    JButton btnfile;
    JButton btnEmoji;
    JTextArea taChat;
    JScrollPane scChat;
    JButton btnSend;
    JScrollPane scInChat;

    JPanel panelChat;
    JButton btnAVT;
    JFrame fatherFrame;

    Image imgCall = new ImageIcon(PATHICON.getPath("call.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    Image imgCallVideo = new ImageIcon(PATHICON.getPath("callvideo.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    Image imgSearch = new ImageIcon(PATHICON.getPath("search.png")).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
    Image imgInfor = new ImageIcon(PATHICON.getPath("inforfrieng.png")).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
    Image imgmore = new ImageIcon(PATHICON.getPath("more.png")).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
    Image imgMic = new ImageIcon(PATHICON.getPath("mic.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    Image imgFile = new ImageIcon(PATHICON.getPath("file.png")).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
    Image imgCallDark = new ImageIcon(PATHICON.getPath("callDark.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    Image imgCallVideoDark = new ImageIcon(PATHICON.getPath("callvideoDark.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    Image imgSearchDark = new ImageIcon(PATHICON.getPath("searchDark.png")).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
    Image imgInforDark = new ImageIcon(PATHICON.getPath("inforfriengDark.png")).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
    Image imgmoreDark = new ImageIcon(PATHICON.getPath("moreDark.png")).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
    Image imgMicDark = new ImageIcon(PATHICON.getPath("micDark.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    Image imgFileDark = new ImageIcon(PATHICON.getPath("fileDark.png")).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
    Image imgSend = new ImageIcon(PATHICON.getPath("sendLight.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    Image imgSendDark = new ImageIcon(PATHICON.getPath("sendDark.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    Image imgEmoji = new ImageIcon(PATHICON.getPath("default.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    Image imgGame = new ImageIcon(PATHICON.getPath("emoji_game.chat.imoji.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    Image imgLocation = new ImageIcon(PATHICON.getPath("emoji_location.chat.imoji.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

    static ColorTheme colorTheme = new ColorTheme();

    private SessionFactory sessionFactory;
    private List<Mes> mesList = new ArrayList<>();

    private ActionChange themeChange;
    private String theme;
    private String lang;
    private boolean isInfor = false;
    private boolean isMore = false;
    private int ID_RE;
    private final int ID_ME;
    private boolean isMes;
    private byte[] AVT_RE;
    private String myName;
    private String mailRe;
    private final byte[] AVT_ME;

    private Language language;
    private String fontName = "Arial";

    GameInterface gameInterface;
    private boolean isRepGame = false;
    private boolean isDeniedGame = false;
    Thread waitThread;

    public boolean isRepGame() {
        return isRepGame;
    }

    public void setRepGame(boolean repGame) {
        isRepGame = repGame;
    }

    public boolean isDeniedGame() {
        return isDeniedGame;
    }

    public void setDeniedGame(boolean deniedGame) {
        isDeniedGame = deniedGame;
    }

    public MainChat(String themes, ActionChange themeChange, String langu, int id_me, SessionFactory sc, byte[] avtME, JFrame fatherFrame, String myName) {
        this.themeChange = themeChange;
        this.theme = themes;
        this.lang = langu;
        this.ID_ME = id_me;
        this.sessionFactory = sc;
        this.AVT_ME = avtME;
        this.fatherFrame = fatherFrame;
        this.myName = myName;
        setOpaque(false);
        setLayout(null);

        language = new Language(this.lang);
        language.loadLanguage();

        lbName = new JLabel();
        lbStatus = new JLabel();
        btnCall = new Round("", colorTheme.transparent, Color.black, 10);
        btnCallVideo = new Round("", colorTheme.transparent, Color.black, 10);
        btnSearch = new Round("", colorTheme.transparent, Color.black, 10);
        btnInfor = new Round("", colorTheme.transparent, Color.black, 10);
        btnmore = new Round("", colorTheme.transparent, Color.black, 10);
        btnmic = new Round("", colorTheme.transparent, Color.black, 10);
        btnfile = new Round("", colorTheme.transparent, Color.black, 10);
        btnEmoji = new Round("", colorTheme.transparent, Color.black, 10);
        btnSend = new Round("", colorTheme.transparent, Color.black, 10);
        btnAVT = new Round("", colorTheme.transparent, colorTheme.transparent, 50);
        btnAVT.setBounds(30, 5, 50, 50);

        lbName.setFont(new Font(fontName, Font.BOLD, 20));
        lbName.setBackground(colorTheme.transparent);
        lbStatus.setFont(new Font(fontName, Font.BOLD, 14));

        taChat = new JTextArea();
        taChat.setLineWrap(true);
        taChat.setWrapStyleWord(true);
        taChat.setFont(new Font(fontName, Font.PLAIN, 22));

        scChat = new JScrollPane(taChat);
        scChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        initScrollPane();
        changeTheme(themes);

        btnEmoji.setIcon(new ImageIcon(imgEmoji));

        btnCall.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (theme.equals("light")) btnCall.setBackground(colorTheme.blackLight35);
                else btnCall.setBackground(colorTheme.whiteDark35);
            }
            public void mouseEntered(MouseEvent e) {
                if (theme.equals("light")) btnCall.setBackground(colorTheme.whiteDark25);
                else btnCall.setBackground(colorTheme.blackLight50);
                btnCall.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnCall.setBackground(colorTheme.transparent);
                btnCall.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnCallVideo.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (theme.equals("light")) btnCallVideo.setBackground(colorTheme.whiteDark15);
                else btnCallVideo.setBackground(colorTheme.blackLight15);
            }
            public void mouseEntered(MouseEvent e) {
                if (theme.equals("light")) btnCallVideo.setBackground(colorTheme.whiteDark15);
                else btnCallVideo.setBackground(colorTheme.blackLight50);
                btnCallVideo.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnCallVideo.setBackground(colorTheme.transparent);
                btnCallVideo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnSearch.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (theme.equals("light")) btnSearch.setBackground(colorTheme.whiteDark15);
                else btnSearch.setBackground(colorTheme.blackLight15);
                JPopupMenu popupMenu = new JPopupMenu();

                JPanel panelSearchText = new JPanel(new BorderLayout(5, 5));
                panelSearchText.setPreferredSize(new Dimension(250, 30));
                JTextField tfSearch = new JTextField();
                JButton btnSearchpopup = new JButton("🔎");
                panelSearchText.add(tfSearch, BorderLayout.CENTER);
                panelSearchText.add(btnSearchpopup, BorderLayout.EAST);
                popupMenu.add(panelSearchText);

                JPanel panelListMesFinded = new JPanel();
                panelListMesFinded.setLayout(new BoxLayout(panelListMesFinded, BoxLayout.Y_AXIS));
                btnSearchpopup.addActionListener(e1 -> {
                    String searchText = tfSearch.getText();
                    if (!searchText.isEmpty()) {
                        panelListMesFinded.removeAll();
                        panelListMesFinded.revalidate();
                        panelListMesFinded.repaint();
                        showMesFound(panelListMesFinded, searchText);
                        popupMenu.remove(panelListMesFinded);
                        popupMenu.add(panelListMesFinded);
                        popupMenu.setVisible(false);
                        popupMenu.show(btnSearch, e.getX() - 250, e.getY());
                        repaint();
                        revalidate();
                    }
                });

                popupMenu.show(btnSearch, e.getX() - 250, e.getY());
            }
            public void mouseEntered(MouseEvent e) {
                if (theme.equals("light")) btnSearch.setBackground(colorTheme.whiteDark15);
                else btnSearch.setBackground(colorTheme.blackLight50);
                btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnSearch.setBackground(colorTheme.transparent);
                btnSearch.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnInfor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!isInfor) {
                    btnInfor.setBackground(colorTheme.purpleLight);
                    btnInfor.setIcon(new ImageIcon(imgInforDark));
                    isInfor = true;
                } else {
                    btnInfor.setBackground(colorTheme.transparent);
                    if (theme.equals("light")) btnInfor.setIcon(new ImageIcon(imgInfor));
                    else btnInfor.setIcon(new ImageIcon(imgInforDark));
                    isInfor = false;
                }

            }
            public void mouseEntered(MouseEvent e) {
                if (!isInfor) {
                    if (theme.equals("light")) btnInfor.setBackground(colorTheme.whiteDark15);
                    else btnInfor.setBackground(colorTheme.blackLight50);
                    btnInfor.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

            }
            public void mouseExited(MouseEvent e) {
                if (!isInfor) btnInfor.setBackground(colorTheme.transparent);
                btnInfor.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnmore.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem game = new JMenuItem();
                game.setIcon(new ImageIcon(imgGame));
                game.setText("Chơi chung");
                JMenuItem location = new JMenuItem();
                location.setIcon(new ImageIcon(imgLocation));
                location.setText("Gửi định vị");

                game.addActionListener(ev -> {
                    // Gửi yêu cầu chơi game cho đối phương

                    if (waitThread != null && waitThread.isAlive()) {
                        waitThread.interrupt();
                    }
                    isRepGame = false;
                    isDeniedGame = false;
                    themeChange.sendRequestGame();

                    // Đợi phản hồi từ đối phương trong 10 giây
                    waitThread = new Thread(() -> {
                        int waitTime = 0;
                        while (!isRepGame && !isDeniedGame && waitTime < 10000) {
                            try {
                                Thread.sleep(200);
                                waitTime += 200;
                            } catch (InterruptedException ex) {
                                return;
                            }
                        }

                        if (isRepGame) {
                            SwingUtilities.invokeLater(() -> {
                                openGameWhenAccept();
                                gameInterface.tfMe.setEditable(true);
                            });
                        } else if (isDeniedGame) {
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(null, "Đối phương đã từ chối lời mời chơi game.");
                            });
                        } else {
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(null, "Không có phản hồi từ đối phương.");
                            });
                        }
                    });
                    waitThread.start();

                    popupMenu.setVisible(false);
                });

                popupMenu.add(game);
                popupMenu.add(location);

                int x = 0; // hoặc căn giữa nếu muốn
                int y = -popupMenu.getPreferredSize().height;

                popupMenu.show(btnmore, x, y);
            }
            public void mouseEntered(MouseEvent e) {
                if (!isMore) {
                    if (theme.equals("light")) btnmore.setBackground(colorTheme.whiteDark15);
                    else btnmore.setBackground(colorTheme.blackLight50);
                }
                btnmore.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                if (!isMore) btnmore.setBackground(colorTheme.transparent);
                btnmore.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnmic.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (theme.equals("light")) btnmic.setBackground(colorTheme.whiteDark15);
                else btnmic.setBackground(colorTheme.blackLight15);

//                VoiceRecordDialog voice = new VoiceRecordDialog(null);
//                voice.btnSend.addActionListener(new ActionListener() {
//                    public void actionPerformed(ActionEvent e) {
//                        byte[] audioRec = voice.getRecordedData();
//                        if (audioRec != null) {
//
//                            voice.dispose();
//                        }
//                    }
//                });
//                voice.setVisible(true);
            }
            public void mouseEntered(MouseEvent e) {
                if (theme.equals("light")) btnmic.setBackground(colorTheme.whiteDark15);
                else btnmic.setBackground(colorTheme.blackLight50);
                btnmic.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnmic.setBackground(colorTheme.transparent);
                btnmic.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnfile.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (theme.equals("light")) btnfile.setBackground(colorTheme.whiteDark15);
                else btnfile.setBackground(colorTheme.blackLight15);


            }
            public void mouseEntered(MouseEvent e) {
                if (theme.equals("light")) btnfile.setBackground(colorTheme.whiteDark15);
                else btnfile.setBackground(colorTheme.blackLight50);
                btnfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnfile.setBackground(colorTheme.transparent);
                btnfile.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnEmoji.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnEmoji.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnEmoji.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnSend.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (theme.equals("light")) btnSend.setBackground(colorTheme.whiteDark15);
                else btnSend.setBackground(colorTheme.blackLight15);
            }
            public void mouseEntered(MouseEvent e) {
                if (theme.equals("light")) btnSend.setBackground(colorTheme.whiteDark15);
                else btnSend.setBackground(colorTheme.blackLight50);
                btnSend.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnSend.setBackground(colorTheme.transparent);
                btnSend.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        taChat.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {resizeTextArea();}

            @Override
            public void removeUpdate(DocumentEvent e) {resizeTextArea();}

            public void changedUpdate(DocumentEvent e) {resizeTextArea();}
        });

        panelChat.setBackground(colorTheme.whiteDark15);
        scInChat = new JScrollPane(panelChat);

        add(lbName);
        add(lbStatus);
        add(btnCall);
        add(btnCallVideo);
        add(btnSearch);
        add(btnInfor);
        add(btnmore);
        add(btnmic);
        add(btnfile);
        add(btnEmoji);
        add(scChat);
        add(btnSend);
        add(scInChat);
        add(btnAVT);
    }

    public void openGameWhenAccept() {
        gameInterface = new GameInterface(
                ID_ME, AVT_ME, myName,
                ID_RE, AVT_RE, lbName.getText(),
                themeChange
        );
        gameInterface.setVisible(true);
    }

    private void initScrollPane() {
        panelChat = new JPanel();
        panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.Y_AXIS));
        panelChat.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                repaint();
            }
        });

        scInChat = new JScrollPane(panelChat);
        scInChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scInChat.setBorder(null);
//        scInChat.setBackground(colorTheme.transparent);

        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalBar = scInChat.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());
        });
    }

    public void showMesIn(String mes, int idmes) {
        Image imgAVT = Function.resizeImage(new ImageIcon(this.AVT_RE).getImage(), 40, 40);
        JButton btnAVT = new JButton();
        btnAVT.setSize(50, 50);
        btnAVT.setBorder(null);
        btnAVT.setIcon(new ImageIcon(imgAVT));

        JLabel lbMes = new JLabel("<html><body style='width: 250px; background: rgb(230, 230, 230); padding: 10px;'><div style='max-width: 250px'>" + mes + "</div></body></html>");
        lbMes.setFont(new Font(fontName, Font.PLAIN, 14));
        lbMes.setOpaque(true);
        lbMes.setBackground(colorTheme.whiteDark15);
        lbMes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding
        lbMes.putClientProperty("rawText", mes);
        lbMes.putClientProperty("namesend", "Re");
        lbMes.putClientProperty("idMes", idmes);

        String type = "";
        MesImoji mesImoji = getImoji(idmes);
        if (mesImoji != null) {
            type = mesImoji.getImojiType();
            System.out.println("type: " + type);
        }

        ImageIcon icon = new ImageIcon(Function.resizeImage(
                new ImageIcon(PATHICON.getPath(checkImoji(type))).getImage(), 30, 30));
        JButton btnIcon = new JButton(icon);
        btnIcon.setBorder(null);
        btnIcon.setPreferredSize(new Dimension(30, 30)); // Đảm bảo chỗ chứa đủ
        btnIcon.setBackground(colorTheme.whiteDark15);
        lbMes.putClientProperty("btnicon", btnIcon);

        btnIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JPopupMenu popupMenu = new JPopupMenu();

                JPanel panelIcon = new JPanel();
                panelIcon.setLayout(new BoxLayout(panelIcon, BoxLayout.X_AXIS));
                panelIcon.setBackground(Color.WHITE); // nên set màu
                panelIcon.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                // Tạo các nút emoji
                String[] emojiTypes = {"like", "heart", "haha", "sad", "wow", "cry", "default"};
                for (String emo : emojiTypes) {
                    ImageIcon icon = new ImageIcon(Function.resizeImage(
                            new ImageIcon(PATHICON.getPath(checkImoji(emo))).getImage(), 30, 30));
                    JButton btn = new JButton(icon);
                    btn.setContentAreaFilled(false);
                    btn.setBorder(null);
                    btn.setFocusPainted(false);
                    btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    panelIcon.add(btn);

                    // Thêm action cho từng emoji
                    btn.addActionListener(ev -> {
                        updateMesImoji(idmes, emo);
                        if (themeChange != null) {
                            if (isMes) {
                                themeChange.imoji(idmes, emo);
                            } else {
                                themeChange.imojiGr(idmes, emo);
                            }

                        }
                        btnIcon.setIcon(new ImageIcon(Function.resizeImage(
                                new ImageIcon(PATHICON.getPath(checkImoji(emo))).getImage(), 30, 30)));
                        repaint();
                        revalidate();
                        popupMenu.setVisible(false);
                    });

                }
                popupMenu.add(panelIcon);

                int x = btnIcon.getWidth() / 2 - popupMenu.getPreferredSize().width / 2;
                popupMenu.show(btnIcon, x, btnIcon.getY() - 80);
            }

            public void mouseEntered(MouseEvent e) {
                btnIcon.setBackground(colorTheme.whiteDark25);
                btnIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnIcon.setBackground(colorTheme.whiteDark15);
                btnIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        messagePanel.setOpaque(false);

        messagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem reply = new JMenuItem("Trả lời");
                    JMenuItem delete = new JMenuItem("Gỡ về phía bạn");
                    delete.setForeground(Color.RED);

                    delete.addActionListener(e1 -> {

                    });

                    popupMenu.add(reply);
                    popupMenu.add(delete);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        messagePanel.add(btnAVT);
        messagePanel.add(lbMes);
        messagePanel.add(btnIcon);

        panelChat.add(messagePanel);
        panelChat.revalidate();
        panelChat.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalBar = scInChat.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());
        });
    }

    public void showMesOut(String mes, int idMes) {
        JLabel lbMes = new JLabel("<html><body style='width: 250px;'><div style='auto'><p style='width: fix-content; background: rgb(194, 249, 255); padding: 10px; text-align: right; border-radius: 10px;'>" + mes +"</p></div></body></html>");
        lbMes.setFont(new Font(fontName, Font.PLAIN, 14));
        lbMes.setOpaque(true);
        lbMes.setBackground(colorTheme.whiteDark15);
//        lbMes.setBackground(new Color(194, 249, 255));  // màu nền
        lbMes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // padding
        lbMes.putClientProperty("rawText", mes);
        lbMes.putClientProperty("namesend", "Me");
        lbMes.putClientProperty("idMes", idMes);

        String type = "";
        MesImoji mesImoji = getImoji(idMes);
        if (mesImoji != null) {
            type = mesImoji.getImojiType();
        }

        ImageIcon icon = new ImageIcon(Function.resizeImage(
                new ImageIcon(PATHICON.getPath(checkImoji(type))).getImage(), 30, 30));
        JButton btnIcon = new JButton(icon);
        btnIcon.setBorder(null);
        btnIcon.setPreferredSize(new Dimension(30, 30)); // Đảm bảo chỗ chứa đủ
        btnIcon.setBackground(colorTheme.whiteDark15);
        lbMes.putClientProperty("btnicon", btnIcon);

        btnIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
            public void mouseEntered(MouseEvent e) {
                btnIcon.setBackground(colorTheme.whiteDark25);
                btnIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnIcon.setBackground(colorTheme.whiteDark15);
                btnIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0 ,0));
        messagePanel.setOpaque(false);
        messagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem copy = new JMenuItem("Sao chép tin nhắn");
                    JMenuItem reply = new JMenuItem("Trả lời");
                    JMenuItem edit = new JMenuItem("Sửa");
                    JMenuItem delete = new JMenuItem("Gỡ");
                    JMenuItem deleteyou = new JMenuItem("Gỡ về phía bạn");
                    delete.setForeground(Color.RED);
                    deleteyou.setForeground(Color.RED);

                    copy.addActionListener(e1 -> {
                        StringSelection selection = new StringSelection(mes);
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(selection, selection);
                    });
                    edit.addActionListener(e1 -> {
                        JTextField tfEditMes = new JTextField(mes);
                        int result = JOptionPane.showConfirmDialog(fatherFrame,
                                tfEditMes,
                                "Sửa tin nhắn",
                                JOptionPane.OK_CANCEL_OPTION);

                        if (result == JOptionPane.OK_OPTION) {
                            String newMes = tfEditMes.getText();
                            if (!newMes.trim().equals(mes.trim())) {
                                editMes(idMes, newMes);
                                if (themeChange != null) {
                                    themeChange.updateMes(idMes, newMes);
                                }
                            }
                        }

                    });
                    delete.addActionListener(e1 -> {
                        deleteMes(idMes);
                        if (themeChange != null) {
                            themeChange.deleteMes(idMes);
                        }
                    });

                    popupMenu.add(copy);
                    popupMenu.add(reply);
                    popupMenu.add(edit);
                    popupMenu.add(delete);
                    popupMenu.add(deleteyou);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        messagePanel.add(btnIcon);
        messagePanel.add(lbMes);

//        messagePanel.setBackground(colorTheme.purpleLight);
//        messagePanel.add(btnAVT);

        panelChat.add(messagePanel);
        panelChat.revalidate();
        panelChat.repaint();

        SwingUtilities.invokeLater(() -> {
            JScrollBar verticalBar = scInChat.getVerticalScrollBar();
            verticalBar.setValue(verticalBar.getMaximum());
        });
    }

    public void showMesFileOut(int idMes, String fileName, byte[] fileData) {
        JPanel messagePanel = createMessagePanel(idMes, fileName, fileData, true);
        panelChat.add(messagePanel);
        panelChat.revalidate();
        panelChat.repaint();
    }

    public void showMesFileIn(int idMes, String fileName, byte[] fileData) {
        JPanel messagePanel = createMessagePanel(idMes, fileName, fileData, false);
        panelChat.add(messagePanel);
        panelChat.revalidate();
        panelChat.repaint();
    }

    private JPanel createMessagePanel(int idMes, String fileName, byte[] fileData, boolean isOut) {
        JPanel messagePanel = new JPanel(new FlowLayout(isOut ? FlowLayout.RIGHT : FlowLayout.LEFT));
        messagePanel.setOpaque(false);

        // Lấy emoji type từ DB
        String type = "";
        MesImoji mesImoji = getImoji(idMes);
        if (mesImoji != null) {
            type = mesImoji.getImojiType();
        }

        // Tạo nút emoji
        ImageIcon iconEmoji = new ImageIcon(Function.resizeImage(
                new ImageIcon(PATHICON.getPath(checkImoji(type))).getImage(), 30, 30));
        JButton btnIcon = new JButton(iconEmoji);
        btnIcon.setBorder(null);
        btnIcon.setPreferredSize(new Dimension(30, 30));
        btnIcon.setBackground(colorTheme.whiteDark15);
        btnIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        messagePanel.putClientProperty("btnicon", btnIcon);

        if (!isOut) {
            btnIcon.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    btnIcon.setBackground(colorTheme.whiteDark25);
                }

                public void mouseExited(MouseEvent e) {
                    btnIcon.setBackground(colorTheme.whiteDark15);
                }

                public void mouseClicked(MouseEvent e) {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JPanel panelIcon = new JPanel();
                    panelIcon.setLayout(new BoxLayout(panelIcon, BoxLayout.X_AXIS));
                    panelIcon.setBackground(Color.WHITE);
                    panelIcon.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                    String[] emojiTypes = {"like", "heart", "haha", "sad", "wow", "cry", ""};
                    for (String emo : emojiTypes) {
                        ImageIcon icon = new ImageIcon(Function.resizeImage(
                                new ImageIcon(PATHICON.getPath(checkImoji(emo))).getImage(), 30, 30));
                        JButton btn = new JButton(icon);
                        btn.setContentAreaFilled(false);
                        btn.setBorder(null);
                        btn.setFocusPainted(false);
                        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                        btn.addActionListener(ev -> {
                            updateMesImoji(idMes, emo);
                            if (themeChange != null) {
                                if (isMes) {
                                    themeChange.imoji(idMes, emo);
                                } else {
                                    themeChange.imojiGr(idMes, emo);
                                }

                            }
                            btnIcon.setIcon(new ImageIcon(Function.resizeImage(
                                    new ImageIcon(PATHICON.getPath(checkImoji(emo))).getImage(), 30, 30)));
                            popupMenu.setVisible(false);
                            repaint();
                            revalidate();
                        });

                        panelIcon.add(btn);
                    }

                    popupMenu.add(panelIcon);
                    int x = btnIcon.getWidth() / 2 - popupMenu.getPreferredSize().width / 2;
                    popupMenu.show(btnIcon, x, btnIcon.getY() - 80);
                }
            });

            Image imgAVT = Function.resizeImage(new ImageIcon(this.AVT_RE).getImage(), 40, 40);
            JButton btnAVT = new JButton(new ImageIcon(imgAVT));
            btnAVT.setPreferredSize(new Dimension(50, 50));
            btnAVT.setBorder(null);
            btnAVT.setContentAreaFilled(false);
            messagePanel.add(btnAVT);
        }

        // Nội dung tin nhắn
        JComponent content = createMessageContent(fileName, fileData, idMes);

        // Thêm theo thứ tự dựa vào isOut
        if (isOut) {
            messagePanel.add(btnIcon);
            messagePanel.add(content);
        } else {
            messagePanel.add(content);
            messagePanel.add(btnIcon);
        }

        // Menu chuột phải
        if (isOut) {
            messagePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem copy = new JMenuItem("Sao chép");
                        JMenuItem reply = new JMenuItem("Trả lời");
                        JMenuItem delete = new JMenuItem("Gỡ");
                        JMenuItem deleteYou = new JMenuItem("Gỡ phía bạn");

                        delete.setForeground(Color.RED);
                        deleteYou.setForeground(Color.RED);

                        delete.addActionListener(e1 -> {
                            deleteMes(idMes);
                            if (themeChange != null) {
                                themeChange.deleteMes(idMes);
                            }
                        });

                        popupMenu.add(copy);
                        popupMenu.add(reply);
                        popupMenu.add(delete);
                        popupMenu.add(deleteYou);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });
        }

        return messagePanel;
    }
    private JComponent createMessageContent(String fileName, byte[] fileData, int idMes) {
        String lower = fileName.toLowerCase();

        try {
            if (lower.startsWith("emoji_") && lower.endsWith(".chat.imoji.png")) {
                ImageIcon icon = new ImageIcon(fileData);
                Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                JLabel imgLabel = new JLabel(new ImageIcon(scaledImage));
                Box box = Box.createVerticalBox();
                box.add(imgLabel);
                imgLabel.putClientProperty("idMes", idMes);
                return box;
            } else if (lower.endsWith(".png") || lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".gif")) {
                ImageIcon icon = new ImageIcon(fileData);
                Image scaledImage = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                JLabel imgLabel = new JLabel(new ImageIcon(scaledImage));
                imgLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                imgLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        showFullImageDialog(fileData, fileName);
                    }
                });

                Box box = Box.createVerticalBox();
                box.add(imgLabel);
                imgLabel.putClientProperty("idMes", idMes);
                return box;
            } else if (lower.equals(".audio")) {
                JPanel voicePanel = createVoiceMessagePanel(fileData);
                voicePanel.putClientProperty("idMes", idMes);
                return voicePanel;
            } else if (lower.endsWith(".mp4")) {
                JPanel miniVideo = createMiniVideoPreviewPanel(fileData, fileName);
                miniVideo.putClientProperty("idMes", idMes);
                return miniVideo;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return createFileLabel(fileName, fileData);
        }

        return createFileLabel(fileName, fileData);
    }
    private void showFullImageDialog(byte[] imageData, String fileName) {
        JLayeredPane layeredPane = fatherFrame.getLayeredPane();

        // Tạo panel phủ
        JPanel overlayPanel = new JPanel(new BorderLayout());
        overlayPanel.setBounds(0, 0, fatherFrame.getWidth() - 15, fatherFrame.getHeight() - 30);
        overlayPanel.setBackground(new Color(0, 0, 0, 180)); // nền đen mờ
        overlayPanel.setOpaque(true);

        // Ảnh co giãn
        ImageIcon icon = new ImageIcon(imageData);
        Image image = icon.getImage();

        int frameWidth = fatherFrame.getWidth();
        int frameHeight = fatherFrame.getHeight();
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);

        double imageRatio = (double) imageWidth / imageHeight;
        double frameRatio = (double) frameWidth / frameHeight;

        int newWidth, newHeight;
        if (imageRatio > frameRatio) {
            newWidth = frameWidth;
            newHeight = (int) (frameWidth / imageRatio);
        } else {
            newHeight = frameHeight;
            newWidth = (int) (frameHeight * imageRatio);
        }

        Image scaled = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(scaled));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setVerticalAlignment(SwingConstants.CENTER);

        // Nút download
        JButton btnDownload = new JButton("Download");
        btnDownload.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File(fileName));
            if (fileChooser.showSaveDialog(fatherFrame) == JFileChooser.APPROVE_OPTION) {
                try {
                    Files.write(fileChooser.getSelectedFile().toPath(), imageData);
                    JOptionPane.showMessageDialog(fatherFrame, "Downloaded successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(fatherFrame, "Error saving file: " + ex.getMessage());
                }
            }
        });

        // Nút đóng
        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            layeredPane.remove(overlayPanel);
            layeredPane.repaint();
        });

        // Panel chứa nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(0, 0, 0, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnDownload);
        buttonPanel.add(btnClose);

        overlayPanel.add(imgLabel, BorderLayout.CENTER);
        overlayPanel.add(buttonPanel, BorderLayout.NORTH);

        layeredPane.add(overlayPanel, JLayeredPane.POPUP_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();
    }
    private File createTempVideoFile(byte[] videoData, String fileName) throws IOException {
        File tempFile = File.createTempFile("video_", "_" + fileName);
        tempFile.deleteOnExit();
        Files.write(tempFile.toPath(), videoData);
        return tempFile;
    }
    private void showFullVideoPanel(File videoFile, String fileName) {
        JLayeredPane layeredPane = fatherFrame.getLayeredPane();

        // Panel phủ mờ
        JPanel overlayPanel = new JPanel(new BorderLayout());
        overlayPanel.setBounds(0, 0, fatherFrame.getWidth() - 15, fatherFrame.getHeight() - 38);
        overlayPanel.setBackground(new Color(0, 0, 0, 180));
        overlayPanel.setOpaque(true);

        EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        overlayPanel.add(mediaPlayerComponent, BorderLayout.CENTER);

        JButton btnDownload = new JButton("Download");
        btnDownload.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File(fileName));
            if (fileChooser.showSaveDialog(fatherFrame) == JFileChooser.APPROVE_OPTION) {
                try {
                    Files.copy(videoFile.toPath(), fileChooser.getSelectedFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(fatherFrame, "Downloaded successfully!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(fatherFrame, "Error saving file: " + ex.getMessage());
                }
            }
        });

        // ======= CONTROLS: PLAY/PAUSE, TIMELINE, VOLUME =======
        JButton btnPlayPause = new Round("⏹", colorTheme.blackLight25, colorTheme.whiteDark15, 10);
        btnPlayPause.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (mediaPlayerComponent.mediaPlayer().status().isPlaying()) {
                    mediaPlayerComponent.mediaPlayer().controls().pause();
                    btnPlayPause.setText("▶");
                } else {
                    mediaPlayerComponent.mediaPlayer().controls().play();
                    btnPlayPause.setText("⏹");
                }
            }
            public void mouseEntered(MouseEvent e) {
                btnPlayPause.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnPlayPause.setBackground(colorTheme.blackLight15);
            }
            public void mouseExited(MouseEvent e) {
                btnPlayPause.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnPlayPause.setBackground(colorTheme.blackLight25);
            }
        });

        // Timeline video
        JSlider timeSlider = new JSlider();
        timeSlider.setMinimum(0);
        timeSlider.setMaximum(1000); // dùng để thể hiện progress từ 0-100%
        timeSlider.setValue(0);

        // Timeline volume
        JSlider volumeSlider = new JSlider(0, 100, 100); // từ 0 đến 100%
        mediaPlayerComponent.mediaPlayer().audio().setVolume(100);
        volumeSlider.addChangeListener(e -> {
            mediaPlayerComponent.mediaPlayer().audio().setVolume(volumeSlider.getValue());
        });

        // Cập nhật liên tục timeline
        javax.swing.Timer timer = new javax.swing.Timer(500, e -> {
            if (mediaPlayerComponent.mediaPlayer().status().isPlaying()) {
                float position = mediaPlayerComponent.mediaPlayer().status().position();
                timeSlider.setValue((int) (position * 1000));
            }
        });
        timer.start();

        // Tua video khi kéo timeline
        timeSlider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                float pos = timeSlider.getValue() / 1000f;
                mediaPlayerComponent.mediaPlayer().controls().setPosition(pos);
            }
        });

        JButton btnClose = new JButton("Close");
        btnClose.addActionListener(e -> {
            if (mediaPlayerComponent.mediaPlayer().status().isPlaying()) {
                mediaPlayerComponent.mediaPlayer().controls().stop();
            }

            timer.stop();

            // Hẹn 300ms trước khi release
            javax.swing.Timer releaseTimer = new javax.swing.Timer(500, evt -> {
                mediaPlayerComponent.release();

                layeredPane.remove(overlayPanel);
                layeredPane.revalidate();
                layeredPane.repaint();
            });
            releaseTimer.setRepeats(false);
            releaseTimer.start();
        });

        // Panel chứa nút control
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        controlPanel.setOpaque(false);
        controlPanel.add(btnPlayPause);
//        controlPanel.add(new JLabel("Time:").setForeground(colorTheme.whiteLight););
        controlPanel.add(timeSlider);
        JLabel label = new JLabel("🔊");
        label.setFont(new Font("<UNK>", Font.BOLD, 24));
        label.setForeground(colorTheme.whiteLight);
        controlPanel.add(label);
        controlPanel.add(volumeSlider);

        // Panel phía trên chứa nút download và close
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(btnDownload);
        topPanel.add(btnClose);

        overlayPanel.add(topPanel, BorderLayout.NORTH);
        overlayPanel.add(controlPanel, BorderLayout.SOUTH);

        // Thêm vào layeredPane
        layeredPane.add(overlayPanel, JLayeredPane.PALETTE_LAYER);
        overlayPanel.setVisible(true);

        // Bắt đầu phát video
        mediaPlayerComponent.mediaPlayer().media().play(videoFile.getAbsolutePath());

        layeredPane.revalidate();
        layeredPane.repaint();
    }
    public JPanel createMiniVideoPreviewPanel(byte[] fileData, String fileName) {
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setBackground(colorTheme.blackLight35);
        previewPanel.setPreferredSize(new Dimension(400, 300));

        JLabel thumbnail = new JLabel("▶", SwingConstants.CENTER);
        thumbnail.setFont(new Font("Arial", Font.BOLD, 20));
        thumbnail.setForeground(Color.WHITE);
        thumbnail.setCursor(new Cursor(Cursor.HAND_CURSOR));
        previewPanel.add(thumbnail, BorderLayout.CENTER);

        thumbnail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    File tempVideo = createTempVideoFile(fileData, fileName);

                    previewPanel.removeAll();

                    EmbeddedMediaPlayerComponent miniPlayer = new EmbeddedMediaPlayerComponent();
                    previewPanel.add(miniPlayer, BorderLayout.CENTER);
                    miniPlayer.mediaPlayer().media().play(tempVideo.getAbsolutePath());

                    // Control panel (Top-right)
                    JButton btnStop = new JButton("⏹");
                    btnStop.setMargin(new Insets(2, 5, 2, 5));
                    btnStop.addActionListener(ev -> {
                        if (miniPlayer.mediaPlayer().status().isPlaying()) {
                            miniPlayer.mediaPlayer().controls().pause();
                            btnStop.setText("▶");
                        } else {
                            miniPlayer.mediaPlayer().controls().play();
                            btnStop.setText("⏹");
                        }
                    });
                    JSlider timeSlider = new JSlider();
                    timeSlider.setMinimum(0);
                    timeSlider.setMaximum(1000); // dùng để thể hiện progress từ 0-100%
                    timeSlider.setValue(0);
                    timeSlider.setPreferredSize(new Dimension(120, 20));
                    timeSlider.setOpaque(false);

                    // Timeline volume
                    JSlider volumeSlider = new JSlider(0, 100, 100); // từ 0 đến 100%
                    miniPlayer.mediaPlayer().audio().setVolume(100);
                    volumeSlider.addChangeListener(ex -> {
                        miniPlayer.mediaPlayer().audio().setVolume(volumeSlider.getValue());
                    });
                    volumeSlider.setPreferredSize(new Dimension(120, 20));
                    volumeSlider.setOpaque(false);

                    // Cập nhật liên tục timeline
                    javax.swing.Timer timer = new javax.swing.Timer(500, ex -> {
                        if (miniPlayer.mediaPlayer().status().isPlaying()) {
                            float position = miniPlayer.mediaPlayer().status().position();
                            timeSlider.setValue((int) (position * 1000));
                        }
                    });
                    timer.start();

                    // Tua video khi kéo timeline
                    timeSlider.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseReleased(MouseEvent e) {
                            float pos = timeSlider.getValue() / 1000f;
                            miniPlayer.mediaPlayer().controls().setPosition(pos);
                        }
                    });

                    JButton btnExpand = new JButton("⛶");
                    btnExpand.setMargin(new Insets(2, 5, 2, 5));
                    btnExpand.addActionListener(ev -> {
                        if (miniPlayer.mediaPlayer().status().isPlaying()) {
                            miniPlayer.mediaPlayer().controls().pause();
                        }

                        showFullVideoPanel(tempVideo, fileName);
                    });

                    JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
                    topRightPanel.setOpaque(false);
                    topRightPanel.add(btnStop);
                    topRightPanel.add(timeSlider);
                    JLabel label = new JLabel("🔊");
                    label.setFont(new Font("<UNK>", Font.BOLD, 24));
                    label.setForeground(colorTheme.whiteLight);
                    topRightPanel.add(label);
                    topRightPanel.add(volumeSlider);
                    topRightPanel.add(btnExpand);

                    previewPanel.add(topRightPanel, BorderLayout.SOUTH);

                    previewPanel.revalidate();
                    previewPanel.repaint();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                thumbnail.setBackground(colorTheme.blackLight35);
            }
        });

        return previewPanel;
    }

    public void editMes(int ID_MES, String newMes) {
        if (newMes != null && !newMes.isEmpty()) {
            Session session = sessionFactory.openSession();

            try {
                session.beginTransaction();

                Mes mes = session.get(Mes.class, ID_MES);
                mes.setContent(newMes);

                session.getTransaction().commit();
            } catch (Exception e) {
                session.getTransaction().rollback();
                throw new RuntimeException(e);
            } finally {
                session.close();
            }
        }
        editMesInterface(ID_MES, newMes, "me");
    }
    public void editMesInterface(int ID_MES, String newMes, String type) {
        for (Component comp : panelChat.getComponents()) {
            if (comp instanceof JPanel panel) {
                for (Component c : panel.getComponents()) {
                    if (c instanceof JLabel lb) {
                        Object idMesObj = lb.getClientProperty("idMes");
                        if (idMesObj instanceof Integer idMes && idMes == ID_MES) {
                            if (type.equals("me")) {
                                lb.setText("<html><body style='width: 250px;'><div style='auto'><p style='width: fix-content; background: rgb(194, 249, 255); padding: 10px; text-align: right; border-radius: 10px;'>" + newMes +"</p></div></body></html>");
                            } else {
                                lb.setText("<html><body style='width: 250px; background: rgb(230, 230, 230); padding: 10px;'><div style='max-width: 250px'>" + newMes + "</div></body></html>");
                            }
                            lb.putClientProperty("rawText", newMes);

                            panelChat.revalidate();
                            panelChat.repaint();
                            return;
                        }
                    }
                }
            }
        }
    }
    public void deleteMes(int ID_MES) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            List<MesImoji> imojis = session.createQuery(
                            "FROM MesImoji WHERE mes.id = :idMes", MesImoji.class)
                    .setParameter("idMes", ID_MES)
                    .getResultList();

            for (MesImoji imoji : imojis) {
                session.remove(imoji);
            }

            Mes mes = session.get(Mes.class, ID_MES);  // lấy đối tượng từ DB
            if (mes != null) {
                session.remove(mes);  // xóa đúng đối tượng entity
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        deleteMesInterface(ID_MES);
    }
    public void deleteMesInterface(int ID_MES) {
        Component toRemove = null;

        for (Component comp : panelChat.getComponents()) {
            if (comp instanceof JPanel panel) {
                for (Component c : panel.getComponents()) {
                    if (c instanceof JComponent jc) {
                        Object idMesObj = jc.getClientProperty("idMes");
                        if (idMesObj instanceof Integer idMes && idMes == ID_MES) {
                            toRemove = panel;
                            break;
                        }
                    }
                }
            }
            if (toRemove != null) break;
        }

        if (toRemove != null) {
            panelChat.remove(toRemove);
            panelChat.revalidate();
            panelChat.repaint();
        }
    }

    private String checkImoji(String type) {
        return switch (type) {
            case "heart" -> "heart.png";
            case "like" -> "imojiLike.png";
            case "haha" -> "imojiHaha.png";
            case "sad" -> "imojiSad.png";
            case "wow" -> "imojiWow.png";
            case "cry" -> "imojiAngry.png";
            default -> "default.png";
        };
    }

    public JPanel createVoiceMessagePanel(byte[] audioData) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panel.setBackground(Color.WHITE);

        JButton btnPlay = new JButton("▶");
        btnPlay.setFocusPainted(false);
        btnPlay.setPreferredSize(new Dimension(50, 30));

        JLabel lblTime = new JLabel("0s");
        lblTime.setFont(new Font("Arial", Font.PLAIN, 14));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setValue(0);
        progressBar.setStringPainted(false);
        progressBar.setPreferredSize(new Dimension(200, 10));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setOpaque(false);
        controlPanel.add(btnPlay);
        controlPanel.add(lblTime);
        controlPanel.add(progressBar);

        panel.add(controlPanel, BorderLayout.CENTER);

        btnPlay.addActionListener(e -> {
            btnPlay.setEnabled(false);
            playAudio(audioData, lblTime, progressBar, () -> btnPlay.setEnabled(true));
        });

        return panel;
    }
    public void playAudio(byte[] audioData, JLabel lblTime, JProgressBar progressBar, Runnable onFinish) {
        new Thread(() -> {
            try {
                AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, true);
                long totalMillis = (long)((audioData.length * 1000.0) / format.getFrameRate() / (format.getSampleSizeInBits() / 8));
                int totalSeconds = (int)(totalMillis / 1000);

                progressBar.setMaximum(totalSeconds);

                SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, format));
                speakers.open(format);
                speakers.start();

                Timer timer = new Timer();
                final int[] sec = {0};
                timer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        if (sec[0] <= totalSeconds) {
                            int current = sec[0]++;
                            SwingUtilities.invokeLater(() -> {
                                lblTime.setText(current + "s");
                                progressBar.setValue(current);
                            });
                        } else {
                            timer.cancel();
                        }
                    }
                }, 0, 1000);

                ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = bais.read(buffer)) != -1) {
                    speakers.write(buffer, 0, bytesRead);
                }

                speakers.drain();
                speakers.close();

                // Hoàn tất
                SwingUtilities.invokeLater(() -> {
                    lblTime.setText(totalSeconds + "s");
                    progressBar.setValue(totalSeconds);
                    onFinish.run();
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private JLabel createFileLabel(String fileName, byte[] fileData) {
        JLabel lbMes = new JLabel("<html><body style='width: auto;'>"
                + "<div style='max-width: 400px'>"
                + "<p style='background: rgb(194, 249, 255); padding: 10px; "
                + "border-radius: 10px;'>" + fileName + "</p>"
                + "</div></body></html>");

        lbMes.setFont(new Font(fontName, Font.BOLD, 13));
        lbMes.setOpaque(false);
        lbMes.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lbMes.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lbMes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Chọn nơi lưu file");
                fileChooser.setSelectedFile(new File(fileName));
                int userSelection = fileChooser.showSaveDialog(null);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File saveFile = fileChooser.getSelectedFile();
                    try (FileOutputStream fos = new FileOutputStream(saveFile)) {
                        fos.write(fileData);
                        JOptionPane.showMessageDialog(null, "Đã lưu file: " + saveFile.getAbsolutePath());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi lưu file!");
                    }
                }
            }
        });

        return lbMes;
    }

    public int saveMes(String mess, boolean isMes) {
        Session session = sessionFactory.openSession();
        int messageId = -1;

        try {
            session.beginTransaction();

            if (isMes) {
                Mes mes = new Mes();
                mes.setId_sender_fkmes(ID_ME);
                mes.setId_receiver_fkmes(ID_RE);
                mes.setId_group_fkmes(null);
                mes.setType("text");
                mes.setContent(Function.encodeBase64(mess));
                mes.setSendat(new Timestamp(System.currentTimeMillis()));
                session.persist(mes);
                session.flush();
                messageId = mes.getId();
            } else {
                Mes mes = new Mes();
                mes.setId_sender_fkmes(ID_ME);
                mes.setId_receiver_fkmes(null);
                mes.setId_group_fkmes(ID_RE);
                mes.setType("text");
                mes.setContent(Function.encodeBase64(mess));
                mes.setSendat(new Timestamp(System.currentTimeMillis()));

                session.persist(mes);
                session.flush();
                messageId = mes.getId();
            }

            session.getTransaction().commit();
            return messageId;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return -1;
        } finally {
            session.close();
        }
    }
    public int saveFile(String fileName, byte[] bytes, boolean isMes) {
        Session session = sessionFactory.openSession();
        int messageId = -1;

        try {
            session.beginTransaction();

            if (isMes) {
                Mes mes = new Mes();
                mes.setId_sender_fkmes(ID_ME);
                mes.setId_receiver_fkmes(ID_RE);
                mes.setId_group_fkmes(null);
                mes.setType("file");
                mes.setContent(fileName);
                mes.setFile_data(bytes);
                mes.setSendat(new Timestamp(System.currentTimeMillis()));

                session.persist(mes);
                messageId = mes.getId();
            } else {
                Mes mes = new Mes();
                mes.setId_sender_fkmes(ID_ME);
                mes.setId_receiver_fkmes(null);
                mes.setId_group_fkmes(ID_RE);
                mes.setType("file");
                mes.setContent(fileName);
                mes.setFile_data(bytes);
                mes.setSendat(new Timestamp(System.currentTimeMillis()));

                session.persist(mes);
                messageId = mes.getId();
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return messageId;
    }

    public void showMes(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            mesList = session.createQuery("""
                FROM Mes 
                WHERE (id_sender_fkmes = :user1 AND id_receiver_fkmes = :user2)
                   OR (id_sender_fkmes = :user2 AND id_receiver_fkmes = :user1)
                ORDER BY sendat ASC
                """, Mes.class)
                    .setParameter("user1", ID_ME)
                    .setParameter("user2", ID_RE)
                    .getResultList();

            for (Mes m : mesList) {
                if (m.getType().equals("text")) {
                    if (m.getId_sender_fkmes() == ID_ME) {
                        showMesOut(Function.decodeBase64(m.getContent()), m.getId());
                    } else {
                        showMesIn(Function.decodeBase64(m.getContent()), m.getId());
                    }
                } else if (m.getType().equals("file")) {
                    if (m.getId_sender_fkmes() == ID_ME) {
                        showMesFileOut(m.getId(), m.getContent(), m.getFile_data());
                    } else {
                        showMesFileIn(m.getId(), m.getContent(), m.getFile_data());
                    }
                }

            }

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
            }
            e.printStackTrace();
        }
    }

    public void showGroupMes() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            // Query to fetch group messages with sender's name
            List<Object[]> mesList = session.createQuery("""
                SELECT m, u.name 
                FROM Mes m 
                JOIN User u ON m.id_sender_fkmes = u.id 
                WHERE m.id_group_fkmes = :groupId 
                ORDER BY m.sendat ASC
                """, Object[].class)
                    .setParameter("groupId", ID_RE)
                    .getResultList();

            for (Object[] result : mesList) {
                Mes m = (Mes) result[0];
                String senderName = (String) result[1];

                if (m.getType().equals("text")) {
                    if (m.getId_sender_fkmes() == ID_ME) {
                        showMesOut(senderName + ": " + Function.decodeBase64(m.getContent()), m.getId());
                    } else {
                        showMesIn(senderName + ": " + Function.decodeBase64(m.getContent()), m.getId());
                    }
                } else if (m.getType().equals("file")) {
                    if (m.getId_sender_fkmes() == ID_ME) {
                        showMesFileOut(m.getId(), senderName + ": " + m.getContent(), m.getFile_data());
                    } else {
                        showMesFileIn(m.getId(), senderName + ": " + m.getContent(), m.getFile_data());
                    }
                }
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void setNameInChat(String name, String status, int id, boolean isMes, byte[] RE_AVT, String mailRe) {
        panelChat.removeAll();
        this.ID_RE = id;
        lbName.setText(name);
        this.isMes = isMes;
        this.AVT_RE = RE_AVT;
        if (mailRe != null) {
            this.mailRe = mailRe;
        }

        if (status.equals("online")) {
            lbStatus.setText("<html><p style='width: 200px;'>" +language.getString("mainChat.online") +"</p></html>");
            lbStatus.setForeground(Color.green);
        } else if (status.equals("off")) {
            lbStatus.setText("<html><p style='width: 200px;'>" +language.getString("mainChat.off") +"</p></html>");
            if (theme.equals("light")) {
                lbStatus.setForeground(Color.BLACK);
            } else {
                lbStatus.setForeground(Color.WHITE);
            }
        } else if (status.equals("busy")) {
            lbStatus.setText("<html><p style='width: 200px;'>" +language.getString("mainChat.busi") +"</p></html>");
            lbStatus.setForeground(Color.RED);
        }

        if (isMes) {
            showMes(sessionFactory);
        } else {
            showGroupMes();
        }

        Image imgAVT = Function.resizeImage(new ImageIcon(this.AVT_RE).getImage(), 50, 50);
        btnAVT.removeAll();
        btnAVT.setIcon(new ImageIcon(imgAVT));
        btnAVT.repaint();

        repaint();
        revalidate();
    }

    public static byte[] convertFileToBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            return baos.toByteArray();
        }
    }

    public void changeBtnMoreStatus() {
        btnInfor.setBackground(colorTheme.transparent);
        if (theme.equals("light")) btnInfor.setIcon(new ImageIcon(imgInfor));
        else btnInfor.setIcon(new ImageIcon(imgInforDark));
        isInfor = false;
    }

    public void changeTheme(String theme) {
        this.theme = theme;
        if (theme.equals("light")) {
            btnCall.setIcon(new ImageIcon(imgCall));
            btnCallVideo.setIcon(new ImageIcon(imgCallVideo));
//            btnCallVideo.setText("<html><i class=\"fa-solid fa-video fa-2xl\" style=\"color: #FFD43B;\"></i></html>");
            btnSearch.setIcon(new ImageIcon(imgSearch));
            btnInfor.setIcon(new ImageIcon(imgInfor));
            btnmore.setIcon(new ImageIcon(imgmore));
            btnmic.setIcon(new ImageIcon(imgMic));
            btnfile.setIcon(new ImageIcon(imgFile));
            btnSend.setIcon(new ImageIcon(imgSend));
        } else {
            btnCall.setIcon(new ImageIcon(imgCallDark));
            btnCallVideo.setIcon(new ImageIcon(imgCallVideoDark));
            btnSearch.setIcon(new ImageIcon(imgSearchDark));
            btnInfor.setIcon(new ImageIcon(imgInforDark));
            btnmore.setIcon(new ImageIcon(imgmoreDark));
            btnmic.setIcon(new ImageIcon(imgMicDark));
            btnfile.setIcon(new ImageIcon(imgFileDark));
            btnSend.setIcon(new ImageIcon(imgSendDark));
        }
        revalidate();
        repaint();
    }

    public void changeLanguage(String langu) {
        this.lang = langu;
        language.changeLanguage(this.lang);
        revalidate();
        repaint();
    }

    private void resizeTextArea() {
        int lineCount = taChat.getLineCount();
        int newHeight = Math.min(lineCount * 40, 80);

        taChat.setPreferredSize(new Dimension(scChat.getWidth(), newHeight));
        taChat.revalidate();

        scChat.setPreferredSize(new Dimension(scChat.getWidth(), newHeight));
        scChat.setBounds(btnfile.getX() + 50, btnfile.getY(), getWidth() - (btnfile.getX() + 110), newHeight);
        scChat.revalidate();

        if (lineCount > 2) {
            scChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        } else {
            scChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        }

        revalidate();
        repaint();
    }

//    public void showMesFound(JPanel panelParent, String text) {
//        Session session = sessionFactory.openSession();
//
//        try {
//            session.beginTransaction();
//
//            String textIt = Function.encodeBase64(text.trim()).substring(0, Function.encodeBase64(text.trim()).length() - 2);
//            System.out.println("textIt: " + textIt);
//            System.out.println("text: " + text);
//            List<Mes> results = session.createQuery(
//                            "FROM Mes m WHERE m.content LIKE :kw", Mes.class)
//                    .setParameter("kw", "%" + textIt + "%")
//                    .list();
//
//            for (Mes m : results) {
//                if (m.getType().equals("text")) {
//                    if ((m.getId_sender_fkmes() == ID_ME && m.getId_receiver_fkmes() == ID_RE) ||
//                            (m.getId_sender_fkmes() == ID_RE && m.getId_receiver_fkmes() == ID_ME)
//                    ) {
//                        User user = session.get(User.class, m.getId_sender_fkmes());
//
//                        JPanel panelMes = new JPanel(new BorderLayout());
//                        panelMes.setPreferredSize(new Dimension(250, 60));
//                        panelMes.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//                        panelMes.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);
//
//                        // Create and configure avatar label
//                        JLabel lbAVT = new JLabel();
//                        Image imgAVT = Function.resizeImage(new ImageIcon(user.getAvatarImg()).getImage(), 30, 30);
//                        lbAVT.setIcon(new ImageIcon(imgAVT));
//                        lbAVT.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//
//                        // Create panel for text content
//                        JPanel panelText = new JPanel();
//                        panelText.setLayout(new BoxLayout(panelText, BoxLayout.Y_AXIS));
//                        panelText.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);
//
//                        // Add username label
//                        JLabel lbName = new JLabel(user.getName());
//                        lbName.setFont(new Font("Arial", Font.BOLD, 14));
//                        lbName.setForeground(theme.equals("light") ? Color.BLACK : Color.WHITE);
//
//                        // Add message content label
//                        JLabel lbContent = new JLabel(Function.decodeBase64(m.getContent()));
//                        lbContent.setFont(new Font("Arial", Font.PLAIN, 13));
//                        lbContent.setForeground(theme.equals("light") ? Color.GRAY : Color.LIGHT_GRAY);
//
//                        // Set message-related properties for searching
//                        lbContent.putClientProperty("rawText", m.getContent());
//                        lbContent.putClientProperty("idMes", m.getId());
//
//                        panelText.add(lbName);
//                        panelText.add(lbContent);
//
//                        // Add components to main panel
//                        panelMes.add(lbAVT, BorderLayout.WEST);
//                        panelMes.add(panelText, BorderLayout.CENTER);
//
//                        panelMes.addMouseListener(new MouseAdapter() {
//                            public void mouseClicked(MouseEvent e) {
//                                String targetText = Function.decodeBase64(m.getContent().trim());
//                                int targetId = m.getId();
//
//                                // Scroll to message position
//                                for (Component comp : panelChat.getComponents()) {
//                                    if (comp instanceof JPanel messagePanel) {
//                                        for (Component c : messagePanel.getComponents()) {
//                                            if (c instanceof JLabel lb) {
//                                                Object rawTextObj = lb.getClientProperty("rawText");
//                                                Object idMesObj = lb.getClientProperty("idMes");
//                                                if (rawTextObj instanceof String rawText && idMesObj instanceof Integer idMesVal) {
//                                                    if (rawText.trim().equals(targetText) && idMesVal == targetId) {
//                                                        Rectangle bounds = messagePanel.getBounds();
//                                                        SwingUtilities.invokeLater(() -> {
//                                                            scInChat.getViewport().setViewPosition(new Point(0, bounds.y));
//                                                        });
//                                                        Color originalColor = lb.getBackground();
//                                                        lb.setOpaque(true); // Đảm bảo màu nền hiển thị
//                                                        lb.setBackground(colorTheme.purpleLight);
//
//                                                        // Đổi lại sau 1.5 giây
//                                                        new javax.swing.Timer(1500, e2 -> {
//                                                            lb.setBackground(originalColor);
//                                                        }) {{
//                                                            setRepeats(false);
//                                                            start();
//                                                        }};
//                                                        return;
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//
//                            public void mouseEntered(MouseEvent e) {
//                                panelMes.setBackground(theme.equals("light") ? colorTheme.whiteDark15 : colorTheme.blackLight15);
//                                panelText.setBackground(theme.equals("light") ? colorTheme.whiteDark15 : colorTheme.blackLight15);
//                                panelMes.setCursor(new Cursor(Cursor.HAND_CURSOR));
//                            }
//
//                            public void mouseExited(MouseEvent e) {
//                                panelMes.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);
//                                panelText.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);
//                                panelMes.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//                            }
//                        });
//
//                        panelParent.add(panelMes);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//            throw new RuntimeException(e);
//        } finally {
//            session.close();
//        }
//    }

    public void showMesFound(JPanel searchResultPanel, String searchText) {
        try {
            searchText = searchText.toLowerCase().trim();

            // Search through existing messages in panel
            for (Component comp : panelChat.getComponents()) {
                if (comp instanceof JPanel messagePanel) {
                    for (Component c : messagePanel.getComponents()) {
                        if (c instanceof JLabel label) {
                            Object rawTextObj = label.getClientProperty("rawText");
                            Object idMesObj = label.getClientProperty("idMes");

                            if (rawTextObj instanceof String rawText && idMesObj instanceof Integer idMes) {
                                String messageText = rawText.toLowerCase();
                                if (messageText.contains(searchText)) {
//                                    // Create result panel
//                                    JPanel resultPanel = new JPanel(new BorderLayout());
//                                    resultPanel.setPreferredSize(new Dimension(250, 60));
//                                    resultPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//                                    resultPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);
//
//                                    Image imgAVT = Function.resizeImage(new ImageIcon(this.AVT_RE).getImage(), 40, 40);
//                                    JButton btnAVT = new JButton();
//                                    btnAVT.setSize(50, 50);
//                                    btnAVT.setBorder(null);
//                                    btnAVT.setIcon(new ImageIcon(imgAVT));
//
//                                    // Create text panel
//                                    JPanel textPanel = new JPanel();
//                                    textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
//                                    textPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);
//
//                                    // Add message content
//                                    JLabel contentLabel = new JLabel(rawText);
//                                    contentLabel.setFont(new Font("Arial", Font.PLAIN, 13));
//                                    contentLabel.setForeground(theme.equals("light") ? Color.GRAY : Color.LIGHT_GRAY);
//
//                                    textPanel.add(contentLabel);
//                                    resultPanel.add(textPanel, BorderLayout.CENTER);

                                    JPanel panelMes = new JPanel(new BorderLayout());
                                    panelMes.setPreferredSize(new Dimension(250, 60));
                                    panelMes.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                                    panelMes.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);

                                    // Create and configure avatar label
                                    JLabel lbAVT = new JLabel();
                                    Image imgAVT;
                                    lbAVT.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                                    // Create panel for text content
                                    JPanel panelText = new JPanel();
                                    panelText.setLayout(new BoxLayout(panelText, BoxLayout.Y_AXIS));
                                    panelText.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);

                                    // Add username label
                                    JLabel lbNamem = new JLabel();
                                    lbNamem.setFont(new Font("Arial", Font.BOLD, 14));
                                    lbNamem.setForeground(theme.equals("light") ? Color.BLACK : Color.WHITE);
                                    String sender = label.getClientProperty("namesend").toString();
                                    if (sender != null) {
                                        if (sender.equals("Me")) {
                                            lbNamem.setText("Bạn");
                                            imgAVT = Function.resizeImage(new ImageIcon(AVT_ME).getImage(), 40, 40);
                                            lbAVT.setIcon(new ImageIcon(imgAVT));
                                        } else {
                                            lbNamem.setText(lbName.getText());
                                            imgAVT = Function.resizeImage(new ImageIcon(AVT_RE).getImage(), 40, 40);
                                            lbAVT.setIcon(new ImageIcon(imgAVT));
                                        }
                                    }

                                    // Add message content label
                                    JLabel lbContent = new JLabel(rawText);
                                    lbContent.setFont(new Font("Arial", Font.PLAIN, 13));
                                    lbContent.setForeground(theme.equals("light") ? Color.GRAY : Color.LIGHT_GRAY);

                                    // Set message-related properties for searching
//                                    lbContent.putClientProperty("rawText", m.getContent());
//                                    lbContent.putClientProperty("idMes", m.getId());
//
                                    panelText.add(lbNamem);
                                    panelText.add(lbContent);

                                    // Add components to main panel
                                    panelMes.add(lbAVT, BorderLayout.WEST);
                                    panelMes.add(panelText, BorderLayout.CENTER);

                                    // Add click handler
                                    panelMes.addMouseListener(new MouseAdapter() {
                                        public void mouseClicked(MouseEvent e) {
                                            // Scroll to message
                                            Rectangle bounds = messagePanel.getBounds();
                                            SwingUtilities.invokeLater(() -> {
                                                scInChat.getViewport().setViewPosition(new Point(0, bounds.y));
                                            });

                                            // Highlight message
                                            Color originalColor = label.getBackground();
                                            label.setOpaque(true);
                                            label.setBackground(colorTheme.purpleLight);

                                            new javax.swing.Timer(1500, e2 -> {
                                                label.setBackground(originalColor);
                                            }) {{
                                                setRepeats(false);
                                                start();
                                            }};
                                        }

                                        public void mouseEntered(MouseEvent e) {
//                                            resultPanel.setBackground(theme.equals("light") ? colorTheme.whiteDark15 : colorTheme.blackLight15);
//                                            textPanel.setBackground(theme.equals("light") ? colorTheme.whiteDark15 : colorTheme.blackLight15);
                                            panelMes.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                        }

                                        public void mouseExited(MouseEvent e) {
//                                            resultPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);
//                                            textPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);
                                            panelMes.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                        }
                                    });

                                    searchResultPanel.add(panelMes);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//                if(m.getType().
//
//    equals("text"))
//
//    {
//        if ((m.getId_sender_fkmes() == ID_ME && m.getId_receiver_fkmes() == ID_RE) ||
//                            (m.getId_sender_fkmes() == ID_RE && m.getId_receiver_fkmes() == ID_ME)
//                    ) {
//                        User user = session.get(User.class, m.getId_sender_fkmes());
//
//                        JPanel panelMes = new JPanel(new BorderLayout());
//                        panelMes.setPreferredSize(new Dimension(250, 60));
//                        panelMes.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//                        panelMes.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);
//
//                        // Create and configure avatar label
//                        JLabel lbAVT = new JLabel();
//                        Image imgAVT = Function.resizeImage(new ImageIcon(user.getAvatarImg()).getImage(), 30, 30);
//                        lbAVT.setIcon(new ImageIcon(imgAVT));
//                        lbAVT.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//
//                        // Create panel for text content
//                        JPanel panelText = new JPanel();
//                        panelText.setLayout(new BoxLayout(panelText, BoxLayout.Y_AXIS));
//                        panelText.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);
//
//                        // Add username label
//                        JLabel lbName = new JLabel(user.getName());
//                        lbName.setFont(new Font("Arial", Font.BOLD, 14));
//                        lbName.setForeground(theme.equals("light") ? Color.BLACK : Color.WHITE);
//
//                        // Add message content label
//                        JLabel lbContent = new JLabel(Function.decodeBase64(m.getContent()));
//                        lbContent.setFont(new Font("Arial", Font.PLAIN, 13));
//                        lbContent.setForeground(theme.equals("light") ? Color.GRAY : Color.LIGHT_GRAY);
//
//                        // Set message-related properties for searching
//                        lbContent.putClientProperty("rawText", m.getContent());
//                        lbContent.putClientProperty("idMes", m.getId());
//
//                        panelText.add(lbName);
//                        panelText.add(lbContent);
//
//                        // Add components to main panel
//                        panelMes.add(lbAVT, BorderLayout.WEST);
//                        panelMes.add(panelText, BorderLayout.CENTER);
//
//                        panelMes.addMouseListener(new MouseAdapter() {
//                            public void mouseClicked(MouseEvent e) {
//                                String targetText = Function.decodeBase64(m.getContent().trim());
//                                int targetId = m.getId();
//
//                                // Scroll to message position
//                                for (Component comp : panelChat.getComponents()) {
//                                    if (comp instanceof JPanel messagePanel) {
//                                        for (Component c : messagePanel.getComponents()) {
//                                            if (c instanceof JLabel lb) {
//                                                Object rawTextObj = lb.getClientProperty("rawText");
//                                                Object idMesObj = lb.getClientProperty("idMes");
//                                                if (rawTextObj instanceof String rawText && idMesObj instanceof Integer idMesVal) {
//                                                    if (rawText.trim().equals(targetText) && idMesVal == targetId) {
//                                                        Rectangle bounds = messagePanel.getBounds();
//                                                        SwingUtilities.invokeLater(() -> {
//                                                            scInChat.getViewport().setViewPosition(new Point(0, bounds.y));
//                                                        });
//                                                        Color originalColor = lb.getBackground();
//                                                        lb.setOpaque(true); // Đảm bảo màu nền hiển thị
//                                                        lb.setBackground(colorTheme.purpleLight);
//
//                                                        // Đổi lại sau 1.5 giây
//                                                        new javax.swing.Timer(1500, e2 -> {
//                                                            lb.setBackground(originalColor);
//                                                        }) {{
//                                                            setRepeats(false);
//                                                            start();
//                                                        }};
//                                                        return;
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//
//                            public void mouseEntered(MouseEvent e) {
//                                panelMes.setBackground(theme.equals("light") ? colorTheme.whiteDark15 : colorTheme.blackLight15);
//                                panelText.setBackground(theme.equals("light") ? colorTheme.whiteDark15 : colorTheme.blackLight15);
//                                panelMes.setCursor(new Cursor(Cursor.HAND_CURSOR));
//                            }
//
//                            public void mouseExited(MouseEvent e) {
//                                panelMes.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);
//                                panelText.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight5);
//                                panelMes.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//                            }
//                        });
//
//                        panelParent.add(panelMes);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            session.getTransaction().rollback();
//            throw new RuntimeException(e);
//        } finally {
//            session.close();
//        }
//    }

    public MesImoji getImoji(int idMes) {
        Session session = sessionFactory.openSession();
        MesImoji mesImoji = null;

        try {
            session.beginTransaction();

            mesImoji = session.createQuery(
                            "FROM MesImoji WHERE mes.id = :id AND imojiType IS NOT NULL", MesImoji.class)
                    .setParameter("id", idMes)
                    .uniqueResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return mesImoji;
    }
    public void updateMesImoji(int idMes, String type) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            // Tìm MesImoji hiện tại
            MesImoji mesImoji = session.createQuery(
                            "FROM MesImoji WHERE mes.id = :id", MesImoji.class)
                    .setParameter("id", idMes)
                    .uniqueResult();

            if (mesImoji != null) {
                // Cập nhật loại cảm xúc
                mesImoji.setImojiType(type);
                session.merge(mesImoji);
            } else {
                Mes mes = session.get(Mes.class, idMes);
                if (mes != null) {
                    mesImoji = new MesImoji();
                    mesImoji.setMes(mes);
                    mesImoji.setImojiType(type);
                    session.persist(mesImoji);
                } else {
                    System.out.println("Không tìm thấy tin nhắn có id = " + idMes);
                }
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        updateMesImojiInterface(ID_ME, type);
    }
    public void updateMesImojiInterface(int ID_MES, String newType) {
        for (Component comp : panelChat.getComponents()) {
            if (comp instanceof JPanel panel) {
                boolean found = false;

                for (Component c : panel.getComponents()) {
                    if (c instanceof JComponent jc) {
                        Object idMesObj = jc.getClientProperty("idMes");

                        if (idMesObj instanceof Integer idMes && idMes == ID_MES) {
                            // ✅ Tìm thấy message cần cập nhật emoji

                            // Tìm btnIcon trong panel
                            for (Component sub : panel.getComponents()) {
                                if (sub instanceof JButton btn) {
                                    Object check = panel.getClientProperty("btnicon"); // hoặc check trực tiếp trong nút
                                    if (check != null || btn.getPreferredSize().equals(new Dimension(30, 30))) {
                                        // Gán icon mới
                                        ImageIcon newIcon = new ImageIcon(Function.resizeImage(
                                                new ImageIcon(PATHICON.getPath(checkImoji(newType))).getImage(), 30, 30));
                                        btn.setIcon(newIcon);
                                        found = true;
                                        break;
                                    }
                                }
                            }

                            if (!found) {
                                // Hoặc lấy từ getClientProperty nếu có gán
                                Object iconObj = jc.getClientProperty("btnicon");
                                if (iconObj instanceof JButton btnIcon) {
                                    ImageIcon newIcon = new ImageIcon(Function.resizeImage(
                                            new ImageIcon(PATHICON.getPath(checkImoji(newType))).getImage(), 30, 30));
                                    btnIcon.setIcon(newIcon);
                                }
                            }

                            panelChat.revalidate();
                            panelChat.repaint();
                            return;
                        }
                    }
                }
            }
        }
    }



    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (theme.equals("light")) {
            g2d.setColor(colorTheme.whiteLight);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            g2d.setColor(colorTheme.whiteDark15);
            g2d.fillRect(0, 60, getWidth(), getHeight() - 120);
        } else if (theme.equals("dark")) {
            g2d.setColor(colorTheme.blackLight5);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            g2d.setColor(colorTheme.lightGrayDark90);
            g2d.fillRect(0, 60, getWidth(), getHeight() - 120);
        }

        g2d.setColor(colorTheme.blackLight25);
        g2d.drawLine(0, 60, getWidth(), 60);
        g2d.drawLine(0, getHeight() - 60, getWidth(), getHeight() - 60);
        g2d.fillOval(30, 5, 50, 50);

        btnCall.setBounds(getWidth() - 200, 10, 40, 40);
        btnCallVideo.setBounds(btnCall.getX() + 50, btnCall.getY(), 40, 40);
        btnSearch.setBounds(btnCallVideo.getX() + 50, btnCallVideo.getY(), 40, 40);
        btnInfor.setBounds(btnSearch.getX() + 50, btnSearch.getY(), 40, 40);
        lbName.setBounds(100, 10, 300, 25);
        lbStatus.setBounds(100, 35, 300, 20);

        btnmore.setBounds(10, getHeight() - 50, 40, 40);
        btnmic.setBounds(btnmore.getX() + 50, btnmore.getY(), 40, 40);
        btnfile.setBounds(btnmic.getX() + 50, btnmic.getY(), 40, 40);
        btnEmoji.setBounds(btnfile.getX() + 50, btnfile.getY(), 40, 40);
        scChat.setBounds(btnEmoji.getX() + 50, btnEmoji.getY(), getWidth() - (btnEmoji.getX() + 110), Math.max(taChat.getPreferredSize().height, 40));
        btnSend.setBounds(scChat.getX() + scChat.getWidth() + 10, scChat.getY(), 40, 40);

        scInChat.setBounds(0, 60, getWidth(), getHeight() - 120);
    }
}

class MessageBubble extends JPanel {
    private String message;
    private final Color backgroundColor;
    private int radius;
    private Insets padding = new Insets(10, 15, 10, 15); // padding trong khung

    FontMetrics fontMetrics;

    public MessageBubble(String message, Color backgroundColor, int radius, String rl) {
        this.message = message;
        this.backgroundColor = backgroundColor;
        this.radius = radius;

        setOpaque(false);
        setLayout(new FlowLayout(rl.equals("right") ? FlowLayout.RIGHT : FlowLayout.LEFT, 10, 5));

        String align = rl.equals("right") ? "right" : "left";

        JLabel label = new JLabel(
                "<html><div style='max-width: 400px; text-align:" + align + ";'>" + message + "</div></html>");
        label.setFont(new Font("Meiryo", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        label.setOpaque(false);
        label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // padding chữ

        JPanel bubble = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(backgroundColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        bubble.setOpaque(false);
        bubble.setLayout(new BorderLayout());
        bubble.add(label, BorderLayout.CENTER);
        bubble.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // không cần viền ngoài

        add(bubble);
    }
}

class VoiceRecordDialog extends JDialog {
    private static final Log log = LogFactory.getLog(VoiceRecordDialog.class);
    private boolean isRecording = false;
    private ByteArrayOutputStream out;
    private TargetDataLine microphone;
    private JButton btnRecord;
    private JButton btnCancel;
    JButton btnSend;
    private JLabel lblTime;
    private Timer timer;
    private int seconds;

    private byte[] recordedData = null;

    public VoiceRecordDialog(JFrame parent) {
        super(parent, "Ghi âm", true);
        setLayout(null);
        setSize(300, 180);
        setLocationRelativeTo(parent);

        // Khởi tạo nút và label
        btnRecord = new JButton("🎙️ Bắt đầu ghi");
        btnCancel = new JButton("Huỷ");
        btnSend = new JButton("Gửi");
        lblTime = new JLabel("0s", SwingConstants.CENTER);
        lblTime.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        lblTime.setBounds(0, 0, 300, 40);
        btnRecord.setBounds(lblTime.getX() + 30, lblTime.getY() + lblTime.getHeight() + 10, lblTime.getWidth() - 60, 40);

        btnSend.setVisible(false);

        btnRecord.addActionListener(e -> {
            if (!isRecording && recordedData == null) {
                startRecording();
            } else if (isRecording) {
                stopRecording();
                btnSend.setVisible(true);
            } else {
                playAudio(recordedData);
            }
        });
        btnCancel.addActionListener(e -> {
            if (isRecording || recordedData != null) {
                if (isRecording) {
                    stopRecording();
                }
                recordedData = null;
                lblTime.setText("0s");
                btnSend.setVisible(false);
                btnRecord.setText("🎙️ Bắt đầu ghi");
            }
        });

        // Panel chứa các nút dưới cùng
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        bottomPanel.setBounds(btnRecord.getX(), btnRecord.getY() + btnRecord.getHeight() + 10, btnRecord.getWidth(), btnRecord.getHeight());
        bottomPanel.add(btnSend);
        bottomPanel.add(btnCancel);

        add(bottomPanel);
        add(btnRecord);
        add(lblTime);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void startRecording() {
        isRecording = true;
        btnRecord.setText("⏹️ Dừng ghi");
        seconds = 0;
        lblTime.setText("0s");

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                seconds++;
                SwingUtilities.invokeLater(() -> lblTime.setText(seconds + "s"));
            }
        }, 1000, 1000);

        try {
            AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, true);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            microphone.start();

            out = new ByteArrayOutputStream();
            new Thread(() -> {
                byte[] buffer = new byte[4096];
                while (isRecording) {
                    int bytesRead = microphone.read(buffer, 0, buffer.length);
                    out.write(buffer, 0, bytesRead);
                }
            }).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void stopRecording() {
        isRecording = false;
        btnRecord.setText("▶️ Nghe lại");

        timer.cancel();
        microphone.stop();
        microphone.close();

        recordedData = out.toByteArray();
    }

    public byte[] getRecordedData() {
        return recordedData;
    }

    private void playAudio(byte[] audioData) {
        new Thread(() -> {
            try {
                AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, true);
                DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);
                speakers.open(format);
                speakers.start();

                Timer playTimer = new Timer();
                final int[] playSec = {0};
                playTimer.scheduleAtFixedRate(new TimerTask() {
                    public void run() {
                        if (playSec[0] <= seconds) {
                            SwingUtilities.invokeLater(() -> lblTime.setText(playSec[0] + "s"));
                            playSec[0]++;
                        } else {
                            playTimer.cancel();
                            SwingUtilities.invokeLater(() -> lblTime.setText(seconds + "s"));  // khôi phục đúng số giây ghi
                        }
                    }
                }, 0, 1000);

                ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = bais.read(buffer)) != -1) {
                    speakers.write(buffer, 0, bytesRead);
                }

                speakers.drain();
                speakers.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public byte[] openDialog(JFrame parent) {
        VoiceRecordDialog dialog = new VoiceRecordDialog(parent);
        dialog.setVisible(true);
        return dialog.getRecordedData();
    }
}

class WaveformPanel extends JPanel {
    private byte[] audioBytes;

    public WaveformPanel(byte[] audioBytes) {
        this.audioBytes = audioBytes;
        setPreferredSize(new Dimension(300, 60));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (audioBytes == null || audioBytes.length == 0) return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(50, 150, 250));

        int width = getWidth();
        int height = getHeight();
        int middle = height / 2;
        int samplesToDraw = width;
        int sampleStep = audioBytes.length / samplesToDraw;

        for (int i = 0; i < samplesToDraw; i++) {
            int index = i * sampleStep;
            if (index + 1 >= audioBytes.length) break;

            // Lấy biên độ mẫu (giá trị từ -128 đến 127)
            int sample = audioBytes[index];

            int barHeight = (sample * height) / 256;
            g2.drawLine(i, middle - barHeight / 2, i, middle + barHeight / 2);
        }
    }
}