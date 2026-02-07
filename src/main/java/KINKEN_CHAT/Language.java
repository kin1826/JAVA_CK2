package KINKEN_CHAT;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Language {
    private String language; // Ngôn ngữ mặc định
    private JSONObject bundle;

    public Language(String language) {
        this.language = language;
    }

    public void loadLanguage() {
        try {
            String filePath = PATHICON.getPathResource("mes_" + language + ".json");
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            bundle = new JSONObject(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String key) {
        return bundle.optString(key, key);
    }

//    public static void switchLanguage() {
//        language = language.equals("en") ? "vi" : "en";
//        loadLanguage();
//    }

    public void changeLanguage(String newLanguage) {
        language = newLanguage;
        loadLanguage();
    }
}
