package org.smart4j.framework.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * 集合工具类
 */
public class CollectionUtil {

    public static boolean isNotEmpty(Map<Class<?>, Object> map) {
        return MapUtils.isNotEmpty(map);
    }

    public static boolean isEmpty(Map<String, Object> map) {
        return MapUtils.isEmpty(map);
    }
}
