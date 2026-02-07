package KINKEN_CHAT;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class Function {
    public static ImageIcon getScaledAvatarIcon(Image originalImage, int boxWidth, int boxHeight) {
        int originalWidth = originalImage.getWidth(null);
        int originalHeight = originalImage.getHeight(null);

        if (originalWidth <= 0 || originalHeight <= 0) {
            // Tránh lỗi ảnh null hoặc lỗi kích thước
            return null;
        }

        // Tính tỉ lệ scale sao cho ảnh nằm gọn trong box
        float scale = Math.min((float) boxWidth / originalWidth, (float) boxHeight / originalHeight);
        int newWidth = Math.round(originalWidth * scale);
        int newHeight = Math.round(originalHeight * scale);

        // Scale ảnh
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // Tạo khung ảnh cố định (boxWidth x boxHeight) và vẽ ảnh vào giữa
        BufferedImage outputImage = new BufferedImage(boxWidth, boxHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = outputImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setColor(new Color(0, 0, 0, 0)); // nền trong suốt
        g2.fillRect(0, 0, boxWidth, boxHeight);

        int x = (boxWidth - newWidth) / 2;
        int y = (boxHeight - newHeight) / 2;
        g2.drawImage(scaledImage, x, y, null);
        g2.dispose();

        return new ImageIcon(outputImage);
    }

    public static String convertDate(String input) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(input, inputFormatter);
            return date.format(outputFormatter);
        } catch (Exception e) {
            return null;
        }
    }

    public static Image resizeImage(Image originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return resizedImage;
    }

    public static byte[] convertFileToBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            return baos.toByteArray();
        }
    }

    //mã
    public static String encodeBase64(String originalMessage) {
        return Base64.getEncoder().encodeToString(originalMessage.getBytes());
    }
    //giải
    public static String decodeBase64(String encodedMessage) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedMessage);
        return new String(decodedBytes);
    }

}
