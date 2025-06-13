package db;

import model.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResultDAO {
    public void saveResult(Result result) {
        String sql = "INSERT INTO results (user_id, score, total_questions, correct_answers, timestamp) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, result.getUserId());
            stmt.setInt(2, result.getScore());
            stmt.setInt(3, result.getTotalQuestions());
            stmt.setInt(4, result.getCorrectAnswers());
            stmt.setTimestamp(5, new Timestamp(result.getTimestamp().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Result> getResultsByUserId(int userId) {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT * FROM results WHERE user_id = ? ORDER BY timestamp DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                results.add(new Result(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("total_questions"),
                    rs.getInt("correct_answers"),
                    rs.getTimestamp("timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
} 