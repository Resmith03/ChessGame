package com.rsmith.models;

public enum PlayerColor {
    WHITE("WHITE"),
    BLACK("BLACK");
    
    private final String color;
    
    private PlayerColor(final String color){
	this.color = color;
    }
    
    @Override
    public String toString(){
	return color;
    }
}
