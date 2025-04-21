package src.ui;

import javax.swing.*;

public class RegisterFrame extends JFrame {
    public RegisterFrame() {
        setTitle("Register");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(20, 20, 80, 25);
        add(userLabel);

        JTextField usernameField = new JTextField();
        usernameField.setBounds(100, 20, 160, 25);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(20, 60, 80, 25);
        add(passLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(100, 60, 160, 25);
        add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(20, 100, 80, 25);
        add(roleLabel);

        String[] roles = {"user", "admin"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        roleBox.setBounds(100, 100, 160, 25);
        add(roleBox);

        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(100, 150, 100, 25);
        add(registerBtn);

        registerBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();

            boolean success = database.Auth.register(username, password, role);
            if (success) {
                JOptionPane.showMessageDialog(this, "Registration successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed.");
            }
        });

        setVisible(true);
    }
}
