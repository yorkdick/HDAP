package com.myself.hdap.server.context;


import com.myself.hdap.server.command.CommandLoader;
import com.myself.hdap.server.deployment.hotdeploy.HotDeployManager;

public class ContextInit {
	
	private static final String BASE_PACKAGE = "com.myself";
	public static final boolean exitSystem = false;

	public static void initCommand() throws Exception{
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> initCommand ........");
		
		CommandLoader.loadCommands(BASE_PACKAGE);
	}
	
	public static void initFunctions() throws Exception {
		//加载系统fucntion
		CommandLoader.loadFunctions(BASE_PACKAGE);
		//加载已经deploy的jar
		HotDeployManager.getInstance().initDeployments();
	}
}
