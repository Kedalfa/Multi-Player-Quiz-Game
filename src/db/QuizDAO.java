package db;

import model.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    public List<Question> getQuestions(String category, String difficulty, int limit) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions WHERE category = ? AND difficulty = ? ORDER BY RAND() LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            stmt.setString(2, difficulty);
            stmt.setInt(3, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String[] options = new String[4];
                options[0] = rs.getString("option1");
                options[1] = rs.getString("option2");
                options[2] = rs.getString("option3");
                options[3] = rs.getString("option4");
                questions.add(new Question(
                    rs.getInt("id"),
                    rs.getString("question_text"),
                    options,
                    rs.getString("correct_answer"),
                    rs.getString("category"),
                    rs.getString("difficulty")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
} 