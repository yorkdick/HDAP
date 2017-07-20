package com.myself.hdap.server.hotdeploy;

import java.lang.reflect.Method;
import java.util.Map;

public class DeployMement {
	private ClassLoader loader;
	private String deployId;
	private Map<String,Method> deployMethods;
	
	public DeployMement( String deployId,ClassLoader loader,Map<String,Method> deployMethods){
		this.deployId = deployId;
		this.loader = loader;
		this.deployMethods = deployMethods;
	}
	
	public ClassLoader getLoader() {
		return loader;
	}
	public String getDeployId() {
		return deployId;
	}
	public Map<String,Method> getDeployMethods() {
		return deployMethods;
	}
}
