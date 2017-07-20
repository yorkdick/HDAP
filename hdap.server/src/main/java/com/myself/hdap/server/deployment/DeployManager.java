package com.myself.hdap.server.deployment;

import com.myself.hdap.server.hotdeploy.HotDeployLoader;

public class DeployManager {
	public static void deploy(String path){
		HotDeployLoader.getInstance().deployJar(path);
	}
	
	public static void unDeploy(String id){
		HotDeployLoader.getInstance().unDeployJar(id);
	}
	
	public static void listDeploy(){
		// TODO listDeploy
	}
}
