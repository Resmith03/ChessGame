package com.rsmith.server;

import java.io.IOException;

public class ClientReader implements Runnable {
    private Client client;

    public ClientReader(Client client) {
	this.client = client;
    }

    @Override
    public void run() {
	while (true) {
	    String message;
	    try {
		message = client.getReader().readLine();

		if (message != null && !"".equals(message)) {
		    System.out.println("Read message from client: " + message);
		    client.addInputMessage(message);
		}
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }
}
