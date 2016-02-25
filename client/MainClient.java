/**
* (C) Stammtisch
* First version created by: J.Bones
* Date of first version: 25.02.2016
* 
* Last version by: J.Bones
* Date of last update: 25.02.2016
* Version number: 1.0
* 
* Commit date:
* Description: A basic client which can communicate with the Server
 */
package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import server.RequestObject;

public class MainClient 
{

	public static void main(String args[]) 
	{
		
		//Local host for testing
		String localHost = "127.0.0.1";
		int localPort = 4444;
		
		//Remote host for remote connection
		String remoteHost = "2.102.60.24";
		int remotePort = 26656;
		Socket serverSocket = null;
		RequestObject request = null;
		String id;
		Object parameter;
		
		try 
		{
			//Setup the socket for network communication
			System.out.println("Setting up the socket...");
			serverSocket = setupSocket(remoteHost,remotePort);
			System.out.println("Socket setup on address "+serverSocket.getInetAddress().toString()+" on port "+serverSocket.getPort());
			
			//Send a request to the server
			System.out.println("Now attempting to send a request to the server");
			
			//Send a request to the server on the named socket
			sendToServer(serverSocket, id, parameter);
			System.out.println("String sent to the server!");
			
			//Read a response from the server
			System.out.println("Attempting to read from the server");
			RequestObject requestFromServer = readFromServer(serverSocket);
			System.out.println("Received request object from server");
			
			//Close the socket
			serverSocket.close();
		}
		catch (UnknownHostException e) 
		{
			System.out.println("Looks like an unknown host!");
		}
		catch (IOException e)
		{
			System.out.println("Looks like an IO exception!");
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("Looks like a class not found exception");
		}
		
	}

	/*
	 * Method for setting up a socket on the host and port given
	 */
	public static Socket setupSocket(String host, int port) throws UnknownHostException, IOException
	{
		Socket socket = new Socket(host, port);
		return socket;
	}

	/*
	 * Method for sending a string to the connected server
	 */
	public static void sendToServer(Socket socket, String id, Object parameter) throws IOException
	{
		//Create a request object to communicate with the server
		RequestObject request = new RequestObject(id,parameter);
		
		//Open the output stream to the server
		ObjectOutputStream infoToServer = new ObjectOutputStream(socket.getOutputStream());
		infoToServer.writeObject(request);
		infoToServer.flush();
	}
	
	/*
	 * Method for reading a string from the connected server
	 */
	public static RequestObject readFromServer(Socket socket) throws IOException, ClassNotFoundException
	{
		ObjectInputStream infoFromServer = new ObjectInputStream(socket.getInputStream());
		RequestObject request = (RequestObject) infoFromServer.readObject();
		return request;
	}

}
