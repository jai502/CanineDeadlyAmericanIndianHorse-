package gui;

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
 * Description: Class which stores the data from the Signup Page
 */

public class SignupDetails 
{

	private String firstName, surname, email, confirmEmail; 
	private String username, password, confirmPassword;

	public SignupDetails()
	{
	}

	// return the first name credentials
	protected Object getFirstName()
	{
		return firstName;
	}

	// return the surname credentials
	protected Object getSurname()
	{
		return surname;
	}

	// return the email credentials
	protected Object getEmail()
	{
		return email;
	}

	// return the confirm email credentials
	protected Object getConfirmEmail()
	{
		return confirmEmail;
	}

	// return the username credentials
	protected Object getUsername()
	{
		return username;
	}


	// return the password credentials
	protected Object getPassword()
	{
		return password;
	}

	// return the confirm password credentials
	protected Object getConfirmPassword()
	{
		return confirmPassword;
	}

	// Sets the first name details
	protected void setFirstName(String setFirstName)
	{
		this.firstName = setFirstName;
	}

	// Sets the surname details
	protected void setSurname(String setSurname)
	{
		this.surname = setSurname;
	}

	// Sets the email details
	protected void setEmail(String setEmail)
	{
		this.email = setEmail;
	}

	// Sets the confirm email details
	protected void setConfirmEmail(String setConfirmEmail)
	{
		this.confirmEmail = setConfirmEmail;
	}


	// Sets the username details
	protected void setUsername(String setUsername)
	{
		this.username = setUsername;
	}

	// Sets the password details
	protected void setPassword(String setCode)
	{
		this.password = setCode;
	}

	// Sets the confirm password details
	protected void setConfirmPassword(String setConfirmCode)
	{
		this.confirmPassword = setConfirmCode;
	}
}

