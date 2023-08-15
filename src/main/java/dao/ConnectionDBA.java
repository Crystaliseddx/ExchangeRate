package dao;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDBA {
    private static Path path = Path.of("/main/resources/Currencies.db");
    private static final String URL = "jdbc:sqlite:" + path.toString();
    private static Connection connection;

    public void openConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static java.sql.Connection getConnection() {
        return connection;
    }
}
