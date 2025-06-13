package ui;

import model.User;
import model.Question;
import network.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MultiplayerQuizWindow extends JFrame {
    private static MultiplayerQuizWindow lastInstance = null;
    private User user;
    private String opponent;
    private List<Question> questions;
    private Client client;
    private boolean isChallenger;
    private int currentQuestion = 0, score = 0, correctAnswers = 0;
    private boolean finished = false;
    private Integer opponentScore = null;
    private Integer opponentCorrect = null;
    private Integer opponentTotal = null;
    private boolean resultsShown = false;

    private JLabel lblQuestion;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private JButton btnNext;

    public MultiplayerQuizWindow(User user, String opponent, List<Question> questions, Client client, boolean isChallenger) {
       
        lastInstance = this;
        this.user = user;
        this.opponent = opponent;
        this.questions = questions;
        this.client = client;
        this.isChallenger = isChallenger;
        setTitle(user.getUsername() + " vs "+ opponent );
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        lblQuestion = new JLabel("", JLabel.CENTER);
        lblQuestion.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(lblQuestion, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        btnNext = new JButton("Next");
        btnNext.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnNext.addActionListener(e -> nextQuestion());
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBack.addActionListener(e -> {
            dispose();
            clearActiveQuizWindow();
            LobbyPage.getLobbyInstance(user);
        });
        // Add window listener for X button
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                clearActiveQuizWindow();
                LobbyPage.getLobbyInstance(user);
            }
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(btnBack);
        bottomPanel.add(btnNext);
        add(bottomPanel, BorderLayout.SOUTH);

        try {
            showQuestion();
        } catch (Exception ex) {
          
        }
        setVisible(true);
    }

    private void showQuestion() {
        if (currentQuestion >= questions.size()) {
            endQuiz();
            return;
        }
        Question q = questions.get(currentQuestion);

        String[] opts = q.getOptions();
        for (int i = 0; i < 4; i++) {
         
            optionButtons[i].setText(opts[i]);
            optionButtons[i].setSelected(false);
            optionButtons[i].setEnabled(true);
        }
        lblQuestion.setText("Q" + (currentQuestion + 1) + ": " + q.getQuestionText());
        optionsGroup.clearSelection();
    }

    private void nextQuestion() {
        if (currentQuestion >= questions.size()) {
            // Already at the end, do nothing
            return;
        }
        Question q = questions.get(currentQuestion);
        String selected = null;
        for (JRadioButton btn : optionButtons) {
            if (btn.isSelected()) selected = btn.getText();
        }
        if (selected != null && selected.equals(q.getCorrectAnswer())) {
            score += 10;
            correctAnswers++;
        }
        currentQuestion++;
        if (currentQuestion >= questions.size()) {
            btnNext.setEnabled(false);
        }
        showQuestion();
    }

    private void endQuiz() {
        finished = true;
        // Send result to opponent via server
        client.sendMessage("QUIZ_RESULT|" + user.getUsername() + "|" + opponent + "|" + score + "|" + correctAnswers + "|" + questions.size());
        checkAndShowResults();
    }

    // Call this when receiving opponent's result
    public void receiveOpponentResult(int score, int correct, int total) {

        opponentScore = score;
        opponentCorrect = correct;
        opponentTotal = total;
        checkAndShowResults();
    }

    // receive both users results and show the result window
    public void receiveBothResults(String user1, int score1, int correct1, int total1, String user2, int score2, int correct2, int total2) {
        // Determine which user is me and which is opponent
        if (user.getUsername().equals(user1)) {
            showBothResults(user1, score1, correct1, total1, user2, score2, correct2, total2);
        } else {
            showBothResults(user2, score2, correct2, total2, user1, score1, correct1, total1);
        }
    }

    private void checkAndShowResults() {

        if (finished && opponentScore != null && opponentCorrect != null && opponentTotal != null) {
            if (!resultsShown) {
                resultsShown = true;
                showBothResults();
        }}
    }

    private void showBothResults() {
        String msg = "Your Score: " + score + " (" + correctAnswers + "/" + questions.size() + ")\n" +
                opponent + "'s Score: " + opponentScore + " (" + opponentCorrect + "/" + opponentTotal + ")";
        // Show a new result comparison window
        SwingUtilities.invokeLater(() -> {
            new MultiplayerQuizResultWindow(user, user.getUsername(), score, correctAnswers, questions.size(), opponent, opponentScore, opponentCorrect, opponentTotal);
        });
        dispose();
        clearActiveQuizWindow();
    }

    // Update showBothResults to accept all params
    private void showBothResults(String user1, int score1, int correct1, int total1, String user2, int score2, int correct2, int total2) {
        SwingUtilities.invokeLater(() -> {
            new MultiplayerQuizResultWindow(user, user1, score1, correct1, total1, user2, score2, correct2, total2);
        });
        dispose();
        clearActiveQuizWindow();
    }

    private void clearActiveQuizWindow() {
        try {
            Class<?> lobbyClass = Class.forName("ui.LobbyPage");
            java.lang.reflect.Field field = lobbyClass.getDeclaredField("activeQuizWindow");
            field.setAccessible(true);
            field.set(null, null);
        
        } catch (Exception e) {
          
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        clearActiveQuizWindow();
     
    }

    public static MultiplayerQuizWindow getLastInstance() {
        return lastInstance;
    }
} 