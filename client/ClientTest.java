/**
* (C) Stammtisch
* First version created by: J.Bones
* Date of first version: 21.05.2016
* 
* Last version by: J.Bones
* Date of last update: 23.05.2016
* Version number: 1.2
* 
* Commit date:
* Description: A basic client test class to communicate with the server
*/

package client;

import java.util.Scanner;
import com.*;

public class ClientTest {

	public static void main(String[] args) {
		//Remote host for remote connection
		//String remoteHost = "2.102.60.24";
		//int remotePort = 26656;
		
		String serverHost = "192.168.1.91";
		int serverPort = 26656;
		
		//Local host for local connection
		//String localHost = "127.0.0.1";
		//int localPort = 4444;
		
		ServerRequestHandler com = new ServerRequestHandler(serverPort, serverHost);
		com.start(); //Open the socket
		
		boolean done = false;
		
		while(!done)
		{
			String id = null;
			System.out.println("Please enter an id to send to the server: ");
			Scanner scanner = new Scanner(System.in);
			id = scanner.next();
			System.out.println("input was: " + id);
			
			switch(id)
			{
			case "login":
				//Create a user to login
				User user1 = new User();
				user1.setUsername("Tangents4Life");
				user1.setPassword("A secret word");
				
				boolean loginResponse = com.loginToServer(user1);
				System.out.println("Login returns: " + loginResponse);
				break;
			case "signup":
				//Create a user for signup
				User user2 = new User();
				user2.setUsername("NewUser");
				user2.setPassword("AllNewHere");
				user2.setEmail("newbie@gmail.com");
				//user2.setDob("1990-02-12");
				
				boolean signupResponse = com.signUp(user2);
				System.out.println("Signup returns: " + signupResponse);
				break;
			case "show":
				break;
			case "search":
				break;
			case "request":
				break;
			case "upload":
				break;
			case "vote":
				break;
			case "comment":
				break;
			case "ping":
				String pong = com.ping();
				System.out.println(pong);
				break;
			case "disconnect":
				com.stop();
				done = true;
				break;
			default:
				System.out.println("[Default]");
			}
			
		}
	}
}
