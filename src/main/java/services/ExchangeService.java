package services;

import dao.ExchangeRateDAO;
import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;
import exceptions.NotFoundException;
import models.ExchangeRate;

public class ExchangeService {
    public double exchangeCurrency(String baseCurrencyCode, String targetCurrencyCode, double amount) throws DBIsNotAvailableException, NotFoundException {
        ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
        ExchangeRate exchangeRate = new ExchangeRate();
        double convertedAmount = 0;
        if (!(exchangeRateDAO.getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode).getRate() == 0)) {
            exchangeRate = exchangeRateDAO.getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
            convertedAmount = amount * exchangeRate.getRate();
        } else if (!(exchangeRateDAO.getExchangeRateByCurrencyCodes(targetCurrencyCode, baseCurrencyCode).getRate() == 0)) {
            exchangeRate = exchangeRateDAO.getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
            convertedAmount = amount / exchangeRate.getRate();
        } else if (!(exchangeRateDAO.getExchangeRateByCurrencyCodes("USD", baseCurrencyCode).getRate() == 0)
                && !(exchangeRateDAO.getExchangeRateByCurrencyCodes("USD", targetCurrencyCode).getRate() == 0)) {
            ExchangeRate exchangeRateToUSD = exchangeRateDAO.getExchangeRateByCurrencyCodes
                    ("USD", baseCurrencyCode);
            ExchangeRate exchangeRateFromUSD = exchangeRateDAO.getExchangeRateByCurrencyCodes
                    ("USD", targetCurrencyCode);
            convertedAmount = amount / exchangeRateToUSD.getRate() * exchangeRateFromUSD.getRate();
        } else {
            throw new NotFoundException(new ErrorMessage("Одна из валют или курс обмена для этих валют отсутствуют в БД"));
        }
        return convertedAmount;
    }
}
