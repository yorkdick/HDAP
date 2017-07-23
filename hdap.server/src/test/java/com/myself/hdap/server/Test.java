package com.myself.hdap.server;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Test {
	public static void main(String[] args) throws Exception {
		File file = new File("E:/WorkSpace/test/tes.jar");
		String jarUrl = "jar:"+file.toURI().toString()+"!/";
		System.out.println(jarUrl);
		  URL FileSysUrl = new URL(jarUrl);  
		  System.out.println(FileSysUrl.toString());
          // Create a jar URL connection object  
          JarURLConnection jarURLConnection = (JarURLConnection)FileSysUrl.openConnection();
//		for(String str  : file.list()) {
//			System.out.println(str);
//		}jarURLConnection
		System.out.println(jarURLConnection.getEntryName());
	}
}
