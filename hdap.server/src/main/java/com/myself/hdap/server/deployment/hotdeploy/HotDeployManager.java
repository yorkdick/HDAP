package com.myself.hdap.server.deployment.hotdeploy;

import com.myself.hdap.server.context.HotDeployClassLoader;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HotDeployManager {
    private final static HotDeployManager hdm = new HotDeployManager();
    //<jarPath,list<serviceName>>
    private final Map<String, Deployment> deploys = new ConcurrentHashMap<>();
    private final Map<String, DeployMethod> deployMethods = new ConcurrentHashMap<>();


    private HotDeployManager() {

    }

    public Map<String, DeployMethod> getDeployMethods() {
        return deployMethods;
    }

    public static HotDeployManager getInstance() {
        return hdm;
    }

    public Map<String, Deployment> getDeploys() {
        return deploys;
    }

    public synchronized void unDeploy(String deployed) {
        if (deploys.containsKey(deployed)) {
            try {
                deploys.get(deployed).unDeploy();
                deploys.remove(deployed);
                System.out.println(deployed + " undeploy success");
            } catch (Exception e) {
                System.out.println("undeploy failed " + e.getMessage());
            }

        } else {
            System.out.println("undeploy failed , " + deployed + " not exits ");
        }
    }

    public synchronized void initDeployments() {
        String classpPath = ClassLoader.getSystemResource("").getPath();
        String cpath = new File(classpPath).getParent() + File.separator + HotDeployLoader.deployPath;
        File cfile = new File(cpath);
        if (cfile.isDirectory()) {
            File[] jars = cfile.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isFile() && file.getName().endsWith(".jar");
                }
            });
            for (File jar : jars) {
                try {
                    HotDeployClassLoader.getLoader(jar);
                    System.out.println(" deploy jar " + jar.getName() + " success ");
                } catch (Exception e) {
                    System.out.println(" deploy jar " + jar.getName() + " error ");
                    e.printStackTrace();
                }
            }
        }
    }
}
