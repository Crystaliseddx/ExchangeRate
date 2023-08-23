package controllers;

import controllers.responses.ErrorResponse;
import controllers.responses.SuccessResponse;
import dao.CurrencyDAO;
import dao.ExchangeRateDAO;
import utils.JSONConverter;
import utils.ModelMapper;
import utils.Validator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class BaseServlet extends HttpServlet {
    protected CurrencyDAO currencyDAO = new CurrencyDAO();
    protected ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
    protected ModelMapper mapper = new ModelMapper();
    protected JSONConverter converter = new JSONConverter();
    protected SuccessResponse successResponse = new SuccessResponse();
    protected ErrorResponse errorResponse = new ErrorResponse();
    protected Validator validator = new Validator();

    protected void configUTF(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
    }
}
