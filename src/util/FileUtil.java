package util;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static void saveResultAsTxt(int score, int correct, int total) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Result as TXT");
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file + ".txt")) {
                writer.write("Score: " + score + "\n");
                writer.write("Correct Answers: " + correct + "\n");
                writer.write("Total Questions: " + total + "\n");
                JOptionPane.showMessageDialog(null, "Result saved as TXT.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving file.");
            }
        }
    }

    public static void saveResultAsCsv(int score, int correct, int total) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Result as CSV");
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(file + ".csv")) {
                writer.write("Score,Correct Answers,Total Questions\n");
                writer.write(score + "," + correct + "," + total + "\n");
                JOptionPane.showMessageDialog(null, "Result saved as CSV.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error saving file.");
            }
        }
    }
} 