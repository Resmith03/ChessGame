package com.rsmith.service;

import java.util.ArrayList;
import java.util.List;

import com.rsmith.models.GameBoard;
import com.rsmith.models.GamePiece;
import com.rsmith.models.Location;

public class RuleService {
    private GameBoard board;
    private ValidMoveService validMoveService;
    
    public RuleService(GameBoard board){
	this.board = board;
	validMoveService = new ValidMoveService();
    }
    
    public boolean validMove(Location from, Location to){
	return true;
//	boolean valid = false;
//	
//	BoardSpace fromSpace = board.getSpaceAtLocation(from);
//	BoardSpace toSpace = board.getSpaceAtLocation(to);
//	
//	GamePiece fromPiece = null;
//	if(fromSpace != null){
//	    fromPiece = fromSpace.getGamePiece();
//	}
//	
//	GamePiece toPiece = null;
//	if(toSpace != null){
//	    toPiece = toSpace.getGamePiece();
//	}
//	
//	if(fromPiece == null){
//	    return false;
//	}
//	
//	if(!isValidPieceMove(fromPiece, from, to)){
//		if(fromPiece.getType() == PieceType.PAWN && toPiece != null){
//			valid = canPawnTake(fromPiece, toPiece, from, to);
//		}
//		else{
//			valid = false;
//		}
//	}
//	
//	if(!(fromPiece.getType() == PieceType.KNIGHT) && isBlocked(from, to)){
//		valid = false;
//	}
//	
//	if(toPiece != null){
//	    if(toPiece.getColor() == fromPiece.getColor()){
//		valid = false;
//	    }
//	}
//	System.out.println("valid move = " + valid);
//	return valid;
    }
    
    private boolean canPawnTake(GamePiece fromPiece, GamePiece toPiece, Location from, Location to){
    	boolean take = false;
    	if((from.getX()+1 == to.getX()|| from.getX()-1 == to.getX()-1) && (from.getY()+1 == to.getY() || from.getY()-1 == to.getY())){
        	if(toPiece.getColor() != fromPiece.getColor()){
        		take = true;
        	}
    	}
 
    	return take;
    }
    
    private boolean isValidPieceMove(GamePiece fromPiece, Location from, Location to){
    	boolean valid = false;
    	List<Location> validMoves;
    	switch(fromPiece.getType()){
    	case PAWN: validMoves = validMoveService.getPawnMoves(fromPiece, from);
    	break;
    	case ROOK: validMoves = validMoveService.getRookMoves(from);
    	break;
    	case KNIGHT: validMoves = validMoveService.getKnightMoves(from);
    	break;
    	case BISHOP: validMoves = validMoveService.getBishopMoves(from);
    	break;
    	case QUEEN: validMoves = validMoveService.getQueenMoves(from);
    	break;
    	case KING: validMoves = validMoveService.getKingMoves(from);
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
    
    private boolean isBlocked(Location start, Location end){
    	boolean blocked = false;
    	
    	if(start.getX() < end.getX() && start.getY() == end.getY()){
    		blocked = checkMoveRight(start, end);
    	}
    	else if(start.getX() > end.getX() && start.getY() == end.getY()){
    		blocked = checkMoveLeft(start, end);
    	}
    	else if(start.getX() == end.getX() && start.getY()>end.getY()){
    		blocked = checkMoveUp(start, end);
    	}
    	else if(start.getX() == end.getX() && start.getY()<end.getY()){
    		blocked = checkMoveDown(start, end);
    	}
    	else if(start.getX()<end.getX() && start.getY()>end.getY()){
    		blocked = checkMoveUpRight(start, end);
    	}
    	else if(start.getX()>end.getX() && start.getY()>end.getY()){
    		blocked = checkMoveUpLeft(start, end);
    	}
    	else if(start.getX()<end.getX() && start.getY()<end.getY()){
    		blocked = checkMoveDownRight(start, end);
    	}
    	else if(start.getX()>end.getX() && start.getY()<end.getY()){
    		blocked = checkMoveDownLeft(start, end);
    	}
    	
    	return blocked;
    }
    
    private boolean checkMoveRight(Location start, Location end){
    	boolean blocked = false;
    	int x = start.getX();
    	int y = start.getY();
    	while(x+1 < end.getX()){
    		Location space = new Location(x+1, y);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		x++;
    	}
    	return blocked;
    }
    
    private boolean checkMoveLeft(Location start, Location end){
    	boolean blocked = false;
    	int x = start.getX();
    	int y = start.getY();
    	
    	while(x-1 > end.getX()){
    		Location space = new Location(x+1, y);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		x--;
    	}
    	return blocked;
    }
    
    private boolean checkMoveUp(Location start, Location end){
    	boolean blocked = false;
    	
    	int x = start.getX();
    	int y = start.getY();
    	while(y-1 > end.getY()){
    		Location space = new Location(x, y-1);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		y--;
    	}
    	return blocked;
    }
    
    private boolean checkMoveDown(Location start, Location end){
    	boolean blocked = false;
    	
    	int x = start.getX();
    	int y = start.getY();
    	while(y+1 < end.getY()){
    		Location space = new Location(x, y+1);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		y++;
    	}
    	return blocked;
    }
    
    private boolean checkMoveUpRight(Location start, Location end){
    	boolean blocked = false;
    	
    	int x = start.getX();
    	int y = start.getY();
    	while(y-1 > end.getY()){
    		Location space = new Location(x+1, y-1);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		x++;
    		y--;
    	}
    	return blocked;
    }
    
    private boolean checkMoveUpLeft(Location start, Location end){
    	boolean blocked = false;
    	
    	int x = start.getX();
    	int y = start.getY();
    	while(y-1 > end.getY()){
    		Location space = new Location(x-1, y-1);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		x--;
    		y--;
    	}
    	return blocked;
    }
    
    private boolean checkMoveDownRight(Location start, Location end){
    	boolean blocked = false;
    	int x = start.getX();
    	int y = start.getY();
    	
    	while(y+1 < end.getY()){
    		Location space = new Location(x+1, y+1);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		x++;
    		y++;
    	}
    	return blocked;
    }
    
    private boolean checkMoveDownLeft(Location start, Location end){
    	boolean blocked = false;
    	
    	int x = start.getX();
    	int y = start.getY();
    	while(y+1 < end.getY()){
    		Location space = new Location(x-1, y+1);
    		if(board.getSpaceAtLocation(space).getGamePiece() != null){
    			blocked = true;
    		}
    		x--;
    		y++;
    	}
    	return blocked;
    }

    public boolean checkmate() {
	// TODO process checkmate
	return false;
    }
}
