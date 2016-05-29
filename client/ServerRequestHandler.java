/**
 * (C) Stammtisch
 * First version created by: J.Bones & P.Mills
 * Date of first version: 16.05.2016
 * 
 * Last version by: J.Bones & A.Cramb
 * Date of last update: 23.05.2016
 * Version number: 1.3
 * 
 * Commit date:
 * Description: Threading to enable network communications
 */

package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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

	public final void getPresentation(PresentationShell pres)
	{
		// create a file for the presentation
		FileOutputStream  fs;
		try {
			fs = new FileOutputStream("test.pws");
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
		// return the request object that terminated the stream
		return response;
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