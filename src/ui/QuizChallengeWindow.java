package ui;

import model.User;
import network.Client;

import javax.swing.*;
import java.awt.*;

public class QuizChallengeWindow extends JFrame {
    public interface QuizChallengeListener {
        void onChallengeSent(String category, String difficulty, int numQuestions);
    }
    private QuizChallengeListener listener;
    public void addQuizChallengeListener(QuizChallengeListener l) { this.listener = l; }

    public QuizChallengeWindow(User currentUser, String opponentUsername, Client client) {
        setTitle("Quiz Challenge: " + opponentUsername + " - " + currentUser.getUsername());
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblInfo = new JLabel("Challenge " + opponentUsername + " to a quiz!", JLabel.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel lblYou = new JLabel("You: " + currentUser.getUsername(), JLabel.CENTER);
        JLabel lblOpponent = new JLabel("Opponent: " + opponentUsername, JLabel.CENTER);
        lblYou.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblOpponent.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JPanel userPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        userPanel.add(lblYou);
        userPanel.add(lblOpponent);
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // Quiz options
        JPanel optionsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Quiz Options"));
        JLabel lblCategory = new JLabel("Category:");
        JComboBox<String> cmbCategory = new JComboBox<>(new String[]{"General", "Science", "History", "Sports"});
        JLabel lblDifficulty = new JLabel("Difficulty:");
        JComboBox<String> cmbDifficulty = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        JLabel lblNumQuestions = new JLabel("Questions:");
        JComboBox<String> cmbNumQuestions = new JComboBox<>(new String[]{"5", "10"});
        optionsPanel.add(lblCategory);
        optionsPanel.add(cmbCategory);
        optionsPanel.add(lblDifficulty);
        optionsPanel.add(cmbDifficulty);
        optionsPanel.add(lblNumQuestions);
        optionsPanel.add(cmbNumQuestions);

        JButton btnStartQuiz = new JButton("Start Quiz Challenge");
        btnStartQuiz.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnStartQuiz.addActionListener(e -> {
            String category = (String) cmbCategory.getSelectedItem();
            String difficulty = (String) cmbDifficulty.getSelectedItem();
            int numQuestions = Integer.parseInt((String) cmbNumQuestions.getSelectedItem());
            if (listener != null) listener.onChallengeSent(category, difficulty, numQuestions);
            // Store challenge info in LobbyPage for robustness
            try { ui.LobbyPage.storePendingChallenge(opponentUsername, category, difficulty, numQuestions); } catch (Exception ex) {}
            client.sendMessage("CHALLENGE|" + currentUser.getUsername() + "|" + opponentUsername + "|" + category + "|" + difficulty + "|" + numQuestions);
            dispose();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnStartQuiz);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(userPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(optionsPanel);

        add(lblInfo, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }
} 