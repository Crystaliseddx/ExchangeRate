package utils;

import models.Currency;
import models.ExchangeRate;

import java.math.BigDecimal;

public class Validator {
    public boolean isValidCurrencyCode(String url) {
        boolean isValidCurrencyCode = true;
        if (url.length() != 3) {
            isValidCurrencyCode = false;
        } else if (url.matches(".*[^A-Z].*")) {
            isValidCurrencyCode = false;
        }
        return isValidCurrencyCode;
    }

    public boolean isValidCurrency(Currency currency) {
        boolean isValidCurrency = true;
        if (currency.getCode() == null || currency.getName() == null || currency.getSign() == null) {
            isValidCurrency = false;
        } else if (!isValidCurrencyCode(currency.getCode())) {
            isValidCurrency = false;
        }
        return isValidCurrency;
    }

    public boolean isValidCurrencyCodes(String currencyCodes) {
        boolean isValidCurrencyCodes = true;
        if (currencyCodes.length() != 6) {
            isValidCurrencyCodes = false;
        } else if (currencyCodes.matches(".*[^a-zA-Z].*")) {
            isValidCurrencyCodes = false;
        }
        return isValidCurrencyCodes;
    }

    public boolean isValidExchangeRate(ExchangeRate exchangeRate) {
        boolean isValidExchangeRate = true;
        if (exchangeRate.getBaseCurrency() == null || exchangeRate.getTargetCurrency() == null || exchangeRate.getRate() == null) {
            isValidExchangeRate = false;
        } else if (exchangeRate.getBaseCurrency().getCode() == null || exchangeRate.getTargetCurrency().getCode() == null) {
            isValidExchangeRate = false;
        } else if (!isValidCurrencyCode(exchangeRate.getBaseCurrency().getCode()) || !isValidCurrencyCode(exchangeRate.getTargetCurrency().getCode())) {
            isValidExchangeRate = false;
        } else if (exchangeRate.getRate().compareTo(BigDecimal.ZERO) == 0) {
            isValidExchangeRate = false;
        } else if (!String.valueOf(exchangeRate.getRate()).matches("\\d+(\\.\\d+)?")) {
            isValidExchangeRate = false;
        }
        return isValidExchangeRate;
    }

    public boolean isValidExchangeRateForUpd(ExchangeRate exchangeRate) {
        boolean isValidExchangeRateForUpd = true;
        if (exchangeRate.getRate() == null) {
            isValidExchangeRateForUpd = false;
        } else if (exchangeRate.getRate().compareTo(BigDecimal.ZERO) == 0) {
            isValidExchangeRateForUpd = false;
        } else if (!String.valueOf(exchangeRate.getRate()).matches("\\d+(\\.\\d+)?")) {
            isValidExchangeRateForUpd = false;
        }
        return isValidExchangeRateForUpd;
    }

    public boolean isValidExchangeRequest(String baseCurrencyCode, String targetCurrencyCode, String amount) {
        boolean isValidExchangeRequest = true;
        if (baseCurrencyCode == null || targetCurrencyCode == null || amount == null) {
            isValidExchangeRequest = false;
        } else if (!isValidCurrencyCode(baseCurrencyCode) || !isValidCurrencyCode(targetCurrencyCode)) {
            isValidExchangeRequest = false;
        } else if (amount.equals("0")) {
            isValidExchangeRequest = false;
        } else if (!amount.matches("\\d+(\\.\\d+)?")) {
            isValidExchangeRequest = false;
        }
        return isValidExchangeRequest;
    }
}
