package controllers;

import dao.CurrencyDAO;
import dto.CurrencyDTO;
import dto.mappers.ConverterJSON;
import dto.mappers.ModelMapper;
import models.Currency;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "CurrenciesServlet", value = "/currencies")
public class CurrenciesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CurrencyDAO currencyDAO = new CurrencyDAO();
        ConverterJSON converter = new ConverterJSON();
        CurrencyDTO currencyDTO;
        ModelMapper modelMapper = new ModelMapper();

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter pw = response.getWriter();

        List<Currency> currencies = null;
        try {
            currencies = currencyDAO.getCurrencies();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String[] jsonArray = new String[currencies.size()];
        for (int i = 0; i < jsonArray.length; i++) {
            currencyDTO = modelMapper.getCurrencyDTO(currencies.get(i));
            jsonArray[i] = converter.convertToJSON(currencyDTO);
        }
        pw.println(Arrays.toString(jsonArray));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
