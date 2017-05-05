package com.rsmith.models;
import java.util.ArrayList;

public class Knight implements GamePiece{
	private PlayerColor color;
	
	public Knight(PlayerColor color) {
	    this.color = color;
	}

	public PlayerColor getColor(){
		return color;
	}
	
	
	public ArrayList<String> findValidMoves(String currentPosition, int moveNumber){
		char xCoord = currentPosition.charAt(0);
		int yCoord = Integer.valueOf(currentPosition.substring(1));
		ArrayList<String> validMoves = new ArrayList<String>();
		
		if(xCoord+2 <='H' && yCoord+1 <= 8){
			validMoves.add(String.valueOf((char)(xCoord+2))+String.valueOf(yCoord+1));
		}
		if(xCoord+2 <='H' && yCoord-1 >= 1){
			validMoves.add(String.valueOf((char)(xCoord+2))+String.valueOf(yCoord-1));
		}
		if(xCoord-2 >='A' && yCoord+1 <= 8){
			validMoves.add(String.valueOf((char)(xCoord-2))+String.valueOf(yCoord+1));
		}
		if(xCoord-2 >='A' && yCoord-1 >= 1){
			validMoves.add(String.valueOf((char)(xCoord-2))+String.valueOf(yCoord-1));
		}
		if(xCoord+1 <='H' && yCoord+2 <= 8){
			validMoves.add(String.valueOf((char)(xCoord+1))+String.valueOf(yCoord+2));
		}
		if(xCoord+1 <='H' && yCoord-2 >= 1){
			validMoves.add(String.valueOf((char)(xCoord+1))+String.valueOf(yCoord-2));
		}
		if(xCoord-1 >='A' && yCoord+2 <= 8){
			validMoves.add(String.valueOf((char)(xCoord-1))+String.valueOf(yCoord+2));
		}
		if(xCoord-1 >='A' && yCoord-2 >= 1){
			validMoves.add(String.valueOf((char)(xCoord-1))+String.valueOf(yCoord-2));
		}

		return validMoves;
	}

	@Override
	public PieceType getType() {
	    return PieceType.KNIGHT;
	}

	@Override
	public String getChar() {
	    String symbol = "N";
	    
	    if(color == PlayerColor.BLACK){
		symbol = "n";
	    }
	    
	    return symbol;
	}
}
