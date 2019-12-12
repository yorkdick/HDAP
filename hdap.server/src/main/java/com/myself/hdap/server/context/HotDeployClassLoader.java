package com.myself.hdap.server.context;

import com.myself.hdap.server.annotation.FunctionService;
import com.myself.hdap.server.annotation.ServerFunction;
import com.myself.hdap.server.deployment.hotdeploy.DeployMethod;
import com.myself.hdap.server.deployment.hotdeploy.Deployment;
import com.myself.hdap.server.deployment.hotdeploy.HotDeployManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;


public class HotDeployClassLoader extends URLClassLoader {
    private File jar;

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
        Deployment deployment = new Deployment(jar.getAbsolutePath(),this);
        HotDeployManager.getInstance().getDeploys().put(jar.getAbsolutePath(),deployment);
        defineAllClass(deployment);
    }

    private void defineAllClass(Deployment deployment) {
        try {
            JarInputStream jis = new JarInputStream(new FileInputStream(jar));
            JarEntry entry = jis.getNextJarEntry();
            String className = entry.getName().replaceAll("/", ".");
            if (className.endsWith(".class")) {
                Class<?> clss = deFineClass(jis, className.substring(0, className.length() - 6));
                if (clss.isAnnotationPresent(FunctionService.class)) {
                    loadFunctionClass(clss,deployment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFunctionClass(Class<?> clss,Deployment deployment) throws IllegalAccessException, InstantiationException {
        final String deployId = clss.getDeclaredAnnotation(FunctionService.class).value();
        Object obj = clss.newInstance();
        deployment.getDeployService().put(deployId,obj);
        for (Method method : clss.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ServerFunction.class)) {
                ServerFunction sf = method.getAnnotation(ServerFunction.class);
                DeployMethod dm = new DeployMethod(jar.getAbsolutePath(),deployId,method, obj, this);
                deployment.getDeployMethods().put(dm.toString(),dm.toString());
                HotDeployManager.getInstance().getDeployMethods().put(dm.toString(),dm);
            }
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            JarInputStream jis = new JarInputStream(new FileInputStream(jar));
            JarEntry entry = jis.getNextJarEntry();
            String className = entry.getName().replaceAll("/", ".");
            if ((name + ".class").equals(className)) {
                return deFineClass(jis, name);
            }
            jis.close();
        } catch (Exception e) {
        }
        return super.findClass(name);
    }

    private Class<?> deFineClass(JarInputStream jis, String name) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = 0;
        try {
            while ((len = jis.read()) != -1) {
                bos.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] data = bos.toByteArray();
        bos.close();
        return defineClass(name, data, 0, data.length);
    }
}
