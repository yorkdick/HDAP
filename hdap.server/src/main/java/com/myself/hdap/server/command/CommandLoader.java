package com.myself.hdap.server.command;


import com.myself.hdap.server.context.SimpleClassLoader;

public class CommandLoader {
	public static void loadCommands(String basePackage) throws Exception {
		SimpleClassLoader simpleClassLoader = new SimpleClassLoader(new CommandClassHandler());
		simpleClassLoader.loadClassesByPackage(basePackage);
	}
}
