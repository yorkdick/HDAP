package com.myself.hdap.server.function;


import com.myself.hdap.common.annotation.FunctionService;
import com.myself.hdap.common.annotation.ServerFunction;

@FunctionService("testRemoteService")
public class TestFunction {

	@ServerFunction
	public void testPrint() {
		System.out.println(" this is test fucntion without args ");
	}
	
	@ServerFunction
	public void testPrint(String key) {
		System.out.println(" this is test fucntion "+key);
	}
}
