/**
* (C) Stammtisch
* First version created by: J.Bones & P.Mills
* Date of first version: 16.05.2016
* 
* Last version by: J.Bones & P.Mills
* Date of last update: 16.05.2016
* Version number: 1.0
* 
* Commit date:
* Description: Threading to enable network communications
 */

package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import com.RequestObject;

public class CommsHandler implements Callable<RequestObject>
{
	String id;
	Object requestParam;
	Socket serverSocket;
	RequestObject contentFromServer;
	
	public CommsHandler(Socket serverSocket, String id, Object requestParam)
	{
		this.serverSocket = serverSocket;
		this.id = id;
		this.requestParam = requestParam;
	}
	
	public RequestObject call()
	{
		synchronized(this)
		{
			try 
			{
				if(id.equals("DISCONNECT"))
				{
					System.out.println("Disconnecting from the server...");
					sendToServer(serverSocket, id, requestParam); //Send the disconnect id
					serverSocket.close();//Close the socket
					System.out.println("Socket successfully closed");
					
				}
				else
				{
					System.out.println("Sending request to server...");
					sendToServer(serverSocket, id, requestParam); //Send a request to the server on the named socket
					System.out.println("Request sent, waiting for response...");
					contentFromServer = readFromServer(serverSocket);//Read a response from the server
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //Send request to the server on the named socket
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return contentFromServer;
	}
	
	//===============================================================================================
	//Method for setting up a socket on the host and port given
	public static void sendToServer(Socket socket, String id, Object parameter) throws IOException
	{
		//Create a request object to communicate with the server
		RequestObject request = new RequestObject(id, parameter, 0);

		//Open the output stream to the server
		ObjectOutputStream infoToServer = new ObjectOutputStream(socket.getOutputStream());
		infoToServer.writeObject(request);
		infoToServer.flush();
	}
	
	//===============================================================================================
	//Method for setting up a socket on the host and port given
	public static RequestObject readFromServer(Socket socket) throws IOException, ClassNotFoundException
	{
		ObjectInputStream infoFromServer = new ObjectInputStream(socket.getInputStream());
		RequestObject request = (RequestObject) infoFromServer.readObject();
		return request;
	}
}