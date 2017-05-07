package com.rsmith.util;

public class Logger {
    public static synchronized void info(String message){
	System.out.println(message);
    }
}
