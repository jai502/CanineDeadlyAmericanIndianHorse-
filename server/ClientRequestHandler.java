package server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientRequestHandler implements Runnable {
	
	private Socket clientSocket;	    // socket for this client connection
	private int handlerInstance;		// unique number associated with client
	boolean done = false;			    // main loop complete
	
	
	
	// constructor for client request handler
	public ClientRequestHandler(Socket thisSocket, int thisInstance){
		clientSocket = thisSocket;		 // client socket
		handlerInstance = thisInstance;  // instance number
	}
	
	
	
	@Override
	public void run() {
		// Thread local variables
		RequestObject currentRequest = null;
		
		// test file for transmission
		// String testFile = new String("M:/w2k/My Pictures/norgate.gif");
		
		while (!done) {
			// wait for request from client
			currentRequest = getRequest(clientSocket);
			
			switch(currentRequest.id.toString()) {
				case "PING":		// ping command
					sendResponse(clientSocket, "PONG", null);
					break;
					
				case "PONG":
					sendResponse(clientSocket, "NO, JUST NO.", null);
					break;

				case "DISCONNECT":	// disconnect request
					System.out.printf("[INFO]-%d Exiting service thread \n", handlerInstance);
					done = true;	// exit handler loop
					break;
					
				default:			// unrecognised request
					sendResponse(clientSocket, "RESPONSE_UNKNOWN", null);
					// print to console stream
					break;
			}
		}
		
		// disconnect socket
		try {
			clientSocket.close();
		} catch (IOException e) {
			System.out.printf("[ERROR]-%d Exception on socket close \n", handlerInstance);
			e.printStackTrace();
		}
	}
	
	
	
	// Thread local request retrieval method
	private RequestObject getRequest(Socket threadSocket) {
		ObjectInputStream inputFromClient;
		RequestObject thisRequest;
		try {
			inputFromClient = new ObjectInputStream(threadSocket.getInputStream());
			thisRequest = (RequestObject)inputFromClient.readObject();
			System.out.printf("[INFO]-%d Request recieved: %s\n", handlerInstance, thisRequest.id);
			return thisRequest;
		} catch (IOException e) {
			System.out.printf("[ERROR]-%d Failed to get request\n", handlerInstance);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.printf("[ERROR]-%d Request is of wrong format\n", handlerInstance);
			e.printStackTrace();			
		}
		return null;
	}
	
	
	
	// method for sending a response to the client
	private void sendResponse(Socket socket, String id, Object param) {
		// instantiate request object
		System.out.printf("[INFO]-%d Sending response: %s\n", handlerInstance, id);
		RequestObject thisRequest = new RequestObject(id, param);
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(thisRequest);
			outputStream.flush();
		} catch(IOException e) {
			System.out.printf("[ERROR]-%d Failed to send respond (IOException)", handlerInstance);
			e.printStackTrace();
		} 
	}
}
