package com.myself.hdap.server.command;

import java.util.List;

public abstract class Command implements BasicCommand{
	
	public List<CommandParam> getParams(){
		return CommandRepository.getParams(getCommandKey());
	}
	
	public String getParamNames(){
		List<CommandParam> params = CommandRepository.getParams(getCommandKey());
		String name = "";
		for(CommandParam param : params) {
			name+=param.getParam()+"[required="+param.isRequired()+"]"+",";
		}
		if(name.length()>0)
			name = name.substring(0, name.length()-1);
		return name;
	}
}
