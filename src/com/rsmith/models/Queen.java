package com.rsmith.models;
import java.util.ArrayList;

import com.rsmith.DTO.GamePieceDTO;

public class Queen implements GamePiece{
	private PlayerColor color;
	private String currentPosition;
	private static ArrayList<String> validMoves;
	
	public Queen(PlayerColor color){
		this.color = color;
	}
	
	public PlayerColor getColor(){
		return color;
	}
	
	public ArrayList<String> getValidMoves(){
		return validMoves;
	}
	
	public String getCurrentPosition(){
		return currentPosition;
	}

	@Override
	public PieceType getType() {
	    return PieceType.QUEEN;
	}

	@Override
	public String getChar() {
	    String symbol = "Q";
	    
	    if(color == PlayerColor.BLACK){
		symbol = "q";
	    }
	    
	    return symbol;
	}
	
	@Override
	public GamePieceDTO toDTO() {
	    GamePieceDTO dto = new GamePieceDTO();
	    dto.setPieceType(PieceType.QUEEN.toString());
	    dto.setPlayerColor(color.toString());
	    dto.setSymbol(getChar());
	    
	    return dto;
	}
}
