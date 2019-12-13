package com.myself.hdap.client.remoteService;

import com.myself.hdap.common.annotation.FunctionService;

public class ServiceFactory {

    public static <T> T getRemoteService(Class<T> clss){
        if(clss.isAnnotationPresent(FunctionService.class)){
            return new CglibProxyFactory<>(clss).getProxyInstance();
        }
        return null;
    }
}
