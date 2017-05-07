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
