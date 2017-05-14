package com.rsmith.models.pieces;

import com.rsmith.models.board.Color;
import com.rsmith.models.game.GamePiece;
import com.rsmith.models.game.PieceType;

public class Bishop extends GamePiece {
    public Bishop(Color color) {
	super(color);
    }
    
    @Override
    public PieceType getType(){
	return PieceType.BISHOP;
    }
    
    @Override
    public String getSymbol() {
	String symbol = "B";

	if (getColor() == Color.BLACK) {
	    symbol = "b";
	}

	return symbol;
    }
}
