package com.rsmith.server;

import java.io.IOException;

public class App {
    public static void main(String args[]){
	try {
	    new Thread(new Server()).start();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
}
