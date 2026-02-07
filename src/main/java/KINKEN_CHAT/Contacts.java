package KINKEN_CHAT;

import Hibernate.FrRequests;
import Hibernate.Friend;
import Hibernate.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Contacts extends JPanel implements ActionContact {
    ListChoice list;
    MainListFriend mainListFriend;
    MainNewFriend mainNewFriend;

    ColorTheme colorTheme = new ColorTheme();

    private String theme;
    private String lang;
    private ActionChange themeChange;
    private Language language;
    private String fontName = "Meiryo";

    public Contacts(String theme, ActionChange themeChange, String langu, SessionFactory sessionFactory, String mail, int ID_user) {
        this.theme = theme;
        this.themeChange = themeChange;
        this.lang = langu;
        setOpaque(false);
        setLayout(null);

        list = new ListChoice(theme, themeChange, lang, sessionFactory, mail, ID_user);
        mainListFriend = new MainListFriend(theme, themeChange, lang, sessionFactory, mail, ID_user);
        mainNewFriend = new MainNewFriend(theme, themeChange, lang, sessionFactory, mail, ID_user);
        list.setActionContact(this);

        list.setVisible(true);
        add(list);
        mainListFriend.setVisible(true);
        mainNewFriend.setVisible(false);
        add(mainListFriend);
        add(mainNewFriend);
    }

    public void changeTheme(String theme) {
        this.theme = theme;


        list.changeTheme(theme);
        repaint();
        revalidate();
    }

    public void changeLanguage(String language) {
        this.lang = language;

        list.changeLang(this.lang);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (theme.equals("light")) {
            g2d.setColor(colorTheme.whiteLight);
        } else {
            g2d.setColor(colorTheme.blackLight5);
        }
        g2d.fillRoundRect(320, 10, getWidth() - 375, getHeight() - 25, 10, 10);

        list.setBounds(10, 10, 300, getHeight() - 30);
        list.setSize(300, getHeight() - 25);
        mainListFriend.setBounds(320, 10, getWidth() - 375, getHeight() - 25);
        mainListFriend.setSize(getWidth() - 375, getHeight() - 25);
        mainNewFriend.setBounds(320, 10, getWidth() - 375, getHeight() - 25);
        mainNewFriend.setSize(getWidth() - 375, getHeight() - 25);
    }

    @Override
    public void choiceSecsion(String choice) {
        if (choice.equals("listFriend")) {
            mainListFriend.showList();
        } else if (choice.equals("addFriend")) {
            mainNewFriend.showAcp();
        }
        mainListFriend.setVisible(choice.equals("listFriend"));
        mainNewFriend.setVisible(choice.equals("addFriend"));

        repaint();
        revalidate();
    }
}

class ListChoice extends JPanel {
    JTextField tfSearch;
    JButton btnNewGroup;
    JButton btnListFriend;
    JButton btnAddFriend;

    ColorTheme colorTheme = new ColorTheme();

    private java.util.List<User> userList = new ArrayList<>();
//    private List<Group> groupList = new ArrayList<>();
    private ActionContact actionContact;

    private String theme;
    private String lang;
    private ActionChange themeChange;
    private Language language;
    private String fontName = "Meiryo";
    private int indexBtn = 1;
    private String choiceNow = "listFriend";

    private int ID_ME;

