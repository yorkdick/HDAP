package com.myself.hdap.server.primary.socket;


import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.command.CommandParse;

public class SocketParse {
	
	public static Object doSocketFunction(String str){
		try {
			Command cmd = CommandParse.parse(str);
			if(cmd !=null && cmd.getCommandKey().equals("execute")) {
				return cmd.doCommand();
			}
			return "Didn't find any cmd '"+cmd.getCommandKey()+"'";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
