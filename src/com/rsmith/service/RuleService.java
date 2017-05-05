package com.rsmith.service;

import com.rsmith.exceptions.InvalidMoveException;
import com.rsmith.models.BoardSpace;
import com.rsmith.models.GameBoard;
import com.rsmith.models.GamePiece;
import com.rsmith.models.Location;

public class RuleService {
    private GameBoard board;
    
    public RuleService(GameBoard board){
	this.board = board;
    }
    
    public boolean validMove(Location from, Location to) throws InvalidMoveException{
	boolean valid = false;
	
	BoardSpace fromSpace = board.getSpaceAtLocation(from);
	BoardSpace toSpace = board.getSpaceAtLocation(to);
	
	GamePiece fromPiece = null;
	if(fromSpace != null){
	    fromPiece = fromSpace.getGamePiece();
	}
	
	GamePiece toPiece = null;
	if(toSpace != null){
	    toPiece = toSpace.getGamePiece();
	}
	
	if(fromPiece == null){
	    throw new InvalidMoveException("No game piece at the source location");
	}
	
	if(toPiece != null){
	    if(toPiece.getColor() == fromPiece.getColor()){
		throw new InvalidMoveException("Cannot capture same color game peice");
	    }
	}
	
	return valid;
    }
}
