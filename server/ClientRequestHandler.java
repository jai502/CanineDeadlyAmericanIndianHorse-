package server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientRequestHandler implements Runnable {
	
	private Socket clientSocket;	    // socket for this client connection
	private int handlerInstance;		// unique number associated with client
	boolean done = false;			    // main loop complete
	boolean bound = false;
	int order;
	
	
	// constructor for client request handler
	public ClientRequestHandler(Socket thisSocket, int thisInstance){
		clientSocket = thisSocket;		 // client socket
		handlerInstance = thisInstance;  // instance number
	}
	
	
	// client request handler thread code
	@Override
	public void run() {
		// Thread local variables
		RequestObject currentRequest = null;
		
		// test file for transmission
		// String testFile = new String("M:/w2k/My Pictures/norgate.gif");
		
		while (!done) {
			// wait for request from client
			try {
				currentRequest = getRequest(clientSocket);
			} catch(IOException e) {
				if(bound)System.out.printf("[H-%d][ERR] Client disconnected unusually \n", handlerInstance);
				e.printStackTrace();
				break;
			}
			
			// take order number for current request
			order = currentRequest.order;
			
			switch(currentRequest.id.toString()) {
				case "PING":		// ping command
					sendResponse(clientSocket, "PONG", null);
					break;

				case "DISCONNECT":	// disconnect request
					done = true;	// exit handler loop
					break;
					
				case "CREATE_ACCOUNT":
					break;
					
				case "REQUEST_LOGIN":
					break;
					
				case "SEARCH_PRESENTATIONS":
					break;
				
				case "LOAD_PRESENTATIONS":
					break;
					
				case "VOTE_PRESENTATION":
					break;
				
				default:			// unrecognised request
					sendResponse(clientSocket, "RESPONSE_UNKNOWN", null);
					// print to console stream
					break;
			}
		}
		
		// disconnect socket
		if(bound)System.out.printf("[H-%d] Exiting service thread \n", handlerInstance);
		try {
			clientSocket.close();
		} catch (IOException e) {
			if(bound)System.out.printf("[H-%d][ERR] Exception on socket close \n", handlerInstance);
			e.printStackTrace();
		}
	}
	
	
	// Thread local request retrieval method
	private RequestObject getRequest(Socket threadSocket) throws IOException {
		ObjectInputStream inputFromClient;
		RequestObject thisRequest;
		try {
			inputFromClient = new ObjectInputStream(threadSocket.getInputStream());
			thisRequest = (RequestObject)inputFromClient.readObject();
			if(bound)System.out.printf("[H-%d] Request recieved: %s\n", handlerInstance, thisRequest.id);
			return thisRequest;
		} catch (ClassNotFoundException e) {
			if(bound)System.out.printf("[H-%d][ERR] Request is of wrong format\n", handlerInstance);
			e.printStackTrace();			
		}
		return null;
	}
	
	
	// method for sending a response to the client
	private void sendResponse(Socket socket, String id, Object param) {
		// instantiate request object
		if(bound)System.out.printf("[H-%d] Sending response: %s\n", handlerInstance, id);
		RequestObject thisRequest = new RequestObject(id, param, order);
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(thisRequest);
			outputStream.flush();
		} catch(IOException e) {
			if(bound)System.out.printf("[H-%d][ERR] Failed to send respond (IOException)", handlerInstance);
			e.printStackTrace();
		} 
	}


	// return this handler instance
	public int getNum() {return handlerInstance;}
	
	
	// get ip address associated with this client request handler
	public String getIp(){return clientSocket.getLocalAddress().toString();}
	
	
	// get thread number associated with this request handler
	public int getReqCount(){return order;}
	

	// get info string
	public String getInfoString(){
		return String.format(
			"th: %d, ip: %s, reqs: %d",
			getNum(),
			getIp(),
			getReqCount()
		);
	}
	

	// returns true if request handler is running
	public boolean isDone() {return done;}
	
	
	// returns true if handler is bound
	public boolean isBound() {return bound;}
	
	
	// bind handler
	public void bind() {bound = true;}
	
	
	// unbind handler
	public void unbind() {bound = false;}
}
