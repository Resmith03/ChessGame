package com.rsmith.models;

import com.rsmith.server.Client;

public class ClientMessage {
    private Client client;
    private String message;
    
    public ClientMessage(Client client, String message){
	this.client = client;
	this.message = message;
    }

    public Client getClient() {
        return client;
    }

    public String getMessage() {
        return message;
    }
}
