package com.myself.hdap.server.hotdeploy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotDeployManager {
	private final static HotDeployManager hdm = new HotDeployManager();
	private final Map<String,List<String>> deploys = new HashMap<String,List<String>>();
	private final Map<String,DeployMethod> deployMethods = new HashMap<String,DeployMethod>();
	
	
	private HotDeployManager() {
		
	}
	
	public static HotDeployManager getInstance() {
		return hdm;
	}
	
	public Map<String,DeployMethod> getDeplyoMethods(){
		return deployMethods;
	}
	
	public DeployMethod getDeployMethod(String key) {
		return deployMethods.get(key);
	}
	
	public void addDeployMethod(DeployMethod dm) {
		deployMethods.put(dm.getDeployId(), dm);
	}

	public Map<String,List<String>> getDeploys() {
		return deploys;
	}
	
	public void undePloy(String deployed) {
		if(deploys.containsKey(deployed)) {
			try {
				for(String key : deploys.get(deployed)) {
					deployMethods.get(key).unDeploy();
					deployMethods.remove(key);
				}
				deploys.remove(deployed);
				System.out.println(deployed+" undeploy success");
			} catch (Exception e) {
				System.out.println("undeploy failed "+e.getMessage());
			}
			
		}else {
			System.out.println("undeploy failed , "+deployed+" not exits ");
		}
	}
}
