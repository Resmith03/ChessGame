package com.rsmith.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.rsmith.models.Player;

public class Client implements Runnable {

    private BufferedReader reader;
    private PrintWriter writer;
    private List<String> inputList;
    private List<String> outputList;
    private Player player;

    public Client(Player player, Socket socket) {
	setInputAndOutputStreams(socket);
	inputList = new ArrayList<String>();
	outputList = new ArrayList<String>();
	this.player = player;
    }

    public List<String> getOutputMessages() {
	return new ArrayList<String>(outputList);
    }

    public PrintWriter getWriter() {
	return writer;
    }

    public void clearOutputMessages() {
	outputList.clear();
    }

    public void addOutputMessage(String message) {
	outputList.add(message);
    }

    private void setInputAndOutputStreams(Socket socket) {
	try {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
	    this.writer = writer;
	    this.reader = reader;
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    public BufferedReader getReader() {
	return reader;
    }

    @Override
    public void run() {
	new Thread(new ClientReader(this)).start();
	new Thread(new ClientWriter(this)).start();
    }

    public Player getPlayer() {
	return player;
    }

    public void addInputMessage(String message) {
	inputList.add(message);
    }
}
