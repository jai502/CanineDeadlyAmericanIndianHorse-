package server;


// java imports
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


// our imports
import com.*;


public class ClientRequestHandler implements Runnable {
	
	private Socket clientSocket;	    // socket for this client connection
	private int handlerInstance;		// unique number associated with client
	boolean done = false;			    // main loop complete
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
				System.out.printf("[H-%d][ERR] Exception on socket recieve \n", handlerInstance);
				e.printStackTrace();
				done = true;
				break;
			}
			
			// take order number for current request
			order = currentRequest.order;
			System.out.printf("[H-%d] Recieved: %s\n", handlerInstance, currentRequest.id);
			
			switch(currentRequest.id.toString()) {
				case "PING":		// ping command
					sendCommand(new RequestObject("PONG", null, order));
					break;

				case "DISCONNECT":	// disconnect request
					done = true;	// exit handler loop
					break;
					
				case "REQUEST_LOGIN":
					break;
				
				default:			// unrecognised request
					sendCommand(new RequestObject("RESPONSE_UNKNOWN", new String(currentRequest.id.toString()), order));
					// print to console stream
					break;
			}
		}
		
		// disconnect socket
		try {
			clientSocket.close();
		} catch (IOException e) {
			System.out.printf("[H-%d][ERR] Exception on socket close \n", handlerInstance);
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
			return thisRequest;
		} catch (ClassNotFoundException e) {
			System.out.printf("[H-%d][ERR] Request is of wrong format\n", handlerInstance);
			e.printStackTrace();			
		}
		return null;
	}
	
	
	
	// method for sending a response to the client
	public void sendCommand(RequestObject thisRequest) {
		// instantiate request object
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			outputStream.writeObject(thisRequest);
			outputStream.flush();
		} catch(IOException e) {
			System.out.printf("[H-%d][ERR] Failed to send respond (IOException)", handlerInstance);
			e.printStackTrace();
		} 
	}


	
	// return this handler instance
	public int getNum() {return handlerInstance;}
	
	
	// get ip address associated with this client request handler
	public String getIp(){return clientSocket.getRemoteSocketAddress().toString();}
	
	
	// get thread number associated with this request handler
	public int getReqCount(){return order;}
	

	// get info string
	public String getInfoString(){
		return String.format(
			"Handler: %d, ip: %s, reqs: %d, running: %b",
			getNum(),
			getIp(),
			getReqCount(),
			!done
		);
	}
	

	// returns true if request handler is running
	public boolean isDone() {return done;}
}
