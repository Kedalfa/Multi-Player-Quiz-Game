package ui;

import javax.swing.*;
import java.awt.*;
import model.User;

public class MultiplayerQuizResultWindow extends JFrame {
    public MultiplayerQuizResultWindow(User user, String user1, int score1, int correct1, int total1, String user2, int score2, int correct2, int total2) {
        setTitle("Quiz Results Comparison");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Quiz Results", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel resultPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        resultPanel.add(new JLabel("", JLabel.CENTER));
        resultPanel.add(new JLabel(user1, JLabel.CENTER));
        resultPanel.add(new JLabel(user2, JLabel.CENTER));
        resultPanel.add(new JLabel("Score:", JLabel.CENTER));
        resultPanel.add(new JLabel(String.valueOf(score1), JLabel.CENTER));
        resultPanel.add(new JLabel(String.valueOf(score2), JLabel.CENTER));
        resultPanel.add(new JLabel("Correct:", JLabel.CENTER));
        resultPanel.add(new JLabel(correct1 + "/" + total1, JLabel.CENTER));
        resultPanel.add(new JLabel(correct2 + "/" + total2, JLabel.CENTER));
        add(resultPanel, BorderLayout.CENTER);


        setVisible(true);
    }
} 