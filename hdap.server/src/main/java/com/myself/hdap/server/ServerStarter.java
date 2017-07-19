package com.myself.hdap.server;

import java.util.Scanner;

import com.myself.hdap.server.adapter.CommandAdapter;
import com.myself.hdap.server.context.ContextInit;

public class ServerStarter {
	private final static ServerStarter starter = new ServerStarter();
	
	private final static String EXIT = "exit";
	
	private ServerStarter(){
		
	}
	
	public static void start() throws Exception{
		starter.startServer();
	}
	
	public synchronized void startServer() throws Exception{
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Server starting ........");
		
		ContextInit.initCommand();
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Server started  ........");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Server waiting for command  ........");

		Scanner sacnner = new Scanner(System.in);
		String cmd = null;
		
		cmd = getCommand(sacnner);
		
		while(cmd !=null && !cmd.equals(EXIT)){
			CommandAdapter.doCommand(cmd);
			
			cmd = getCommand(sacnner);
		}
		sacnner.close();
	}

	private String getCommand(Scanner sacnner) {
		System.out.print("cmd>");
		return sacnner.nextLine().trim();
	}
}
