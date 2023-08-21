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
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "ExchangeRatesServlet", value = "/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
        ConverterJSON converter = new ConverterJSON();
        ExchangeRateDTO exchangeRateDTO;
        ExchangeRateMapper exchangeRateMapper = new ExchangeRateMapper();

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter pw = response.getWriter();

        List<ExchangeRate> exchangeRates = null;
        try {
            exchangeRates = exchangeRateDAO.getExchangeRates();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String[] jsonArray = new String[exchangeRates.size()];
        for (int i = 0; i < jsonArray.length; i++) {
            exchangeRateDTO = exchangeRateMapper.getExchangeRateDTO(exchangeRates.get(i));
            jsonArray[i] = converter.convertToJSON(exchangeRateDTO);
        }
        pw.println(Arrays.toString(jsonArray));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
