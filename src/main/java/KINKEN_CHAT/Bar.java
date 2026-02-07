package KINKEN_CHAT;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Hibernate.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Bar extends JPanel {
    JButton btnChat;
    JButton btnContacts;
    JButton btnReel;
    JButton btnSetting;
    JButton btnInfor;

    JMenuItem infor;

    private String theme;
    private String language;
    private String choiceNow = "chat";

    static ColorTheme colorTheme = new ColorTheme();
    private ActionChange themeChange;
    private Language languageChange;

    DialogAccount dialogAccount;
    DialogChangeAccount dialogChangeAccount;
    SessionFactory sessionFactory;
    private int ID_NOW;

    Image chatImg = new ImageIcon(PATHICON.getPath("chat.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    Image contactsImg = new ImageIcon(PATHICON.getPath("contacts.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    Image reelImg = new ImageIcon(PATHICON.getPath("reel.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
    Image settingImg = new ImageIcon(PATHICON.getPath("setting.png")).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);

    private String name;
    private String nickname;
    private Date birthday;
    private String mail;
    private byte[] avatarImg;
    private Date createAt;
    private byte[] coverImg;
    private String sex;
    private String bio;
    private String sizeAtv;
    private String sizeCover;
    private JFrame frameCur;

    public Bar(JFrame frameCur, String theme, ActionChange themeChange, String lang, SessionFactory sessionFactory, int ID_NOW) {
        this.theme = theme;
        this.language = lang;
        this.themeChange = themeChange;
        setLayout(null);
        this.sessionFactory = sessionFactory;
        this.ID_NOW = ID_NOW;
        this.frameCur = frameCur;

        setValue(ID_NOW);

        languageChange = new Language(this.language);
        languageChange.loadLanguage();
        changLanguage(this.language);

        btnInfor = new Round("", colorTheme.whiteDark15, Color.BLACK, 60);
        btnChat = new Round("", colorTheme.transparent, Color.black, 10);
        btnContacts = new Round("", colorTheme.transparent, Color.black, 10);
        btnReel = new Round("", colorTheme.transparent, Color.black, 10);
        btnSetting = new Round("", colorTheme.transparent, Color.black, 10);

        btnChat.setIcon(new ImageIcon(chatImg));
        btnContacts.setIcon(new ImageIcon(contactsImg));
        btnReel.setIcon(new ImageIcon(reelImg));
        btnSetting.setIcon(new ImageIcon(settingImg));

        btnInfor.setBounds(5, 10, 60, 60);
        add(btnInfor);
        Image imgAVT = new ImageIcon(avatarImg).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        btnInfor.setIcon(new ImageIcon(imgAVT));

        btnChat.setBounds(10, 100, 50, 50);
        add(btnChat);
        btnChat.setBackground(colorTheme.whiteDark25);
        btnContacts.setBounds(10, btnChat.getY() + btnChat.getHeight() + 20, btnChat.getWidth(), btnChat.getHeight());
        add(btnContacts);
        btnReel.setBounds(10, btnContacts.getY() + btnContacts.getHeight() + 20, btnContacts.getWidth(), btnContacts.getHeight());
        add(btnReel);
        updateSettingLocatation();
        add(btnSetting);

        changeChoice("chat");

        btnInfor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dialogAccount = new DialogAccount(null, "Thông tin tài khoản", true, theme, language,
                        name, nickname, birthday, mail, avatarImg, createAt, coverImg, sex, bio, sizeAtv, sizeCover, sessionFactory, ID_NOW);

                dialogAccount.btnChange.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        dialogAccount.dispose();
                        dialogChangeAccount = new DialogChangeAccount(null, "Thay đổi thông tin", true, theme, language,
                                name, nickname, birthday, mail, avatarImg, createAt, coverImg, sex, bio, sizeAtv, sizeCover, sessionFactory, ID_NOW);

                        dialogChangeAccount.btnChange.addMouseListener(new MouseAdapter() {
                            public void mouseClicked(MouseEvent e) {
                                setValue(ID_NOW); // Load lại thông tin mới sau khi thay đổi
                            }
                        });

                        dialogChangeAccount.setVisible(true); // phải gọi sau khi gán sự kiện
                    }
                });

                dialogAccount.setVisible(true); // hiển thị dialog sau khi gán sự kiện
            }
        });

        btnChat.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                changeChoice("chat");
            }
            public void mouseEntered(MouseEvent e) {
                btnChat.setBackground(colorTheme.whiteDark25);
                btnChat.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                if (!choiceNow.equals("chat")) {
                    btnChat.setBackground(colorTheme.transparent);
                }
                btnChat.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnContacts.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                changeChoice("contacts");
            }
            public void mouseEntered(MouseEvent e) {
                btnContacts.setBackground(colorTheme.whiteDark25);
                btnContacts.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                if (!choiceNow.equals("contacts")) {
                    btnContacts.setBackground(colorTheme.transparent);
                }
                btnContacts.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnReel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                changeChoice("reel");
            }
            public void mouseEntered(MouseEvent e) {
                btnReel.setBackground(colorTheme.whiteDark25);
                btnReel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                if (!choiceNow.equals("reel")) {
                    btnReel.setBackground(colorTheme.transparent);
                }
                btnReel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnSetting.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                btnSetting.setBackground(colorTheme.whiteDark25);
                btnSetting.setSelected(true);
                settingShowPopup(themeChange);
            }
            public void mouseEntered(MouseEvent e) {
                btnSetting.setBackground(colorTheme.whiteDark25);
                btnSetting.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                if (!btnSetting.isSelected()) {
                    btnSetting.setBackground(colorTheme.transparent);
                }
                btnSetting.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });



        infor = new JMenuItem(languageChange.getString("bar.popup.infor"));
        infor.addActionListener(e -> new DialogAccount(null, "Thông tin tài khoản", true, theme, language,
                name, nickname, birthday, mail, avatarImg, createAt, coverImg, sex, bio, sizeAtv, sizeCover, sessionFactory, ID_NOW));

        changeTheme(theme);
    }

    public void setValue(int id) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            User user = session.get(User.class, id);

            if (user != null) {
                this.name = user.getName();
                this.nickname = user.getNickname();
                this.birthday = user.getBirthday();
                this.mail = user.getMail();
                this.avatarImg = user.getAvatarImg();
                this.createAt = user.getCreateAt();
                this.coverImg = user.getCoverImg();
                this.sex = user.getSex();
                this.bio = user.getBio();
                this.sizeAtv = user.getSizeAtv();
                this.sizeCover = user.getSizeCover();
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cropImageToCircle(byte[] imgBytes, String kc) {
        String[] p = kc.trim().split("\\s+");
        int crX = Integer.parseInt(p[0]);
        int crY = Integer.parseInt(p[1]);
        int radius = Integer.parseInt(p[2]);

        try {
            // 1. Đọc byte[] thành BufferedImage
            InputStream in = new ByteArrayInputStream(imgBytes);
            BufferedImage originalImage = ImageIO.read(in);

            // 2. Kiểm tra hợp lệ
            if (originalImage == null) {
                throw new IllegalArgumentException("Ảnh không hợp lệ hoặc không đọc được!");
            }
//            int diameter = radius * 2; // Đường kính của hình tròn
//
//            // 3. Tạo ảnh mới với nền trong suốt (Transparent)
//            BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g = circularImage.createGraphics();
//
//            // 4. Tạo hình tròn mask để cắt ảnh
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g.fill(new Ellipse2D.Double(0, 0, diameter, diameter));
//
//            // 5. Cắt ảnh gốc theo hình tròn
//            g.setComposite(AlphaComposite.SrcIn);
//            g.drawImage(originalImage, -crX + radius, -crY + radius, null);
//            g.dispose();

            // 6. Trả về ImageIcon


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInfor(String infor) {}

    private void changeChoice(String choice) {
        if (choice.equals("chat")) {
            btnChat.setBackground(colorTheme.whiteDark25);
            btnContacts.setBackground(colorTheme.transparent);
            btnReel.setBackground(colorTheme.transparent);
        } else if (choice.equals("contacts")) {
            btnContacts.setBackground(colorTheme.whiteDark25);
            btnReel.setBackground(colorTheme.transparent);
            btnChat.setBackground(colorTheme.transparent);
        } else if (choice.equals("reel")) {
            btnReel.setBackground(colorTheme.whiteDark25);
            btnSetting.setBackground(colorTheme.transparent);
            btnChat.setBackground(colorTheme.transparent);
        }
        this.choiceNow = choice;
    }

    public void updateSettingLocatation() {
        SwingUtilities.invokeLater(() -> {
            int height = getHeight();
            if (height > 0) {
                btnSetting.setBounds(btnChat.getX(), height - 100, btnChat.getWidth(), btnChat.getHeight());
                repaint();
                revalidate();
            }
        });
    }

    public void settingShowPopup(ActionChange actionChange) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem setting = new JMenuItem(languageChange.getString("bar.popup.setting"));
        JMenuItem help = new JMenuItem(languageChange.getString("bar.popup.help"));
        JMenuItem logout = new JMenuItem(languageChange.getString("bar.popup.logout"));
        JMenuItem exit = new JMenuItem(languageChange.getString("bar.popup.exit"));

        logout.setForeground(colorTheme.darkRed);
        exit.setForeground(colorTheme.darkRed);

        setting.addActionListener(e -> new DialogSetting(null, "Cài đặt", true, this.theme, themeChange, this.language));
        logout.addActionListener(e -> {
            this.frameCur.dispose();
            try {
                actionChange.logout();
                new Main();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        exit.addActionListener(e -> System.exit(0));

        popup.add(infor);
        popup.add(setting);
        popup.add(help);
        popup.add(logout);
        popup.add(exit);
        popup.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}
            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                btnSetting.setBackground(colorTheme.transparent);
                btnSetting.setSelected(false);
            }
            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });

        popup.show(btnSetting, 0, btnSetting.getX() - 120);
    }

    public void changeTheme(String theme) {
        this.theme = theme;
        if (theme.equals("light")) {
            setBackground(colorTheme.purpleLight);
        } else {
            setBackground(colorTheme.purpleDark);
        }
        repaint();
        revalidate();
    }

    public void changLanguage(String language) {
        this.language = language;
        languageChange.changeLanguage(this.language);
        repaint();
        revalidate();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
//        g2d.fillOval(5, 10, 60, 60);
    }
}

