package com.myself.hdap.server.deployment;

import com.myself.hdap.server.hotdeploy.HotDeployLoader;
import com.myself.hdap.server.hotdeploy.HotDeployManager;

public class DeployManager {
	public static void deploy(String path){
		HotDeployLoader.getInstance().deployJar(path);
	}
	
	public static void unDeploy(String id){
		if(HotDeployManager.getInstance().getDeploys().containsKey(id)) {
			HotDeployLoader.getInstance().unDeployJar(id);
			System.err.println("unDeployJar "+id+" success");
		}else {
			System.err.println("unDeployJar error, "+id+" not exits");
		}
	}
	
	public static void listDeploy(){
		int i = 0;
		for(String key : HotDeployManager.getInstance().getDeploys().keySet()) {
			System.out.println("deploy"+(++i)+"\t\t"+key);
		}
	}
	
	public static void listDeployFunctions(){
		int i = 0;
		for(String key : HotDeployManager.getInstance().getDeploys().keySet()) {
			for(String function : HotDeployManager.getInstance().getDeploys().get(key)) {
				System.out.println("deployFunction"+(++i)+"\t"+key+"\t"+function);
			}
		}
	}
}
