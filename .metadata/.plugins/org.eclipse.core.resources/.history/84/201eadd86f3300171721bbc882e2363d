package com.rsmith.models;
import java.util.ArrayList;

public class Pawn implements GamePiece{
	private PlayerColor color;
	private String currentPosition;
	private static ArrayList<String> validMoves;
	private boolean isFirstMove;
	
	public Pawn(PlayerColor color){
		this.color = color;
		this.isFirstMove = true;
	}

	public PlayerColor getColor(){
		return color;
	}
	
	public ArrayList<String> getValidMoves(){
		return validMoves;
	}
	
	public String getCurrentPosition(){
		return currentPosition;
	}
	
	public boolean getIsFirstMove(){
		return isFirstMove;
	}
	
	public void setIsFirstMove(boolean isFirstMove){
		this.isFirstMove = isFirstMove;
	}
	
	public ArrayList<String> findValidMoves(String currentPosition, int moveNumber){
		char xCoord = currentPosition.charAt(0);
		int yCoord = Integer.valueOf(currentPosition.substring(1));
		ArrayList<String> validMoves = new ArrayList<String>();
		if (moveNumber == 1){
			validMoves.add(String.valueOf(xCoord)+String.valueOf(yCoord+2));
		}
		validMoves.add(String.valueOf(xCoord)+String.valueOf(yCoord+1));
		return validMoves;
	}

	@Override
	public PieceType getType() {
	    return PieceType.PAWN;
	}

	@Override
	public String getChar() {
	    String symbol = "P";
	    
	    if(color == PlayerColor.BLACK){
		symbol = "p";
	    }
	    
	    return symbol;
	}
}