class DialogSetting extends JDialog {
    ColorTheme colorTheme = new ColorTheme();

    JPanel themePanel = new JPanel();
    JPanel languagePanel = new JPanel();

    JButton btnTheme;
    JButton btnLanguage;

    private String theme;
    private String language;
    private ActionChange themeChange;
    private Language languageChange;
    private String fontName = "Meiryo";

    public DialogSetting(JFrame frameParent, String title, boolean modal, String themes, ActionChange themeChange, String lang) {
        super(frameParent, title, modal);
        this.theme = themes;
        this.language = lang;
        this.themeChange = themeChange;

        languageChange = new Language(this.language);
        languageChange.loadLanguage();

        setSize(800, 500);
        setLayout(null);
        getContentPane().setBackground(colorTheme.whiteLight);

        btnTheme = new Round("", colorTheme.transparent, Color.black, 0);
        btnLanguage = new Round("", colorTheme.transparent, Color.black, 0);
        btnTheme.setText(languageChange.getString("bar.Dialog.btnTheme"));
        btnLanguage.setText(languageChange.getString("bar.Dialog.btnLanguage"));

        btnTheme.setBounds(0, 0 , 200, 30);
        add(btnTheme);
        btnLanguage.setBounds(0, btnTheme.getY() + btnTheme.getHeight(), 200, 30);
        add(btnLanguage);

        btnTheme.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                themePanel.setBounds(200, 0, 600, 500);
                themePanel.setVisible(true);
                languagePanel.setVisible(false);
                showThemePanel(theme);
                add(themePanel);
                repaint();
                revalidate();
            }
        });
        btnLanguage.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                languagePanel.setBounds(200, 0, 600, 500);
                languagePanel.setVisible(true);
                themePanel.setVisible(false);
                showLanguagePanel();
                add(languagePanel);
                repaint();
                revalidate();
            }
        });

        changTheme(theme);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showThemePanel(String themeP) {
        JLabel themeLabel = new JLabel(languageChange.getString("bar.themePanel.themeLabel"));
        JRadioButton light = new JRadioButton(languageChange.getString("bar.themePanel.light"));
        JRadioButton dark = new JRadioButton(languageChange.getString("bar.themePanel.dark"));
        JRadioButton system = new JRadioButton(languageChange.getString("bar.themePanel.system"));
        ButtonGroup group = new ButtonGroup();
        themePanel.setLayout(null);

        group.add(light);
        group.add(dark);
        light.setOpaque(false);
        dark.setOpaque(false);
        themeLabel.setOpaque(false);

        themeLabel.setBounds(100, 20, 200, 30);
        themePanel.add(themeLabel);
        light.setBounds(themeLabel.getX(), themeLabel.getY() + 30, 100, 30);
        themePanel.add(light);
        dark.setBounds(light.getX() + 120, 50, light.getWidth(), light.getHeight());
        themePanel.add(dark);

        if (theme.equals("light")) {
            light.setSelected(true);
            setBackground(colorTheme.whiteDark15);
            themeLabel.setForeground(Color.BLACK);
            light.setForeground(Color.BLACK);
            dark.setForeground(Color.BLACK);
        } else {
            dark.setSelected(true);
            setBackground(colorTheme.blackLight25);
            themeLabel.setForeground(Color.WHITE);
            light.setForeground(Color.WHITE);
            dark.setForeground(Color.WHITE);
        }

        light.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themeChange.changeTheme("light");
                theme = "light";
                changTheme(theme);
                themeLabel.setForeground(Color.BLACK);
                light.setForeground(Color.BLACK);
                dark.setForeground(Color.BLACK);
            }
        });
        dark.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themeChange.changeTheme("dark");
                theme = "dark";
                changTheme(theme);
                themeLabel.setForeground(Color.WHITE);
                light.setForeground(Color.WHITE);
                dark.setForeground(Color.WHITE);
            }
        });
        repaint();
        revalidate();
    }

    private void showLanguagePanel() {
        JLabel languageLabel = new JLabel(languageChange.getString("bar.languagePanel.languageLabel"));
        JRadioButton langVi = new JRadioButton(languageChange.getString("bar.languagePanel.langVi"));
        JRadioButton langEn = new JRadioButton(languageChange.getString("bar.languagePanel.langEn"));
        JRadioButton langJp = new JRadioButton(languageChange.getString("bar.languagePanel.langJP"));
        ButtonGroup group = new ButtonGroup();
        languagePanel.setLayout(null);

        group.add(langVi);
        group.add(langEn);
        group.add(langJp);
        langVi.setOpaque(false);
        langEn.setOpaque(false);
        langJp.setOpaque(false);
        languageLabel.setOpaque(false);

        languageLabel.setBounds(100, 20, 200, 30);
        languagePanel.add(languageLabel);
        langVi.setBounds(languageLabel.getX(), languageLabel.getY() + 30, 150, 30);
        languagePanel.add(langVi);
        langEn.setBounds(langVi.getX() + 150, 50, langVi.getWidth(), langVi.getHeight());
        languagePanel.add(langEn);
        langJp.setBounds(langEn.getX() + 150, 50, langEn.getWidth(), langEn.getHeight());
        languagePanel.add(langJp);

        if (theme.equals("light")) {
            languageLabel.setForeground(Color.BLACK);
            langVi.setForeground(Color.BLACK);
            langEn.setForeground(Color.BLACK);
            langJp.setForeground(Color.BLACK);
        } else {
            languageLabel.setForeground(Color.WHITE);
            langVi.setForeground(Color.WHITE);
            langEn.setForeground(Color.WHITE);
            langJp.setForeground(Color.WHITE);
        }

        if (language.equals("vi")) {
            langVi.setSelected(true);
        } else if (language.equals("en")) {
            langEn.setSelected(true);
        } else if (language.equals("jp")) {
            langJp.setSelected(true);
        }

        langVi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themeChange.changeLanguage("vi");
            }
        });

        langEn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themeChange.changeLanguage("en");
            }
        });

        langJp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themeChange.changeLanguage("jp");
            }
        });

        repaint();
        revalidate();
    }


    private void changTheme(String theme) {
        this.theme = theme;
        if (theme.equals("light")) {
            getContentPane().setBackground(colorTheme.whiteLight);
            btnTheme.setForeground(Color.BLACK);
            themePanel.setBackground(colorTheme.whiteDark15);
            btnLanguage.setForeground(Color.BLACK);
            languagePanel.setBackground(colorTheme.whiteDark15);
        } else {
            getContentPane().setBackground(colorTheme.blackLight15);
            btnTheme.setForeground(Color.WHITE);
            themePanel.setBackground(colorTheme.blackLight25);
            btnLanguage.setForeground(Color.WHITE);
            languagePanel.setBackground(colorTheme.blackLight25);
        }
        repaint();
    }
}

