package com.myself.hdap.server.primary;

import java.util.Scanner;

import com.myself.hdap.server.command.adapter.CommandAdapter;
import com.myself.hdap.server.context.ContextInit;
import com.myself.hdap.server.primary.socket.HDAPServerSocket;

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
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Init system command ........");
		//遍历指定包路劲下所有calss，把继承了Command的类存入仓库中
		ContextInit.initCommand();
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Init functions ........");
		ContextInit.initFunctions();
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Init serverSocket ........");
		HDAPServerSocket.startServerSocket();
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Server started  ........");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>> Server waiting for command  ........");

		Scanner scanner = new Scanner(System.in);
		String cmd = null;
		
		cmd = getCommand(scanner);
		
		while(cmd !=null && !cmd.equals(EXIT)){
			CommandAdapter.doCommand(cmd);
			
			cmd = getCommand(scanner);
		}
		if(cmd.endsWith(EXIT)){
			System.exit(0);
		}
		scanner.close();
	}

	private String getCommand(Scanner scanner) {
//		System.out.print("cmd>");
		return scanner.nextLine().trim();
	}
}
