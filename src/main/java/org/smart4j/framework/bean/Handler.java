package org.smart4j.framework.bean;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 */
public class Handler {

    /**
     * controller类
     */
    private Class<?> controllerClass;

    /**
     * Action方法
     */
    private Method actionMethon;

    public Handler(Class<?> controllerClass, Method actionMethon) {
        this.controllerClass = controllerClass;
        this.actionMethon = actionMethon;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<?> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public Method getActionMethon() {
        return actionMethon;
    }

    public void setActionMethon(Method actionMethon) {
        this.actionMethon = actionMethon;
    }
}