class DialogAccount extends JDialog {
    ColorTheme colorTheme = new ColorTheme();

    JPanel acc = new JPanel();

    JLabel lbCover = new JLabel();
    JLabel lbNoti = new JLabel();
    JButton btnAvt;
    JLabel lbName = new JLabel();
    JLabel lbEmail = new JLabel();
    JLabel lbBirth = new JLabel();
    JLabel lbNick = new JLabel();
    JLabel lbSex = new JLabel();
    JLabel lbBio = new JLabel();
    JButton btnChange;

    private String theme;
    private final String language;
    private final Language languageChange;
    private String fontName = "Meiryo";

    public DialogAccount(JFrame frameParent, String title, boolean modal, String themes, String lang,
                         String name, String nickname, Date birthday, String mail, byte[] avatarImg, Date createAt, byte[] coverImg, String sex, String bio, String sizeAtv, String sizeCover, SessionFactory sessionFactory, int ID_NOW) {
        super(frameParent, title, modal);
        this.theme = themes;
        this.language = lang;

        languageChange = new Language(this.language);
        languageChange.loadLanguage();

        setSize(500, 600);
        setLayout(null);
        getContentPane().setBackground(colorTheme.whiteLight);

        acc.setLayout(null);

        btnAvt = new Round("", colorTheme.blackLight25, colorTheme.transparent, 200);
        btnChange = new Round(languageChange.getString("bar.DialogAcc.btnChange"), colorTheme.purpleLight, Color.white, 30);

        lbCover.setBounds(0, 0, 500, 200);
        lbCover.setBackground(colorTheme.blackLight50);
        btnAvt.setBounds(40, (lbCover.getHeight() - 80), 160, 160);
        lbName.setBounds(btnAvt.getX() + btnAvt.getWidth() + 20, 160, 300, btnAvt.getHeight());
        lbNoti.setBounds(50, btnAvt.getY() + btnAvt.getHeight() + 20, 460, 30);
        lbBio.setBounds(60, lbNoti.getY() + lbNoti.getHeight() + 20, 460, 60);
        lbEmail.setBounds(lbBio.getX(), lbBio.getY() + lbBio.getHeight(), lbBio.getWidth(), 30);
        lbSex.setBounds(lbEmail.getX(), lbEmail.getY() + lbEmail.getHeight(), lbEmail.getWidth(), 30);
        lbBirth.setBounds(lbSex.getX(), lbSex.getY() + lbSex.getHeight(), lbSex.getWidth(), 30);
        btnChange.setBounds(100, lbBirth.getY() + lbBirth.getHeight() +10, getWidth() - 200, 40);
//        btnChange.setBackground(colorTheme.whiteLight);

        lbName.setFont(new Font(fontName, Font.BOLD, 22));
        lbBio.setFont(new Font(fontName, Font.BOLD, 16));

        lbName.setText(name + " (" + nickname + ")");
        Image imgcover = new ImageIcon(coverImg).getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH);
        lbCover.setIcon(new ImageIcon(imgcover));
        Image imgavt = new ImageIcon(avatarImg).getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
        btnAvt.setIcon(new ImageIcon(imgavt));
        lbEmail.setText("Email: " +mail);
        lbBirth.setText("Sinh nhật: " +birthday.toString());
        lbBio.setText("Bio: "  +bio);
        lbSex.setText("Giới tính: " +sex);

