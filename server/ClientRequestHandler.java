package server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientRequestHandler implements Runnable {
	
	private Socket clientSocket;	    // socket for this client connection
	private int handlerInstance;		// unique number associated with client
	boolean done = false;			    // main loop complete
	int requestNumber;
	
	
	
	// constructor for client request handler
	public ClientRequestHandler(Socket thisSocket, int thisInstance){
		clientSocket = thisSocket;		 // client socket
		handlerInstance = thisInstance;  // instance number
		requestNumber = 0;
	}
	
	
	
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
				System.out.printf("[ERROR]-%d Client disconnected unusually \n", handlerInstance);
				e.printStackTrace();
				break;
			}
			
			switch(currentRequest.id.toString()) {
				case "PING":		// ping command
					sendResponse(clientSocket, "PONG", null);
					break;
					
				case "CREATE_ACCOUNT":
					break;
					
				case "REQUEST_LOGIN":
					break;

				case "DISCONNECT":	// disconnect request
					done = true;	// exit handler loop
					break;
				
				default:			// unrecognised request
					sendResponse(clientSocket, "RESPONSE_UNKNOWN", null);
					// print to console stream
					break;
			}
		}
		
		// disconnect socket
		System.out.printf("[INFO]-%d Exiting service thread \n", handlerInstance);
		try {
			clientSocket.close();
		} catch (IOException e) {
			System.out.printf("[ERROR]-%d Exception on socket close \n", handlerInstance);
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
			System.out.printf("[INFO]-%d Request recieved: %s\n", handlerInstance, thisRequest.id);
			return thisRequest;
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
		RequestObject thisRequest = new RequestObject(id, param, requestNumber);
		requestNumber++;
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
