package com.myself.hdap.server.command.instance.deploy;

import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.deployment.DeployManager;

public class ListDeployCommand extends Command{

	public Object doCommand() {
		DeployManager.listDeploy();
		return null;
	}

	public String getCommandKey() {
		return "listDeploy";
	}
	
}
