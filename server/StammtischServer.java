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
	public static int defaultPort = 26656;
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
	static ArrayList<ClientRequestHandler> handlers;
	static ArrayList<ClientRequestHandler> boundHandlers;
	
	// signal variable for ending server operation
	static boolean done = false;			
	
	
	
	// server constructor. Launches connectio9n listener thread
	public StammtischServer(int port) {
		// Indicate that server is starting
		System.out.printf("Starting server on port %d\n", port);
		
		// connect to users database
		System.out.printf("Connecting to users database on port: %d\n", sqlPort);
		userCon = SQLServer.connect(sqlServer, sqlPort, userDB);
		
		// connect to presentation database
		System.out.printf("Connecting to presentation database on port: %d\n", sqlPort);
		userCon = SQLServer.connect(sqlServer, sqlPort, presDB);

		// connect to user 
		System.out.printf("Connecting to user tracking database on port: %d\n", sqlPort);
		userCon = SQLServer.connect(sqlServer, sqlPort, userTrackingDB);

		// initialise client request handler list
		handlers = new ArrayList<ClientRequestHandler>();
		handlers.clear();
		
		// initialise bound request handler list
		boundHandlers = new ArrayList<ClientRequestHandler>();
		boundHandlers.clear();
		
		// Initialise connection listener
		System.out.printf("Starting connection listener \n");
		connectionListener.start();
	}

	
	
	// Connection listener thread
	// waits for incoming connection
	Thread connectionListener = new Thread("Stammtish connection listener") {
		private int nextInstance = 0;
		
		@Override
		public void run() {
			try {
				sSocket = new ServerSocket(defaultPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while(true) {
				try {
					// wait for client connection
					handlers.add(new ClientRequestHandler(sSocket.accept(), nextInstance));		// create client handler object
					Thread thread = new Thread(handlers.get(handlers.size()-1));					// start client request handler in its own thread
					System.out.printf("[INFO] Client connected, starting handler thread %d \n", nextInstance);
					nextInstance++;
					thread.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	
	
	// Remove client handlers that are no longer connected
	// returns the number of client request handlers removed
	public static int pruneRequestHandlers(){
		ArrayList<Integer> inactiveClients = new ArrayList<Integer>();
		
		// loop through all request handlers, determine which of them are inactive
		for(int i = 0; i < handlers.size(); i++)
			if (handlers.get(i).isDone()) 
				inactiveClients.add(i);
		
		// remove all inactive request handlers from the handler list
		for(int i = 0; i < inactiveClients.size(); i++){
			// if inactive client was bound, unbind client
			
			// remove from handler list
			handlers.remove(inactiveClients.get(i));
		}
		
		// return number of pruned handlers
		return inactiveClients.size();
	}
	
	
	
	// display data for all connected clients
	public static void printHandlerList(ArrayList<ClientRequestHandler> handler){
		// if no clients are connected, indicate this
		if(handler.size() == 0){
			System.out.printf("No handlers in list!\n");
		}else{ 
			// print out info for all clients
			for (int i = 0; i < handler.size(); i++) {
				System.out.printf("Client: %d, %s\n",i , handler.get(i).getInfoString());
			}
		}
	}
	
	
	
	// method parses an integer from input string
	public static Integer parseCommandInt(String p1, int min, int max){
		Integer p1int = 0;
		
		// try to parse the command parameter
		try{
			p1int = Integer.parseInt(p1);
		}catch (NumberFormatException e){
			System.out.printf("[ERR] '%s' invalid, expected integer.\n", p1);
			return null;
		}
		
		// if the number is negative, client cannot be bound
		if ((p1int < min) || (p1int > max)){
			System.out.printf("[ERR] '%d' invalid integer, out of range.\n", p1int);
			return null;
		}
		
		// return integer object
		return p1int;
	}
	
	
	
	// bind a client, bound clients print to the stream
	public static void bindHandler(Scanner commandScanner){
		// command parameter (client to bind)
		String p1 = commandScanner.next();
		
		// attempt to parse command input
		Integer p1int = parseCommandInt(p1, 0, handlers.size());
		if(p1int == null) return;
		
		// prune inactive request handlers
		pruneRequestHandlers();
		
		// check that client with this number actually exists
		if(p1int > handlers.size()){
			System.out.printf("[ERR] '%d' - no such handler.\n", p1int);			
			return;
		}
		
		// your parse succeeded! add handler to bound handlers list
		boundHandlers.add(handlers.get(p1int));
		System.out.printf("Handler '%d' added to bound handlers.\n", p1int);	
	}
	
	
	
	// unbind specified handler
	public static void unbindHandler(Scanner commandScanner){
		// command parameter (client to bind)
		String p1 = commandScanner.next();
		
		// attempt to parse command input
		Integer p1int = parseCommandInt(p1, 0, boundHandlers.size());
		
		// prune inactive request handlers
		pruneRequestHandlers();
		
		// check that client with this number actually exists
		if(p1int > handlers.size()){
			System.out.printf("[ERR] '%d' - no such handler.\n", p1int);			
			return;
		}
		
		// your parse succeeded! add handler to bound handlers list
		boundHandlers.remove(p1int);
		System.out.printf("Handler '%d' added to bound handlers.\n", p1int);	
	}
	
	
	// unbind all handlers
	static void unbindAllHandlers(){
		System.out.printf("Unbound %d client request handlers\n", boundHandlers.size());
		boundHandlers.clear();
	}
	
	
	
	// sends a custom request object to given client
	static void sendCustomRequestObject(Scanner commandScanner){
		String id = null;
		
		RequestObject thisRequest = new RequestObject(id, null, 0);
	}
	
	
	
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
					System.out.printf("Stopping stammtisch server.\n");
					done = true;
					break;
				
				// list currently running handlers
				case "listHandlers": 
					System.out.printf("%d running request handlers:\n", handlers.size());
					printHandlerList(handlers); 
					break;	
				
				// list all currently bound handlers
				case "listBoundHandlers": 
					System.out.printf("%d bound client request handlers:\n", boundHandlers.size());
					printHandlerList(boundHandlers); 
					break;
				
				// unbind all handlers
				case "unbindAll": unbindAllHandlers(); break;
				
				// bind handler
				case "bindHandler": bindHandler(commandScanner); break; 
				
				// unbind handler
				case "unbindHandler": unbindHandler(commandScanner); break;
				
				// send request to client serviced by bound handler
				case "sendReq": sendCustomRequestObject(commandScanner); break;
					
				// unrecognised command
				default:
					System.out.printf("[ERR] Unrecognised command! '%s'.\n", command);
					break;
			}
		}
		// close command scanner
		commandScanner.close();
		
		// shut down server & all worker threads
		System.exit(0);
	}
}
