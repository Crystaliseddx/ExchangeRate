package controllers;

import dto.ExchangeRateDTO;
import exceptions.*;
import models.ExchangeRate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

@WebServlet(name = "ExchangeRateServlet", value = "/exchangeRate/*")
public class ExchangeRateServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);

        String[] urlArray = request.getRequestURL().toString().split("/");
        String currencyCodes = urlArray[urlArray.length-1].toUpperCase();
        if (validator.isValidCurrencyPair(currencyCodes)) {
            String baseCurrencyCode = currencyCodes.substring(0, 3);
            String targetCurrencyCode = currencyCodes.substring(3, 6);
            ExchangeRate exchangeRate;
            try {
                exchangeRate = exchangeRateDAO.getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
                if (exchangeRate.getRate() == 0) {
                    NotFoundException e = new NotFoundException(new ErrorMessage("Обменный курс для пары не найден"));
                    errorResponse.sendErrorResponse(response, e, 404);
                } else {
                    String json = converter.convertToJSON(mapper.getExchangeRateDTO(exchangeRate));
                    successResponse.sendSuccessResponse(response, json);
                }
            } catch (DBIsNotAvailableException e) {
                errorResponse.sendErrorResponse(response, e, 500);
            }
        } else {
            InvalidRequestException e = new InvalidRequestException(new ErrorMessage("Коды валют пары отсутствуют в адресе"));
            errorResponse.sendErrorResponse(response, e, 400);
        }
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("PATCH".equals(request.getMethod())) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);
        String[] urlArray = request.getRequestURL().toString().split("/");
        String currencyCodes = urlArray[urlArray.length-1].toUpperCase();
        if (validator.isValidCurrencyPair(currencyCodes)) {
            String baseCurrencyCode = currencyCodes.substring(0, 3);
            String targetCurrencyCode = currencyCodes.substring(3, 6);
            StringBuilder body = new StringBuilder();
            char[] buffer = new char[1024];
            int readChars;

            try (Reader reader = request.getReader()){
                while ((readChars = reader.read(buffer)) != -1) {
                    body.append(buffer,0, readChars);
                }
            }
            ExchangeRate exchangeRate = mapper.getExchangeRate(converter.convertToExchangeRateDTO(body.toString()));
            if (validator.isValidExchangeRateForUpd(exchangeRate)) {
                try {
                    exchangeRate = exchangeRateDAO.updateExchangeRate(exchangeRate, baseCurrencyCode, targetCurrencyCode);
                    if (exchangeRate.getRate() == 0) {
                        NotFoundException e = new NotFoundException(new ErrorMessage("Обменный курс для пары не найден"));
                        errorResponse.sendErrorResponse(response, e, 404);
                    } else {
                        String json = converter.convertToJSON(mapper.getExchangeRateDTO(exchangeRate));
                        successResponse.sendSuccessResponse(response, json);
                    }
                } catch (DBIsNotAvailableException e) {
                    errorResponse.sendErrorResponse(response, e, 500);
                }
            } else {
                InvalidRequestException e = new InvalidRequestException(new ErrorMessage
                        ("Предоставлено недостаточно информации для внесения валюты в БД"));
                errorResponse.sendErrorResponse(response, e, 400);
            }
        } else {
            InvalidRequestException e = new InvalidRequestException(new ErrorMessage("Коды валют пары отсутствуют в адресе"));
            errorResponse.sendErrorResponse(response, e, 400);
        }
    }
}
