package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import util.FileUtil;
import model.User;
import java.text.SimpleDateFormat;

public class ResultPage extends JFrame {
    private JLabel lblScore, lblCorrect, lblTotal, lblTitle;
    private JButton btnDownloadTxt, btnDownloadCsv, btnBack;
    private int score, correct, total;
    private User user;
    private String category;

    public ResultPage(User user, int score, int correct, int total, String category) {
        this.user = user;
        this.score = score;
        this.correct = correct;
        this.total = total;
        this.category = category;
        setTitle("Quiz Game - Result");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setMinimumSize(new Dimension(500, 400));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        lblTitle = new JLabel("Quiz Results");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(new Color(52, 73, 94));

        lblScore = new JLabel("Score: " + score, JLabel.CENTER);
        lblScore.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCorrect = new JLabel("Correct Answers: " + correct + " / " + total, JLabel.CENTER);
        lblCorrect.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCorrect.setAlignmentX(Component.CENTER_ALIGNMENT);
        double percent = total > 0 ? (100.0 * correct / total) : 0.0;
        JLabel lblPercent = new JLabel(String.format("Score Percentage: %.2f%%", percent), JLabel.CENTER);
        lblPercent.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblPercent.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblCategory = new JLabel("Category: " + category, JLabel.CENTER);
        lblCategory.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCategory.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblUser = new JLabel("User: " + (user != null ? user.getUsername() : "-"), JLabel.CENTER);
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblDate = new JLabel("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()), JLabel.CENTER);
        lblDate.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblDate.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnDownloadTxt = createModernButton("Download as TXT");
        btnBack = createModernButton("Back");

        btnDownloadTxt.addActionListener(e -> saveResultAsTxt());
        btnBack.addActionListener(e -> {
            dispose();
            new DashboardPage(user);
        });

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(lblUser);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(lblCategory);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(lblScore);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(lblPercent);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(lblCorrect);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(lblDate);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(btnDownloadTxt);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        mainPanel.add(btnBack);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void saveResultAsTxt() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Result as TXT");
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try (java.io.FileWriter writer = new java.io.FileWriter(file + ".txt")) {
                writer.write("User: " + (user != null ? user.getUsername() : "-") + "\n");
                writer.write("Category: " + category + "\n");
                writer.write("Number of Questions: " + total + "\n");
                writer.write(String.format("Score Percentage: %.2f%%\n", total > 0 ? (100.0 * correct / total) : 0.0));
                writer.write("Correct Answers: " + correct + " / " + total + "\n");
                writer.write("Score: " + score + "\n");
                writer.write("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date()) + "\n");
                JOptionPane.showMessageDialog(this, "Result saved as TXT.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error saving file.");
            }
        }
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
} 