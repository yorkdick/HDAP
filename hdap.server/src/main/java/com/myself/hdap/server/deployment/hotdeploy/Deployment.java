package com.myself.hdap.server.deployment.hotdeploy;

import java.net.URLClassLoader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Deployment {
	private ClassLoader loader;
	private String jarPath;
	private Map<String,String> deployMethods = new ConcurrentHashMap<>();
	private Map<String,Object> deployService = new ConcurrentHashMap<>();

	public Deployment(String jarPath, ClassLoader loader){
		this.jarPath = jarPath;
		this.loader = loader;
	}

	public ClassLoader getLoader() {
		return loader;
	}

	public String getJarPath() {
		return jarPath;
	}

	public Map<String, String> getDeployMethods() {
		return deployMethods;
	}

	public Map<String, Object> getDeployService() {
		return deployService;
	}


	public void unDeploy(){
		try {
			jarPath = null;
			deployMethods.values().forEach(method ->{
				HotDeployManager.getInstance().getDeployMethods().get(method).unDeploy();
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
