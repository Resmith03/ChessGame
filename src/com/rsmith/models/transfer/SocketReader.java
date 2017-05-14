package com.rsmith.models.transfer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsmith.util.Logger;

public class SocketReader implements Runnable {

    private BufferedReader reader;
    private List<Request> requests;
    private List<Response> responses;
    private ObjectMapper mapper;

    public SocketReader(Socket socket) throws IOException {
	mapper = new ObjectMapper();
	requests = new ArrayList<Request>();
	responses = new ArrayList<Response>();
	reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
	while (true) {
	    try {
		String line = reader.readLine();
		if (line != null && !"".equals(line)) {
		    boolean processed = false;
		    
		    String requestType = "";
		    try {
			Request request = mapper.readValue(line, Request.class);
			Logger.info("Reader: Got Request: " + request.getId());
			requests.add(request);
			requestType = " <<REQUEST>> ";
			processed = true;
		    } catch (Exception ex) {

		    }

		    try {
			Response response = mapper.readValue(line, Response.class);
			Logger.info("Reader: Got Response: " + response.getRequestId());
			responses.add(response);
			requestType=" <<RESPONSE>> ";
			processed = true;
		    } catch (Exception ex) {

		    }

		    if (processed == false) {
			System.err.println("<<< UNABLE TO PROCESS REQUEST >>>");
			System.err.println(line);
		    }else{
			System.out.println("Processed Request: " + requestType + line);
		    }
		}

		Thread.sleep(10);

	    } catch (IOException ex) {
		ex.printStackTrace();
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }

    public Request getRequest() {
	Request request = null;

	if (requests.size() > 0) {
	    request = requests.get(0);
	    requests.remove(0);
	}

	return request;
    }

    private void sleep(int millis) {
	try {
	    Thread.sleep(millis);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
    
    private Response findResponse(String requestId){
	Response found = null;
	
	for(Response response:new ArrayList<Response>(responses)){
	    if(response.getRequestId().equals(requestId)){
		found = response;
		break;
	    }
	}
	
	return found;
    }
    
    private Response getResponse(String requestId) {
	Response response = null;

	Integer attempts = 0;

	while (response == null && attempts < 1000) {
	    attempts++;
	    response = findResponse(requestId);
	    sleep(10);
	}

	return response;
    }

    public Response getResponseByRequestId(String requestId) {
	return getResponse(requestId);
    }

}
