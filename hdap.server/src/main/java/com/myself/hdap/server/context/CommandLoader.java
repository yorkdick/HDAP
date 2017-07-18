package com.myself.hdap.server.context;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.myself.hdap.server.annotation.CmdParam;
import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.command.CommandParam;
import com.myself.hdap.server.command.CommandRepository;

public class CommandLoader {
	
	public static void main(String[] args) {
		loadCommands("com.myself");
	}

	public static void loadCommands(String basePackage) {
		try {
			basePackage = resolvePackage(basePackage);
			String basePath = resolvePath(basePackage.replace(".", "/"));
			
			Enumeration<URL> urls = getClassloader().getResources(basePath);
			while(urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if(url!=null) {
					String protocol = url.getProtocol();
					if(protocol.equals("file")) {
						String packagePath = url.getPath().replaceAll("%20", " ");
						addClass(packagePath,basePackage);
					}else if(protocol.equals("jar")) {
						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void addClass(String packagePath, String packageName) {
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
			if(file.isFile()) {
				String className = packageName+"."+fileName.substring(0, fileName.lastIndexOf("."));
				loadClass(className,false);
			}else {
				String subPackagePath = packagePath+fileName+"/";
				String subPackageName = packageName+"."+fileName;
				addClass(subPackagePath,subPackageName);
			}
		}
	}

	private static ClassLoader getClassloader() {
		return Thread.currentThread().getContextClassLoader();
	}

	private static void loadClass(String className, boolean isInitialized) {
		try {
			Class<?> cls = Class.forName(className, isInitialized, getClassloader());
			if (isChildOrImplOfClass(cls,Command.class)) {
				System.out.println("laodClass " + cls.getName() + " success!");

				Command command = (Command) cls.newInstance();

				List<CommandParam> params = new ArrayList<CommandParam>();

				Field[] fields = cls.getDeclaredFields();
				for (Field field : fields) {
					if (field.isAnnotationPresent(CmdParam.class)) {
						CmdParam cmdParam = field.getAnnotation(CmdParam.class);
						CommandParam param = new CommandParam();
						param.setEmpty(cmdParam.require());
						param.setRegex(cmdParam.regex());
						param.setParam(field.getName().toLowerCase());
						params.add(param);
					}
				}
				
				if(CommandRepository.getAllCommands().containsKey(command.getCommandKey())) {
					throw new RuntimeException("command duplicated "+command.getCommandKey());
				}else {
					CommandRepository.putCommand(command, params);
				}
				
				System.out.println("laod command " + cls.getName() + " success!");
			}
		} catch (Exception e) {
			System.out.println(className + " load failed!" + e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	private static boolean isChildOrImplOfClass(Class<?> cls, Class<?> class1) {
		for(Class<?> in : cls.getInterfaces()) {
			if(in.equals(class1)) {
				return true;
			}
		}
		
		Class<?> ss = cls.getSuperclass();
		while(ss!=null && !ss.isPrimitive() && !ss.equals(Object.class)) {
			if(ss.equals(class1)) {
				return true;
			}
			ss = cls.getSuperclass();
		}
		return false;
	}

	private static String resolvePackage(String basePackage) {
		basePackage.replaceAll("/", ".");
		if(basePackage.startsWith(".")) {
			basePackage = basePackage.substring(1);
		}
		if(basePackage.endsWith(".")) {
			basePackage = basePackage.substring(0, basePackage.length()-1);
		}
		return basePackage;
	}

	private static String resolvePath(String basePath) {
		if(basePath.startsWith("/")) {
			basePath = basePath.substring(1);
		}
		if(!basePath.endsWith("/")) {
			basePath = basePath+"/";
		}
		return basePath;
	}

}