    Image imgNewGroupLight = new ImageIcon(PATHICON.getPath("createGroupLight.png")).getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
    Image imgNewGroupDark = new ImageIcon(PATHICON.getPath("createGroupDark.png")).getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);

    public ListChoice(String theme, ActionChange themeChange, String langu, SessionFactory sessionFactory, String mail, int ID_user) {
        this.theme = theme;
        this.themeChange = themeChange;
        this.lang = langu;
        this.ID_ME = ID_user;
        setOpaque(false);
        setLayout(null);

        language = new Language(lang);
        language.loadLanguage();

        btnListFriend = new Round("", colorTheme.transparent, Color.BLACK, 0);
        btnAddFriend = new Round("", colorTheme.transparent, Color.BLACK, 0);

        int height = 50;

        btnListFriend.setBounds(0, 0, 300, height);
        btnAddFriend.setBounds(0, btnListFriend.getY() + btnListFriend.getHeight(), btnListFriend.getWidth(), height);

        setupFriendMenuButton(btnListFriend, "listFriend");
        setupFriendMenuButton(btnAddFriend, "addFriend");

        changeTheme(theme);
        changeLang(lang);

        add(btnListFriend);
        add(btnAddFriend);
    }

    public void setActionContact(ActionContact actionContact) {
        this.actionContact = actionContact;
    }

    private void changeChoice(String choice) {
        this.choiceNow = choice;

        btnListFriend.setBackground(choice.equals("listFriend") ? colorTheme.whiteDark25 : colorTheme.transparent);
        btnAddFriend.setBackground(choice.equals("addFriend") ? colorTheme.whiteDark25 : colorTheme.transparent);

        repaint();
        revalidate();
    }

    private void setupFriendMenuButton(JButton button, String choiceName) {
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font(fontName, Font.PLAIN, 18));
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                changeChoice(choiceName);
                actionContact.choiceSecsion(choiceName);
            }

            public void mouseEntered(MouseEvent e) {
                button.setBackground(colorTheme.whiteDark25);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(MouseEvent e) {
                if (!choiceNow.equals(choiceName)) {
                    button.setBackground(colorTheme.transparent);
                }
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }


    public void changeTheme(String theme) {
        this.theme = theme;
        if (theme.equals("light")) {
            btnListFriend.setForeground(Color.BLACK);
            btnAddFriend.setForeground(Color.BLACK);
        } else {
            btnListFriend.setForeground(Color.WHITE);
            btnAddFriend.setForeground(Color.WHITE);
        }

        repaint();
        revalidate();
    }

    public void changeLang(String lang) {
        this.lang = lang;
        language.changeLanguage(this.lang);

        btnListFriend.setText(language.getString("listChoice.btnListFriend"));
        btnAddFriend.setText(language.getString("listChoice.btnAddFriend"));
        repaint();
        revalidate();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if (theme.equals("light")) {
            g2d.setColor(colorTheme.whiteLight);
        } else if (theme.equals("dark")) {
            g2d.setColor(colorTheme.blackLight5);
        }

        int radius = 15;
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        g2d.setColor(colorTheme.blackLight25);
    }
}

class MainListFriend extends JPanel {
    ColorTheme colorTheme = new ColorTheme();

    private List<User> userList = new ArrayList<>();

    private String theme;
    private String lang;
    private ActionChange themeChange;
    private Language language;
    private String fontName = "Meiryo";
    private int ID_ME;
    private SessionFactory sessionFactory;

    //ListFriend
    JLabel lbNoti = new JLabel();
    JTextField tfSearch;
    JPanel panelListFriend = new JPanel();
    JScrollPane scrollPaneListFriend = new JScrollPane(panelListFriend);

