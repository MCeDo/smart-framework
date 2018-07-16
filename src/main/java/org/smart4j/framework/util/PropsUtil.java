package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件读取工具类
 */
public class PropsUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 加载配置文件
     * @param configFile
     * @return
     */
    public static Properties loadprops(String configFile) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            if(StringUtil.isEmpty(configFile)) {
                throw new IllegalArgumentException();
            }
            String suffix = ".properties";
            if(configFile.lastIndexOf(suffix) == -1) {
                configFile += suffix;
            }
            is = ClassUtil.getClassLoader().getResourceAsStream(configFile);
            if(is != null) {
                properties.load(is);
            }
        } catch (IOException e) {
            logger.error("加载属性文件出错！", e);
            throw new RuntimeException(e);
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("释放资源出错！", e);
            }
        }
        return properties;
    }
    
    public static String getString(Properties configProps, String key) {
        return configProps.getProperty(key);
    }

    public static String getString(Properties configProps, String key, String defaultValue) {
        return configProps.getProperty(key, defaultValue);
    }

    public static Integer getInt(Properties configProps, String key, int defaultValue) {
        Integer res = (Integer)configProps.get(key);
        return res==null?defaultValue:res;
    }

}
