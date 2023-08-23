package controllers;

import exceptions.DBIsNotAvailableException;
import models.Currency;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
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
        } catch (DBIsNotAvailableException e) {
            errorResponse.sendErrorResponse(response, e, 500);
        }
        String[] jsonArray = new String[currencies.size()];
        for (int i = 0; i < jsonArray.length; i++) {
            jsonArray[i] = converter.convertToJSON(mapper.getCurrencyDTO(currencies.get(i)));
        }
        successResponse.sendSuccessResponse(response, jsonArray);
    }
}
