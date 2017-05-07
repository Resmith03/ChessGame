package com.rsmith.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.rsmith.models.Player;
import com.rsmith.util.Logger;

public class Server implements Runnable {
    private static final int PORT = 1321;
    private ServerSocket listener;
    private List<Player> playerList;
    
    public Server() throws IOException {
	listener = new ServerSocket(PORT);
	playerList = new ArrayList<Player>();
    }
    
    public List<Player> getPlayerList(){
	return new ArrayList<Player>(playerList);
    }
    

    
    @Override
    public void run() {
	
	while (true) {
	    try {
		Logger.info("Listening for client connection...");
		Socket socket = listener.accept();
		Logger.info("Got client connection...");
		
		SocketManager socketManager = new SocketManager(socket,this);
		String playerUsername = "Player " + (playerList.size() + 1);
		Player player = new Player(playerUsername, socketManager);
		playerList.add(player);
		Thread.sleep(100);
	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }    
}
