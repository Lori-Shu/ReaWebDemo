package com.ggl.annotations.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;



public class MyWebClientProxyBean implements FactoryBean<Object> {
    Class type;
    Object instance;

    public MyWebClientProxyBean(Class objectClass, Class interfaces,String baseUrl) {
        type = objectClass;
        instance = Proxy.newProxyInstance(objectClass.getClassLoader(), new Class[] { interfaces },
                new MyWebClientProxyHandler(baseUrl));
    }
    @Override
    @Nullable
    public Object getObject() throws Exception {
        // TODO Auto-generated method stub
        return instance;
    }

    @Override
    @Nullable
    public Class<?> getObjectType() {
        // TODO Auto-generated method stub
        return type;
    }


}
