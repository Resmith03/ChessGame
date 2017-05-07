package com.rsmith.models;
import java.util.ArrayList;

public class Bishop implements GamePiece{
	private PlayerColor color;
	private static ArrayList<String> validMoves;
	
	public Bishop(PlayerColor color){
		this.color = color;
	}
	
	public ArrayList<String> getValidMoves(){
		return validMoves;
	}
	
	
	public ArrayList<String> findValidMoves(String currentPosition, int moveNumber){
		char xCoord = currentPosition.charAt(0);
		int yCoord = Integer.valueOf(currentPosition.substring(1));
		ArrayList<String> validMoves = new ArrayList<String>();
		
		//Up-Right
		if(xCoord != 'H' && yCoord!= 8){
			char c = xCoord;
			int i = yCoord;
			while(c < 'H' && i < 8){
				c++;
				i++;
				validMoves.add(String.valueOf(c)+String.valueOf(i));
			}
		}
		
		//Up-Left
		if(xCoord != 'A' && yCoord!= 8){
			char c = xCoord;
			int i = yCoord;
			while(c > 'A' && i < 8){
				c--;
				i++;
				validMoves.add(String.valueOf(c)+String.valueOf(i));
			}
		}
		
		//Down-Right
		if(xCoord != 'H' && yCoord!= 1){
			char c = xCoord;
			int i = yCoord;
			while(c < 'H' && i > 1){
				c++;
				i--;
				validMoves.add(String.valueOf(c)+String.valueOf(i));
			}
		}
		
		//Down-Left
		if(xCoord != 'A' && yCoord!= 1){
			char c = xCoord;
			int i = yCoord;
			while(c > 'A' && i > 1){
				c--;
				i--;
				validMoves.add(String.valueOf(c)+String.valueOf(i));
			}
		}

		return validMoves;
	}

	@Override
	public PieceType getType() {
	    return PieceType.BISHOP;
	}

	@Override
	public PlayerColor getColor() {
	    return color;
	}

	@Override
	public String getChar() {
	    String symbol = "B";
	    
	    if(color == PlayerColor.BLACK){
		symbol = "b";
	    }
	    
	    return symbol;
	}
}
