package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CurrencyDTO;
import dto.ExchangeAmountDTO;
import dto.ExchangeRateDTO;
import exceptions.ErrorMessage;

public class JSONConverter {
    private ObjectMapper objectMapper = new ObjectMapper();

    public String convertToJSON(CurrencyDTO currencyDTO) {
        String json;
        try {
            json = objectMapper.writeValueAsString(currencyDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public String convertToJSON(ExchangeRateDTO exchangeRateDTO) {
        String json;
        try {
            json = objectMapper.writeValueAsString(exchangeRateDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
    public String convertToJSON(ExchangeAmountDTO exchangeAmountDTO) {
        String json;
        try {
            json = objectMapper.writeValueAsString(exchangeAmountDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
    public String convertToJSON(ErrorMessage message) {
        String json;
        try {
            json = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
    public CurrencyDTO convertToCurrencyDTO(String json) {
        CurrencyDTO currencyDTO;
        try {
            currencyDTO = objectMapper.readValue(json, CurrencyDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return currencyDTO;
    }
    public ExchangeRateDTO convertToExchangeRateDTO(String json) {
        ExchangeRateDTO exchangeRateDTO;
        try {
            exchangeRateDTO = objectMapper.readValue(json, ExchangeRateDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return exchangeRateDTO;
    }
}
