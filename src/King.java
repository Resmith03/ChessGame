import java.util.ArrayList;
import java.util.Scanner;

public class King implements GamePiece{
	private String color;
	private String currentPosition;
	private static ArrayList<String> validMoves;
	private int moveNumber;
	
	public King(String color, String position){
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
		
		//Up
		if(yCoord != 8){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf(x)+String.valueOf(y+1));
		}
		//Down
		if(yCoord != 1){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf(x)+String.valueOf(y-1));
		}
		//Left
		if(xCoord != 'A'){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf((char)(x-1))+String.valueOf(y));
		}
		//Right
		if(xCoord != 'H'){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf((char)(x+1))+String.valueOf(y));
		}
		//Up-Right
		if(yCoord != 8 && xCoord != 'H'){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf((char)(x+1))+String.valueOf(y+1));
		}
		//Up-Left
		if(yCoord != 8 && xCoord != 'A'){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf((char)(x-1))+String.valueOf(y++));
		}
		//Down-Right
		if(yCoord != 1 && xCoord != 'H'){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf((char)(x++))+String.valueOf(y--));
		}
		//Down-Left
		if(yCoord != 1 && xCoord != 'A'){
			char x = xCoord;
			int y = yCoord;
			validMoves.add(String.valueOf((char)(x-1))+String.valueOf(y--));
		}
		
		return validMoves;
	}
}
