package network;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public Client(String host, int port) {
        try {
    
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
        } catch (IOException e) {
            System.err.println("[CLIENT] Failed to connect to server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String receiveMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean login(String username) {
        this.username = username;
        sendMessage("LOGIN|" + username);
        String response = receiveMessage();
        if (response != null && response.startsWith("LOGIN_SUCCESS")) {
            return true;
        } else {
            
            return false;
        }
    }

    public void sendChatMessage(String message) {
        sendMessage("CHAT|" + username + "|" + message);
    }

    // Send a chat request to another user
    public void sendChatRequest(String toUser) {
        sendMessage("CHAT_REQUEST|" + username + "|" + toUser);
    }

    // Send chat approval/denial
    public void sendChatApproval(String toUser, boolean approved) {
        sendMessage("CHAT_APPROVAL|" + username + "|" + toUser + "|" + (approved ? "YES" : "NO"));
    }
} 