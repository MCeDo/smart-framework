package org.smart4j.framework.bean;

import org.smart4j.framework.util.CastUtil;
import org.smart4j.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数对象
 */
public class Param {

    private List<FormParam> formParamList;
    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获取请求参数映射
     * @return
     */
    public Map<String, Object> getFieldMap() {
        Map<String, Object> fieldMap = new HashMap<>();
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(formParamList)) {
            for(FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 获取上传文件映射
     * @return
     */
    public Map<String, List<FileParam>> getFileMap() {
        Map<String, List<FileParam>> fileMap = new HashMap<>();
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(fileParamList)) {
            for (FileParam fileParam : fileParamList) {
                String filedName = fileParam.getFieldName();
                List<FileParam> fileParams;
                if(fileMap.containsKey(filedName)) {
                    fileParams = fileMap.get(filedName);
                }else {
                    fileParams = new ArrayList<>();
                }
                fileParams.add(fileParam);
                fileMap.put(filedName, fileParams);
            }
        }
        return fileMap;
    }

    /**
     * 获取所有上传文件
     * @param fieldName
     * @return
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一上传文件
     * @param fieldName
     * @return
     */
    public FileParam getFile(String fieldName) {
        List<FileParam> list = getFileMap().get(fieldName);
        if(org.apache.commons.collections4.CollectionUtils.isNotEmpty(list) && list.size()==1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据参数名获取long型参数值
     * @param name
     * @return
     */
    public Long getLong(String name) {
        return CastUtil.castLong(getFieldMap().get(name));
    }

    public String getString(String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }

    public double getDouble(String name) {
        return CastUtil.castSDouble(getFieldMap().get(name));
    }

    public Integer getInt(String name) {
        return CastUtil.castInteger(getFieldMap().get(name));
    }

    public boolean isEmpty() {
        return org.apache.commons.collections4.CollectionUtils.isEmpty(fileParamList)
                && org.apache.commons.collections4.CollectionUtils.isEmpty(formParamList);
    }
}
