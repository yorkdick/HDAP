package com.myself.hdap.server.command.instance.deploy;

import com.myself.hdap.common.CommonInstant;
import com.myself.hdap.server.annotation.CmdParam;
import com.myself.hdap.server.command.Command;
import com.myself.hdap.server.deployment.DeployManager;

public class UndeployCommand extends Command{

	@CmdParam(require=true)
	private String jar;
	
	public Object doCommand() {
		if(!CommonInstant.SYSTEM_JAR.equals(jar)){
			DeployManager.unDeploy(jar);
		}
		return null;
	}

	public String getCommandKey() {
		return "undeploy";
	}
}
