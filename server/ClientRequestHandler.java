package server;


//import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
//import java.io.InputStreamReader;
//import java.net.ServerSocket;
import java.net.Socket;


public class ClientRequestHandler implements Runnable {

	private Socket clientSocket;
	//private int localStream;
	boolean done = false;
	
	
	
	// constructor for client request handler
	public ClientRequestHandler(Socket thisSocket){
		clientSocket = thisSocket;
	}
	
	
	@Override
	public void run() {
		// Thread local variables
		RequestObject currentRequest = null;
		
		while (!done) {
			System.out.printf("[INFO] Waiting for data from client \n");
			currentRequest = getRequest(clientSocket);
			
			switch(currentRequest.id) {
				case "DISCONNECT":
					System.out.printf("[INFO] Exiting service thread \n");
					// exit loop
					done = true;
				
				default:
					System.out.printf("[INFO] Request ''%s'' not recognised \n", currentRequest.id);
					break;
			}
		}
		
		try {
			clientSocket.close();
		} catch (IOException e) {
			System.out.printf("[ERROR] Failed to close thread socket \n");
			e.printStackTrace();
		}
	}
	
	
	
	// Thread local request retrieval method
	private RequestObject getRequest(Socket threadSocket) {
		ObjectInputStream inputFromClient;
		RequestObject thisRequest;
		try {
			inputFromClient = new ObjectInputStream(threadSocket.getInputStream());
			thisRequest = (RequestObject) inputFromClient.readObject();
			System.out.printf("[INFO] Request recieved: %s\n", thisRequest.id);
			return thisRequest;
		} catch (IOException e) {
			System.out.printf("[ERROR] Failed to get request\n");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.printf("[ERROR] Request is of wrong format\n");
			e.printStackTrace();			
		}
		return null;
	}
}
