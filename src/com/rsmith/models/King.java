package com.rsmith.models;
import java.util.ArrayList;

public class King implements GamePiece{
	private PlayerColor color;
	
	public King(PlayerColor color){
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
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf(x)+String.valueOf(y+1));
		}
		//Down
		if(yCoord != 1){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf(x)+String.valueOf(y-1));
		}
		//Left
		if(xCoord != 'A'){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf((char)(x-1))+String.valueOf(y));
		}
		//Right
		if(xCoord != 'H'){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf((char)(x+1))+String.valueOf(y));
		}
		//Up-Right
		if(yCoord != 8 && xCoord != 'H'){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf((char)(x+1))+String.valueOf(y+1));
		}
		//Up-Left
		if(yCoord != 8 && xCoord != 'A'){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf((char)(x-1))+String.valueOf(y++));
		}
		//Down-Right
		if(yCoord != 1 && xCoord != 'H'){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf((char)(x++))+String.valueOf(y--));
		}
		//Down-Left
		if(yCoord != 1 && xCoord != 'A'){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf((char)(x-1))+String.valueOf(y--));
		}
		
		return validMoves;
	}

	@Override
	public PieceType getType() {
	    return PieceType.KING;
	}

	@Override
	public String getChar() {
	    String symbol = "K";
	    
	    if(color == PlayerColor.BLACK){
		symbol = "k";
	    }
	    
	    return symbol;
	}
}
