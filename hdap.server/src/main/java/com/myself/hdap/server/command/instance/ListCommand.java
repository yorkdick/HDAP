package com.myself.hdap.server.command.instance;

import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.command.CommandRepository;

public class ListCommand  extends Command{

	public void doCommand() {
		for(String command : CommandRepository.getAllCommands().keySet()) {
			System.out.println(command+":{"+CommandRepository.getAllCommands().get(command).getParamNames()+"}");
		}
	}

	public String getCommandKey() {
		return "list";
	}

}