    Image imgMore = new ImageIcon(PATHICON.getPath("more3.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

    public MainListFriend(String theme, ActionChange themeChange, String langu, SessionFactory sessionFactory, String mail, int ID_user) {
        this.theme = theme;
        this.lang = langu;
        this.themeChange = themeChange;
        this.sessionFactory = sessionFactory;
        this.ID_ME = ID_user;
        setLayout(null);
        setOpaque(false);

        language = new Language(lang);
        language.loadLanguage();

        tfSearch = new TextFieldRound(language.getString("chatlist.tfSearch"), colorTheme.purpleLight, colorTheme.whiteLight, 20);

        lbNoti.setText(language.getString("contact.MainListFriend.lbNoti"));
        lbNoti.setBounds(10, 10, 400, 30);
        lbNoti.setFont(new Font(fontName, Font.PLAIN, 18));
        tfSearch.setBounds(10, 50, 400, 40);
        tfSearch.setFont(new Font(fontName, Font.PLAIN, 18));
        scrollPaneListFriend.setBorder(null);
//        scrollPaneListFriend.setBounds(10, 100, this.getWidth(), this.getHeight());

        panelListFriend.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelListFriend.setOpaque(false);
        panelListFriend.setLayout(new BoxLayout(panelListFriend, BoxLayout.Y_AXIS));

        showListFriend(sessionFactory, this.ID_ME);

        tfSearch.addActionListener(e -> {
            String text = tfSearch.getText();
        });

        add(lbNoti);
        add(tfSearch);
        add(scrollPaneListFriend);
    }

    public void showList() {
        showListFriend(sessionFactory, this.ID_ME);
    }

    private void showListFriend(SessionFactory sessionFactory, int ID_ME) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

//            userList = session.createQuery("FROM User WHERE id != :id", User.class)
//                    .setParameter("id", ID_ME)
//                    .getResultList();
            userList = session.createQuery("""
                FROM User u
                WHERE u.id != :currentId AND u.id IN (
                    SELECT f.ID_FRIEND 
                    FROM Friend f 
                    WHERE f.ID_USER_FK = :currentId
                    
                    UNION
                    
                    SELECT f.ID_USER_FK 
                    FROM Friend f 
                    WHERE f.ID_FRIEND = :currentId
                )
                """, User.class)
                    .setParameter("currentId", ID_ME)
                    .getResultList();

            showUserList();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private void showUserList() {
        panelListFriend.removeAll();

        for (User user : userList) {
            JPanel userPanel = new JPanel(new BorderLayout());
//            userPanel.setPreferredSize(new Dimension(0, 50));
            userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
            userPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            userPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight25);

//            Image imgAVT = new ImageIcon(user.getAvatarImg()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            Image imgAVT = Function.resizeImage(new ImageIcon(user.getAvatarImg()).getImage(), 50, 50);
            JButton btnAVT = new Round("", colorTheme.transparent, Color.BLACK, 50);
            btnAVT.setIcon(new ImageIcon(imgAVT));
//            btnAVT.setEnabled(false);
            JLabel lbName = new JLabel(user.getName() + " (" + user.getNickname() +")");
            lbName.setFont(new Font(fontName, Font.PLAIN, 16));
            JButton btnMore = new JButton(new ImageIcon(imgMore));
            btnMore.setContentAreaFilled(false);
            btnMore.setBorderPainted(false);
            btnMore.setOpaque(false);
            btnMore.setBorder(null);
            btnMore.setPreferredSize(new Dimension(40, 40));

            btnMore.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JPopupMenu menuFr = new JPopupMenu();
                    JMenuItem seeInfor = new JMenuItem("Xem thông tin");
                    JMenuItem deleteFr = new JMenuItem("Hủy kết bạn");
                    deleteFr.setForeground(Color.RED);

                    seeInfor.addActionListener(e1 -> {
                        User clickedUser = (User) ((JPanel) e.getSource()).getClientProperty("user");
                        DialogAccount dialogAccount = new DialogAccount(null, "Thông tin tài khoản", true, theme, lang,
                                clickedUser.getName(), clickedUser.getNickname(), clickedUser.getBirthday(), clickedUser.getMail(), clickedUser.getAvatarImg(), clickedUser.getCreateAt(), clickedUser.getCoverImg(), clickedUser.getSex(), clickedUser.getBio(), clickedUser.getSizeAtv(), clickedUser.getSizeCover(), sessionFactory, clickedUser.getId());
                        dialogAccount.btnChange.setVisible(false);
                        dialogAccount.setVisible(true);
                    });
                    deleteFr.addActionListener(e1 -> {
                        deleteFriend(ID_ME, user.getId());
                        showListFriend(sessionFactory, ID_ME);
                        menuFr.setVisible(false);
                        repaint();
                        revalidate();
                    });

                    menuFr.add(seeInfor);
                    menuFr.add(deleteFr);

                    menuFr.show(e.getComponent(), e.getX() - 100, btnMore.getY());
                }
            });

