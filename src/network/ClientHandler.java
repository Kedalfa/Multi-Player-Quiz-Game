package network;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ClientHandler extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter out;
    private BufferedReader in;
    private boolean running = true;
    private String username;
    // Static map to store quiz results for each match: key = user1|user2, value = Map<username, result[]>
    private static final Map<String, Map<String, String[]>> quizResults = new ConcurrentHashMap<>();

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String message;
            
            message = in.readLine();
            if (message != null && message.startsWith("LOGIN|")) {
                username = message.substring(6);
                sendMessage("LOGIN_SUCCESS");
                server.addUser(username, this);
                System.out.println("User logged in: " + username);
            } else {
                sendMessage("LOGIN_FAILED|Invalid login message");
                closeConnection();
                return;
            }
            while (running && (message = in.readLine()) != null) {
                if (!running) break;
                if (message.startsWith("CHAT_REQUEST|")) {
                    // Format: CHAT_REQUEST|fromUser|toUser
                    String[] parts = message.split("\\|", 3);
                    if (parts.length == 3) {
                        String fromUser = parts[1];
                        String toUser = parts[2];
                        System.out.println("[SERVER DEBUG] Routing CHAT_REQUEST from " + fromUser + " to " + toUser);
                        server.sendToUser(toUser, "CHAT_REQUEST|" + fromUser + "|" + toUser);
                    }
                } else if (message.startsWith("CHAT_APPROVAL|")) {
                    // Format: CHAT_APPROVAL|fromUser|toUser|YES/NO
                    String[] parts = message.split("\\|", 4);
                    if (parts.length == 4) {
                        String fromUser = parts[1];
                        String toUser = parts[2];
                        String approval = parts[3];
                        System.out.println("[SERVER DEBUG] Routing CHAT_APPROVAL from " + fromUser + " to " + toUser + " with approval=" + approval);
                        server.sendToUser(toUser, "CHAT_APPROVAL|" + fromUser + "|" + approval);
                    }
                } else if (message.startsWith("CHAT|")) {
                    // Format: CHAT|username|message
                    server.broadcast(message);
                } else if (message.startsWith("CHALLENGE|")) {
                    // Format: CHALLENGE|fromUser|toUser|category|difficulty|numQuestions
                    String[] parts = message.split("\\|", 6);
                    if (parts.length == 6) {
                        String fromUser = parts[1];
                        String toUser = parts[2];
                        String category = parts[3];
                        String difficulty = parts[4];
                        String numQuestions = parts[5];
                        server.sendToUser(toUser, "CHALLENGE_REQUEST|" + fromUser + "|" + category + "|" + difficulty + "|" + numQuestions);
                    }
                } else if (message.startsWith("CHALLENGE_ACCEPT|")) {
                    // Format: CHALLENGE_ACCEPT|fromUser|toUser
                    String[] parts = message.split("\\|", 3);
                    if (parts.length == 3) {
                        String fromUser = parts[1];
                        String toUser = parts[2];
                        server.sendToUser(fromUser, "CHALLENGE_ACCEPTED|" + toUser);
                    }
                } else if (message.startsWith("CHALLENGE_DECLINE|")) {
                    // Format: CHALLENGE_DECLINE|fromUser|toUser
                    String[] parts = message.split("\\|", 3);
                    if (parts.length == 3) {
                        String fromUser = parts[1];
                        String toUser = parts[2];
                        server.sendToUser(fromUser, "CHALLENGE_DECLINED|" + toUser);
                    }
                } else if (message.startsWith("QUIZ_RESULT|")) {
                    // Format: QUIZ_RESULT|fromUser|toUser|score|correct|total
                    String[] parts = message.split("\\|", 6);
                    if (parts.length == 6) {
                        String fromUser = parts[1];
                        String toUser = parts[2];
                        String score = parts[3];
                        String correct = parts[4];
                        String total = parts[5];
                        // Store result for this match
                        String key = fromUser.compareTo(toUser) < 0 ? fromUser + "|" + toUser : toUser + "|" + fromUser;
                        quizResults.putIfAbsent(key, new ConcurrentHashMap<>());
                        Map<String, String[]> resultMap = quizResults.get(key);
                        resultMap.put(fromUser, new String[]{score, correct, total});
                        // If both results are present, send both to both users and clear
                        if (resultMap.containsKey(fromUser) && resultMap.containsKey(toUser)) {
                            String[] res1 = resultMap.get(fromUser);
                            String[] res2 = resultMap.get(toUser);
                            server.sendToUser(fromUser, "QUIZ_RESULT_RECEIVED|" + fromUser + "|" + res1[0] + "|" + res1[1] + "|" + res1[2] + "|" + toUser + "|" + res2[0] + "|" + res2[1] + "|" + res2[2]);
                            server.sendToUser(toUser, "QUIZ_RESULT_RECEIVED|" + toUser + "|" + res2[0] + "|" + res2[1] + "|" + res2[2] + "|" + fromUser + "|" + res1[0] + "|" + res1[1] + "|" + res1[2]);
                            quizResults.remove(key);
                        }
                    }
                } else if (message.startsWith("QUIZ_START|")) {
                    // Format: QUIZ_START|fromUser|toUser|quizData
                    String[] parts = message.split("\\|", 4);
                    System.out.println("[SERVER DEBUG] QUIZ_START received: " + message);
                    if (parts.length == 4) {
                        String fromUser = parts[1];
                        String toUser = parts[2];
                        String quizData = parts[3];
                        // only forward if both users are online
                        boolean fromUserOnline = server != null && fromUser != null && !fromUser.isEmpty();
                        boolean toUserOnline = server != null && toUser != null && !toUser.isEmpty();
                        if (fromUserOnline) server.sendToUser(fromUser, "PING");
                        if (toUserOnline) server.sendToUser(toUser, "PING");
                        // Actually forward the quiz start
                        server.sendToUser(fromUser, message);
                        server.sendToUser(toUser, message);
                        System.out.println("[SERVER DEBUG] QUIZ_START forwarded to both users");
                    } else {
                        System.out.println("[SERVER DEBUG] Malformed QUIZ_START message: " + message);
                    }
                } else if (message.startsWith("CHAT_DIRECT|")) {
                    // Format: CHAT_DIRECT|fromUser|toUser|message
                    String[] parts = message.split("\\|", 4);
                    if (parts.length == 4) {
                        String fromUser = parts[1];
                        String toUser = parts[2];
                        String chatMsg = parts[3];
                        server.sendToUser(toUser, "CHAT_DIRECT|" + fromUser + "|" + toUser + "|" + chatMsg);
                        server.sendToUser(fromUser, "CHAT_DIRECT|" + fromUser + "|" + toUser + "|" + chatMsg);
                    }
                } else if (message.startsWith("LEAVE_CHAT|")) {
                    // LEAVE_CHAT|username|opponent
                    String[] parts = message.split("\\|", 3);
                    if (parts.length >= 3) {
                        String leavingUser = parts[1];
                        String opponent = parts[2];
                        // Notify both users
                        server.sendToUser(opponent, "CHAT_TERMINATED|" + leavingUser);
                        server.sendToUser(leavingUser, "CHAT_TERMINATED|" + leavingUser);
                        // Remove both users from any chat state if needed
                        server.broadcastUserList();
                    }
                } else if (message.startsWith("LEAVE_LOBBY|")) {
                    // Format: LEAVE_LOBBY|username
                    String[] parts = message.split("\\|", 2);
                    if (parts.length == 2) {
                        String leavingUser = parts[1];
                        server.removeUser(leavingUser, this);
                        server.broadcastUserList();
                    }
                }
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public void closeConnection() {
        running = false;
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (username != null) server.removeUser(username, this);
        server.removeClient(this);
    }
} 