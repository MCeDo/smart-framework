package org.smart4j.framework.helper;

import org.smart4j.framework.ConfigConstant;
import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 */
public class ClassHelper {

    /**
     * 类集合，存放所加载的类
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        System.out.println("classHelper");
        String basePaceage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePaceage);
    }

    /**
     * 获取应用包名下的所有类
     * @return
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * 获取应用包名下的所有service类
     * @return
     */
    public static Set<Class<?>> getServiceClassSet() {
        return getAnnotationClassSet(Service.class);
    }

    /**
     * 获取应用包下的所有controller类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet() {
        return getAnnotationClassSet(Controller.class);
    }

    /**
     * 获取应用包下的所有 Bean 类（包括service和controller）
     * @return
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    /**
     * 获取应用包下对应注解的类
     * @param annotationClass
     * @return
     */
    private static Set<Class<?>> getAnnotationClassSet(Class<?> annotationClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包下的某父类（或接口）的所有子类（或实现类）
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<>();
        for(Class<?> cls : CLASS_SET) {
            if(superClass.isAssignableFrom(cls) && !superClass.equals(cls)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包下带有某注解的类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class cls : CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }


}
