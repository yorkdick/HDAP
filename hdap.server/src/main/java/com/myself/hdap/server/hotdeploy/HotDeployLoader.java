package com.myself.hdap.server.hotdeploy;

import java.io.File;

import com.myself.hdap.server.util.FileUtil;

public class HotDeployLoader {
	private final static HotDeployLoader hdl = new HotDeployLoader();
	
	private final static String deployPath = "deploy";
	
	private HotDeployLoader() {
		
	}
	
	public static HotDeployLoader getInstance() {
		return hdl;
	}
	
	public void deployJar(String path) {
		File jar = null ;
		try {
			jar = FileUtil.moveFile2Path(new File(path), deployPath);
		} catch (Exception e) {
			System.out.println(" deploy jar error ");
			System.out.println(e.getMessage());
		}
		System.out.println("deploy jar:"+path);
	}
	
	public void unDeployJar(String key) {
		// TODO unDeployJar
	}
}
