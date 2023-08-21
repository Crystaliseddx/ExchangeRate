package controllers;

import dao.CurrencyDAO;
import dto.CurrencyDTO;
import dto.mappers.ConverterJSON;
import dto.mappers.CurrencyMapper;
import models.Currency;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CurrencyServlet", value = "/currency/*")
public class CurrencyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CurrencyDAO currencyDAO = new CurrencyDAO();
        ConverterJSON converter = new ConverterJSON();
        CurrencyDTO currencyDTO;
        CurrencyMapper currencyMapper = new CurrencyMapper();

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter pw = response.getWriter();

        String[] urlArray = request.getRequestURL().toString().split("/");
        String url = urlArray[urlArray.length-1].toUpperCase();

        Currency currency = currencyDAO.getCurrencyByCode(url);
        currencyDTO = currencyMapper.getCurrencyDTO(currency);
        String json = converter.convertToJSON(currencyDTO);
        pw.println(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
