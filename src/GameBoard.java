import java.util.HashMap;

public class GameBoard {
	private String[][] boardState;
	private HashMap<String, GamePiece> pieceLocations;
	
	public GameBoard(String[][] boardState){
		this.boardState = boardState;
	}
	
	private String[][] getBoardState(){
		return boardState;
	}
	
	private void setBoardState(String[][] boardState){
		this.boardState = boardState;
	}
	
	private void printBoardState(){
		for(String[] row : boardState){
			
		}
	}
	
	private void setInitialPieceLocations(){
		pieceLocations.put("A7", new Pawn("white", "A7"));
		pieceLocations.put("B7", new Pawn("white", "B7"));
		pieceLocations.put("C7", new Pawn("white", "C7"));
		pieceLocations.put("D7", new Pawn("white", "D7"));
		pieceLocations.put("E7", new Pawn("white", "E7"));
		pieceLocations.put("F7", new Pawn("white", "F7"));
		pieceLocations.put("G7", new Pawn("white", "G7"));
		pieceLocations.put("H7", new Pawn("white", "H7"));
	}
}
