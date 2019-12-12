package com.myself.hdap.client.remoteService;

public class ServiceFactory {

    public static <T> T getRemoteService(Class<T> clss){
        if(clss.isAnnotationPresent(FunctionInterface.class)){
            return new CglibProxyFactory<>(clss).getProxyInstance();
        }
        return null;
    }
}
