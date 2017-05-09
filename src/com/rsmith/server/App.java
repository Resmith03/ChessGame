package com.rsmith.server;

public class App {
    public static void main(String args[]){
	new Thread(Server.getInstance()).start();;
    }
}
