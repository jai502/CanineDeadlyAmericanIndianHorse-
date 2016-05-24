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
import com.*;


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
	static ArrayList<Response> responses;
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
		
		// set up commands and responses
		setUpCommands();
		setUpRequests();
		
		// Initialise connection listener
		System.out.printf("Starting connection listener \n");
		connectionListener.start();
	}
	
	
	
	// sets up responses
	public static void setUpRequests(){
		// set up blank request array list
		responses = new ArrayList<Response>();
		responses.clear();
		
		// response to a ping
		responses.add(new Response("PING"){
			@Override public void respond(ClientRequestHandler handler,  RequestObject thisRequest){
				RequestObject response = new RequestObject("PONG", null, handler.getOrder());
				handler.sendResponse(response);
			}
		});
		
		// response to a disconnect request
		responses.add(new Response("DISCONNECT"){
			@Override public void respond(ClientRequestHandler handler, RequestObject thisRequest){
				handler.stop();
			}
		});
	
		// respond to login request
		responses.add(new Response("REQUEST_LOGIN"){
			@Override public void respond(ClientRequestHandler handler, RequestObject thisRequest){
				User thisUser = (User)thisRequest.param;
				System.out.printf("Name: %s, Pass: %s\n",
								  thisUser.getUsername(),
								  thisUser.getPassword());
			}
		});
	
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
							responses
						);
					
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
	public static Integer parseAsInt(String p1, int min, int max){
		Integer p1int = new Integer(0);
		
		// try to parse the command parameter
		try{
			p1int = Integer.parseInt(p1);
		}catch (NumberFormatException e){
			System.out.printf("[ERR] '%s' invalid, expected integer.\n", p1);
			return null;
		}
		
		// if the number is not in expected range
		if ((p1int < min) || (p1int > max)){
			System.out.printf("[ERR] '%d' out of range.\n", p1int);
			return null;
		}
		
		// return integer object
		return p1int;
	}
	

	
	// gets command parameters an arraylist of strings
	public static ArrayList<String> getParams(Scanner scanner){
		// list of command parameters
		ArrayList<String> params = new ArrayList<String>();
		params.clear();
		
		// loop, get all strings from scanner
		while(scanner.hasNext())
			params.add(scanner.next());
		
		// close the scanner
		scanner.close();
		
		// return parameter list
		return params;
	}
	
	
	
	// sets up commands
	public static void setUpCommands(){
		// clear commands
		commands = new ArrayList<Command>();
		commands.clear();
		
		// stop command
		commands.add(new Command("stop", 0){
			@Override public void execute(Scanner cs){
				// some code here
				System.out.printf("Stopping stammtisch server.\n");
				done = true;
			}
		});
		
		// list handlers command
		commands.add(new Command("lh", 0){
			@Override public void execute(Scanner cs){
				System.out.printf("%d running request handlers:\n", handlers.size());
				printHandlerList(handlers);
			}
		});
	
		// list bound handlers
		commands.add(new Command("lbh", 0){
			@Override public void execute(Scanner cs){
				System.out.printf("%d bound client request handlers:\n", boundHandlers.size());
				printHandlerList(boundHandlers); 
			}
		});		
			
		// bind all handlers
		commands.add(new Command("bindAll", 0){
			@Override public void execute(Scanner cs){
				boundHandlers.clear();
				for(int i = 0; i < handlers.size(); i++){
					boundHandlers.add(handlers.get(i));
				}
				System.out.printf("bound %d handlers\n", boundHandlers.size());
			}
		});	
		
		// unbind all handlers
		commands.add(new Command("unbindall", 0){
			@Override public void execute(Scanner cs){
				System.out.printf("Unbound %d client request handlers\n", boundHandlers.size());
				boundHandlers.clear();
			}
		});
		
		// bind a handler
		commands.add(new Command("bind", 1){
			@Override public void execute(Scanner cs){
				// get command parameters
				ArrayList<String> params = getParams(cs);
				
				// check that there are enough parameters
				if(params.size() < this.getParamCount()){
					System.out.printf(
						"[ERR] '%s' expects at least %d parameters\n", 
						this.toString(), this.getParamCount()
					);
					return;
				}
				
				// parse first parameter as an integer
				Integer p1 = parseAsInt(params.get(0), 0, handlers.size());
				if(p1 == null) return;
				
				// check that there are handlers to bind
				if(!handlers.isEmpty()){					
					// your parse succeeded! add handler to bound handlers list
					if(!boundHandlers.contains(handlers.get(p1))){
						boundHandlers.add(handlers.get(p1));
						System.out.printf("Handler '%d' added to bound handlers.\n", p1);	
					}
				} else {
					System.out.printf("[ERR] No handlers exist!\n");
				}	
			}
		});	
		
		// unbind handler
		commands.add(new Command("unbind", 1){
			@Override public void execute(Scanner cs){
				// get command parameters
				ArrayList<String> params = getParams(cs);

				// check that there are enough parameters
				if(params.size() < this.getParamCount()){
					System.out.printf(
						"[ERR] '%s' expects at least %d parameters\n", 
						this.toString(), this.getParamCount()
					);
					return;
				}
				
				// attempt to parse command input
				Integer p1 = parseAsInt(params.get(0), 0, boundHandlers.size());
				if(p1 == null) return;
				
				// your parse succeeded! add handler to bound handlers list
				if(!boundHandlers.isEmpty()){
					boundHandlers.remove(p1);
					System.out.printf("Handler '%d' added to bound handlers.\n", p1);
				} else {
					System.out.printf("[ERR] No bound handlers!\n");
				}
			}
		});		
		
		// remove inactive handlers
		commands.add(new Command("Prune", 0){
			@Override public void execute(Scanner cs){
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
				
				// print number of pruned handlers
				System.out.printf("Removed %d handlers\n", inactiveHandlers.size());
			}
		});
	
		// disconnect all bound handlers
		commands.add(new Command("kick", 0){
			@Override public void execute(Scanner scanner){
				// loop though all handlers and disconnect them
				System.out.printf("Forcefully disconnected %d handlers\n", boundHandlers.size());
				for(int i = 0; i < boundHandlers.size(); i++){
					boundHandlers.get(i).stop();
				}
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
	
	
	
	// method runs a command
	public static void runCommand(String line){
		Scanner lineScanner = new Scanner(line);			// create a scanner associated with this line
		String commandID = lineScanner.next();				// get command ID
		Command thisCommand = searchCommands(commandID);	// get the command associated with this ID
		
		if (thisCommand != null) thisCommand.execute(lineScanner);
		else System.out.printf("[ERR] '%s' not recognised as command\n", commandID);
	}
	
	
	
	// Main method for launching server
	public static void main(String[] args) {
		// do command-line argument parsing here
		
		// start the stammtisch server connection listener
		new StammtischServer(defaultPort);
		
		// "scanner" object for reading from command line (javawtf?)
		String line = null;
		Scanner lineScanner = new Scanner(System.in);
		
		// command handling loop
		while(!done) {
			// get console input
			line = lineScanner.nextLine();
			
			// Parse and run the command
			runCommand(line);
		}
		
		// close line scanner
		lineScanner.close();
		
		// shut down server & all worker threads
		System.exit(0);
	}
}
