package KINKEN_CHAT;

import java.io.File;

public class PATHICON {
    public PATHICON() {

    }

    public static String getPath(String fileName) {
        File folder = new File(System.getProperty("user.dir"), "IMAGE");
        return folder.getAbsolutePath() + File.separator + fileName;
    }

    public static String getPathResource(String fileName) {
        File folder = new File(System.getProperty("user.dir"), File.separator +"src" +File.separator +"main" +File.separator +"resources");
        return folder.getAbsolutePath() + File.separator + fileName;
    }

    public static void main(String[] args) {
        System.out.println(getPathResource("mes_en.json"));
    }

}