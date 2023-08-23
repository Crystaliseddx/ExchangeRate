//package controllers;
//
//import dto.ExchangeRateDTO;
//import models.ExchangeRate;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.Reader;
//
//@WebServlet(name = "ExchangeRateServlet", value = "/exchangeRate/*")
//public class ExchangeRateServlet extends BaseServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ExchangeRateDTO exchangeRateDTO;
//
//        response.setContentType("application/json; charset=UTF-8");
//        PrintWriter pw = response.getWriter();
//
//        String[] urlArray = request.getRequestURL().toString().split("/");
//        String currencyCodes = urlArray[urlArray.length-1].toUpperCase();
//        String baseCurrencyCode = currencyCodes.substring(0, 3);
//        String targetCurrencyCode = currencyCodes.substring(3, 6);
//
//
//        ExchangeRate exchangeRate = null;
//
//        exchangeRate = exchangeRateDAO.getExchangeRateByCurrencyCodes(baseCurrencyCode, targetCurrencyCode);
//
//        exchangeRateDTO = mapper.getExchangeRateDTO(exchangeRate);
//        String json = converter.convertToJSON(exchangeRateDTO);
//        pw.println(json);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        PrintWriter pw = response.getWriter();
//
//        StringBuilder body = new StringBuilder();
//        char[] buffer = new char[1024];
//        int readChars;
//
//        try (Reader reader = request.getReader()){
//            while ((readChars = reader.read(buffer)) != -1) {
//                body.append(buffer,0, readChars);
//            }
//        }
//        ExchangeRateDTO exchangeRateDTO = converter.convertToExchangeRateDTO(body.toString());
//        ExchangeRate exchangeRate = mapper.getExchangeRate(exchangeRateDTO);
//
//        exchangeRate = exchangeRateDAO.saveExchangeRate(exchangeRate);
//
//        exchangeRateDTO = mapper.getExchangeRateDTO(exchangeRate);
//        String json = converter.convertToJSON(exchangeRateDTO);
//        pw.println(json);
//    }
//
//    @Override
//    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        if ("PATCH".equals(request.getMethod())) {
//            doPatch(request, response);
//        } else {
//            super.service(request, response);
//        }
//    }
//    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        PrintWriter pw = response.getWriter();
//
//        StringBuilder body = new StringBuilder();
//        char[] buffer = new char[1024];
//        int readChars;
//
//        try (Reader reader = request.getReader()){
//            while ((readChars = reader.read(buffer)) != -1) {
//                body.append(buffer,0, readChars);
//            }
//        }
//        ExchangeRateDTO exchangeRateDTO = converter.convertToExchangeRateDTO(body.toString());
//        ExchangeRate exchangeRate = mapper.getExchangeRate(exchangeRateDTO);
//
//        exchangeRate = exchangeRateDAO.updateExchangeRate(exchangeRate);
//
//        exchangeRateDTO = mapper.getExchangeRateDTO(exchangeRate);
//        String json = converter.convertToJSON(exchangeRateDTO);
//        pw.println(json);
//    }
//}
