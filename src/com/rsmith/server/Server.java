package com.rsmith.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.rsmith.models.Player;

public class Server implements Runnable {
    private static final int PORT = 1321;
    private ServerSocket listener;
    private List<Client> clientList;
    
    public Server() throws IOException {
	listener = new ServerSocket(PORT);
	clientList = new ArrayList<Client>();
	new Thread(new GameChallengeListener(this)).start();
    }
    
    public List<Client> getClientList(){
	return new ArrayList<Client>(clientList);
    }
    
    public void removeClient(Client client){
	clientList.remove(client);
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
    
    public void showLobby(Client client){
	
	for(int i = 0; i < 50; i++){
	    broadcast("                 ");
	}
	
	broadcast("---------------------------------------------------");
	broadcast("     Frostburg State University: Chess Lobby       ");
	broadcast("---------------------------------------------------");
	broadcast("                                                   ");
	broadcast("  Select a player to challenge to a game of chess  ");
	broadcast("---------------------------------------------------");
	broadcast(" <<<<<< Enter (X) to refresh client list >>>>>>>>> ");
	broadcast("---------------------------------------------------");
		
	if(clientList != null){
	    for(Client displayClient: clientList){
		if(!displayClient.equals(client)){
		    client.sendMessage("Player Id: " + displayClient.getPlayer().getId());
		}
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
		showLobby(client);
		Thread.sleep(100);
	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }    
}
