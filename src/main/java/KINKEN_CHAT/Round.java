package KINKEN_CHAT;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DateFormat;

public class Round extends JButton {
    private Color background;
    private Color foreground;
    private int radius;

    public Round(String text, Color background, Color foreground, int radius) {
        super(text);
        setBackground(background);
        setForeground(foreground);
        this.radius = radius;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
    }
}

class TextFieldRound extends JTextField {
    private Color backgroundColor;
    private Color foregroundColor;
    private int radius;
    private String placeholder;

    public TextFieldRound(String placeholder, Color background, Color foreground, int radius) {
        super();
        this.backgroundColor = background;
        this.foregroundColor = foreground;
        this.radius = radius;
        this.placeholder = placeholder;

        setOpaque(false);
        setForeground(foreground);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) { repaint(); }
            public void focusLost(FocusEvent e) { repaint(); }
        });

        getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { repaint(); }
            public void removeUpdate(DocumentEvent e) { repaint(); }
            public void changedUpdate(DocumentEvent e) { repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g2);

        if (getText().isEmpty() && !isFocusOwner()) {
            g2.setColor(Color.GRAY);
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            Insets insets = getInsets();
            g2.drawString(placeholder, insets.left + 2, getHeight() / 2 + getFont().getSize() / 2 - 2);
        }

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
    }
}


class TextFieldPasswordRound extends JPasswordField {
    private Color backgroundColor;
    private Color foregroundColor;
    private int radius;
    private String textPlaceHolder;
    private boolean isPlaceHolderActive = true;

    public TextFieldPasswordRound(String textPlaceHolder, Color background, Color foreground, int radius) {
        super();
        this.backgroundColor = background;
        this.foregroundColor = foreground;
        this.radius = radius;
        this.textPlaceHolder = textPlaceHolder;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        setText(textPlaceHolder);
        setForeground(Color.GRAY);
        setEchoChar((char) 0);

        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (isPlaceHolderActive) {
                    setText("");
                    setForeground(foregroundColor);
                    isPlaceHolderActive = false;
                    setEchoChar('●');
                }
            }
            public void focusLost(FocusEvent e) {
                if (getPassword().length == 0) {
                    setText(textPlaceHolder);
                    setForeground(Color.GRAY);
                    isPlaceHolderActive = true;
                    setEchoChar((char) 0);
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
    }
}

class FormattedTextFieldRound extends JFormattedTextField {
    private Color backgroundColor;
    private Color foregroundColor;
    private int radius;
    private String textPlaceHolder;
    private boolean isPlaceHolderActive = true;

    public FormattedTextFieldRound(String textPlaceHolder, Color background, Color foreground, int radius, DateFormat dateFormat) {
        super();
        this.backgroundColor = background;
        this.foregroundColor = foreground;
        this.radius = radius;
        this.textPlaceHolder = textPlaceHolder;

        if (dateFormat != null) {
            setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(dateFormat)));
        }
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // ✅ Đặt placeholder mặc định
        setText(textPlaceHolder);
        setForeground(Color.GRAY);

        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (isPlaceHolderActive) {
                    setText("");
                    setForeground(foregroundColor);
                    isPlaceHolderActive = false;
                }
            }
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(textPlaceHolder);
                    setForeground(Color.GRAY);
                    isPlaceHolderActive = true;
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
    }
}

class TextAreaRound extends JTextArea {
    private Color backgroundColor;
    private Color foregroundColor;
    private int radius;
    private String textPlaceHolder;
    private boolean isPlaceHolderActive = true;

    public TextAreaRound(String textPlaceHolder, Color background, Color foreground, int radius) {
        super();
        this.backgroundColor = background;
        this.foregroundColor = foreground;
        this.radius = radius;
        this.textPlaceHolder = textPlaceHolder;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        setText(textPlaceHolder);
        setForeground(Color.GRAY);

        addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (isPlaceHolderActive) {
                    setText("");
                    setForeground(foregroundColor);
                    isPlaceHolderActive = false;
                }
            }
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(textPlaceHolder);
                    setForeground(Color.GRAY);
                    isPlaceHolderActive = true;
                }
            }
        });
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
    }
}

class ScrollPaneRound implements Border {
    private final int radius;
    private final Color borderColor;
    private final int thickness;

    public ScrollPaneRound(int radius, Color borderColor, int thickness) {
        this.radius = radius;
        this.borderColor = borderColor;
        this.thickness = thickness;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(thickness));
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}

class PanelRound extends JPanel {
    private Color background;
    private Color foreground;
    private int radius;

    public PanelRound(Color background, int radius) {
        this.background = background;
        this.radius = radius;
        setBackground(background);
        setForeground(foreground);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(background);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}
