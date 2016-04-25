/*
* (C) Stammtisch
* First version created by: Alexander Cramb (ac1362)
* Date of first version: 28/01/2016
* 
* Last version by: Alexander Cramb (ac1362)
* Date of last update: 
* Version number: 0.1
* 
* Commit date: 
* Description: 
* Load bearing server for stammtisch application
*/


package server;


//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.net.ServerSocket;
//import java.net.Socket;
import java.util.Scanner;


public class StammtischServer {
	
	// Socket stuff
	public static int defaultPort = 26656; // Port to run server on
	public ServerSocket sSocket;
	
	
	
	// server constructor. Launches connectio9n listener thread
	public StammtischServer(int port) {
		// Indicate that server is starting
		System.out.printf("[INFO] Starting server on port %d\n", port);
		
		// Initialise connection listener
		System.out.printf("[INFO] Starting connection listener \n");
		connectionListener.start();
	}

	
	
	// Connection listener thread
	// waits for incoming connection
	Thread connectionListener = new Thread("Stammtish connection listener") {
		public void run() {
			try {
				sSocket = new ServerSocket(defaultPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while(true) {
				try {
					System.out.printf("[INFO] Waiting for client connection... \n");
					Thread thread = new Thread(new ClientRequestHandler(sSocket.accept()));
					System.out.printf("[INFO] Client connected, starting handler thread \n");
					thread.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	
	
	// Main method for launching server
	public static void main(String[] args) {
		// do input argument passing here
		
		// start the stammtisch server
		new StammtischServer(defaultPort);
		
		// "scanner" object for reading from command line (javawtf?)
		Scanner commandScanner = new Scanner(System.in);	
		String command = new String();	// command typed on the command line
		boolean done = false;			// signal variable for ending server operation
		
		// command handling loop
		while(!done) {
			// wait for command input
			command = commandScanner.next();
			
			// handle command input
			switch(command) {
				// exit command 
				case "stop":
					System.out.printf("[INFO] Stopping stammtisch server.\n");
					done = true;
					break;
					
				// unrecognised command
				default:
					System.out.printf("[ERROR] Unrecognised command '%s'.\n", command);
					break;
			}
		}
		// close command scanner
		commandScanner.close();
		
		// shut down server & all worker threads
		System.exit(0);
	}
}
