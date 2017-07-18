package com.myself.hdap.server.command;

import java.util.List;

public abstract class Command implements BasicCommand{
	
	public List<CommandParam> getParams(){
		return CommandRepository.getParams(getCommandKey());
	}
}
