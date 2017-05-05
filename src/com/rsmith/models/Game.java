package com.rsmith.models;
import java.util.HashMap;
import java.util.Scanner;

public class Game {
	
	private static String[][] gameBoard;
	public static Player blackPlayer;
	public static Player whitePlayer;
	
	public static void main(String args[]){
		gameStart();
		printBoard();
		while(isKingAlive()){
		    getMove();
		    printBoard();
		}
		
	}
	
	private static void gameStart(){	
		System.out.println("Welcome to Chess");
		String[][] initialBoardState = {{"r", "n", "b", "q", "k", "b", "n", "r"},
				   						{"p", "p", "p", "p", "p", "p", "p", "p"},
				   						{"-", "-", "-", "-", "-", "-", "-", "-"},
				   						{"-", "-", "-", "-", "-", "-", "-", "-"},
				   						{"-", "-", "-", "-", "-", "-", "-", "-"},
				   						{"-", "-", "-", "-", "-", "-", "-", "-"},
				   						{"P", "P", "P", "P", "P", "P", "P", "P"},
				   						{"R", "N", "B", "Q", "K", "B", "N", "R"}
				   						};
		gameBoard = initialBoardState;
	}
	
	private static void printBoard(){
		int rowCoord = 1;
		char columnCoord = 'A';
		for(String[] row : gameBoard){
			System.out.print(String.valueOf(rowCoord)+"...");
			for(String space : row){
				System.out.print(space + " ");
			}
			rowCoord++;
			System.out.println("");
		}
		System.out.print("    ");
		for(int i = 0; i < gameBoard[0].length; i++){
			System.out.print(columnCoord+" ");
			columnCoord++;
		}
		System.out.println("");
	}
	
	private static boolean checkInput(String input){
		if(input.length() != 2){
			return false;
		}
		String firstChar = input.substring(0, 1);
		String secondChar = input.substring(1, input.length());
		
		if(!firstChar.matches("^[A-Z]+$") || !secondChar.matches("^[0-9]+$")){
			return false;
		}
		char xCoord = input.charAt(0);
		int yCoord = Integer.valueOf(input.substring(1, input.length()));
		if(xCoord < 'A' || xCoord > 'H' || yCoord < 1 || yCoord > 8){
			return false;
		}
		else{
			return true;
		}
	}
	
	private static void getMove(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Please input piece location:");
		String start = sc.next();
		String end = "";
		if(start != " "){
			if(checkInput(start)){
				System.out.println("Move piece to where?");
				end = sc.next();
			}
			else{
				System.out.print("Invalid input. ");
				getMove();
			}
		}
		else {
			System.out.println("There is no piece there.");
			getMove();
		}
		move(start, end);
	}
	
	private static void move(String from, String to){
		HashMap<Character, Integer> xCoords = new HashMap<Character, Integer>();
		xCoords.put('A', 1);
		xCoords.put('B', 2);
		xCoords.put('C', 3);
		xCoords.put('D', 4);
		xCoords.put('E', 5);
		xCoords.put('F', 6);
		xCoords.put('G', 7);
		xCoords.put('H', 8);
		
		int startX = xCoords.get(from.charAt(0));
		int startY = Integer.valueOf(from.substring(1, from.length()));
		String piece = gameBoard[startY-1][startX-1];
		gameBoard[startY-1][startX-1] = " ";
		
		int endX = xCoords.get(to.charAt(0));
		int endY = Integer.valueOf(to.substring(1, to.length()));
		gameBoard[endY-1][endX-1] = piece;
	}
	
	private static boolean isKingAlive(){
		boolean blackKing = false;
		boolean whiteKing = false;
		for(String[] row: gameBoard){
			for(String piece: row){
				if(piece.equals("k")){
					blackKing = true;
				}
				if(piece.equals("K")){
					whiteKing = true;
				}
			}
		}
		return blackKing && whiteKing;
	}
}
