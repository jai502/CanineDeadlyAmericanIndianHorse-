/*
* (C) Stammtisch
* First version created by: Alexander Cramb (ac1362)
* Date of first version: 28/01/2016
* 
* Last version by: Alexander Cramb (ac1362)
* Date of last update: 01/06/2016
* Version number: 1.3.7
* 
* Commit date: 01/06/2016
* Description: 
* 	Load bearing server for stammtisch application
*/

package server;


// our imports
import SQL.*;
import com.*;

import java.io.File;
// java imports
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.ArrayList;


public class StammtischServer {
	// path to configuration file
	public static String configFilePath = "config.txt";
	
	// Default configuration file values
	public static Integer defaultPort = 26656;
	public static Integer defaultSqlPort = 3306;
	public static Integer minTransferBlockSize = 1024;
	public static Integer maxTransferBlockSize = 1048576;
	public static Integer transferBlockSize = 32768;
	public static String defaultSqlHost = "stammtischsql.ddns.net";
	public static String dlDirPath = "presentations" + File.separator;
	
	// actual values from configuration file
	public static Integer port = 0;
	public static Integer sqlPort = 0;
	public static String sqlHost = null;
	
	// server socket factory
	public ServerSocket sSocket;
	
	// SQL connection stuff
	SQLHandler sqlInterface;

	// client request handlers
	static ArrayList<Response> responses;
	static ArrayList<Command> commands; 
	static ArrayList<ClientRequestHandler> handlers;
	static ArrayList<ClientRequestHandler> boundHandlers;
	
	// signal variable for ending server operation
	static boolean done = false;		
	static boolean defaultLoggingState = true;
	
	
	
	// server constructor. Launches connection listener thread
	public StammtischServer(int port) {
		// Indicate that server is starting
		System.out.printf("Starting server on port %d\n", port);
		
		// connect to SQL servers
		sqlInterface = new SQLHandler(sqlHost, sqlPort);

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
				System.exit(0);
			}
			
