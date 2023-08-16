package dao;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static ConnectionDB instance;
    private String URL = "jdbc:sqlite:C:\\Users\\Konstantin\\Desktop\\Projects\\CurrencyExchange\\src\\main\\resources\\Currencies.db";
    private Connection connection;

    private ConnectionDB() {
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

    public Connection getConnection() {
        return connection;
    }

    public static ConnectionDB getConnectionDB() {
        try {
            if (instance == null) {
                instance = new ConnectionDB();
            } else if (instance.getConnection().isClosed()){
                instance = new ConnectionDB();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance;
    }
}
