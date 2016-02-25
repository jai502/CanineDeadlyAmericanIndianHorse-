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
	String remoteHost = "2.102.60.24";
	int remotePort = 26656;
	Socket serverSocket = null;
	
	String id, parameter = null;
	String serverMessage = null;

	@Before
	public void setUp() throws Exception {
		//First launch the server to allow client communication
		/*Place code for server launch here!!*/
		
		//Launch the client and setup a socket
		serverSocket = MainClient.setupSocket(remoteHost,remotePort);
		//Send a request to the server
		MainClient.sendToServer(serverSocket, id, parameter);
	
	}

	@Test
	public void test() {
		
		try {
			//Test that the port number and host address match that in the test
			assertEquals(MainClient.setupSocket(remoteHost, remotePort).getInetAddress(), remoteHost);
			assertEquals(MainClient.setupSocket(remoteHost, remotePort).getLocalPort(), remotePort);
			
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
