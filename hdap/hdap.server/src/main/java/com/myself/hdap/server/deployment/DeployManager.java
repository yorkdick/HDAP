package com.myself.hdap.server.deployment;

import com.myself.hdap.server.deployment.hotdeploy.DeployMethod;
import com.myself.hdap.server.deployment.hotdeploy.Deployment;
import com.myself.hdap.server.deployment.hotdeploy.HotDeployLoader;
import com.myself.hdap.server.deployment.hotdeploy.HotDeployManager;

import java.util.Map;

public class DeployManager {
    public static void deploy(String path) {
        HotDeployLoader.getInstance().deployJar(path);
    }

    public static void unDeploy(String id) {
        if (HotDeployManager.getInstance().getDeploys().containsKey(id)) {
            HotDeployLoader.getInstance().unDeployJar(id);
            System.err.println("unDeployJar " + id + " success");
        } else {
            System.err.println("unDeployJar error, " + id + " not exits");
        }
    }

    public static void listDeploy() {
        int i = 0;
        for (Map.Entry<String, Deployment> entry : HotDeployManager.getInstance().getDeploys().entrySet()) {
            System.out.println("deploy" + (++i) + "\t" + entry.getKey());
        }
    }

    public static void listDeployFunctions() {
        int i = 0;
        for (Map.Entry<String, DeployMethod> entry : HotDeployManager.getInstance().getDeployMethods().entrySet()) {
            System.out.println((++i)+"\t"+entry.getValue().toString());
        }
    }
}
