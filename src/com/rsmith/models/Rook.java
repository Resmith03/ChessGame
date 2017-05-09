package com.rsmith.models;
import com.rsmith.DTO.GamePieceDTO;

public class Rook implements GamePiece{
	private PlayerColor color;

	
	public Rook(PlayerColor color){
		this.color = color;
	}
	
	public PlayerColor getColor(){
		return color;
	}

	@Override
	public PieceType getType() {
	    return PieceType.ROOK;
	}

	@Override
	public String getChar() {
	    String symbol = "R";
	    
	    if(color == PlayerColor.BLACK){
		symbol = "r";
	    }
	    
	    return symbol;
	}
	
	@Override
	public GamePieceDTO toDTO() {
	    GamePieceDTO dto = new GamePieceDTO();
	    dto.setPieceType(PieceType.ROOK.toString());
	    dto.setPlayerColor(color.toString());
	    dto.setSymbol(getChar());
	    
	    return dto;
	}
}
