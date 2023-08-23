package controllers;

import controllers.responses.ErrorResponse;
import controllers.responses.SuccessResponse;
import dao.CurrencyDAO;
import dao.ExchangeRateDAO;
import utils.ConverterJSON;
import utils.ModelMapper;
import utils.Validation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class BaseServlet extends HttpServlet {
    protected CurrencyDAO currencyDAO = new CurrencyDAO();
    protected ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
    protected ModelMapper mapper = new ModelMapper();
    protected ConverterJSON converter = new ConverterJSON();
    protected SuccessResponse successResponse = new SuccessResponse();
    protected ErrorResponse errorResponse = new ErrorResponse();
    protected Validation validation = new Validation();

    protected void configUTF(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
    }
}
