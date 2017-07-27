package com.myself.hdap.client.remoteService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.myself.hdap.client.connection.ServiceConnect;

public class ExecuteFunction {
	
	public static boolean execute(String fucntionId,String params) {
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter bw = null;
		
		String bool = "false";
		try {
			socket = new Socket(ServiceConnect.getHost(), ServiceConnect.getPort());
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			bw = new PrintWriter(socket.getOutputStream());
			
			bw.println(getCmdStr(fucntionId,params));
			bw.flush();
			bool = br.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(socket != null)
					socket.close();
				if(br != null)
					br.close();
				if(bw != null)
					bw.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return Boolean.valueOf(bool);
	}

	private static String getCmdStr(String fucntionId, String params) {
		String str = "execute --functionId "+fucntionId;
		if(params!=null && params.length()>0) {
			str+=" --arguments=\""+params+"\"";
		}
		return str;
	}
}
