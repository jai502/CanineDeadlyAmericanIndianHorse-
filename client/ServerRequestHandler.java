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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import com.*;

//=========================================================================
// Class for handling server requests
//=========================================================================
public class ServerRequestHandler
{
	private int port;
	private String host;
	private static int order = 0;
	
	public static Socket socket;
	public RequestObject contentFromServer;
	
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
		try
		{
			//Set up the port using the specified host and port
			socket = new Socket(host, port);
			
			System.out.println("[INFO] Socket successfully setup on: "
			+ socket.getInetAddress().toString() 
			+ " on port: " + socket.getPort());
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
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
		
		System.out.println("Response received: " + response.id + " with order: " + response.order);
		String message = (String) response.param;
		
		if(message.equals("success"))
		{
			login = true;
		}
		return login;
	}
	
	//=========================================================================
	// Send the sign-up details to the Server
	//=========================================================================
	public final boolean signUp(User user)
	{
		boolean signUp = false;
		
		RequestObject signUpRequest = new RequestObject("REQUEST_SIGNUP", (Object) user, order);
		System.out.println("Sending " + signUpRequest.id + " with order " + signUpRequest.order + "...");
		sendRequest(signUpRequest);
		System.out.println("Successfully sent, waiting for response...");
		
		RequestObject response = getResponse();
		System.out.println("Response received: " + response.id + " with order: " + response.order);
		String message = (String) response.param;
		
		if(message.equals("success"))
		{
			signUp = true;
		}
		return signUp;
	}
	
	public final String ping()
	{
		String result = null;
		RequestObject ping = new RequestObject("PING", null, order);
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
		ObjectOutputStream contentToServer;
		
		try {
			contentToServer = new ObjectOutputStream(socket.getOutputStream());
			contentToServer.writeObject(request);
			contentToServer.flush();
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
		int timeout = 5000;
		RequestObject response = null;
		ObjectInputStream contentFromServer;
		
		try 
		{
			socket.setSoTimeout(timeout);
			contentFromServer = new ObjectInputStream(socket.getInputStream());
			response = (RequestObject) contentFromServer.readObject();
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