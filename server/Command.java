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
	private String descString;
	private String usageString;
	private String name;
	private int paramCount;
	
	// constructor
	protected Command(String name, int paramCount, String desc, String usage){
		this.name = name;
		this.paramCount = paramCount;
		this.descString = desc;
		this.usageString = usage;
	}
	
	// get name of command
	@Override public String toString(){
		return name;
	}
	
	// command execute memory
	public void execute(Scanner sc){
		System.out.printf("[ERR] %s Unimplemented, override this method you scrub!\n", this.name);
	}
	
	// returns number of parameters the command takes
	public int getParamCount(){
		return paramCount;
	}
	
	// displays description info
	public void displayDesc(){
		System.out.printf("'" + name + "'" + "\t" + descString + "\n");
	}
	
	// displays usage and description info
	public void displayUsage(){
		System.out.printf("Usage:" + "\t" + usageString + "\n");
	}
}
