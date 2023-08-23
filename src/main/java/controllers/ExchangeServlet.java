package controllers;

import dao.CurrencyDAO;
import dao.ExchangeRateDAO;
import dto.ExchangeAmountDTO;
import dto.mappers.ConverterJSON;
import dto.mappers.ModelMapper;
import models.Currency;
import services.ExchangeService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "ExchangeServlet", value = "/exchange")
public class ExchangeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ExchangeService exchangeService = new ExchangeService();
        ExchangeAmountDTO exchangeAmountDTO;
        Currency baseCurrency;
        Currency targetCurrency;
        ModelMapper modelMapper = new ModelMapper();
        CurrencyDAO currencyDAO = new CurrencyDAO();
        ConverterJSON converter = new ConverterJSON();
        PrintWriter pw = response.getWriter();

        String baseCurrencyCode = request.getParameter("from");
        String targetCurrencyCode = request.getParameter("to");
        double amount = Double.parseDouble(request.getParameter("amount"));
        double convertedAmount = exchangeService.exchangeCurrency(baseCurrencyCode, targetCurrencyCode, amount);
        double rate = amount / convertedAmount; // aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        try {
            baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCode);
            targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        exchangeAmountDTO = modelMapper.getExchangeAmountDTO(baseCurrency, targetCurrency, rate, amount, convertedAmount);
        String json = converter.convertToJSON(exchangeAmountDTO);
        pw.println(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
