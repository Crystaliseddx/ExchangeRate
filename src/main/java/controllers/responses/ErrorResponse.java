package controllers.responses;

import exceptions.BaseException;
import utils.JSONConverter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorResponse {
    private String json;
    private JSONConverter converter= new JSONConverter();

    public void sendErrorResponse(HttpServletResponse response, BaseException exception, int status) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setStatus(status);
        json = converter.convertToJSON(exception.getErrorMessage());
        pw.println(json);
    }
}
