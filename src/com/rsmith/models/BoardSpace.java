package com.rsmith.models;

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
}
