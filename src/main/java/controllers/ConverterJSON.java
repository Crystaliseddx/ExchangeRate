package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Currency;
import models.ExchangeRate;

public class ConverterJSON {
    private ObjectMapper objectMapper = new ObjectMapper();

    public String convertToJSON(Currency currency) {
        String json;
        try {
            json = objectMapper.writeValueAsString(currency);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public String convertToJSON(ExchangeRate exchangeRate) {
        String json;
        try {
            json = objectMapper.writeValueAsString(exchangeRate);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
