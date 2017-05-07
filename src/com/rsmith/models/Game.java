package com.rsmith.models;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * Controls the main operations of the game and coordinates with the players and board
 *
 */
public class Game implements Runnable {
	
	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private GameBoard gameBoard;
	private ObjectMapper mapper;
	
	private static final String INPUT_ERROR_MESSAGE = "Error processing move. Please ensure you are moving the right color and selected a valid space.";
	
	public Game(Player player1, Player player2){
	    mapper = new ObjectMapper();
	    player1.setColor(PlayerColor.BLACK);
	    player2.setColor(PlayerColor.WHITE);
	    this.player1 = player1;
	    this.player2 = player2;
	    this.currentPlayer = player1;
	    gameBoard = new GameBoard(this);
	}
	
	public void broadcast(String message){
	    player1.sendRequest(new Request(ContentType.STRING,MessageType.POST, ResponseType.NONE,message));
	    player2.sendRequest(new Request(ContentType.STRING,MessageType.POST, ResponseType.NONE,message));
	}
	
	private void printBlankLines(int lines){
	    String space = "          ";
	    for(int i = 0; i < lines; i++){
		broadcast(space);
	    }
	}
	
	private void printBoard(){		
	    printBlankLines(1);
	    printLogo();
	    printHeader();
	    gameBoard.printBoard();
	    printFooter();
	}
	
	private void printLogo(){
	    broadcast("----------------------------------");
	    broadcast(" Frostburg State University:Chess ");
	    broadcast("----------------------------------");
	}
	private void printFooter(){
	    broadcast("----------------------------------");
	}
	
	private void printHeader() {
	    broadcast("                                  ");
	    broadcast("         WHITE = UPPERCASE        ");
	    broadcast("         BLACK = LOWERCASE        ");
	    broadcast("----------------------------------");
	    broadcast("       A  B  C  D  E  F  G  H     ");
	    broadcast("----------------------------------");
	}
	
	private boolean validLength(String input){
	    boolean valid = true;
	    
	    if(input.length() != 2){
		valid = false;
	    }
	    
	    return valid;
	}
	
	private boolean validFormat(String input){
	    boolean valid = false;
	    
	    String firstChar = String.valueOf(input.charAt(0));
	    String secondChar = String.valueOf(input.charAt(1));
		
	    if(firstChar.matches("^[A-Z]+$") && secondChar.matches("^[0-9]+$")){
		valid = true;
	    }
	    
	    return valid;
	}
	
	private boolean validRange(String input){
	    boolean valid = true;
	    
	    char x = input.charAt(0);
	    int y = Character.getNumericValue(input.charAt(1));
	    
	    if(x < 'A' || x > 'H' || y < 1 || y > 8){
		valid = false;
	    }
	    
	    return valid;
	    
	}
	
	private boolean checkInput(String input){
	    boolean valid = false;
	    
	    if(validLength(input)){
		if(validFormat(input)){
		    if(validRange(input)){
			valid = true;
		    }
		}
	    }
	    
	    return valid;
	}
	
	private Location askForLocation(String question){
	    Location location = null;
	    
	    try{
		Response response = currentPlayer.sendRequest(new Request(ContentType.STRING, MessageType.GET, ResponseType.LOCATION,"PLAYER " + currentPlayer.getColor() + " " + question));
		location = mapper.readValue(response.getPayload(), Location.class);
	    }catch(Exception ex){
		ex.printStackTrace();
	    }
	    
	    return location;
	}
	
	private void getMove(){
		Location start = askForLocation("Please input piece location (ex. B2):");
		Location end = askForLocation("Move piece to what location (ex. B3)?");
		
		if(!gameBoard.move(currentPlayer, start, end)){
		    currentPlayer.sendRequest(new Request(ContentType.STRING, MessageType.INFO, ResponseType.NONE, INPUT_ERROR_MESSAGE));
		    getMove();
		}
	}
	
	private int convertCharToInt(char y){
	    int response = 0;
	    
	    switch(y){
	    	case 'A': response = 1;
	    	break;
	    	case 'B': response = 2;
	    	break;
	    	case 'C': response = 3;
	    	break;
	    	case 'D': response = 4;
	    	break;
	    	case 'E': response = 5;
	    	break;
	    	case 'F': response = 6;
	    	break;
	    	case 'G': response = 7;
	    	break;
	    	case 'H': response = 8;
	    	break;
	    	default:response = Character.getNumericValue(y);
	    }
	    
	    return response;
	}
	
	private boolean isKingAlive(){
	    return gameBoard.isKingsAlive();
	}

	@Override
	public void run() {
	    printBoard();
	    updateTurn();
	    while(isKingAlive()){
		getMove();
		printBoard();
		updateTurn();
	    }
	    //TODO: send GAME OVER message
	    
	}
	
	private void updateTurn(){
	    
	    if(currentPlayer.equals(player1)){
		currentPlayer = player2;
		player1.sendRequest(new Request(ContentType.STRING, MessageType.INFO, ResponseType.NONE, "Waiting on other player to move...."));
	    }else{
		currentPlayer = player1;
		player2.sendRequest(new Request(ContentType.STRING, MessageType.INFO, ResponseType.NONE, "Waiting on other player to move...."));
	    }
	}
}
