package com.myself.hdap.server.command;


import com.myself.hdap.common.CommonInstant;
import com.myself.hdap.common.annotation.FunctionService;
import com.myself.hdap.common.annotation.ServerFunction;
import com.myself.hdap.server.context.ClassHandler;
import com.myself.hdap.server.context.SimpleClassLoader;
import com.myself.hdap.server.deployment.hotdeploy.DeployMethod;
import com.myself.hdap.server.deployment.hotdeploy.Deployment;
import com.myself.hdap.server.deployment.hotdeploy.HotDeployManager;

import java.lang.reflect.Method;

public class CommandLoader {
	public static void loadCommands(String basePackage) throws Exception {
		SimpleClassLoader simpleClassLoader = new SimpleClassLoader(new CommandClassHandler());
		simpleClassLoader.loadClassesByPackage(basePackage);
	}

	public static void loadFunctions(String basePackage) throws Exception {
		SimpleClassLoader simpleClassLoader = new SimpleClassLoader(new ClassHandler() {
			@Override
			public boolean filter(Class<?> cls) {
				return cls.isAnnotationPresent(FunctionService.class);
			}

			@Override
			public void handler(Class<?> cls) throws Exception {
				Deployment deployment = new Deployment(CommonInstant.SYSTEM_JAR,ClassLoader.getSystemClassLoader());
				HotDeployManager.getInstance().getDeploys().put(CommonInstant.SYSTEM_JAR,deployment);
				final String deployId = cls.getDeclaredAnnotation(FunctionService.class).value();
				Object obj = cls.newInstance();
				deployment.getDeployService().put(deployId,obj);
				for (Method method : cls.getDeclaredMethods()) {
					if (method.isAnnotationPresent(ServerFunction.class)) {
						ServerFunction sf = method.getAnnotation(ServerFunction.class);
						DeployMethod dm = new DeployMethod(CommonInstant.SYSTEM_JAR,deployId,method, obj, ClassLoader.getSystemClassLoader());
						deployment.getDeployMethods().add(dm.toString());
						HotDeployManager.getInstance().getDeployMethods().put(dm.toString(),dm);
					}
				}
			}
		});
		simpleClassLoader.loadClassesByPackage(basePackage);
	}
}
