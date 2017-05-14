package com.rsmith.models.transfer;

import com.rsmith.services.SocketService;
import com.rsmith.util.Logger;

public class ConnectionHandler implements Runnable {

    private boolean connected;
    private final int MAX_ATTEMPTS = 10;
    private final int PING_PAUSE = 5000;
    private SocketService manager;

    public ConnectionHandler(SocketService manager) {
	connected = true;
    }

    private Response sendPingRequest() {
	Response response = null;
	int counter = 0;

	while (MAX_ATTEMPTS < counter && response == null) {
	    counter++;
	    response = manager.sendRequest(ContentType.NONE,MessageType.PING,ResponseType.NONE,"");
	}

	return response;
    }

    private void sleep(int millis) {
	try {
	    Thread.sleep(millis);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    public boolean isConnected() {
	return connected;
    }

    @Override
    public void run() {
	while (connected) {
	    if (sendPingRequest() == null) {
		Logger.info("Closing connection handler due to invalid ping...");
		connected = false;
	    }

	    sleep(PING_PAUSE);
	}
    }

}
