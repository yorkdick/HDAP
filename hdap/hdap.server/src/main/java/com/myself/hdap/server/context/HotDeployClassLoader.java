package com.myself.hdap.server.context;

import com.myself.hdap.common.annotation.FunctionService;
import com.myself.hdap.common.annotation.ServerFunction;
import com.myself.hdap.server.deployment.hotdeploy.DeployMethod;
import com.myself.hdap.server.deployment.hotdeploy.Deployment;
import com.myself.hdap.server.deployment.hotdeploy.HotDeployManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class HotDeployClassLoader extends URLClassLoader {
    private File jar;

    private Map<String, Class> classMap = new ConcurrentHashMap<>();

    /**
     *
     */
    public static HotDeployClassLoader getLoader(File jar) throws MalformedURLException {
        URL[] urls = new URL[]{jar.toURI().toURL()};
        return new HotDeployClassLoader(urls, jar);
    }

    public HotDeployClassLoader(URL[] classPath, File jar) {
        super(classPath);
        this.jar = jar;
        Deployment deployment = new Deployment(jar.getName(), this);
        HotDeployManager.getInstance().getDeploys().put(jar.getName(), deployment);
        defineAllClass(deployment);
    }

    private void defineAllClass(Deployment deployment) {
        try {
            JarFile jarFile = new JarFile(jar);
            Enumeration<JarEntry> entry = jarFile.entries();
            JarEntry jarEntry;
            String name, className;
            Class<?> clss;
            while (entry.hasMoreElements()) {
                jarEntry = entry.nextElement();
                name = jarEntry.getName();
                // 如果是以/开头的
                if (name.charAt(0) == '/') {
                    // 获取后面的字符串
                    name = name.substring(1);
                }
                if (jarEntry.isDirectory()) {
                    continue;
                }
                //如果是一个.class文件 而且不是目录
                // 去掉后面的".class" 获取真正的类名
                if(jarEntry.getName().endsWith(".class")){
                    className = name.substring(0, name.length() - 6).replace("/", ".");
                    //加载类
                    clss = loadClass(className);
                    classMap.put(className, clss);
                    // 添加到集合中去
                    if (clss != null && clss.isAnnotationPresent(FunctionService.class)) {
                        loadFunctionClass(clss, deployment);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFunctionClass(Class<?> clss, Deployment deployment) throws IllegalAccessException, InstantiationException {
        final String deployId = clss.getDeclaredAnnotation(FunctionService.class).value();
        Object obj = clss.newInstance();
        deployment.getDeployService().put(deployId, obj);
        for (Method method : clss.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ServerFunction.class)) {
                ServerFunction sf = method.getAnnotation(ServerFunction.class);
                DeployMethod dm = new DeployMethod(jar.getName(), deployId, method, obj, this);
                deployment.getDeployMethods().add(dm.toString());
                HotDeployManager.getInstance().getDeployMethods().put(dm.toString(), dm);
            }
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        final Class aClass = classMap.get(name);
        if (aClass != null) {
            return aClass;
        }
        return super.findClass(name);
    }

    @Override
    public void close() throws IOException {
        jar = null;
        classMap.clear();
        classMap = null;
        super.close();
    }
}
