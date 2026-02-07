package Test;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;

public class EmailVerificationApp {
    private static final Map<String, String> verificationCodes = new ConcurrentHashMap<>();
    private static final Map<String, ScheduledFuture<?>> expiryTasks = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        JFrame frame = new JFrame("Xác thực Email");
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField emailField = new JTextField(25);
        JButton sendButton = new JButton("Gửi mã xác thực");
        JTextField codeField = new JTextField(10);
        JButton verifyButton = new JButton("Xác minh");
        JLabel resultLabel = new JLabel();

        frame.add(new JLabel("Email:"));
        frame.add(emailField);
        frame.add(sendButton);
        frame.add(new JLabel("Nhập mã xác thực:"));
        frame.add(codeField);
        frame.add(verifyButton);
        frame.add(resultLabel);

        sendButton.addActionListener(e -> {
            String email = emailField.getText();
            String code = generateCode();

            new Thread(() -> {
                try {
                    sendEmail(email, code);

                    verificationCodes.put(email, code);
                    resultLabel.setText("✅ Mã đã được gửi tới email.");

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
                    SwingUtilities.invokeLater(() -> resultLabel.setText("❌ Lỗi khi gửi email."));
                }
            }).start();
        });

        verifyButton.addActionListener(e -> {
            String email = emailField.getText();
            String enteredCode = codeField.getText();
            String correctCode = verificationCodes.get(email);

            if (correctCode != null && enteredCode.equals(correctCode)) {
                resultLabel.setText("✅ Mã chính xác!");
                verificationCodes.remove(email); // Xoá mã sau khi đúng
                ScheduledFuture<?> task = expiryTasks.remove(email);
                if (task != null) task.cancel(true);
            } else {
                resultLabel.setText("❌ Sai mã hoặc mã đã hết hạn!");
            }
        });

        frame.setVisible(true);
    }

    private static String generateCode() {
        Random rand = new Random();
        int code = 100000 + rand.nextInt(900000);
        return String.valueOf(code);
    }

    private static void sendEmail(String toEmail, String code) throws MessagingException {
        final String fromEmail = "tvbphu2@gmail.com";      // <- Gmail bạn
        final String password = "gcaq feah xcdx bvnc";     // <- App password

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
}
