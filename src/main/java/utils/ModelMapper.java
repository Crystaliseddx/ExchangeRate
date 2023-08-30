package utils;

import dto.ConvertedAmountDTO;
import dto.CurrencyDTO;
import dto.ExchangeRateDTO;
import models.Currency;
import models.ExchangeRate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
        currency.setCode(currencyDTO.getCode());
        currency.setName(currencyDTO.getName());
        currency.setSign(currencyDTO.getSign());
        return currency;
    }

    public ExchangeRateDTO getExchangeRateDTO(ExchangeRate exchangeRate) {
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();
        exchangeRateDTO.setId(exchangeRate.getId());
        exchangeRateDTO.setBaseCurrency(exchangeRate.getBaseCurrency());
        exchangeRateDTO.setTargetCurrency(exchangeRate.getTargetCurrency());
        exchangeRateDTO.setRate(exchangeRate.getRate());
        return exchangeRateDTO;
    }

    public ExchangeRate getExchangeRate(ExchangeRateDTO exchangeRateDTO) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setBaseCurrency(exchangeRateDTO.getBaseCurrency());
        exchangeRate.setTargetCurrency(exchangeRateDTO.getTargetCurrency());
        exchangeRate.setRate(exchangeRateDTO.getRate());
        return exchangeRate;
    }

    public ConvertedAmountDTO getConvertedAmountDTO(Currency baseCurrency, Currency targetCurrency, BigDecimal rate, BigDecimal amount, BigDecimal convertedAmount) {
        ConvertedAmountDTO convertedAmountDTO = new ConvertedAmountDTO();
        convertedAmountDTO.setBaseCurrency(baseCurrency);
        convertedAmountDTO.setTargetCurrency(targetCurrency);
        convertedAmountDTO.setRate(rate.setScale(6, RoundingMode.HALF_EVEN));
        convertedAmountDTO.setAmount(amount.setScale(6, RoundingMode.HALF_EVEN));
        convertedAmountDTO.setConvertedAmount(convertedAmount.setScale(6, RoundingMode.HALF_EVEN));
        return convertedAmountDTO;
    }
}
