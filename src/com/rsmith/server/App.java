package com.rsmith.server;

import com.rsmith.services.Server;

public class App {
    public static void main(String args[]) {
	new Thread(Server.getInstance()).start();
	;
    }
}
