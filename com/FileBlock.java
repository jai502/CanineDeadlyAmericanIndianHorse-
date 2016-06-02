/**
 * (C) Stammtisch
 * First version created by: Alexander cramb
 * Date of first version: 27/05/2016
 * 
 * Last version by: Alexander cramb
 * Date of last update: 
 * Version number: 1.0
 * 
 * Commit date: 
 * Description: 
 * 	Block of file data for file transfer
 */

package com;

import java.io.Serializable;

public class FileBlock implements Serializable{
	private static final long serialVersionUID = 1L;
	
	// size of block data in bytes
	int hash = 0;
	int size = 0;
	byte data[];
	
	// constructor sets size of block
	public FileBlock(byte[] data, int size){
		this.size = size;
		this.data = data;
		this.hash = this.data.hashCode();
	}
	
	// getter for size to be used in client
	public int size(){
		return this.size;
	}
	
	// get data
	public byte[] getData(){
		return data;
	}
	
	// compute data hash
	public boolean checkHash(){
		// compare hash of data to actual hash
		int currentHash = data.hashCode();
		if (this.hash != currentHash) return false;
		else return true;
	}
}
