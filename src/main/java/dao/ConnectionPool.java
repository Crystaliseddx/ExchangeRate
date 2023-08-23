package dao;

import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private String url;
    private List<Connection> connectionPool;
    private List<Connection> usedConnections = new ArrayList<>();
    private static final int POOL_SIZE = 10;

    private ConnectionPool(String url, List<Connection> connectionPool) {
        this.url = url;
        this.connectionPool = connectionPool;
    }

    public static ConnectionPool createConnectionPool() {
        String url = new String
                ("C:\\Users\\Konstantin\\Desktop\\Projects\\CurrencyExchange\\src\\main\\resources\\Currencies.db");
        List<Connection> pool = new ArrayList<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(createConnection(url));
        }
        return new ConnectionPool(url, pool);
    }
    private static Connection createConnection(String url) {
        Connection connection;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public Connection getConnection() throws DBIsNotAvailableException {
        Connection connection;
        if (usedConnections.size() < POOL_SIZE) {
            connection = connectionPool.remove(connectionPool.size() - 1);
            usedConnections.add(connection);
        } else {
            throw new DBIsNotAvailableException(new ErrorMessage("База данных недоступна"));
        }
        return connection;
    }
    public void releaseConnection(Connection connection){
        usedConnections.remove(connection);
        connectionPool.add(connection);
    }
    public int getPoolSize() {
        return connectionPool.size() + usedConnections.size();
    }
}
