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
