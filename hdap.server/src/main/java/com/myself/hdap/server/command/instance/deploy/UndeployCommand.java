package com.myself.hdap.server.command.instance.deploy;

import com.myself.hdap.server.annotation.CmdParam;
import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.deployment.DeployManager;

public class UndeployCommand extends Command{

	@CmdParam(require=true)
	private String jar;
	
	public void doCommand() {
		DeployManager.unDeploy(jar);
	}

	public String getCommandKey() {
		return "undeploy";
	}
}
