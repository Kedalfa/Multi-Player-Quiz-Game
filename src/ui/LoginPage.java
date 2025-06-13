package ui;

import db.UserDAO;
import model.User;
import util.SoundManager;
import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister, btnBack;

    public LoginPage() {
        setTitle("Quiz Game - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 245, 245));

        JLabel lblTitle = new JLabel("Login");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(new Color(52, 73, 94));

        txtUsername = new JTextField();
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtUsername.setBorder(BorderFactory.createTitledBorder("Username"));

        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPassword.setBorder(BorderFactory.createTitledBorder("Password"));

        btnLogin = createModernButton("Login");
        btnRegister = createModernButton("Register");
        btnBack = createModernButton("Back");

        btnLogin.addActionListener(e -> {
            SoundManager.playCorrect();
            login();
        });
        btnRegister.addActionListener(e -> {
            dispose();
            new RegisterPage();
        });
        btnBack.addActionListener(e -> {
            dispose();
            new HomePage();
        });

        panel.add(lblTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(txtUsername);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(txtPassword);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnLogin);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnRegister);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnBack);

        add(panel, BorderLayout.CENTER);
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

    private void login() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.login(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new DashboardPage(user);
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 