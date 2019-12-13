package com.myself.hdap.internal.service;

import com.myself.hdap.common.annotation.FunctionService;
import com.myself.hdap.common.annotation.ServerFunction;
import com.myself.hdap.server.command.CommandRepository;

import java.util.stream.Collectors;

@FunctionService("internalService2")
public class InternalService2 {

    @ServerFunction
    public String getSystemFunctions(){
        return CommandRepository.getAllCommands().keySet().stream().collect(Collectors.joining(","));
    }

    @ServerFunction
    public String getSystemInfo(){
        StringBuilder sb = new StringBuilder();
        System.getenv().entrySet().forEach(entry -> {
            sb.append(entry.getKey()+":"+entry.getValue()+"\r\n");
        });
        return sb.toString();
    }

}
