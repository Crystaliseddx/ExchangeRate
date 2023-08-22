package dto.mappers;

import dto.CurrencyDTO;
import models.Currency;

public class CurrencyMapper {
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
}
