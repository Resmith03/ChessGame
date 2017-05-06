package com.rsmith.models;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private static final int BOARD_WIDTH = 8;
    private static final int BOARD_HEIGHT = 8;
    private List<BoardSpace> boardSpaceList;
    private Game game;
    
    public GameBoard(Game game) {
	this.game = game;
	boardSpaceList = new ArrayList<BoardSpace>();
	populateDefaultBoard();
    }
    
    /**
     * Creates the default board and loads the game pieces
     */
    private void populateDefaultBoard() {
	for (int x = 0; x < BOARD_WIDTH; x++) {
	    for (int y = 0; y < BOARD_HEIGHT; y++) {

		BoardSpace space = new BoardSpace(x, y);
		GamePiece piece = getDefaultGamePiece(x, y);

		if (piece != null) {
		    space.setGamePiece(piece);
		}

		boardSpaceList.add(space);
	    }
	}
    }

    /*
     * ======================================== 
     * 		DEFAULT BOARD LAYOUT
     * ========================================
     * <><><><><><><><><><><><><><><><><><><><>
     * ======================================== 
     * {"r", "n", "b", "q", "k", "b", "n", "r"} 
     * {"p", "p", "p", "p", "p", "p", "p", "p"} 
     * {"-", "-", "-", "-", "-", "-", "-", "-"} 
     * {"-", "-", "-", "-", "-", "-", "-", "-"} 
     * {"-", "-", "-", "-", "-", "-", "-", "-"} 
     * {"-", "-", "-", "-", "-", "-", "-", "-"}
     * {"P", "P", "P", "P", "P", "P", "P", "P"} 
     * {"R", "N", "B", "Q", "K", "B", "N", "R"} 
     * ========================================
     * <><><><><><><><><><><><><><><><><><><><>
     * ========================================
     * 
     * Game Piece Types 
     * ---------------- 
     * r/R = Rook 
     * n/N = Knight 
     * p/P = Pawn 
     * k/K = King 
     * b/B = Bishop 
     * q/Q = Queen 
     *
     * Lower-case = White 
     * Upper-case = Black
     * 
     */
    private GamePiece getDefaultGamePiece(int x, int y) {
	GamePiece piece = null;

	if (x == 0 || x == 7) {
	    if (y == 0) {
		piece = new Rook(PlayerColor.WHITE);
	    } else if (y == 7) {
		piece = new Rook(PlayerColor.BLACK);
	    }

	    if (y == 1) {
		piece = new Pawn(PlayerColor.WHITE);
	    } else if (y == 6) {
		piece = new Pawn(PlayerColor.BLACK);
	    }
	} else if (x == 1 || x == 6) {
	    if (y == 0) {
		piece = new Knight(PlayerColor.WHITE);
	    } else if (y == 7) {
		piece = new Knight(PlayerColor.BLACK);
	    }
	    
	    if (y == 1) {
		piece = new Bishop(PlayerColor.WHITE);
	    } else if (y == 6) {
		piece = new Bishop(PlayerColor.BLACK);
	    }
	}else if(x == 2 || x == 5){
	    if (y == 0) {
		piece = new Pawn(PlayerColor.WHITE);
	    } else if (y == 7) {
		piece = new Pawn(PlayerColor.BLACK);
	    }
	    
	    if (y == 1) {
		piece = new Pawn(PlayerColor.WHITE);
	    } else if (y == 6) {
		piece = new Pawn(PlayerColor.BLACK);
	    }
	}else if(x == 3){
	    if (y == 0) {
		piece = new Queen(PlayerColor.WHITE);
	    } else if (y == 7) {
		piece = new Queen(PlayerColor.BLACK);
	    }
	    
	    if (y == 1) {
		piece = new Pawn(PlayerColor.WHITE);
	    } else if (y == 6) {
		piece = new Pawn(PlayerColor.BLACK);
	    }
	}else if(x == 4){
	    if (y == 0) {
		piece = new King(PlayerColor.WHITE);
	    } else if (y == 7) {
		piece = new King(PlayerColor.BLACK);
	    }
	    
	    if (y == 1) {
		piece = new Pawn(PlayerColor.WHITE);
	    } else if (y == 6) {
		piece = new Pawn(PlayerColor.BLACK);
	    }
	}

	return piece;

    }
    
    public BoardSpace getSpaceAtLocation(Location location){
	
	BoardSpace response = null;
	
	for(BoardSpace space:boardSpaceList){
	    if(space.getX() == location.getX() && space.getY() == location.getY()){
		response = space;
		break;
	    }
	}
	
	return response;
    }
    
    public GamePiece getPieceAtLocation(Location location) {
	GamePiece gamePiece = null;
	
	BoardSpace space = getSpaceAtLocation(location);
	
	if(space != null){
	    gamePiece = space.getGamePiece();
	}
	
	return gamePiece;
    }

    public boolean move(Player player, Location fromLocation, Location toLocation) {
	boolean validMove = true;
	
	GamePiece fromPiece = getPieceAtLocation(fromLocation);
	GamePiece toPiece = getPieceAtLocation(toLocation);
	
	if(fromPiece != null && fromPiece.getColor().equals(player.getColor())){
	    if(toPiece != null){
		game.broadcast("Game Piece: " + toPiece.getType() + " of color: " + toPiece.getColor() + " was successfully captured!");
	    }
		
	    setPieceAtLocation(toLocation, fromPiece);
	    setPieceAtLocation(fromLocation, null);
	}else{
	    validMove = false;
	}
	
	return validMove;
    }

    private void setPieceAtLocation(Location location, GamePiece piece) {
	BoardSpace space = getSpaceAtLocation(location);
	space.setGamePiece(piece);
	setSpaceAtLocation(location, space);
    }

    private void setSpaceAtLocation(Location location, BoardSpace space) {
	for(BoardSpace boardSpace:boardSpaceList){
	    if(boardSpace.getX() == location.getX() && boardSpace.getY() == location.getY()){
		boardSpace = space;
		break;
	    }
	}
	
    }

    public void printBoard() {
	for(int y = 0; y < BOARD_WIDTH;y++){
	    
	    String row = (y + 1) + "...  ";
	    for(int x = 0; x < BOARD_HEIGHT; x++){
		BoardSpace space = getSpaceAtLocation(new Location(x,y));
		
		GamePiece piece = space.getGamePiece();
		if(piece == null){
		    row += " - ";
		}else{
		    row += " " + piece.getChar() + " ";
		}
	    }
	    game.broadcast(row);
	}
	
    }

    public boolean isKingsAlive() {
	int foundCounter = 0;
	boolean kingsAlive = false;
	
	for(BoardSpace space:boardSpaceList){
	    GamePiece piece = space.getGamePiece();
	    if(piece != null){
		if(piece.getType() == PieceType.KING){
		    foundCounter++;
		}
		
		if(foundCounter > 1){
		    kingsAlive = true;
		    break;
		}
	    }
	}
	
	return kingsAlive;
    }
}
