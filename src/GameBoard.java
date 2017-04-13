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
	}
}
