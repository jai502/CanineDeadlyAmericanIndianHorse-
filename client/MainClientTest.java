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
* Description: The client test class which tests functionality!
 */

package client;

import static org.junit.Assert.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import org.junit.Before;
import org.junit.Test;

public class MainClientTest {
	
	//Initialise the variables for the local host & port locations
	String localHost = "127.0.0.1";
	int localPort = 4444;
	Socket serverSocket = null;
	
	String clientMessage = "hello";
	String serverMessage = null;

	@Before
	public void setUp() throws Exception {
		//First launch the server to allow client communication
		/*Place code for server launch here!!*/
		
		//Launch the client and setup a socket
		serverSocket = MainClient.setupSocket(localHost,localPort);
		//Send a string to the server
		MainClient.sendToServer(serverSocket, "Hello");
		serverMessage = MainClient.readFromServer(serverSocket);
		
	}

	@Test
	public void test() {
		
		try {
			//Test that the port number and host address match that in the test
			assertEquals(MainClient.setupSocket(localHost, localPort).getInetAddress(), localHost);
			assertEquals(MainClient.setupSocket(localHost, localPort).getLocalPort(), localPort);
			
			//Check that the correct information is sent to the server
			
			
			
		}
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Looks like a UHE!");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Looks like a IOE!");
		}
		
		fail("Not yet implemented");
	}

}
