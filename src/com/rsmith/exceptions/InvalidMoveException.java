package com.rsmith.exceptions;

public class InvalidMoveException extends Exception{
    public InvalidMoveException(String message){
	super(message);
    }
    public InvalidMoveException(){
	super("Player entered an invalid move!");
    }
}
