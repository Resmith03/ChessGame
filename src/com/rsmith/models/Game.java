package com.rsmith.models;
import java.util.List;

import com.rsmith.exceptions.InvalidMoveException;
import com.rsmith.server.Client;
import com.rsmith.service.RuleService;

/**
 * 
 * Controls the main operations of the game and coordinates with the players and board
 *
 */
public class Game implements Runnable {
	
	private Client player1;
	private Client player2;
	private Client currentPlayer;
	private GameBoard gameBoard;
	private RuleService service;
	private static final String INPUT_ERROR_MESSAGE = "Error processing move. Please ensure you are moving the right color and selected a valid space.";
	
	public Game(Client player1, Client player2){
	    player1.getPlayer().setColor(PlayerColor.BLACK);
	    player2.getPlayer().setColor(PlayerColor.WHITE);
	    this.player1 = player1;
	    this.player2 = player2;
	    this.currentPlayer = player1;
	    gameBoard = new GameBoard(this);
	    service = new RuleService(gameBoard);
	}
	
	public void broadcast(String message){
	    player1.sendMessage(message);
	    player2.sendMessage(message);
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
	
	private String getCurrentPlayerInput(){
	    String response = "";
	    
	    boolean inputValid = false;
	    
	    while(inputValid == false){
		List<String> inputList = currentPlayer.getMessages();
		if(inputList != null && inputList.size() > 0){
		    String input = inputList.get(0);
		    
		    if(checkInput(input)){
			inputValid=true;
			response = input;
		    }else{
			currentPlayer.clearInputMessages();
			currentPlayer.sendMessage("Invlid input format!");
			sleep(1000);
		    }
		}else{
		    sleep(1000);
		}
	    }
	    
	    return response;
	}
	
	private void sleep(int millis){
	    try {
		Thread.sleep(millis);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}	
	
	private Location askForLocation(String question){
	    currentPlayer.clearInputMessages();
	    currentPlayer.sendMessage("Player: " + currentPlayer.getPlayer().getColor() + " " + question);
	    String input = getCurrentPlayerInput();	    
	    return convertInputToLocation(input);
	}
	
	private void getMove() throws InvalidMoveException{
		Location start = askForLocation("Please input piece location (ex. B2):");
		Location end = askForLocation("Move piece to what location (ex. B3)?");
		
		if(!service.validMove(start, end)){
				getMove();
			}
		if(!gameBoard.move(currentPlayer.getPlayer(), start, end)){
		    currentPlayer.sendMessage(INPUT_ERROR_MESSAGE);
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
	
	private Location convertInputToLocation(String input){
	    int x = convertCharToInt(input.charAt(0));
	    int y = convertCharToInt(input.charAt(1));
	    return new Location(x - 1,y - 1);
	}
	
	private boolean isKingAlive(){
	    return gameBoard.isKingsAlive();
	}

	@Override
	public void run() {
	    printBoard();
	    updateTurn();
	    while(isKingAlive()){
		try {
			getMove();
			printBoard();
			updateTurn();
		} catch (InvalidMoveException e) {
			currentPlayer.sendMessage(e.getMessage());
		}
	    }
	    
	    player1.sendMessage("<><><><><><><><><><>");
	    player1.sendMessage("     GAME OVER!!!   ");
	    player1.sendMessage("<><><><><><><><><><>");
	    
	    player2.sendMessage("<><><><><><><><><><>");
	    player2.sendMessage("     GAME OVER!!!   ");
	    player2.sendMessage("<><><><><><><><><><>");
	    
	}
	
	private void updateTurn(){
	    
	    if(currentPlayer.equals(player1)){
		currentPlayer = player2;
		player1.sendMessage("Waiting on other player to move....");
	    }else{
		currentPlayer = player1;
		player2.sendMessage("Waiting on other player to move...");
	    }
	}
}
