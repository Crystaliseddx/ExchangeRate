package controllers.responses;

import exceptions.MyException;
import utils.ConverterJSON;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorResponse {
    private String json;
    private ConverterJSON converter= new ConverterJSON();

    public void sendErrorResponse(HttpServletResponse response, MyException exception, int status) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setStatus(status);
        json = converter.convertToJSON(exception.getErrorMessage());
        pw.println(json);
    }
}
