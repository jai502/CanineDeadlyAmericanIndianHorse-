/**
* (C) Stammtisch
* First version created by: J.Bones
* Date of first version: 21.05.2016
* 
* Last version by: J.Bones
* Date of last update: 21.05.2016
* Version number: 1.0
* 
* Commit date:
* Description: A basic client test class to communicate with the server
*/

package client;

import java.util.Scanner;
import server.RequestObject;

public class ClientTest {

	public static void main(String[] args) {
		//Remote host for remote connection
		//String remoteHost = "2.102.60.24";
		//int remotePort = 26656;
		
		//Local host for local connection
		String localHost = "127.0.0.1";
		int localPort = 4444;
		
		ServerRequestHandler com = new ServerRequestHandler(localPort, localHost);
		com.start();
		
		boolean done = false;
		while(!done)
		{
			String id;
			System.out.println("Please enter an id to send to the server: ");
			Scanner scanner = new Scanner(System.in);
			id = scanner.next();
			scanner.close();
			
			RequestObject request = new RequestObject(id, null);
			com.sendToServer(request);
			
			if(id.equals("DISCONNECT"))
			{
				done = true;
			}
		}
	}
}
