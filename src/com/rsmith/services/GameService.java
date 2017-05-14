package com.rsmith.services;

import java.util.ArrayList;
import java.util.List;

import com.rsmith.models.board.Color;
import com.rsmith.models.game.Game;
import com.rsmith.models.game.Player;

public class GameService {
    private static final GameService instance = new GameService();
    
    private List<Game> games;
    
    private GameService(){
	games = new ArrayList<Game>();
    }
    
    public static GameService getInstance(){
	return instance;
    }
    
    public Game getGameByPlayerUsername(String username){
	Game game = null;
	
	for(Game curGame:games){
	    if(curGame.getPlayer1().getUsername().equals(username) || curGame.getPlayer2().getUsername().equals(username)){
		game = curGame;
		break;
	    }
	}
	
	return game;
    }
    
    public Game createGame(SocketService player1, SocketService player2){
	Player gamePlayer1 = new Player(player1.getIpAddress(), Color.BLACK);
	Player gamePlayer2 = new Player(player2.getIpAddress(), Color.WHITE);
	//TODO: check to ensure players are not already in game
	Game game = new Game(gamePlayer1, gamePlayer2);
	games.add(game);
	
	return game;
    }
}
