package dao;

import models.Currency;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {
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
    public List<Currency> getCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM currencies");
            ResultSet resultSet = statement.executeQuery();
            Currency currency = new Currency();
            while (resultSet.next()) {
                currency.setId(resultSet.getInt("ID"));
                currency.setCode(resultSet.getString("CODE"));
                currency.setFullname(resultSet.getString("FULLNAME"));
                currency.setSign(resultSet.getString("SIGN"));

                currencies.add(currency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currencies;
    }
}
