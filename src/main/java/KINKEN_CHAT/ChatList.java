package KINKEN_CHAT;

import Hibernate.Group;
import Hibernate.GroupMember;
import Hibernate.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class ChatList extends JPanel{
    JTextField tfSearch;
    JButton btnFind;
    JScrollPane scrollPane;
    JPanel panelContainer;

    private List<User> userList = new ArrayList<>();
    private List<Group> groupList = new ArrayList<>();
//    private List<Group, int id>
    private ChoiceUserChat choiceUserChat;
    private SessionFactory sessionFactory;

    ColorTheme colorTheme = new ColorTheme();

    private String theme;
    private String lang;
    private ActionChange themeChange;
    private Language language;
    private String fontName = "Meiryo";
    private int ID_ME;

    Image imgNewGroupLight = new ImageIcon(PATHICON.getPath("search.png")).getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);
    Image imgNewGroupDark = new ImageIcon(PATHICON.getPath("searchDark.png")).getImage().getScaledInstance(36, 36, Image.SCALE_SMOOTH);

    public ChatList(String theme, ActionChange themeChange, String langu, SessionFactory sessionFactory, String mail, int ID_user, ChoiceUserChat choiceUser) {
        this.theme = theme;
        this.themeChange = themeChange;
        this.lang = langu;
        setOpaque(false);
        setLayout(null);
        this.choiceUserChat = choiceUser;
        this.sessionFactory = sessionFactory;
        this.ID_ME = ID_user;

        language = new Language(lang);
        language.loadLanguage();

        initScrollPane();

        tfSearch = new TextFieldRound(language.getString("chatlist.tfSearch"), colorTheme.whiteDark15, Color.BLACK, 10);
        tfSearch.setFont(new Font(fontName, Font.PLAIN, 18));
        btnFind = new Round("", colorTheme.transparent, Color.BLACK, 10);

        tfSearch.setBounds(10, 10, 230, 40);
        btnFind.setBounds(tfSearch.getX() + tfSearch.getWidth() + 10, tfSearch.getY(), 40, 40);

        tfSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkEmpty();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                checkEmpty();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            private void checkEmpty() {
                if (tfSearch.getText().isEmpty()) {
                    showUserList();
                }
            }
        });

        btnFind.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showUserList(tfSearch.getText());
            }
            public void mouseEntered(MouseEvent e) {
                if (theme.equals("light")) {
                    btnFind.setBackground(colorTheme.whiteDark25);
                } else {
                    btnFind.setBackground(colorTheme.blackLight50);
                }
            }
            public void mouseExited(MouseEvent e) {
                btnFind.setBackground(colorTheme.transparent);
            }
        });

        changeTheme(theme);

        getAcc();
        getGroup(sessionFactory, ID_user);

        add(tfSearch);
        add(btnFind);
    }

    public void getAcc() {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

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
    private void getGroup(SessionFactory sessionFactory, int userId) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            groupList = session.createQuery(
                            "SELECT g FROM Group g JOIN GroupMember gm ON g.id = gm.groupId WHERE gm.userId = :userId",
                            Group.class
                    ).setParameter("userId", userId)
                    .getResultList();

            session.getTransaction().commit();

            showUserList();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    private void initScrollPane() {
        panelContainer = new JPanel();
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panelContainer);
        scrollPane.setBounds(0, 60, 300, 780);
        panelContainer.setBackground(colorTheme.whiteLight);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);

        add(scrollPane);
    }

    private void createGroup(SessionFactory sessionFactory, String name_group, int id_create) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            Group group = new Group();
            group.setGroupName(name_group);
            group.setCreateBy(id_create);

            session.persist(group);

