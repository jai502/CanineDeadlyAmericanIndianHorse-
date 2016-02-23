package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class StammtischServer {
	
	// Socket stuff
	public static int defaultPort = 26656; //Port to run server on
	public ServerSocket sSocket;
	
	
	
	public StammtischServer(int port) {
		// Indicate that server is starting
		System.out.printf("[INFO] Starting server on port %d\n", port);
		
		// Initialise connection listener
		System.out.printf("[INFO] Starting connection listener \n");
		connectionListener.start();
	}

	
	
	// Connection listener thread
	// waits for incoming socket connection
	Thread connectionListener = new Thread("Stammtish connection listener") {
		public void run() {
			try {
				sSocket = new ServerSocket(defaultPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while(true) {
				try {
					System.out.printf("[INFO] Waiting for client connection... \n");
					Thread thread = new Thread(new ClientRequestHandler(sSocket.accept()));
					System.out.printf("[INFO] Client connected, starting handler thread \n");
					thread.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	};
	
	
	
	// Main method for launching server
	public static void main(String[] args){
		new StammtischServer(defaultPort);
	}

}


