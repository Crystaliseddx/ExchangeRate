package dao;

import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;
import exceptions.NotFoundException;
import models.Currency;
import models.ExchangeRate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDAO {
    private static ConnectionPool connectionPool = ConnectionPool.createConnectionPool();

    public List<ExchangeRate> getExchangeRates() throws DBIsNotAvailableException {
        Connection connection = connectionPool.getConnection();
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT er.ID, er.BaseCurrencyID, er.TargetCurrencyID, er.Rate, " +
                            "bc.ID bcID, bc.Code bcCode, bc.FullName bcFullName, bc.Sign bcSign, " +
                            "tc.ID tcID, tc.Code tcCode, tc.FullName tcFullName, tc.Sign tcSign " +
                            "FROM exchangerates er " +
                            "JOIN currencies bc ON (er.BaseCurrencyID = bc.ID) " +
                            "JOIN currencies tc ON (er.TargetCurrencyID = tc.ID)");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ExchangeRate exchangeRate = getExchangeRate(resultSet);
                exchangeRates.add(exchangeRate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return exchangeRates;
    }

    public ExchangeRate getExchangeRateByCurrencyCodes(String baseCurrencyCode, String targetCurrencyCode) throws DBIsNotAvailableException {
        Connection connection = connectionPool.getConnection();
        ExchangeRate exchangeRate = new ExchangeRate();
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM exchangerates er " +
                            "JOIN currencies bc ON (er.BaseCurrencyID = bc.ID) " +
                            "JOIN currencies tc ON (er.TargetCurrencyID = tc.ID) " +
                            "WHERE bcCode = ? AND tcCode = ?");

            statement.setString(1, baseCurrencyCode);
            statement.setString(2, targetCurrencyCode);

            ResultSet resultSet = statement.executeQuery();
            exchangeRate = getExchangeRate(resultSet);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return exchangeRate;
    }

    private ExchangeRate getExchangeRate(ResultSet resultSet) {
        ExchangeRate exchangeRate = new ExchangeRate();
        Currency baseCurrency = new Currency();
        Currency targetCurrency = new Currency();

        try {
            baseCurrency.setId(resultSet.getInt("bcID"));
            baseCurrency.setCode(resultSet.getString("bcCode"));
            baseCurrency.setName(resultSet.getString("bcFullName"));
            baseCurrency.setSign(resultSet.getString("bcSign"));

            targetCurrency.setId(resultSet.getInt("tcID"));
            targetCurrency.setCode(resultSet.getString("tcCode"));
            targetCurrency.setName(resultSet.getString("tcFullName"));
            targetCurrency.setSign(resultSet.getString("tcSign"));

            exchangeRate.setId(resultSet.getInt("ID"));
            exchangeRate.setBaseCurrency(baseCurrency);
            exchangeRate.setTargetCurrency(targetCurrency);
            exchangeRate.setRate(resultSet.getDouble("Rate"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exchangeRate;
    }

    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) throws DBIsNotAvailableException, NotFoundException {
        Connection connection = connectionPool.getConnection();
        CurrencyDAO currencyDAO = new CurrencyDAO();
        String baseCurrencyCode = exchangeRate.getBaseCurrency().getCode();
        String targetCurrencyCode = exchangeRate.getTargetCurrency().getCode();
        Currency baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCode);
        Currency targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCode);
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO exchangerates (BaseCurrencyID, TargetCurrencyID, Rate) VALUES (?, ?, ?)");

            statement.setInt(1, baseCurrency.getId());
            statement.setInt(2, targetCurrency.getId());
            statement.setDouble(3, exchangeRate.getRate());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new NotFoundException(new ErrorMessage("Валютная пара с таким кодом уже существует"));
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return exchangeRate = getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
    }
    public ExchangeRate updateExchangeRate(ExchangeRate exchangeRate, String baseCurrencyCode, String targetCurrencyCode) throws DBIsNotAvailableException {
        Connection connection = connectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("UPDATE exchangerates SET Rate = ? FROM exchangerates er " +
                            "JOIN currencies bc ON (er.BaseCurrencyID = bc.ID) " +
                            "JOIN currencies tc ON (er.TargetCurrencyID = tc.ID) " +
                            "WHERE bc.Code = ? AND tc.Code = ?");


            statement.setDouble(1, exchangeRate.getRate());
            statement.setString(2, baseCurrencyCode);
            statement.setString(3, targetCurrencyCode);

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return getExchangeRateByCurrencyCodes
                (exchangeRate.getBaseCurrency().getCode(), exchangeRate.getTargetCurrency().getCode());
    }
}
