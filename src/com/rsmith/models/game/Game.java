package com.rsmith.models.game;

import com.rsmith.models.board.Color;
import com.rsmith.models.board.Move;
import com.rsmith.services.BoardService;

/**
 * 
 * Controls the main operations of the game and coordinates with the players and
 * board
 *
 */
public class Game {

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private BoardService board;

    public Game(Player player1, Player player2) {
	this.player1 = player1;
	this.player2 = player2;
	this.currentPlayer = player1;
	board = new BoardService();
    }

    public BoardService getGameBoard() {
	return board;
    }

    public Player getPlayer1() {
	return player1;
    }

    public Player getPlayer2() {
	return player2;
    }

    public Player getWhitePlayer() {
	return player1.getColor().equals(Color.WHITE) ? player1 : player2;
    }

    public boolean move(Move move) {
	boolean moved = board.move(move);

	if (moved) {
	    updateCurrentPlayer();
	}

	return moved;
    }

    public Player getCurrentPlayer() {
	return currentPlayer;
    }

    private void updateCurrentPlayer() {
	if (currentPlayer.equals(player1)) {
	    currentPlayer = player2;
	} else {
	    currentPlayer = player1;
	}
    }
}
