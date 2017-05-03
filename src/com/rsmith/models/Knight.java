package com.rsmith.models;
import java.util.ArrayList;
import java.util.Scanner;

public class Knight implements GamePiece{
	private String color;
	private String currentPosition;
	private static ArrayList<String> validMoves;
	private int moveNumber;
	
	public Knight(String color, String position){
		moveNumber = 1;
		this.color = color;
		currentPosition = position;
	}
	
	public String getColor(){
		return color;
	}
	
	public ArrayList<String> getValidMoves(){
		return validMoves;
	}
	
	public String getCurrentPosition(){
		return currentPosition;
	}
	
	public ArrayList<String> findValidMoves(String currentPosition, int moveNumber){
		char xCoord = currentPosition.charAt(0);
		int yCoord = Integer.valueOf(currentPosition.substring(1));
		ArrayList<String> validMoves = new ArrayList<String>();
		
		if(xCoord+2 <='H' && yCoord+1 <= 8){
			validMoves.add(String.valueOf((char)(xCoord+2))+String.valueOf(yCoord+1));
		}
		if(xCoord+2 <='H' && yCoord-1 >= 1){
			validMoves.add(String.valueOf((char)(xCoord+2))+String.valueOf(yCoord-1));
		}
		if(xCoord-2 >='A' && yCoord+1 <= 8){
			validMoves.add(String.valueOf((char)(xCoord-2))+String.valueOf(yCoord+1));
		}
		if(xCoord-2 >='A' && yCoord-1 >= 1){
			validMoves.add(String.valueOf((char)(xCoord-2))+String.valueOf(yCoord-1));
		}
		if(xCoord+1 <='H' && yCoord+2 <= 8){
			validMoves.add(String.valueOf((char)(xCoord+1))+String.valueOf(yCoord+2));
		}
		if(xCoord+1 <='H' && yCoord-2 >= 1){
			validMoves.add(String.valueOf((char)(xCoord+1))+String.valueOf(yCoord-2));
		}
		if(xCoord-1 >='A' && yCoord+2 <= 8){
			validMoves.add(String.valueOf((char)(xCoord-1))+String.valueOf(yCoord+2));
		}
		if(xCoord-1 >='A' && yCoord-2 >= 1){
			validMoves.add(String.valueOf((char)(xCoord-1))+String.valueOf(yCoord-2));
		}

		return validMoves;
	}
}
