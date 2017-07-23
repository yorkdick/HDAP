package com.myself.hdap.server.command.instance.deploy;

import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.deployment.DeployManager;

public class ListDeployFunctionsCommand extends Command{

	public void doCommand() {
		DeployManager.listDeployFunctions();
	}

	public String getCommandKey() {
		return "listFunctions";
	}

}
