package com.rsmith.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsmith.DTO.BoardSpaceDTO;
import com.rsmith.DTO.GamePieceDTO;
import com.rsmith.DTO.PlayerDTO;
import com.rsmith.models.BoardSpace;
import com.rsmith.models.ContentType;
import com.rsmith.models.GameBoard;
import com.rsmith.models.GamePiece;
import com.rsmith.models.MessageType;
import com.rsmith.models.Player;
import com.rsmith.models.Request;
import com.rsmith.models.Response;
import com.rsmith.models.ResponseType;
import com.rsmith.util.Logger;

public class SocketManager {
    private SocketReader reader;
    private SocketWriter writer;
    private RequestHandler requestHandler;
    private ObjectMapper mapper; 
    private boolean connected;
    private final Integer RETRY_PAUSE = 10;
    private final Integer MAX_ATTEMPTS = 500;
    private ConnectionHandler connectionHandler;
    private Server server;
    public boolean isConnected(){
	return connected;
    }
    
    public SocketManager(Socket socket, Server server){
	try {
	    mapper = new ObjectMapper();	    
	    reader = new SocketReader(socket.getInputStream());
	    writer = new SocketWriter(socket.getOutputStream());
	    requestHandler = new RequestHandler(reader, writer);
	    connectionHandler = new ConnectionHandler(writer);
	    connected = true;
	    this.server = server;
	    new Thread(reader).start();
	    new Thread(writer).start();
	    new Thread(requestHandler).start();
	    new Thread(connectionHandler).start();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    private void sleep(){
	try {
	    Thread.sleep(RETRY_PAUSE);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }
    
    private class SocketReader implements Runnable {
	private BufferedReader reader;
	private List<Request> requests;
	private List<Response>responses;
	private ObjectMapper mapper;
	
	SocketReader(InputStream inputStream) throws IOException {
	    mapper = new ObjectMapper();
	    requests = new ArrayList<Request>();
	    responses = new ArrayList<Response>();
	    reader = new BufferedReader(new InputStreamReader(inputStream));
	}

	@Override
	public void run() {
	    while (connected) {
		try {
		    String line = reader.readLine();
		    if(line != null && !"".equals(line)){
			   boolean processed = false;
			    
			    try{
				Request request = mapper.readValue(line, Request.class);
				Logger.info("Reader: Got Request: " + request.getId());
				requests.add(request);
				processed = true;
			    }catch(Exception ex){
			    }
			    
			    try{
				Response response = mapper.readValue(line, Response.class);
				Logger.info("Reader: Got Response: " + response.getRequestId());
				responses.add(response);
				processed = true;
			    }catch(Exception ex){
			    }
			    
			    if(processed == false){
				System.err.println("<<< UNABLE TO PROCESS REQUEST >>>");
				System.err.println(line);
			    }
		    }
		    
		    
		    Thread.sleep(10);
		    
		} 
		catch(IOException ex){
		    ex.printStackTrace();
		    connected = false;
		}
		catch (Exception e) {
		    e.printStackTrace();
		    connected = false;
		}		
	    }
	}

	public Response getResponseByRequestId(String requestId) {
	    Response response = null;
	    List<Response> responseList = new ArrayList<Response>(responses);
	    for(Response curResponse:responseList){
		if(curResponse != null){
        		if(curResponse.getRequestId().equals(requestId)){
        		    response = curResponse;
        		    responses.remove(curResponse);
        		    break;
        		}
		}
	    }
	    
	    return response;
	}

	public Request getRequest() {
	    Request request = null;
	    
	    if(requests.size() > 0){
		request = requests.get(0);
		requests.remove(0);
	    }
	    
	    return request;
	}
    }
    
    private class SocketWriter implements Runnable {
	private List<Request> requests;
	private List<Response> responses;
	private PrintWriter writer;
	private ObjectMapper mapper;
	
	public SocketWriter(OutputStream outputStream) throws IOException{
	    requests = new ArrayList<Request>();
	    responses = new ArrayList<Response>();
	    writer = new PrintWriter(outputStream, true);
	    mapper = new ObjectMapper();
	}
	
	private void sendRequests() throws JsonProcessingException{
	    for(Request request:new ArrayList<Request>(requests)){
		writer.println(mapper.writeValueAsString(request));
		writer.flush();
		Logger.info("Writer: Sending Request: " + request.getId());
		requests.remove(request);
	    }
	}
	
	private void sendResponses() throws JsonProcessingException{
	    for(Response response:new ArrayList<Response>(responses)){
		writer.println(mapper.writeValueAsString(response));
		writer.flush();
		Logger.info("Writer: Sending response: " + response.getId() + " content: " + response.getPayload());
		responses.remove(response);
	    }
	}
	@Override
	public void run() {
	    while (connected) {
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
	
	public Response sendRequest(Request request){
	    Response response = null;
	    String requestId = request.getId();
	    
	    Logger.info("Writer: Queue request: " + request.getId());
	    requests.add(request);

	    Integer attempts = 0;
	    while(response == null && attempts < MAX_ATTEMPTS){
		attempts++;
	   	response = reader.getResponseByRequestId(requestId);
	   	
	   	try {
		    Thread.sleep(1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	    
	    return response;
	}
    }
    
    private class RequestHandler implements Runnable{
	private SocketReader reader;
	private SocketWriter writer;
	
	public RequestHandler(SocketReader reader, SocketWriter writer){
	    this.reader = reader;
	    this.writer = writer;
	}
	
	@Override
	public void run() {
	    while(connected){
		
		Request request = reader.getRequest();
		if(request != null){
		    Response response = process(request);
		    writer.addResponse(response);
		}
		try {
		    Thread.sleep(10);
		} catch (InterruptedException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	}


	private Response process(Request request) {
	    Response response = null;
	    if(request != null){
        	    MessageType type = request.getMessageType();
        	    
        	    switch(type){
        	    	case PING:
        	    	    response = processPingRequest(request);
        	    	    break;
        	    	case GET:
        	    	    response = processGetRequest(request);
        	    	    break;
        	    	case POST:
        	    	    response = processPostRequest(request);
        	    	    break;
        	    	case INFO:
        	    	    response = processInfoRequest(request);
        	    	    break;
        	    }
	    }
	    return response;
	}


	private Response processInfoRequest(Request request) {
	    // TODO Auto-generated method stub
	    return null;
	}


	private Response processPostRequest(Request request) {
	    Response response = null;
	    ContentType type = request.getContentType();
	    
	    switch(type){
	    	case PLAYER_LIST: response = getPlayerListResponse(request);
	    }
	    
	    return response;
	}

	private Response getPlayerListResponse(Request request){
	    List<Player> playerList = server.getPlayerList();
	    List<PlayerDTO> playerDTOList = new ArrayList<PlayerDTO>();
	    
	    for(Player player:playerList){
		PlayerDTO dto = new PlayerDTO();
		
		dto.setId(player.getId());
		dto.setUsername(player.getUsername());
		playerDTOList.add(dto);
	    }
	    
	    String dtoString = "";
	    try {
		dtoString = mapper.writeValueAsString(playerDTOList);
	    } catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return new Response(request.getId(), ContentType.PLAYER_LIST, MessageType.POST,dtoString);
	}
	
	private Response processGetRequest(Request request) {
	    Response response = null;
	    ResponseType type = request.getResponseType();
	    
	    switch(type){
	    	case PLAYER_LIST: response = getPlayerListResponse(request);
	    	break;
	    	case BOARD: response = getBoardSpaceResponse(request);
	    	break;
		
	    }
	    
	    return response;
	}


	private Response getBoardSpaceResponse(Request request) {
	    Response response = null;
	    
	    List<BoardSpaceDTO> spaces = new ArrayList<BoardSpaceDTO>();
	    
	    for(BoardSpace space: GameBoard.getDefaultBoard()){
		BoardSpaceDTO dto = new BoardSpaceDTO();
		GamePieceDTO pieceDTO = new GamePieceDTO();
		
		GamePiece piece = space.getGamePiece();
		if(piece != null){
		    pieceDTO.setPlayerColor(piece.getColor().toString());
		    pieceDTO.setPieceType(piece.getType().toString());
		    pieceDTO.setSymbol(piece.getChar());
		}
		dto.setGamePiece(pieceDTO);
		dto.setX(space.getX());
		dto.setY(space.getY());
		spaces.add(dto);
	    }
	    
	    String boardString = "";
	    try {
		boardString = new ObjectMapper().writeValueAsString(spaces);
	    } catch (JsonProcessingException e) {
		e.printStackTrace();
	    }
	    
	    response = new Response(request.getId(), ContentType.BOARD, MessageType.POST, boardString);
	    return response;
	}

	private Response processPingRequest(Request request) {
	    // TODO Auto-generated method stub
	    return null;
	}
	
    }

    private class ConnectionHandler implements Runnable{
	private SocketWriter writer;
	
	public ConnectionHandler(SocketWriter writer){
	    this.writer = writer;
	}
	
	@Override
	public void run() {
	    while(connected){
		Response response = writer.sendRequest(new Request(ContentType.NONE, MessageType.PING, ResponseType.NONE, ""));
		if(response == null){
		    Logger.info("Closing connection...");
		    connected = false;
		}
		try {
		    Thread.sleep(5000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
	}
	
    }

    public Response sendRequest(Request request) {
	return writer.sendRequest(request);
    }
    
}
