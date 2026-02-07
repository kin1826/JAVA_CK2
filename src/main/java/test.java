import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import java.awt.*;

public class test extends JPanel {
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public test(String videoPath) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 300)); // Kích thước của khu vực video

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        add(mediaPlayerComponent, BorderLayout.CENTER);
    }

    public void playVideo(String videoPath) {
        if (videoPath != null && !videoPath.isEmpty()) {
            mediaPlayerComponent.mediaPlayer().media().start(videoPath);
        } else {
            System.out.println("Không tìm thấy file video!");
        }
    }
}

class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Ứng dụng với Video");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Chạy NativeDiscovery để VLCJ tìm thư viện
        new NativeDiscovery().discover();
        System.setProperty("jna.library.path", "C:\\Program Files\\VideoLAN\\VLC");

        // Tạo panel chứa video
        test videoPanel = new test("D:\\24\\Edit\\sp\\0315.mp4");

        // Tạo panel chứa nội dung khác (ví dụ: danh sách bạn bè)
        JPanel otherPanel = new JPanel();
        otherPanel.setPreferredSize(new Dimension(400, 600));
        otherPanel.setBackground(Color.LIGHT_GRAY);
        otherPanel.add(new JLabel("Danh sách bạn bè"));

        // Chia giao diện: Video bên trái, nội dung khác bên phải
        add(videoPanel, BorderLayout.WEST);
        add(otherPanel, BorderLayout.CENTER);

        setVisible(true);

        // Bắt đầu phát video
        videoPanel.playVideo("D:\\24\\Edit\\sp\\0315.mp4");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
