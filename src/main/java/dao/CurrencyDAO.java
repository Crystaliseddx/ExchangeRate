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
            Currency currency = new Currency();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM currencies");
            ResultSet resultSet = statement.executeQuery();

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

    public Currency getCurrency(String code) {
        Currency currency = new Currency();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM currencies WHERE CODE = ?");
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();

            currency.setId(resultSet.getInt("ID"));
            currency.setCode(resultSet.getString("CODE"));
            currency.setFullname(resultSet.getString("FULLNAME"));
            currency.setSign(resultSet.getString("SIGN"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currency;
    }

    public void saveCurrency(Currency currency) {
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO currencies (CODE, FULLNAME, SIGN) VALUES (?, ?, ?)");

            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getFullname());
            statement.setString(3, currency.getSign());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