            userPanel.add(btnAVT, BorderLayout.WEST);
            userPanel.add(lbName, BorderLayout.CENTER);
            userPanel.add(btnMore, BorderLayout.EAST);

            userPanel.putClientProperty("user", user);
            userPanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    User clickedUser = (User) ((JPanel) e.getSource()).getClientProperty("user");
                    DialogAccount dialogAccount = new DialogAccount(null, "Thông tin tài khoản", true, theme, lang,
                            clickedUser.getName(), clickedUser.getNickname(), clickedUser.getBirthday(), clickedUser.getMail(), clickedUser.getAvatarImg(), clickedUser.getCreateAt(), clickedUser.getCoverImg(), clickedUser.getSex(), clickedUser.getBio(), clickedUser.getSizeAtv(), clickedUser.getSizeCover(), sessionFactory, clickedUser.getId());
                    dialogAccount.btnChange.setVisible(false);
                    dialogAccount.setVisible(true);
                }
                public void mouseEntered(MouseEvent e) {
                    userPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent e) {
                    userPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });

            panelListFriend.add(userPanel);
        }
        repaint();
        revalidate();
    }

    public void deleteFriend(int ID_ME, int ID_FRIEND) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            Friend friend = session.createQuery("FROM Friend WHERE ID_USER_FK = :id1 AND ID_FRIEND = :id2", Friend.class)
                    .setParameter("id1", ID_ME)
                    .setParameter("id2", ID_FRIEND)
                    .getSingleResult();

            session.remove(friend);
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.getTransaction().commit();
            session.close();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        scrollPaneListFriend.setBounds(10, 100, this.getWidth() - 20, this.getHeight() - 100);
    }
}

class MainNewFriend extends JPanel {
    ColorTheme colorTheme = new ColorTheme();

    private List<User> userList = new ArrayList<>();
    private List<User> friendListAcp = new ArrayList<>();

    private String theme;
    private String lang;
    private ActionChange themeChange;
    private Language language;
    private String fontName = "Meiryo";
    private int ID_ME;

    private SessionFactory sessionFactory;

    //ListFriend
    JLabel lbNoti = new JLabel();
    JTextField tfSearch;
    JPanel panelListFriend = new JPanel();
    JPanel panelListRequest = new JPanel();
    JScrollPane scrollPaneListFriend = new JScrollPane(panelListFriend);
    JScrollPane scrollPaneListAcp = new JScrollPane(panelListRequest);

