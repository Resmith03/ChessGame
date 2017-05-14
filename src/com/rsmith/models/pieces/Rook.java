package com.rsmith.models.pieces;

import com.rsmith.models.board.Color;
import com.rsmith.models.game.GamePiece;
import com.rsmith.models.game.PieceType;

public class Rook extends GamePiece {
    public Rook(Color color) {
	super(color);
    }
    
    @Override
    public PieceType getType(){
	return PieceType.ROOK;
    }

    @Override
    public String getSymbol() {
	String symbol = "R";

	if (getColor() == Color.BLACK) {
	    symbol = "r";
	}

	return symbol;
    }
}
