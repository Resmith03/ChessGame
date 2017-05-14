package com.rsmith.models.transfer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SocketWriter implements Runnable {

    private List<Request> requests;
    private List<Response> responses;
    private PrintWriter writer;
    private ObjectMapper mapper;
    
    public SocketWriter(Socket socket) throws IOException {
	requests = new ArrayList<Request>();
	responses = new ArrayList<Response>();
	writer = new PrintWriter(socket.getOutputStream(), true);
	mapper = new ObjectMapper();
    }

    private void sendRequests() throws JsonProcessingException {
	for (Request request : new ArrayList<Request>(requests)) {
	    writer.println(mapper.writeValueAsString(request));
	    writer.flush();
	    requests.remove(request);
	}
    }

    private void sendResponses() throws JsonProcessingException {
	for (Response response : new ArrayList<Response>(responses)) {
	    System.out.println("Sending response to client: " + response.getPayload());
	    writer.println(mapper.writeValueAsString(response));
	    writer.flush();
	    responses.remove(response);
	}
    }

    @Override
    public void run() {
	while (true) {
	    try {
		sendRequests();
		sendResponses();
		Thread.sleep(10);
	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	}
    }

    public void addResponse(Response response) {
	responses.add(response);
    }

    public void sendRequest(Request request) {
	requests.add(request);
    }

}
