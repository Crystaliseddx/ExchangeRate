package dao;

import models.ExchangeRate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateDAO {
    private static Connection connection = ConnectionDB.getConnectionDB().getConnection();
    public List<ExchangeRate> getExchangeRates() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        try {
            ExchangeRate exchangeRate = new ExchangeRate();
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM exchangerates");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                exchangeRate.setId(resultSet.getInt("ID"));
                exchangeRate.setBaseCurrencyId(resultSet.getInt("BASECURRENCYID"));
                exchangeRate.setTargetCurrencyId(resultSet.getInt("TARGETCURRENCYID"));
                exchangeRate.setRate(resultSet.getDouble("RATE"));

                exchangeRates.add(exchangeRate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exchangeRates;
    }

    public ExchangeRate getExchangeRate(String baseCurrencyId, String targetCurrencyId) {
        ExchangeRate exchangeRate = new ExchangeRate();
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT * FROM exchangerates WHERE BASECURRENCYID = ? AND TARGETCURRENCYID = ?");

            statement.setString(1, baseCurrencyId);
            statement.setString(2, targetCurrencyId);

            ResultSet resultSet = statement.executeQuery();

            exchangeRate.setId(resultSet.getInt("ID"));
            exchangeRate.setBaseCurrencyId(resultSet.getInt("BASECURRENCYID"));
            exchangeRate.setTargetCurrencyId(resultSet.getInt("TARGETCURRENCYID"));
            exchangeRate.setRate(resultSet.getDouble("RATE"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return exchangeRate;
    }

    public void saveExchangeRate(ExchangeRate exchangeRate) {
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO exchangerates (BASECURRENCYID, TARGETCURRENCYID, RATE) VALUES (?, ?, ?)");

            statement.setInt(1, exchangeRate.getBaseCurrencyId());
            statement.setInt(2, exchangeRate.getTargetCurrencyId());
            statement.setDouble(3, exchangeRate.getRate());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateExchangeRate(ExchangeRate exchangeRate) {
        try {
            PreparedStatement statement = connection.prepareStatement
                    ("UPDATE exchangerates SET RATE = ? WHERE BASECURRENCYID = ? AND TARGETCURRENCYID = ?");

            statement.setDouble(1, exchangeRate.getRate());
            statement.setInt(2, exchangeRate.getBaseCurrencyId());
            statement.setInt(3, exchangeRate.getTargetCurrencyId());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
