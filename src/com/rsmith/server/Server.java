package com.rsmith.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.rsmith.models.Player;
import com.rsmith.util.Logger;

public class Server implements Runnable {
    private static Server instance = new Server();
    private static final int PORT = 1321;
    private ServerSocket listener;
    private List<SocketManager> managers;
    
    private Server() {
	listener = getServerSocket();
	managers = new ArrayList<SocketManager>();
    }
    
    private ServerSocket getServerSocket(){
	ServerSocket socket = null;
	
	try {
	    socket = new ServerSocket(PORT);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	return socket;
    }
    
    public static Server getInstance(){
	return instance;
    }
    
    public List<SocketManager> getSocketManagers(){
	return new ArrayList<SocketManager>(managers);
    }
    
    @Override
    public void run() {
	
	while (true) {
	    try {
		Logger.info("Listening for client connection...");
		Socket socket = listener.accept();
		Logger.info("Got client connection...");
		
		
		String playerUsername = "Player " + (managers.size() + 1);
		Player player = new Player(playerUsername);
		
		SocketManager socketManager = new SocketManager(socket, player);
		managers.add(socketManager);
		Thread.sleep(100);
	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }    
}