//            session.getTransaction().commit();

            GroupMember groupMember = new GroupMember();
            groupMember.setGroupId(group.getId());
            groupMember.setUserId(id_create);

            session.persist(groupMember);

            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, group.getGroupName() + " has been created");
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    public void showUserList() {
        panelContainer.removeAll();

        // Hiển thị danh sách người dùng
        for (User user : userList) {
            JPanel userPanel = new JPanel(new BorderLayout());
            userPanel.setPreferredSize(new Dimension(300, 50));
            userPanel.setMaximumSize(new Dimension(300, 50));
            userPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            userPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight25);

//            Image imgAVT = new ImageIcon(user.getAvatarImg()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            Image imgAVT = Function.resizeImage(new ImageIcon(user.getAvatarImg()).getImage(), 50, 50);
            JButton btnAVT = new Round("", colorTheme.transparent, Color.BLACK, 50);
            btnAVT.setIcon(new ImageIcon(imgAVT));
//            btnAVT.setEnabled(false);
            JLabel lbName = new JLabel(user.getName() + " - " + user.getStatus());
            lbName.setFont(new Font(fontName, Font.PLAIN, 14));
            userPanel.add(btnAVT, BorderLayout.WEST);
            userPanel.add(lbName, BorderLayout.CENTER);

            userPanel.putClientProperty("user", user);
            userPanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    User clickedUser = (User) ((JPanel) e.getSource()).getClientProperty("user");
                    if (choiceUserChat != null) {
                        choiceUserChat.choice(clickedUser, clickedUser.getMail(), clickedUser.getName(), clickedUser.getStatus(), user.getAvatarImg());
                    }
                }
                public void mouseEntered(MouseEvent e) {
                    userPanel.setBackground(theme.equals("light") ? colorTheme.whiteDark15 : colorTheme.blackLight35);
                    userPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent e) {
                    userPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight25);
                    userPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });

            panelContainer.add(userPanel);
        }

        // Hiển thị danh sách nhóm
        for (Group group : groupList) {
            JPanel groupPanel = new JPanel(new BorderLayout());
            groupPanel.setPreferredSize(new Dimension(300, 50));
            groupPanel.setMaximumSize(new Dimension(300, 50));
            groupPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            groupPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight25);

            Image imgAVT = Function.resizeImage(new ImageIcon(group.getGroupAvtUrl()).getImage(), 50, 50);
            JButton btnAVT = new Round("", colorTheme.transparent, Color.BLACK, 50);
            btnAVT.setIcon(new ImageIcon(imgAVT));

            JLabel lbGroup = new JLabel("G: " + group.getGroupName());
            lbGroup.setFont(new Font(fontName, Font.BOLD, 14));

            groupPanel.add(btnAVT, BorderLayout.WEST);
            groupPanel.add(lbGroup, BorderLayout.CENTER);

            groupPanel.putClientProperty("group", group);

            groupPanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    Group clickedGroup = (Group) ((JPanel) e.getSource()).getClientProperty("group");
                    if (choiceUserChat != null) {
                        choiceUserChat.choiceGroup(clickedGroup, clickedGroup.getGroupName(), getUserInGroup(sessionFactory, clickedGroup.getId()), clickedGroup.getGroupAvtUrl());
                    }
                }
                public void mouseEntered(MouseEvent e) {
                    groupPanel.setBackground(theme.equals("light") ? colorTheme.whiteDark15 : colorTheme.blackLight35);
                    groupPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                public void mouseExited(MouseEvent e) {
                    groupPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight25);
                    groupPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });

            panelContainer.add(groupPanel);
        }

        panelContainer.revalidate();
        panelContainer.repaint();
    }

    public ArrayList<String> getUserInGroup(SessionFactory sessionFactory, int groupId) {
        ArrayList<String> mails = new ArrayList<>();
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            mails = new ArrayList<>(
                    session.createQuery(
                                    "SELECT u.mail FROM User u JOIN GroupMember gm ON u.id = gm.userId WHERE gm.groupId = :groupId",
                                    String.class
                            ).setParameter("groupId", groupId)
                            .getResultList()
            );

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        return mails;
    }
    private void showUserList(String find) {
        panelContainer.removeAll();

        // Hiển thị danh sách người dùng
        for (User user : userList) {
            if (user.getName().toLowerCase().contains(find.toLowerCase()) || user.getMail().toLowerCase().contains(find.toLowerCase())) {
                JPanel userPanel = new JPanel(new BorderLayout());
                userPanel.setPreferredSize(new Dimension(300, 50));
                userPanel.setMaximumSize(new Dimension(300, 50));
                userPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                userPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight25);

//            Image imgAVT = new ImageIcon(user.getAvatarImg()).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                Image imgAVT = Function.resizeImage(new ImageIcon(user.getAvatarImg()).getImage(), 50, 50);
                JButton btnAVT = new Round("", colorTheme.transparent, Color.BLACK, 50);
                btnAVT.setIcon(new ImageIcon(imgAVT));
//            btnAVT.setEnabled(false);
                JLabel lbName = new JLabel(user.getName() + " - " + user.getStatus());
                lbName.setFont(new Font(fontName, Font.PLAIN, 14));
                userPanel.add(btnAVT, BorderLayout.WEST);
                userPanel.add(lbName, BorderLayout.CENTER);

                userPanel.putClientProperty("user", user);
                userPanel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        User clickedUser = (User) ((JPanel) e.getSource()).getClientProperty("user");
                        if (choiceUserChat != null) {
                            choiceUserChat.choice(clickedUser, clickedUser.getMail(), clickedUser.getName(), clickedUser.getStatus(), user.getAvatarImg());
                        }
                    }
                    public void mouseEntered(MouseEvent e) {
                        userPanel.setBackground(theme.equals("light") ? colorTheme.whiteDark15 : colorTheme.blackLight35);
                        userPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    public void mouseExited(MouseEvent e) {
                        userPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight25);
                        userPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                });

                panelContainer.add(userPanel);
            }

        }

        // Hiển thị danh sách nhóm
        for (Group group : groupList) {
            if (group.getGroupName().toLowerCase().contains(find.toLowerCase())) {
                JPanel groupPanel = new JPanel(new BorderLayout());
                groupPanel.setPreferredSize(new Dimension(300, 50));
                groupPanel.setMaximumSize(new Dimension(300, 50));
                groupPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                groupPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight25);

                Image imgAVT = Function.resizeImage(new ImageIcon(group.getGroupAvtUrl()).getImage(), 50, 50);
                JButton btnAVT = new Round("", colorTheme.transparent, Color.BLACK, 50);
                btnAVT.setIcon(new ImageIcon(imgAVT));

                JLabel lbGroup = new JLabel("G: " + group.getGroupName());
                lbGroup.setFont(new Font(fontName, Font.BOLD, 14));

                groupPanel.add(btnAVT, BorderLayout.WEST);
                groupPanel.add(lbGroup, BorderLayout.CENTER);

                groupPanel.putClientProperty("group", group);

                groupPanel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        Group clickedGroup = (Group) ((JPanel) e.getSource()).getClientProperty("group");
                        if (choiceUserChat != null) {
                            choiceUserChat.choiceGroup(clickedGroup, clickedGroup.getGroupName(), getUserInGroup(sessionFactory, clickedGroup.getId()), clickedGroup.getGroupAvtUrl());
                        }
                    }
                    public void mouseEntered(MouseEvent e) {
                        groupPanel.setBackground(theme.equals("light") ? colorTheme.whiteDark15 : colorTheme.blackLight35);
                        groupPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    public void mouseExited(MouseEvent e) {
                        groupPanel.setBackground(theme.equals("light") ? colorTheme.whiteLight : colorTheme.blackLight25);
                        groupPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                });

                panelContainer.add(groupPanel);
            }

        }

        panelContainer.revalidate();
        panelContainer.repaint();
    }

    public void changeTheme(String theme) {
        this.theme = theme;
        if (theme.equals("light")) {
            btnFind.setIcon(new ImageIcon(imgNewGroupLight));
        } else {
            btnFind.setIcon(new ImageIcon(imgNewGroupDark));
        }

        repaint();
        revalidate();
    }

    public void changeLang(String lang) {
        this.lang = lang;
        language.changeLanguage(this.lang);
        tfSearch.setText(language.getString("chatlist.tfSearch"));
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
        g2d.drawLine(0, 60, getWidth(), 60);
    }
}