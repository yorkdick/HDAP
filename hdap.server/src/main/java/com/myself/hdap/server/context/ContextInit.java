package com.myself.hdap.server.context;


import com.myself.hdap.server.command.CommandLoader;

public class ContextInit {
	
	private static final String BASE_PACKAGE = "com.myself";
	
	public static void initCommand() throws Exception{
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> initCommand ........");
		
		CommandLoader.loadCommands(BASE_PACKAGE);
	}
}
