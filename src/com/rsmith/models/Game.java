package com.rsmith.models;
import java.util.HashMap;
import java.util.List;

import com.rsmith.server.Client;

public class Game implements Runnable {
	
	private String[][] gameBoard;
	public Client blackPlayer;
	public Client whitePlayer;
	public Client currentPlayer;
	
	public Game(Client blackPlayer, Client whitePlayer){
	    this.blackPlayer = blackPlayer;
	    this.whitePlayer = whitePlayer;
	    this.currentPlayer = whitePlayer;
	}
	
	private void broadcast(String message){
	    blackPlayer.sendMessage(message);
	    whitePlayer.sendMessage(message);
	}
	
	private void gameStart(){	
		
		broadcast("Welcome to Chess");
		String[][] initialBoardState = {
			{"r", "n", "b", "q", "k", "b", "n", "r"},
			{"p", "p", "p", "p", "p", "p", "p", "p"},
			{"-", "-", "-", "-", "-", "-", "-", "-"},
			{"-", "-", "-", "-", "-", "-", "-", "-"},
			{"-", "-", "-", "-", "-", "-", "-", "-"},
			{"-", "-", "-", "-", "-", "-", "-", "-"},
			{"P", "P", "P", "P", "P", "P", "P", "P"},
			{"R", "N", "B", "Q", "K", "B", "N", "R"}
		};
		
		gameBoard = initialBoardState;
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
		printRows();
		printFooter();
	}
	
	private void printLogo(){
	    String logo = "Frostburg State University:Chess";
	    broadcast(logo);
	}
	private void printFooter(){
	    String footer = "    _______________";
	    broadcast(footer);
	}
	
	private void printHeader(){
	    String header =    "    A B C D E F G H";
	    broadcast(header);
	    String underline = "    _______________";
	    broadcast(underline);
	}
	
	private void printRows(){
	    int rowCounter = 1;
	    for(String[] row : gameBoard){
		
		String line = String.valueOf(rowCounter++) + " | ";
		line += getRowAsString(row);
		line += " | ";
		broadcast(line);
	    }
	}
	
	private String getRowAsString(String[] row){
	    String line = "";
	    
	    for(String col : row){
		line += (col + " ");
	    }
	    
	    return line;
	}
	
	private boolean checkInput(String input){
		if(input.length() != 2){
			return false;
		}
		String firstChar = input.substring(0, 1);
		String secondChar = input.substring(1, input.length());
		
		if(!firstChar.matches("^[A-Z]+$") || !secondChar.matches("^[0-9]+$")){
			return false;
		}
		char xCoord = input.charAt(0);
		int yCoord = Integer.valueOf(input.substring(1, input.length()));
		if(xCoord < 'A' || xCoord > 'H' || yCoord < 1 || yCoord > 8){
			return false;
		}
		else{
			return true;
		}
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
		currentPlayer.sendMessage("Please input piece location:");
		String start = getCurrentPlayerInput();
		
		String end = "";
		if(start != " "){
			if(checkInput(start)){
				currentPlayer.sendMessage("Move piece to where?");
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
		move(start, end);
	}
	
	private void move(String from, String to){
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
		String piece = gameBoard[startY-1][startX-1];
		gameBoard[startY-1][startX-1] = "-";
		
		int endX = xCoords.get(to.charAt(0));
		int endY = Integer.valueOf(to.substring(1, to.length()));
		gameBoard[endY-1][endX-1] = piece;
	}
	
	private boolean isKingAlive(){
		boolean blackKing = false;
		boolean whiteKing = false;
		for(String[] row: gameBoard){
			for(String piece: row){
				if(piece.equals("k")){
					blackKing = true;
				}
				if(piece.equals("K")){
					whiteKing = true;
				}
			}
		}
		return blackKing && whiteKing;
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
	    
	    if(currentPlayer.equals(blackPlayer)){
		currentPlayer = whitePlayer;
		blackPlayer.sendMessage("Waiting on other player to move....");
	    }else{
		currentPlayer = blackPlayer;
		whitePlayer.sendMessage("Waiting on other player to move...");
	    }
	}
}
