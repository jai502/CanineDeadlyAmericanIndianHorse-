/**
 * (C) Stammtisch
 * First version created by: J.Bones & P.Mills
 * Date of first version: 16.05.2016
 * 
 * Last version by: P.Mills
 * Date of last update: 30.05.2016
 * Version number: 1.7
 * 
 * Commit date:
 * Description: Threading to enable network communications
 */

package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import com.*;
import com.sun.corba.se.spi.orbutil.fsm.FSM;

//=========================================================================
// Class for handling server requests
//=========================================================================
public class ServerRequestHandler
{
	private int port;
	private String host;
	private static int order = 1;

	public static Socket socket;
	public RequestObject contentFromServer;

	public ObjectInputStream inputStream;
	public ObjectOutputStream outputStream;

	public ServerRequestHandler(int port, String host)
	{
		this.port = port;
		this.host = host;
	}

	//=========================================================================
	// Setup the socket on the named host and port
	//=========================================================================
	public final void start()
	{
		int timeOut = 5001;
		
		try
		{
			//Set up the port using the specified host and port
			System.out.println("Opening port to server: " + host);
			socket = new Socket(host, port);
			socket.setSoTimeout(timeOut);
			System.out.println("[INFO] Socket successfully setup on: "
					+ socket.getInetAddress().toString() 
					+ " on port: " + socket.getPort());
		}
		
		catch (UnknownHostException e)
		{
			e.printStackTrace();
			return;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}

		// open the output stream
		try {
			outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			System.out.println("output stream open");
		} catch (Exception e) {
			System.out.println("Failed to create output stream:");
			e.printStackTrace();
		}
		
		// open the input stream
		try {
			inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			System.out.println("input stream open");
		} catch (Exception e) {
			System.out.println("Failed to create input stream:");
			e.printStackTrace();
		}
	}

	//=========================================================================
	// Send the login details to Server
	//=========================================================================
	public final boolean loginToServer(User user)
	{
		boolean login = false;

		RequestObject loginRequest = new RequestObject("REQUEST_LOGIN", (Object) user, order);
		System.out.println("Sending " + loginRequest.id + " with order " + loginRequest.order + "...");
		sendRequest(loginRequest);
		System.out.println("Successfully sent, waiting for response...");

		RequestObject response = getResponse();

		switch(response.id)
		{
		case "RESPONSE_OK":
			login = true;
			break;
		case "RESPONSE_FAIL":
			System.out.println("Login failed: " + (String) response.param);
			login = false;
			break;
		default:
			System.out.println("Login failed: (Response " + response.id + " Unknown)");
			login = false;
			break;
		}

		return login;
	}

	//=========================================================================
	// Send the logout command to Server
	//=========================================================================
	public final Boolean logoutFromServer()
	{
		Boolean logout = false;

		RequestObject logoutRequest = new RequestObject("REQUEST_LOGOUT", null, order);
		System.out.println("Sending " + logoutRequest.id + " with order " + logoutRequest.order + "...");
		sendRequest(logoutRequest);
		System.out.println("Successfully sent, waiting for response...");

		RequestObject response = getResponse();

		switch(response.id)
		{
		case "RESPONSE_OK":
			logout = true;
			break;
		case "RESPONSE_FAIL":
			System.out.println("Logout failed: " + (String) response.param);
			logout = false;
			break;
		default:
			System.out.println("Logout failed: (Response " + response.id + " Unknown)");
			logout = false;
			break;
		}

		return logout;
	}

	//=========================================================================
	// Send the sign-up details to the Server
	//=========================================================================
	public final String signUp(User user)
	{
		String signUp = "Sign Up Failed";

		RequestObject signUpRequest = new RequestObject("REQUEST_SIGNUP", (Object) user, order);
		System.out.println("Sending " + signUpRequest.id + " with order " + signUpRequest.order + "...");
		sendRequest(signUpRequest);
		System.out.println("Successfully sent, waiting for response...");

		RequestObject response = getResponse();

		switch(response.id)
		{
		case "RESPONSE_OK":
			signUp = null;
			break;
		case "RESPONSE_FAIL":
			signUp = (String) response.param;
			System.out.println(signUp);
			break;
		default:
			signUp = "(Internal Server Error: " + response.id;
			System.out.println(signUp);
			break;
		}

		return signUp;
	}

	@SuppressWarnings("unchecked")
	public final ArrayList<String[]> searchForPresentation(PresentationShell pres)
	{
		ArrayList<String[]> presentationList;

		RequestObject searchPresentationRequest = new RequestObject("REQUEST_SEARCH", (Object) pres, order);
		System.out.println("Sending " + searchPresentationRequest.id + " with order " + searchPresentationRequest.order + "...");
		sendRequest(searchPresentationRequest);
		RequestObject response = getResponse();

		switch(response.id)
		{
		case "RESPONSE_OK":
			break;
		case "RESPONSE_FAIL":
			presentationList = null;
			System.out.println("Search failed: " + response.id);
			break;
		default:
			System.out.println("Internal Server Error: " + response.id);
			break;
		}
		presentationList = (ArrayList<String[]>) response.param; //suppress warning since we know object has type ArrayList<String[]>

		return presentationList;
	}

