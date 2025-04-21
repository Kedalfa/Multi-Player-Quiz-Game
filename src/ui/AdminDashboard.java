package src.ui;

import javax.swing.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard(String username) {
        setTitle("Admin Dashboard - Welcome " + username);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel label = new JLabel("You are logged in as ADMIN.");
        label.setBounds(100, 50, 250, 30);
        add(label);

       // create quizes and some other features to be dded 

        setVisible(true);
    }
}
