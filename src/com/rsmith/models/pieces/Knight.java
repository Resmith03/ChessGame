package com.rsmith.models.pieces;

import com.rsmith.models.board.Color;
import com.rsmith.models.game.GamePiece;
import com.rsmith.models.game.PieceType;

public class Knight extends GamePiece {
    public Knight(Color color) {
	super(color);
    }
    
    @Override
    public PieceType getType(){
	return PieceType.KNIGHT;
    }
    
    @Override
    public String getSymbol() {
	String symbol = "N";

	if (getColor() == Color.BLACK) {
	    symbol = "n";
	}

	return symbol;
    }
}
