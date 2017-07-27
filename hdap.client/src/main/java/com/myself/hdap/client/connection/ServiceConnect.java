package com.myself.hdap.client.connection;

import com.myself.hdap.client.remoteService.ExecuteFunction;

public class ServiceConnect {
	
	private static String host ;
	private static int port;
	
	public static synchronized boolean connect(String host , int port) {
		ServiceConnect.host = host;
		ServiceConnect.port = port;
		return true;
	}
	
	public static String getHost() {
		return host;
	}

	public static int getPort() {
		return port;
	}

	public static boolean execute(String functionId,Object... objects) {
		String params = "";
		for(Object obj : objects) {
			params += obj==null ? "" : String.valueOf(obj)+";";
		}
		if(params.length()>0)
		{
			params = params.substring(0, params.length()-1);
		}
		return ExecuteFunction.execute(functionId, params);
	}
}
