package KINKEN_CHAT;

public interface ActionChange {
    void changeTheme(String theme);
    void changeLanguage(String language);
    void changeBackground(String backCode);
    void logout();
    void updateMes(int idMes, String newMes);
    void deleteMes(int idMes);
    void imoji(int idMes, String imoji);
    void imojiGr(int idMes, String emoji);
    void deleteChatReload();
    void sendRequestGame();
    void repRequestGame(int idRe, String mail, String status);
    void sendGameText(String text);
    void reGameText(String text);
    void quitGame();
    void sendWin();
}
