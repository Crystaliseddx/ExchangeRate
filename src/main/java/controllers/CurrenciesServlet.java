package controllers;

import dto.CurrencyDTO;
import exceptions.*;
import models.Currency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);
        try {
            List<Currency> currencies;
            currencies = currencyDAO.getCurrencies();
            if (currencies.size() == 0) {
                throw new NotFoundException(new ErrorMessage("В БД отсутствуют валюты"));
            }
            String[] jsonArray = new String[currencies.size()];
            for (int i = 0; i < jsonArray.length; i++) {
                jsonArray[i] = converter.convertToJSON(mapper.getCurrencyDTO(currencies.get(i)));
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
            Currency currency = mapper.getCurrency(converter.convertToCurrencyDTO(body.toString()));
            if (!validator.isValidCurrency(currency)) {
                throw new InvalidRequestException(new ErrorMessage("Предоставлены некорректные данные для внесения валюты в БД"));
            }
            currency = currencyDAO.saveCurrency(currency);
            String json = converter.convertToJSON(mapper.getCurrencyDTO(currency));
            sendSuccessResponse(response, json);
        } catch (DBIsNotAvailableException e) {
            sendErrorResponse(response, e, 500);
        } catch (AlreadyExistsException e) {
            sendErrorResponse(response, e, 409);
        } catch (InvalidRequestException e) {
            sendErrorResponse(response, e, 400);
        }
    }
}