	public final void getPresentation(PresentationShell pres, String path)
	{
		// create a file for the presentation
		FileOutputStream  fs;
		try {
			fs = new FileOutputStream(path + pres.getId().toString() + ".pws");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		RequestObject getPresentation = new RequestObject("REQUEST_PRES", (Object) pres, order);
		System.out.println("Sending " + getPresentation.id + " with order " + getPresentation.order + "...");
		sendRequest(getPresentation);

		RequestObject response = getFileResponse(fs);

		switch(response.id)
		{
		case "RESPONSE_OK":
			System.out.println("Server terminated file transfer affirmitively");
			break;
		case "RESPONSE_FAIL":
			System.out.println("Return failed: " + (String) response.param);
			break;
		default:
			System.out.println("Unknown response: " + response.id);
			break;
		}   
	}

	@SuppressWarnings("unused")
	public RequestObject getFileResponse(OutputStream fs)
	{
		byte[] recievedData;
		byte[] fileData = null;

		boolean doTransfer = true;
		RequestObject response = null;
		FileBlock blockData = null;

		while(doTransfer){
			// get data from server
			response = getResponse();

			// check that response has data ID
			if (!response.id.equals("RESPOND_DATA"))
			{
				break;
			}

			blockData = (FileBlock)response.param;

			try {
				fs.write(blockData.getData(), 0, blockData.size());
				fs.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		try {
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}       
		// return the request object that terminated the stream
		return response;
	}
	
	
	public final String uploadPresentation(PresentationShell pres, String path)
	{
		String result = null;
				
		File file = new File(path);
		// Check if file exists before proceed
		if(file.exists())
		{
			RequestObject requestUpload = new RequestObject("REQUEST_UPLOAD", (Object) pres, order);
			System.out.println("Sending " + requestUpload.id + " with order " + requestUpload.order + "...");
			sendRequest(requestUpload);
			RequestObject response = getResponse();

			switch(response.id)
			{
			case "RESPONSE_OK":
				break;
			case "RESPONSE_FAIL":
				result = "Server: " + (String) response.param;
				// Exit from method with failure reason
				return result;
			default:
				result = "Internal Server Error: " + response.id;
				// Exit from method with failure reason
				return result;
			}
			
			//Attempt to send file
			RequestObject sendFileResponse = sendFile(file);
				
		}
		else
		{
			result = "File does not exit at path: " + path;
		}
		
		return result;
	}
	
	public final RequestObject sendFile(File file)
	{
		RequestObject result = null;
		// Buffer variables
		int bufSize = 80000;
		int blockCount = 0;
		byte[] buffer = new byte[bufSize];
		
		// File transfer variables
		FileInputStream fs = null;
		boolean doTransfer = true;
	
		long fileSize = file.length();
		
		RequestObject sendSize = new RequestObject("SIZE", (Object) fileSize, order);
		System.out.println("Sending " + sendSize.id + " with order " + sendSize.order + "...");
		sendRequest(sendSize);
		RequestObject sendSizeResponse = getResponse();
		
		switch(sendSizeResponse.id)
		{
		case "RESPONSE_OK":
			System.out.println("Server terminated file transfer affirmitively");
			break;
		case "RESPONSE_FAIL":
			System.out.println("Return failed: " + (String) sendSizeResponse.param);
			// Exit, sending RequestObject from sendSize request
			return sendSizeResponse;
		default:
			System.out.println("Unknown response: " + sendSizeResponse.id);
			// Exit, sending RequestObject from sendSize request
			return sendSizeResponse;
		}
		
		try {
			fs = new FileInputStream(file.getPath());
		} catch (FileNotFoundException e) {
			System.out.println("File not found at path: " + file.getPath());
			e.printStackTrace();
		}
		
		int count = 0;
		FileBlock blockData = null;
		
		while(doTransfer)
		{
			try {
				count = fs.read(buffer);
			} catch (IOException e) {
				System.out.println("Error with file read");
				e.printStackTrace();
				doTransfer = false;
			}
			
			// Put block of data into file transfer object
			if((count < 0) || (!doTransfer)) break;
			
			// Create data block
			blockData = new FileBlock(buffer, count);
			
			RequestObject sendData = new RequestObject("FILE_DATA", (Object) blockData, order);
			
			// Send the response object with data to the server
			sendRequest(sendData);
			blockCount++;
		}
		
		String transferMessage = "Sent " + file.getPath() + " in " + blockCount + " blocks";
		
		RequestObject sendFileEnd = new RequestObject("FILE_END", (Object)transferMessage, order);
		
		sendRequest(sendFileEnd);
		
		System.out.println(transferMessage);
		
		switch(sendFileEnd.id)
		{
		case "RESPONSE_OK":
			System.out.println("Server terminated file transfer affirmitively");
			break;
		case "RESPONSE_FAIL":
			System.out.println("Return failed: " + (String) sendFileEnd.param);
			// Exit, sending RequestObject from sendSize request
			return sendFileEnd;
		default:
			System.out.println("Unknown response: " + sendFileEnd.id);
			// Exit, sending RequestObject from sendSize request
			return sendFileEnd;
		}
		
		// Close the fileStream
		try {
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	//=========================================================================
	// Send a user presentation vote to the server
	//=========================================================================
	public final boolean sendVote(PresentationShell pres, Integer vote)
	{
		boolean voteRegistered = false;
		
		RequestObject addVote = new RequestObject("REQUEST_SET_VOTE", (Object) pres, order);
		System.out.println("Adding vote to existing ratings id:"+ addVote.id + " with order " + order);
		sendRequest(addVote);
		System.out.println("Successfully sent id, now sending vote");
		
		RequestObject sendVote = new RequestObject("SEND_INT", (Object) vote, order);
		sendRequest(sendVote);
		System.out.println("Vote sent to server");
		
		RequestObject response = getResponse();
		System.out.println("Response received with id:" + response.id);
		
		switch(response.id)
		{
		case "RESPONSE_OK":
			System.out.println("Vote registered on server");
			voteRegistered = true;
			break;
		case "RESPONSE_FAIL":
			System.out.println("Response failed " + (String) response.param);
			break;
		default:
			System.out.println("Unknown response: " + response.id);
			break;
		}
		return voteRegistered;
	}
	
	//=========================================================================
	// Send a comment to the server
	//=========================================================================
	public final boolean addComment(PresentationShell pres, String comment)
	{
		boolean commentAdded = false;
		
		RequestObject commentPres = new RequestObject("REQUEST_SET_COMMENT", (Object) pres, order);
		System.out.println("Adding comment to database with order " + order);
		sendRequest(commentPres);
		
		
		RequestObject attachComment = new RequestObject("", (String) comment, order);
		sendRequest(attachComment);
		System.out.println("Successfully sent, waiting for response...");
		
		RequestObject response = getResponse();
		
		switch(response.id)
		{
		case "RESPONSE_OK":
			System.out.println("Comment added to server database");
			commentAdded = true;
			break;
		case "RESPONSE_FAIL":
			System.out.println("Response failed " + (String) response.param);
			break;
		default:
			System.out.println("Unknown response: " + response.id);
			break;
		}
		
		return commentAdded;
	}
	
	public final ArrayList<String[]> getComments(PresentationShell pres)
	{
		ArrayList<String[]> commentList = null;
		
		RequestObject comments = new RequestObject("REQUEST_GET_COMMENTS", (Object) pres, order);
		System.out.println("Requesting comments list from server with order " + order);
		sendRequest(comments);
		System.out.println("Successfully sent, waiting for response...");
		
		RequestObject response = getResponse();
		
		switch(response.id)
		{
		case "RESPONSE_OK":
			System.out.println("Comments recieved from server");
			commentList = (ArrayList<String[]>) response.param;
			break;
		case "RESPONSE_FAIL":
			System.out.println("Response failed " + (String) response.param);
			break;
		default:
			System.out.println("Unknown response: " + response.id);
			break;
		}
		
		return commentList;
	}
	
	
	public final String ping()
	{
		// Don't ask...
		//byte[] megaPing = new byte[1000000];

		String result = null;
		RequestObject ping = new RequestObject("PING", (Object) null, order);
		System.out.println("Sending " + ping.id + " with order " + ping.order + "...");
		sendRequest(ping);

		RequestObject response = getResponse();
		System.out.println("Response received: " + response.id + " with order: " + response.order);

		if(response.id.equals("PONG"))
		{
			result = response.id;
		}
		return result;
	}

	//=========================================================================
	// Close all network communications
	//=========================================================================
	public void stop()
	{
		RequestObject disconnect = new RequestObject("DISCONNECT", null, order);
		try
		{
			System.out.println("Closing connections...");
			sendRequest(disconnect);
			socket.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	//=========================================================================
	// Private class method for sending content to the Server
	//=========================================================================
	private void sendRequest(RequestObject request)
	{
		//Open the output stream to the server

		try 
		{
			outputStream.reset();
			outputStream.writeObject(request);
			outputStream.flush();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		order += 1;
	}

	//=========================================================================
	// Private class method for getting content from the specified socket
	//=========================================================================
	private RequestObject getResponse()
	{
		RequestObject response = null;

		try 
		{
			response = (RequestObject) inputStream.readObject();
		}
		catch (IOException e)
		{
			System.out.println("Timeout");
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		return response;
	}
}