package controllers;

import dto.ConvertedAmountDTO;
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
import java.math.BigDecimal;

@WebServlet(name = "ExchangeServlet", value = "/exchange")
public class ExchangeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);
        try {
            ExchangeService exchangeService = new ExchangeService();
            String baseCurrencyCode = request.getParameter("from");
            String targetCurrencyCode = request.getParameter("to");
            String amountStr = request.getParameter("amount");
            if (!validator.isValidExchangeRequest(baseCurrencyCode, targetCurrencyCode, amountStr)) {
                throw new InvalidRequestException(new ErrorMessage("Неккоректные параметры запроса"));
            }
            BigDecimal amount = new BigDecimal(amountStr);
            BigDecimal convertedAmount = exchangeService.exchangeCurrency(baseCurrencyCode, targetCurrencyCode, amount);
            BigDecimal rate = convertedAmount.divide(amount);
            Currency baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCode).get();
            Currency targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCode).get();
            ConvertedAmountDTO convertedAmountDTO = mapper.getConvertedAmountDTO(baseCurrency, targetCurrency, rate, amount, convertedAmount);
            String json = converter.convertToJSON(convertedAmountDTO);
            sendSuccessResponse(response, json);
        } catch (DBIsNotAvailableException e) {
            sendErrorResponse(response, e, 500);
        } catch (NotFoundException e) {
            sendErrorResponse(response, e, 404);
        } catch (InvalidRequestException e) {
            sendErrorResponse(response, e, 400);
        }
    }
}
