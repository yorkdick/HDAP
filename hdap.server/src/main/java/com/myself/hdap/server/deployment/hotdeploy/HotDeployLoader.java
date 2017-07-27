package com.myself.hdap.server.deployment.hotdeploy;

import java.io.File;

import com.myself.hdap.server.context.HotDeployClassLoader;
import com.myself.hdap.server.util.FileUtil;

public class HotDeployLoader {
	private final static HotDeployLoader hdl = new HotDeployLoader();
	
	public final static String deployPath = "deploy";
	
	private HotDeployLoader() {
		
	}
	
	public static HotDeployLoader getInstance() {
		return hdl;
	}
	
	public void deployJar(String path) {
		if(isDeployPath(path)){
			File jar = null ;
			try {
				jar = FileUtil.moveJar2Path(new File(path), deployPath);
				new HotDeployClassLoader(jar).loadJar();
			} catch (Exception e) {
				System.out.println(" deploy jar error ");
				e.printStackTrace();
			}
//			System.out.println("deploy jar:"+path);
		}
	}
	
	private boolean isDeployPath(String path) {
		if(path.endsWith(".jar") || path.endsWith(".JAR")){
			File file = new File(path);
			return file.exists();
		}
		return false;
	}

	public void unDeployJar(String key) {
		HotDeployManager.getInstance().undePloy(key);
	}
}
