package com.rsmith.server;

import java.util.List;

public class ClientWriter implements Runnable {
    private Client client;

    public ClientWriter(Client client) {
	this.client = client;
    }

    @Override
    public void run() {
	List<String> messageList = client.getOutputMessages();

	if (messageList != null && messageList.size() > 0) {
	    for (String message : messageList) {
		System.out.println("Writing message to server: " + message);
		client.getWriter().write(message);
	    }

	    client.clearOutputMessages();
	}
    }

}
