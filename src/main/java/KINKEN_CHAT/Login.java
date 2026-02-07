package KINKEN_CHAT;

import com.toedter.calendar.JDateChooser;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.*;

public class Login extends JPanel {
    JLabel lblog = new JLabel();
    JLabel lbback = new JLabel();
    JLabel lbUsername = new JLabel();
    JLabel lbPassword = new JLabel();
    JTextField tfUsername;
    JPasswordField tfPassword;
    JButton btnlogin;
    JLabel lbIMG;
    JButton btnShow ;
    JLabel lbCreate = new JLabel();
    JLabel lbForget = new JLabel();
    JLabel lbHelp = new JLabel();
    JButton btnCreate;
    JButton btnGoogle;
    private String fontName = "Meiryo";
    private Language lang;
    private ColorTheme colorTheme = new ColorTheme();
    private boolean checkShow = false;

    Database db = new Database();

    Image imgLogin = new ImageIcon(PATHICON.getPath("Login.png")).getImage().getScaledInstance(500, 700, Image.SCALE_SMOOTH);
    Image imgshow = new ImageIcon(PATHICON.getPath("show.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    Image imghide = new ImageIcon(PATHICON.getPath("hide.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    Image imgGoogle = new ImageIcon(PATHICON.getPath("logogg.png")).getImage().getScaledInstance(140, 70, Image.SCALE_SMOOTH);
    Image imgback = new ImageIcon(PATHICON.getPath("backlogin.png")).getImage().getScaledInstance(700, 700, Image.SCALE_SMOOTH);

    public Login(String theme, String langquage) {
        setLayout(null);
        setSize(1000, 700);

        lang = new Language(langquage);
        lang.loadLanguage();
        db.connect();

        tfUsername = new TextFieldRound("Email", colorTheme.whiteLight, Color.black, 40);
        tfPassword = new TextFieldPasswordRound(lang.getString("login.lbpass"), colorTheme.whiteLight, Color.BLACK, 40);
        btnlogin = new Round(lang.getString("login.btnlog"), colorTheme.whiteDark15, Color.BLACK, 40);
        btnShow = new Round(lang.getString("login.btnShow"), colorTheme.transparent, Color.BLACK, 40);
        btnCreate = new Round(lang.getString("login.create"), colorTheme.whiteDark15, Color.BLACK, 40);
        btnGoogle = new Round("", colorTheme.whiteDark15, Color.BLACK, 40);

        lbback.setIcon(new ImageIcon(imgback));
        lbback.setBounds(0, 0, 700, 700);
        lbIMG = new JLabel(new ImageIcon(imgLogin));
        lbIMG.setBounds(500, 0, 500, 700);
        lblog.setText(lang.getString("login.lblog"));
        lblog.setFont(new Font(fontName, Font.PLAIN, 40));
        lblog.setHorizontalAlignment(SwingConstants.CENTER);
        lblog.setForeground(Color.WHITE);
        Dimension logSize = lblog.getPreferredSize();
        lblog.setBounds((lbIMG.getX() + (lbIMG.getWidth() / 2) - (logSize.width / 2)), 200, logSize.width, logSize.height);

        int place = 10;
        tfUsername.setBounds(lbIMG.getX() + 60, lblog.getY() + 90, 400, 40);
        tfUsername.setFont(new Font(fontName, Font.PLAIN, 20));
        tfPassword.setBounds(tfUsername.getX(), tfUsername.getY() + tfUsername.getHeight() + place, 400, 40);
        tfPassword.setFont(new Font(fontName, Font.PLAIN, 20));
        btnlogin.setBounds((lbIMG.getX() + (lbIMG.getWidth() / 2) - 100), tfPassword.getY() + 80, 200, 40);
        btnlogin.setFont(new Font(fontName, Font.PLAIN, 20));
        btnShow.setIcon(new ImageIcon(imgshow));
        btnShow.setBounds(tfPassword.getX() + 350, tfPassword.getY(), 40, 40);
        btnShow.setOpaque(false);
        lbForget.setText(lang.getString("login.forget"));
        lbForget.setFont(new Font(fontName, Font.PLAIN, 12));
        lbForget.setForeground(Color.WHITE);
        lbForget.setBounds(tfPassword.getX() + 20, tfPassword.getY() + 30, 150, 40);
        btnCreate.setBounds(tfPassword.getX(), btnlogin.getY() + 80, 190, 40);
        btnCreate.setFont(new Font(fontName, Font.PLAIN, 20));
        btnGoogle.setBounds(btnCreate.getX() + 210, btnCreate.getY(), 190, 40);
        btnGoogle.setIcon(new ImageIcon(imgGoogle));
        lbHelp.setText(lang.getString("login.help"));
        lbHelp.setFont(new Font(fontName, Font.PLAIN, 12));
        lbHelp.setForeground(Color.WHITE);
        lbHelp.setBounds(tfPassword.getX() + 340, btnGoogle.getY() + 50, 200, 40);

        btnShow.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!checkShow) {
                    btnShow.setIcon(new ImageIcon(imghide));
                    checkShow = true;
                    tfPassword.setEchoChar((char) 0);
                } else {
                    btnShow.setIcon(new ImageIcon(imgshow));
                    checkShow = false;
                    tfPassword.setEchoChar('●');
                }
            }
            public void mouseEntered(MouseEvent e) {
                btnShow.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnShow.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        btnlogin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
            public void mouseEntered(MouseEvent e) {
                btnlogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnlogin.setBackground(colorTheme.whiteLight);
            }
            public void mouseExited(MouseEvent e) {
                btnlogin.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnlogin.setBackground(colorTheme.whiteDark15);
            }
        });
        btnCreate.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
            public void mouseEntered(MouseEvent e) {
                btnCreate.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnCreate.setBackground(colorTheme.whiteLight);
            }
            public void mouseExited(MouseEvent e) {
                btnCreate.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnCreate.setBackground(colorTheme.whiteDark15);
            }
        });
        btnGoogle.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
            public void mouseEntered(MouseEvent e) {
                btnGoogle.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnGoogle.setBackground(colorTheme.whiteLight);
            }
            public void mouseExited(MouseEvent e) {
                btnGoogle.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnGoogle.setBackground(colorTheme.whiteDark15);
            }
        });

        lbCreate.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
            public void mouseEntered(MouseEvent e) {
                lbCreate.setCursor(new Cursor(Cursor.HAND_CURSOR));
                lbCreate.setForeground(new Color(66, 202, 236));
                lbCreate.setText("<html><u>" +lang.getString("login.create") + "</u></html>");
            }
            public void mouseExited(MouseEvent e) {
                lbCreate.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                lbCreate.setForeground(colorTheme.whiteLight);
                lbCreate.setText("<html>" +lang.getString("login.create") + "</html>");
            }
        });
        lbForget.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                forgotPassword();
            }
            public void mouseEntered(MouseEvent e) {
                lbForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
                lbForget.setForeground(new Color(66, 202, 236));
                lbForget.setText("<html><u>" +lang.getString("login.forget") + "</u></html>");
            }
            public void mouseExited(MouseEvent e) {
                lbForget.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                lbForget.setForeground(colorTheme.whiteLight);
                lbForget.setText("<html>" +lang.getString("login.forget") + "</html>");
            }
        });
        lbHelp.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    URI uri = new URI(lang.getString("https://www.facebook.com/tvbang.1811"));
                    desktop.browse(uri);
                } catch (URISyntaxException | IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            public void mouseEntered(MouseEvent e) {
                lbHelp.setCursor(new Cursor(Cursor.HAND_CURSOR));
                lbHelp.setForeground(new Color(66, 202, 236));
                lbHelp.setText("<html><u>" +lang.getString("login.help") + "</u></html>");
            }
            public void mouseExited(MouseEvent e) {
                lbHelp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                lbHelp.setForeground(colorTheme.whiteLight);
                lbHelp.setText("<html>" +lang.getString("login.help") + "</html>");
            }
        });

        add(lblog);
        add(lbUsername);
        add(tfUsername);
        add(lbPassword);
        add(btnShow);
        add(lbForget);
        add(lbHelp);
        add(tfPassword);
        add(btnlogin);
        add(btnCreate);
        add(btnGoogle);
        add(lbIMG);
        add(lbback);

        setVisible(true);
    }

    public static boolean check(String pass, String pass2) {
        return BCrypt.checkpw(pass, pass2);
    }

    public boolean checklogin() {
        ResultSet rs = db.getDB("SELECT MAIL, PASS FROM user");

        try {
            while (rs.next()) {
                String mail = rs.getString("MAIL");
                String password = rs.getString("PASS");
                if (mail.equals(tfUsername.getText()) && check(new String(tfPassword.getPassword()), password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private void forgotPassword() {
        String mail = tfUsername.getText();
        JTextField emailField = new TextFieldRound(mail, colorTheme.whiteLight, Color.black, 20);
        emailField.setText(mail);
        Object[] message = {
                "Enter your mail address:", emailField
        };

        int option = JOptionPane.showConfirmDialog(
                null, message, "Confirm Email",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        );
        String codeSend = CreateAccount.generateCode();
        String email;
        if (option == JOptionPane.YES_OPTION) {
            email = emailField.getText();

            if (email == null || email.trim().isEmpty() || !email.contains("@")) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập địa chỉ email hợp lệ");
                return;
            }

            try {
                CreateAccount.sendEmail(email, codeSend);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            String optionCode = JOptionPane.showInputDialog("Mã xác nhận");

            if (optionCode != null) {
                if (optionCode.equals(codeSend)) {
                    String newPass = JOptionPane.showInputDialog("Nhập mật khẩu mới");

                    if (newPass != null) {
                        updatePassword(email, newPass);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Mã xác nhận không đúng");
                }
            }
        }
    }

    private static String hashWithBCrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    private void updatePassword(String mail, String pass) {
        try {
            int resurt = db.executeDB("UPDATE User SET PASS = '" +hashWithBCrypt(pass) +"' WHERE MAIL = '" + mail + "'");

            if (resurt > 0) {
                JOptionPane.showMessageDialog(null, "Đổi mật khẩu thành công");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

class CreateAccount extends JPanel {
    JLabel lbIMG;
    JLabel lbBack = new JLabel();
    JLabel lbCreate = new JLabel();
    JLabel lbEmail = new JLabel();
    JTextField tfEmail;
    JPasswordField tfPassword;
    JPasswordField tfRePassword;
    JTextField tfCode;
    JButton btnSendCode;
    JButton btnCreate;
    JButton btnShow1;
    JButton btnShow2;
    JButton btnGoogle;
    JLabel lbor = new JLabel();
    JLabel lbNext = new JLabel();
    JLabel lblogin = new JLabel();
    private String fontName = "Meiryo";
    private Language lang;
    private ColorTheme colorTheme = new ColorTheme();
    private boolean checkShow = false;

    Database db = new Database();

    private static final Map<String, String> verificationCodes = new ConcurrentHashMap<>();
    private static final Map<String, ScheduledFuture<?>> expiryTasks = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    Image imgLogin = new ImageIcon(PATHICON.getPath("Login.png")).getImage().getScaledInstance(500, 700, Image.SCALE_SMOOTH);
    Image imgshow = new ImageIcon(PATHICON.getPath("show.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    Image imghide = new ImageIcon(PATHICON.getPath("hide.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
    Image imgGoogle = new ImageIcon(PATHICON.getPath("logogg.png")).getImage().getScaledInstance(140, 70, Image.SCALE_SMOOTH);
    Image imgback = new ImageIcon(PATHICON.getPath("backlogin.png")).getImage().getScaledInstance(700, 700, Image.SCALE_SMOOTH);

    public CreateAccount(String theme, String language) {
        setLayout(null);
        setSize(1000, 700);

        lang = new Language(language);
        lang.loadLanguage();
        db.connect();

        tfEmail = new TextFieldRound("Email", colorTheme.whiteLight, Color.black, 40);
        tfPassword = new TextFieldPasswordRound(lang.getString("create.tfPassword"), colorTheme.whiteLight, Color.black, 40);
        tfRePassword = new TextFieldPasswordRound(lang.getString("create.tfRePassword"), colorTheme.whiteLight, Color.black, 40);
        tfCode = new TextFieldRound(lang.getString("create.tfCode"), colorTheme.whiteLight, Color.black, 40);
        btnSendCode = new Round(lang.getString("create.btnSendCode"), colorTheme.whiteDark15, Color.BLACK, 40);
        btnCreate = new Round(lang.getString("create.btnCreateAcc"), colorTheme.whiteDark15, Color.BLACK, 40);
        btnShow1 = new Round("", colorTheme.transparent, Color.BLACK, 40);
        btnShow2 = new Round("", colorTheme.transparent, Color.BLACK, 40);
        btnGoogle = new Round("", colorTheme.whiteDark15, Color.BLACK, 40);

        int place = 10;
        lbIMG = new JLabel(new ImageIcon(imgLogin));
        lbIMG.setBounds(0, 0, 500, 700);
        lbBack.setIcon(new ImageIcon(imgback));
        lbBack.setBounds(300, 0, 700, 700);
        lbCreate.setText(lang.getString("create.lbCreate"));
        lbCreate.setFont(new Font(fontName, Font.PLAIN, 40));
        lbCreate.setHorizontalAlignment(SwingConstants.CENTER);
        lbCreate.setForeground(Color.WHITE);
        Dimension logSize = lbCreate.getPreferredSize();
        lbCreate.setBounds((lbIMG.getX() + (lbIMG.getWidth() / 2) - (logSize.width / 2)) - 20, 190, logSize.width + 50, logSize.height);
        tfEmail.setBounds(lbIMG.getX() + 60, lbCreate.getY() + 70, 400, 40);
        tfEmail.setFont(new Font(fontName, Font.PLAIN, 20));
        tfPassword.setBounds(tfEmail.getX(), tfEmail.getY() + tfEmail.getHeight() + place, 400, 40);
        tfPassword.setFont(new Font(fontName, Font.PLAIN, 20));
        tfRePassword.setBounds(tfEmail.getX(), tfPassword.getY() + tfPassword.getHeight() + place, 400, 40);
        tfRePassword.setFont(new Font(fontName, Font.PLAIN, 20));
        tfCode.setBounds(tfEmail.getX(), tfRePassword.getY() + tfRePassword.getHeight() + place, 250, 40);
        tfCode.setFont(new Font(fontName, Font.PLAIN, 20));
        btnSendCode.setBounds(tfCode.getX() + tfCode.getWidth() + 10, tfCode.getY(), 140, 40);
        btnSendCode.setFont(new Font(fontName, Font.PLAIN, 20));
        btnCustom(btnSendCode);
        btnShow1.setIcon(new ImageIcon(imgshow));
        btnShow1.setBounds(tfPassword.getX() + 350, tfPassword.getY(), 40, 40);
        btnShow1.setOpaque(false);
        btnShow2.setIcon(new ImageIcon(imgshow));
        btnShow2.setBounds(tfRePassword.getX() + 350, tfRePassword.getY(), 40, 40);
        btnShow2.setOpaque(false);
        btnCreate.setBounds(tfCode.getX(), tfCode.getY() + 100, 180, 40);
        btnCreate.setFont(new Font(fontName, Font.PLAIN, 20));
        btnCustom(btnCreate);
        btnGoogle.setIcon(new ImageIcon(imgGoogle));
        btnGoogle.setBounds(btnCreate.getX() + btnCreate.getWidth() + 40, btnCreate.getY(), 180, 40);
        btnCustom(btnGoogle);
        lbor.setText(lang.getString("create.lbor"));
        lbor.setFont(new Font(fontName, Font.PLAIN, 12));
        lbor.setForeground(Color.WHITE);
        lbor.setHorizontalAlignment(SwingConstants.CENTER);
        lbor.setBounds(btnCreate.getX() + btnCreate.getWidth(), btnCreate.getY(), 40, 40);
        lbor.setOpaque(false);
        lbNext.setText(lang.getString("create.lbNext"));
        lbNext.setFont(new Font(fontName, Font.PLAIN, 12));
        lbNext.setForeground(Color.WHITE);
        lbNext.setHorizontalAlignment(SwingConstants.CENTER);
        lbNext.setBounds(btnGoogle.getX(), btnGoogle.getY() - 25, btnGoogle.getWidth(), 25);
        lbNext.setOpaque(false);
        lblogin.setText(lang.getString("login.lblog"));
        lblogin.setFont(new Font(fontName, Font.PLAIN, 20));
        lblogin.setForeground(Color.WHITE);
        lblogin.setHorizontalAlignment(SwingConstants.CENTER);
        lblogin.setBounds((lbIMG.getX() + (lbIMG.getWidth() / 2) - 100), btnCreate.getY() + 100, 200, 40);
        lblogin.setOpaque(false);

        tfEmail.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (tfEmail.getForeground().equals(Color.RED)) {
                    tfEmail.setForeground(Color.BLACK);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if (tfEmail.getForeground().equals(Color.RED)) {
                    tfEmail.setForeground(Color.BLACK);
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                if (tfEmail.getForeground().equals(Color.RED)) {
                    tfEmail.setForeground(Color.BLACK);
                }
            }
        });

        tfPassword.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!tfEmail.getText().endsWith("@gmail.com")) {
                    tfEmail.setForeground(Color.RED);
                } else tfEmail.setForeground(Color.BLACK);
            }
        });

        btnSendCode.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (Arrays.equals(tfPassword.getPassword(), tfRePassword.getPassword()) && tfEmail.getText().endsWith("@gmail.com")) {
                    String email = tfEmail.getText();
                    String code = generateCode();

                    new Thread(() -> {
                        try {
                            sendEmail(email, code);

                            verificationCodes.put(email, code);
                            JOptionPane.showMessageDialog(null, lang.getString("create.JOSendCode"));

                            // Nếu email đã có task cũ -> huỷ nó
                            ScheduledFuture<?> oldTask = expiryTasks.remove(email);
                            if (oldTask != null) oldTask.cancel(true);

                            // Tự xoá mã sau 3 phút
                            ScheduledFuture<?> expiryTask = scheduler.schedule(() -> {
                                verificationCodes.remove(email);
                                expiryTasks.remove(email);
                            }, 1, TimeUnit.MINUTES);

                            expiryTasks.put(email, expiryTask);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, lang.getString("create.JOSendFail")));
                        }
                    }).start();
                } else JOptionPane.showMessageDialog(null, lang.getString("create.JOMailErrol"));
            }
        });

        btnShow1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!checkShow) {
                    btnShow1.setIcon(new ImageIcon(imghide));
                    checkShow = true;
                    tfPassword.setEchoChar((char) 0);
                } else {
                    btnShow1.setIcon(new ImageIcon(imgshow));
                    checkShow = false;
                    tfPassword.setEchoChar('●');
                }
            }
            public void mouseEntered(MouseEvent e) {
                btnShow1.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnShow1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        btnShow2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!checkShow) {
                    btnShow2.setIcon(new ImageIcon(imghide));
                    checkShow = true;
                    tfRePassword.setEchoChar((char) 0);
                } else {
                    btnShow2.setIcon(new ImageIcon(imgshow));
                    checkShow = false;
                    tfRePassword.setEchoChar('●');
                }
            }
            public void mouseEntered(MouseEvent e) {
                btnShow2.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnShow2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        lblogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                lblogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
                lblogin.setText("<html><u>" + lang.getString("login.lblog") + "</u></html>");
                lblogin.setForeground(new Color(66, 202, 236));
            }
            public void mouseExited(MouseEvent e) {
                lblogin.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                lblogin.setText("<html>" + lang.getString("login.lblog") + "</html>");
                lblogin.setForeground(colorTheme.whiteLight);
            }
        });

        add(btnShow1);
        add(btnShow2);
        add(lbEmail);
        add(tfEmail);
        add(tfPassword);
        add(tfRePassword);
        add(lbCreate);
        add(btnSendCode);
        add(tfCode);
        add(btnCreate);
        add(btnGoogle);
        add(lbor);
        add(lbNext);
        add(lblogin);
        add(lbIMG);
        add(lbBack);
    }

    public boolean check() {
        String email = tfEmail.getText();
        String enteredCode = tfCode.getText();
        String correctCode = verificationCodes.get(email);

        if (correctCode != null && enteredCode.equals(correctCode)) {
            verificationCodes.remove(email);
            ScheduledFuture<?> task = expiryTasks.remove(email);
            if (task != null) task.cancel(true);
            return true;
        } else {
            tfCode.setForeground(Color.RED);
            JOptionPane.showMessageDialog(null, lang.getString("create.JOCodeErrol"));
            return false;
        }
    }

    public static String generateCode() {
        Random rand = new Random();
        int code = 100000 + rand.nextInt(900000);
        return String.valueOf(code);
    }

    public static void sendEmail(String toEmail, String code) throws MessagingException {
        final String fromEmail = "tvbphu2@gmail.com";
        final String password = "gcaq feah xcdx bvnc";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Xác thực từ Kin-Ken");
        message.setText("Mã xác thực của bạn là: " + code + "\nLưu ý: Mã chỉ có hiệu lực trong 1 phút.");

        Transport.send(message);
    }

    private void btnCustom(JButton btn) {
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btn.setBackground(colorTheme.whiteLight);
            }
            public void mouseExited(MouseEvent e) {
                btn.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btn.setBackground(colorTheme.whiteDark15);
            }
        });
    }
}

