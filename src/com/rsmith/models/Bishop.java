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
