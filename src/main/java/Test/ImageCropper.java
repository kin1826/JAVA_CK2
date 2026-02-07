package Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCropper extends JDialog {
    private BufferedImage originalImage;
    private BufferedImage croppedImage;
    private JPanel imagePanel;
    private int imageX = 0, imageY = 0;
    private double scale = 1.0;
    private Point dragStart;
    private final int cropWidth = 500;
    private final int cropHeight = 200;
    private boolean cropped = false;
    private File imageFile;

    public ImageCropper(Frame owner, File imageFile) {
        super(owner, "Crop ảnh kiểu Facebook", true);
        this.imageFile = imageFile;
        setSize(800, 600);
        setLayout(new BorderLayout());

        JButton cropButton = new JButton("Cắt ảnh");
        JButton rotateButton = new JButton("Xoay 90°");
        JSlider zoomSlider = new JSlider(10, 300, 100);
        zoomSlider.setMajorTickSpacing(50);
        zoomSlider.setPaintTicks(true);

        JPanel controlPanel = new JPanel();
        controlPanel.add(cropButton);
        controlPanel.add(rotateButton);
        controlPanel.add(new JLabel("Zoom:"));
        controlPanel.add(zoomSlider);

        imagePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (originalImage != null) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int drawWidth = (int) (originalImage.getWidth() * scale);
                    int drawHeight = (int) (originalImage.getHeight() * scale);
                    g2d.drawImage(originalImage, imageX, imageY, drawWidth, drawHeight, null);

                    int cx = (getWidth() - cropWidth) / 2;
                    int cy = (getHeight() - cropHeight) / 2;
                    g2d.setColor(new Color(0, 0, 0, 150));
                    g2d.fillRect(0, 0, getWidth(), cy);
                    g2d.fillRect(0, cy + cropHeight, getWidth(), getHeight() - cy - cropHeight);
                    g2d.fillRect(0, cy, cx, cropHeight);
                    g2d.fillRect(cx + cropWidth, cy, getWidth() - cx - cropWidth, cropHeight);

                    g2d.setColor(Color.WHITE);
                    g2d.setStroke(new BasicStroke(2));
                    g2d.drawRect(cx, cy, cropWidth, cropHeight);
                }
            }
        };

        imagePanel.setBackground(Color.DARK_GRAY);
        imagePanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                dragStart = e.getPoint();
            }
        });

        imagePanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (originalImage != null && dragStart != null) {
                    int dx = e.getX() - dragStart.x;
                    int dy = e.getY() - dragStart.y;
                    imageX += dx;
                    imageY += dy;
                    dragStart = e.getPoint();
                    repaint();
                }
            }
        });

        openImage(this.imageFile);
        cropButton.addActionListener(e -> {
            cropImage();
            if (croppedImage != null) {
                cropped = true;
                setVisible(false);
            }
        });
        rotateButton.addActionListener(e -> rotateImage90());

        zoomSlider.addChangeListener(e -> {
            scale = zoomSlider.getValue() / 100.0;
            repaint();
        });

        add(controlPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
    }

    public void openImage(File imgFile) {
        try {
            originalImage = ImageIO.read(imgFile);
            imageX = (imagePanel.getWidth() - (int) (originalImage.getWidth() * scale)) / 2;
            imageY = (imagePanel.getHeight() - (int) (originalImage.getHeight() * scale)) / 2;
            scale = 1.0;
            repaint();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi mở ảnh!");
        }
    }

    private void cropImage() {
        if (originalImage == null) return;

        int cx = (imagePanel.getWidth() - cropWidth) / 2;
        int cy = (imagePanel.getHeight() - cropHeight) / 2;

        int imgCropX = Math.max(0, (int) ((cx - imageX) / scale));
        int imgCropY = Math.max(0, (int) ((cy - imageY) / scale));
        int imgCropW = Math.min(originalImage.getWidth() - imgCropX, (int) (cropWidth / scale));
        int imgCropH = Math.min(originalImage.getHeight() - imgCropY, (int) (cropHeight / scale));

        if (imgCropW <= 0 || imgCropH <= 0) {
            JOptionPane.showMessageDialog(this, "Không thể cắt vì nằm ngoài ảnh!");
            return;
        }

        croppedImage = new BufferedImage(cropWidth, cropHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = croppedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, cropWidth, cropHeight,
                imgCropX, imgCropY, imgCropX + imgCropW, imgCropY + imgCropH, null);
        g.dispose();
    }

    private void rotateImage90() {
        if (originalImage == null) return;
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();

        BufferedImage rotated = new BufferedImage(h, w, originalImage.getType());
        Graphics2D g2d = rotated.createGraphics();
        g2d.translate(h / 2.0, w / 2.0);
        g2d.rotate(Math.toRadians(90));
        g2d.translate(-w / 2.0, -h / 2.0);
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        originalImage = rotated;
        imageX = (imagePanel.getWidth() - (int) (originalImage.getWidth() * scale)) / 2;
        imageY = (imagePanel.getHeight() - (int) (originalImage.getHeight() * scale)) / 2;
        repaint();
    }

    public BufferedImage showDialogAndGetImage() {
        setVisible(true);
        return cropped ? croppedImage : null;
    }

    public static void main(String[] args) {
        File file = new File("E:\\My Picture\\NOEL\\BEFORE\\DSC00192.JPG");
        SwingUtilities.invokeLater(() -> {
            ImageCropper dialog = new ImageCropper(null, file);
            BufferedImage result = dialog.showDialogAndGetImage();
            if (result != null) {
                JFrame frame = new JFrame("Ảnh đã cắt");
                frame.setSize(600, 300);
                frame.add(new JLabel(new ImageIcon(result)));
                frame.setVisible(true);
            }
        });
    }
}
