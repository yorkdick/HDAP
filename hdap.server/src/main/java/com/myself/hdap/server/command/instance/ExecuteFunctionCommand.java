package com.myself.hdap.server.command.instance;

import com.myself.hdap.server.annotation.CmdParam;
import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.deployment.hotdeploy.HotDeployManager;

public class ExecuteFunctionCommand extends Command{

	@CmdParam(require=true)
	String functionId;
	
	@CmdParam
	String arguments;
	
	public void doCommand() {
		try {
			if(HotDeployManager.getInstance().getDeployMethods().containsKey(functionId)) {
				String[] args = null;
				if(arguments!=null && !arguments.trim().equals("")) {
					args = arguments.split(",");
				}
				HotDeployManager.getInstance().getDeployMethods().get(functionId).invoke(args);
				System.out.println("function "+functionId+" execute success");
			}else {
				System.out.println("function "+functionId+" not exits");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCommandKey() {
		return "execute";
	}
	
}
