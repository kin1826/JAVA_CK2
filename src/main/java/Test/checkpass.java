package Test;

import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class checkpass {
    public static String hash(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }
    public static boolean check(String pass, String pass2) {
        return BCrypt.checkpw(pass, pass2);
    }

    private static String getNowTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    public static void main(String[] args) {
        String pass = "1234";
        String pa = hash(pass);
        if (check(pass, pa)) {
            System.out.println("YES");
            System.out.println(pa);
        } else {
            System.out.println("NO");
        }
        System.out.println(getNowTime());
    }
}
