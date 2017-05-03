package com.rsmith.models;
public class Player{
    public String username;
    public String color;

    public Player() {

    }

    public Player(String username) {
	this.username = username;
    }

    public Player(String username, String color) {
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

    public String getColor() {
	return color;
    }

    public void setColor(String color) {
	this.color = color;
    }
	
}
