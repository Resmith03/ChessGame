package com.rsmith.models;
import java.util.HashMap;
import java.util.List;

import com.rsmith.server.Client;

public class Game implements Runnable {
	
	private Client player1;
	private Client player2;
	private Client currentPlayer;
	private GameBoard gameBoard;
	
	public Game(Client player1, Client player2){
	    this.player1 = player1;
	    this.player2 = player2;
	    this.currentPlayer = player1;
	    gameBoard = new GameBoard(this);
	}
	
	public void broadcast(String message){
	    player1.sendMessage(message);
	    player2.sendMessage(message);
	}
	
	private void gameStart(){	
	    broadcast("Welcome to Frostburg State University: Chess");
	}
	
	private void printBlankLines(int lines){
	    String space = "          ";
	    for(int i = 0; i < lines; i++){
		broadcast(space);
	    }
	}
	
	private void printBoard(){		
	    printBlankLines(50);
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
	    broadcast("><><><><><><><><><><><><><><><><><");
	    broadcast("----------------------------------");
	}
	
	private void printHeader() {
	    
	    broadcast("----------------------------------");
	    broadcast("WHITE = UPPERCASE BLACK= LOWERCASE");
	    broadcast("----------------------------------");
	    broadcast(" Col:  A  B  C  D  E  F  G  H     ");
	    broadcast("----------------------------------");
	}
	
	private boolean checkInput(String input){
	    boolean valid = true;
	    
	    if(input.length() != 2){
		valid = false;
	    }
	    
	    String firstChar = input.substring(0, 1);
	    String secondChar = input.substring(1, input.length());
		
	    if(!firstChar.matches("^[A-Z]+$") || !secondChar.matches("^[0-9]+$")){
		valid = false;
	    }
	    
	    char xCoord = input.charAt(0);
	    int yCoord = Integer.valueOf(input.substring(1, input.length()));
	    if(xCoord < 'A' || xCoord > 'H' || yCoord < 1 || yCoord > 8){
		valid = false;
	    }
	    
	    return valid;
	}
	
	private String getCurrentPlayerInput(){
	    currentPlayer.clearInputMessages();
	    
	    String response = "";
	    
	    boolean inputValid = false;
	    
	    while(inputValid == false){
		List<String> inputList = currentPlayer.getMessages();
		
		if(inputIsValid(inputList)){
		    inputValid = true;
		    response = inputList.get(0);
		}else{
		    try {
			Thread.sleep(1000);
		    } catch (InterruptedException e) {
			e.printStackTrace();
		    }
		}
	    }
	    
	    return response;
	}
	
	private boolean inputIsValid(List<String> inputs){
	    boolean valid = false;
	    
	    if(inputs != null && inputs.size() > 0){
		valid = checkInput(inputs.get(0));
		if(valid == false){
		    currentPlayer.sendMessage("Invlid input format!");
		    currentPlayer.clearInputMessages();
		}
	    }
	    
	    return valid;
	}
	private void getMove(){
		currentPlayer.sendMessage("Player: " + currentPlayer.getPlayer().getColor() +" Please input piece location (ex. B2):");
		String start = getCurrentPlayerInput();
		
		String end = "";
		if(start != " "){
			if(checkInput(start)){
				currentPlayer.sendMessage("Player: " + currentPlayer.getPlayer().getColor() + " Move piece to what location (ex. B3)?");
				end = getCurrentPlayerInput();
			}
			else{
				currentPlayer.sendMessage("Invalid input. ");
				getMove();
			}
		}
		else {
		    	currentPlayer.sendMessage("There is no piece there.");
			getMove();
		}
		
		if(!move(start, end)){
		    currentPlayer.sendMessage("Error processing move. Please ensure you are moving the right color and selected a valid space.");
		    getMove();
		}
	}
	
	private boolean move(String from, String to){
		HashMap<Character, Integer> xCoords = new HashMap<Character, Integer>();
		xCoords.put('A', 1);
		xCoords.put('B', 2);
		xCoords.put('C', 3);
		xCoords.put('D', 4);
		xCoords.put('E', 5);
		xCoords.put('F', 6);
		xCoords.put('G', 7);
		xCoords.put('H', 8);
		
		int startX = xCoords.get(from.charAt(0));
		int startY = Integer.valueOf(from.substring(1, from.length()));
		int endX = xCoords.get(to.charAt(0));
		int endY = Integer.valueOf(to.substring(1, to.length()));
		
		Location fromLocation = new Location(startX-1, startY-1);
		Location toLocation = new Location(endX-1, endY-1);
		
		return gameBoard.move(currentPlayer.getPlayer(), fromLocation, toLocation);
	}
	
	private boolean isKingAlive(){
	    return gameBoard.isKingsAlive();
	}

	@Override
	public void run() {
	    gameStart();
	    printBoard();
	    updateTurn();
	    while(isKingAlive()){
		getMove();
		printBoard();
		updateTurn();
	    }
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
