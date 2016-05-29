package com;

import java.io.Serializable;


// DO NOT CHANGE WITHOUT SPEAKING TO jb1237

// Object to identify requests from the client
// serialised and sent over the network to identify requests
public final class RequestObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public String id;
	public Object param;
	public int order;

	public RequestObject(String ident, Object parameter, int order) {
		this.id = ident;
		this.param = parameter;
		this.order = order;
	}
}