package ui;

import main.Main;
import javax.swing.*;
import java.awt.*;

public class HomePage extends JFrame {
    private JButton btnLogin, btnRegister;
    private JPanel mainPanel;

    public HomePage() {
        setTitle("Quiz Game - Home");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel lblLogo = new JLabel(new ImageIcon(getClass().getResource("/ui/images/icon.png")));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblTitle = new JLabel("Welcome to Quiz Game");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(new Color(52, 73, 94));

        btnLogin = createModernButton("Login");
        btnRegister = createModernButton("Register");

        btnLogin.addActionListener(e -> {
            dispose();
            new LoginPage();
        });
        btnRegister.addActionListener(e -> {
            dispose();
            new RegisterPage();
        });

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(lblLogo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(btnLogin);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnRegister);
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

    private void showLoginRegisterDialog() {
        String[] options = {"Login", "Register", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this, "Please login or register to continue.", "Authentication Required",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            dispose();
            new LoginPage();
        } else if (choice == 1) {
            dispose();
            new RegisterPage();
        }
    }
} 