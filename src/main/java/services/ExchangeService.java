package services;

import dao.ExchangeRateDAO;
import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;
import exceptions.NotFoundException;
import models.ExchangeRate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

public class ExchangeService {
    public BigDecimal exchangeCurrency(String baseCurrencyCode, String targetCurrencyCode, BigDecimal amount) throws DBIsNotAvailableException, NotFoundException {
        ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
        Optional<ExchangeRate> exchangeRateOptional = exchangeRateDAO.getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
        Optional<ExchangeRate> reverseExchangeRateOptional = exchangeRateDAO.getExchangeRateByCurrencyCodes(targetCurrencyCode, baseCurrencyCode);
        Optional<ExchangeRate> exchangeRateUSDBaseOptional = exchangeRateDAO.getExchangeRateByCurrencyCodes("USD", baseCurrencyCode);
        Optional<ExchangeRate> exchangeRateUSDTargetOptional = exchangeRateDAO.getExchangeRateByCurrencyCodes("USD", targetCurrencyCode);
        BigDecimal convertedAmount;
        if (exchangeRateOptional.isPresent()) {
            convertedAmount = amount.multiply(exchangeRateOptional.get().getRate());
        } else if (reverseExchangeRateOptional.isPresent()) {
            convertedAmount = amount.divide(reverseExchangeRateOptional.get().getRate(), MathContext.DECIMAL128);
        } else if (exchangeRateUSDBaseOptional.isPresent() && exchangeRateUSDTargetOptional.isPresent()) {
            convertedAmount = amount.divide(exchangeRateUSDBaseOptional.get().getRate(), MathContext.DECIMAL128).multiply(exchangeRateUSDTargetOptional.get().getRate());
        } else {
            throw new NotFoundException(new ErrorMessage("Курс обмена для указанных валют отсутствует в БД"));
        }
        return convertedAmount;
    }
}
