package com.myself.hdap.server.hotdeploy;

import java.util.Map;

public class DeployMement {
	private ClassLoader loader;
	private String deployId;
	private Map<String,DeployMethod> deployMethods;
	
	public DeployMement( String deployId,ClassLoader loader,Map<String,DeployMethod> deployMethods){
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
	public Map<String,DeployMethod> getDeployMethods() {
		return deployMethods;
	}
}
