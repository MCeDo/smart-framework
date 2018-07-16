package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码和解码工具类
 */
public final class CodeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeUtil.class);

    /**
     * 将URL编码
     * @param str
     * @return
     */
    public static String encodeURL(String str) {
        String target;
        try {
            target = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode URL failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将URL 解码
     * @param string
     * @return
     */
    public static String decodeURL(String string) {
        String target;
        try {
            target = URLDecoder.decode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("decode URL failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
