package com.myself.hdap.server.primary.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
			try {
				final ServerSocket serverSocket = new ServerSocket(port);
				ExecutorService st = Executors.newSingleThreadExecutor();
				st.execute(new Runnable() {
					@Override
					public void run() {
						Socket socket = null;
						while (true) {
							try {
								socket = serverSocket.accept();
								BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
								String linestr = null;
								while ((linestr = br.readLine()) != null) {
									SocketParse.doSocketFunction(linestr);
								}
							} catch (IOException e) {
								try {
									if (socket != null)
										socket.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								e.printStackTrace();
							}
						}
					}
				});
			} catch (Exception e) {
				System.out.println("Server listens to " + port + " failed");
				e.printStackTrace();
			}
		} else {
			System.out.println("Server is listening to " + port + " already");
		}
	}
}
