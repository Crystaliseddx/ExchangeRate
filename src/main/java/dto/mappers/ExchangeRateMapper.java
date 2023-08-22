package dto.mappers;

import dao.CurrencyDAO;
import dao.ExchangeRateDAO;
import dto.CurrencyDTO;
import dto.ExchangeRateDTO;
import models.ExchangeRate;

public class ExchangeRateMapper {
    public ExchangeRateDTO getExchangeRateDTO (ExchangeRate exchangeRate) {
        ExchangeRateDTO exchangeRateDTO = new ExchangeRateDTO();

        exchangeRateDTO.setId(exchangeRate.getId());
        exchangeRateDTO.setBaseCurrencyId(exchangeRate.getBaseCurrencyId());
        exchangeRateDTO.setBaseCurrency(exchangeRate.getBaseCurrency());
        exchangeRateDTO.setTargetCurrencyId(exchangeRate.getTargetCurrencyId());
        exchangeRateDTO.setTargetCurrency(exchangeRate.getTargetCurrency());
        exchangeRateDTO.setRate(exchangeRate.getRate());

        return exchangeRateDTO;
    }
    public  ExchangeRate getExchangeRate(ExchangeRateDTO exchangeRateDTO) {
        ExchangeRate exchangeRate = new ExchangeRate();

        exchangeRate.setId(exchangeRateDTO.getId());
        exchangeRate.setBaseCurrencyId(exchangeRateDTO.getBaseCurrencyId());
        exchangeRate.setBaseCurrency(exchangeRateDTO.getBaseCurrency());
        exchangeRate.setTargetCurrencyId(exchangeRateDTO.getTargetCurrencyId());
        exchangeRate.setTargetCurrency(exchangeRateDTO.getTargetCurrency());
        exchangeRate.setRate(exchangeRateDTO.getRate());

        return exchangeRate;
    }
}
