package org.smart4j.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 */
public final class IocHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);

    static {
        System.out.println("ioc");
        //获取所有的bean类与bean实例之间的映射关系
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap)) {
            //遍历beanMap
            LOGGER.debug("遍历"+beanMap.size());
            for (Map.Entry<Class<?>, Object> entry : beanMap.entrySet()) {
                //从beanMap中获取Bean类与Bean实例
                Class<?> beanClass = entry.getKey();
                Object beanInstance = entry.getValue();
                //获取Bean类定义的所有成员变量
                Field[] beanFields = beanClass.getDeclaredFields();
                LOGGER.debug("获取cy变量"+beanFields.length);
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    //遍历Bean Fields
                    for (Field beanField : beanFields) {
                        //当前field是否有注解
                        LOGGER.debug("是否有注解");
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            //在BeanMap中获取Bean Field对应的实例
                            Class<?> beanFieldClass = beanField.getType();

                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            LOGGER.debug("属性实例"+beanFieldClass);
                            if(beanFieldInstance != null) {
                                //利用反射注入
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }

        }
    }
}
