package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ConvertedAmountDTO;
import dto.CurrencyDTO;
import dto.ExchangeRateDTO;
import exceptions.ErrorMessage;
import exceptions.InvalidRequestException;

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

    public String convertToJSON(ConvertedAmountDTO convertedAmountDTO) {
        String json;
        try {
            json = objectMapper.writeValueAsString(convertedAmountDTO);
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

    public CurrencyDTO convertToCurrencyDTO(String json) throws InvalidRequestException {
        CurrencyDTO currencyDTO;
        try {
            currencyDTO = objectMapper.readValue(json, CurrencyDTO.class);
        } catch (JsonProcessingException e) {
            throw new InvalidRequestException(new ErrorMessage("Предоставлены некорректные данные для внесения валюты в БД"));
        }
        return currencyDTO;
    }

    public ExchangeRateDTO convertToExchangeRateDTO(String json) throws InvalidRequestException {
        ExchangeRateDTO exchangeRateDTO;
        try {
            exchangeRateDTO = objectMapper.readValue(json, ExchangeRateDTO.class);
        } catch (JsonProcessingException e) {
            throw new InvalidRequestException(new ErrorMessage("Предоставлены некорректные данные для внесения/обновления валютного курса в БД"));
        }
        return exchangeRateDTO;
    }
}
