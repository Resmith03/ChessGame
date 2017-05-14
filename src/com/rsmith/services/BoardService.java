package com.rsmith.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rsmith.models.board.BoardSpace;
import com.rsmith.models.board.Color;
import com.rsmith.models.board.Location;
import com.rsmith.models.board.Move;
import com.rsmith.models.game.GamePiece;
import com.rsmith.models.game.Player;
import com.rsmith.models.pieces.Bishop;
import com.rsmith.models.pieces.King;
import com.rsmith.models.pieces.Knight;
import com.rsmith.models.pieces.Pawn;
import com.rsmith.models.pieces.Queen;
import com.rsmith.models.pieces.Rook;

public class BoardService {
    private static final int BOARD_WIDTH = 8;
    private static final int BOARD_HEIGHT = 8;
    private List<BoardSpace> boardSpaceList;
    private Map<GamePiece, List<Move>> moveHistory;
    private RuleService service;

    public BoardService() {
	moveHistory = new HashMap<GamePiece, List<Move>>();
	boardSpaceList = new ArrayList<BoardSpace>();
	service = new RuleService(this);
	populateDefaultBoard();
    }

    private void populateDefaultBoard() {
	for (int x = 0; x < BOARD_WIDTH; x++) {
	    for (int y = 0; y < BOARD_HEIGHT; y++) {

		BoardSpace space = new BoardSpace(new Location(x, y));
		GamePiece piece = getDefaultGamePiece(x, y);

		if (piece != null) {
		    space.setGamePiece(piece);
		}

		boardSpaceList.add(space);
	    }
	}
    }

    private static GamePiece getDefaultGamePiece(int x, int y) {
	GamePiece piece = null;

	if (x == 0 || x == 7) {
	    if (y == 0) {
		piece = new Rook(Color.WHITE);
	    } else if (y == 7) {
		piece = new Rook(Color.BLACK);
	    }

	    if (y == 1) {
		piece = new Pawn(Color.WHITE);
	    } else if (y == 6) {
		piece = new Pawn(Color.BLACK);
	    }
	} else if (x == 1 || x == 6) {
	    if (y == 0) {
		piece = new Knight(Color.WHITE);
	    } else if (y == 7) {
		piece = new Knight(Color.BLACK);
	    }

	    if (y == 1) {
		piece = new Pawn(Color.WHITE);
	    } else if (y == 6) {
		piece = new Pawn(Color.BLACK);
	    }
	} else if (x == 2 || x == 5) {
	    if (y == 0) {
		piece = new Bishop(Color.WHITE);
	    } else if (y == 7) {
		piece = new Bishop(Color.BLACK);
	    }

	    if (y == 1) {
		piece = new Pawn(Color.WHITE);
	    } else if (y == 6) {
		piece = new Pawn(Color.BLACK);
	    }
	} else if (x == 3) {
	    if (y == 0) {
		piece = new Queen(Color.WHITE);
	    } else if (y == 7) {
		piece = new Queen(Color.BLACK);
	    }

	    if (y == 1) {
		piece = new Pawn(Color.WHITE);
	    } else if (y == 6) {
		piece = new Pawn(Color.BLACK);
	    }
	} else if (x == 4) {
	    if (y == 0) {
		piece = new King(Color.WHITE);
	    } else if (y == 7) {
		piece = new King(Color.BLACK);
	    }

	    if (y == 1) {
		piece = new Pawn(Color.WHITE);
	    } else if (y == 6) {
		piece = new Pawn(Color.BLACK);
	    }
	}

	return piece;

    }

    public BoardSpace getSpaceAtLocation(Location location) {

	BoardSpace response = null;

	for (BoardSpace space : boardSpaceList) {
	    if (space.getLocation().equals(location)) {
		response = space;
		break;
	    }
	}

	return response;
    }

    public GamePiece getPieceAtLocation(Location location) {
	GamePiece gamePiece = null;

	BoardSpace space = getSpaceAtLocation(location);

	if (space != null) {
	    gamePiece = space.getGamePiece();
	}

	return gamePiece;
    }

    public boolean move(Player player, Move move) {
	boolean moved = false;
	
	if (service.validMove(player, move)) {
	    addHistory(move);
	    setPieceAtLocation(move.getTo(), getPieceAtLocation(move.getFrom()));
	    setPieceAtLocation(move.getFrom(), null);
	    moved = true;
	}

	return moved;
    }

    public Map<GamePiece, List<Move>> getMoveHistory() {
	return new HashMap<GamePiece, List<Move>>(moveHistory);
    }

    private void addHistory(Move move) {
	GamePiece piece = getPieceAtLocation(move.getFrom());
	List<Move> moves = moveHistory.get(piece);
	
	if(moves == null){
	    moves = new ArrayList<Move>();
	}
	
	moves.add(move);
	moveHistory.put(piece, moves);
    }

    private void setPieceAtLocation(Location location, GamePiece piece) {
	BoardSpace space = getSpaceAtLocation(location);
	space.setGamePiece(piece);
	setSpaceAtLocation(location, space);
    }

    private void setSpaceAtLocation(Location location, BoardSpace space) {
	for (BoardSpace boardSpace : boardSpaceList) {
	    if (boardSpace.getLocation().equals(location)) {
		boardSpace = space;
		break;
	    }
	}
    }

    public List<BoardSpace> getBoardSpaces() {
	return new ArrayList<BoardSpace>(boardSpaceList);
    }
}
