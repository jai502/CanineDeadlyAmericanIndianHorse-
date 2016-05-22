/**
* (C) Stammtisch
* First version created by: J.Bones
* Date of first version: 25.02.2016
* 
* Last version by: J.Bones
* Date of last update: 16.05.2016
* Version number: 1.3
* 
* Commit date:
* Description: A basic client which can communicate with the Server
 */
package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import server.RequestObject;

public class MainClient
{

	public static void main(String args[]) 
	{
		//Remote host for remote connection
		//String remoteHost = "2.102.60.24";
		//int remotePort = 26656;
		String localHost = "127.0.0.1";
		int localPort = 4444;
		Socket serverSocket = null;
		Object request = null;
		String id = null;
		Boolean flag = true;
		RequestObject contentFromServer;

			
				try {
					System.out.println("Setting up the socket...");
					serverSocket = setupSocket(localHost,localPort);//Setup the socket for network communication
					System.out.println("Socket setup on address "+serverSocket.getInetAddress().toString()+" on port "+serverSocket.getPort());
					
					while(flag == true)
					{
						System.out.println("Please enter an id to send to the server: ");
						Scanner scanner = new Scanner(System.in);
						id = scanner.next();
	
						CommsHandler comms = new CommsHandler(serverSocket, id, request);
						contentFromServer = comms.call();
						
						if(id.equals("DISCONNECT"))
						{
							System.out.println("Client disconnected from server");
							flag = false;
						}
						else
						{
							System.out.println("Received request object from server: "+contentFromServer.id.toString());
						}	
					}
				}
				catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	//===============================================================================================
	//Method for setting up a socket on the host and port given
	public static Socket setupSocket(String host, int port) throws UnknownHostException, IOException
	{
		Socket socket = new Socket(host, port);
		return socket;
	}
}
