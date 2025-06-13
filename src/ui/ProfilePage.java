package ui;

import model.User;
import db.UserDAO;
import db.ResultDAO;
import model.Result;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProfilePage extends JFrame {
    private JLabel lblUsername, lblEmail, lblRegDate, lblAvatar;
    private JButton btnBack;
    private User user;

    public ProfilePage(User user, boolean showHistory) {
        this.user = user;
        setTitle("Quiz Game - Profile");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        lblAvatar = new JLabel(new ImageIcon(getClass().getResource("/ui/images/avatar.png")));
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblUsername = new JLabel("Username: " + user.getUsername(), JLabel.CENTER);
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblUsername.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEmail = new JLabel("Email: " + user.getEmail(), JLabel.CENTER);
        lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRegDate = new JLabel("Registered: " + new SimpleDateFormat("yyyy-MM-dd").format(user.getRegistrationDate()), JLabel.CENTER);
        lblRegDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRegDate.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnBack = createModernButton("Back");
        btnBack.addActionListener(e -> {
            dispose();
            new DashboardPage(user);
        });

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(lblAvatar);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(lblUsername);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(lblEmail);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(lblRegDate);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        if (showHistory) {
            ResultDAO resultDAO = new ResultDAO();
            List<Result> results = resultDAO.getResultsByUserId(user.getId());
            String[] columns = {"Date", "Score", "Correct", "Total"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            for (Result r : results) {
                String dateStr = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(r.getTimestamp());
                model.addRow(new Object[]{dateStr, r.getScore(), r.getCorrectAnswers(), r.getTotalQuestions()});
            }
            JTable table = new JTable(model);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            table.setRowHeight(28);
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
            table.setGridColor(new Color(189, 195, 199));
            table.setShowGrid(true);
            table.setBackground(new Color(236, 240, 241));
            table.setForeground(new Color(44, 62, 80));
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
            scrollPane.setPreferredSize(new Dimension(800, 350));
            mainPanel.add(scrollPane);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        mainPanel.add(btnBack);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public ProfilePage(User user) {
        this(user, false);
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