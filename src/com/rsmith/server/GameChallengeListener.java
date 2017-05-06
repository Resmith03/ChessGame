package com.rsmith.server;

import java.util.Iterator;
import java.util.List;

import com.rsmith.models.Game;

public class GameChallengeListener implements Runnable{
    
    
    private Server server;
    
    public GameChallengeListener(Server server){
	this.server = server;
    }
    
    
    private void sleep(int millis){
	try {
	    Thread.sleep(millis);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
    
    private String getClientResponse(Client client, String question){
	client.sendMessage(question);
	boolean validInput = false;
	
	String response = "";
	
	while(validInput == false){
	    response = client.getInputMessage();
	    
	    if(response != null && !"".equals(response)){
		validInput = true;
	    }else{
		sleep(1000);
	    }
	}
	
	return response;
    }
    
    private Client getClientById(Integer id){
	Client found = null;
	
	List<Client> clientList = server.getClientList();
	
	if(clientList != null){
	    for(Client client:clientList){
		if(client.getPlayer().getId() == id){
		    found = client;
		    break;
		}
	    }
	}
	
	return found;
    }
    private void challengeClient(Client client, String input){
	try{
	    Integer id = Integer.valueOf(input);
	    Client challenge = getClientById(id);
	    
	    if(challenge != null){
		String response = getClientResponse(challenge, "Do you want to play chess with player: " + client.getPlayer().getUsername() + " (y/n)");
		
		if(response.equalsIgnoreCase("Y")){
		    server.removeClient(client);
		    server.removeClient(challenge);
		    new Thread(new Game(client, challenge)).start();
		}
	    }
	}catch(Exception ex){
	    ex.printStackTrace();
	}
    }
    
    
    @Override
    public void run() {
	
	while(true){
	    List<Client> clientList = server.getClientList();
	    
	    if(clientList != null){
		Iterator<Client> iterator = clientList.iterator();
		while(iterator.hasNext()){
		    Client client = iterator.next();
		    String input = client.getInputMessage();
		    
		    if(input != null && !"".equals(input)){
			if(input.equalsIgnoreCase("x")){
			    server.showLobby(client);
			}else{
			    challengeClient(client, input);
			}
		    }
		}
	    }
	    
	    sleep(100);
	}
    }
}
