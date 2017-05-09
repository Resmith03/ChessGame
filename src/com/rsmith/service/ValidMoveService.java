package com.rsmith.service;

import java.util.ArrayList;
import java.util.List;

import com.rsmith.models.GamePiece;
import com.rsmith.models.Location;
import com.rsmith.models.Pawn;
import com.rsmith.models.PlayerColor;

public class ValidMoveService {
    private List<Location> validLocations;
    private Location location;

    public List<Location> getPawnMoves(GamePiece pawn, Location start) {
	
	validLocations = new ArrayList<Location>();
	if (pawn.getColor() == PlayerColor.BLACK) {
	    validLocations.add(new Location(start.getX(), start.getY() - 1));
	    if (((Pawn) pawn).getIsFirstMove()) {
		validLocations.add(new Location(start.getX(), start.getY() - 2));
		((Pawn) pawn).setIsFirstMove(false);
	    }
	}

	if (pawn.getColor() == PlayerColor.WHITE) {
	    validLocations.add(new Location(start.getX(), start.getY() + 1));
	    if (((Pawn) pawn).getIsFirstMove()) {
		validLocations.add(new Location(start.getX(), start.getY() + 2));
		((Pawn) pawn).setIsFirstMove(false);
	    }
	}

	return validLocations;
    }

    public List<Location> getRookMoves(Location start) {
	List<Location> validLocations = new ArrayList<Location>();

	validLocations.addAll(moveUnlimitedRight(start));
	validLocations.addAll(moveUnlimitedUp(start));
	validLocations.addAll(moveUnlimitedLeft(start));
	validLocations.addAll(moveUnlimitedDown(start));
	return validLocations;
    }

    // Ain't it funny how the night moves. Bob Seger. Classic...
    public List<Location> getKnightMoves(Location start) {

	List<Location> validLocations = new ArrayList<Location>();

	validLocations.add(new Location(start.getX() + 2, start.getY() + 1));
	validLocations.add(new Location(start.getX() + 2, start.getY() - 1));
	validLocations.add(new Location(start.getX() - 2, start.getY() + 1));
	validLocations.add(new Location(start.getX() - 2, start.getY() - 1));
	validLocations.add(new Location(start.getX() + 1, start.getY() + 2));
	validLocations.add(new Location(start.getX() + 1, start.getY() - 2));
	validLocations.add(new Location(start.getX() - 1, start.getY() + 2));
	validLocations.add(new Location(start.getX() - 1, start.getY() - 2));

	return validLocations;
    }

    public List<Location> getBishopMoves(Location start) {
	List<Location> validLocations = new ArrayList<Location>();

	validLocations.addAll(moveUnlimitedUpRight(start));
	validLocations.addAll(moveUnlimitedUpLeft(start));
	validLocations.addAll(moveUnlimitedDownRight(start));
	validLocations.addAll(moveUnlimitedDownLeft(start));

	return validLocations;
    }

    public List<Location> getKingMoves(Location start) {
	List<Location> validLocations = new ArrayList<Location>();
	
	validLocations.add(new Location(start.getX(), start.getY() + 1));
	validLocations.add(new Location(start.getX(), start.getY() - 1));
	validLocations.add(new Location(start.getX() + 1, start.getY()));
	validLocations.add(new Location(start.getX() - 1, start.getY()));
	validLocations.add(new Location(start.getX() + 1, start.getY() + 1));
	validLocations.add(new Location(start.getX() + 1, start.getY() - 1));
	validLocations.add(new Location(start.getX() - 1, start.getY() + 1));
	validLocations.add(new Location(start.getX() - 1, start.getY() - 1));
	
	return validLocations;
    }

    public List<Location> getQueenMoves(Location start) {

	List<Location> validLocations = new ArrayList<Location>();
	validLocations.addAll(moveUnlimitedUp(start));
	validLocations.addAll(moveUnlimitedDown(start));
	validLocations.addAll(moveUnlimitedLeft(start));
	validLocations.addAll(moveUnlimitedRight(start));
	validLocations.addAll(moveUnlimitedUpRight(start));
	validLocations.addAll(moveUnlimitedUpLeft(start));
	validLocations.addAll(moveUnlimitedDownRight(start));
	validLocations.addAll(moveUnlimitedDownLeft(start));
	return validLocations;
    }

    private List<Location> moveUnlimitedRight(Location start) {
	int x = start.getX();
	int y = start.getY();
	
	List<Location> validLocations = new ArrayList<Location>();
	for (int i = 1; (x + i) < 8; i++) {
	    validLocations.add(new Location(x + i, y));
	}
	return validLocations;
    }

    private List<Location> moveUnlimitedLeft(Location start) {
	int x = start.getX();
	int y = start.getY();
	
	List<Location> validLocations = new ArrayList<Location>();
	for (int i = 1; (x - i) >= 0; i++) {
	    validLocations.add(new Location(x - i, y));
	}
	return validLocations;
    }

    private List<Location> moveUnlimitedUp(Location start) {
	int x = start.getX();
	int y = start.getY();
	
	List<Location> validLocations = new ArrayList<Location>();
	for (int i = 1; (y - i) >= 0; i++) {
	    validLocations.add(new Location(x, y - i));
	}
	return validLocations;
    }

    private List<Location> moveUnlimitedDown(Location start) {
	int x = start.getX();
	int y = start.getY();
	
	List<Location> validLocations = new ArrayList<Location>();
	for (int i = 1; (y + i) < 8; i++) {
	    validLocations.add(new Location(x, y + i));
	}
	return validLocations;
    }

    private List<Location> moveUnlimitedUpRight(Location start) {
	List<Location> locations = new ArrayList<Location>();
	int x = start.getX();
	int y = start.getY();
	while (x < 7 && y > 0) {
	    x++;
	    y--;
	    locations.add(new Location(x, y));
	}

	return locations;
    }

    private List<Location> moveUnlimitedUpLeft(Location location) {
	List<Location> locations = new ArrayList<Location>();
	
	int x = location.getX();
	int y = location.getY();
	
	while (x > 0 && y > 0) {
	    x--;
	    y--;
	    locations.add(new Location(x, y));
	}
	
	return locations;
    }

    private List<Location> moveUnlimitedDownRight(Location location) {	
	List<Location> locations = new ArrayList<Location>();
	
	int x = location.getX();
	int y = location.getY();
	
	while (x < 7 && y < 7) {
	    x++;
	    y++;
	    locations.add(new Location(x, y));
	}
	
	return locations;
    }

    private List<Location> moveUnlimitedDownLeft(Location location) {	
	List<Location> locations = new ArrayList<Location>();
	
	int x = location.getX();
	int y = location.getY();
	
	while (x > 0 && y < 7) {
	    x--;
	    y++;
	    validLocations.add(new Location(x, y));
	}
	
	return locations;
    }

}