        setFont(lbEmail);
        setFont(lbSex);
        setFont(lbBirth);

        btnChange.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
//                dispose();
//                new DialogChangeAccount(null, "Thông tin tài khoản", true, theme, language,
//                        name, nickname, birthday, mail, avatarImg, createAt, coverImg, sex, bio, sizeAtv, sizeCover, sessionFactory, ID_NOW);
            }
            public void mouseEntered(MouseEvent e) {
                btnChange.setBackground(new Color(130, 57, 185));
            }
            public void mouseExited(MouseEvent e) {
                btnChange.setBackground(colorTheme.purpleLight);
            }
        });

        acc.add(lbNoti);
        acc.add(btnAvt);
        acc.add(lbCover);
        acc.add(lbName);
        acc.add(lbEmail);
        acc.add(lbBio);
        acc.add(lbSex);
        acc.add(lbBirth);
        acc.add(btnChange);

        acc.setBounds(0, 0, 500, 600);
        add(acc);
        acc.setVisible(true);

        if (theme.equals("light")) {
            acc.setBackground(colorTheme.whiteLight);
            lbName.setForeground(Color.BLACK);
            lbBio.setForeground(Color.BLACK);
        } else {
            acc.setBackground(colorTheme.blackLight5);
            lbName.setForeground(Color.WHITE);
            lbBio.setForeground(Color.WHITE);
        }

        setLocationRelativeTo(null);
