package src.ui;

import javax.swing.*;

import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn, registerBtn;

    public LoginFrame() {
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 20, 80, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 160, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 60, 80, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 60, 160, 25);
        add(passwordField);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(30, 100, 100, 25);
        add(loginBtn);

        registerBtn = new JButton("Register");
        registerBtn.setBounds(140, 100, 100, 25);
        add(registerBtn);

        loginBtn.addActionListener(e -> handleLogin());
        registerBtn.addActionListener(e -> new RegisterFrame());

        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        // Placeholder for actual DB check
        String role = database.Auth.checkLogin(username, password); 

        if (role != null) {
            dispose();
            if (role.equals("admin")) {
                new AdminDashboard(username);
            } else {
                new UserDashboard(username);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    }
}