    Image imgMore = new ImageIcon(PATHICON.getPath("more3.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

    public MainNewFriend(String theme, ActionChange themeChange, String langu, SessionFactory sessionFactory, String mail, int ID_user) {
        this.theme = theme;
        this.lang = langu;
        this.themeChange = themeChange;
        this.sessionFactory = sessionFactory;
        this.ID_ME = ID_user;
        setLayout(null);
        setOpaque(false);

        language = new Language(lang);
        language.loadLanguage();

        tfSearch = new TextFieldRound(language.getString("chatlist.tfSearch"), colorTheme.purpleLight, colorTheme.whiteLight, 20);

        lbNoti.setText(language.getString("contact.MainNewFriend.lbNoti"));
        lbNoti.setBounds(10, 10, 400, 30);
        lbNoti.setFont(new Font(fontName, Font.PLAIN, 18));
        tfSearch.setBounds(10, 50, 400, 40);
        tfSearch.setFont(new Font(fontName, Font.PLAIN, 18));
        scrollPaneListFriend.setBorder(null);
        scrollPaneListAcp.setBorder(null);

        panelListFriend.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelListFriend.setOpaque(false);
        panelListFriend.setLayout(new BoxLayout(panelListFriend, BoxLayout.Y_AXIS));

        showAcp();

        tfSearch.addActionListener(e -> {
            String text = tfSearch.getText();
            showListFriend(sessionFactory, ID_ME, text);
        });

        add(lbNoti);
        add(tfSearch);
        add(scrollPaneListFriend);
        add(scrollPaneListAcp);
    }

    public void showAcp() {
        showListFriendRequest(sessionFactory, ID_ME);
    }

    private void showListFriend(SessionFactory sessionFactory, int ID_ME, String mail) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            userList = session.createQuery("""
                FROM User u 
                WHERE u.id != :currentId 
                  AND u.mail LIKE :mail
                  AND u.id NOT IN (
                    SELECT CASE 
                        WHEN f.ID_USER_FK = :currentId THEN f.ID_FRIEND 
                        WHEN f.ID_FRIEND = :currentId THEN f.ID_USER_FK 
                    END
                    FROM Friend f
                    WHERE (f.ID_USER_FK = :currentId OR f.ID_FRIEND = :currentId)
                  )
                  AND u.id NOT IN (
                    SELECT fr.ID_USER FROM FrRequests fr WHERE fr.ID_RECEIVER = :currentId
                    UNION
                    SELECT fr.ID_RECEIVER FROM FrRequests fr WHERE fr.ID_USER = :currentId
                  )
                """, User.class)
                    .setParameter("currentId", ID_ME)
                    .setParameter("mail", mail + "%")
                    .getResultList();

//            userList = session.createQuery("""
//                FROM User u
//                WHERE u.id != :currentId AND u.id IN (
//                    SELECT CASE
//                        WHEN f.ID_USER_FK = :currentId THEN f.ID_FRIEND
//                        WHEN f.ID_FRIEND = :currentId THEN f.ID_USER_FK
//                    END
//                    FROM Friend f
//                    WHERE (f.ID_USER_FK = :currentId OR f.ID_FRIEND = :currentId)
//                      AND f.STATUS = 'accepted'
//                )
//                """, User.class)
//                    .setParameter("currentId", ID_ME)
//                    .getResultList();
//            System.out.println(userList.size());

            showUserList();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    public void showListFriendRequest(SessionFactory sessionFactory, int ID_ME) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            friendListAcp = session.createQuery("""
            FROM User u
            WHERE u.id IN (
                SELECT f.ID_USER
                FROM FrRequests f
                WHERE f.ID_RECEIVER = :currentId
            )
            """, User.class)
                    .setParameter("currentId", ID_ME)
                    .getResultList();

            showAcpList();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private void showUserList() {
        panelListFriend.removeAll();

        // Hiển thị danh sách người dùng
        for (User user : userList) {
            JPanel userPanel = new JPanel(new BorderLayout());
//            userPanel.setPreferredSize(new Dimension(0, 50));
            userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
            userPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            userPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight25);

//            Image imgAVT = new ImageIcon(user.getAvatarImg()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            Image imgAVT = Function.resizeImage(new ImageIcon(user.getAvatarImg()).getImage(), 60, 60);
            JButton btnAVT = new Round("", colorTheme.transparent, Color.BLACK, 50);
            btnAVT.setIcon(new ImageIcon(imgAVT));
//            btnAVT.setEnabled(false);
            JLabel lbName = new JLabel(user.getName() + " (" + user.getNickname() +")");
            lbName.setFont(new Font(fontName, Font.PLAIN, 16));

            JButton btnadd = new Round("", colorTheme.purpleLight, Color.WHITE, 10);
            btnadd.setBounds(5, 20, 140, 40);
            if (checkFriendRequest(ID_ME, user.getId())) {
                btnadd.setText(language.getString("contact.MainNewFriend.btnDef"));
            } else {
                btnadd.setText(language.getString("contact.MainNewFriend.lbNoti"));
            }

            JPanel panelbnt = new JPanel();
            panelbnt.setLayout(null);
            panelbnt.setOpaque(false);
            panelbnt.add(btnadd);
            panelbnt.setMaximumSize(new Dimension(150, 30)); // Giới hạn cao nhất
            panelbnt.setPreferredSize(new Dimension(150, 30));
            panelbnt.setAlignmentX(Component.CENTER_ALIGNMENT);

            btnadd.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (checkFriendRequest(ID_ME, user.getId())) {
                        cancelAddFriend(ID_ME, user.getId());
                        btnadd.setText(language.getString("contact.MainNewFriend.lbNoti"));
                    } else {
                        sendAddFriend(ID_ME, user.getId());
                        btnadd.setText(language.getString("contact.MainNewFriend.btnDef"));
                    }
                    repaint();
                    revalidate();
                }
                public void mouseEntered(MouseEvent e) {
                    btnadd.setBackground(new Color(133, 56, 190));
                    btnadd.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent e) {
                    btnadd.setBackground(colorTheme.purpleLight);
                    btnadd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });


            userPanel.add(btnAVT, BorderLayout.WEST);
            userPanel.add(lbName, BorderLayout.CENTER);
            userPanel.add(panelbnt, BorderLayout.EAST);

            userPanel.putClientProperty("user", user);
            userPanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    User clickedUser = (User) ((JPanel) e.getSource()).getClientProperty("user");
                    DialogAccount dialogAccount = new DialogAccount(null, "Thông tin tài khoản", true, theme, lang,
                            clickedUser.getName(), clickedUser.getNickname(), clickedUser.getBirthday(), clickedUser.getMail(), clickedUser.getAvatarImg(), clickedUser.getCreateAt(), clickedUser.getCoverImg(), clickedUser.getSex(), clickedUser.getBio(), clickedUser.getSizeAtv(), clickedUser.getSizeCover(), sessionFactory, clickedUser.getId());
                    dialogAccount.btnChange.setVisible(false);
                    dialogAccount.setVisible(true);
                }
                public void mouseEntered(MouseEvent e) {
                    userPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent e) {
                    userPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });

            panelListFriend.add(userPanel);
        }
        repaint();
        revalidate();
    }

    private void showAcpList() {
        panelListRequest.removeAll();

        // Hiển thị danh sách người dùng
        for (User user : friendListAcp) {
            JPanel userPanel = new JPanel(new BorderLayout());
//            userPanel.setPreferredSize(new Dimension(0, 50));
            userPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
            userPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            userPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight25);

//            Image imgAVT = new ImageIcon(user.getAvatarImg()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            Image imgAVT = Function.resizeImage(new ImageIcon(user.getAvatarImg()).getImage(), 60, 60);
            JButton btnAVT = new Round("", colorTheme.transparent, Color.BLACK, 50);
            btnAVT.setIcon(new ImageIcon(imgAVT));
//            btnAVT.setEnabled(false);
            JLabel lbName = new JLabel(user.getName() + " (" + user.getNickname() +")");
            lbName.setFont(new Font(fontName, Font.PLAIN, 16));

            JButton btnadd = new Round("", colorTheme.purpleLight, Color.WHITE, 10);
            btnadd.setBounds(5, 20, 140, 40);
            if (checkFriendRequest(ID_ME, user.getId())) {
                btnadd.setText(language.getString("contact.MainNewFriend.cancel"));
            } else {
                btnadd.setText(language.getString("contact.MainNewFriend.acp"));
            }

            JPanel panelbnt = new JPanel();
            panelbnt.setLayout(null);
            panelbnt.setOpaque(false);
            panelbnt.add(btnadd);
            panelbnt.setMaximumSize(new Dimension(150, 30)); // Giới hạn cao nhất
            panelbnt.setPreferredSize(new Dimension(150, 30));
            panelbnt.setAlignmentX(Component.CENTER_ALIGNMENT);

            btnadd.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (checkFriendRequest(ID_ME, user.getId())) {
                        cancelAddFriend(ID_ME, user.getId());
                        btnadd.setText(language.getString("contact.MainNewFriend.acp"));
                    } else {
                        acpInvite(ID_ME, user.getId());
                        showAcp();
//                        btnadd.setText(language.getString("contact.MainNewFriend.cancel"));
                    }
                    repaint();
                    revalidate();
                }
                public void mouseEntered(MouseEvent e) {
                    btnadd.setBackground(new Color(133, 56, 190));
                    btnadd.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent e) {
                    btnadd.setBackground(colorTheme.purpleLight);
                    btnadd.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });


            userPanel.add(btnAVT, BorderLayout.WEST);
            userPanel.add(lbName, BorderLayout.CENTER);
            userPanel.add(panelbnt, BorderLayout.EAST);

            userPanel.putClientProperty("user", user);
            userPanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    User clickedUser = (User) ((JPanel) e.getSource()).getClientProperty("user");
                    DialogAccount dialogAccount = new DialogAccount(null, "Thông tin tài khoản", true, theme, lang,
                            clickedUser.getName(), clickedUser.getNickname(), clickedUser.getBirthday(), clickedUser.getMail(), clickedUser.getAvatarImg(), clickedUser.getCreateAt(), clickedUser.getCoverImg(), clickedUser.getSex(), clickedUser.getBio(), clickedUser.getSizeAtv(), clickedUser.getSizeCover(), sessionFactory, clickedUser.getId());
                    dialogAccount.btnChange.setVisible(false);
                    dialogAccount.setVisible(true);
                }
                public void mouseEntered(MouseEvent e) {
                    userPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent e) {
                    userPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });

            panelListRequest.add(userPanel);
        }
        repaint();
        revalidate();
    }

    private void acpInvite(int ID_ME, int ID_RE) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            cancelAddFriend(ID_ME, ID_RE);

            Friend friend = new Friend();
            friend.setID_USER_FK(ID_ME);
            friend.setID_FRIEND(ID_RE);

            session.persist(friend);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private boolean checkFriendRequest(int ID_ME, int ID_FRIEND) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            Long count = session.createQuery("""
            SELECT COUNT(*) FROM FrRequests fr
            WHERE fr.ID_USER = :idMe AND fr.ID_RECEIVER = :idFriend
        """, Long.class)
                    .setParameter("idMe", ID_ME)
                    .setParameter("idFriend", ID_FRIEND)
                    .uniqueResult();

            session.getTransaction().commit();

            return count != null && count > 0;
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    private void sendAddFriend(int ID_ME, int ID_RE) {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            FrRequests frRequests = new FrRequests();
            frRequests.setID_USER(ID_ME);
            frRequests.setID_RECEIVER(ID_RE);
            frRequests.setSEND_AT(new Timestamp(System.currentTimeMillis()));

            session.persist(frRequests);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void cancelAddFriend(int ID_ME, int ID_RE) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Tìm lời mời kết bạn (nếu có)
            FrRequests request = session.createQuery("""
            FROM FrRequests fr
            WHERE fr.ID_USER = :idMe AND fr.ID_RECEIVER = :idRe
                OR (fr.ID_USER = :idRe AND fr.ID_RECEIVER = :idMe)
        """, FrRequests.class)
                    .setParameter("idMe", ID_ME)
                    .setParameter("idRe", ID_RE)
                    .uniqueResult();

            if (request != null) {
                session.remove(request);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();
        int topMargin = 100;
        int halfWidth = panelWidth / 2;

        scrollPaneListFriend.setBounds(10, topMargin, halfWidth - 15, panelHeight - topMargin);
        scrollPaneListAcp.setBounds(halfWidth + 5, topMargin, halfWidth - 15, panelHeight - topMargin);

    }
}