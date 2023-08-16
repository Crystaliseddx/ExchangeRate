package controllers;

import dao.CurrencyDAO;
import dao.ExchangeRateDAO;
import models.Currency;
import models.ExchangeRate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "ExchangeRatesServlet", value = "/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
        ConverterJSON converter = new ConverterJSON();

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();


        List<ExchangeRate> exchangeRates = exchangeRateDAO.getExchangeRates();
        String[] jsonArray = new String[exchangeRates.size()];
        for (int i = 0; i < jsonArray.length; i++) {
            jsonArray[i] = converter.convertToJSON(exchangeRates.get(i));
        }
        pw.println(Arrays.toString(jsonArray));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
