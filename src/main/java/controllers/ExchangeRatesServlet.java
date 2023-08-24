package controllers;

import dto.ExchangeRateDTO;
import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;
import exceptions.InvalidRequestException;
import exceptions.NotFoundException;
import models.ExchangeRate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ExchangeRatesServlet", value = "/exchangeRates")
public class ExchangeRatesServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        try {
            exchangeRates = exchangeRateDAO.getExchangeRates();
            String[] jsonArray = new String[exchangeRates.size()];
            for (int i = 0; i < jsonArray.length; i++) {
                jsonArray[i] = converter.convertToJSON(mapper.getExchangeRateDTO(exchangeRates.get(i)));
            }
            successResponse.sendSuccessResponse(response, jsonArray);
        } catch (DBIsNotAvailableException e) {
            errorResponse.sendErrorResponse(response, e, 500);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);
        StringBuilder body = new StringBuilder();
        char[] buffer = new char[1024];
        int readChars;
        try (Reader reader = request.getReader()){
            while ((readChars = reader.read(buffer)) != -1) {
                body.append(buffer,0, readChars);
            }
        }
        ExchangeRateDTO exchangeRateDTO = converter.convertToExchangeRateDTO(body.toString());
        ExchangeRate exchangeRate = mapper.getExchangeRate(exchangeRateDTO);
        if (validator.isValidExchangeRate(exchangeRate)) {
            try {
                exchangeRate = exchangeRateDAO.saveExchangeRate(exchangeRate);
                exchangeRateDTO = mapper.getExchangeRateDTO(exchangeRate);
                String json = converter.convertToJSON(exchangeRateDTO);
            } catch (DBIsNotAvailableException e) {
                errorResponse.sendErrorResponse(response, e, 500);
            } catch (NotFoundException e) {
                errorResponse.sendErrorResponse(response, e, 409);
            }
        } else {
            InvalidRequestException e = new InvalidRequestException
                    (new ErrorMessage("Предоставлено недостаточно информации для внесения валютного курса в БД"));
            errorResponse.sendErrorResponse(response, e, 400);
        }
    }
}
