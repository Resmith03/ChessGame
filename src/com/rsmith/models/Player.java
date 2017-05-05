package com.rsmith.models;
public class Player{
    public String username;
    public PlayerColor color;

    public Player() {

    }

    public Player(String username) {
	this.username = username;
    }

    public Player(String username, PlayerColor color) {
	super();
	this.username = username;
	this.color = color;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public PlayerColor getColor() {
	return color;
    }

    public void setColor(PlayerColor color) {
	this.color = color;
    }
	
}
