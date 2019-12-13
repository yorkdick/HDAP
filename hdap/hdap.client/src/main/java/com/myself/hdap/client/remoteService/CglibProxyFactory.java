package com.myself.hdap.client.remoteService;

import com.myself.hdap.common.annotation.FunctionService;
import com.myself.hdap.common.annotation.ServerFunction;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyFactory<T> implements MethodInterceptor {
    private Class<T> clss;
    private String serviceId;
    public CglibProxyFactory(Class<T> clss){
        this.clss = clss;
        this.serviceId = clss.getDeclaredAnnotation(FunctionService.class).value();
    }

    //给目标对象创建一个代理对象
    public T getProxyInstance(){
        //1.工具类
        Enhancer en = new Enhancer();
        //2.设置父类
        en.setSuperclass(clss);
        //3.设置回调函数
        en.setCallback(this);
        //4.创建子类(代理对象)
        return (T)en.create();
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) {
        final ServerFunction declaredAnnotation = method.getDeclaredAnnotation(ServerFunction.class);
        if(declaredAnnotation!=null){
            return ExecuteFunction.invoke(serviceId,method,objects);
        }
        return null;
    }
}
