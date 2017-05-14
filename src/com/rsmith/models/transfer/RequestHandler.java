package com.rsmith.models.transfer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsmith.models.board.BoardSpace;
import com.rsmith.models.board.Location;
import com.rsmith.models.board.Move;
import com.rsmith.models.game.Game;
import com.rsmith.models.game.Player;
import com.rsmith.services.GameService;
import com.rsmith.services.Server;
import com.rsmith.services.SocketService;

public class RequestHandler implements Runnable {

    private SocketReader reader;
    private SocketWriter writer;
    private ObjectMapper mapper;

    public RequestHandler(SocketReader reader, SocketWriter writer) {
	this.reader = reader;
	this.writer = writer;
	mapper = new ObjectMapper();
    }

    @Override
    public void run() {
	while (true) {

	    Request request = reader.getRequest();
	    if (request != null) {
		Response response = process(request);
		writer.addResponse(response);
	    }
	    sleep(10);
	}
    }

    private void sleep(int millis) {
	try {
	    Thread.sleep(millis);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    private Response process(Request request) {
	Response response = null;
	if (request != null) {
	    MessageType type = request.getMessageType();

	    switch (type) {
	    case PING:
		response = new Response(request.getId(), null, null, null);
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
	    response = getClientListResponse(request);
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

	try {
	    SocketService challenger = Server.getInstance().getSocketManagerByIp(request.getIpAddress());
	    SocketService challenged = Server.getInstance().getSocketManagerByIp(request.getPayload());

	    if (challenged != null) {
		response = challenged.sendRequest(ContentType.CHALLENGE, MessageType.POST, ResponseType.YES_NO, "");

		if (response != null) {
		    if (response.getPayload().equalsIgnoreCase("Y")) {
			
			Game game = GameService.getInstance().createGame(challenger, challenged);

			challenged.sendRequest(ContentType.BOARD, MessageType.POST, ResponseType.NONE,
				    mapper.writeValueAsString(game.getGameBoard().getBoardSpaces()));
			challenger.sendRequest(ContentType.BOARD, MessageType.POST, ResponseType.NONE,
				    mapper.writeValueAsString(game.getGameBoard().getBoardSpaces()));
			
			
			response = new Response(request.getId(), ContentType.BOARD, MessageType.POST, mapper.writeValueAsString(game.getGameBoard().getBoardSpaces()));
		    }
		}
	    }

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return response;
    }

    private Response getMoveResponse(Request request) {

	Response response = null;

	try {
	    Game game = GameService.getInstance().getGameByPlayerUsername(request.getIpAddress());
	    Player p1 = game.getPlayer1();
	    Player p2 = game.getPlayer2();
	    Player requestingPlayer = null;
	    
	    SocketService manager = null;

	    if (p1.getUsername().equals(request.getIpAddress())) {
		requestingPlayer = p1;
		manager = Server.getInstance().getSocketManagerByIp(p2.getUsername());
	    } else {
		requestingPlayer = p2;
		manager = Server.getInstance().getSocketManagerByIp(p1.getUsername());
	    }
		
	    Move move = new ObjectMapper().readValue(request.getPayload(), Move.class);

	    Location fromDTO = move.getFrom();
	    Location toDTO = move.getTo();

	    Location from = new Location(fromDTO.getX(), fromDTO.getY());
	    Location to = new Location(toDTO.getX(), toDTO.getY());

	    Move pieceMove = new Move(new Location(from.getX(), from.getY()), new Location(to.getX(), to.getY()));
	    
	    boolean moved = game.move(requestingPlayer, pieceMove);
	    
	    if(moved){
		List<BoardSpace> boardSpaces = game.getGameBoard().getBoardSpaces();
		manager.sendRequest(ContentType.BOARD, MessageType.POST, ResponseType.NONE, getValueAsString(boardSpaces));
	    }
	    
	    response = getBoardSpaceResponse(request);
	} catch (IOException e) {
	    e.printStackTrace();
	    response = new Response(request.getId(), ContentType.STRING, MessageType.ERROR, e.getMessage());
	}

	return response;
    }

    private List<Player> getLobbyPlayerList() {
	List<Player> playerList = new ArrayList<Player>();

	for (SocketService manager : Server.getInstance().getSocketManagers()) {
	    playerList.add(new Player(manager.getIpAddress(), null));
	}

	return playerList;
    }

    private Response getClientListResponse(Request request) {
	Response response = null;

	try {
	    response = new Response(request.getId(), ContentType.PLAYER_LIST, MessageType.POST,
		    mapper.writeValueAsString(getLobbyPlayerList()));
	} catch (JsonProcessingException e) {
	    e.printStackTrace();
	}

	return response;
    }

    private Response processGetRequest(Request request) {
	Response response = null;

	switch (request.getResponseType()) {
	case PLAYER_LIST:
	    response = getClientListResponse(request);
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

    private Response getBoardSpaceResponse(Request request) {
	Game game = GameService.getInstance().getGameByPlayerUsername(request.getIpAddress());
	List<BoardSpace> boardSpaces = game.getGameBoard().getBoardSpaces();
	return new Response(request.getId(), ContentType.BOARD, MessageType.POST, getValueAsString(boardSpaces));
    }
}
