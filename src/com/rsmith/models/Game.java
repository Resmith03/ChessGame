package com.rsmith.models;

import com.rsmith.service.RuleService;

/**
 * 
 * Controls the main operations of the game and coordinates with the players and board
 *
 */
public class Game {
	
    	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private GameBoard gameBoard;
	private RuleService service;
	
	public GameBoard getGameBoard(){
	    return gameBoard;
	}
	
	public Player getPlayer1(){
	    return player1;
	}
	
	public Player getPlayer2(){
	    return player2;
	}
	
	public Player getWhitePlayer(){
	    return player1.getColor().equals(PlayerColor.WHITE) ? player1:player2;
	}
	
	public Game(Player player1, Player player2){
	    player1.setColor(PlayerColor.BLACK);
	    player2.setColor(PlayerColor.WHITE);
	    this.player1 = player1;
	    this.player2 = player2;
	    this.currentPlayer = player1;
	    gameBoard = new GameBoard(this);
	    service = new RuleService(gameBoard);
	}

	public boolean move(Location start, Location end){
	    boolean moved = false;
		
	    if(service.validMove(start, end)){
		gameBoard.move(start, end);
		updateCurrentPlayer();
		moved = true;
	    }
	    
	    if(service.checkmate()){
		//TODO: process checkmate
	    }
	    
	    return moved;
	}
	
	public Player getCurrentPlayer(){
	    return currentPlayer;
	}
	
	private void updateCurrentPlayer(){
	    if(currentPlayer.equals(player1)){
		currentPlayer = player2;
	    }else{
		currentPlayer = player1;
	    }
	}
}
