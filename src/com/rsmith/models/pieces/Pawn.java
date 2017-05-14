package com.rsmith.models.pieces;

import com.rsmith.models.board.Color;
import com.rsmith.models.game.GamePiece;
import com.rsmith.models.game.PieceType;

public class Pawn extends GamePiece {
    public Pawn(Color color) {
	super(color);
    }
    
    @Override
    public PieceType getType(){
	return PieceType.PAWN;
    }
    
    @Override
    public String getSymbol() {
	String symbol = "P";

	if (getColor() == Color.BLACK) {
	    symbol = "p";
	}

	return symbol;
    }
}
