package com.rsmith.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.rsmith.models.Game;
import com.rsmith.models.Player;
import com.rsmith.models.PlayerColor;

public class Server implements Runnable {
    private static final int PORT = 1321;
    private ServerSocket listener;
    private List<Client> clientList;
    
    public Server() throws IOException {
	listener = new ServerSocket(PORT);
	clientList = new ArrayList<Client>();
	
    }
    
    
    public void broadcast(String message){
	for(Client client:clientList){
	    client.sendMessage(message);
	}
    }
    
    public void broadcastToOtherPlayers(Client requestingClient, String message){
	for(Client client:clientList){
	    if(!client.equals(requestingClient)){
		client.sendMessage(message);
	    }
	}
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
		    
		    Client player1 = clientList.get(0);
		    Client player2 = clientList.get(1);
		    
		    player1.getPlayer().setColor(PlayerColor.BLACK);
		    player2.getPlayer().setColor(PlayerColor.WHITE);
		    
		    clientList.remove(player1);
		    clientList.remove(player2);
		    
		    new Thread(new Game(player1,player2)).start();
		}
		
		printAvailablePlayers();
		Thread.sleep(100);
	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }
    
    private void printAvailablePlayers(){
	broadcast("     Frostburg State University: Chess Lobby       ");
	broadcast("___________________________________________________");
	broadcast("                                                   ");
	broadcast("  Select a player to challenge to a game of chess  ");
	broadcast("___________________________________________________");
	
	int counter = 1;
	for(Client client: clientList){
	    broadcast(String.valueOf(counter) + " : " + client.getPlayer().username);
	}
    }
    
}
