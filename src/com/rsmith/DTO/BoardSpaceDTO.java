package com.rsmith.DTO;

public class BoardSpaceDTO {
    private GamePieceDTO gamePiece;
    private int x;
    private int y;
    
    public BoardSpaceDTO(){
	
    }
    public BoardSpaceDTO(int x, int y){
	this.x = x;
	this.y = y;
    }

    public GamePieceDTO getGamePiece() {
        return gamePiece;
    }

    public void setGamePiece(GamePieceDTO gamePiece) {
        this.gamePiece = gamePiece;
    }
    public void setX(int x){
	this.x=x;
    }
    public void setY(int y){
	this.y = y;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }    
}
