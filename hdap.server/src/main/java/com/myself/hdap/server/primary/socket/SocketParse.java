package com.myself.hdap.server.primary.socket;


import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.command.CommandParse;

public class SocketParse {
	
	public static boolean doSocketFunction(String str){
		try {
			Command cmd = CommandParse.parse(str);
			if(cmd !=null && cmd.getCommandKey().equals("execute")) {
				cmd.doCommand();
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
