package com.myself.hdap.server.command.instance.deploy;

import com.myself.hdap.server.annotation.CmdParam;
import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.hotdeploy.HotDeployManager;

public class ExecuteFuncitonCommand extends Command{

	@CmdParam(require=true)
	String functionId;
	
	@CmdParam
	String arguments;
	
	public void doCommand() {
		try {
			if(HotDeployManager.getInstance().getDeplyoMethods().containsKey(functionId)) {
				String[] args = null;
				if(arguments!=null && !arguments.trim().equals("")) {
					args = arguments.split(",");
				}
				HotDeployManager.getInstance().getDeployMethod(functionId).invoke(args);
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
