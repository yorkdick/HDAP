package com.myself.hdap.server.hotdeploy;

import java.util.HashMap;
import java.util.Map;

public class HotDeployManager {
	private final  static HotDeployManager hdm = new HotDeployManager();
	
	private final Map<String,DeployMement> deployMents = new HashMap<String,DeployMement>();
	
	private HotDeployManager() {
		
	}
	
	public static HotDeployManager getInstance() {
		return hdm;
	}
	
	public Map<String,DeployMement> getDeplyoMents(){
		return deployMents;
	}
	
	public DeployMement getDeployMement(String key) {
		return deployMents.get(key);
	}
	
	public void addDeployMement(DeployMement dm) {
		deployMents.put(dm.getDeployId(), dm);
	}
}
