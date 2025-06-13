package ui;

import db.QuizDAO;
import model.Question;
import model.User;
import util.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GamePage extends JFrame {
    private JComboBox<String> cmbCategory, cmbDifficulty, cmbNumQuestions;
    private JButton btnStart, btnNext, btnBack;
    private JLabel lblQuestion, lblTimer, lblFeedback;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private JPanel optionsPanel;
    private List<Question> questions;
    private int currentQuestion = 0, score = 0, correctAnswers = 0;
    private int timeLeft = 15;
    private Timer timer;
    private User user;
    private JPanel topPanel;
    private String selectedCategory;

    public GamePage(User user) {
        this.user = user;
        setTitle("Quiz Game - Play");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(245, 245, 245));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        topPanel.setMinimumSize(new Dimension(780, 180));

        JLabel lblTitle = new JLabel("Start a New Quiz");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(52, 73, 94));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(new GridLayout(2, 3, 10, 10));
        selectPanel.setBackground(new Color(245, 245, 245));
        cmbCategory = new JComboBox<>(new String[]{"General", "Science", "History", "Sports"});
        cmbDifficulty = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        cmbNumQuestions = new JComboBox<>(new String[]{"5", "10"});
        btnStart = createModernButton("Start Quiz");
        btnBack = createModernButton("Back");
        selectPanel.add(new JLabel("Category:"));
        selectPanel.add(new JLabel("Difficulty:"));
        selectPanel.add(new JLabel("Questions:"));
        selectPanel.add(cmbCategory);
        selectPanel.add(cmbDifficulty);
        selectPanel.add(cmbNumQuestions);

        topPanel.add(lblTitle);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(selectPanel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(btnStart);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(btnBack);

        add(topPanel, BorderLayout.NORTH);

        lblQuestion = new JLabel("", JLabel.CENTER);
        lblQuestion.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblQuestion.setForeground(new Color(44, 62, 80));
        add(lblQuestion, BorderLayout.CENTER);

        optionsPanel = new JPanel(new GridLayout(4, 1, 8, 8));
        optionsPanel.setBackground(new Color(245, 245, 245));
        optionsPanel.setMinimumSize(new Dimension(780, 160));
        optionButtons = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            optionButtons[i].setBackground(new Color(236, 240, 241));
            optionButtons[i].setForeground(new Color(44, 62, 80));
            optionButtons[i].setFocusPainted(false);
            optionButtons[i].setBorder(BorderFactory.createEmptyBorder());
            optionsGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
        }

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        bottomPanel.setBackground(new Color(245, 245, 245));
        lblTimer = new JLabel("Time: 15", JLabel.CENTER);
        lblTimer.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblFeedback = new JLabel("", JLabel.CENTER);
        lblFeedback.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnNext = createModernButton("Next");
        btnNext.setEnabled(false);
        bottomPanel.add(lblTimer);
        bottomPanel.add(lblFeedback);
        bottomPanel.add(btnNext);

        JPanel mainBottomPanel = new JPanel();
        mainBottomPanel.setLayout(new BoxLayout(mainBottomPanel, BoxLayout.Y_AXIS));
        mainBottomPanel.setBackground(new Color(245, 245, 245));
        mainBottomPanel.add(optionsPanel);
        mainBottomPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainBottomPanel.add(bottomPanel);
        add(mainBottomPanel, BorderLayout.SOUTH);

        btnStart.addActionListener(e -> startQuiz());
        btnNext.addActionListener(e -> nextQuestion());
        btnBack.addActionListener(e -> {
            stopTimer();
            dispose();
            new DashboardPage(user);
        });
        for (JRadioButton btn : optionButtons) {
            btn.addActionListener(e -> checkAnswer());
        }

        setVisible(true);
    }

    private JButton createModernButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 28, 10, 28));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(new Color(41, 128, 185), 2, true));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(41, 128, 185));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(52, 152, 219));
            }
        });
        return btn;
    }

    private void startQuiz() {
        QuizDAO quizDAO = new QuizDAO();
        selectedCategory = (String) cmbCategory.getSelectedItem();
        String difficulty = (String) cmbDifficulty.getSelectedItem();
        int numQuestions = Integer.parseInt((String) cmbNumQuestions.getSelectedItem());
        questions = quizDAO.getQuestions(selectedCategory, difficulty, numQuestions);
        if (questions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions found for this category and difficulty. Please try another selection.", "No Questions", JOptionPane.WARNING_MESSAGE);
            return;
        }
        remove(topPanel);
        revalidate();
        repaint();
        currentQuestion = 0;
        score = 0;
        correctAnswers = 0;
        showQuestion();
    }

    private void showQuestion() {
        if (currentQuestion >= questions.size()) {
            endQuiz();
            return;
        }
        Question q = questions.get(currentQuestion);
        lblQuestion.setText("Q" + (currentQuestion + 1) + ": " + q.getQuestionText());
        String[] opts = q.getOptions();
        optionsGroup.clearSelection();
        for (int i = 0; i < 4; i++) {
            optionButtons[i].setText(opts[i]);
            optionButtons[i].setSelected(false);
            optionButtons[i].setEnabled(true);
            optionButtons[i].setBackground(new Color(236, 240, 241));
        }
        lblFeedback.setText("");
        btnNext.setEnabled(false);
        startTimer();
    }

    private void checkAnswer() {
        stopTimer();
        Question q = questions.get(currentQuestion);
        String selected = null;
        for (JRadioButton btn : optionButtons) {
            if (btn.isSelected()) selected = btn.getText();
        }
        if (selected != null && selected.equals(q.getCorrectAnswer())) {
            score += 10;
            correctAnswers++;
            lblFeedback.setText("Correct!");
            SoundManager.playCorrect();
        } else {
            lblFeedback.setText("Incorrect!");
            SoundManager.playWrong();
            for (JRadioButton btn : optionButtons) {
                if (btn.getText().equals(q.getCorrectAnswer())) {
                    btn.setBackground(new Color(46, 204, 113));
                }
            }
        }
        for (JRadioButton btn : optionButtons) btn.setEnabled(false);
        btnNext.setEnabled(true);
    }

    private void nextQuestion() {
        currentQuestion++;
        showQuestion();
    }

    private void endQuiz() {
        stopTimer();
        if (user != null) {
            db.ResultDAO resultDAO = new db.ResultDAO();
            model.Result result = new model.Result(0, user.getId(), score, questions.size(), correctAnswers, new java.util.Date());
            resultDAO.saveResult(result);
        }
        JOptionPane.showMessageDialog(this, "Quiz Finished!\nScore: " + score + "\nCorrect: " + correctAnswers + "/" + questions.size(), "Result", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new ResultPage(user, score, correctAnswers, questions.size(), selectedCategory);
    }

    private void startTimer() {
        timeLeft = 15;
        lblTimer.setText("Time: " + timeLeft);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    timeLeft--;
                    lblTimer.setText("Time: " + timeLeft);
                    if (timeLeft <= 0) {
                        stopTimer();
                        lblFeedback.setText("Time's up!");
                        SoundManager.playWrong();
                        Question q = questions.get(currentQuestion);
                        for (JRadioButton btn : optionButtons) {
                            if (btn.getText().equals(q.getCorrectAnswer())) {
                                btn.setBackground(new Color(46, 204, 113));
                            }
                            btn.setEnabled(false);
                        }
                        btnNext.setEnabled(true);
                    }
                });
            }
        }, 1000, 1000);
    }

    private void stopTimer() {
        if (timer != null) timer.cancel();
    }
} 