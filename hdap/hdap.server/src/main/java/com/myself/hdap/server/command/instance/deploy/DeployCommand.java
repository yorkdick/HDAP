package com.myself.hdap.server.command.instance.deploy;

import com.myself.hdap.server.annotation.CmdParam;
import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.deployment.DeployManager;

public class DeployCommand extends Command{
	
	@CmdParam
	private String jar;

	public Object doCommand() {
		DeployManager.deploy(jar);
		return null;

	}

	public String getCommandKey() {
		return "deploy";
	}
}
