/*
* (C) Stammtisch
* First version created by: A.Cramb and J.White
* Date of first version: 01/06/16
* 
* Last version by: A.Cramb and J.White
* Date of last update:  01/06/16
* Version number: 1.0
* 
* Commit date: 01/06/16
* Description: JUnit test for ServerRequestHandler class
*/

package client;

import static org.junit.Assert.*;

import org.junit.Test;

import com.*;

public class ServerRequestHandlerTest 
{
	//declaration of test variables
	public int port = 26656;
	public String host = "stammsvr.servebeer.com";
	public String id = "Invalid ID";
	public int order = 0;
	
	//test sends ping to server and recieves "PONG" if connection is secure
	@Test
	public void testConnection() 
	{
		ServerRequestHandler connection = new ServerRequestHandler(port, host);
		connection.start();
		
		assertEquals("PONG", connection.ping());
		
		connection.stop();
	}
	
	//test attempts to login with correct login details and passes if login successful
	@Test
	public void testLoginLogout() 
	{
		ServerRequestHandler connection = new ServerRequestHandler(port, host);
		User user = new User();
		connection.start();
		user.setPassword("pass");
		user.setUsername("Peter");
		
		assertEquals(true, connection.loginToServer(user));
		
		//Code from:
		//http://stackoverflow.com/questions/3342651/how-can-i-delay-a-java-program-for-a-few-seconds
		try { Thread.sleep(50); } catch(InterruptedException ex) { Thread.currentThread().interrupt(); }
		
		assertEquals(true, connection.logoutFromServer());
		
		connection.stop();
	}
	
	//test to ensure that login is prevented for incorrect login details
	@Test
	public void testLoginLogoutFails() 
	{
		ServerRequestHandler connection = new ServerRequestHandler(port, host);
		User user = new User();
		connection.start();
		user.setPassword("IncorrectPassword");
		user.setUsername("IncorrectUsername");
		
		assertEquals(false, connection.loginToServer(user));
		assertEquals(false, connection.logoutFromServer());
		
		connection.stop();
	}
}