//        setVisible(true);
    }

    private void setFont (JLabel label) {
        label.setFont(new Font(fontName, Font.PLAIN, 18));
        label.setOpaque(false);

        if (theme.equals("light")) {
            label.setForeground(Color.BLACK);
        } else {
            label.setForeground(Color.WHITE);
        }
    }
}

class DialogChangeAccount extends JDialog {
    ColorTheme colorTheme = new ColorTheme();

    JPanel acc = new JPanel();

    JLabel lbCover = new JLabel();
    JLabel lbNoti = new JLabel();
    JButton btnAvt;
    JButton btnChange;
    JTextField tfName;
    JTextField tfNick;
    JTextField tfEmail;
    JTextField tfBirth;
    JRadioButton btnMale;
    JRadioButton btnFemale;
    JTextArea taBio;

    private String theme;
    private final String language;
    private final Language languageChange;
    private String fontName = "Meiryo";

    SessionFactory sessionFactory;
    private int ID_NOW;

    public DialogChangeAccount(JFrame frameParent, String title, boolean modal, String themes, String lang,
                               String name, String nickname, Date birthday, String mail, byte[] avatarImg, Date createAt, byte[] coverImg, String sex, String bio, String sizeAtv, String sizeCover, SessionFactory sessionFactory, int ID_NOW) {
        super(frameParent, title, modal);
        this.theme = themes;
        this.language = lang;
        this.sessionFactory = sessionFactory;
        this.ID_NOW = ID_NOW;

        languageChange = new Language(this.language);
        languageChange.loadLanguage();

        setSize(500, 700);
        setLayout(null);
        getContentPane().setBackground(colorTheme.whiteLight);

        acc.setLayout(null);

        btnAvt = new Round("", colorTheme.blackLight25, colorTheme.transparent, 200);
        btnChange = new Round(languageChange.getString("bar.DialogAcc.btnChange"), colorTheme.purpleLight, Color.white, 30);

        tfName = new TextFieldRound(name, colorTheme.whiteDark25, Color.black, 10);
        tfNick = new TextFieldRound(nickname, colorTheme.whiteDark25, Color.black, 10);
        tfEmail = new TextFieldRound(mail, colorTheme.whiteDark25, Color.black, 10);
        tfBirth = new TextFieldRound(birthday.toString(), colorTheme.whiteDark25, Color.black, 10);
        taBio = new JTextArea(bio);
        taBio.setLineWrap(true);
        taBio.setWrapStyleWord(true);
        taBio.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        btnMale = new JRadioButton(languageChange.getString("createFull.btnMale"));
        btnFemale = new JRadioButton(languageChange.getString("createFull.btnFemale"));

        ButtonGroup group = new ButtonGroup();
        group.add(btnMale);
        group.add(btnFemale);

        lbCover.setBounds(0, 0, 500, 200);
        lbCover.setBackground(colorTheme.blackLight50);
        btnAvt.setBounds(40, (lbCover.getHeight() - 80), 160, 160);

        tfName.setText(name);
        tfNick.setText(nickname);
        tfEmail.setText(mail);
        tfBirth.setText(birthday.toString());
        btnMale.setBackground(colorTheme.whiteLight);
        btnFemale.setBackground(colorTheme.whiteLight);

        if (sex.equals("Male")) {
            btnMale.setSelected(true);
        } else {
            btnFemale.setSelected(true);
        }

        tfName.setBounds(50, 320, 400, 30);
        tfNick.setBounds(50, tfName.getY() + tfName.getHeight() + 10, 400, 30);
        taBio.setBounds(50, tfNick.getY() + tfNick.getHeight() + 10, 400, 60);
        tfEmail.setBounds(50, taBio.getY() + taBio.getHeight() + 10, 400, 30);
        btnMale.setBounds(50, tfEmail.getY() + tfEmail.getHeight() + 10, 100, 30);
        btnFemale.setBounds(btnMale.getX() + btnMale.getWidth() + 50, btnMale.getY(), 100, 30);
        tfBirth.setBounds(50, btnMale.getY() + btnMale.getHeight() + 10, 400, 30);
        btnChange.setBounds(100, tfBirth.getY() + tfBirth.getHeight() + 20, getWidth() - 200, 40);

        Image imgcover = new ImageIcon(coverImg).getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH);
        lbCover.setIcon(new ImageIcon(imgcover));
        Image imgavt = new ImageIcon(avatarImg).getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH);
        btnAvt.setIcon(new ImageIcon(imgavt));

        btnAvt.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Chọn hình ảnh");
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (file != null) {
                    try {
                        BufferedImage bufferedImage = ImageIO.read(file);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        btnChange.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                updateAcc();
                dispose();
            }
            public void mouseEntered(MouseEvent e) {
                btnChange.setBackground(new Color(130, 57, 185));
            }
            public void mouseExited(MouseEvent e) {
                btnChange.setBackground(colorTheme.purpleLight);
            }
        });

        acc.add(lbCover);
        acc.add(btnAvt);
        acc.add(tfName);
        acc.add(tfNick);
        acc.add(taBio);
        acc.add(tfEmail);
        acc.add(btnMale);
        acc.add(btnFemale);
        acc.add(tfBirth);
        acc.add(btnChange);

        acc.setBounds(0, 0, 500, 700);
        add(acc);
        acc.setVisible(true);

        if (theme.equals("light")) {
            acc.setBackground(colorTheme.whiteLight);
        } else {
            acc.setBackground(colorTheme.blackLight5);
            tfName.setForeground(Color.WHITE);
            tfNick.setForeground(Color.WHITE);
            tfEmail.setForeground(Color.WHITE);
            btnMale.setForeground(Color.WHITE);
            btnFemale.setForeground(Color.WHITE);
            tfBirth.setForeground(Color.WHITE);
            taBio.setForeground(Color.WHITE);
        }

        setLocationRelativeTo(null);
//        setVisible(true);
    }

    private void updateAcc() {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            User user = session.get(User.class, ID_NOW);

            String sexChoice = "";
            if (btnMale.isSelected()) {
                sexChoice = "Male";
            } else if (btnFemale.isSelected()) {
                sexChoice = "Female";
            }
            if (user != null) {
                user.setName(tfName.getText());
                user.setNickname(tfNick.getText());
                user.setMail(tfEmail.getText());
                user.setBio(taBio.getText());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date birthday = sdf.parse(tfBirth.getText());
                    user.setBirthday(birthday);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                user.setSex(sexChoice);

                session.merge(user);

                session.getTransaction().commit();
            }
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }
}
