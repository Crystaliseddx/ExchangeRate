package dao;

import exceptions.AlreadyExistsException;
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
import java.util.Optional;

public class ExchangeRateDAO {
    public List<ExchangeRate> getExchangeRates() throws DBIsNotAvailableException {
        ConnectionPool.checkConnectionPool();
        Connection connection = ConnectionPool.getConnection();
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        boolean resultSetIsNotEmpty = true;
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT er.ID erID, er.BaseCurrencyID, er.TargetCurrencyID, er.Rate erRate, " +
                            "bc.ID bcID, bc.Code bcCode, bc.FullName bcFullName, bc.Sign bcSign, " +
                            "tc.ID tcID, tc.Code tcCode, tc.FullName tcFullName, tc.Sign tcSign " +
                            "FROM exchangerates er " +
                            "JOIN currencies bc ON (er.BaseCurrencyID = bc.ID) " +
                            "JOIN currencies tc ON (er.TargetCurrencyID = tc.ID)");

            ResultSet resultSet = statement.executeQuery();
            while (resultSetIsNotEmpty) {
                Optional<ExchangeRate> exchangeRateOptional = getExchangeRate(resultSet);
                if (exchangeRateOptional.isEmpty()) {
                    resultSetIsNotEmpty = false;
                    break;
                }
                exchangeRates.add(exchangeRateOptional.get());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
        return exchangeRates;
    }

    public Optional<ExchangeRate> getExchangeRateByCurrencyCodes(String baseCurrencyCode, String targetCurrencyCode) throws DBIsNotAvailableException {
        ConnectionPool.checkConnectionPool();
        Connection connection = ConnectionPool.getConnection();
        Optional<ExchangeRate> exchangeRateOptional;
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT er.ID erID, er.BaseCurrencyID, er.TargetCurrencyID, er.Rate erRate, " +
                            "bc.ID bcID, bc.Code bcCode, bc.FullName bcFullName, bc.Sign bcSign, " +
                            "tc.ID tcID, tc.Code tcCode, tc.FullName tcFullName, tc.Sign tcSign " +
                            "FROM exchangerates er " +
                            "JOIN currencies bc ON (er.BaseCurrencyID = bc.ID) " +
                            "JOIN currencies tc ON (er.TargetCurrencyID = tc.ID) " +
                            "WHERE bcCode = ? AND tcCode = ?");

            statement.setString(1, baseCurrencyCode);
            statement.setString(2, targetCurrencyCode);
            ResultSet resultSet = statement.executeQuery();
            exchangeRateOptional = getExchangeRate(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
        return exchangeRateOptional;
    }

    private Optional<ExchangeRate> getExchangeRate(ResultSet resultSet) {
        ExchangeRate exchangeRate = new ExchangeRate();
        Currency baseCurrency = new Currency();
        Currency targetCurrency = new Currency();
        try {
            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                baseCurrency.setId(resultSet.getInt("bcID"));
                baseCurrency.setCode(resultSet.getString("bcCode"));
                baseCurrency.setName(resultSet.getString("bcFullName"));
                baseCurrency.setSign(resultSet.getString("bcSign"));

                targetCurrency.setId(resultSet.getInt("tcID"));
                targetCurrency.setCode(resultSet.getString("tcCode"));
                targetCurrency.setName(resultSet.getString("tcFullName"));
                targetCurrency.setSign(resultSet.getString("tcSign"));

                exchangeRate.setId(resultSet.getInt("erID"));
                exchangeRate.setBaseCurrency(baseCurrency);
                exchangeRate.setTargetCurrency(targetCurrency);
                exchangeRate.setRate(resultSet.getBigDecimal("erRate"));

                return Optional.of(exchangeRate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate, int baseCurrencyID, int targetCurrencyID) throws DBIsNotAvailableException, AlreadyExistsException, NotFoundException {
        ConnectionPool.checkConnectionPool();
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO exchangerates (BaseCurrencyID, TargetCurrencyID, Rate) VALUES (?, ?, ?)");

            statement.setInt(1, baseCurrencyID);
            statement.setInt(2, targetCurrencyID);
            statement.setBigDecimal(3, exchangeRate.getRate());
            statement.executeUpdate();
        } catch (SQLException e) {
            if (baseCurrencyID == -1 && targetCurrencyID == -1) {
                throw new NotFoundException(new ErrorMessage("Базовая и целевая валюты отсутствуют в БД"));
            } else if (baseCurrencyID == -1) {
                throw new NotFoundException(new ErrorMessage("Базовая валюта отсутствует в БД"));
            } else if (targetCurrencyID == -1) {
                throw new NotFoundException(new ErrorMessage("Целевая валюта отсутствует в БД"));
            } else {
                throw new AlreadyExistsException(new ErrorMessage("Валютная пара с таким кодом уже существует"));
            }
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
        String baseCurrencyCode = exchangeRate.getBaseCurrency().getCode();
        String targetCurrencyCode = exchangeRate.getTargetCurrency().getCode();
        return getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode).get();
    }

    public Optional<ExchangeRate> updateExchangeRate(ExchangeRate exchangeRate, String baseCurrencyCode, String targetCurrencyCode) throws DBIsNotAvailableException {
        ConnectionPool.checkConnectionPool();
        Connection connection = ConnectionPool.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("UPDATE exchangerates SET Rate = ? FROM exchangerates er " +
                            "JOIN currencies bc ON (er.BaseCurrencyID = bc.ID) " +
                            "JOIN currencies tc ON (er.TargetCurrencyID = tc.ID) " +
                            "WHERE bc.Code = ? AND tc.Code = ? " +
                            "AND exchangerates.BaseCurrencyID = er.BaseCurrencyID " +
                            "AND exchangerates.TargetCurrencyID = er.TargetCurrencyID");

            statement.setBigDecimal(1, exchangeRate.getRate());
            statement.setString(2, baseCurrencyCode);
            statement.setString(3, targetCurrencyCode);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionPool.releaseConnection(connection);
        }
        return getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
    }
}
