package server;


// java imports
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


// our imports
import com.*;
import SQL.*;


public class ClientRequestHandler implements Runnable {
	private ArrayList<String> preLoginReqs;
	private RequestObject currentRequest;
	private User user;
	private SQLHandler sql;
	private Socket clientSocket;	    // socket for this client connection
	private int handlerInstance;		// unique number associated with client
	boolean done = false;			    // main loop complete
	boolean threadDone = false;			// thread is still running
	int order;
	
	// list of request handling objects
	ArrayList<Response> responses;
	
	
	// constructor for client request handler
	public ClientRequestHandler(Socket thisSocket, int thisInstance, ArrayList<Response> responses, SQLHandler sql){
		clientSocket = thisSocket;		 // client socket
		handlerInstance = thisInstance;  // instance number
		this.responses = responses;
		this.sql = sql;
		
		// create list of allowable pre-login requests
		preLoginReqs = new ArrayList<String>();
		preLoginReqs.clear();
		
		// allowable pre-login requests
		preLoginReqs.add(new String("PING"));
		preLoginReqs.add(new String("DISCONNECT"));
		preLoginReqs.add(new String("REQUEST_LOGIN"));
		preLoginReqs.add(new String("REQUEST_SIGNUP"));
	}
	
	
	
	// client request handler thread code
	@Override
	public void run() {
		// test file for transmission
		//String testFile = new String("M:/w2k/My Pictures/norgate.gif");
		
		while (!done) {
			// wait for request from client
			try {
				currentRequest = getRequest(clientSocket);
			} catch(IOException e) {
				System.out.printf("[H-%d][ERR] Exception on socket recieve \n", handlerInstance);
				e.printStackTrace();
				done = true;
				break;
			}
			
			// take order number for current request
			order = currentRequest.order;
			
			// search for response in response list
			Response currentResponse = searchResponses(currentRequest.id);
			System.out.printf("[H-%d] Got request %s\n", handlerInstance, currentRequest.id);
			
			if(currentResponse != null){
				// if the user has not logged in, log them in
				if((user != null) || reqAllowed(currentRequest.id)){
					// respond to request
					currentResponse.respond(this);
				} else {
					sendResponse(new RequestObject("RESPONSE_FAIL", new String("login first you little shit"), order));
				}
			} else {
				System.out.printf("[H-%d][ERR] Unrecognised request '%s'\n", handlerInstance, currentRequest.id);
				sendResponse(new RequestObject("RESPONSE_UNKNOWN", currentRequest.id, order));
			}
		}
		
		// disconnect socket
		try {
			clientSocket.close();
		} catch (IOException e) {
			System.out.printf("[H-%d][ERR] Exception on socket close \n", handlerInstance);
			e.printStackTrace();
		}
		
		// indicate that thread is no longer running
		threadDone = true;
	}
	
	
	
	// method returns true of string matches any allowable pre-login requests
	private boolean reqAllowed(String req){
		// loop through all allowable pre-login requests
		for(int i = 0; i < preLoginReqs.size(); i++)
			if(preLoginReqs.get(i).equals(req))
				return true;
		
		// if a matching allowed request string is not found, return false
		return false;
	}
	
	
	
	// Thread local request retrieval method
	private RequestObject getRequest(Socket threadSocket) throws IOException {
		ObjectInputStream inputFromClient;
		RequestObject thisRequest;
		try {
			inputFromClient = new ObjectInputStream(threadSocket.getInputStream());
			thisRequest = (RequestObject)inputFromClient.readObject();
			return thisRequest;
		} catch (ClassNotFoundException e) {
			System.out.printf("[H-%d][ERR] Request is of wrong format\n", handlerInstance);
			e.printStackTrace();			
		}
		return null;
	}
	
	
	
	// method for sending a response to the client
	public void sendResponse(RequestObject thisRequest) {
		// instantiate request object
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			outputStream.writeObject(thisRequest);
			outputStream.flush();
		} catch(IOException e) {
			System.out.printf("[H-%d][ERR] Failed to send response (IOException)", handlerInstance);
			e.printStackTrace();
		} 
	}
	
	
	
	// send OK acknowledgement response
	public void respondOk(Object param){
		// request object to return
		RequestObject thisRequest = new RequestObject(
			"RESPONSE_OK",
			param,
			order
		);
		
		// send response
		sendResponse(thisRequest);
	}
	
	
	
	// send FAIL acknowledgement response
	public void respondFail(Object param){
		// request object to return
		RequestObject thisRequest = new RequestObject(
			"RESPONSE_FAIL",
			param,
			order
		);
		
		// send response
		sendResponse(thisRequest);		
	}
	
	
	
	// searches for request in response list
	public Response searchResponses(String requestId){
		// loop through all responses, look for appropriate ID
		for(int i = 0; i < responses.size(); i++)
			if (requestId.equals(responses.get(i).getRequestId()))
				return responses.get(i);
		
		// if response isn't found, return null
		return null;
	}
	

	
	// get info string
	public String getInfoString(){
		return String.format(
			"Handler: %d, ip: %s, reqs: %d, running: %b",
			getNum(),
			getIp(),
			getReqCount(),
			!threadDone
		);
	}
	
	
	
	// return this handler instance number
	public int getNum() {return handlerInstance;}
	
	// get ip address associated with this client request handler
	public String getIp(){return clientSocket.getRemoteSocketAddress().toString();}
	
	// get thread number associated with this request handler
	public int getReqCount(){return order;}

	// function to stop the handler
	public void stop(){done = true;}
	
	// returns true if request handler is running
	public boolean isDone() {return threadDone;}
	
	// returns the socket to the caller (not used very often)
	public Socket getSocket() {return clientSocket;} 
	
	// returns current request number
	public int getOrder() {return order;}
	
	// getter for current request object
	public RequestObject getCurrentRequest() {return currentRequest;}
	
	// getter for this handlers SQL interface
	public SQLHandler getSQLHandler() {return sql;}
	
	// setter for logging in a user
	public void setUser(User user) {this.user = user;}
}
