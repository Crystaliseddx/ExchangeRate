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
}
