package com.rsmith.server;

import java.io.IOException;
import java.util.List;

public class ConnectionManager implements Runnable {
    private Client client;

    public ConnectionManager(Client client) {
	this.client = client;
    }

    @Override
    public void run() {
	new Thread(new SocketReader(client)).start();
	new Thread(new SocketWriter()).start();
    }

    private class SocketReader implements Runnable {
	private Client client;

	SocketReader(Client client) {
	    this.client = client;
	}

	@Override
	public void run() {
	    while (true) {
		String message;
		try {
		    message = client.getReader().readLine();
		    if (message != null && !"".equals(message)) {
			System.out.println("Reading message from " + client.getPlayer().getUsername() + ": " + message);
			client.addInputMessage(message);
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
    
    private class SocketWriter implements Runnable {

	@Override
	public void run() {
	    while (true) {
		List<String> messages = client.getOutputMessages();
		client.clearOutputMessages();
		
		for(String message:messages){
		    client.getWriter().println(message);
		    client.getWriter().flush();
		}
		
		
		try {
		    Thread.sleep(500);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	}
    }
}
