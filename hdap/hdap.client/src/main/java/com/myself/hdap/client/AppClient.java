package com.myself.hdap.client;

import com.myself.hdap.client.connection.ServiceConnect;
import com.myself.hdap.client.remoteService.ServiceFactory;
import com.myself.hdap.client.remoteService.functionservice.InternalService1;

/**
 * Hello world!
 *
 */
public class AppClient
{
    public static void main( String[] args )
    {
    	ServiceConnect.connect("127.0.0.1", 60893);
//    	String result = ServiceConnect.execute("internalService2-getSystemFunctions()","4444");
//        System.out.println(result);
        final InternalService1 remoteService = ServiceFactory.getRemoteService(InternalService1.class);
    	System.out.println(remoteService.getFunctionList());
    	System.out.println(remoteService.getServiceList());
    }
}
