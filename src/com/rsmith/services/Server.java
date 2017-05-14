package com.rsmith.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.rsmith.models.game.Game;
import com.rsmith.util.Logger;

public class Server implements Runnable {
    private static Server instance = new Server();
    private static final int PORT = 1321;
    private ServerSocket listener;
    private List<SocketService> managers;

    private Server() {
	listener = getServerSocket();
	managers = new ArrayList<SocketService>();
    }

    private ServerSocket getServerSocket() {
	ServerSocket socket = null;

	try {
	    socket = new ServerSocket(PORT);
	} catch (IOException e) {
	    e.printStackTrace();
	}

	return socket;
    }

    public static Server getInstance() {
	return instance;
    }
    
    public List<SocketService> getAvailablePlayers(){
	List<SocketService> availablePlayers = new ArrayList<SocketService>();
	
	for(SocketService manager:managers){
	    Game game = GameService.getInstance().getGameByPlayerUsername(manager.getIpAddress());
	    if(game == null){
		availablePlayers.add(manager);
	    }
	}
	
	return availablePlayers;
    }
    
    public SocketService getSocketManagerByIp(String ip){
	SocketService manager = null;
	
	for(SocketService curManager:managers){
	    String ipAddress = curManager.getIpAddress();
	    if(ipAddress.equals(ip)){
		manager = curManager;
		break;
	    }
	}
	
	return manager;
    }

    @Override
    public void run() {

	while (true) {
	    try {
		Logger.info("Listening for client connection...");
		Socket socket = listener.accept();
		Logger.info("Got client connection...");

		SocketService socketManager = new SocketService(socket);
		managers.add(socketManager);
		Thread.sleep(10);
	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }
}
