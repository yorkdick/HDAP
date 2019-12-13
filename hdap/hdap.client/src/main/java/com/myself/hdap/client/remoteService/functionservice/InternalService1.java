package com.myself.hdap.client.remoteService.functionservice;


import com.myself.hdap.common.annotation.FunctionService;
import com.myself.hdap.common.annotation.ServerFunction;

@FunctionService("internalService1")
public interface InternalService1 {

    @ServerFunction
    String getServiceList();

    @ServerFunction
    String getFunctionList();
}
