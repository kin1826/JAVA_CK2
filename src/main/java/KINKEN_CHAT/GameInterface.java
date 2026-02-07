package KINKEN_CHAT;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameInterface extends JFrame {
    JLabel lbTitle = new JLabel("Nối từ thôi nào!", SwingConstants.CENTER);
    ActionChange actionChange;
    JTextField tfOther = new JTextField();
    JTextField tfMe = new JTextField();
    JButton btnSend;
    JLabel lbTime = new JLabel();

    JPanel panelOther;
    JPanel panelMe;

    boolean isYourTurn = false;
    Timer turnTimer;

    Image imgGame = new ImageIcon(PATHICON.getPath("emoji_game.chat.imoji.png"))
            .getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

    public GameInterface(int ID_ME, byte[] avatarMe, String nameme,
                         int ID_OTHER, byte[] avatarOther, String nameother,
                         ActionChange actionChange) {

        this.actionChange = actionChange;
        getContentPane().setBackground(Color.WHITE);

        setTitle("Game - Nối Từ");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        lbTime.setFont(new Font("Times New Roman", Font.BOLD, 30));
        lbTime.setHorizontalAlignment(SwingConstants.CENTER);

        lbTitle.setIcon(new ImageIcon(imgGame));
        lbTitle.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel panelTop = new JPanel(new BorderLayout(5, 5));
        panelTop.add(lbTitle, BorderLayout.CENTER);
        panelTop.add(lbTime, BorderLayout.SOUTH);
        add(panelTop, BorderLayout.NORTH);

        JPanel panelCenter = new JPanel(new GridLayout(1, 2, 10, 10));
        panelCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 👤 Đối thủ
        panelOther = new JPanel(new BorderLayout(5, 5));
        JLabel lbOther = new JLabel(nameother, SwingConstants.CENTER);
        Image imgOther = Function.resizeImage(new ImageIcon(avatarOther).getImage(), 120, 120);
        lbOther.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lbOther.setIcon(new ImageIcon(imgOther));
        lbOther.setHorizontalTextPosition(SwingConstants.CENTER);
        lbOther.setVerticalTextPosition(SwingConstants.BOTTOM);

        tfOther.setEditable(false);
        tfOther.setFont(new Font("Arial", Font.PLAIN, 16));
        tfOther.setHorizontalAlignment(SwingConstants.CENTER);
        tfOther.setText("Đang chờ...");

        JButton btnHide = new JButton("Ẩn");
        btnHide.setVisible(false);

        panelOther.add(lbOther, BorderLayout.NORTH);
        panelOther.add(tfOther, BorderLayout.CENTER);
        panelOther.add(btnHide, BorderLayout.SOUTH);

        // 🧍 Bạn
        panelMe = new JPanel(new BorderLayout(5, 5));
        JLabel lbMe = new JLabel(nameme + " (Bạn)", SwingConstants.CENTER);
        Image imgMe = Function.resizeImage(new ImageIcon(avatarMe).getImage(), 120, 120);
        lbMe.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        lbMe.setIcon(new ImageIcon(imgMe));
        lbMe.setHorizontalTextPosition(SwingConstants.CENTER);
        lbMe.setVerticalTextPosition(SwingConstants.BOTTOM);

        tfMe.setFont(new Font("Arial", Font.PLAIN, 16));
        tfMe.setHorizontalAlignment(SwingConstants.CENTER);

        btnSend = new JButton("Gửi");

        panelMe.add(lbMe, BorderLayout.NORTH);
        panelMe.add(tfMe, BorderLayout.CENTER);
        panelMe.add(btnSend, BorderLayout.SOUTH);

        panelCenter.add(panelOther);
        panelCenter.add(panelMe);

        add(panelCenter, BorderLayout.CENTER);

        // Sự kiện gửi từ
        btnSend.addActionListener(e -> {
            String input = tfMe.getText().trim();
            if (!input.isEmpty() && isValidWord(input)) {
                actionChange.sendGameText(input);

                tfMe.setEditable(false);
                btnSend.setEnabled(false);

                if (turnTimer != null) {
                    turnTimer.cancel(); // Dừng thời gian sau khi gửi
                }

                isYourTurn = false;
                lbTime.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng 2 từ (cách nhau bằng dấu cách)!");
            }
        });

        // Khi tắt cửa sổ
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (turnTimer != null) turnTimer.cancel();
                actionChange.quitGame();
            }
        });
    }

    // Bắt đầu lượt của bạn
    public void startYourTurn() {
        isYourTurn = true;
        tfMe.setEditable(true);
        btnSend.setEnabled(true);
        tfMe.setText("");
        setTimeReduce();
    }

    // Đếm ngược thời gian
    public void setTimeReduce() {
        if (turnTimer != null) {
            turnTimer.cancel();
        }

        if (!isYourTurn) {
            lbTime.setText("");
            return;
        }

        int[] seconds = {10};
        turnTimer = new Timer();
        turnTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (seconds[0] >= 0) {
                    lbTime.setText(seconds[0] + "s");
                    seconds[0]--;
                } else {
                    turnTimer.cancel();
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "⏰ Hết giờ! Bạn thua.");
                        actionChange.sendWin();
                        tfMe.setEditable(false);
                        btnSend.setEnabled(false);
                    });
                }
            }
        }, 0, 1000);
    }

    // Kiểm tra hợp lệ: đúng 2 từ, cách nhau bởi khoảng trắng
    public boolean isValidWord(String word) {
        if (word == null) return false;
        String[] parts = word.trim().split("\\s+");
        return parts.length == 2;
    }

    public void showWin() {
        JOptionPane.showMessageDialog(null, "✅ Chúc mừng, Bạn đã thắng!");
    }

    // Hiển thị từ của đối phương
    public void setWordFromOther(String word) {
        tfOther.setText(word);
    }
}
