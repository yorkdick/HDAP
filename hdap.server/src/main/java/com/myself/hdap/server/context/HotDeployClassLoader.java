package com.myself.hdap.server.context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import com.myself.hdap.server.annotation.FunctionService;
import com.myself.hdap.server.annotation.ServerFunction;
import com.myself.hdap.server.hotdeploy.DeployMethod;
import com.myself.hdap.server.hotdeploy.HotDeployManager;

public class HotDeployClassLoader extends ClassLoader{
	private SimpleClassLoader scl ;
	private Map<String,Class<?>> classes = new HashMap<String,Class<?>>();
	private String deployId;
	private File jar;
	
	/**
	 * 
	 */
	public HotDeployClassLoader(File jar) {
		scl = new SimpleClassLoader(this, new HotDeployClassHandler());
		this.jar = jar;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			JarInputStream jis = new JarInputStream(new FileInputStream(jar));
			JarEntry entry = jis.getNextJarEntry();
			String className = entry.getName().replaceAll("/", ".");
			if((name+".class").equals(className)) {
				return deFineClass(jis,name);
			}
			jis.close();
		} catch (Exception e) {
		}
		return super.findClass(name);
	}
	
	private Class<?> deFineClass(JarInputStream jis,String name) throws IOException {
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
         return defineClass(name,data,0,data.length);
	}

	public void loadJar() throws Exception {
		deployId = jar.getName();
		
		scl.loadJar(getJarUrl());
	}
	
	public JarURLConnection getJarUrl() throws Exception {
		String jarUrl = "jar:"+jar.toURI().toString()+"!/";
		return (JarURLConnection)new URL(jarUrl).openConnection();
	}

	private class HotDeployClassHandler implements ClassHandler{

		public boolean filter(Class<?> cls) {
			return cls.isAnnotationPresent(FunctionService.class);
		}

		public void handler(Class<?> cls) throws Exception {
			List<String> list = new ArrayList<String>();
			
			for(Method method : cls.getDeclaredMethods()) {
				Object obj = cls.newInstance();
				if(method.isAnnotationPresent(ServerFunction.class)) {
					ServerFunction sf = method.getAnnotation(ServerFunction.class);
					DeployMethod dm = new DeployMethod(sf.value(),method,obj,scl.getLoader());
					HotDeployManager.getInstance().addDeployMethod(dm);
					list.add(dm.getDeployId());
				}
			}
			
			if(list.size()>0) {
				HotDeployManager.getInstance().getDeploys().put(deployId, list);
			}
		}
	}
	
}
