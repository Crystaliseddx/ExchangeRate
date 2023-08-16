package controllers;

import dao.CurrencyDAO;
import models.Currency;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CurrencyDAO currencyDAO = new CurrencyDAO();
        ConverterJSON converter = new ConverterJSON();

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();

        String[] urlArray = request.getRequestURL().toString().split("/");
        String url = urlArray[urlArray.length-1].toUpperCase();

        Currency currency = currencyDAO.getCurrency(url);
        String json = converter.convertToJSON(currency);
        pw.println(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
