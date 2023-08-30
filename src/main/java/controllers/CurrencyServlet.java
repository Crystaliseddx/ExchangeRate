package controllers;

import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;
import exceptions.InvalidRequestException;
import exceptions.NotFoundException;
import models.Currency;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);
        try {
            String[] urlArray = request.getRequestURL().toString().split("/");
            String url = urlArray[urlArray.length - 1].toUpperCase();
            if (!validator.isValidCurrencyCode(url)) {
                throw new InvalidRequestException(new ErrorMessage("Код валюты отсутствует в адресе"));
            }
            Optional<Currency> currencyOptional = currencyDAO.getCurrencyByCode(url);
            Currency currency = currencyOptional.orElseThrow(() -> new NotFoundException(new ErrorMessage("Валюта не найдена в БД")));
            String json = converter.convertToJSON(mapper.getCurrencyDTO(currency));
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
