package gui;

import java.io.Serializable;

/*
 * (C) Stammtisch
 * First version created by: Mathew Gould & Alexander Stassis (Design Team)
 * Date of first version: 06/04/16
 * 
 * Last version by: Mathew Gould & Alexander Stassis (Design Team)
 * Date of last update:  06/04/16
 * Version number: 1
 * 
 * Commit date: 06/04/16
 * Description: Class which stores the data from the Login Page
 */

public class LoginDetails implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String username, password;

	public LoginDetails()
	{
	}

	// return the username credentials
	public String getUsername()
	{
		return username;
	}

	// return the password credentials
	public String getPassword()
	{
		return password;
	}

	// Sets the username details
	public void setUsername(String setName)
	{
		this.username = setName;
	}

	// Sets the password details
	public void setPassword(String setCode)
	{
		this.password = setCode;
	}
}
