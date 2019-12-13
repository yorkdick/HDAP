package com.myself.hdap.server.command;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommandRepository {
	private static final Map<String,Command> commands = new ConcurrentHashMap<String,Command>();
	
	private static final Map<String,List<CommandParam>> commandParams = new  ConcurrentHashMap<String,List<CommandParam>>();
	
	public static List<CommandParam> getParams(String key){
		return commandParams.get(key);
	}
	
	public static Command getCommand(String key){
		return commands.get(key);
	}
	
	public static synchronized void putCommand(Command command,List<CommandParam> params){
		if(commands.containsKey(command.getCommandKey())){
			return;
		}
		
		commands.put(command.getCommandKey(), command);
		commandParams.put(command.getCommandKey(), params);
		
		System.out.println(" add command "+command.getCommandKey()+"{"+command.getParamNames()+"} success");
	}
	
	public static Map<String,Command> getAllCommands() {
		return commands;
	}
	
	public static Map<String,List<CommandParam>> getAllCommandParams(){
		return commandParams;
	}
}
