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
import com.rsmith.DTO.LocationDTO;
import com.rsmith.DTO.MoveDTO;
import com.rsmith.DTO.PlayerDTO;
import com.rsmith.models.BoardSpace;
import com.rsmith.models.ContentType;
import com.rsmith.models.Game;
import com.rsmith.models.Location;
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
    private ConnectionHandler connectionHandler;
    private Game game;
    private Player player;

    public void removeGame() {
	game = null;
    }

    public boolean isConnected() {
	return connected;
    }

    public SocketManager(Socket socket, Player player) {
	try {
	    this.player = player;
	    mapper = new ObjectMapper();
	    reader = new SocketReader(socket.getInputStream());
	    writer = new SocketWriter(socket.getOutputStream());
	    requestHandler = new RequestHandler(reader, writer);
	    connectionHandler = new ConnectionHandler(writer);
	    connected = true;
	    new Thread(reader).start();
	    new Thread(writer).start();
	    new Thread(requestHandler).start();
	    new Thread(connectionHandler).start();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public Player getPlayer(){
	return this.player;
    }
    private class SocketReader implements Runnable {
	private BufferedReader reader;
	private List<Request> requests;
	private List<Response> responses;
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
		    if (line != null && !"".equals(line)) {
			boolean processed = false;

			try {
			    Request request = mapper.readValue(line, Request.class);
			    Logger.info("Reader: Got Request: " + request.getId());
			    requests.add(request);
			    processed = true;
			} catch (Exception ex) {

			}

			try {
			    Response response = mapper.readValue(line, Response.class);
			    Logger.info("Reader: Got Response: " + response.getRequestId());
			    responses.add(response);
			    processed = true;
			} catch (Exception ex) {

			}

			if (processed == false) {
			    System.err.println("<<< UNABLE TO PROCESS REQUEST >>>");
			    System.err.println(line);
			}
		    }

		    Thread.sleep(10);

		} catch (IOException ex) {
		    ex.printStackTrace();
		    connected = false;
		} catch (Exception e) {
		    e.printStackTrace();
		    connected = false;
		}
	    }
	}

	public Response getResponseByRequestId(String requestId) {
	    Response response = null;
	    List<Response> responseList = new ArrayList<Response>(responses);
	    for (Response curResponse : responseList) {
		if (curResponse != null) {
		    if (curResponse.getRequestId().equals(requestId)) {
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

	    if (requests.size() > 0) {
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

	public SocketWriter(OutputStream outputStream) throws IOException {
	    requests = new ArrayList<Request>();
	    responses = new ArrayList<Response>();
	    writer = new PrintWriter(outputStream, true);
	    mapper = new ObjectMapper();
	}

	private void sendRequests() throws JsonProcessingException {
	    for (Request request : new ArrayList<Request>(requests)) {
		writer.println(mapper.writeValueAsString(request));
		writer.flush();
		Logger.info("Writer: Sending Request: " + request.getId());
		requests.remove(request);
	    }
	}

	private void sendResponses() throws JsonProcessingException {
	    for (Response response : new ArrayList<Response>(responses)) {
		writer.println(mapper.writeValueAsString(response));
		writer.flush();
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

	public Response sendRequest(Request request) {
	    Response response = null;
	    String requestId = request.getId();

	    Logger.info("Writer: Queue request: " + request.getId());
	    requests.add(request);

	    Integer attempts = 0;
	    while (response == null && attempts < 60) {
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

    private class RequestHandler implements Runnable {
	private SocketReader reader;
	private SocketWriter writer;

	public RequestHandler(SocketReader reader, SocketWriter writer) {
	    this.reader = reader;
	    this.writer = writer;
	}

	@Override
	public void run() {
	    while (connected) {

		Request request = reader.getRequest();
		if (request != null) {
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
	    if (request != null) {
		MessageType type = request.getMessageType();

		switch (type) {
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
		case ERROR:
		    break;
		default:
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

	    switch (type) {
	    case PLAYER_LIST:
		response = getPlayerListResponse(request);
		break;
	    case MOVE:
		response = getMoveResponse(request);
	    case BOARD:
		break;
	    case NONE:
		break;
	    case PLAYER:
		break;
	    case STRING:
		break;
	    case CHALLENGE: 
		response = getChallengeResponse(request);
		break;
	    default:
		break;
	    }

	    return response;
	}
	
	
	private Response getChallengeResponse(Request request) {
	    Response response = null;
	    
	    try{
		String id = request.getPayload();
		
		List<SocketManager> managers = Server.getInstance().getSocketManagers();
		SocketManager challenge = null;

		if(managers != null && managers.size() > 0){
		    for(SocketManager manager:managers){
			if(manager.getPlayer().getId().equals(Integer.valueOf(id))){
			    challenge = manager;
			    break;
			}
		    }
		}
		
		Game newGame = new Game(player,challenge.getPlayer());
		game = newGame;
		if(challenge != null){
		    System.out.println("<<< Sending challenge request >>>");
		    response = challenge.sendRequest(new Request(ContentType.CHALLENGE, MessageType.POST, ResponseType.YES_NO, getValueAsString(getCurrentBoardSpaces())));
		    if(response.getPayload().equalsIgnoreCase("Y")){
			response = new Response(request.getId(), ContentType.BOARD, MessageType.POST, getValueAsString(getCurrentBoardSpaces()));
		    }
		    System.out.println("<<< Server Got Challenge Response: " + response + " >>>>");
		}
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	    
	    return response;
	}

	private Response getMoveResponse(Request request) {

	    if (game == null) {
		return new Response(request.getId(), ContentType.STRING, MessageType.ERROR, "No active game");
	    }

	    Response response = null;

	    try {

		MoveDTO move = mapper.readValue(request.getPayload(), MoveDTO.class);

		LocationDTO fromDTO = move.getFrom();
		LocationDTO toDTO = move.getTo();

		Location from = new Location(fromDTO.getX(), fromDTO.getY());
		Location to = new Location(toDTO.getX(), toDTO.getY());

		game.move(new Location(from.getX(), from.getY()), new Location(to.getX(), to.getY()));
		response = this.getBoardSpaceResponse(request);
	    } catch (IOException e) {
		e.printStackTrace();
		response = new Response(request.getId(), ContentType.STRING, MessageType.ERROR, e.getMessage());
		System.out.println("Error processing request: " + e.getMessage());
	    }

	    return response;
	}

	private Response getPlayerListResponse(Request request) {
	    List<SocketManager> managers = Server.getInstance().getSocketManagers();
	    List<PlayerDTO> playerDTOList = new ArrayList<PlayerDTO>();

	    for (SocketManager manager : managers) {
		PlayerDTO dto = new PlayerDTO();
		
		dto.setId(manager.getPlayer().getId());
		dto.setUsername(manager.getPlayer().getUsername());
		playerDTOList.add(dto);
	    }

	    String dtoString = "";
	    try {
		dtoString = mapper.writeValueAsString(playerDTOList);
	    } catch (JsonProcessingException e) {
		e.printStackTrace();
	    }
	    return new Response(request.getId(), ContentType.PLAYER_LIST, MessageType.POST, dtoString);
	}

	private Response processGetRequest(Request request) {
	    Response response = null;

	    switch (request.getResponseType()) {
	    case PLAYER_LIST:
		response = getPlayerListResponse(request);
		break;

	    case BOARD:
		response = getBoardSpaceResponse(request);
		break;

	    case YES_NO:
		break;

	    case INTEGER:
		break;

	    case LOCATION:
		break;

	    case MOVE:
		break;

	    case NONE:
		break;

	    case STRING:
		break;

	    default:
		break;
	    }

	    return response;
	}

	private String getValueAsString(Object object) {
	    String value = "";

	    try {
		value = mapper.writeValueAsString(object);
	    } catch (JsonProcessingException e) {
		e.printStackTrace();
	    }

	    return value;
	}
	private List<BoardSpaceDTO> getCurrentBoardSpaces(){
	    List<BoardSpaceDTO> boardSpaces = new ArrayList<BoardSpaceDTO>();
	    if(game != null){
    	    	for (BoardSpace space : game.getGameBoard().getBoardSpaces()) {
    	    	    boardSpaces.add(space.toDTO());
    	    	}
	    }
	    return boardSpaces;
	}
	private Response getBoardSpaceResponse(Request request) {
	    List<BoardSpaceDTO> boardSpaces = getCurrentBoardSpaces();
	    return new Response(request.getId(), ContentType.BOARD, MessageType.POST, getValueAsString(boardSpaces));
	}

	private Response processPingRequest(Request request) {
	    // TODO Auto-generated method stub
	    return null;
	}

    }

    private class ConnectionHandler implements Runnable {
	private SocketWriter writer;

	public ConnectionHandler(SocketWriter writer) {
	    this.writer = writer;
	}

	@Override
	public void run() {
	    while (connected) {
		Response response = writer
			.sendRequest(new Request(ContentType.NONE, MessageType.PING, ResponseType.NONE, ""));
		if (response == null) {
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
