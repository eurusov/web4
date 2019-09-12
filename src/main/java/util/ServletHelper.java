package util;

import com.google.gson.Gson;
import com.sun.istack.internal.Nullable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletHelper {

    public static void writeJsonToResponse(@Nullable Object src, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        new Gson().toJson(src, resp.getWriter());
        resp.flushBuffer();
    }
}
