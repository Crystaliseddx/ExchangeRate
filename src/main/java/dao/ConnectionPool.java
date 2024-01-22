package dao;

import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;
import org.sqlite.SQLiteConfig;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static List<Connection> connectionPool;
    private static List<Connection> usedConnections = new ArrayList<>();
    private static final int POOL_SIZE = 10;

    public static void checkConnectionPool() {
        if (connectionPool == null) {
            createConnectionPool();
        }
    }

    public static void createConnectionPool() {

        ClassLoader classLoader = ConnectionPool.class.getClassLoader();
        URL resource = classLoader.getResource("Currencies.db");
        File file = new File(resource.getFile());

        List<Connection> pool = new ArrayList<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.add(createConnection(file.getAbsolutePath()));
        }
        connectionPool = pool;
    }

    private static Connection createConnection(String pathToDB) {
        Connection connection;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            connection = DriverManager.getConnection("jdbc:sqlite:" + pathToDB, config.toProperties());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static Connection getConnection() throws DBIsNotAvailableException {
        Connection connection;
        if (usedConnections.size() < POOL_SIZE) {
            connection = connectionPool.remove(connectionPool.size() - 1);
            usedConnections.add(connection);
        } else {
            throw new DBIsNotAvailableException(new ErrorMessage("База данных недоступна"));
        }
        return connection;
    }

    public static void releaseConnection(Connection connection) {
        usedConnections.remove(connection);
        connectionPool.add(connection);
    }
}
