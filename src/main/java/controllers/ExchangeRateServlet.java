package controllers;

import dao.ExchangeRateDAO;
import dto.ExchangeRateDTO;
import dto.mappers.ConverterJSON;
import dto.mappers.ExchangeRateMapper;
import models.ExchangeRate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "ExchangeRateServlet", value = "/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
        ConverterJSON converter = new ConverterJSON();
        ExchangeRateDTO exchangeRateDTO;
        ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter pw = response.getWriter();

        String[] urlArray = request.getRequestURL().toString().split("/");
        String currencyCodes = urlArray[urlArray.length-1].toUpperCase();
        String baseCurrencyCode = currencyCodes.substring(0, 3);
        String targetCurrencyCode = currencyCodes.substring(3, 6);


        ExchangeRate exchangeRate = null;
        try {
            exchangeRate = exchangeRateDAO.getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        exchangeRateDTO = exchangeRateMapper.getExchangeRateDTO(exchangeRate);
        String json = converter.convertToJSON(exchangeRateDTO);
        pw.println(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
