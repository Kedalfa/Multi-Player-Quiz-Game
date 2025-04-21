package src.database;

import java.sql.*;

public class Db {
    private static final String URL = "jdbc:mysql://kedalfa.com:3306/my_database_name";
    private static final String USER = "";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
