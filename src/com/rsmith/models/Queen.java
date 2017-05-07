package com.rsmith.models;
import java.util.ArrayList;

public class Queen implements GamePiece{
	private PlayerColor color;
	private String currentPosition;
	private static ArrayList<String> validMoves;
	
	public Queen(PlayerColor color){
		this.color = color;
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

	@Override
	public PieceType getType() {
	    return PieceType.QUEEN;
	}

	@Override
	public String getChar() {
	    String symbol = "Q";
	    
	    if(color == PlayerColor.BLACK){
		symbol = "q";
	    }
	    
	    return symbol;
	}
}
