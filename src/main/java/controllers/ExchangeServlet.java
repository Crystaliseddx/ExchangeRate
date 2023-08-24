package controllers;

import dto.ExchangeAmountDTO;
import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;
import exceptions.InvalidRequestException;
import exceptions.NotFoundException;
import models.Currency;
import services.ExchangeService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ExchangeServlet", value = "/exchange")
public class ExchangeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExchangeService exchangeService = new ExchangeService();

        String baseCurrencyCode = request.getParameter("from");
        String targetCurrencyCode = request.getParameter("to");
        String amountStr = request.getParameter("amount");

        if (validator.isValidExchangeRequest(baseCurrencyCode, targetCurrencyCode, amountStr)) {
            try {
                double amount = Double.parseDouble(amountStr);
                double convertedAmount = exchangeService.exchangeCurrency(baseCurrencyCode, targetCurrencyCode, amount);
                double rate = amount / convertedAmount;
                Currency baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCode);
                Currency targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCode);
                ExchangeAmountDTO exchangeAmountDTO = mapper.getExchangeAmountDTO(baseCurrency, targetCurrency, rate, amount, convertedAmount);
                String json = converter.convertToJSON(exchangeAmountDTO);
                successResponse.sendSuccessResponse(response, json);
            } catch (DBIsNotAvailableException e) {
                errorResponse.sendErrorResponse(response, e, 500);
            } catch (NotFoundException e) {
                errorResponse.sendErrorResponse(response, e, 404);
            }
        } else {
            InvalidRequestException e = new InvalidRequestException(new ErrorMessage("Неккоректные параметры запроса"));
            errorResponse.sendErrorResponse(response, e, 400);
        }
    }
}
