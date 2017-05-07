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
