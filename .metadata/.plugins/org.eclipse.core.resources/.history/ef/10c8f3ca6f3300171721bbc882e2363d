package com.rsmith.models;
import java.util.ArrayList;

public class Rook implements GamePiece{
	private PlayerColor color;

	
	public Rook(PlayerColor color){
		this.color = color;
	}
	
	public PlayerColor getColor(){
		return color;
	}
	
	
	
	public ArrayList<String> findValidMoves(String currentPosition, int moveNumber){
		char xCoord = currentPosition.charAt(0);
		int yCoord = Integer.valueOf(currentPosition.substring(1));
		ArrayList<String> validMoves = new ArrayList<String>();
		
		//Up
		if(yCoord != 8){
			for(int i = yCoord; i <= 8; i++){
				validMoves.add(String.valueOf(xCoord)+String.valueOf(i));
			}
		}
		
		//Down
		if(yCoord != 1){
			for(int i = yCoord; i >= 1; i--){
				validMoves.add(String.valueOf(xCoord)+String.valueOf(i));
			}
		}
		
		//Left
		if(xCoord != 'A'){
			for(char c = xCoord; c >= 'A'; c--){
				validMoves.add(String.valueOf(c)+String.valueOf(yCoord));
			}
		}
		
		//Right
		if(xCoord != 'H'){
			for(char c = xCoord; c <= 'H'; c++){
				validMoves.add(String.valueOf(c)+String.valueOf(yCoord));
			}
		}		
		
		while(validMoves.contains(currentPosition)){
			validMoves.remove(currentPosition);
		}
		return validMoves;
	}

	@Override
	public PieceType getType() {
	    return PieceType.ROOK;
	}

	@Override
	public String getChar() {
	    String symbol = "R";
	    
	    if(color == PlayerColor.BLACK){
		symbol = "r";
	    }
	    
	    return symbol;
	}
}
