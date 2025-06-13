package ui;

import db.UserDAO;
import model.User;
import util.SoundManager;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class RegisterPage extends JFrame {
    private JTextField txtUsername, txtEmail;
    private JPasswordField txtPassword, txtConfirmPassword;
    private JButton btnRegister, btnLogin;

    public RegisterPage() {
        setTitle("Quiz Game - Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setMinimumSize(new Dimension(600, 500));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(new Color(245, 245, 245));

        JLabel lblTitle = new JLabel("Register");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(new Color(52, 73, 94));

        txtUsername = new JTextField();
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUsername.setBorder(BorderFactory.createTitledBorder("Username"));

        txtEmail = new JTextField();
        txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtEmail.setBorder(BorderFactory.createTitledBorder("Email"));

        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPassword.setBorder(BorderFactory.createTitledBorder("Password"));

        txtConfirmPassword = new JPasswordField();
        txtConfirmPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtConfirmPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtConfirmPassword.setBorder(BorderFactory.createTitledBorder("Confirm Password"));

        btnRegister = createModernButton("Register");
        btnLogin = createModernButton("Login");

        btnRegister.addActionListener(e -> {
            SoundManager.playCorrect();
            register();
        });
        btnLogin.addActionListener(e -> {
            dispose();
            new LoginPage();
        });

        panel.add(lblTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(txtUsername);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(txtEmail);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(txtPassword);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(txtConfirmPassword);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(btnRegister);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(btnLogin);

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

    private void register() {
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirmPassword = new String(txtConfirmPassword.getPassword());
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        UserDAO userDAO = new UserDAO();
        if (userDAO.checkUsernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        User user = new User(username, password, email, new Date());
        if (userDAO.register(user)) {
            JOptionPane.showMessageDialog(this, "Registration successful! Logging in...", "Success", JOptionPane.INFORMATION_MESSAGE);
            new DashboardPage(user);
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 