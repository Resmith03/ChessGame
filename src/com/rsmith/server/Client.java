package com.rsmith.server;

import java.io.IOException;

import com.rsmith.models.Player;

public class Client {
    private Player player;
    private SocketManager manager;
    
    public Client(Player player, SocketManager manager) throws IOException {
	this.manager = manager;
	this.player = player;
    }

    public Player getPlayer() {
	return player;
    }    
}
