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

// our imports
import SQL.*;


// java imports
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
	static ArrayList<Command> commands; 
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
		presCon = SQLServer.connect(sqlServer, sqlPort, presDB);

		// connect to user 
		System.out.printf("Connecting to user tracking database on port: %d\n", sqlPort);
		userTrackingCon = SQLServer.connect(sqlServer, sqlPort, userTrackingDB);

		// initialise client request handler list
		handlers = new ArrayList<ClientRequestHandler>();
		handlers.clear();
		
		// initialise bound request handler list
		boundHandlers = new ArrayList<ClientRequestHandler>();
		boundHandlers.clear();
		
		// set up commands
		setUpCommands();
		
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
					// wait for client connection & make handler
					ClientRequestHandler thisHandler = 
						new ClientRequestHandler(
							sSocket.accept(), 
							nextInstance,
							userCon,
							presCon,
							userTrackingCon);
					
					// add handler to handler list
					handlers.add(thisHandler);
					
					// start request handler thread
					Thread thread = new Thread(handlers.get(handlers.size()-1));					
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
	public static int pruneHandlerLists(){
		ArrayList<ClientRequestHandler> inactiveHandlers = new ArrayList<ClientRequestHandler>();
		
		inactiveHandlers.clear();
		
		// loop through all request handlers, determine which of them are inactive
		for(int i = 0; i < handlers.size(); i++)
			if (handlers.get(i).isDone()) 
				inactiveHandlers.add(handlers.get(i));
		
		// remove all inactive request handlers from the handler list
		for(int i = 0; i < inactiveHandlers.size(); i++){
			boundHandlers.remove(inactiveHandlers.get(i));
			handlers.remove(inactiveHandlers.get(i));
		}
		
		// return number of pruned handlers
		return inactiveHandlers.size();
	}
	
	
	
	// display data for all connected clients
	public static void printHandlerList(ArrayList<ClientRequestHandler> handler){
		// if no clients are connected, indicate this
		if(handler.size() == 0){
			System.out.printf("No handlers in list!\n");
		}else{ 
			// print out info for all clients
			for (int i = 0; i < handler.size(); i++) {
				System.out.printf("%d) %s\n",i ,handler.get(i).getInfoString());
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
		
		// check that client with this number actually exists
		if(p1int > handlers.size()){
			System.out.printf("[ERR] '%d' - no such handler.\n", p1int);			
			return;
		}
		
		// your parse succeeded! add handler to bound handlers list
		if(!boundHandlers.contains(handlers.get(p1int)))
			boundHandlers.add(handlers.get(p1int));
		System.out.printf("Handler '%d' added to bound handlers.\n", p1int);	
	}
	
	
	
	// unbind specified handler
	public static void unbindHandler(Scanner commandScanner){
		// command parameter (client to bind)
		String p1 = commandScanner.next();
		
		// attempt to parse command input
		Integer p1int = parseCommandInt(p1, 0, boundHandlers.size());
		
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
	
	
	
	// bind all handlers
	static void bindAllHandlers(){
		boundHandlers.clear();
		for(int i = 0; i < handlers.size(); i++)
			boundHandlers.add(handlers.get(i));
	}
	

	
	// sets up commands
	public static void setUpCommands(){
		// clear commands
		commands = new ArrayList<Command>();
		commands.clear();
		
		// stop command
		commands.add(new Command("stop"){
			@Override public void execute(Scanner cs){
				// some code here
				System.out.printf("Stopping stammtisch server.\n");
				done = true;
			}
		});
		
		// list handlers command
		commands.add(new Command("lh"){
			@Override public void execute(Scanner cs){
				System.out.printf("%d running request handlers:\n", handlers.size());
				printHandlerList(handlers);
			}
		});
	
		// list bound handlers
		commands.add(new Command("lbh"){
			@Override public void execute(Scanner cs){
				System.out.printf("%d bound client request handlers:\n", boundHandlers.size());
				printHandlerList(boundHandlers); 
			}
		});		
		

	}
	
	
	
	// searches for a command
	public static Command searchCommands(String command){
		for(int i = 0; i < commands.size(); i++){
			if(command.equalsIgnoreCase(commands.get(i).toString()))
				return commands.get(i);
		}
		return null;
	}
	
	
	
	// Main method for launching server
	public static void main(String[] args) {
		// do command-line argument parsing here
		
		// start the stammtisch server connection listener
		new StammtischServer(defaultPort);
		
		// "scanner" object for reading from command line (javawtf?)
		Scanner commandScanner = new Scanner(System.in);	
		String command = new String();
		Command currentCommand = null;
		
		// command handling loop
		while(!done) {
			command = commandScanner.next();
			currentCommand = searchCommands(command);
			
			// run command
			if (currentCommand != null){
				currentCommand.execute(commandScanner);
			}else{
				System.out.printf("[ERR] '%s' not recognised as command\n", command);
			}
				
			
			// handle command input
			switch(command) {
				// bind all handlers
				case "bindAll": bindAllHandlers(); break;
					
				// unbind all handlers
				case "unbindAll": unbindAllHandlers(); break;
				
				// bind handler
				case "bind": bindHandler(commandScanner); break; 
				
				// unbind handler
				case "unbind": unbindHandler(commandScanner); break;
					
				// remove inactive handlers
				case "prune": pruneHandlerLists(); break;
				
				// unrecognised command
				default:
					//System.out.printf("[ERR] Unrecognised command! '%s'.\n", command);
					break;
			}
		}
		// close command scanner
		commandScanner.close();
		
		// shut down server & all worker threads
		System.exit(0);
	}
}
