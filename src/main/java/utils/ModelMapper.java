package utils;

import dto.CurrencyDTO;
import dto.ExchangeAmountDTO;
import dto.ExchangeRateDTO;
import models.Currency;
import models.ExchangeRate;

public class ModelMapper {
    public CurrencyDTO getCurrencyDTO(Currency currency) {
        CurrencyDTO currencyDTO = new CurrencyDTO();

        currencyDTO.setId(currency.getId());
        currencyDTO.setCode(currency.getCode());
        currencyDTO.setName(currency.getName());
        currencyDTO.setSign(currency.getSign());

        return currencyDTO;
    }
    public Currency getCurrency(CurrencyDTO currencyDTO) {
        Currency currency = new Currency();

        currency.setId(currencyDTO.getId());
        currency.setCode(currencyDTO.getCode());
        currency.setName(currencyDTO.getName());
        currency.setSign(currencyDTO.getSign());

        return currency;
    }
    public ExchangeRateDTO getExchangeRateDTO (ExchangeRate exchangeRate) {
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();

        exchangeRateDTO.setId(exchangeRate.getId());
        exchangeRateDTO.setBaseCurrency(exchangeRate.getBaseCurrency());
        exchangeRateDTO.setTargetCurrency(exchangeRate.getTargetCurrency());
        exchangeRateDTO.setRate(exchangeRate.getRate());

        return exchangeRateDTO;
    }
    public ExchangeRate getExchangeRate(ExchangeRateDTO exchangeRateDTO) {
        ExchangeRate exchangeRate = new ExchangeRate();

        exchangeRate.setId(exchangeRateDTO.getId());
        exchangeRate.setBaseCurrency(exchangeRateDTO.getBaseCurrency());
        exchangeRate.setTargetCurrency(exchangeRateDTO.getTargetCurrency());
        exchangeRate.setRate(exchangeRateDTO.getRate());

        return exchangeRate;
    }
    public ExchangeAmountDTO getExchangeAmountDTO
            (Currency baseCurrency, Currency targetCurrency, double rate, double amount, double covertedAmount) {
        ExchangeAmountDTO exchangeAmountDTO = new ExchangeAmountDTO();

        exchangeAmountDTO.setBaseCurrency(baseCurrency);
        exchangeAmountDTO.setTargetCurrency(targetCurrency);
        exchangeAmountDTO.setRate(rate);
        exchangeAmountDTO.setAmount(amount);
        exchangeAmountDTO.setConvertedAmount(covertedAmount);

        return exchangeAmountDTO;
    }
}
