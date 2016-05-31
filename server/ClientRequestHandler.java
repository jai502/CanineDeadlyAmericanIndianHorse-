/*
* (C) Stammtisch
* First version created by: Alexander Cramb (ac1362)
* Date of first version: 28/01/2016
* 
* Last version by: Alexander Cramb (ac1362)
* Date of last update: 30/05/2016
* Version number: 1.5.12
* 
* Commit date: 28/05/2016
* Description: 
* 	Client request handler
*/


package server;


//java imports
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
	private boolean blocked = false;			// user is allowed to make requests
	private boolean done = false;			    // main loop complete
	private boolean threadDone = false;			// thread running
	private boolean doLogging = false;
	private int transferBlockSize;
	private int order;
	
	// input and output streams
	ObjectOutputStream outputStream;
	ObjectInputStream inputStream;
	
	// list of request handling objects
	ArrayList<Response> responses;
	
	
	// constructor for client request handler
	public ClientRequestHandler(Socket thisSocket, int thisInstance, ArrayList<Response> responses, SQLHandler sql, int blockSize){
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
		
		// get transfer block size
		this.transferBlockSize = blockSize;
	}
	
	
	
	// client request handler thread code
	@Override
	public void run() {
		// open the output stream
		try {
			outputStream = new ObjectOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
			outputStream.flush();
		} catch (Exception e) {
			logErr(doLogging, "Failed to open output stream! Handler terminating.");
			e.printStackTrace();
			done = true;
		}
		
		// open the input stream
		try {
			inputStream = new ObjectInputStream(new BufferedInputStream(clientSocket.getInputStream()));
		} catch (Exception e) {
			logErr(doLogging, "Failed to open input stream! Handler terminating.");
			e.printStackTrace();
			done = true;
		}
		
		// main loop
		while (!done) {
			// wait for request from client
			currentRequest = null;
			while(currentRequest == null){
				try {
					currentRequest = getRequest();
				} catch (Exception e) {
					logErr(doLogging, "Exception on request recieve");
					e.printStackTrace();
					done = true;
					break;
				}
				if (done) break;
			}
			if (done) break;
			
			// take order number for current request
			order = currentRequest.order;
			
			// search for response in response list
			Response currentResponse = searchResponses(currentRequest.id);
			
			if(currentResponse != null){
				if(reqAllowed(currentRequest.id, user) && !blocked){
					// respond to request
					currentResponse.respond(this);
				} else {
					if(blocked) respondFail("Permission denied: request blocked");
					else respondFail("Permission denied: not logged in");
				}
			} else {
				respondFail(String.format("'%s' not recongised", currentRequest.id));
			}
		}
		
		// disconnect socket
		try {
			clientSocket.close();
		} catch (IOException e) {
			logErr(doLogging, "Exception on socket close");
			e.printStackTrace();
		}
		
		// indicate that thread is no longer running
		threadDone = true;
	}
	
	
	
	// method returns true of string matches any allowable pre-login requests
	private boolean reqAllowed(String req, User user){
		// if user is logged in, allow the request
		if (user != null) return true;
		
		// loop through all allowable pre-login requests
		for(int i = 0; i < preLoginReqs.size(); i++)
			if(preLoginReqs.get(i).equals(req))
				return true;
		
		// if a matching allowed request string is not found, return false
		return false;
	}
	
	
	
	// Thread local request retrieval method
	public RequestObject getRequest() throws IOException {
		RequestObject thisRequest;
		try {
			thisRequest = (RequestObject)inputStream.readObject();
			return thisRequest;
		} catch (ClassNotFoundException e1) {
			logErr(doLogging, "Malformed request object recieved, parameter cast failure");
			e1.printStackTrace();
		}
		return null;
	}
	
	
	
	// method for sending a response to the client
	public void sendResponse(RequestObject thisRequest) throws IOException {
		// reset socket, send request object
		outputStream.reset();
		outputStream.writeObject(thisRequest);
		outputStream.flush();
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
		try {
			sendResponse(thisRequest);
		} catch (IOException e) {
			logErr(doLogging, "Exception respoding 'RESPONSE_OK'");
			e.printStackTrace();
		}
	}
	
	
	
	// send FAIL acknowledgement response
	public void respondFail(String reason){
		// request object to return
		RequestObject thisRequest = new RequestObject(
			"RESPONSE_FAIL",
			reason,
			order
		);
		
		// print out fail response
		log(doLogging, "Responding fail: %s", reason);
		
		// send response
		try {
			sendResponse(thisRequest);	
		} catch (IOException e) {
			logErr(doLogging, "Exception on response: 'RESPONSE_FAIL'");
			e.printStackTrace();
		}
	}
	
	
	
	// sends file to client
	// returns null on success
	public String sendFile(String path){
		// buffer variables
		int bufSize = transferBlockSize; 
		int blockCount = 0;
		byte[] buffer = new byte[bufSize];
		
		// other variables
		boolean doTransfer = true;			// end of transfer signal variable
		FileInputStream file = null;		// file input stream
		String rString = null;				// return value
		
		try {
			file = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			// report failure to handler
			doTransfer = false;
			return "File not found";
		}
		
		// per loop variables
		int count = 0;
		FileBlock blockData = null;
		
		// iterate over the file sending one chunk at a time
		while (doTransfer){
			// get block of data from file
			try {
				count = file.read(buffer);
			} catch (IOException e) {
				e.printStackTrace(); 
				rString = "Error on file read";
				doTransfer = false;
			}
			
			// put block of data into file transfer object
			if ((count < 0) || (!doTransfer)) {
				log(doLogging, "Sent %s in %d blocks of %d bytes", path, blockCount, bufSize);
				break;
			}
			
			// create data block
			blockData = new FileBlock(buffer, count);
			
			RequestObject responseData = new RequestObject("RESPOND_DATA", (Object)blockData, order);
			
			// send the response object with the data in it to client
			try {
				sendResponse(responseData);
			} catch (IOException e) {
				logErr(doLogging, "Exception sending file block, halting transfer");
				e.printStackTrace();
				doTransfer = false;
			}
			
			// count the number of transmitted file blocks
			blockCount++;
		}
		
		// close file
		try {
			file.close();
		} catch (IOException e) {
			logErr(doLogging, "Exception on file close, path: %s", path);
			e.printStackTrace();
		}
		
		// indicate state of transfer
		return rString;
	}
	
	
	
	// gets the file
	public String getFile(FileOutputStream fs){
		// get the file size data
		RequestObject thisRequest = null;
		FileBlock blockData;
		long bytesRemaining;
		boolean doTransfer = true;
		String rString = null;
		
		// get size request
		try {
			thisRequest = getRequest();
		} catch (Exception e) {
			e.printStackTrace();
			return "Malformed request on file transfer begin";
		}
		
		// check that appropriate id has been received
		if(!thisRequest.id.equals("SIZE")) {
			return "Expected size of file before transfer. With ID: 'SIZE'";
		}
		
		// get file size
		try {
			bytesRemaining = (long)thisRequest.param;
		} catch (Exception e) {
			return "Malformed filesize parameter";
		}
		
		// check that size makes sense
		// respond ok if things are ok
		log(doLogging, "File size received. notifying client");
		respondOk(null);
		
		// do the file transfer
		while (doTransfer) {
			// get the file block data
			try {
				thisRequest = getRequest();
			} catch (IOException e) {
				e.printStackTrace();
				rString = "Exception during file block recieve";
			}
			
			// if the transfer has not ended
			if(!thisRequest.id.equals("FILE_DATA")) break;
			
			// get the block data
			blockData = null;
			try {
				blockData = (FileBlock)thisRequest.param;
			} catch (Exception e) {
				e.printStackTrace();
				rString = "file data block malformed";
			}
			
			// write the block data to file
			try {
				fs.write(blockData.getData(), 0, blockData.size());
				fs.flush();
			} catch (Exception e) {
				e.printStackTrace();
				rString = "Exception writing file block to disk";
			}
			
			// update bytes remaining
			bytesRemaining -= blockData.size();
		}
		
		// check for over/under-runs
		if (bytesRemaining > 0) rString = String.format("file stransfer underran by %d bytes", bytesRemaining);
		if (bytesRemaining < 0) rString = String.format("file stransfer overran by %d bytes", 0 - bytesRemaining);
		
		// close the file stream
		try {	
			fs.close();
		} catch (Exception e) {
			logErr(doLogging, "Error closing file stream");
			e.printStackTrace();
		}
		
		// everything was successful
		return rString;
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
			"Handler: %d, ip: %s, reqs: %d, running: %b, logging: %b",
			getNum(),
			getIp(),
			getReqCount(),
			!threadDone,
			doLogging
		);
	}
	
	
	
	// print stuff, if printing is enabled
	public void log(boolean doLog, String format, Object... args){
		String outputStr;	// string to output after the handler identifier
		String idStr;		// [H-#] etc
		
		// only print if 
		if (doLog) {
			// format output string
			outputStr = String.format(format, args);
			idStr = String.format("[H-%d] ", handlerInstance);
			
			// print along with ID string
			System.out.println(idStr + outputStr);
		}
	}
	
	
	
	// print stuff, if printing is enabled
	public void logErr(boolean doLog, String format, Object... args){
		String outputStr;	// string to output after the handler identifier
		String idStr;		// [H-#] etc
		
		// only print if 
		if (doLog) {
			// format output string
			outputStr = String.format(format, args);
			idStr = String.format("[H-%d-ERR] ", handlerInstance);
			
			// print along with ID string
			System.out.println(idStr + outputStr);
		}
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
	
	// setter for logging in a user
	public User getUser() {return user;}
	
	// method for blocking/unblocking a handler
	public void setBlock(boolean value) {blocked = value;}
	
	// set whether handler can output
	public void doLogging(boolean value) {doLogging = value;}
	
	// returns whether handler is currently logging to console
	public boolean isLogging() {return doLogging;}
	
	// set size of file transfer block
	public void setTransferBlockSize(int value) {transferBlockSize = value;}
}
