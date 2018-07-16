package org.smart4j.framework;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 */
@WebServlet(name = "/", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化Helper类
        HelpLoader.init();
        //获取ServletContex，用于注册servlet
        ServletContext servletContext = config.getServletContext();
        //注册处理jsp的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletHelper.init(req, resp);
        try {
            //获取请求方法与路径
            String requestMethod = req.getMethod().toLowerCase();
            String requestPath = req.getPathInfo();
            if (requestPath.equals("/favicon.ico")) {
                return;
            }
            LOGGER.debug("请求方法和路径：" + requestMethod + "," + requestPath);
            //获取Action处理器
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if (handler != null) {
                //获取Controller类和实例
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);
                //创建请求参数对象
                Param param;
                if (UploadHelper.isMultipart(req)) {
                    param = UploadHelper.createParam(req);
                } else {
                    param = RequestHelper.createParam(req);
                }
                //调用Action方法
                Method method = handler.getActionMethon();
                Object result;
                if (param.isEmpty()) {
                    result = ReflectionUtil.invokeMethod(controllerBean, method);
                } else {
                    result = ReflectionUtil.invokeMethod(controllerBean, method, param);
                }
                //处理Action返回的结果
                if (result instanceof View) {
                    handleViewResult((View) result, req, resp);
                } else if (result instanceof Data) {
                    hadnleDataResult((Data) result, req, resp);
                }
            }
        }finally {
            ServletHelper.destory();
        }
    }

    /**
     * 处理返回Data
     * @param result
     * @param req
     * @param resp
     */
    private void hadnleDataResult(Data result, HttpServletRequest req, HttpServletResponse resp) {
        //返回JSON数据
        Data data = (Data) result;
        Object model = data.getModel();
        if(model != null) {
            ResponseUtil.write(resp, model);
        }
    }

    /**
     * 处理返回视图结果
     * @param result
     * @param req
     * @param resp
     * @throws IOException
     */
    private void handleViewResult(View result, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        //返回JSP视图
        View view = (View) result;
        String path = view.getPath();
        if(StringUtil.isNotEmpty(path)){
            if (path.startsWith("/")) {
                System.out.print(req.getContextPath() + path);
                resp.sendRedirect(path);
            }else {
                Map<String, Object> model = view.getModel();
                if (model!=null) {
                    for (Map.Entry<String, Object> entry : model.entrySet()) {
                        req.setAttribute(entry.getKey(), entry.getValue());
                    }
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
            }
        }
    }
}
