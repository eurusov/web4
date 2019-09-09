package servlet;

import com.google.gson.Gson;
import com.sun.istack.internal.Nullable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletHelper {

    static void writeJsonToResponse(@Nullable Object src, HttpServletResponse resp) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(src);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(json);
        resp.flushBuffer();
    }

}
