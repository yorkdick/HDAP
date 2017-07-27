package com.myself.hdap.server.primary.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HDAPServerSocket {
	public static final int port = 60893;
	private static boolean started = false;

	@SuppressWarnings("resource")
	public static synchronized void startServerSocket() {
		if (!started) {
			started = true;
			ExecutorService st = Executors.newSingleThreadExecutor();
			st.execute(new Runnable() {
				@Override
				public void run() {
					try {
						final ServerSocket serverSocket = new ServerSocket(port);
						System.out.println("Server listens to " +serverSocket.getInetAddress().getHostAddress()+":["+ port + "] success");
						Socket socket = null;
						BufferedReader br = null;
						PrintWriter bw = null;
						while (true) {
							try {
								socket = serverSocket.accept();
								System.out.println("accept socket ");
								br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								bw = new PrintWriter(socket.getOutputStream());
								String linestr = null;
								if ((linestr = br.readLine()) != null) {
									System.out.println("get line "+linestr);
									boolean bool = SocketParse.doSocketFunction(linestr);
									String result = String.valueOf(bool);
									System.out.println("write result "+result);
									bw.write(result);
									bw.flush();
								}
							} catch (Exception e) {
								e.printStackTrace();
							}finally {
								if(socket != null)
									socket.close();
								if(br != null)
									br.close();
								if(bw != null)
									bw.close();
							}
						}
					} catch (Exception e) {
						System.out.println("Server listens to " + port + " failed");
						e.printStackTrace();
					}
				}
			});
		} else {
			System.out.println("Server is listening to " + port + " already");
		}
	}
}
