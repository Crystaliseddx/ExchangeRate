package controllers;

import dto.ExchangeRateDTO;
import models.ExchangeRate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "ExchangeRatesServlet", value = "/exchangeRates")
public class ExchangeRatesServlet extends BaseServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ExchangeRateDTO exchangeRateDTO;
//        PrintWriter pw = response.getWriter();
//
//        List<ExchangeRate> exchangeRates = null;
//
//        exchangeRates = exchangeRateDAO.getExchangeRates();
//
//        String[] jsonArray = new String[exchangeRates.size()];
//        for (int i = 0; i < jsonArray.length; i++) {
//            exchangeRateDTO = mapper.getExchangeRateDTO(exchangeRates.get(i));
//            jsonArray[i] = converter.convertToJSON(exchangeRateDTO);
//        }
//        pw.println(Arrays.toString(jsonArray));
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
}
