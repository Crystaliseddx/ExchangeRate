package services;

import dao.ExchangeRateDAO;
import models.ExchangeRate;

import java.sql.SQLException;

public class ExchangeService {
//    public double exchangeCurrency(String baseCurrencyCode, String targetCurrencyCode, double amount) {
//        ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
//        ExchangeRate exchangeRate = new ExchangeRate();
//        double convertedAmount = 0;
//        if (exchangeRateDAO.getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode) != null) {
//            exchangeRate = exchangeRateDAO.getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
//            convertedAmount = amount * exchangeRate.getRate();
//        } else if (exchangeRateDAO.getExchangeRateByCurrencyCodes(targetCurrencyCode, baseCurrencyCode) != null) {
//            exchangeRate = exchangeRateDAO.getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
//            convertedAmount = amount / exchangeRate.getRate();
//        } else if (exchangeRateDAO.getExchangeRateByCurrencyCodes("USD", baseCurrencyCode) != null
//                && exchangeRateDAO.getExchangeRateByCurrencyCodes("USD", targetCurrencyCode) != null) {
//            ExchangeRate exchangeRateToUSD = exchangeRateDAO.getExchangeRateByCurrencyCodes
//                    ("USD", baseCurrencyCode);
//            ExchangeRate exchangeRateFromUSD = exchangeRateDAO.getExchangeRateByCurrencyCodes
//                    ("USD", targetCurrencyCode);
//            convertedAmount = amount / exchangeRateToUSD.getRate() * exchangeRateFromUSD.getRate();
//        }
//        return convertedAmount;
//    }
}
