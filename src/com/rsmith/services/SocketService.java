package com.rsmith.services;

import java.io.IOException;
import java.net.Socket;

import com.rsmith.models.transfer.ConnectionHandler;
import com.rsmith.models.transfer.ContentType;
import com.rsmith.models.transfer.MessageType;
import com.rsmith.models.transfer.Request;
import com.rsmith.models.transfer.RequestHandler;
import com.rsmith.models.transfer.Response;
import com.rsmith.models.transfer.ResponseType;
import com.rsmith.models.transfer.SocketReader;
import com.rsmith.models.transfer.SocketWriter;

public class SocketService {
    private SocketReader reader;
    private SocketWriter writer;
    private RequestHandler requestHandler;
    private ConnectionHandler connectionHandler;
    private Socket socket;
    
    public void setConnected(boolean connected){
	reader.setConnected(connected);
	writer.setConnected(connected);
	requestHandler.setConnected(connected);
    }

    public SocketService(Socket socket) {
	try {
	    reader = new SocketReader(socket);
	    writer = new SocketWriter(socket);
	    requestHandler = new RequestHandler(reader, writer);
	    connectionHandler = new ConnectionHandler(this);
	    this.socket = socket;
	    new Thread(reader).start();
	    new Thread(writer).start();
	    new Thread(requestHandler).start();
	    new Thread(connectionHandler).start();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public Response sendRequest(ContentType contentType, MessageType messageType, ResponseType responseType, String string) {
	Request request = new Request(getIpAddress(), contentType, messageType, responseType, string);
	writer.sendRequest(request);
	return reader.getResponseByRequestId(request.getId());
    }

    public String getIpAddress() {
	String ipAddress = socket.getRemoteSocketAddress().toString();
	return ipAddress;
    }
}
