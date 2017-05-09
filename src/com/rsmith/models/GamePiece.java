package com.rsmith.models;

import com.rsmith.DTO.GamePieceDTO;

public interface GamePiece {

	public PlayerColor getColor();
	public PieceType getType();
	public String getChar();
	public GamePieceDTO toDTO();
}
