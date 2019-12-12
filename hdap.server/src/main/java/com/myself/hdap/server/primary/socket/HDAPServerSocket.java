package com.myself.hdap.server.primary.socket;

import com.myself.hdap.common.CommonInstant;
import com.myself.hdap.server.context.ContextInit;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class HDAPServerSocket {
	public static final int port = 60893;
	private static boolean started = false;
	private static int maxConnections = 5;

	public static AtomicInteger connectionSize = new AtomicInteger(0);

	private static ExecutorService st = Executors.newSingleThreadExecutor();
	private static ExecutorService fixedPool = Executors.newFixedThreadPool(3);


	@SuppressWarnings("resource")
	public static synchronized void startServerSocket() {
		if (!started) {
			started = true;
			st.execute(()->{
				try {
					final ServerSocket serverSocket = new ServerSocket(port);
					System.out.println("Server listens to " +serverSocket.getInetAddress().getHostAddress()+":["+ port + "] success");

					while(true && !ContextInit.exitSystem){
						try {
							final Socket socket = serverSocket.accept();
							fixedPool.execute(()->{
								try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
									 PrintWriter bw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))
								){
									System.out.println("accept socket ");
									int current = connectionSize.incrementAndGet();
									if(current>maxConnections){
										bw.println("Server socket connection is full.");
										bw.flush();
									}else{
										String linestr = null;
										if ((linestr = br.readLine()) != CommonInstant.SOCKET_END) {
											System.out.println("get line "+linestr);
											boolean bool = SocketParse.doSocketFunction(linestr);
											String result = String.valueOf(bool);
											System.out.println("write result "+result);
											bw.println(result);
											bw.flush();
										}
									}
								}catch (Exception e){
									if(socket!=null){
										try {
											socket.close();
										} catch (IOException ex) {
											ex.printStackTrace();
										}
									}
									e.printStackTrace();
								}
							});
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				} catch (Exception e) {
					System.out.println("Server listens to " + port + " failed");
					e.printStackTrace();
					System.exit(0);
				}
			});
		} else {
			System.out.println("Server is listening to " + port + " already");
		}
	}
}
