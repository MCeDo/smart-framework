package org.smart4j.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.util.CodeUtil;
import org.smart4j.framework.util.StreamUtil;
import org.smart4j.framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * 请求助手类
 */
public class RequestHelper {

    /**
     * 创建Param参数
     * @param request
     * @return
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    /**
     * 解析获取输入流数据，request body
     * @param request
     * @return
     */
    private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        String body = CodeUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if(StringUtil.isNotEmpty(body)) {
            String[] kvs = body.split("&");
            if(ArrayUtils.isNotEmpty(kvs)){
                for(String kv : kvs) {
                    String[] array =  kv.split("=");
                    if(ArrayUtils.isNotEmpty(array) && array.length==2) {
                        String fieldName = array[0];
                        String fieldValue = array[1];
                        formParamList.add(new FormParam(fieldName, fieldValue));
                    }
                }
            }
        }
        return formParamList;
    }

    /**
     * 解析获取表单数据
     * @param request
     * @return
     */
    private static List<FormParam> parseParameterNames(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String fieldName = paramNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName);
            if(ArrayUtils.isNotEmpty(fieldValues)) {
                Object fieldValue;
                if(fieldValues.length == 1) {
                    fieldValue = fieldValues[0];
                }else {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < fieldValues.length; i++) {
                        sb.append(fieldValues);
                        if(i != fieldValues.length-1) {
                            sb.append(StringUtil.SEPARATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParamList.add(new FormParam(fieldName, fieldValue));
            }
        }
        return formParamList;
    }
}
