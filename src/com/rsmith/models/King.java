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
