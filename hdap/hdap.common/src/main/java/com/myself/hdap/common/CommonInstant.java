package com.myself.hdap.common;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CommonInstant {
    public static final String SOCKET_END = "SOCKET_END";
    public final static String deployPath = "deploy";
    public static final String SYSTEM_JAR = "SystemFunction.jar";

    public static File getDeployDirectory(){
        String classPath = ClassLoader.getSystemResource("").getPath();
        return new File(classPath,deployPath);
    }

    public static File getJarFile(String fileName){
        String classPath = ClassLoader.getSystemResource("").getPath();
        return new File(new File(classPath,deployPath),fileName);
    }

    public static String getDeployMethodId(String deployId, Method method){
        StringBuilder sb = new StringBuilder();
        Class<?>[] parameterTypes = method.getParameterTypes();
        sb.append(deployId).append("-").append(method.getName()).append("(").append(
                Arrays.stream(parameterTypes).map(type -> {
                    final String typeName = type.getTypeName();
                    return typeName.substring(typeName.lastIndexOf(".")+1);
                }).collect(Collectors.joining(","))
        ).append(")");
        return sb.toString();
    }
}
