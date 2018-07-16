package org.smart4j.framework.util;

public class StringUtil {

    public static final String SEPARATOR = String.valueOf((char)29);

    public static boolean isEmpty(String str) {
        if(str==null || str.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
