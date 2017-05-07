package com.rsmith.models;

import com.rsmith.server.SocketManager;

public class Player{
    private String username;
    private PlayerColor color;
    private Integer id;
    private static volatile Integer playerCounter = 0; 
    private SocketManager manager;
    
    private Player() {
	playerCounter++;
	id = playerCounter;
    }

    public Player(String username, SocketManager manager) {
	this();
	this.username = username;
	this.manager = manager;
    }

    public Player(String username, PlayerColor color, SocketManager manager) {
	this(username, manager);
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

    public Response sendRequest(Request request) {
	return manager.sendRequest(request);
    }

    public boolean isConnected() {
	return manager.isConnected();
    }
}
