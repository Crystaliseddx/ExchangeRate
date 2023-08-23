package controllers;

import dto.CurrencyDTO;
import exceptions.CurrencyNotFoundException;
import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;
import exceptions.NoCurrencyCodeException;
import models.Currency;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.Reader;

@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);

        String[] urlArray = request.getRequestURL().toString().split("/");
        String url = urlArray[urlArray.length-1].toUpperCase();

        if (validation.isValidCurrencyCode(url)) {
            Currency currency;
            try {
                currency = currencyDAO.getCurrencyByCode(url);
                if (currency.getCode() == null) {
                    CurrencyNotFoundException e = new CurrencyNotFoundException(new ErrorMessage("Валюта не найдена"));
                    errorResponse.sendErrorResponse(response, e, 404);
                } else {
                    String json = converter.convertToJSON(mapper.getCurrencyDTO(currency));
                    successResponse.sendSuccessResponse(response, json);
                }
            } catch (DBIsNotAvailableException e) {
                errorResponse.sendErrorResponse(response, e, 500);
            }
        } else {
            NoCurrencyCodeException e= new NoCurrencyCodeException(new ErrorMessage("Код валюты отсутствует в адресе"));
            errorResponse.sendErrorResponse(response, e, 400);
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

        currency = currencyDAO.saveCurrency(currency);

        currencyDTO = mapper.getCurrencyDTO(currency);
        String json = converter.convertToJSON(currencyDTO);

    }
}
