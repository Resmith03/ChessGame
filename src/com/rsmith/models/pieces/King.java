package com.rsmith.models.pieces;

import com.rsmith.models.board.Color;
import com.rsmith.models.game.GamePiece;
import com.rsmith.models.game.PieceType;

public class King extends GamePiece {
    public King(Color color) {
	super(color);
    }
    
    @Override
    public PieceType getType(){
	return PieceType.KING;
    }
    
    @Override
    public String getSymbol() {
	String symbol = "K";

	if (getColor() == Color.BLACK) {
	    symbol = "k";
	}

	return symbol;
    }
}
