package com.rsmith.models;

public enum PieceType {
    KING("KING"), 
    QUEEN("QUEEN"),
    KNIGHT("KNIGHT"), 
    BISHOP("BISHOP"),
    PAWN("PAWN"),
    ROOK("ROCK");
    
    private final String name;
    private PieceType(String name){
	this.name=name;
    }
    
    @Override
    public String toString(){
	return name;
    }
}
