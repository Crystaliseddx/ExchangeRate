package dao;

import exceptions.AlreadyExistsException;
import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;
import models.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyDAO {
    public List<Currency> getCurrencies() throws DBIsNotAvailableException {
        ConnectionPool.checkConnectionPool();
        Connection connection = ConnectionPool.getConnection();
        List<Currency> currencies = new ArrayList<>();
        boolean resultSetIsNotEmpty = true;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM currencies");
            ResultSet resultSet = statement.executeQuery();
            while (resultSetIsNotEmpty) {
                Optional<Currency> currencyOptional = getCurrency(resultSet);
                if (currencyOptional.isEmpty()) {
                    resultSetIsNotEmpty = false;
                    break;
                }
                currencies.add(currencyOptional.get());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
        return currencies;
    }

    public Optional<Currency> getCurrencyByCode(String code) throws DBIsNotAvailableException {
        ConnectionPool.checkConnectionPool();
        Connection connection = ConnectionPool.getConnection();
        Optional<Currency> currencyOptional;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM currencies WHERE Code = ?");
            statement.setString(1, code);
            ResultSet resultSet = statement.executeQuery();
            currencyOptional = getCurrency(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
        return currencyOptional;
    }

    private Optional<Currency> getCurrency(ResultSet resultSet) {
        try {
            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                Currency currency = new Currency();
                currency.setId(resultSet.getInt("ID"));
                currency.setCode(resultSet.getString("Code"));
                currency.setName(resultSet.getString("FullName"));
                currency.setSign(resultSet.getString("Sign"));
                return Optional.of(currency);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Currency saveCurrency(Currency currency) throws DBIsNotAvailableException, AlreadyExistsException {
        ConnectionPool.checkConnectionPool();
        Connection connection = ConnectionPool.getConnection();
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
            ConnectionPool.releaseConnection(connection);
        }
        return getCurrencyByCode(currency.getCode()).get();
    }
}
