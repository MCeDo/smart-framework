package org.smart4j.framework.util;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 文件上传工具类
 */
public final class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 获取真实文件名（自动去掉文件路径）
      * @param fileName
     * @return
     */
    public static String getRealFileName(String fileName) {
        int index = fileName.lastIndexOf("\\");
        return fileName.substring(index+1, fileName.length());
    }

    /**
     * 创建文件
     * @param filePath
     * @return
     */
    public static File createFile(String filePath) {
        File file;
        try {
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if(!parentDir.exists()) {
                FileUtils.forceMkdir(parentDir);
            }
        }catch (Exception e){
            LOGGER.error("create file failure", e);
            throw new RuntimeException(e);
        }
        return file;
    }

}
