package com.myself.hdap.internal.service;


import com.myself.hdap.common.annotation.FunctionService;
import com.myself.hdap.common.annotation.ServerFunction;
import com.myself.hdap.server.deployment.hotdeploy.HotDeployManager;

import java.util.stream.Collectors;

@FunctionService("internalService1")
public class InternalService1 {

    @ServerFunction
    public String getServiceList(){
        return HotDeployManager.getInstance().getDeploys().keySet().stream().collect(Collectors.joining(","));
    }

    @ServerFunction
    public String getFunctionList(){
        return HotDeployManager.getInstance().getDeployMethods().keySet().stream().collect(Collectors.joining(","));
    }
}
