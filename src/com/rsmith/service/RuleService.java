package com.rsmith.service;

import java.util.ArrayList;
import java.util.List;

import com.rsmith.exceptions.InvalidMoveException;
import com.rsmith.models.BoardSpace;
import com.rsmith.models.GameBoard;
import com.rsmith.models.GamePiece;
import com.rsmith.models.Location;
import com.rsmith.models.Pawn;
import com.rsmith.models.PieceType;
import com.rsmith.models.PlayerColor;

public class RuleService {
    private GameBoard board;
    
    public RuleService(GameBoard board){
	this.board = board;
    }

    public boolean validMove(Location from, Location to) throws InvalidMoveException{
	boolean valid = true;
	
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
		valid = false;
	    throw new InvalidMoveException("No game piece at the source location");
	}
	
	if(!isValidPieceMove(fromPiece, from, to)){
		if(fromPiece.getType() == PieceType.PAWN){
			valid = canPawnTake(fromPiece, toPiece, from, to);
		}
		else{
			valid = false;
			throw new InvalidMoveException("That "+fromPiece.getType().toString()+" cannot reach that space");
		}
	}
	
	if(!(fromPiece.getType() == PieceType.KNIGHT) && isBlocked(from, to)){
		valid = false;
		throw new InvalidMoveException("The path to that space is blocked.");
	}
	
	if(toPiece != null){
	    if(toPiece.getColor() == fromPiece.getColor()){
	    	valid = false;
	    	throw new InvalidMoveException("Cannot capture same color game peice");
	    }
	}
	
	return valid;
    }
    
    public boolean canPawnTake(GamePiece fromPiece, GamePiece toPiece, Location from, Location to) throws InvalidMoveException{
    	boolean take = false;
    	if((from.getX()+1 == to.getX()|| from.getX()-1 == to.getX()-1) && (from.getY()+1 == to.getY() || from.getY()-1 == to.getY())){
        	if(toPiece.getColor() != fromPiece.getColor()){
        		take = true;
        	}
    	}
    	if(!take){
    		throw new InvalidMoveException("Pawn cannot take that.");
    	}
    	return take;
    }
    
    public boolean isValidPieceMove(GamePiece fromPiece, Location from, Location to){
    	boolean valid = false;
    	List<Location> validMoves;
    	switch(fromPiece.getType()){
    	case PAWN: validMoves = ValidMoveService.getPawnMoves(fromPiece, from);
    	break;
    	case ROOK: validMoves = ValidMoveService.getRookMoves(from);
    	break;
    	case KNIGHT: validMoves = ValidMoveService.getKnightMoves(from);
    	break;
    	case BISHOP: validMoves = ValidMoveService.getBishopMoves(from);
    	break;
    	case QUEEN: validMoves = ValidMoveService.getQueenMoves(from);
    	break;
    	case KING: validMoves = ValidMoveService.getKingMoves(from);
    	break;
    	default: validMoves = new ArrayList<Location>();
    	}
    	for(Location l : validMoves){
    		if(l.getX() == to.getX() && l.getY() == to.getY()){
    			valid = true;
    		}
    	}
    	return valid;
    }
    
    public boolean isBlocked(Location start, Location end){
    	boolean blocked = false;
    	int[] startCoords = start.toArray();
    	int[] endCoords = end.toArray();
    	
    	if(startCoords[0] < endCoords[0] && startCoords[1] == endCoords[1]){
    		blocked = checkMoveRight(startCoords, endCoords);
    	}
    	else if(startCoords[0] > endCoords[0] && startCoords[1] == endCoords[1]){
    		blocked = checkMoveLeft(startCoords, endCoords);
    	}
    	else if(startCoords[0] == endCoords[0] && startCoords[1]>endCoords[1]){
    		blocked = checkMoveUp(startCoords, endCoords);
    	}
    	else if(startCoords[0] == endCoords[0] && startCoords[1]<endCoords[1]){
    		blocked = checkMoveDown(startCoords, endCoords);
    	}
    	else if(startCoords[0]<endCoords[0] && startCoords[1]>endCoords[1]){
    		blocked = checkMoveUpRight(startCoords, endCoords);
    	}
    	else if(startCoords[0]>endCoords[0] && startCoords[1]>endCoords[1]){
    		blocked = checkMoveUpLeft(startCoords, endCoords);
    	}
    	else if(startCoords[0]<endCoords[0] && startCoords[1]<endCoords[1]){
    		blocked = checkMoveDownRight(startCoords, endCoords);
    	}
    	else if(startCoords[0]>endCoords[0] && startCoords[1]<endCoords[1]){
    		blocked = checkMoveDownLeft(startCoords, endCoords);
    	}
    	
    	return blocked;
    }
    
    public boolean checkMoveRight(int[] start, int[] end){
    	boolean blocked = false;
    	while(start[0]+1 < end[0]){
    		Location space = new Location(start[0]+1, start[1]);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		start[0]++;
    	}
    	return blocked;
    }
    
    public boolean checkMoveLeft(int[] start, int[] end){
    	boolean blocked = false;
    	while(start[0]-1 > end[0]){
    		Location space = new Location(start[0]+1, start[1]);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		start[0]--;
    	}
    	return blocked;
    }
    
    public boolean checkMoveUp(int[] start, int[] end){
    	boolean blocked = false;
    	while(start[1]-1 > end[1]){
    		Location space = new Location(start[0], start[1]-1);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		start[1]--;
    	}
    	return blocked;
    }
    
    public boolean checkMoveDown(int[] start, int[] end){
    	boolean blocked = false;
    	while(start[1]+1 < end[1]){
    		Location space = new Location(start[0], start[1]+1);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		start[1]++;
    	}
    	return blocked;
    }
    
    public boolean checkMoveUpRight(int[] start, int[] end){
    	boolean blocked = false;
    	while(start[1]-1 > end[1]){
    		Location space = new Location(start[0]+1, start[1]-1);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		start[0]++;
    		start[1]--;
    	}
    	return blocked;
    }
    
    public boolean checkMoveUpLeft(int[] start, int[] end){
    	boolean blocked = false;
    	while(start[1]-1 > end[1]){
    		Location space = new Location(start[0]-1, start[1]-1);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		start[0]--;
    		start[1]--;
    	}
    	return blocked;
    }
    
    public boolean checkMoveDownRight(int[] start, int[] end){
    	boolean blocked = false;
    	while(start[1]+1 < end[1]){
    		Location space = new Location(start[0]+1, start[1]+1);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		start[0]++;
    		start[1]++;
    	}
    	return blocked;
    }
    
    public boolean checkMoveDownLeft(int[] start, int[] end){
    	boolean blocked = false;
    	while(start[1]+1 < end[1]){
    		Location space = new Location(start[0]-1, start[1]+1);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		start[0]--;
    		start[1]++;
    	}
    	return blocked;
    }
}
