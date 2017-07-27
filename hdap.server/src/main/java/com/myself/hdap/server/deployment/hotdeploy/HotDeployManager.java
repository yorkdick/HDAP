package com.myself.hdap.server.deployment.hotdeploy;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myself.hdap.server.context.HotDeployClassLoader;

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
	

	public void initFunctions(){
		String classpPath = ClassLoader.getSystemResource("").getPath();
		String cpath = new File(classpPath).getParent()+File.separator+HotDeployLoader.deployPath;
		File cfile = new File(cpath);
		if(cfile.isDirectory()){
			File[] jars = cfile.listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.isFile() && file.getName().endsWith(".jar");
				}
			});
			for(File jar : jars){
				try {
					new HotDeployClassLoader(jar).loadJar();
					System.out.println(" deploy jar "+jar.getName()+" success ");
				} catch (Exception e) {
					System.out.println(" deploy jar "+jar.getName()+" error ");
					e.printStackTrace();
				}
			}
		}
	}
}
