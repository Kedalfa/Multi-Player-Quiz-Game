package ui;

import network.Client;
import model.User;
import db.QuizDAO;
import model.Question;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class LobbyPage extends JFrame {
    private Client client;
    private String username;
    private JList<String> userList;
    private DefaultListModel<String> userListModel;
    private Thread listenerThread;
    private boolean connected = false;
    private User user;
    private JButton btnBack;
    private MultiplayerQuizWindow activeQuizWindow = null;
    private String pendingChallengeCategory = null;
    private String pendingChallengeDifficulty = null;
    private int pendingChallengeNumQuestions = 0;
    private volatile boolean running = true;
    // Prevent multiple Dashboard windows
    private static volatile boolean dashboardOpen = false;
    // Track open chat windows by opponent username
    private java.util.Map<String, MultiplayerChatWindow> chatWindows = new java.util.HashMap<>();
    private static LobbyPage lobbyInstance = null;
    private static DashboardPage dashboardInstance = null;

    public static LobbyPage getLobbyInstance(User user) {
        if (lobbyInstance != null) lobbyInstance.dispose();
        lobbyInstance = new LobbyPage(user);
        return lobbyInstance;
    }

    public LobbyPage(User user) {
        this.user = user;
        this.username = user.getUsername();
        // Clear any old pending challenge state
        this.pendingChallengeCategory = null;
        this.pendingChallengeDifficulty = null;
        this.pendingChallengeNumQuestions = 0;
        setTitle("Lobby - " + username);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(700, 500);
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top panel with Dashboard button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnDashboard = new JButton("Back to Dashboard");
        btnDashboard.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnDashboard.setBackground(new Color(52, 152, 219));
        btnDashboard.setForeground(Color.WHITE);
        btnDashboard.setFocusPainted(false);
        btnDashboard.setBorder(BorderFactory.createEmptyBorder(8, 24, 8, 24));
        btnDashboard.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDashboard.addActionListener(e -> dispose());
        topPanel.setBackground(new Color(245, 245, 245));
        topPanel.add(btnDashboard);
        add(topPanel, BorderLayout.NORTH);

        // User list
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        userList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane userScroll = new JScrollPane(userList);
        userScroll.setPreferredSize(new Dimension(180, 0));
        userScroll.setBorder(BorderFactory.createTitledBorder("Online Users"));
        add(userScroll, BorderLayout.CENTER);

        userList.setEnabled(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // Connect to server immediately
        connectToServer();
        setVisible(true);

        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && userList.isEnabled()) {
                String selectedUser = userList.getSelectedValue();
                if (selectedUser != null && !selectedUser.equals(username) && !selectedUser.equals("Waiting for players to join...")) {
                    // Show dialog with options
                    Object[] options = {"Chat", "Start Quiz", "Close"};
                    int choice = JOptionPane.showOptionDialog(
                        this,
                        "What would you like to do with '" + selectedUser + "'?",
                        "User Options",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]
                    );
                    if (choice == 0) { // Chat
                        client.sendChatRequest(selectedUser);
                        // Remove waiting dialog, chat will open automatically on approval
                    } else if (choice == 1) { // Start Quiz
                        new QuizChallengeWindow(user, selectedUser, client);
                    }
                    userList.clearSelection();
                }
            }
        });
    }

    private void connectToServer() {
        if (connected) return;
        client = new network.Client("localhost", 12345);
        if (client.login(username)) {
            connected = true;
            userList.setEnabled(true);
            listenerThread = new Thread(this::listenForMessages);
            listenerThread.start();
           requestUserList();
        } else {
            // Show empty user list and friendly message
            SwingUtilities.invokeLater(() -> {
                userListModel.clear();
                userListModel.addElement("Waiting for players to join...");
            });
            userList.setEnabled(false);
        }
    }

   public void requestUserList() {
    //user list will update automatically
    }

    private void listenForMessages() {
        String msg;
        try {
            while (running && (msg = client.receiveMessage()) != null) {
                if (!running) break;
                if (msg.startsWith("USERLIST|")) {
                    String[] users = msg.substring(9).split(",");

                    SwingUtilities.invokeLater(() -> {
                        userListModel.clear();
                        if (users.length == 1 && users[0].isEmpty()) {
                            userListModel.addElement("Waiting for players to join...");
                        } else {
                            boolean any = false;
                            for (String user : users) {
                                if (!user.trim().isEmpty() && !user.equals(username)) {
                                    userListModel.addElement(user);
                                    any = true;
                                }
                            }
                            if (!any) userListModel.addElement("Waiting for players to join...");
                        }
                    });
                } else if (msg.startsWith("CHALLENGE_REQUEST|")) {
                    // Format: CHALLENGE_REQUEST|fromUser|category|difficulty|numQuestions
                    String[] parts = msg.split("\\|", 5);
                    String fromUser = parts[1];
                    String category = parts.length > 2 ? parts[2] : "General";
                    String difficulty = parts.length > 3 ? parts[3] : "Easy";
                    int numQuestions = parts.length > 4 ? Integer.parseInt(parts[4]) : 5;
                    int result = JOptionPane.showConfirmDialog(this, fromUser + " has challenged you to a quiz!\nCategory: " + category + "\nDifficulty: " + difficulty + "\nQuestions: " + numQuestions + "\nAccept?", "Quiz Challenge", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        // Store challenge options for quiz start
                        this.pendingChallengeCategory = category;
                        this.pendingChallengeDifficulty = difficulty;
                        this.pendingChallengeNumQuestions = numQuestions;
                        client.sendMessage("CHALLENGE_ACCEPT|" + fromUser + "|" + username);
                        // Start the quiz for both users
                    } else {
                        client.sendMessage("CHALLENGE_DECLINE|" + fromUser + "|" + username);
                    }
                } else if (msg.startsWith("CHALLENGE_ACCEPTED|")) {
                    String opponent = msg.substring("CHALLENGE_ACCEPTED|".length());
            
                    QuizDAO quizDAO = new QuizDAO();
                    List<Question> questions = quizDAO.getQuestions(this.pendingChallengeCategory, this.pendingChallengeDifficulty, this.pendingChallengeNumQuestions);
                    String quizData = serializeQuestions(questions);
                    client.sendMessage("QUIZ_START|" + username + "|" + opponent + "|" + quizData);
             
                } else if (msg.startsWith("QUIZ_START|")) {
                    // Format: QUIZ_START|fromUser|toUser|quizData
                    String[] parts = msg.split("\\|", 4);
                
                    if (parts.length == 4) {
                        String fromUser = parts[1];
                        String toUser = parts[2];
                        String quizData = parts[3];
                        // do not open quiz window if quizData is empty
                        if (quizData == null || quizData.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Quiz could not start: No questions received from server.", "Quiz Error", JOptionPane.ERROR_MESSAGE);
                           
                            return;
                        }
                        // Only open the quiz window if this user is a participant, no quiz window is open, and there is a valid pending challenge
                        if ((fromUser.equals(username) || toUser.equals(username)) && activeQuizWindow == null && pendingChallengeCategory != null && pendingChallengeNumQuestions > 0) {
                            List<Question> questions = deserializeQuestions(quizData);
                            if (questions == null || questions.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "No quiz questions received. Quiz cannot start.", "Quiz Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                           //clear any old reference
                            activeQuizWindow = null;
                            setVisible(false);
                            SwingUtilities.invokeLater(() -> {
                                MultiplayerQuizWindow win = new MultiplayerQuizWindow(user, fromUser.equals(username) ? toUser : fromUser, questions, client, false);
                                activeQuizWindow = MultiplayerQuizWindow.getLastInstance();
                            });
                        }
                    }
                } else if (msg.startsWith("CHALLENGE_DECLINED|")) {
                    String opponent = msg.substring("CHALLENGE_DECLINED|".length());
                    JOptionPane.showMessageDialog(this, opponent + " declined your challenge.");
                } else if (msg.startsWith("QUIZ_RESULT_RECEIVED|")) {
                    // Format: QUIZ_RESULT_RECEIVED|user1|score1|correct1|total1|user2|score2|correct2|total2
                    String[] parts = msg.split("\\|", 9);
                    if (parts.length == 9 && activeQuizWindow != null) {
                        String user1 = parts[1];
                        int score1 = Integer.parseInt(parts[2]);
                        int correct1 = Integer.parseInt(parts[3]);
                        int total1 = Integer.parseInt(parts[4]);
                        String user2 = parts[5];
                        int score2 = Integer.parseInt(parts[6]);
                        int correct2 = Integer.parseInt(parts[7]);
                        int total2 = Integer.parseInt(parts[8]);
                        activeQuizWindow.receiveBothResults(user1, score1, correct1, total1, user2, score2, correct2, total2);
                    }
                } else if (msg.startsWith("CHAT_REQUEST|")) {
                    // Format: CHAT_REQUEST|fromUser|toUser
                    String[] parts = msg.split("\\|", 3);
                    if (parts.length == 3) {
                        String fromUser = parts[1];
                        String toUser = parts[2];
                        if (toUser.trim().equalsIgnoreCase(username.trim())) {
                            // Immediately approve and open chat window, no dialog
                            client.sendChatApproval(fromUser, true);
                            SwingUtilities.invokeLater(() -> openChatWindow(fromUser));
                        }
                    }
                } else if (msg.startsWith("CHAT_APPROVAL|")) {
                    // Format: CHAT_APPROVAL|fromUser|YES/NO
                    String[] parts = msg.split("\\|", 3);
                    if (parts.length == 3) {
                        String fromUser = parts[1];
                        String approval = parts[2];
                        if (approval.equals("YES")) {
                            SwingUtilities.invokeLater(() -> openChatWindow(fromUser));
                        } else {
                            // Optionally notify user of decline, or just ignore
                        }
                    }
                } else if (msg.startsWith("CHAT_DIRECT|")) {
                    // Format: CHAT_DIRECT|fromUser|toUser|message
                    String[] parts = msg.split("\\|", 4);
                    if (parts.length == 4) {
                        String fromUser = parts[1];
                        String toUser = parts[2];
                        String chatMsg = parts[3];
                        String otherUser = fromUser.equals(username) ? toUser : fromUser;
                        SwingUtilities.invokeLater(() -> {
                            MultiplayerChatWindow win = chatWindows.get(otherUser);
                            if (win == null) {
                                win = openChatWindow(otherUser);
                            }
                            if (win != null) {
                                win.appendMessage(fromUser + ": " + chatMsg);
                            }
                        });
                    }
                }
         
            }
        } catch (Exception ex) {
          
        }
       
    }

    // Helper to serialize questions as a simple string (custom format)
    private String serializeQuestions(List<Question> questions) {
        StringBuilder sb = new StringBuilder();
        for (Question q : questions) {
            sb.append(q.getQuestionText().replace("|", "/")).append("~");
            for (String opt : q.getOptions()) sb.append(opt.replace("|", "/")).append("~");
            sb.append(q.getCorrectAnswer().replace("|", "/")).append("~");
            sb.append(q.getCategory().replace("|", "/")).append("~");
            sb.append(q.getDifficulty().replace("|", "/")).append("`"); // end of question
        }
        return sb.toString();
    }

    private List<Question> deserializeQuestions(String data) {
        java.util.List<Question> questions = new java.util.ArrayList<>();
        String[] qBlocks = data.split("`");
        for (String block : qBlocks) {
            if (block.trim().isEmpty()) continue;
            String[] parts = block.split("~");
            if (parts.length >= 7) {
                String questionText = parts[0];
                String[] options = new String[]{parts[1], parts[2], parts[3], parts[4]};
                String correctAnswer = parts[5];
                String category = parts[6];
                String difficulty = parts.length > 7 ? parts[7] : "Easy";
                questions.add(new Question(0, questionText, options, correctAnswer, category, difficulty));
            }
        }
        return questions;
    }

    // Static method to store challenge info for robustness
    public static void storePendingChallenge(String opponent, String category, String difficulty, int numQuestions) {
        // Find the last open LobbyPage instance (if any)
        java.awt.Window[] windows = java.awt.Window.getWindows();
        for (java.awt.Window w : windows) {
            if (w instanceof LobbyPage) {
                LobbyPage lobby = (LobbyPage) w;
                lobby.pendingChallengeCategory = category;
                lobby.pendingChallengeDifficulty = difficulty;
                lobby.pendingChallengeNumQuestions = numQuestions;
                break;
            }
        }
    }

    // Helper to open or focus a chat window with a given opponent
    private MultiplayerChatWindow openChatWindow(String opponent) {
        // Use the static openChat method to enforce singleton per opponent
        MultiplayerChatWindow win = MultiplayerChatWindow.openChat(user, opponent, client);
        chatWindows.put(opponent, win);
        win.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                chatWindows.remove(opponent);
                // Refresh user list when chat window closes
                requestUserList();
            }
        });
        return win;
    }

    @Override
    public void dispose() {
        running = false;
        if (client != null) {
            try { client.sendMessage("LEAVE_LOBBY|" + username); } catch (Exception ex) { ex.printStackTrace(); }
            client.close();
        }
        if (listenerThread != null) {
            listenerThread.interrupt();
            try { listenerThread.join(1000); } catch (InterruptedException ex) { ex.printStackTrace(); }
        }
        lobbyInstance = null;
        super.dispose();
        openDashboard();
    }

    private void openDashboard() {
        if (dashboardInstance != null) {
            dashboardInstance.dispose();
        }
        dashboardInstance = new DashboardPage(user);
        dashboardInstance.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                dashboardInstance = null;
            }
        });
    }

    public void refreshUserListAndEnable() {
        requestUserList();
        userList.setEnabled(true);
    }
} 