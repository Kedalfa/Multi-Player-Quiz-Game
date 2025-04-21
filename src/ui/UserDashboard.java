package src.ui;

import javax.swing.*;

public class UserDashboard extends JFrame {
    public UserDashboard(String username) {
        setTitle("User Dashboard - Welcome " + username);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel label = new JLabel("You are logged in as USER.");
        label.setBounds(100, 50, 250, 30);
        add(label);

        // Add more user-specific features here (like join quiz, view scores, etc.)

        setVisible(true);
    }
}
