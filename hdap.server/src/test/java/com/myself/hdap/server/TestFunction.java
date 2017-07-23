package com.myself.hdap.server;

import com.myself.hdap.server.annotation.FunctionService;
import com.myself.hdap.server.annotation.ServerFunction;

@FunctionService
public class TestFunction {

	@ServerFunction(value = "testPrint")
	public void testPrint() {
		System.out.println(" this is test fucntion without args ");
	}
	
	@ServerFunction(value = "testPrint2")
	public void testPrint(String key) {
		System.out.println(" this is test fucntion "+key);
	}
}
