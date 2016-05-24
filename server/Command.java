/*
* (C) Stammtisch
* First version created by: Alexander Cramb (ac1362)
* Date of first version: 23/05/2016
* 
* Last version by: Alexander Cramb (ac1362)
* Date of last update: 23/05/2016
* Version number: 0.1
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
	
	// constructor
	protected Command(String name){
		this.name = name;
	}
	
	// get name of command
	@Override public String toString(){
		return name;
	}
	
	// command execute memory
	public void execute(Scanner sc){
		
	}
}