class CreateFull extends JPanel {
    JLabel lbIMG;
    JLabel lbBack;
    JLabel lbCover = new JLabel();
    JButton btnAVT;
    JTextField tfName;
    JTextField tfBirth;
    JTextField tfNickName;
    JLabel lbSex = new JLabel();
    JRadioButton btnMale = new JRadioButton("Male");
    JRadioButton btnFemale = new JRadioButton("Female");
    JTextArea taBIO;
    JButton btnUpdate;
    JButton btnCalenda;
    JDateChooser dateChooser = new JDateChooser();
    JButton btnBack;
    private String fontName = "Meiryo";
    private Language lang;
    private ColorTheme colorTheme = new ColorTheme();
    private boolean isUpdating = false;
    private boolean isBirFocus = false;
    private String mail;
    private String password;
    private File AVT_IMG;
    private File COVER_IMG;
    private String coverIndex;
    private String avatarIndex;

    public boolean isBirFocus() {
        return isBirFocus;
    }

    public void setBirFocus(boolean birFocus) {
        isBirFocus = birFocus;
    }

    public File getAVT_IMG() {
        return AVT_IMG;
    }

    public void setAVT_IMG(File AVT_IMG) {
        this.AVT_IMG = AVT_IMG;
    }

    public File getCOVER_IMG() {
        return COVER_IMG;
    }

