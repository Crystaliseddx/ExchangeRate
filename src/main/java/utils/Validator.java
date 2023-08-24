package utils;

import models.Currency;
import models.ExchangeRate;

public class Validator {
    public boolean isValidCurrencyCode(String url) {
        boolean isValidCurrencyCode = true;
        if (url.length() != 3) {
            isValidCurrencyCode = false;
        } else if (!url.matches("[a-zA-Z]")) {
            isValidCurrencyCode = false;
        }
        return isValidCurrencyCode;
    }
    public boolean isValidCurrency(Currency currency) {
        boolean isValidCurrency = true;
        if (currency.getCode() == null) {
            isValidCurrency = false;
        } else if(currency.getName() == null) {
            isValidCurrency = false;
        } else if (currency.getSign() == null) {
            isValidCurrency = false;
        }
        return isValidCurrency;
    }
    public boolean isValidCurrencyPair(String currencyCodes) {
        boolean isValidCurrencyPair = true;
        if (currencyCodes.length() != 6) {
            isValidCurrencyPair = false;
        } else if (!currencyCodes.matches("[a-zA-Z]")) {
            isValidCurrencyPair = false;
        }
        return isValidCurrencyPair;
    }
    public boolean isValidExchangeRate(ExchangeRate exchangeRate) {
        boolean isValidExchangeRate = true;
        if (exchangeRate.getBaseCurrency().getCode() == null) {
            isValidExchangeRate = false;
        } else if (exchangeRate.getTargetCurrency().getCode() == null) {
            isValidExchangeRate = false;
        } else if (exchangeRate.getRate() == 0) {
            isValidExchangeRate = false;
        }
        return isValidExchangeRate;
    }
    public boolean isValidExchangeRateForUpd(ExchangeRate exchangeRate) {
        boolean isValidExchangeRateForUpd = true;
        if (exchangeRate.getRate() == 0) {
            isValidExchangeRateForUpd = false;
        }
        return isValidExchangeRateForUpd;
    }
    public boolean isValidExchangeRequest(String baseCurrencyCode, String targetCurrencyCode, String amount) {
        boolean isValidExchangeRequest = true;
        if (baseCurrencyCode.length() != 3 || targetCurrencyCode.length() != 3) {
            isValidExchangeRequest = false;
        } else if (!baseCurrencyCode.matches("[a-zA-Z]") || !targetCurrencyCode.matches("[a-zA-Z]")) {
            isValidExchangeRequest = false;
        } else if (!amount.matches("\\d+(\\.\\d+)?")) {
            isValidExchangeRequest = false;
        } else if (Double.parseDouble(amount) <= 0) {
            isValidExchangeRequest = false;
        }
        return isValidExchangeRequest;
    }
}
