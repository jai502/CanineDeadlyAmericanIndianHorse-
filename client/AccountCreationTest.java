/*
* (C) Stammtisch
* First version created by: A.Cramb and J.White
* Date of first version: 01/06/16
* 
* Last version by: A.Cramb and J.White
* Date of last update: 01/06/16
* Version number: 1.0
* 
* Commit date:
* Description: Class to create new user account on the server
*/

package client;

import java.time.LocalDate;
import com.*;

public class AccountCreationTest {
	
	public static void main(String[] args) 
	{
		//declaration of variables
		int port = 26656;
		String host = "stammsvr.servebeer.com";
		ServerRequestHandler connection = new ServerRequestHandler(port, host);
		
		//start connection to server
		connection.start();
		
		//declare user and date
		User thisUser = new User();
		LocalDate date = LocalDate.of(1995, 1, 21);
		
		//set new user details
		thisUser.setUsername("test");
		thisUser.setPassword("password");
		thisUser.setEmail("jww521@york.ac.uk"); 
		thisUser.getDob();
		thisUser.setDob(date);
		
		String result = connection.signUp(thisUser);
		
		//return signup results
		if (result == null) 
		{
			System.out.println("Signup successful");
		}
		else System.out.println("Signup not succsesful: " + result);
		
		if (connection.loginToServer(thisUser)) 
		{
			System.out.println("Login successful");
		}
		else System.out.println("Login failed");
		
		connection.stop();
	}	
}
