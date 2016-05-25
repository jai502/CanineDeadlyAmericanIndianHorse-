/*
* (C) Stammtisch
* First version created by: Alexander Cramb (ac1362)
* Date of first version: 24/05/2016
* 
* Last version by: Alexander Cramb (ac1362)
* Date of last update: 24/05/2016
* Version number: 0.1
* 
* Commit date: 
* Description: 
* 	Template for responding to requests
*/


package server;


import com.*;


class Response {
	private String requestId;
	
	// constructor
	protected Response(String requestId){
		this.requestId = requestId;
	}
	
	// get name of command
	public String getRequestId(){
		return requestId;
	}
	
	// command execute memory
	public void respond(ClientRequestHandler handler){
		System.out.printf("[ERR-%d] Unhandled response: %s\n", handler.getNum(), requestId);
	}
}