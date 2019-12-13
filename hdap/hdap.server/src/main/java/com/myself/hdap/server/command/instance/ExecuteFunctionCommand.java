package com.myself.hdap.server.command.instance;

import com.myself.hdap.server.annotation.CmdParam;
import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.deployment.hotdeploy.HotDeployManager;

public class ExecuteFunctionCommand extends Command{

	@CmdParam(require=true)
	String functionId;
	
	@CmdParam
	String arguments;
	
	public Object doCommand() {
		try {
			if(HotDeployManager.getInstance().getDeployMethods().containsKey(functionId)) {
				String[] args = null;
				if(arguments!=null && !arguments.trim().equals("")) {
					args = arguments.split(",");
				}
				return HotDeployManager.getInstance().getDeployMethods().get(functionId).invoke(args);
			}else {
				return "function "+functionId+" not exits";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getCommandKey() {
		return "execute";
	}
	
}
