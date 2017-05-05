package com.rsmith.server;

import java.io.BufferedReader;
import java.io.IOException;
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
    private Socket socket;
    
    public Client(Player player, Socket socket) throws IOException {
	this.socket = socket;
	reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	writer = new PrintWriter(socket.getOutputStream(), true);
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
    
    public void sendMessage(String message){
	outputList.add(message);
    }
    
    public List<String> getMessages(){
	return inputList;
    }

    public BufferedReader getReader() {
	return reader;
    }
    
    public boolean isConnected(){
	boolean connected = false;
	
	if(socket != null && socket.isConnected() && !socket.isClosed()){
	    connected = true;
	}
	
	return connected;
    }
    @Override
    public void run() {
	new Thread(new ConnectionManager(this)).start();
    }

    public Player getPlayer() {
	return player;
    }

    public void addInputMessage(String message) {
	inputList.add(message);
    }

    public void clearInputMessages() {
	inputList.clear();
	
    }
}
