package controllers;

import dao.CurrencyDAO;
import dao.ExchangeRateDAO;
import exceptions.BaseException;
import utils.JSONConverter;
import utils.ModelMapper;
import utils.Validator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class BaseServlet extends HttpServlet {
    protected CurrencyDAO currencyDAO = new CurrencyDAO();
    protected ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
    protected ModelMapper mapper = new ModelMapper();
    protected JSONConverter converter = new JSONConverter();
    protected Validator validator = new Validator();

    protected void configUTF(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
    }
    public void sendSuccessResponse(HttpServletResponse response, String[] jsonArray) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setStatus(200);
        pw.println(Arrays.toString(jsonArray));
        pw.close();
    }
    public void sendSuccessResponse(HttpServletResponse response, String json) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setStatus(200);
        pw.println(json);
        pw.close();
    }
    public void sendErrorResponse(HttpServletResponse response, BaseException exception, int status) throws IOException {
        String json;
        PrintWriter pw = response.getWriter();
        response.setStatus(status);
        json = converter.convertToJSON(exception.getErrorMessage());
        pw.println(json);
        pw.close();
    }

}