    public void setCOVER_IMG(File COVER_IMG) {
        this.COVER_IMG = COVER_IMG;
    }

    public String getCoverIndex() {
        return coverIndex;
    }

    public void setCoverIndex(String coverIndex) {
        this.coverIndex = coverIndex;
    }

    public String getAvatarIndex() {
        return avatarIndex;
    }

    public void setAvatarIndex(String avatarIndex) {
        this.avatarIndex = avatarIndex;
    }

    public void setAcc(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    Database db = new Database();

    Image imgLogin = new ImageIcon(PATHICON.getPath("Create2.png")).getImage().getScaledInstance(500, 700, Image.SCALE_SMOOTH);
    Image imgCalendar = new ImageIcon(PATHICON.getPath("Calendar.png")).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    Image imgBack = new ImageIcon(PATHICON.getPath("Back.png")).getImage().getScaledInstance(700, 700, Image.SCALE_SMOOTH);

    public CreateFull(String theme, String language) {
        setLayout(null);
        setSize(1000, 700);

        lang = new Language(language);
        lang.loadLanguage();
        db.connect();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        tfName = new TextFieldRound(lang.getString("createFull.tfname"), colorTheme.whiteLight, Color.black, 40);
        tfBirth = new TextFieldRound("dd/mm/yyyy", colorTheme.whiteLight, Color.black, 40);
        tfNickName = new TextFieldRound(lang.getString("createFull.tfNickname"), colorTheme.whiteLight, Color.black, 40);
        btnAVT = new Round("", colorTheme.whiteLight, Color.black, 200);
        btnUpdate = new Round(lang.getString("createFull.btnUpdate"), colorTheme.whiteDark15, Color.black, 40);
        btnCalenda = new Round("", colorTheme.transparent, Color.black, 10);
        btnBack = new Round(lang.getString("createFull.btnBack"), colorTheme.whiteLight, Color.black, 20);

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(btnMale);
        btnGroup.add(btnFemale);

        int place = 10;
        lbIMG = new JLabel(new ImageIcon(imgLogin));
        lbIMG.setBounds(0, 0, 500, 700);
        lbBack = new JLabel(new ImageIcon(imgBack));
        lbBack.setBounds(300, 0, 700, 700);
        lbCover.setOpaque(true);
        lbCover.setBackground(colorTheme.whiteDark15);
        lbCover.setBounds(0, 0, 500, 200);
        btnAVT.setBounds(40, (lbCover.getHeight() - 80), 160, 160);
        tfName.setBounds(lbIMG.getX() + (lbIMG.getWidth() / 2) - 200, btnAVT.getY() + btnAVT.getHeight() + 20, 400, 40);
        tfName.setFont(new Font(fontName, Font.PLAIN, 20));
        tfBirth.setBounds(tfName.getX(), tfName.getY() + tfName.getHeight() + place, 400, 40);
        tfBirth.setFont(new Font(fontName, Font.PLAIN, 20));
        btnCalenda.setIcon(new ImageIcon(imgCalendar));
        btnCalenda.setOpaque(false);
        btnCalenda.setBounds(tfBirth.getX() + 350, tfBirth.getY(), 40, 40);
        tfNickName.setBounds(tfName.getX(), tfBirth.getY() + tfBirth.getHeight() + place, 400, 40);
        tfNickName.setFont(new Font(fontName, Font.PLAIN, 20));
        lbSex.setText(lang.getString("createFull.lbSex"));
        lbSex.setForeground(colorTheme.whiteLight);
        lbSex.setFont(new Font(fontName, Font.PLAIN, 20));
        lbSex.setBounds(tfName.getX() + 20, tfNickName.getY() + tfNickName.getHeight() + place, 170, 20);
        btnMale.setFont(new Font(fontName, Font.PLAIN, 20));
        btnMale.setOpaque(false);
        btnMale.setForeground(colorTheme.whiteLight);
        btnMale.setText(lang.getString("createFull.btnMale"));
        btnMale.setBounds(lbSex.getX() + lbSex.getWidth(), lbSex.getY(), 100, 20);
        btnFemale.setFont(new Font(fontName, Font.PLAIN, 20));
        btnFemale.setText(lang.getString("createFull.btnFemale"));
        btnFemale.setOpaque(false);
        btnFemale.setForeground(colorTheme.whiteLight);
        btnFemale.setBounds(btnMale.getX() + btnMale.getWidth() + 20, lbSex.getY(), 100, 20);
        taBIO = new TextAreaRound(lang.getString("createFull.taBIO"), colorTheme.whiteLight, Color.black, 20);
        taBIO.setFont(new Font(fontName, Font.PLAIN, 20));
        taBIO.setBounds(tfName.getX(), lbSex.getY() + lbSex.getHeight() + place, 400, 120);
        btnUpdate.setBounds(lbIMG.getX() + (lbIMG.getWidth() / 2) - 90, taBIO.getY() + taBIO.getHeight() + place, 180, 40);
        btnUpdate.setFont(new Font(fontName, Font.PLAIN, 20));
        btnBack.setBounds(10, btnUpdate.getY() + btnUpdate.getHeight() - 20, 100, 20);
        btnBack.setFont(new Font(fontName, Font.PLAIN, 12));

        dateChooser.setDateFormatString("dd/MM/yyyy");

        lbCover.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                File file = chooseImageFile();
                if (file != null) {
                    KINKEN_CHAT.ImageCropperDialog cr = new KINKEN_CHAT.ImageCropperDialog(null, file, "R");
                    BufferedImage crop = cr.showDialogAndGetImage();
                    setCoverIndex(cr.getCropRect());
                    if (crop != null) {
                        lbCover.setIcon(new ImageIcon(crop));
                        setCOVER_IMG(file);
                    }

                }
            }
            public void mouseEntered(MouseEvent e) {
                lbCover.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                lbCover.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnAVT.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                File file = chooseImageFile();
                if (file != null) {
                    KINKEN_CHAT.ImageCropperDialog cr = new KINKEN_CHAT.ImageCropperDialog(null, file, "C");
                    BufferedImage crop = cr.showDialogAndGetImage();
                    setAvatarIndex(cr.getCropCircle());
                    if (crop != null) {
                        btnAVT.setIcon(new ImageIcon(crop));
                        setAVT_IMG(file);
                    }

                }
            }
            public void mouseEntered(MouseEvent e) {
                btnAVT.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnAVT.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        tfBirth.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                setBirFocus(true);
            }
        });
        tfBirth.getDocument().addDocumentListener(new DocumentListener() {
            private boolean isUpdating = false;

            @Override public void insertUpdate(DocumentEvent e) {
                if (isBirFocus) {
                    formatText();
                    checkTFB();
                }
            }
            @Override public void removeUpdate(DocumentEvent e) {
                if (isBirFocus) {
                    formatText();
                    checkTFB();
                }
            }
            @Override public void changedUpdate(DocumentEvent e) {}

            private void formatText() {
                if (isUpdating) return;

                isUpdating = true;
                SwingUtilities.invokeLater(() -> {
                    String raw = tfBirth.getText().replaceAll("[^0-9]", "");
                    StringBuilder result = new StringBuilder();

                    for (int i = 0; i < raw.length() && i < 8; i++) {
                        result.append(raw.charAt(i));
                        if (i == 1 || i == 3) result.append('/');
                    }

                    String formatted = result.toString();
                    if (!formatted.equals(tfBirth.getText())) {
                        tfBirth.setText(formatted);
                        tfBirth.setCaretPosition(formatted.length());
                    }
                    isUpdating = false;
                });
            }
        });
        btnCalenda.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setBirFocus(false);
                JPopupMenu popup = new JPopupMenu();
                popup.add(dateChooser);
                popup.show(btnCalenda, 0, btnCalenda.getHeight());

                dateChooser.getDateEditor().addPropertyChangeListener("date", evt -> {
                    Date selectedDate = dateChooser.getDate();
                    if (selectedDate != null) {
                        SwingUtilities.invokeLater(() -> {
                            String formatted = new SimpleDateFormat("dd/MM/yyyy").format(selectedDate);
                            tfBirth.setText(formatted);
                            tfBirth.setForeground(Color.BLACK);
                            tfBirth.setCaretPosition(formatted.length());
                            popup.setVisible(false);
                        });
                    }
                });

            }
            public void mouseEntered(MouseEvent e) {
                btnCalenda.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnCalenda.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnCalenda.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e) {
                btnCalenda.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        btnUpdate.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

            }
            public void mouseEntered(MouseEvent e) {
                btnUpdate.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnUpdate.setBackground(colorTheme.whiteLight);
            }
            public void mouseExited(MouseEvent e) {
                btnUpdate.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnUpdate.setBackground(colorTheme.whiteDark15);
            }
        });

        add(btnCalenda);
        add(btnAVT);
        add(lbCover);
        add(tfName);
        add(tfBirth);
        add(tfNickName);
        add(lbSex);
        add(btnMale);
        add(btnFemale);
        add(taBIO);
        add(btnUpdate);
        add(btnBack);
        add(lbIMG);

        setComponentZOrder(btnAVT, 0);
    }

    private File chooseImageFile() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Chọn ảnh");

        fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "gif"));

        int result = fc.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fc.getSelectedFile();
        }

        return null;
    }

    public String convertImageToBytes(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            String base64 = Base64.getEncoder().encodeToString(bytes);
            fis.read(bytes);
            fis.close();
            return base64;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkTFB() {
        String st = tfBirth.getText();
        if (st.length() == 10) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            try {
                LocalDate date = LocalDate.parse(st, formatter);
                LocalDate now = LocalDate.now();

                if (!date.isBefore(now)) {
                    JOptionPane.showMessageDialog(this, lang.getString("createFull.JODateCheck"));
                    SwingUtilities.invokeLater(() -> {
                        String newText = String.format("%02d/%02d", date.getDayOfMonth(), date.getMonthValue());
                        tfBirth.setText(newText);
                    });
                } else if (date.isAfter(now.minusYears(16))) {
                    JOptionPane.showMessageDialog(this, lang.getString("createFull.JOSixteen"));
                    SwingUtilities.invokeLater(() -> {
                        String newText = String.format("%02d/%02d", date.getDayOfMonth(), date.getMonthValue());
                        tfBirth.setText(newText);
                    });
                }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, lang.getString("createFull.JODateCheck"));
            }
        }
    }

    private String getSex() {
        if (btnMale.isSelected()) {
            return "Male";
        } else if (btnFemale.isSelected()) {
            return "Female";
        }
        return "Unknown";
    }

    private String getNowTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    private static String hashWithBCrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    private String convertDate(String input) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(input, inputFormatter);
            return date.format(outputFormatter);
        } catch (Exception e) {
            return null;
        }
    }

    private byte[] convertImgToByte(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean createAccDatabase() {
        try {
            int rs = db.executeDB("INSERT INTO user(NAME, PASS, NICKNAME, BIRTHDAY, MAIL, CREATE_AT, SEX, BIO, SIZEATV, SIZECOVER) VALUES ('"
                    + tfName.getText() + "', '"
                    + hashWithBCrypt(this.password) + "', '"
                    + tfNickName.getText() + "', '"
                    + convertDate(tfBirth.getText()) + "', '"
                    + this.mail + "', '"
                    + getNowTime() + "', '"
                    + getSex() + "', '"
                    + taBIO.getText() + "', '"
                    + getAvatarIndex() + "', '"
                    + getCoverIndex()
                    + "')"
            );
            db.saveImage(convertImgToByte(getAVT_IMG()), "UPDATE user SET AVATAR_IMG = (?) WHERE MAIL = '" + this.mail +"'");
            db.saveImage(convertImgToByte(getCOVER_IMG()), "UPDATE user SET COVER_IMG = (?) WHERE MAIL = '" + this.mail +"'");
            if (rs > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

class LoginDisplay extends JFrame {
    Login login;
    CreateAccount createAccount;
    CreateFull createFull;
    JPanel p = new JPanel();

    Language lang;
    private Socket socket;

    public LoginDisplay(String theme, String language, Socket soc) {
        setTitle("Login KinKen - Chat");
        setLayout(null);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        this.socket = soc;

        lang = new Language(language);
        lang.loadLanguage();

        login = new Login(theme, language);
        login.setBounds(0, 0, 1000, 700);
        login.setVisible(true);
        add(login);
        createAccount = new CreateAccount(theme, language);
        createAccount.setBounds(0, 0, 1000, 700);
        createAccount.setVisible(false);
        add(createAccount);
        createFull = new CreateFull(theme, language);
        createFull.setBounds(0, 0, 1000, 700);
        createFull.setVisible(false);
        add(createFull);

        p.setFocusable(true);
        add(p);

        login.btnCreate.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                login.setVisible(false);
                createAccount.setVisible(true);
                repaint();
                revalidate();
            }
        });

        login.btnlogin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (login.checklogin()) {
                    updateAccount(login.tfUsername.getText());
                    JOptionPane.showMessageDialog(LoginDisplay.this, lang.getString("LoginDisplay.JOLoginSus"));
                    setVisible(false);
                    try {
                        new Main();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    dispose();
                } else JOptionPane.showMessageDialog(LoginDisplay.this, lang.getString("LoginDisplay.JOLoginFail"));
            }
        });

        createAccount.lblogin.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                createAccount.setVisible(false);
                login.setVisible(true);
                repaint();
                revalidate();
            }
        });
        createAccount.btnCreate.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (createAccount.check()) {
                    createAccount.setVisible(false);
                    createFull.setVisible(true);
                    createFull.setAcc(createAccount.tfEmail.getText(), createAccount.tfPassword.getText());
                    repaint();
                    revalidate();
                }
            }
        });

        createFull.btnBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                createFull.setVisible(false);
                createAccount.setVisible(true);
                repaint();
                revalidate();
            }
        });

        createFull.btnUpdate.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (createFull.createAccDatabase()) {
                    JOptionPane.showMessageDialog(null, lang.getString("createFull.JOCreateAccSus"));

                    createFull.setVisible(false);
                    login.setVisible(true);
                } else JOptionPane.showMessageDialog(null, lang.getString("createFull.JOCreateAccErrol"));

            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                p.requestFocusInWindow();
            }
        });

        setVisible(true);
    }

    public void updateAccount(String mail) {
        try {
            String filePath = PATHICON.getPathResource("whenStart.json");
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

            JSONObject window = new JSONObject(content);

            window.put("account", mail);

            Files.write(Paths.get(filePath), window.toString(4).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        new LoginDisplay("light", "vi", new Socket());
    }
}
