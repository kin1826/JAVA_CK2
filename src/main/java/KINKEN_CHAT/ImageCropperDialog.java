package KINKEN_CHAT;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageCropperDialog extends JDialog {
    private BufferedImage originalImage;
    private BufferedImage croppedImage;
    private JPanel imagePanel;
    private int imageX = 0, imageY = 0;
    private double scale = 0.1;
    private Point dragStart;
    private final int cropWidth = 500;
    private final int cropHeight = 200;
    private final int cropDiameter = 160;
    private boolean cropped = false;
    private final String cropType;

    private String cropRect;
    private String cropCircle;

    public String getCropRect() {
        return cropRect;
    }

    public void setCropRect(String cropRect) {
        this.cropRect = cropRect;
    }

    public String getCropCircle() {
        return cropCircle;
    }

    public void setCropCircle(String cropCircle) {
        this.cropCircle = cropCircle;
    }

    public ImageCropperDialog(Frame owner, File imageFile, String type) {
        super(owner, "Crop ảnh", true);
        this.cropType = type;
        setSize(800, 600);
        setLayout(new BorderLayout());

        JButton cropButton = new JButton("Cắt ảnh");
        JButton rotateButton = new JButton("Xoay 90°");
        JSlider zoomSlider = new JSlider(5, 300, 100);
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

                    if (cropType.equals("R")) {
                        g2d.setColor(new Color(0, 0, 0, 150));
                        g2d.fillRect(0, 0, getWidth(), cy);
                        g2d.fillRect(0, cy + cropHeight, getWidth(), getHeight() - cy - cropHeight);
                        g2d.fillRect(0, cy, cx, cropHeight);
                        g2d.fillRect(cx + cropWidth, cy, getWidth() - cx - cropWidth, cropHeight);
                        g2d.setColor(Color.WHITE);
                        g2d.setStroke(new BasicStroke(2));
                        g2d.drawRect(cx, cy, cropWidth, cropHeight);
                    } else {
                        int r = cropDiameter / 2;
                        int cxCircle = getWidth() / 2 - r;
                        int cyCircle = getHeight() / 2 - r;
                        Ellipse2D circle = new Ellipse2D.Double(cxCircle, cyCircle, cropDiameter, cropDiameter);

                        Area outside = new Area(new Rectangle(0, 0, getWidth(), getHeight()));

                        outside.subtract(new Area(circle));

                        g2d.setColor(new Color(0, 0, 0, 150));
                        g2d.fill(outside);

                        g2d.setColor(Color.WHITE);
                        g2d.setStroke(new BasicStroke(2));
                        g2d.draw(circle);
                    }
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

        cropButton.addActionListener(e -> {
            if (cropType.equals("R")) {
                setCropRect(cropRect());
            } else {
                setCropCircle(cropCircle());
            }
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

        openImage(imageFile);
    }

    public void openImage(File imgFile) {
        try {
            originalImage = ImageIO.read(imgFile);
            scale = 0.1;
            SwingUtilities.invokeLater(() -> {
                imageX = (imagePanel.getWidth() - (int) (originalImage.getWidth() * scale)) / 2;
                imageY = (imagePanel.getHeight() - (int) (originalImage.getHeight() * scale)) / 2;
                repaint();
            });
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi mở ảnh!");
        }
    }

    private String cropRect() {
        int cx = (imagePanel.getWidth() - cropWidth) / 2;
        int cy = (imagePanel.getHeight() - cropHeight) / 2;
        int imgCropX = (int) ((cx - imageX) / scale);
        int imgCropY = (int) ((cy - imageY) / scale);
        int imgCropW = (int) (cropWidth / scale);
        int imgCropH = (int) (cropHeight / scale);

        if (imgCropX < 0 || imgCropY < 0 ||
                imgCropX + imgCropW > originalImage.getWidth() ||
                imgCropY + imgCropH > originalImage.getHeight()) {
            JOptionPane.showMessageDialog(this, "Không thể cắt vì nằm ngoài ảnh!");
            return null;
        }

        croppedImage = new BufferedImage(cropWidth, cropHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = croppedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, cropWidth, cropHeight,
                imgCropX, imgCropY, imgCropX + imgCropW, imgCropY + imgCropH, null);
        g.dispose();

        return imgCropX + " " + imgCropY + " " + imgCropW + " " + imgCropH;
    }

    private String cropCircle() {
        int cx = imagePanel.getWidth() / 2 - cropDiameter / 2;
        int cy = imagePanel.getHeight() / 2 - cropDiameter / 2;

        int imgCropX = (int) ((cx - imageX) / scale);
        int imgCropY = (int) ((cy - imageY) / scale);
        int imgCropD = (int) (cropDiameter / scale);

        if (imgCropX < 0 || imgCropY < 0 ||
                imgCropX + imgCropD > originalImage.getWidth() ||
                imgCropY + imgCropD > originalImage.getHeight()) {
            JOptionPane.showMessageDialog(this, "Không thể cắt vì nằm ngoài ảnh!");
            return null;
        }

        BufferedImage sub = originalImage.getSubimage(imgCropX, imgCropY, imgCropD, imgCropD);
        BufferedImage output = new BufferedImage(cropDiameter, cropDiameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, cropDiameter, cropDiameter);

        g2.setComposite(AlphaComposite.Src);
        Ellipse2D circle = new Ellipse2D.Double(0, 0, cropDiameter, cropDiameter);
        g2.setClip(circle);
        g2.drawImage(sub, 0, 0, cropDiameter, cropDiameter, null);
        g2.dispose();

        croppedImage = output;

        return imgCropX + " " + imgCropY + " " + imgCropD;
    }


    private void rotateImage90() {
        if (originalImage == null) return;
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();

        BufferedImage rotated = new BufferedImage(h, w, BufferedImage.TYPE_INT_ARGB);
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
        SwingUtilities.invokeLater(() -> {
            File file = new File("E:/My Picture/NOEL/BEFORE/DSC00192.JPG");
            ImageCropperDialog dialog = new ImageCropperDialog(null, file, "C"); // "C" cho circle
            BufferedImage result = dialog.showDialogAndGetImage();
            if (result != null) {
                JFrame frame = new JFrame("Ảnh đã cắt");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(result.getWidth(), result.getHeight());
                frame.setLocationRelativeTo(null);
                frame.add(new JLabel(new ImageIcon(result)));
                frame.setVisible(true);
            }
        });
    }
}
