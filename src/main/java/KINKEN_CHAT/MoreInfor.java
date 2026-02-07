package KINKEN_CHAT;

import Hibernate.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MoreInfor extends JPanel {
    JLabel lbInforTitle = new JLabel();
    JButton btnChangeNickname;
    JButton btnNotification;
    JButton btnPin;
    JButton btnCreateGroup;
    JButton btnTemporaryChat;
    static JLabel lbName = new JLabel("<Name>");
    JLabel lbNotification = new JLabel();
    JLabel lbPin = new JLabel();
    JLabel lbCreateGroup = new JLabel();
    JLabel lbTemporaryChat = new JLabel();
    JButton btnChatCustom;
    JButton btnCustomTheme;
    JButton btnCustomImoji;
    JButton btnStorage;
    JButton btnDeleteChat;
    JButton btnAVT;
    JButton btnDeleteGroup;

    Image imgChangeNickNameLight = new ImageIcon(PATHICON.getPath("changeNickNameLight.png")).getImage().getScaledInstance(23, 23, Image.SCALE_SMOOTH);
    Image imgChangeNickNameDark = new ImageIcon(PATHICON.getPath("changeNickNameDark.png")).getImage().getScaledInstance(23, 23, Image.SCALE_SMOOTH);
    Image imgNotificationLight = new ImageIcon(PATHICON.getPath("notificationLight.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
    Image imgNotificationDark = new ImageIcon(PATHICON.getPath("notificationDark.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
    Image imgPinLight = new ImageIcon(PATHICON.getPath("pinLight.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
    Image imgPinDark = new ImageIcon(PATHICON.getPath("pinDark.png")).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
    Image imgCreateGroupLight = new ImageIcon(PATHICON.getPath("createGroupLight.png")).getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
    Image imgCreateGroupDark = new ImageIcon(PATHICON.getPath("createGroupDark.png")).getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
    Image imgTemporaryChatLight = new ImageIcon(PATHICON.getPath("temporaryChatLight.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    Image imgTemporaryChatDark = new ImageIcon(PATHICON.getPath("temporaryChatDark.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    Image imgStorageLight = new ImageIcon(PATHICON.getPath("StorageLight.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    Image imgStorageDark = new ImageIcon(PATHICON.getPath("StorageDark.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    Image imgDelete = new ImageIcon(PATHICON.getPath("Delete.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

    static ColorTheme colorTheme = new ColorTheme();

    private String theme;
    private String lang;
    private ActionChange themeChange;
    private Language language;
    private String fontName = "Meiryo";
    private boolean isMes = false;
    private int ID_ME;
    private User User_RE;
    private Group Group_RE;
    private SessionFactory sessionFactory;
    private byte[] avt_RE;

    //Dialog add nhóm
    private JList<User> listFriend = new JList<>();
//    private String[] listFriend = {"Bảo", "Dũng", "Minh", "Trúc", "Nam"}; // danh sách bạn bè mẫu
    private JDialog dialogCreateGroup;
    private JTextField tfname;
    private JList<String> listName;
    private DefaultListModel<User> listModel;
    JScrollPane jscrollPaneListFriend;
    JTextField tfGroupName;

    public MoreInfor(String themes, ActionChange themeChange, String langu, int ID_ME, SessionFactory sessionFactory) {
        this.ID_ME = ID_ME;
        this.theme = themes;
        this.themeChange = themeChange;
        this.lang = langu;
        setLayout(null);
        setOpaque(false);
        this.sessionFactory = sessionFactory;

        language = new Language(this.lang);
        language.loadLanguage();

        lbInforTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lbInforTitle.setFont(new Font(fontName, Font.BOLD, 20));
        lbInforTitle.setOpaque(false);
        lbInforTitle.setText("<html>" +language.getString("moreInfor.lbInforTitle" +"</html>"));

        lbName.setHorizontalAlignment(SwingConstants.CENTER);
        lbName.setFont(new Font(fontName, Font.BOLD, 24));
        lbName.setOpaque(false);
        lbNotification.setHorizontalAlignment(SwingConstants.CENTER);
        lbNotification.setFont(new Font(fontName, Font.BOLD, 14));
        lbNotification.setOpaque(false);
        lbNotification.setText("<html><div style='text-align: center;'>" +language.getString("moreInfor.lbNotification") +"</div></html>");
        lbPin.setHorizontalAlignment(SwingConstants.CENTER);
        lbPin.setFont(new Font(fontName, Font.BOLD, 14));
        lbPin.setOpaque(false);
        lbPin.setText("<html><div style='text-align: center;'>" +language.getString("moreInfor.lbPin") +"</div></html>");
        lbCreateGroup.setHorizontalAlignment(SwingConstants.CENTER);
        lbCreateGroup.setFont(new Font(fontName, Font.BOLD, 14));
        lbCreateGroup.setOpaque(false);
        lbCreateGroup.setText("<html><div style='text-align: center;'>" +language.getString("moreInfor.lbCreateGroup") +"</div></html>");
        lbTemporaryChat.setHorizontalAlignment(SwingConstants.CENTER);
        lbTemporaryChat.setFont(new Font(fontName, Font.BOLD, 14));
        lbTemporaryChat.setOpaque(false);
        lbTemporaryChat.setText("<html><div style='text-align: center;'>" +language.getString("moreInfor.lbTemporaryChat") +"</div></html>");

        btnChangeNickname = new Round("", colorTheme.whiteDark15, Color.BLACK, 50);
        btnNotification = new Round("", colorTheme.whiteDark15, Color.BLACK, 50);
        btnPin = new Round("", colorTheme.whiteDark15, Color.BLACK, 50);
        btnCreateGroup = new Round("", colorTheme.whiteDark15, Color.BLACK, 50);
        btnTemporaryChat = new Round("", colorTheme.whiteDark15, Color.BLACK, 50);

        btnChatCustom = new Round("", colorTheme.transparent, Color.BLACK, 20);
        btnCustomTheme = new Round("", colorTheme.transparent, Color.BLACK, 20);
        btnCustomImoji = new Round("", colorTheme.transparent, Color.BLACK, 20);
        btnStorage = new Round("", colorTheme.transparent, Color.BLACK, 20);
        btnDeleteChat = new Round("", colorTheme.transparent, Color.BLACK, 20);
        btnAVT = new Round("", colorTheme.transparent, Color.BLACK, 50);
        btnDeleteGroup = new Round("Xóa nhóm", colorTheme.transparent, Color.BLACK, 20);

        btnMouseListener(btnChangeNickname);
        btnMouseListener(btnNotification);
        btnMouseListener(btnPin);
        btnMouseListener(btnCreateGroup);
        btnMouseListener(btnTemporaryChat);
        btnMouseListener(btnDeleteGroup);

        btnActionUnder(btnChatCustom);
        btnActionUnder(btnCustomTheme);
        btnActionUnder(btnCustomImoji);
        btnActionUnder(btnStorage);
        btnActionUnder(btnDeleteChat);
        btnActionUnder(btnDeleteGroup);
        btnDeleteChat.setIcon(new ImageIcon(imgDelete));
        btnDeleteGroup.setIcon(new ImageIcon(imgDelete));
        btnStorage.setIconTextGap(80);
        btnDeleteChat.setIconTextGap(110);
        btnDeleteGroup.setIconTextGap(180);

        btnChangeNickname.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dialogChangeNickname(lbName.getText(), theme);
            }
        });
        btnCreateGroup.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                getFriends();
                if (isMes) {
                    showDialogCreateGroup();
                } else {
                    showDialogAddToGroup();
                }
            }
        });
        btnCustomTheme.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
        });
        btnDeleteChat.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Tạo panel chứa nội dung + 2 nút
                JPanel panel = new JPanel(new BorderLayout(10, 10));
                JLabel label = new JLabel("Bạn muốn xoá đoạn chat này:");
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                panel.add(label, BorderLayout.NORTH);

                // Tùy chọn nút
                Object[] options = {"Chỉ xoá phía bạn", "Xoá cả hai phía", "Huỷ"};
                int result = JOptionPane.showOptionDialog(
                        null,
                        panel,
                        "Xác nhận xoá đoạn chat",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                int option = -1;
                if (result == JOptionPane.YES_OPTION) {
                    option = 0; // chỉ xoá phía bạn
                } else if (result == JOptionPane.NO_OPTION) {
                    option = 2; // xoá cả 2 phía
                }

                // Gọi hàm nếu chọn hợp lệ
                if (option != -1) {
                    deleteChat(ID_ME, User_RE.getId(), isMes, option);
                }
                themeChange.deleteChatReload();
                repaint();
                revalidate();
            }
        });


        changeTheme(theme);
        changeLanguage(lang);

        add(lbInforTitle);
        add(lbName);
        add(btnChangeNickname);
        add(btnNotification);
        add(btnPin);
        add(btnCreateGroup);
        add(btnTemporaryChat);
        add(lbNotification);
        add(lbCreateGroup);
        add(lbTemporaryChat);
        add(lbPin);
        add(btnChatCustom);
        add(btnCustomTheme);
        add(btnCustomImoji);
        add(btnStorage);
        add(btnDeleteChat);
        add(btnAVT);
        add(btnDeleteGroup);
    }

    public void updateUserCur(String name, User user, boolean isMes, byte[] avtRE) {
        lbName.setText(name);
        this.User_RE = user;
        this.isMes = isMes;
        this.avt_RE = avtRE;

        Image imgAVT = Function.resizeImage(new ImageIcon(this.avt_RE).getImage(), 80, 80);
        btnAVT.removeAll();
        btnAVT.setIcon(new ImageIcon(imgAVT));
        btnAVT.repaint();

        btnDeleteGroup.setVisible(false);

        repaint();
        revalidate();
    }

    public void updateUserCur(String name, Group group, boolean isMes, byte[] avtRE) {
        lbName.setText(name);
        this.Group_RE = group;
        this.isMes = isMes;
        this.avt_RE = avtRE;

        Image imgAVT = Function.resizeImage(new ImageIcon(this.avt_RE).getImage(), 80, 80);
        btnAVT.removeAll();
        btnAVT.setIcon(new ImageIcon(imgAVT));
        btnAVT.repaint();

        btnDeleteGroup.setVisible(!isMes && group.getCreateBy() == ID_ME);

        repaint();
        revalidate();
    }

    private void deleteChat(int ID_ME, int ID_RE, boolean isMes, int option) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            if (isMes) {
                if (option == 0) {
                    // ❗ Chỉ xóa 1 chiều: những tin nhắn do ID_ME gửi cho ID_RE
                    // Bỏ cảm xúc liên quan
                    List<Mes> mesList = session.createQuery("""
                    FROM Mes WHERE id_sender_fkmes = :me AND id_receiver_fkmes = :re
                """, Mes.class)
                            .setParameter("me", ID_ME)
                            .setParameter("re", ID_RE)
                            .getResultList();

                    for (Mes mes : mesList) {
                        List<MesImoji> imojis = session.createQuery(
                                        "FROM MesImoji WHERE mes.id = :idMes", MesImoji.class)
                                .setParameter("idMes", mes.getId())
                                .getResultList();
                        for (MesImoji imoji : imojis) {
                            session.remove(imoji);
                        }
                        session.remove(mes);
                    }

                } else if (option == 2) {
                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "Bạn có chắc muốn xoá toàn bộ đoạn chat này ở cả hai phía?\nHành động này không thể hoàn tác.",
                            "Xác nhận xoá cả 2 phía",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (confirm != JOptionPane.OK_OPTION) {
                        session.getTransaction().rollback();
                        return;
                    }

                    // Lấy tất cả tin nhắn 2 chiều
                    List<Mes> mesList = session.createQuery("""
                    FROM Mes WHERE 
                    (id_sender_fkmes = :me AND id_receiver_fkmes = :re)
                    OR 
                    (id_sender_fkmes = :re AND id_receiver_fkmes = :me)
                """, Mes.class)
                            .setParameter("me", ID_ME)
                            .setParameter("re", ID_RE)
                            .getResultList();

                    for (Mes mes : mesList) {
                        List<MesImoji> imojis = session.createQuery(
                                        "FROM MesImoji WHERE mes.id = :idMes", MesImoji.class)
                                .setParameter("idMes", mes.getId())
                                .getResultList();
                        for (MesImoji imoji : imojis) {
                            session.remove(imoji);
                        }
                        session.remove(mes);
                    }
                }
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException("❌ Xoá chat thất bại", e);
        } finally {
            session.close();
        }
    }


    private void showDialogAddToGroup() {
        dialogCreateGroup = new JDialog((Frame) null, language.getString("moreInfor.DialogAddToGroup.Title"), true);
        dialogCreateGroup.setSize(500, 800);
        dialogCreateGroup.setLocationRelativeTo(null);
        dialogCreateGroup.setLayout(new BorderLayout(10, 10));

        tfname = new JTextField();
        tfname.setBorder(BorderFactory.createTitledBorder("Nhập tên"));

        List<User> listFriends = getFriends();

        DefaultListModel<User> friendModel = new DefaultListModel<>();
        listFriends.forEach(friendModel::addElement);

        JList<User> listFriend = new JList<>(friendModel);
        listFriend.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listFriend.setCellRenderer(new UserCellRenderer());

        JScrollPane scrollFriends = new JScrollPane(listFriend);
        scrollFriends.setBorder(BorderFactory.createTitledBorder("Danh sách bạn bè"));

        // Model & JList thành viên đã chọn
        List<User> members = getMemberInGroup(Group_RE); // TRẢ VỀ List<User>
        DefaultListModel<User> selectedModel = new DefaultListModel<>();
        members.forEach(selectedModel::addElement);

        JList<User> listSelected = new JList<>(selectedModel);
        listSelected.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listSelected.setCellRenderer(new UserCellRenderer());
        JScrollPane scrollSelected = new JScrollPane(listSelected);
        scrollSelected.setBorder(BorderFactory.createTitledBorder("Thành viên nhóm"));

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.add(scrollFriends);
        centerPanel.add(scrollSelected);

        // Lọc khi nhập
        tfname.getDocument().addDocumentListener(new DocumentListener() {
            private void filter() {
                String keyword = tfname.getText().trim().toLowerCase();
                friendModel.clear();
                for (User u : listFriends) {
                    if (u.getName().toLowerCase().contains(keyword)) {
                        friendModel.addElement(u);
                    }
                }
            }

            public void insertUpdate(DocumentEvent e) { filter(); }
            public void removeUpdate(DocumentEvent e) { filter(); }
            public void changedUpdate(DocumentEvent e) { filter(); }
        });

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnOk = new JButton(language.getString("moreInfor.DialogAddToGroup.btnAdd"));
        JButton btnCancel = new JButton(language.getString("moreInfor.DialogAddToGroup.btnCancel"));

        btnOk.addActionListener(e -> {
            List<User> membersChoice = Collections.list(friendModel.elements());
            if (membersChoice.isEmpty()) {
                JOptionPane.showMessageDialog(dialogCreateGroup, "Chưa chọn thành viên nào.");
                return;
            }
            // Lưu nhóm hoặc xử lý
            List<Integer> ids = membersChoice.stream().map(User::getId).toList();
            System.out.println("Thêm thành viên vào nhóm: " + ids);
            insertGroup(sessionFactory, Group_RE.getId(), ids);
            dialogCreateGroup.dispose();
        });

        btnCancel.addActionListener(e -> dialogCreateGroup.dispose());

        bottomPanel.add(btnOk);
        bottomPanel.add(btnCancel);

        dialogCreateGroup.add(tfname, BorderLayout.NORTH);
        dialogCreateGroup.add(centerPanel, BorderLayout.CENTER);
        dialogCreateGroup.add(bottomPanel, BorderLayout.SOUTH);
        dialogCreateGroup.setVisible(true);
    }

    private void showDialogCreateGroup() {
        dialogCreateGroup = new JDialog((Frame) null, language.getString("moreInfor.DialogCreateGroup.Title"), true);
        dialogCreateGroup.setSize(400, 600);
        dialogCreateGroup.setLocationRelativeTo(null);
        dialogCreateGroup.setLayout(new BorderLayout(10, 10));

        // Lấy danh sách bạn
        List<User> allFriends = getFriends(); // List<User>

        // Panel nhập tên nhóm và tìm kiếm
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JPanel groupNamePanel = new JPanel(new BorderLayout());
        groupNamePanel.setBorder(BorderFactory.createTitledBorder(language.getString("moreInfor.DialogCreateGroup.GrName")));
        tfGroupName = new JTextField();
        groupNamePanel.add(tfGroupName, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder(language.getString("moreInfor.DialogAddToGroup.FindTitle")));
        tfname = new JTextField();
        searchPanel.add(tfname, BorderLayout.CENTER);

        topPanel.add(groupNamePanel);
        topPanel.add(searchPanel);

        // Model & JList<User>
        DefaultListModel<User> listModelUser = new DefaultListModel<>();
        allFriends.forEach(listModelUser::addElement);

        JList<User> listUser = new JList<>(listModelUser);
        listUser.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                User user = (User) value;
                JLabel label = (JLabel) super.getListCellRendererComponent(list,
                        user.getName(), index, isSelected, cellHasFocus);
                ImageIcon icon = new ImageIcon(Function.resizeImage(new ImageIcon(user.getAvatarImg()).getImage(), 30, 30));
                label.setIcon(icon);
                label.setIconTextGap(10);
                return label;
            }
        });
        listUser.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listUser);
        scrollPane.setBorder(BorderFactory.createTitledBorder(language.getString("moreInfor.DialogAddToGroup.ListFri")));

        // Lọc theo tên
        tfname.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void filterList() {
                String filter = tfname.getText().trim().toLowerCase();
                listModelUser.clear();
                for (User user : allFriends) {
                    if (user.getName().toLowerCase().contains(filter)) {
                        listModelUser.addElement(user);
                    }
                }
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterList(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterList(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterList(); }
        });

        // Nút
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCreate = new JButton(language.getString("moreInfor.DialogAddToGroup.btnAdd"));
        JButton btnCancel = new JButton(language.getString("moreInfor.DialogAddToGroup.btnCancel"));

        btnCreate.addActionListener(e -> {
            String groupName = tfGroupName.getText().trim();
            List<User> selectedUsers = listUser.getSelectedValuesList();

            if (groupName.isEmpty()) {
                JOptionPane.showMessageDialog(dialogCreateGroup, language.getString("moreInfor.DialogCreateGroup.NotiNameGrEmpty"));
            } else if (selectedUsers.isEmpty()) {
                JOptionPane.showMessageDialog(dialogCreateGroup, language.getString("moreInfor.DialogAddToGroup.NotiChoicePer"));
            } else {
                List<Integer> selectedIds = selectedUsers.stream().map(User::getId).toList();
                createGroup(sessionFactory, groupName, ID_ME, User_RE.getId(), selectedIds);
                System.out.println("Tạo nhóm: " + groupName);
                System.out.println("Thành viên: " + selectedUsers);
                dialogCreateGroup.dispose();
            }
        });

        btnCancel.addActionListener(e -> dialogCreateGroup.dispose());

        bottomPanel.add(btnCreate);
        bottomPanel.add(btnCancel);

        dialogCreateGroup.add(topPanel, BorderLayout.NORTH);
        dialogCreateGroup.add(scrollPane, BorderLayout.CENTER);
        dialogCreateGroup.add(bottomPanel, BorderLayout.SOUTH);

        dialogCreateGroup.setVisible(true);
    }


    private List<User> getFriends() {
        Session session = sessionFactory.openSession();
        List<User> listFriends;

        try {
            session.beginTransaction();


            if (isMes) {
                listFriends = session.createQuery(
                                "FROM User u WHERE u.id != :id AND u.id != :id_re", User.class)
                        .setParameter("id", ID_ME)
                        .setParameter("id_re", User_RE.getId())
                        .getResultList();
            } else {
//                result = session.createQuery("""
//                        SELECT u.id, u.name
//                        FROM User u
//                        WHERE u.id NOT IN (
//                            SELECT gm.userId FROM GroupMember gm WHERE gm.groupId = :groupId
//                        ) AND u.id != :id
//                        """, Object[].class)
//                            .setParameter("groupId", User_RE.getId())
//                            .setParameter("id", ID_ME)
//                            .getResultList();
                listFriends = session.createQuery("""
                FROM User u
                WHERE u.id IN (
                    SELECT f.ID_FRIEND FROM Friend f WHERE f.ID_USER_FK = :myId
                    UNION
                    SELECT f.ID_USER_FK FROM Friend f WHERE f.ID_FRIEND = :myId
                )
                AND u.id NOT IN (
                    SELECT gm.userId FROM GroupMember gm WHERE gm.groupId = :groupId
                )
                """, User.class)
                        .setParameter("myId", ID_ME)
                        .setParameter("groupId", Group_RE.getId())
                        .getResultList();

            }

            session.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return listFriends;
    }
    
    private List<User> getMemberInGroup(Group group) {
        Session session = sessionFactory.openSession();
        List<User> listMember;
        
        try {
            session.beginTransaction();

            listMember = session.createQuery(
                            "FROM User u WHERE u.id IN (SELECT gm.userId FROM GroupMember gm WHERE gm.groupId = :groupId)",
                            User.class)
                    .setParameter("groupId", group.getId())
                    .getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException("Failed to get group members", e);
        } finally {
            session.close();
        }
        return listMember;
    }

    private void createGroup(SessionFactory sessionFactory, String name_group, int id_create, int id_re, List<Integer> list) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            Group group = new Group();
            group.setGroupName(name_group);
            group.setCreateBy(id_create);
            group.setGroupAvtUrl(Function.convertFileToBytes(new File(PATHICON.getPath("AVTGRDEF.png"))));

            session.persist(group);

            GroupMember groupMember = new GroupMember();
            groupMember.setGroupId(group.getId());
            groupMember.setUserId(id_create);

            session.persist(groupMember);

            GroupMember groupMemberRe = new GroupMember();
            groupMemberRe.setGroupId(group.getId());
            groupMemberRe.setUserId(id_re);

            session.persist(groupMemberRe);

            for (Integer id : list) {
                GroupMember groupmem = new GroupMember();
                groupmem.setGroupId(group.getId());
                groupmem.setUserId(id);

                session.persist(groupmem);
            }

            session.getTransaction().commit();
            JOptionPane.showMessageDialog(null, group.getGroupName() + " has been created");
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
    private void insertGroup(SessionFactory sessionFactory, int id_group, List<Integer> list) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            for (Integer id : list) {
                GroupMember groupmem = new GroupMember();
                groupmem.setGroupId(id_group);
                groupmem.setUserId(id);

                session.persist(groupmem);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    public void changeTheme(String theme) {
        this.theme = theme;
        if (theme.equals("light")) {
            lbInforTitle.setForeground(Color.BLACK);
            btnChangeNickname.setIcon(new ImageIcon(imgChangeNickNameLight));
            btnNotification.setIcon(new ImageIcon(imgNotificationLight));
            btnPin.setIcon(new ImageIcon(imgPinLight));
            btnCreateGroup.setIcon(new ImageIcon(imgCreateGroupLight));
            btnTemporaryChat.setIcon(new ImageIcon(imgTemporaryChatLight));
            btnChangeNickname.setBackground(colorTheme.whiteDark15);
            btnNotification.setBackground(colorTheme.whiteDark15);
            btnPin.setBackground(colorTheme.whiteDark15);
            btnCreateGroup.setBackground(colorTheme.whiteDark15);
            btnTemporaryChat.setBackground(colorTheme.whiteDark15);
            lbName.setForeground(Color.BLACK);
            lbNotification.setForeground(Color.BLACK);
            lbCreateGroup.setForeground(Color.BLACK);
            lbTemporaryChat.setForeground(Color.BLACK);
            lbPin.setForeground(Color.BLACK);
            btnCustomTheme.setForeground(Color.BLACK);
            btnCustomImoji.setForeground(Color.BLACK);
            btnStorage.setForeground(Color.BLACK);
            btnDeleteChat.setForeground(Color.BLACK);
            btnStorage.setIcon(new ImageIcon(imgStorageLight));
        } else {
            lbInforTitle.setForeground(Color.WHITE);
            btnChangeNickname.setIcon(new ImageIcon(imgChangeNickNameDark));
            btnNotification.setIcon(new ImageIcon(imgNotificationDark));
            btnPin.setIcon(new ImageIcon(imgPinDark));
            btnCreateGroup.setIcon(new ImageIcon(imgCreateGroupDark));
            btnTemporaryChat.setIcon(new ImageIcon(imgTemporaryChatDark));
            btnChangeNickname.setBackground(colorTheme.blackLight25);
            btnNotification.setBackground(colorTheme.blackLight25);
            btnPin.setBackground(colorTheme.blackLight25);
            btnCreateGroup.setBackground(colorTheme.blackLight25);
            btnTemporaryChat.setBackground(colorTheme.blackLight25);
            lbName.setForeground(Color.WHITE);
            lbNotification.setForeground(Color.WHITE);
            lbCreateGroup.setForeground(Color.WHITE);
            lbTemporaryChat.setForeground(Color.WHITE);
            lbPin.setForeground(Color.WHITE);
            btnCustomTheme.setForeground(Color.WHITE);
            btnCustomImoji.setForeground(Color.WHITE);
            btnStorage.setForeground(Color.WHITE);
            btnDeleteChat.setForeground(Color.WHITE);
            btnStorage.setIcon(new ImageIcon(imgStorageDark));
        }
        repaint();
        revalidate();
    }

    public void changeLanguage(String langu) {
        this.lang = langu;
        language.changeLanguage(this.lang);

        lbInforTitle.setText(language.getString("moreInfor.lbInforTitle"));
        lbNotification.setText("<html><div style='text-align: center;'>" + language.getString("moreInfor.lbNotification") + "</div></html>");
        lbPin.setText("<html><div style='text-align: center;'>" + language.getString("moreInfor.lbPin") + "</div></html>");
        lbCreateGroup.setText("<html><div style='text-align: center;'>" + language.getString("moreInfor.lbCreateGroup") + "</div></html>");
        lbTemporaryChat.setText("<html><div style='text-align: center;'>" + language.getString("moreInfor.lbTemporaryChat") + "</div></html>");

        btnChatCustom.setText(language.getString("moreInfor.btnChatCustom"));
        btnCustomTheme.setText(language.getString("moreInfor.btnCustomTheme"));
        btnCustomImoji.setText(language.getString("moreInfor.btnCustomImoji"));
        btnStorage.setText(language.getString("moreInfor.btnStorage"));
        btnDeleteChat.setText(language.getString("moreInfor.btnDeleteChat"));

        repaint();
        revalidate();
    }

    private void btnMouseListener(JButton btn) {
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (theme.equals("light")) {
                    btn.setBackground(colorTheme.whiteDark35);
                } else {
                    btn.setBackground(colorTheme.blackLight35);
                }
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                if (theme.equals("light")) {
                    btn.setBackground(colorTheme.whiteDark15);
                } else {
                    btn.setBackground(colorTheme.blackLight25);
                }
                btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    private void btnActionUnder(JButton btn) {
        btn.setHorizontalTextPosition(SwingConstants.LEFT);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(new Font(fontName, Font.PLAIN, 18));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (theme.equals("light")) {
                    btn.setBackground(colorTheme.whiteDark35);
                } else {
                    btn.setBackground(colorTheme.blackLight35);
                }
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(colorTheme.transparent);
                btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    private void dialogChangeNickname(String oldName, String themeD) {
        JDialog dialog = new JDialog((Frame) null, "", true);
        JLabel lbNoti = new JLabel();
        JTextField tfNewName = new TextFieldRound("", colorTheme.whiteDark15, Color.BLACK, 10);
        JButton btnDone = new Round("Xác nhận", colorTheme.purpleLight, Color.WHITE, 10);
        JButton btnCancel = new Round("Hủy", colorTheme.whiteDark25, Color.WHITE, 10);

        if (isMes) {
            dialog.setTitle("Đặt biệt danh");
            lbNoti.setText("Đổi biệt danh cho " + oldName);
        } else {
            dialog.setTitle("Đổi tên nhóm");
            lbNoti.setText("Đổi tên cho cho " + oldName);
        }

        dialog.setLayout(null);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(null);

        lbNoti.setOpaque(false);
        lbNoti.setFont(new Font(fontName, Font.BOLD, 16));

        if (themeD.equals("light")) {
            dialog.getContentPane().setBackground(colorTheme.whiteLight);
            lbNoti.setForeground(Color.BLACK);
            tfNewName.setForeground(Color.BLACK);
            tfNewName.setBackground(colorTheme.whiteDark15);
            btnDone.setBackground(colorTheme.purpleLight);
            btnCancel.setBackground(colorTheme.whiteDark25);
            btnCancel.setForeground(Color.BLACK);
        } else {
            dialog.getContentPane().setBackground(colorTheme.blackLight5);
            lbNoti.setForeground(Color.WHITE);
            tfNewName.setForeground(Color.WHITE);
            tfNewName.setBackground(colorTheme.blackLight25);
            btnDone.setBackground(colorTheme.purpleDark);
            btnCancel.setBackground(colorTheme.blackLight15);
            btnCancel.setForeground(Color.WHITE);
        }

        lbNoti.setBounds(30, 20, 330, 30);
        tfNewName.setBounds(30, 70, 330, 30);
        btnDone.setBounds(dialog.getWidth() / 2, 110, 150, 40);
        btnCancel.setBounds(dialog.getWidth() / 2 - 160, 110, 150, 40);

        btnDone.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!tfNewName.getText().isEmpty()) {
                    if (isMes) {
//                        changeNickname(oldName, tfNewName.getText());
                    } else {
                        changeNameGroup(Group_RE, oldName, tfNewName.getText());
                    }
                }
                dialog.dispose();
            }
            public void mouseEntered(MouseEvent e) {
                if (theme.equals("light")) {
                    btnDone.setBackground(colorTheme.purpleLightHover);
                } else {
                    btnDone.setBackground(colorTheme.purpleDarkHover);
                }
            }
            public void mouseExited(MouseEvent e) {
                if (theme.equals("light")) {
                    btnDone.setBackground(colorTheme.purpleLight);
                } else {
                    btnDone.setBackground(colorTheme.purpleDark);
                }
            }
        });
        btnCancel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dialog.dispose();
            }
            public void mouseEntered(MouseEvent e) {
                if (theme.equals("light")) {
                    btnCancel.setBackground(colorTheme.whiteDark15);
                } else {
                    btnCancel.setBackground(colorTheme.blackLight25);
                }
            }
            public void mouseExited(MouseEvent e) {
                if (theme.equals("light")) {
                    btnCancel.setBackground(colorTheme.whiteDark25);
                } else {
                    btnCancel.setBackground(colorTheme.blackLight15);
                }
            }
        });

        dialog.add(lbNoti);
        dialog.add(tfNewName);
        dialog.add(btnDone);
        dialog.add(btnCancel);
        dialog.setVisible(true);
    }

    private void changeNameGroup(Group group, String oldName, String newName) {
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();

            if (group != null && group.getGroupName().equals(oldName)) {
                group.setGroupName(newName);
                session.merge(group);

                JOptionPane.showMessageDialog(null, "Đã thay đổi tên nhóm thành công");
                lbName.setText(newName);
            }
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException("Failed to change name group", e);
        } finally {
            session.close();
        }
    }

    private static class UserCellRenderer extends JPanel implements ListCellRenderer<User> {
        private final JLabel lblAvatar = new JLabel();
        private final JLabel lblName = new JLabel();

        public UserCellRenderer() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            add(lblAvatar);
            add(lblName);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends User> list, User user, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            // Resize avatar
            ImageIcon icon = new ImageIcon(user.getAvatarImg()); // bạn đã có byte[] hoặc path
            Image scaled = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            lblAvatar.setIcon(new ImageIcon(scaled));
            lblName.setText(user.getName());

            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return this;
        }
    }


    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Dimension titleSize = lbInforTitle.getPreferredSize();
        lbInforTitle.setBounds((getWidth() - titleSize.width) / 2, 10, titleSize.width, 40);
        Dimension size = lbName.getPreferredSize();
        lbName.setBounds((getWidth() - size.width) / 2, 190, size.width, 40);
        btnChangeNickname.setBounds(lbName.getX() - 40, lbName.getY() + 5, 28, 28);
        btnPin.setBounds(getWidth() / 2 - 50, lbName.getY() + lbName.getHeight() + 40, 30, 30);
        btnNotification.setBounds(btnPin.getX() - 70, btnPin.getY(), btnPin.getWidth(), btnPin.getHeight());
        btnCreateGroup.setBounds(getWidth() / 2 + 20, btnPin.getY(), btnPin.getWidth(), btnPin.getHeight());
        btnTemporaryChat.setBounds(btnCreateGroup.getX() + 70, btnCreateGroup.getY(), btnCreateGroup.getWidth(), btnCreateGroup.getHeight());

        lbNotification.setBounds(btnNotification.getX() + btnNotification.getWidth() / 2 - 45, btnNotification.getY() + btnNotification.getHeight() + 10, 80, 60);
        lbPin.setBounds(btnPin.getX() + btnPin.getWidth() / 2 - 35, btnPin.getY() + btnPin.getHeight() + 10, 70, 60);
        lbCreateGroup.setBounds(btnCreateGroup.getX() + btnCreateGroup.getWidth() / 2 - 35, btnCreateGroup.getY() + btnCreateGroup.getHeight() + 10, 70, 60);
        lbTemporaryChat.setBounds(btnTemporaryChat.getX() + btnTemporaryChat.getWidth() / 2 - 35, btnTemporaryChat.getY() + btnTemporaryChat.getHeight() + 10, 75, 60);

        btnCustomTheme.setBounds(10, lbNotification.getY() + lbNotification.getHeight() + 30, getWidth()- 20, 40);
        btnCustomImoji.setBounds(btnCustomTheme.getX(), btnCustomTheme.getY() + btnCustomTheme.getHeight(), btnCustomTheme.getWidth(), btnCustomTheme.getHeight());
        btnStorage.setBounds(btnCustomImoji.getX(), btnCustomImoji.getY() + btnCustomImoji.getHeight() + 15, btnCustomImoji.getWidth(), btnCustomImoji.getHeight());
        btnDeleteChat.setBounds(btnStorage.getX(), btnStorage.getY() + btnStorage.getHeight(), btnStorage.getWidth(), btnStorage.getHeight());
        btnDeleteGroup.setBounds(btnDeleteChat.getX(), btnDeleteChat.getY() + btnDeleteChat.getHeight() + 15, btnDeleteChat.getWidth(), btnDeleteChat.getHeight());

        if (theme.equals("light")) {
            g2d.setColor(colorTheme.whiteLight);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            g2d.setColor(colorTheme.whiteDark25);
            g2d.fillRect(0, lbNotification.getY() + lbNotification.getWidth(), getWidth(), 5);
            g2d.fillRect(0, btnCustomImoji.getY() + btnCustomImoji.getHeight() + 5, getWidth(), 5);
        } else {
            g2d.setColor(colorTheme.blackLight5);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            g2d.setColor(colorTheme.blackLight50);
            g2d.fillRect(0, lbNotification.getY() + lbNotification.getWidth(), getWidth(), 5);
            g2d.fillRect(0, btnCustomImoji.getY() + btnCustomImoji.getHeight() + 5, getWidth(), 5);
        }
        g2d.setColor(Color.GRAY);
        btnAVT.setBounds(getWidth() / 2 - 40, 100, 80, 80);
    }
}

