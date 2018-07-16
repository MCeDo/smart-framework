package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;
import org.smart4j.framework.proxy.TransactionProxy;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 *  切面助手类
 */
public class AopHelper {

    private static Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            System.out.println("AOP HELPER");
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxtMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
                Class<?> targetClass = entry.getKey();
                List<Proxy> proxyList = entry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        }catch (Exception e) {
            LOGGER.error("aop failure", e);

        }
    }

    /**
     * 获取带切面注解的类
     * @param aspect
     * @return
     * @throws Exception
     */
    public static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
        Set<Class<?>> classSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && !annotation.equals(Aspect.class)) {
            classSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return classSet;
    }

    /**
     * 获取代理类和目标类集合之间的关系映射
     * @return
     * @throws Exception
     */
    public static Map<Class<?>, Set<Class<?>>> createProxtMap() throws Exception {
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
        Set<Class<?>> proxyClassSet = ClassHelper.getClassBySuper(AspectProxy.class);
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 创建代理类对应目标类的实例集合
     * @param proxyMap
     * @return
     * @throws Exception
     */
    public static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception{
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
        for(Map.Entry<Class<?>, Set<Class<?>>> entry : proxyMap.entrySet()) {
            Class<?> proxyClass = entry.getKey();
            Set<Class<?>> targetClassSet = entry.getValue();
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy)proxyClass.newInstance();
                if(targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                }else {
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }

    /**
     * 添加切面代理
     * @param proxyMap
     * @throws Exception
     */
    private static void addAspectProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Set<Class<?>> proxyClassSet = ClassHelper.getClassBySuper(AspectProxy.class);
        for(Class<?> proxtClass : proxyClassSet) {
            Aspect aspect = proxtClass.getAnnotation(Aspect.class);
            Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
            proxyMap.put(proxtClass, targetClassSet);
        }
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassBySuper(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClassSet);
    }
}
