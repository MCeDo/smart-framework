package org.smart4j.framework.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {


    public static void write(HttpServletResponse resp, Object model) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            PrintWriter writer = resp.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
