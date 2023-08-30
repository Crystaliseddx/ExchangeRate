package controllers;

import exceptions.DBIsNotAvailableException;
import exceptions.ErrorMessage;
import exceptions.InvalidRequestException;
import exceptions.NotFoundException;
import models.ExchangeRate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@WebServlet(name = "ExchangeRateServlet", value = "/exchangeRate/*")
public class ExchangeRateServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        configUTF(request, response);
        try {
            String[] urlArray = request.getRequestURL().toString().split("/");
            String currencyCodes = urlArray[urlArray.length - 1].toUpperCase();
            if (!validator.isValidCurrencyCodes(currencyCodes)) {
                throw new InvalidRequestException(new ErrorMessage("Коды валют пары отсутствуют в адресе"));
            }
            String baseCurrencyCode = currencyCodes.substring(0, 3);
            String targetCurrencyCode = currencyCodes.substring(3, 6);
            Optional<ExchangeRate> exchangeRateOptional = exchangeRateDAO.getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
            ExchangeRate exchangeRate = exchangeRateOptional.orElseThrow(() -> new NotFoundException(new ErrorMessage("Обменный курс для пары валют не найден")));
            String json = converter.convertToJSON(mapper.getExchangeRateDTO(exchangeRate));
            sendSuccessResponse(response, json);
        } catch (DBIsNotAvailableException e) {
            sendErrorResponse(response, e, 500);
        } catch (NotFoundException e) {
            sendErrorResponse(response, e, 404);
        } catch (InvalidRequestException e) {
            sendErrorResponse(response, e, 400);
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
        try {
            String[] urlArray = request.getRequestURL().toString().split("/");
            String currencyCodes = urlArray[urlArray.length - 1].toUpperCase();
            if (!validator.isValidCurrencyCodes(currencyCodes)) {
                throw new InvalidRequestException(new ErrorMessage("Коды валют пары отсутствуют в адресе"));
            }
            String baseCurrencyCode = currencyCodes.substring(0, 3);
            String targetCurrencyCode = currencyCodes.substring(3, 6);
            StringBuilder body = getSBFromJSON(request);
            ExchangeRate exchangeRate = mapper.getExchangeRate(converter.convertToExchangeRateDTO(body.toString()));
            if (!validator.isValidExchangeRateForUpd(exchangeRate)) {
                throw new InvalidRequestException(new ErrorMessage("Предоставлены некорректные данные для обновления валютного курса в БД"));
            }
            Optional<ExchangeRate> exchangeRateOptional = exchangeRateDAO.updateExchangeRate(exchangeRate, baseCurrencyCode, targetCurrencyCode);
            exchangeRate = exchangeRateOptional.orElseThrow(() -> new NotFoundException(new ErrorMessage("Обменный курс для пары не найден")));
            String json = converter.convertToJSON(mapper.getExchangeRateDTO(exchangeRate));
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
