package com.rsmith.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.rsmith.models.Game;
import com.rsmith.models.Player;

public class Server implements Runnable {
    private static final int PORT = 1321;
    private ServerSocket listener;
    private List<Client> clientList;
    
    public Server() throws IOException {
	listener = new ServerSocket(PORT);
	clientList = new ArrayList<Client>();
	
    }

    @Override
    public void run() {
	while (true) {
	    try {
		System.out.println("Listening for client connection...");
		Socket socket = listener.accept();
		System.out.println("Got client connection...");
		Player player = new Player("Player " + (clientList.size() + 1));
		Client client = new Client(player, socket);
		clientList.add(client);
		new Thread(client).start();
		
		if(clientList.size() > 1){
		    new Thread(new Game(clientList.get(0), clientList.get(1))).start();
		}
		Thread.sleep(100);
	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }
    
}
