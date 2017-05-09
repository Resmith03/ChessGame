package com.rsmith.models;

public class Player{
    private String username;
    private PlayerColor color;
    private Integer id;
    private static volatile Integer playerCounter = 0; 

    private Player() {
	playerCounter++;
	id = playerCounter;
    }

    public Player(String username) {
	this();
	this.username = username;
    }

    public Player(String username, PlayerColor color) {
	this(username);
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
