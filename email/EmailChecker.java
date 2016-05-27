/*
* (C) Stammtisch
* First version created by: J.White
* Date of first version: 27/05/2016
* 
* Last version by: J.White
* Date of last update: 27/05/2016
* Version number: 1.0
* 
* Commit date: 27/05/2016
* Description: Class that checks input user details and returns boolean value
* for valid/invalid details
*/

package email;

public class EmailChecker 
{
	//takes single email input and checks correct
	public static boolean checker(String emailToCheck) 
	{
		boolean pass = false;		//set pass to false initially to avoid incorrect true output
		int hasAt = emailToCheck.indexOf('@');	//check for @ symbol in string
		int hasDot = emailToCheck.indexOf('.');	//check for . in string
		
		//checks if string has @ symbol, if this is fulfilled checks for .
		//if both conditions are satisfied, pass is changed to true
		//if either fails, false is immediately returned
		if((hasAt >= 0) && (hasDot >= 0))
		{
			pass = true;
		}
		else pass = false;
		
		return pass;
	}
}
