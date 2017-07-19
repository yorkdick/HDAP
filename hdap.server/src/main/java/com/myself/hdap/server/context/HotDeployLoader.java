package com.myself.hdap.server.context;

public class HotDeployLoader {
	private final static HotDeployLoader hdl = new HotDeployLoader();
	
	private HotDeployLoader() {
		
	}
	
	public static HotDeployLoader getInstance() {
		return hdl;
	}
	
	public void deployJar(String path) {
		// TODO deployJar
		System.out.println("deploy jar:"+path);
	}
	
	public void unDeployJar(String key) {
		// TODO unDeployJar
	}
}