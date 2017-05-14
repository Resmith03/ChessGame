package com.rsmith.models.pieces;

import com.rsmith.models.board.Color;
import com.rsmith.models.game.GamePiece;
import com.rsmith.models.game.PieceType;

public class Queen extends GamePiece {
    public Queen(Color color) {
	super(color);
    }

    @Override
    public PieceType getType() {
	return PieceType.QUEEN;
    }

    @Override
    public String getSymbol() {
	String symbol = "Q";

	if (getColor() == Color.BLACK) {
	    symbol = "q";
	}

	return symbol;
    }
}
