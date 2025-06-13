package ui;

import main.Main;
import model.User;
import javax.swing.*;
import java.awt.*;

public class DashboardPage extends JFrame {
    private JButton btnStartQuiz, btnMultiplayer, btnProfile, btnLogout;
    private JLabel lblWelcome, lblAvatar;
    private User user;

    public DashboardPage(User user) {
        this.user = user;
        setTitle("Quiz Game - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        lblAvatar = new JLabel(new ImageIcon(getClass().getResource("/ui/images/avatar.png")));
        lblAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);
        String welcomeText = (user != null && user.getUsername() != null) ? ("Welcome, " + user.getUsername() + "!") : "Welcome!";
        lblWelcome = new JLabel(welcomeText);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblWelcome.setForeground(new Color(52, 73, 94));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblLogo = new JLabel(new ImageIcon(getClass().getResource("/ui/images/icon.png")));

        btnStartQuiz = createModernButton("Single Player Quiz");
        btnMultiplayer = createModernButton("Multiplayer Quiz");
        btnProfile = createModernButton("Profile & History");
        btnLogout = createModernButton("Logout");

        btnStartQuiz.addActionListener(e -> {
            dispose();
            new GamePage(user);
        });
        btnMultiplayer.addActionListener(e -> {
            dispose();
            new LobbyPage(user);
        });
        btnProfile.addActionListener(e -> {
            dispose();
            new ProfilePage(user, true);
        });
        btnLogout.addActionListener(e -> {
            dispose();
            new HomePage();
        });

        if (user == null) {
            btnStartQuiz.setEnabled(false);
            btnMultiplayer.setEnabled(false);
            btnProfile.setEnabled(false);
        }

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(lblAvatar);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(lblWelcome);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(btnStartQuiz);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnMultiplayer);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnProfile);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnLogout);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createModernButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 32, 12, 32));
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