package dao;

import exceptions.AlreadyExistsException;
import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;
import models.Currency;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CurrencyDAO {
    private static ConnectionPool connectionPool = ConnectionPool.createConnectionPool();

    public List<Currency> getCurrencies() throws DBIsNotAvailableException {
        Connection connection = connectionPool.getConnection();
        List<Currency> currencies = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM currencies");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Currency currency = getCurrency(resultSet);
                currencies.add(currency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return currencies;
    }

    public Currency getCurrencyByCode (String code) throws DBIsNotAvailableException {
        Connection connection = connectionPool.getConnection();
        Currency currency;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM currencies WHERE Code = ?");
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();
            currency = getCurrency(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return currency;
    }
    private Currency getCurrency(ResultSet resultSet) {
        Currency currency = new Currency();
        try {
            currency.setId(resultSet.getInt("ID"));
            currency.setCode(resultSet.getString("Code"));
            currency.setName(resultSet.getString("FullName"));
            currency.setSign(resultSet.getString("Sign"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return currency;
    }

    public Currency saveCurrency(Currency currency) throws DBIsNotAvailableException, AlreadyExistsException {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO currencies (Code, FullName, Sign) VALUES (?, ?, ?)");

            statement.setString(1, currency.getCode());
            statement.setString(2, currency.getName());
            statement.setString(3, currency.getSign());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new AlreadyExistsException(new ErrorMessage("Валюта с таким кодом уже существует"));
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return currency = getCurrencyByCode(currency.getCode());
    }
}
