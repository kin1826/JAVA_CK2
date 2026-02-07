package KINKEN_CHAT;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 8888;
    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    // map username -> handler, để điều hướng message
    public static ConcurrentHashMap<String, ClientHandler> userMap = new ConcurrentHashMap<>();

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            pool = Executors.newCachedThreadPool();
        } catch (IOException e) {
            throw new RuntimeException("Không thể mở cổng " + port, e);
        }
    }

    public void start() {
        System.out.println("Server started on port " + PORT + ", waiting for clients...");
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New connection from " + clientSocket.getRemoteSocketAddress());
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi chấp nhận kết nối", e);
        }
    }

    public static void main(String[] args) {
        new Server(PORT).start();
    }
}

class ClientHandler implements Runnable {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            in  = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Lỗi tạo luồng I/O cho client", e);
        }
    }

    @Override
    public void run() {
        try {
            // 1. Nhập username
            username = in.readUTF().trim();
            // Nếu trùng username thì yêu cầu nhập lại
            while (Server.userMap.containsKey(username)) {
                username = in.readUTF().trim();
            }
            Server.userMap.put(username, this);
            System.out.println(username + " joined the chat.");

            // 2. Lắng nghe lệnh từ client
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String command = in.readUTF();
                System.out.println(username + ": " + command);
                if ("sendfileone".equalsIgnoreCase(command)) {
                    receiveFileOne();
                } else if ("message".equalsIgnoreCase(command)) {
                    handleMessage();
                } else if ("groupmessage".equalsIgnoreCase(command)) {
                    handleGroupMessage();
                } else if ("sendfilemore".equalsIgnoreCase(command)) {
                    receiveFileMore();
                } else if ("update".equalsIgnoreCase(command)) {
                    updateMessage();
                } else if ("delete".equalsIgnoreCase(command)) {
                    deleteMessage();
                } else if ("emojione".equalsIgnoreCase(command)) {
                    imojiMessage();
                } else if ("emojigroup".equalsIgnoreCase(command)) {
                    imojiMessageGr();
                } else if ("sendrequestgame".equalsIgnoreCase(command)) {
                    sendrequestgame();
                } else if ("repRequestGame".equalsIgnoreCase(command)) {
                    repRequestGame();
                } else if ("sendGameText".equalsIgnoreCase(command)) {
                    sendGameText();
                } else if ("quitGame".equalsIgnoreCase(command)) {
                    quitGame();
                } else if ("sendWin".equalsIgnoreCase(command)) {
                    sendWin();
                }
            }
        } catch (IOException e) {
            System.out.println("Connection with " + username + " lost.");
        } finally {
            // dọn dẹp khi client disconnect
            Server.userMap.remove(username);
            try { socket.close(); } catch (IOException ignored) {}
            System.out.println(username + " has disconnected.");
        }
    }

    private void receiveFileOne() throws IOException {
        int idMes = in.readInt();
        String nameRE = in.readUTF().trim();          // Ví dụ: "@bwd Đây là file mới nè"
        String fileName = in.readUTF();               // Tên file
        long fileSize = in.readLong();
        System.out.println(fileName + " from " + nameRE);

        byte[] fileContent = new byte[(int) fileSize];
        int bytesRead;
        int totalRead = 0;
        while (totalRead < fileSize) {
            bytesRead = in.read(fileContent, totalRead, (int)(fileSize - totalRead));
            if (bytesRead == -1) break;
            totalRead += bytesRead;
        }

        if (nameRE.startsWith("@") && nameRE.length() > 1) {
            String targetUser = nameRE.substring(1).trim();

            ClientHandler target = Server.userMap.get(targetUser);
            if (target != null) {

                target.out.writeUTF("file");     // Lệnh client nhận biết
                target.out.writeInt(idMes);        // Id của tin nhắn gửi lên
                target.out.writeUTF(fileName);           // Tên file
                target.out.writeLong(fileSize);          // Kích thước
                target.out.write(fileContent);           // Dữ liệu file
                target.out.flush();
            } else {
                out.writeUTF("Người dùng '" + targetUser + "' không online.");
            }
        }
    }
    private void receiveFileMore() throws IOException {
        int idMes = in.readInt();
        String fileName = in.readUTF();               // Tên file
        long fileSize = in.readLong();
        byte[] fileContent = new byte[(int) fileSize];
        int bytesRead;
        int totalRead = 0;
        while (totalRead < fileSize) {
            bytesRead = in.read(fileContent, totalRead, (int)(fileSize - totalRead));
            if (bytesRead == -1) break;
            totalRead += bytesRead;
        }

        // Gửi tin nhắn tới tất cả thành viên trong nhóm
        // Giả sử server có danh sách thành viên nhóm (cần thêm cấu trúc dữ liệu)
        // Ở đây, client gửi danh sách email thành viên (inGroup) để đơn giản hóa
        String members = in.readUTF(); // Danh sách email cách nhau bởi dấu phẩy
        String[] memberEmails = Arrays.stream(members.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty() && !s.equals(username))
                .toArray(String[]::new);

        for (String memberEmail : memberEmails) {
            ClientHandler target = Server.userMap.get(memberEmail.trim());
            if (target != null && !target.username.equals(username)) {
                try {
                    target.out.writeUTF("file");
                    target.out.writeInt(idMes);        // Id của tin nhắn gửi lên
                    target.out.writeUTF(fileName);           // Tên file
                    target.out.writeLong(fileSize);          // Kích thước
                    target.out.write(fileContent);
                    target.out.flush();
                } catch (IOException e) {
                    System.out.println("Failed to send to " + memberEmail);
                }
            }
        }
    }

    // Xử lý tin nhắn: private nếu bắt đầu @, ngược lại broadcast
    private void handleMessage() throws IOException {
        String message = in.readUTF().trim();
        int mesid = in.readInt();
        System.out.println(message);

        // Kiểm tra nếu tin nhắn bắt đầu bằng @username
        if (message.startsWith("@")) {
            int spaceIndex = message.indexOf(' ');
            if (spaceIndex > 1) {
                String targetUser = message.substring(1, spaceIndex);
                String realMsg    = message.substring(spaceIndex + 1);

                ClientHandler target = Server.userMap.get(targetUser);
                if (target != null) {
                    target.out.writeUTF("message");
                    target.out.writeUTF(realMsg);
                    target.out.writeInt(mesid);
//                    out.writeUTF("[To " + targetUser + "] " + realMsg);
                } else {
                    out.writeUTF("User '" + targetUser + "' không online.");
                }
                return;
            }
        }

        // Broadcast tin nhắn nếu không phải gửi riêng
        broadcast(username + ": " + message);
    }

    private void handleGroupMessage() throws IOException {
        // Đọc groupId và nội dung tin nhắn
        String message = in.readUTF().trim();
        int mesid = in.readInt();

        // Gửi tin nhắn tới tất cả thành viên trong nhóm
        // Giả sử server có danh sách thành viên nhóm (cần thêm cấu trúc dữ liệu)
        // Ở đây, client gửi danh sách email thành viên (inGroup) để đơn giản hóa
        String members = in.readUTF(); // Danh sách email cách nhau bởi dấu phẩy
        String[] memberEmails = members.split(",");

        for (String memberEmail : memberEmails) {
            ClientHandler target = Server.userMap.get(memberEmail.trim());
            if (target != null && !target.username.equals(username)) {
                try {
                    target.out.writeUTF("message");
                    target.out.writeUTF(message);
                    target.out.writeInt(mesid);
                } catch (IOException e) {
                    System.out.println("Failed to send to " + memberEmail);
                }
            }
        }
        // Gửi lại cho người gửi để hiển thị trên giao diện
//        out.writeUTF(message);
    }

    private void updateMessage() throws IOException {
        String message = in.readUTF().trim();
        int mesid = in.readInt();
        System.out.println(message);

        // Kiểm tra nếu tin nhắn bắt đầu bằng @username
        if (message.startsWith("@")) {
            int spaceIndex = message.indexOf(' ');
            if (spaceIndex > 1) {
                String targetUser = message.substring(1, spaceIndex);
                String realMsg    = message.substring(spaceIndex + 1);

                ClientHandler target = Server.userMap.get(targetUser);
                if (target != null) {
                    target.out.writeUTF("update");
                    target.out.writeInt(mesid);
                    target.out.writeUTF(realMsg);
//                    out.writeUTF("[To " + targetUser + "] " + realMsg);
                }
                return;
            }
        }

        // Broadcast tin nhắn nếu không phải gửi riêng
        broadcast(username + ": " + message);
    }

    private void updateGroupMessage() throws IOException {
        // Đọc groupId và nội dung tin nhắn
        String message = in.readUTF().trim();
        int mesid = in.readInt();

        // Gửi tin nhắn tới tất cả thành viên trong nhóm
        // Giả sử server có danh sách thành viên nhóm (cần thêm cấu trúc dữ liệu)
        // Ở đây, client gửi danh sách email thành viên (inGroup) để đơn giản hóa
        String members = in.readUTF(); // Danh sách email cách nhau bởi dấu phẩy
        String[] memberEmails = members.split(",");

        for (String memberEmail : memberEmails) {
            ClientHandler target = Server.userMap.get(memberEmail.trim());
            if (target != null && !target.username.equals(username)) {
                try {
                    target.out.writeUTF("update");
                    target.out.writeUTF(message);
                    target.out.writeInt(mesid);
                } catch (IOException e) {
                    System.out.println("Failed to send to " + memberEmail);
                }
            }
        }
        // Gửi lại cho người gửi để hiển thị trên giao diện
//        out.writeUTF(message);
    }

    private void deleteMessage() throws IOException {
        String message = in.readUTF().trim();
        int mesid = in.readInt();
        System.out.println(message);

        // Kiểm tra nếu tin nhắn bắt đầu bằng @username
        if (message.startsWith("@")) {
            int spaceIndex = message.indexOf(' ');
            if (spaceIndex > 1) {
                String targetUser = message.substring(1, spaceIndex);
                String realMsg    = message.substring(spaceIndex + 1);

                ClientHandler target = Server.userMap.get(targetUser);
                if (target != null) {
                    target.out.writeUTF("delete");
                    target.out.writeInt(mesid);
//                    out.writeUTF("[To " + targetUser + "] " + realMsg);
                }
                return;
            }
        }

        // Broadcast tin nhắn nếu không phải gửi riêng
        broadcast(username + ": " + message);
    }

    private void deleteGroupMessage() throws IOException {
        // Đọc groupId và nội dung tin nhắn
        String message = in.readUTF().trim();
        int mesid = in.readInt();

        // Gửi tin nhắn tới tất cả thành viên trong nhóm
        // Giả sử server có danh sách thành viên nhóm (cần thêm cấu trúc dữ liệu)
        // Ở đây, client gửi danh sách email thành viên (inGroup) để đơn giản hóa
        String members = in.readUTF(); // Danh sách email cách nhau bởi dấu phẩy
        String[] memberEmails = members.split(",");

        for (String memberEmail : memberEmails) {
            ClientHandler target = Server.userMap.get(memberEmail.trim());
            if (target != null && !target.username.equals(username)) {
                try {
                    target.out.writeUTF("delete");
                    target.out.writeInt(mesid);
                } catch (IOException e) {
                    System.out.println("Failed to send to " + memberEmail);
                }
            }
        }
    }

    private void imojiMessage() throws IOException {
        String message = in.readUTF().trim();
        int mesid = in.readInt();
        System.out.println(message);

        // Kiểm tra nếu tin nhắn bắt đầu bằng @username
        if (message.startsWith("@")) {
            int spaceIndex = message.indexOf(' ');
            if (spaceIndex > 1) {
                String targetUser = message.substring(1, spaceIndex);
                String realMsg    = message.substring(spaceIndex + 1);

                ClientHandler target = Server.userMap.get(targetUser);
                if (target != null) {
                    target.out.writeUTF("emojione");
                    target.out.writeInt(mesid);
                    target.out.writeUTF(realMsg);
//                    out.writeUTF("[To " + targetUser + "] " + realMsg);
                }
                return;
            }
        }

        // Broadcast tin nhắn nếu không phải gửi riêng
        broadcast(username + ": " + message);
    }
    private void imojiMessageGr() throws IOException {
        String message = in.readUTF().trim();
        int mesid = in.readInt();

        // Kiểm tra nếu tin nhắn bắt đầu bằng @username
        String members = in.readUTF(); // Danh sách email cách nhau bởi dấu phẩy
        String[] memberEmails = members.split(",");

        for (String memberEmail : memberEmails) {
            ClientHandler target = Server.userMap.get(memberEmail.trim());
            if (target != null && !target.username.equals(username)) {
                try {
                    target.out.writeUTF("emojigroup");
                    target.out.writeUTF(message);
                    target.out.writeInt(mesid);
                } catch (IOException e) {
                    System.out.println("Failed to send to " + memberEmail);
                }
            }
        }
    }

    private void sendrequestgame() throws IOException {
        String text = in.readUTF();

        if (text.startsWith("@")) {
            int spaceIndex = text.indexOf(' ');
            if (spaceIndex > 1) {
                String targetUser = text.substring(1, spaceIndex);
                String realMsg    = text.substring(spaceIndex + 1);

                ClientHandler target = Server.userMap.get(targetUser);
                if (target != null) {
                    target.out.writeUTF("sendrequestgame");
                }
            }
        }
    }
    private void repRequestGame() throws IOException {
        String text = in.readUTF();

        if (text.startsWith("@")) {
            int spaceIndex = text.indexOf(' ');
            if (spaceIndex > 1) {
                String targetUser = text.substring(1, spaceIndex);
                String realMsg    = text.substring(spaceIndex + 1);

                ClientHandler target = Server.userMap.get(targetUser);
                if (target != null) {
                    target.out.writeUTF("repRequestGame");
                    if (realMsg.equals("ok")) {
                        target.out.writeUTF("ok");
                    } else {
                        target.out.writeUTF("no");
                    }
                }
            }
        }
    }
    private void sendGameText() throws IOException {
        String text = in.readUTF();

        if (text.startsWith("@")) {
            int spaceIndex = text.indexOf(' ');
            if (spaceIndex > 1) {
                String targetUser = text.substring(1, spaceIndex);
                String realMsg    = text.substring(spaceIndex + 1);

                ClientHandler target = Server.userMap.get(targetUser);
                if (target != null) {
                    target.out.writeUTF("sendGameText");
                    target.out.writeUTF(realMsg);
                }
            }
        }
    }
    private void quitGame() throws IOException {
        String text = in.readUTF();

        if (text.startsWith("@")) {
            int spaceIndex = text.indexOf(' ');
            if (spaceIndex > 1) {
                String targetUser = text.substring(1, spaceIndex);
                String realMsg    = text.substring(spaceIndex + 1);

                ClientHandler target = Server.userMap.get(targetUser);
                if (target != null) {
                    target.out.writeUTF("quitGame");
                }
            }
        }
    }

    private void sendWin() throws IOException {
        String text = in.readUTF();

        if (text.startsWith("@")) {
            int spaceIndex = text.indexOf(' ');
            if (spaceIndex > 1) {
                String targetUser = text.substring(1, spaceIndex);
                String realMsg    = text.substring(spaceIndex + 1);

                ClientHandler target = Server.userMap.get(targetUser);
                if (target != null) {
                    target.out.writeUTF("sendWin");
                }
            }
        }
    }

    // Gửi tin nhắn tới mọi client đang online
    private void broadcast(String msg) {
        Server.userMap.forEach((user, handler) -> {
            try {
                handler.out.writeUTF(msg);
            } catch (IOException e) {
                // Bỏ qua lỗi khi gửi tới client này
            }
        });
    }
}

