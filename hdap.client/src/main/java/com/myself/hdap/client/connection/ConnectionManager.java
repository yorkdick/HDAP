package com.myself.hdap.client.connection;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ConnectionManager {
    private static final int maxConnections = 3;
    private static List<Socket> idleSockets = new LinkedList<>();
    private static List<Socket> usingSockets = new LinkedList<>();

    public synchronized static Socket getSocket() {
        try {
            if(!idleSockets.isEmpty()){
                Socket socket = idleSockets.remove(0);
                usingSockets.add(socket);
                return socket;
            }else if (idleSockets.size() + usingSockets.size() < maxConnections) {
                Socket socket = new Socket(ServiceConnect.getHost(), ServiceConnect.getPort());
                usingSockets.add(socket);
                return socket;
            }
        }catch (Exception e){
            throw new RuntimeException("Can't create new connection.",e);
        }
        throw new RuntimeException("Can't create new connection.");
    }

    public static synchronized void returnSocket(Socket socket) {
        usingSockets.remove(socket);
        if(!socket.isClosed() && !socket.isConnected()){
            idleSockets.add(socket);
        }

    }
}
