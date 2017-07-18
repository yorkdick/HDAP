package com.myself.hdap.server.command;

public interface BasicCommand {
	//excute command
	void doCommand();
	//get command key
	String getCommandKey();
}
