package controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CurrencyDAO;
import models.Currency;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CurrencyDAO currencyDAO = new CurrencyDAO();
        ConverterJSON converter = new ConverterJSON();

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();


        List<Currency> currencies = currencyDAO.getCurrencies();
        String[] jsonArray = new String[currencies.size()];
        for (int i = 0; i < jsonArray.length; i++) {
            jsonArray[i] = converter.convertToJSON(currencies.get(i));
        }
        pw.println(Arrays.toString(jsonArray));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
