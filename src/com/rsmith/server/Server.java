package com.rsmith.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.rsmith.models.Player;

public class Server implements Runnable {
    private static final int PORT = 1321;
    ServerSocket listener;
    private List<Client> clientList;

    public Server() throws IOException {
	listener = new ServerSocket(PORT);
	clientList = new ArrayList<Client>();
    }

    @Override
    public void run() {
	while (true) {
	    try {
		Socket socket = listener.accept();
		Player player = new Player("Player " + (clientList.size() + 1));
		Client client = new Client(player, socket);
		clientList.add(client);
		new Thread(client).start();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }
}