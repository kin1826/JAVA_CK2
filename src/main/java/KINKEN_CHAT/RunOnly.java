package KINKEN_CHAT;

import javax.swing.*;

public class RunOnly extends JFrame {
    DialogSetting dialogSetting;
    ActionChange themeChange;

    public RunOnly() {
        setLayout(null);
        setSize(500, 500);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        dialogSetting = new DialogSetting(this, "h", true, "light", themeChange, "");
        add(dialogSetting);
        setVisible(true);
    }

    public static void main(String[] args) {
        new RunOnly();
    }
}
