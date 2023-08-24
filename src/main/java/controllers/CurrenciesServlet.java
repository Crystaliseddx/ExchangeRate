package controllers;

import dto.CurrencyDTO;
import exceptions.AlreadyExistsException;
import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;
import exceptions.InvalidRequestException;
import models.Currency;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);
        List<Currency> currencies = new ArrayList<>();
        try {
            currencies = currencyDAO.getCurrencies();
            String[] jsonArray = new String[currencies.size()];
            for (int i = 0; i < jsonArray.length; i++) {
                jsonArray[i] = converter.convertToJSON(mapper.getCurrencyDTO(currencies.get(i)));
            }
            sendSuccessResponse(response, jsonArray);
        } catch (DBIsNotAvailableException e) {
            sendErrorResponse(response, e, 500);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);
        StringBuilder body = new StringBuilder();
        char[] buffer = new char[1024];
        int readChars;
        try(Reader reader = request.getReader()){
            while ((readChars = reader.read(buffer)) != -1) {
                body.append(buffer,0, readChars);
            }
        }
        CurrencyDTO currencyDTO = converter.convertToCurrencyDTO(body.toString());
        Currency currency = mapper.getCurrency(currencyDTO);
        if (validator.isValidCurrency(currency)) {
            try {
                currency = currencyDAO.saveCurrency(currency);
                currencyDTO = mapper.getCurrencyDTO(currency);
                String json = converter.convertToJSON(currencyDTO);
                sendSuccessResponse(response, json);
            } catch (DBIsNotAvailableException e) {
                sendErrorResponse(response, e, 500);
            } catch (AlreadyExistsException e) {
                sendErrorResponse(response, e, 409);
            }
        } else {
            InvalidRequestException e = new InvalidRequestException(new ErrorMessage
                    ("Предоставлено недостаточно информации для внесения валюты в БД"));
            sendErrorResponse(response, e, 400);
        }
    }
}
