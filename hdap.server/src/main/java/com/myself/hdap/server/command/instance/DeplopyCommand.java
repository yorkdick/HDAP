package com.myself.hdap.server.command.instance;

import com.myself.hdap.server.annotation.CmdParam;
import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.deployment.DeployManager;

public class DeplopyCommand extends Command{
	
	@CmdParam(require=true, regex = "")
	private String jar;

	public void doCommand() {
		DeployManager.deploy(jar);
	}

	public String getCommandKey() {
		return "delpoy";
	}
}
