package controllers;

import exceptions.*;
import models.Currency;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);

        String[] urlArray = request.getRequestURL().toString().split("/");
        String url = urlArray[urlArray.length-1].toUpperCase();

        if (validator.isValidCurrencyCode(url)) {
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
}
