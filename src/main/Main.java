package main;

import ui.HomePage;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Start the client UI
        SwingUtilities.invokeLater(() -> new HomePage());
    }
} 