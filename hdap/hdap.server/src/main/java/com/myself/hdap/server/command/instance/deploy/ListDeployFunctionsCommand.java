package com.myself.hdap.server.command.instance.deploy;

import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.deployment.DeployManager;

public class ListDeployFunctionsCommand extends Command{

	public Object doCommand() {
		DeployManager.listDeployFunctions();
		return null;
	}

	public String getCommandKey() {
		return "listFunctions";
	}

}
