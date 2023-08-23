//package controllers;
//
//import dto.ExchangeAmountDTO;
//import models.Currency;
//import services.ExchangeService;
//
//import javax.servlet.*;
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//@WebServlet(name = "ExchangeServlet", value = "/exchange")
//public class ExchangeServlet extends BaseServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ExchangeService exchangeService = new ExchangeService();
//        ExchangeAmountDTO exchangeAmountDTO;
//        Currency baseCurrency;
//        Currency targetCurrency;
//        PrintWriter pw = response.getWriter();
//
//        String baseCurrencyCode = request.getParameter("from");
//        String targetCurrencyCode = request.getParameter("to");
//        double amount = Double.parseDouble(request.getParameter("amount"));
//        double convertedAmount = exchangeService.exchangeCurrency(baseCurrencyCode, targetCurrencyCode, amount);
//        double rate = amount / convertedAmount; // aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
//
//        baseCurrency = currencyDAO.getCurrencyByCode(baseCurrencyCode);
//        targetCurrency = currencyDAO.getCurrencyByCode(targetCurrencyCode);
//
//        exchangeAmountDTO = mapper.getExchangeAmountDTO(baseCurrency, targetCurrency, rate, amount, convertedAmount);
//        String json = converter.convertToJSON(exchangeAmountDTO);
//        pw.println(json);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//}
