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
import server.RequestObject;

//=========================================================================
// Class for handling server requests
//=========================================================================
public class ServerRequestHandler implements Runnable
{
	private int port;
	private String host;
	private boolean done = false;
	
	public static Socket socket;
	public RequestObject contentFromServer;
	
	public ServerRequestHandler(int port, String host)
	{
		this.port = port;
		this.host = host;
		
		start();
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
		
		//run the thread to listen on the specified port
		run();
	}
	
	public final void run()
	{
		while(!done)
		{
			System.out.println("[INFO] Waiting for server...");
			contentFromServer = readFromServer(socket);
		}
	}
	
	//=========================================================================
	// Method for sending content to the Server
	//=========================================================================
	public final void sendToServer(RequestObject request)
	{
		//Open the output stream to the server
		ObjectOutputStream infoToServer;
		
		if(request.id.equals("DISCONNECT"))
		{
			try 
			{
				infoToServer = new ObjectOutputStream(socket.getOutputStream());
				infoToServer.writeObject(request);
				infoToServer.flush();
				
				socket.close();//close the socket once disconnect command is sent
				System.out.println("[INFO] Socket successfully closed");
				done = true; //stop the thread from listening on a closed socket
			}
			catch (IOException e) 
			{
				e.printStackTrace();
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
	}
	
	//=========================================================================
	// Method for reading content from the specified socket
	//=========================================================================
	public static RequestObject readFromServer(Socket socket)
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