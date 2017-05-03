package com.rsmith.models;
import java.util.ArrayList;

public interface GamePiece {
	
	public String getColor();
	
	public String getCurrentPosition();
	
	public ArrayList<String> getValidMoves();
	
	public ArrayList<String> findValidMoves(String currentPosition, int moveNumber);
}