			while(true) {
				try {
					// wait for client connection & make handler
					ClientRequestHandler thisHandler = 
						new ClientRequestHandler(
							sSocket.accept(),
							nextInstance,
							responses,
							sqlInterface,
							transferBlockSize,
							defaultLoggingState
						);
					
					// add handler to handler list
					handlers.add(thisHandler);
					
					// start request handler thread
					Thread thread = new Thread(thisHandler);					
					thisHandler.log(true, "Connected");
					nextInstance++;
					thread.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	
	
	// sets up responses
	public static void setUpRequests(){
		// set up blank request array list
		responses = new ArrayList<Response>();
		responses.clear();
		
		// response to a ping
		responses.add(new Response("PING"){
			@Override public void respond(ClientRequestHandler handler){ 
				// log request
				handler.log(handler.isLogging(), "Recieved PING, responding PONG");
				try {
					handler.sendResponse(new RequestObject("PONG", null, handler.getOrder()));
				} catch (IOException e) {
					handler.logErr(handler.isLogging(), "Exception on PING reply");
					e.printStackTrace();
				}
			}
		});
		
		// response to a disconnect request
		responses.add(new Response("DISCONNECT"){
			@Override public void respond(ClientRequestHandler handler){
				// log request
				handler.log(true, "Recieved disconnect request, stopping handler");
				handler.stop();
			}
		});
	
		// respond to login request
		responses.add(new Response("REQUEST_LOGIN"){
			@Override public void respond(ClientRequestHandler handler){
				// log request
				handler.log(handler.isLogging(), "Received login request, validating credentials...");
				
				// get required objects from calling handler
				RequestObject thisRequest = handler.getCurrentRequest();
				SQLHandler SQLHandler = handler.getSQLHandler();
				
				// get the user object
				User thisUser;
				try {
					thisUser = (User)thisRequest.param;
				} catch (Exception e){
					handler.respondFail("Request object malformed");
					return;
				}
				
				// check that user exists 
				if(SQLHandler.checkLoginDetails(thisUser)){
					handler.log(handler.isLogging(), "'%s' logged in successfully, responding ok", thisUser.getUsername());
					
					// set handler user to this user
					handler.setUser(thisUser);	
					handler.respondOk(null);
				}
				
				// invalid login, respond with failure message
				else handler.respondFail("Invalid login credentials");
			}
		});
		
		// Request upload of presentation
		responses.add(new Response("REQUEST_LOGOUT"){
			@Override public void respond(ClientRequestHandler handler){
				// log request
				handler.log(handler.isLogging(), "Received logout request");
				
				User thisUser = handler.getUser();
				if (thisUser == null) {
					// failed to log out (not logged in)
					handler.respondFail("User not logged in");
					return;
				} 
				
				// log user out of server
				handler.log(handler.isLogging(), "User '%s' logged out", thisUser.getUsername());
				handler.setUser(null);
				handler.respondOk(null);
			}
		});	
		
		// respond to signup request
		responses.add(new Response("REQUEST_SIGNUP"){
			@Override public void respond(ClientRequestHandler handler){
				// log request
				handler.log(handler.isLogging(), "Recieved signup request");
				
				// get required objects from calling handler
				RequestObject thisRequest = handler.getCurrentRequest();
				SQLHandler SQLHandler = handler.getSQLHandler();
				
				// request object for response
				User thisUser = (User)thisRequest.param;
				
				// check that user doesn't already exist
				if(SQLHandler.checkUser(thisUser)) 
					handler.respondFail("Username already in use");
				
				// check that user object is valid for signup
				else if(!thisUser.validForSignup()) 
					handler.respondFail("Insufficient signup information");
				
				// Success! add user to the database & notify client
				else{
					handler.log(handler.isLogging(), "User '%s' signed up successfully", thisUser.getUsername());
					SQLHandler.addUser(thisUser);
					handler.respondOk(null);
				}
			}
		});
		
		// search request handler
		responses.add(new Response("REQUEST_SEARCH"){
			@Override public void respond(ClientRequestHandler handler){
				// log request
				handler.log(handler.isLogging(), "Received search request");
				
				// get current request and sql handler
				RequestObject thisRequest = handler.getCurrentRequest();
				SQLHandler SQLHandler = handler.getSQLHandler();
				PresentationShell presentation = null;
				
				// get the presentation object to respond with
				try {
					presentation = (PresentationShell)thisRequest.param;
				} catch (Exception e){
					handler.respondFail("Search request malformed");
					e.printStackTrace();
					return;
				}
				
				// query the presentation database
				ArrayList<String[]> searchResults = SQLHandler.searchPresentation(presentation);
				
				// generate request object
				handler.log(handler.isLogging(), "Search returned %d results", searchResults.size());
				handler.respondOk(searchResults);
			}
		});
		
		// request download of presentation
		responses.add(new Response("REQUEST_PRES"){
			@Override public void respond(ClientRequestHandler handler){
				// log request
				handler.log(handler.isLogging(), "Received presentation download request");
				
				// get current request and SQL handler
				RequestObject thisRequest = handler.getCurrentRequest();
				SQLHandler sql = handler.getSQLHandler();
				PresentationShell presentation = null;
				
				// get presentation shell object
				try {
					presentation = (PresentationShell)thisRequest.param;
				} catch (Exception e) {
					handler.respondFail("Malformed presentation metadata object");
					e.printStackTrace();
					return;
				}
				
				// mark presentation as having being accessed by the user
				sql.userFirstAccess(handler.getUser(), presentation);
				
				// get ID of the presentation that the client wants
				Integer presId = presentation.getId();
				
				// open the file for transfer
				String presPath = "presentations/" + presId.toString() + ".zip";
				String transferFailureReason = handler.sendFile(presPath);		
				
				// respond with failure of file transfer did not success
				if(transferFailureReason == null){
					handler.respondOk(null);
				} else {
					handler.respondFail(transferFailureReason);
				}
			}
		});
		
		// Request upload of presentation
		responses.add(new Response("REQUEST_UPLOAD"){
			@Override public void respond(ClientRequestHandler handler){
				// log request
				handler.log(handler.isLogging(), "Received upload request");
				
				// get current request and SQL handler
				RequestObject thisRequest = handler.getCurrentRequest();
				SQLHandler sql = handler.getSQLHandler();
				PresentationShell presentation = null;
				
				// get the presentation shell object
				try {
					presentation = (PresentationShell)thisRequest.param;
				} catch (Exception e) {
					e.printStackTrace();
					handler.respondFail("Malformed upload request");
					return;
				}
				
				// check that presentation is valid for signup
				if (!presentation.validForUpload()){
					handler.respondFail("Insufficient metadata for upload");
					return;
				}
				
				// create entry in the SQL database
				sql.addPresentation(presentation);
				Integer presId = sql.getPresId(presentation);
				
				// create the download directory if it doesn't already exist
				File dlDir = new File(dlDirPath);
				if(!dlDir.exists()) {
					try {
						dlDir.mkdirs();
					} catch (Exception e) {
						handler.respondFail("Could not create download directory");
						e.printStackTrace();
						return;
					}
				}
				
				// create the file output stream
				String presPath = dlDirPath + presId.toString() + ".zip";
				FileOutputStream fs = null;
				try {
					fs = new FileOutputStream(presPath);
				} catch (Exception e) {
					handler.respondFail("Server failed to create filestream");
					sql.removePresentation(presId);
					e.printStackTrace();
					return;
				}
				
				// tell client to continue with transfer
				handler.log(handler.isLogging(), "Presentation metadata valid for upload, informing client");
				handler.respondOk(null);
				
				// get the file
				String transferFailureReason = handler.getFile(fs);
				
				// if there was a failure, print it
				if(transferFailureReason != null) {
					sql.removePresentation(presId);
					handler.respondFail(transferFailureReason);
					sql.removePresentation(presId);
				} else {
					handler.log(handler.isLogging(), "File transfer successful!");
					// make the presentation available (upload complete)
					sql.makePresentationAvailable(presId);
					handler.respondOk(null);
				}
				
			}
		});		

		// Request upload of presentation
		responses.add(new Response("REQUEST_SET_VOTE"){
			@Override public void respond(ClientRequestHandler handler){
				// log request
				handler.log(handler.isLogging(), "Received submit vote request");
				
				// get current request and SQL handler
				RequestObject thisRequest = handler.getCurrentRequest();
				SQLHandler sql = handler.getSQLHandler();
				User thisUser = handler.getUser();
				PresentationShell presentation = null;
				Integer voteValue = null;
				
				// get the presentation object
				try {
					presentation = (PresentationShell)thisRequest.param;
				} catch (Exception e) {
					handler.respondFail("Malformed presentation metadata");
					e.printStackTrace();
				}
				
				// get the vote 
				try {
					RequestObject voteValueRequestObject = handler.getRequest();
					voteValue = (Integer)voteValueRequestObject.param;
				} catch (Exception e) {
					e.printStackTrace();
					handler.respondFail("Malformed vote request object");
				}
				
				// clamp vote value to +/- 1
				if (voteValue > 0) voteValue = 1;
				else if (voteValue < 0) voteValue = -1;
				else voteValue = 0;
				
				// send vote to SQL server
				if (!sql.setUserRating(thisUser, presentation, voteValue)) {
					handler.respondFail("Failed to apply vote");
				}
				
				// respond ok & log
				handler.respondOk(null);					
				handler.log(handler.isLogging(), 
					"User '%s' cast vote %d on %s", 
					thisUser.getUsername(),
					voteValue,
					presentation.getTitle()
				);
			}
		});	
		
		// Request upload of presentation
		responses.add(new Response("REQUEST_SET_COMMENT"){
			@Override public void respond(ClientRequestHandler handler){
				// log request
				handler.log(handler.isLogging(), "Received set comment request");
				
				// get current request and SQL handler
				RequestObject thisRequest = handler.getCurrentRequest();
				SQLHandler sql = handler.getSQLHandler();
				User thisUser = handler.getUser();
				PresentationShell presentation = null;
				String comment = null;
				
				// get the presentation object
				try {
					presentation = (PresentationShell)thisRequest.param;
				} catch (Exception e) {
					handler.respondFail("Malformed presentation metadata");
					e.printStackTrace();
				}
				
				// get the comment 
				try {
					RequestObject commentRequestObject = handler.getRequest();
					comment = (String)commentRequestObject.param;
				} catch (Exception e) {
					e.printStackTrace();
					handler.respondFail("Malformed comment request object");
				}
				
				// sendvoteValue vote to SQL server
				int presId = sql.getPresId(presentation);
				sql.addComment(presId, thisUser, comment);
				
				// respond ok & log
				handler.respondOk(null);					
				handler.log(handler.isLogging(), 
					"User '%s' commented on '%s'",
					thisUser.getUsername(),
					presentation.getTitle()
				);
			}
		});	
		
		// Request upload of presentation
		responses.add(new Response("REQUEST_GET_COMMENTS"){
			@Override public void respond(ClientRequestHandler handler){
				// log request
				handler.log(handler.isLogging(), "Received recall comment request");
				
				// get the currentRequest & sql handler
				RequestObject thisRequest = handler.getCurrentRequest();
				SQLHandler sql = handler.getSQLHandler();
				PresentationShell presentation = null;
				ArrayList<String[]> comments = null;
				
				// get the presentation shell object
				try {
					presentation = (PresentationShell)thisRequest.param;
				} catch (Exception e) {
					handler.respondFail("Malformed presentation shell object");
					e.printStackTrace();
				}
				
				// get the comments from the SQL server
				int presId = sql.getPresId(presentation);
				comments = sql.searchComments(presId);
				handler.log(handler.isLogging(), "Found %d comments for presentation '%s'", comments.size(), presentation.getTitle());
				
				// respond OK with the arraylist of comments
				handler.respondOk((Object)comments);
			}
		});
		
		// Request upload of presentation
		responses.add(new Response("REQUEST_GET_VOTE"){
			@Override public void respond(ClientRequestHandler handler){
				// log request
				handler.log(handler.isLogging(), "Received recall vote request");
				
				// get the currentRequest
				RequestObject currentRequest = handler.getCurrentRequest();
				
				handler.respondFail(String.format("%s not implemented!", currentRequest.id));
			}
		});	
	}
	
	
	
	
	
	
	
	// display data for all connected clients
	public static void printHandlerList(ArrayList<ClientRequestHandler> handler){
		// if no clients are connected, indicate this
		if(handler.size() == 0){
			System.out.printf("No handlers in list!\n");
		} else { 
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
		try {
			p1int = Integer.parseInt(p1);
		} catch (NumberFormatException e){
			System.out.printf("'%s' invalid, expected integer.\n", p1);
			return null;
		}
		
		// if the number is not in expected range
		if ((p1int < min) || (p1int > max)){
			System.out.printf("'%d' out of range.\n", p1int);
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
		// Usage string
		String desc, usage;
		
		// clear commands
		commands = new ArrayList<Command>();
		commands.clear();
		
		// stop command
		desc = "\tstops the server";
		usage = "\t [stop] - no parameters";
		commands.add(new Command("stop", 0, desc, usage){
			@Override public void execute(Scanner cs){
				// some code here
				System.out.printf("Stopping stammtisch server.\n");
				done = true;
			}
		});
		
		// list handlers
		desc = "\tlists all currently running handlers";
		usage = "\t[lh] - no parameters";
		commands.add(new Command("lh", 0, desc, usage){
			@Override public void execute(Scanner cs){
				// prune the handler list
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
				
				// print out handler list
				System.out.printf("%d running request handlers:\n", handlers.size());
				printHandlerList(handlers);
			}
		});
	
		// list bound handlers
		desc = "\tlists all currently bound handlers";
		usage = "\t[lbh] - no parameters";
		commands.add(new Command("lbh", 0, desc, usage){
			@Override public void execute(Scanner cs){
				// prune the handler list
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
				
				// print out handler list
				System.out.printf("%d bound client request handlers:\n", boundHandlers.size());
				printHandlerList(boundHandlers); 
			}
		});		
		
		// bind a handler
		desc = "\tadds a handler to the list of bound handlers";
		usage = "\t[bind <handler index>] - find handler index with 'lh', or use 'all'";
		commands.add(new Command("bind", 1, desc, usage){
			@Override public void execute(Scanner cs){
				// get command parameters
				ArrayList<String> params = getParams(cs);
				
				// check that there are enough parameters
				if(params.size() < this.getParamCount()){
					System.out.printf(
						"'%s' expects at least %d parameters\n", 
						this.toString(), this.getParamCount()
					);
					return;
				}
				
				// bind all handlers
				if(params.get(0).equalsIgnoreCase("all")){
					// clear bound handlers
					boundHandlers.clear();
					
					// add all handlers to bound handler list
					for(int i = 0; i < handlers.size(); i++)
						boundHandlers.add(handlers.get(i));
					
					// print results of command to console
					System.out.printf("Bound %d handlers\n", boundHandlers.size());
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
					System.out.printf("No handlers exist!\n");
				}	
			}
		});	
		
		// unbind handler
		desc = "removes a handler from the list of currently bound handlers";
		usage = "\t[unbind <bound handler index>] - find handler index with 'lh' or use 'all'";
		commands.add(new Command("unbind", 1, desc, usage){
			@Override public void execute(Scanner cs){
				// get command parameters
				ArrayList<String> params = getParams(cs);

				// check that there are enough parameters
				if(params.size() < this.getParamCount()){
					System.out.printf(
						"'%s' expects at least %d parameters\n", 
						this.toString(), this.getParamCount()
					);
					return;
				}
				
				// if parameter is all, unbind all handlers
				if(params.get(0).equalsIgnoreCase("all")){
					// clear bound handlers
					System.out.printf("Unbound %d handlers\n", boundHandlers.size());
					boundHandlers.clear();
					return;
				}
				
				// attempt to parse command input
				Integer p1 = parseAsInt(params.get(0), 0, boundHandlers.size());
				if(p1 == null) return;
				
				// your parse succeeded! add handler to bound handlers list
				if(!boundHandlers.isEmpty()){
					boundHandlers.remove(p1);
					System.out.printf("Bound handler '%d' removed from bound handlers\n", p1);
				} else {
					System.out.printf("No bound handlers!\n");
				}
			}
		});		
		
		// remove inactive handlers
		desc = "\tremoves inactive handlers from the handler lists";
		usage = "\t[prune] - no parameters";
		commands.add(new Command("prune", 0, desc, usage){
			@Override public void execute(Scanner cs){
				// list of inactive handlers
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
		desc = "\tforcefully kicks handlers";
		usage = "\t[kick <handler index>] - use 'lh' to get handler index, or use 'all'/'bound'";
		commands.add(new Command("kick", 1, desc, usage){
			@Override public void execute(Scanner scanner){
				// get command parameters
				ArrayList<String> params = getParams(scanner);

				// check that there are enough parameters
				if(params.size() < this.getParamCount()){
					System.out.printf(
						"'%s' expects at least %d parameters\n", 
						this.toString(), this.getParamCount()
					);
					return;
				}
				
				// kick all handlers
				if(params.get(0).equalsIgnoreCase("all")){
					// loop though all handlers and disconnect them
					System.out.printf("Forcefully disconnected %d handlers\n", handlers.size());
					for(int i = 0; i < handlers.size(); i++){
						handlers.get(i).stop();
						handlers.get(i).setUser(null);
						handlers.get(i).setBlock(true);
					}
					return;
				} 
				
				// bound handlers
				if (params.get(0).equalsIgnoreCase("bound")){
					// loop though all handlers and disconnect them
					System.out.printf("Forcefully disconnected %d handlers\n", boundHandlers.size());
					for(int i = 0; i < boundHandlers.size(); i++){
						boundHandlers.get(i).stop();
						boundHandlers.get(i).setUser(null);
						boundHandlers.get(i).setBlock(true);
					}
					return;
				}
				
				// else, treat command as kicking a specific handler
				// parse first parameter as an integer
				Integer p1 = parseAsInt(params.get(0), 0, handlers.size());
				if(p1 == null) return;
				
				// kick this handler
				ClientRequestHandler handlerToKick = handlers.get(p1);
				handlerToKick.stop();
				handlerToKick.setUser(null);
				handlerToKick.setBlock(true);
			}
		});
		
		// disconnect all bound handlers
		desc = "sets block size (in bytes) to be used for file transfers";
		usage = "\t[blockSize <size of blocks in bytes>]";
		commands.add(new Command("blockSize", 1, desc, usage){
			@Override public void execute(Scanner cs){
				// get command parameters
				ArrayList<String> params = getParams(cs);

				// check that there are enough parameters
				if(params.size() < this.getParamCount()){
					System.out.printf(
						"'%s' expects at least %d parameters\n", 
						this.toString(), this.getParamCount()
					);
					return;
				}
				
				// attempt to parse command input
				Integer p1 = parseAsInt(
					params.get(0), 
					minTransferBlockSize, maxTransferBlockSize
				);
				
				// didn't parse the integer properly
				if(p1 == null) return;
				
				// your parse succeeded! add handler to bound handlers list
				if(!handlers.isEmpty()){
					// loop through all bound handlers setting transfer size
					for (int i = 0; i < handlers.size(); i++){
						boundHandlers.get(i).setTransferBlockSize(p1);
					}
				}
				
				// print out new block size
				transferBlockSize = p1;
				System.out.printf("Set transfer block size to %d bytes\n", p1);
			}
		});
		
		// do handler logging
		desc = "sets the command line logging status of running handlers";
		usage = "\t[doLogging <handler index> <status>]\n"
			  + "\t\t - find handler index using 'lh' or use 'all/bound' \n"
			  + "\t\t - status can take the value 'true' or 'false'";
		commands.add(new Command("dologging", 2, desc, usage){
			@Override public void execute(Scanner scanner){
				// get command parameters
				ArrayList<String> params = getParams(scanner);

				// check that there are enough parameters
				if(params.size() < this.getParamCount()){
					System.out.printf(
						"'%s' expects at least %d parameters\n", 
						this.toString(), this.getParamCount()
					);
					return;
				}
				
				// use second argument to determine how to set logging status
				boolean newLoggingStatus;
				String p2 = params.get(1);
				if (p2.equalsIgnoreCase("false") || p2.equalsIgnoreCase("0")) {
					newLoggingStatus = false;
				} else if (p2.equalsIgnoreCase("true") || p2.equalsIgnoreCase("1")) {
					newLoggingStatus = true;
				} else {
					System.out.printf("'%s' invalid, expected [true/false], [1/0]\n", p2);
					return;
				}
				
				// set logging status of all handlers
				if(params.get(0).equalsIgnoreCase("all")){
					// loop though all handlers and disconnect them
					System.out.printf("Set logging status for %d handlers\n", handlers.size());
					for(int i = 0; i < handlers.size(); i++){
						handlers.get(i).doLogging(newLoggingStatus);
					}
					return;
				} 
				
				// set logging status of bound handlers only 
				if (params.get(0).equalsIgnoreCase("bound")){
					// loop though all handlers and disconnect them
					System.out.printf("Set logging status for %d handlers\n", boundHandlers.size());
					for(int i = 0; i < boundHandlers.size(); i++){
						boundHandlers.get(i).doLogging(newLoggingStatus);
					}
					return;
				}
				
				// parse first parameter as an integer
				Integer p1 = parseAsInt(params.get(0), 0, handlers.size() - 1);
				if(p1 == null) return;
				
				// set logging status of just this handler
				ClientRequestHandler thisHandler = handlers.get(p1);
				System.out.printf("Set logging status for 1 handlers\n");
				thisHandler.doLogging(newLoggingStatus);
			}
		});
		
		// server help command
		desc = "\tdisplays help information for server commands";
		usage = "\t[help] or [help <name of command>]";
		commands.add(new Command("help", 0, desc, usage){
			@Override public void execute(Scanner cs){
				// get command parameters
				ArrayList<String> params = getParams(cs);
				Command command = null;
				
				// if params is not empty then display usage of listed command
				if(params.size() > 0){
					// get the command for which the help command was issued
					command = searchCommands(params.get(0));
					
					// if command is null then there is no command with this name
					if(command == null){
						// notify user that this command does not exist
						System.out.printf("No such command! %s", params.get(0));
					} else {
						// print out usage and help of selected command
						command.displayDesc();
						command.displayUsage();
					}
				} else {
					// loop through all server commands, displaying their usage strings 
					System.out.printf(
						"====[SERVER COMMANDS]==================================================\n");
					for(int i = 0; i < commands.size(); i++){
						commands.get(i).displayDesc();
					}
					System.out.printf(
						"=======================================================================\n");
					System.out.printf(
						"For usage information on a specific command use [help <command name>]\n");
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
		else System.out.printf("'%s' is not a recognised command!\n", commandID);
	}
	
	
	
	// loads configuration file
	public static void loadConfig(String path){
		Scanner fs = null;			// file scanner
		String line;				// line from file
		ArrayList<String> lineData;	// line data
	
		//set default values
		// these will be overridden with config file values if they are found to exist
		port = defaultPort;
		sqlPort = defaultSqlPort;
		sqlHost = defaultSqlHost;
		
		// open the file and check that it exists
		File cfgFile = new File(path);
		if (!cfgFile.exists()) {
			// create configuration file with default values
			PrintWriter fos;
			try {
				fos = new PrintWriter(cfgFile);
			} catch (FileNotFoundException e) {
				System.out.printf("[ERR] Could not open file output stream for writing: %s", path);
				e.printStackTrace();
				return;
			}
			
			// write default values to file
			fos.println("serverPort: " + defaultPort);
			fos.println("sqlPort: " + defaultSqlPort);
			fos.println("sqlHost: " + defaultSqlHost);
			
			// print out that default file was created
			System.out.printf("%s not found, creating file with default values\n", path);
			
			// close stream and return
			fos.close();
			return;
		}
		
		// anything after here runs only if the file was found
		
		// open the file
		FileInputStream fis;
		try {
			fis = new FileInputStream(path);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		// parse the config file
		fs = new Scanner(fis);
		
		// while
		while(fs.hasNextLine()){
			line = fs.nextLine();						// get line from the file
			lineData = getParams(new Scanner(line));	// get space delimited parameters
			
			// handle line
			switch(lineData.get(0)){
				// server port parameter
				case "serverPort:":
					if (lineData.size() < 2) break;							// No data in line
					Integer value = parseAsInt(lineData.get(1), 0, 65535);	// Get the server port
					if(value != null) {										// Integer parse succeeded, set value
						port = value;
						System.out.printf("Server port read from config file: %d\n", port);
					} break;
				
				// sql port parameter
				case "sqlPort:":
					if (lineData.size() < 2) break;							// no data on line
					value = parseAsInt(lineData.get(1), 0, 65535);			// get the server port
					if(sqlPort != null) {									// Integer parse succeeded, set value
						sqlPort = value;
						System.out.printf("SQL server port read from config file: %d\n", sqlPort);
					} break;
				
				// SQL server host parameter
				case "sqlHost:":
					if (lineData.size() < 2) break;							// no data on line
					sqlHost = lineData.get(1);
					System.out.printf("Server host read from config file: %s\n", sqlHost);
					break;
					
				// line tag not recognised
				default:
					System.out.printf("Unrecognised config file tag: %s\n", lineData.get(0));
					break;
			}
		}
		
		// no resource leaks, thank you!
		fs.close();
	}
	
	
	
	// Main method for launching server
	public static void main(String[] args) {
		// configuration file load would happen here
		loadConfig(configFilePath);
		
		// start the stammtisch server connection listener
		new StammtischServer(port);
		
		// "scanner" object for reading from command
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
