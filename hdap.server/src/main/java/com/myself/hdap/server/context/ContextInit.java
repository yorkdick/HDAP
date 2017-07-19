package com.myself.hdap.server.context;

public class ContextInit {
	
	private static final String BASE_PACKAGE = "com.myself";
	
	public static void initCommand(){
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> initCommand ........");
		
		CommandLoader.loadCommands(BASE_PACKAGE);
	}
}
