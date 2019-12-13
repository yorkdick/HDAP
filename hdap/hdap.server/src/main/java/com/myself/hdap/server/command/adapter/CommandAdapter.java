package com.myself.hdap.server.command.adapter;

import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.command.CommandParse;

public class CommandAdapter {
	
	public static void doCommand(String cmd){
		Command command = getCommand(cmd);
		if(command!=null){
			command.doCommand();
			System.out.println( cmd+" is done.");
		}
	}

	private static Command getCommand(String cmd) {
		return CommandParse.parse(cmd);
	}
}
