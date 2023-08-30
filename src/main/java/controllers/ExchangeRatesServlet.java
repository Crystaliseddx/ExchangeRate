package controllers;

import dto.ExchangeRateDTO;
import exceptions.*;
import models.Currency;
import models.ExchangeRate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "ExchangeRatesServlet", value = "/exchangeRates")
public class ExchangeRatesServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);
        try {
            List<ExchangeRate> exchangeRates;
            exchangeRates = exchangeRateDAO.getExchangeRates();
            if (exchangeRates.size() == 0) {
                throw new NotFoundException(new ErrorMessage("В БД отсутствуют обменные курсы"));
            }
            String[] jsonArray = new String[exchangeRates.size()];
            for (int i = 0; i < jsonArray.length; i++) {
                jsonArray[i] = converter.convertToJSON(mapper.getExchangeRateDTO(exchangeRates.get(i)));
            }
            sendSuccessResponse(response, jsonArray);
        } catch (DBIsNotAvailableException e) {
            sendErrorResponse(response, e, 500);
        } catch (NotFoundException e) {
            sendErrorResponse(response, e, 404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);
        try {
            StringBuilder body = getSBFromJSON(request);
            ExchangeRate exchangeRate = mapper.getExchangeRate(converter.convertToExchangeRateDTO(body.toString()));
            if (!validator.isValidExchangeRate(exchangeRate)) {
                throw new InvalidRequestException(new ErrorMessage("Предоставлены некорректные данные для внесения валютного курса в БД"));
            }
            String baseCurrencyCode = exchangeRate.getBaseCurrency().getCode();
            String targetCurrencyCode = exchangeRate.getTargetCurrency().getCode();
            Optional<Currency> baseCurrencyOptional = currencyDAO.getCurrencyByCode(baseCurrencyCode);
            Optional<Currency> targetCurrencyOptional = currencyDAO.getCurrencyByCode(targetCurrencyCode);
            int baseCurrencyID;
            int targetCurrencyID;
            if (baseCurrencyOptional.isEmpty()) {
                baseCurrencyID = -1;
            } else {
                baseCurrencyID = baseCurrencyOptional.get().getId();
            }
            if (targetCurrencyOptional.isEmpty()) {
                targetCurrencyID = -1;
            } else {
                targetCurrencyID = targetCurrencyOptional.get().getId();
            }
            exchangeRate = exchangeRateDAO.saveExchangeRate(exchangeRate, baseCurrencyID, targetCurrencyID);
            String json = converter.convertToJSON(mapper.getExchangeRateDTO(exchangeRate));
            sendSuccessResponse(response, json);
        } catch (DBIsNotAvailableException e) {
            sendErrorResponse(response, e, 500);
        } catch (AlreadyExistsException e) {
            sendErrorResponse(response, e, 409);
        } catch (NotFoundException e) {
            sendErrorResponse(response, e, 404);
        } catch (InvalidRequestException e) {
            sendErrorResponse(response, e, 400);
        }
    }
}
