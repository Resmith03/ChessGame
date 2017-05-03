package com.rsmith.models;
import java.util.ArrayList;
import java.util.Scanner;

public class Queen implements GamePiece{
	private String color;
	private String currentPosition;
	private static ArrayList<String> validMoves;
	private int moveNumber;
	
	public Queen(String color, String position){
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
		
		//Up-Right
		if(xCoord != 'H' && yCoord!= 8){
			char c = xCoord;
			int i = yCoord;
			while(c < 'H' && i < 8){
				c++;
				i++;
				validMoves.add(String.valueOf(c)+String.valueOf(i));
			}
		}
		
		//Up-Left
		if(xCoord != 'A' && yCoord!= 8){
			char c = xCoord;
			int i = yCoord;
			while(c > 'A' && i < 8){
				c--;
				i++;
				validMoves.add(String.valueOf(c)+String.valueOf(i));
			}
		}
		
		//Down-Right
		if(xCoord != 'H' && yCoord!= 1){
			char c = xCoord;
			int i = yCoord;
			while(c < 'H' && i > 1){
				c++;
				i--;
				validMoves.add(String.valueOf(c)+String.valueOf(i));
			}
		}
		
		//Down-Left
		if(xCoord != 'A' && yCoord!= 1){
			char c = xCoord;
			int i = yCoord;
			while(c > 'A' && i > 1){
				c--;
				i--;
				validMoves.add(String.valueOf(c)+String.valueOf(i));
			}
		}
		
		//Up
		if(yCoord != 8){
			for(int i = yCoord; i <= 8; i++){
				validMoves.add(String.valueOf(xCoord)+String.valueOf(i));
			}
		}
		
		//Down
		if(yCoord != 1){
			for(int i = yCoord; i >= 1; i--){
				validMoves.add(String.valueOf(xCoord)+String.valueOf(i));
			}
		}
		
		//Left
		if(xCoord != 'A'){
			for(char c = xCoord; c >= 'A'; c--){
				validMoves.add(String.valueOf(c)+String.valueOf(yCoord));
			}
		}
		
		//Right
		if(xCoord != 'H'){
			for(char c = xCoord; c <= 'H'; c++){
				validMoves.add(String.valueOf(c)+String.valueOf(yCoord));
			}
		}		
		
		while(validMoves.contains(currentPosition)){
			validMoves.remove(currentPosition);
		}
		return validMoves;
	}
}
