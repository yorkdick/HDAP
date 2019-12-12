package com.myself.hdap.client.remoteService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.myself.hdap.client.connection.ConnectionManager;
import com.myself.hdap.client.connection.ServiceConnect;
import com.myself.hdap.common.CommonInstant;

public class ExecuteFunction {
	
	public static boolean execute(String functionId,String params) {
		String bool = "false";
		final Socket socket = ConnectionManager.getSocket();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 PrintWriter bw = new PrintWriter(socket.getOutputStream());){
			bw.println(getCmdStr(functionId,params));
			bw.flush();
			bool = br.readLine();
			bw.println(CommonInstant.SOCKET_END);
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			ConnectionManager.returnSocket(socket);
		}
		return Boolean.valueOf(bool);
	}

	private static String getCmdStr(String functionId, String params) {
		String str = "execute --functionId "+functionId;
		if(params!=null && params.length()>0) {
			str+=" --arguments=\""+params+"\"";
		}
		return str;
	}

	public static Object invoke(String serviceId, Method method, Object[] objects) {
		String functionId = CommonInstant.getDeployMethodId(serviceId,method);
		String params = Arrays.stream(objects).map(object -> object ==null ? "":object.toString()).collect(Collectors.joining(","));
		return execute(functionId,params);
	}
}
