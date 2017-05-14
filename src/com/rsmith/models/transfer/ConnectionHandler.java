package com.rsmith.models.transfer;

import com.rsmith.services.Server;
import com.rsmith.services.SocketService;
import com.rsmith.util.Logger;

public class ConnectionHandler implements Runnable {

    private boolean connected;
    private SocketService manager;

    public ConnectionHandler(SocketService manager) {
	connected = true;
	this.manager=manager;
    }

    private Response sendPingRequest() {
	Response response = null;

	response = manager.sendRequest(ContentType.NONE,MessageType.PING,ResponseType.NONE,"");
	

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
		break;
	    }
	    
	    sleep(5000);
	}
	Logger.info("<<<<<<<<<<<<<<< DISCONNECTING CLIENT >>>>>>>>>>>>>");
	Server.getInstance().removeDisconnectedManager(manager);
    }

}
