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
    
    public boolean checkPath(Location start, Location end){
    	boolean blocked = false;
    	int[] startCoords = {start.getX(), start.getY()};
    	int[] endCoords = {end.getX(), end.getY()};
    	
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
    	while(start[1]+1 > end[1]){
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
