package com.rsmith.models;
import com.rsmith.DTO.GamePieceDTO;

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

	@Override
	public GamePieceDTO toDTO() {
	    GamePieceDTO dto = new GamePieceDTO();
	    dto.setPieceType(PieceType.KNIGHT.toString());
	    dto.setPlayerColor(color.toString());
	    dto.setSymbol(getChar());
	    
	    return dto;
	}
}
