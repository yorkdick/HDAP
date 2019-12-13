package com.myself.hdap.server.deployment.hotdeploy;

import com.myself.hdap.common.CommonInstant;

import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class Deployment {
	private ClassLoader loader;
	private String jarName;
	private Set<String> deployMethods = new CopyOnWriteArraySet<>();
	private Map<String,Object> deployService = new ConcurrentHashMap<>();

	public Deployment(String jarPath, ClassLoader loader){
		this.jarName = jarPath;
		this.loader = loader;
	}

	public ClassLoader getLoader() {
		return loader;
	}

	public String getJarName() {
		return jarName;
	}

	public Set<String> getDeployMethods() {
		return deployMethods;
	}

	public Map<String, Object> getDeployService() {
		return deployService;
	}


	public void unDeploy(){
		try {
			Files.deleteIfExists( CommonInstant.getJarFile(jarName).toPath());
			jarName = null;
			deployMethods.forEach(method -> {
				HotDeployManager.getInstance().getDeployMethods().get(method).unDeploy();
				HotDeployManager.getInstance().getDeployMethods().remove(method);
			});
			deployMethods.clear();
			deployMethods = null;
			deployService.clear();
			deployService = null;
			((URLClassLoader)loader).close();
			loader=null;
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
