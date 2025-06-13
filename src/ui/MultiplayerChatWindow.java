package ui;

import network.Client;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiplayerChatWindow extends JFrame {
    private User user;
    private String opponent;
    private Client client;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton startQuizButton;
    private static final java.util.Map<String, MultiplayerChatWindow> openChats = new java.util.HashMap<>();
    private volatile boolean chatActive = true;

    public MultiplayerChatWindow(User user, String opponent, Client client) {
        this.user = user;
        this.opponent = opponent;
        this.client = client;
        setTitle("Chat with " + opponent);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        startQuizButton = new JButton("Start Quiz");
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(sendButton, BorderLayout.NORTH);
        rightPanel.add(startQuizButton, BorderLayout.SOUTH);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(rightPanel, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());
        startQuizButton.addActionListener(e -> startQuiz());

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                try {
                    if (chatActive) {
                        client.sendMessage("LEAVE_CHAT|" + user.getUsername() + "|" + opponent);
                        chatActive = false;
                        SwingUtilities.invokeLater(() -> {
                            LobbyPage lobby = LobbyPage.getLobbyInstance(user);
                            lobby.refreshUserListAndEnable();
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Start a background thread to listen for incoming messages
        new Thread(() -> {
            try {
                while (true) {
                    String msg = client.receiveMessage();
                    if (msg == null) break;
                    if (msg.startsWith("CHAT_TERMINATED|")) {
                        final String terminatedMsg = msg;
                        SwingUtilities.invokeLater(() -> {
                            if (chatActive) {
                                chatActive = false;
                                dispose();
                                LobbyPage.getLobbyInstance(user);
                            }
                        });
                        break;
                    } else if (msg.startsWith("CHAT_DIRECT|")) {
                        String[] parts = msg.split("\\|", 4);
                        if (parts.length == 4) {
                            String fromUser = parts[1];
                            String toUser = parts[2];
                            String chatMsg = parts[3];
                            SwingUtilities.invokeLater(() -> appendMessage(fromUser + ": " + chatMsg));
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();

        setVisible(true);
    }

    private void handleChatTerminated(String msg) {
        chatActive = false;
        inputField.setEnabled(false);
        sendButton.setEnabled(false);
        startQuizButton.setEnabled(false);
        dispose();
        SwingUtilities.invokeLater(() -> {
            LobbyPage lobby = LobbyPage.getLobbyInstance(user);
            lobby.refreshUserListAndEnable();
        });
        try { client.close(); } catch (Exception ex) { ex.printStackTrace(); }
    }

    private void sendMessage() {
        if (!chatActive) return;
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            client.sendMessage("CHAT_DIRECT|" + user.getUsername() + "|" + opponent + "|" + text);
            inputField.setText("");
        }
    }

    private void startQuiz() {
        // Clean up chat state before starting quiz
        try {
            client.sendMessage("LEAVE_CHAT|" + user.getUsername() + "|" + opponent);
            client.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        dispose();
        new QuizChallengeWindow(user, opponent, client);
        // Optionally disable the button to prevent multiple challenges
        startQuizButton.setEnabled(false);
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }

    public static MultiplayerChatWindow openChat(User user, String opponent, Client client) {
        MultiplayerChatWindow win = openChats.get(opponent);
        if (win != null) {
            win.dispose();
        }
        win = new MultiplayerChatWindow(user, opponent, client);
        openChats.put(opponent, win);
        return win;
    }

    @Override
    public void dispose() {
        chatActive = false;
        openChats.remove(opponent);
        super.dispose();
    }
} 