/**
* (C) Stammtisch
* First version created by: J.Bones & P.Mills
* Date of first version: 16.05.2016
* 
* Last version by: J.Bones & A.Cramb
* Date of last update: 22.05.2016
* Version number: 1.2
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

import gui.LoginDetails;
import server.RequestObject;

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
	// Method for sending login details to Server
	//=========================================================================
	public final void loginToServer(LoginDetails loginDetails)
	{
		RequestObject loginRequest = new RequestObject("REQUEST_LOGIN", (Object)loginDetails, order);
		System.out.println(loginRequest.id + "being sent...");
		sendToServer(loginRequest);
		System.out.println("Successfully Sent");
	}
	
	public void close()
	{
		RequestObject disconnect = new RequestObject("DISCONNECT",null, order);
		try {
			System.out.println("Closing connections...");
			sendToServer(disconnect);
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//=========================================================================
	// Method for sending content to the Server
	//=========================================================================
	public final void sendToServer(RequestObject request)
	{
		//Open the output stream to the server
		ObjectOutputStream infoToServer;
		
		request.order = this.order;
		
		if(request.id.equals("DISCONNECT"))
		{
			try 
			{
				infoToServer = new ObjectOutputStream(socket.getOutputStream());
				infoToServer.writeObject(request);
				infoToServer.flush();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
				System.out.println("Exiting ... ");
				System.exit(0);
			}
		}
		else
		{
			try
			{
				infoToServer = new ObjectOutputStream(socket.getOutputStream());
				infoToServer.writeObject(request);
				infoToServer.flush();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		order = order + 1;
	}
	
	//=========================================================================
	// Method for reading content from the specified socket
	//=========================================================================
	public final RequestObject readFromServer()
	{
		RequestObject content = null;
		ObjectInputStream infoFromServer;
		
		try 
		{
			infoFromServer = new ObjectInputStream(socket.getInputStream());
			content = (RequestObject) infoFromServer.readObject();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return content;
	}
}