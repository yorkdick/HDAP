package com.myself.hdap.client;

import com.myself.hdap.client.connection.ServiceConnect;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ServiceConnect.connect("127.0.0.1", 60893);
    	boolean ss = ServiceConnect.execute("testPrint2","4444");
    	System.out.println(ss);
    }
}
