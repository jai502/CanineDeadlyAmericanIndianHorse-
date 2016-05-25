/**
 * (C) Stammtisch
 * First version created by: Jonathan Bones & Peter Mills
 * Date of first version: 18/05/2016
 * 
 * Last version by: Jonathan Bones & Peter Mills
 * Date of last update: 18/05/2016
 * Version number: 1.0
 * 
 * Commit date: 18/06/2015
 * Description: Simple class to simulate user account data
 *
 */

package com;

import java.io.Serializable;

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String username, password, email, dob;

	/**
	 * @return the username
	 */
	public String getUsername() 
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return the dob
	 */
	public String getDob()
	{
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob)
	{
		this.dob = dob;
	} 

	/**
	 * @param check that user object has all the fields neccessary for signup
	 */
	public boolean validForSignup()
	{
		if(this.dob == null) return false;
		if(this.username == null) return false;
		if(this.password == null) return false;
		if(this.email == null) return false;
		
		return true;
	}
}
