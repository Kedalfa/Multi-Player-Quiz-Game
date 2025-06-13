package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends Thread {
    private int port;
    private List<ClientHandler> clients = new ArrayList<>();
    private boolean running = true;
    private Map<String, java.util.List<ClientHandler>> userMap = new ConcurrentHashMap<>(); // username -> handlers

    public Server(int port) {
        this.port = port;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);
            while (running) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from: " + clientSocket.getInetAddress()); // DEBUG
                ClientHandler handler = new ClientHandler(clientSocket, this);
                clients.add(handler);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void broadcast(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public synchronized void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public synchronized void addUser(String username, ClientHandler handler) {
        userMap.computeIfAbsent(username, k -> new ArrayList<>()).add(handler);
        broadcastUserList();
    }

    public synchronized void removeUser(String username, ClientHandler handler) {
        if (userMap.containsKey(username)) {
            userMap.get(username).remove(handler);
            if (userMap.get(username).isEmpty()) {
                userMap.remove(username);
            }
            broadcastUserList();
        }
    }

    public synchronized void broadcastUserList() {
        StringBuilder sb = new StringBuilder("USERLIST|");
        for (String user : userMap.keySet()) {
            sb.append(user).append(",");
        }
        if (sb.length() > 9 && sb.charAt(sb.length()-1) == ',') sb.deleteCharAt(sb.length()-1);
        System.out.println("Broadcasting user list: " + sb.toString()); // DEBUG
        broadcast(sb.toString());
    }

    public synchronized boolean isUsernameTaken(String username) {
        return false;
    }

    public synchronized void sendToUser(String username, String message) {
        if (userMap.containsKey(username)) {
            for (ClientHandler handler : userMap.get(username)) {
                handler.sendMessage(message);
            }
        }
    }
} 