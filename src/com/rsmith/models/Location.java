package com.rsmith.models;

import com.rsmith.DTO.LocationDTO;

public class Location {
    private int x;
    private int y;
    
    public Location(int x, int y){
	this.x = x;
	this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public LocationDTO toDTO(){
	return new LocationDTO(x,y);
    }
}
