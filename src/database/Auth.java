package src.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Auth {

    public static boolean register(String username, String password, String role) {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = Db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password); 
            stmt.setString(3, role);     

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String login(String username, String password) {
        String query = "SELECT role FROM users WHERE username = ? AND password = ?";

        try (Connection conn = Db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role"); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // login failed
    }
}
