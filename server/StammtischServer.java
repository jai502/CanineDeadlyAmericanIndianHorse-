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

import SQL.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.ArrayList;
import java.sql.*;


public class StammtischServer {
	
	// Socket stuff
	//public static int defaultPort = 26656; // Port to run server on
	public static int defaultPort = 4444;
	public ServerSocket sSocket;
	
	// SQL connection stuff
	// address and port
	int sqlPort = 3306;
	String sqlServer = "stammtischsql.ddns.net";
	
	// database identifiers
	String userTrackingDB = "usertracking";
	String presDB = "presentations";	
	String userDB = "useraccounts";
	
	// table identifiers
	String presTable = "testpresentations";	
	String userTable = "users";
	
	// sql connections
	Connection userCon;
	Connection presCon;
	Connection userTrackingCon;

	// client request handlers
	ArrayList<ClientRequestHandler> clients;
	
	// signal variable for ending server operation
	static boolean done = false;			
	
	
	// server constructor. Launches connectio9n listener thread
	public StammtischServer(int port) {
		// Indicate that server is starting
		System.out.printf("[INFO] Starting server on port %d\n", port);
		
		// connect to users database
		System.out.printf("[INFO] Connecting to users database on port: %d\n", sqlPort);
		userCon = SQLServer.connect(sqlServer, sqlPort, userDB);
		
		// connect to prsentation database
		System.out.printf("[INFO] Connecting to presentation database on port: %d\n", sqlPort);
		userCon = SQLServer.connect(sqlServer, sqlPort, presDB);

		// connect to user 
		System.out.printf("[INFO] Connecting to user tracking database on port: %d\n", sqlPort);
		userCon = SQLServer.connect(sqlServer, sqlPort, userTrackingDB);
		
		// Initialise connection listener
		System.out.printf("[INFO] Starting connection listener \n");
		connectionListener.start();
	}

	
	
	// Connection listener thread
	// waits for incoming connection
	Thread connectionListener = new Thread("Stammtish connection listener") {
		private int nextInstance = 0;
		
		public void run() {
			try {
				sSocket = new ServerSocket(defaultPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while(true) {
				try {
					// wait for client connection
					clients.add(new ClientRequestHandler(sSocket.accept(), nextInstance));		// create client handler object
					Thread thread = new Thread(clients.get(clients.size()-1));					// start cleint request handler in its own thread
					System.out.printf("[INFO] Client connected, starting handler thread %d \n", nextInstance);
					nextInstance++;
					thread.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	
	
	// Main method for launching server
	public static void main(String[] args) {
		// do command-line argument parsing here
		
		// start the stammtisch server connection listener
		new StammtischServer(defaultPort);
		
		// "scanner" object for reading from command line (javawtf?)
		Scanner commandScanner = new Scanner(System.in);	
		String command = new String();	// command typed on the command line
		
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
