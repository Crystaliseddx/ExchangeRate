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
import java.io.Reader;
import java.sql.SQLException;

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

        Currency currency = null;
        try {
            currency = currencyDAO.getCurrencyByCode(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        currencyDTO = currencyMapper.getCurrencyDTO(currency);
        String json = converter.convertToJSON(currencyDTO);
        pw.println(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ConverterJSON converter = new ConverterJSON();
        CurrencyMapper currencyMapper = new CurrencyMapper();
        CurrencyDAO currencyDAO = new CurrencyDAO();
        PrintWriter pw = response.getWriter();

        StringBuilder body = new StringBuilder();
        char[] buffer = new char[1024];
        int readChars;
        try(Reader reader = request.getReader()){
            while ((readChars = reader.read(buffer)) != -1) {
                body.append(buffer,0, readChars);
            }
        }
        CurrencyDTO currencyDTO = converter.convertToCurrencyDTO(body.toString());
        Currency currency = currencyMapper.getCurrency(currencyDTO);
        try {
            currency = currencyDAO.saveCurrency(currency);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        currencyDTO = currencyMapper.getCurrencyDTO(currency);
        String json = converter.convertToJSON(currencyDTO);
        pw.println(json);
    }
}
