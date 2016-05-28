/*
* (C) Stammtisch
* First version created by: Alexander Cramb (ac1362)
* Date of first version: 23/05/2016
* 
* Last version by: Alexander Cramb (ac1362)
* Date of last update: 23/05/2016
* Version number: 1.0.2
* 
* Commit date: 
* Description: 
* 	Generic command class
*/


package server;


// java imports
import java.util.Scanner;


class Command {
	private String name;
	private int paramCount;
	
	// constructor
	protected Command(String name, int paramCount){
		this.name = name;
		this.paramCount = paramCount;
	}
	
	// get name of command
	@Override public String toString(){
		return name;
	}
	
	// command execute memory
	public void execute(Scanner sc){
		
	}
	
	// returns number of parameters the command takes
	public int getParamCount(){
		return paramCount;
	}
}
