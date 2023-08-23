package controllers.responses;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class SuccessResponse {
    public void sendSuccessResponse(HttpServletResponse response, String[] jsonArray) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setStatus(200);
        pw.println(Arrays.toString(jsonArray));
    }
    public void sendSuccessResponse(HttpServletResponse response, String json) throws IOException {
        PrintWriter pw = response.getWriter();
        response.setStatus(200);
        pw.println(json);
    }
}
