package com.myself.hdap.server.command;

public interface BasicCommand {
	//excute command
	Object doCommand();
	//get command key
	String getCommandKey();
}
