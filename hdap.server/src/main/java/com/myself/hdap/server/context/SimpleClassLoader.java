package com.myself.hdap.server.context;

import java.io.File;
import java.io.FileFilter;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class SimpleClassLoader {
	
	
	private ClassLoader loader;
	private ClassHandler handler;
	private Map<String,Class<?>> classes;
	
	public SimpleClassLoader(){
		this.handler = null;
		this.loader = ClassLoader.getSystemClassLoader();
		this.classes = null;
	}
	
	public SimpleClassLoader(ClassHandler filter){
		this.handler = filter;
		this.loader = ClassLoader.getSystemClassLoader();
		this.classes = null;
	}
	
	public SimpleClassLoader(ClassLoader loader,ClassHandler filter){
		this.handler = filter;
		this.loader = loader;
		this.classes = null;
	}
	
	public void loadClassesByPackage(String basePackage) throws Exception {
		basePackage = resolvePackage(basePackage);
		String basePath = resolvePath(basePackage.replace(".", "/"));
		
		Enumeration<URL> urls = loader.getResources(basePath);
		while(urls.hasMoreElements()) {
			URL url = urls.nextElement();
			if(url!=null) {
				String protocol = url.getProtocol();
				if(protocol.equals("file")) {
					String packagePath = url.getPath().replaceAll("%20", " ");
					addClass(packagePath,basePackage);
				}else if(protocol.equals("jar")) {
					JarURLConnection jarUrlConnection = (JarURLConnection)url.openConnection();
					loadJar(jarUrlConnection);
				}
			}
		}
	}
	
	public void loadJar(JarURLConnection jarUrlConnection) throws Exception {
		if(jarUrlConnection!=null) {
			JarFile jarFile = jarUrlConnection.getJarFile();
			if(jarFile!=null) {
				Enumeration<JarEntry> jarEntries = jarFile.entries();
				while(jarEntries.hasMoreElements()) {
					JarEntry jarEntry = jarEntries.nextElement();
					String jarName = jarEntry.getName();
					if(jarName.endsWith(".class")) {
						String className = jarName.substring(0,jarName.lastIndexOf(".")).replaceAll("/", ".");
						loadClass(className);
					}
				}
			}
		}
	}

	private void addClass(String packagePath, String packageName) throws Exception {
		System.out.println("packagePath" + "  "+packageName);
		
		packagePath = resolvePath(packagePath);
		packageName = resolvePackage(packageName);
		
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			public boolean accept(File file) {
				if((file.isFile() && file.getName().endsWith(".class")) || file.isDirectory()) {
					return true;
				}
				return true;
			}
		});
		
		for(File file : files) {
			String fileName = file.getName();
			
			System.out.println("fileName" + "  "+fileName);
			
			if(file.isFile()) {
				String className = packageName+"."+fileName.substring(0, fileName.lastIndexOf("."));
				loadClass(className);
			}else {
				String subPackagePath = packagePath+fileName+"/";
				String subPackageName = packageName+"."+fileName;
				addClass(subPackagePath,subPackageName);
			}
		}
	}

	private void loadClass(String className) throws Exception {
		System.out.println("loadClass "+className);
		
		Class<?> cls = loader.loadClass(className);
		if(classes!=null) {
			classes.put(cls.getName(), cls);
		}
//		System.out.println("laodClass " + cls.getName() + " success!");
		if (handler != null && handler.filter(cls)) {
			handler.handler(cls);
		}
		
		System.out.println("loadClass "+className+" success");
	}

	private String resolvePackage(String basePackage) {
		basePackage.replaceAll("/", ".");
		if(basePackage.startsWith(".")) {
			basePackage = basePackage.substring(1);
		}
		if(basePackage.endsWith(".")) {
			basePackage = basePackage.substring(0, basePackage.length()-1);
		}
		return basePackage;
	}

	private String resolvePath(String basePath) {
		if(basePath.startsWith("/")) {
			basePath = basePath.substring(1);
		}
		if(!basePath.endsWith("/")) {
			basePath = basePath+"/";
		}
		return basePath;
	}

	public ClassLoader getLoader() {
		return loader;
	}

	public void setLoader(ClassLoader loader) {
		this.loader = loader;
	}

	public ClassHandler getHandler() {
		return handler;
	}

	public void setHandler(ClassHandler handler) {
		this.handler = handler;
	}

	public Map<String, Class<?>> getClasses() {
		return classes;
	}

	public void setClasses(Map<String, Class<?>> classes) {
		this.classes = classes;
	}
}
