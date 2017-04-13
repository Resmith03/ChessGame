import java.util.ArrayList;

public class Pawn implements GamePiece{
	private String color;
	private String currentPosition;
	private static ArrayList<String> validMoves;
	private int moveNumber;
	
	public Pawn(String color, String position){
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
		if (moveNumber == 1){
			validMoves.add(String.valueOf(xCoord)+String.valueOf(yCoord+2));
		}
		validMoves.add(String.valueOf(xCoord)+String.valueOf(yCoord+1));
		return validMoves;
	}
}
