package com.rsmith.models;

import com.rsmith.DTO.BoardSpaceDTO;

public class BoardSpace {
    private GamePiece gamePiece;
    private int x;
    private int y;
    
    public BoardSpace(int x, int y){
	this.x = x;
	this.y = y;
    }

    public GamePiece getGamePiece() {
        return gamePiece;
    }

    public void setGamePiece(GamePiece gamePiece) {
        this.gamePiece = gamePiece;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }    
    
    public BoardSpaceDTO toDTO(){
	BoardSpaceDTO dto = new BoardSpaceDTO();
	if(gamePiece != null){
	    dto.setGamePiece(gamePiece.toDTO());
	}
	
	dto.setX(x);
	dto.setY(y);
	
	return dto;
    }
}
