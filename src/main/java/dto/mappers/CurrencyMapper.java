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
}
