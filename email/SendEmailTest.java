/*
* (C) Stammtisch
* First version created by: J.White
* Date of first version: 20/05/2016
* 
* Last version by: J.White
* Date of last update: 23/05/2016
* Version number: 1.0
* 
* Commit date:
* Description: JUnit test for SendEmail class
*/

package email;

import static org.junit.Assert.*;

import javax.mail.Message;
import javax.mail.Transport;

import org.junit.Before;
import org.junit.Test;

public class SendEmailTest {
	private boolean email;

	//Test to ensure email is sent correctly
	//SendEmail class modified to return boolean true when email sent
	//to easily test for success
	//test needs to be modified
	@Test
	public void testControlEmail() {
		
		String to = "jww521@york.ac.uk";
		String subject = "Welcome to Stammtisch!";
		String username = "Retrieve username here";
		String usrPassword = "Retrieve password here";
		String message = "<head><title></title></head><body><table><tr><td><table><tr><td id=\"header\" width=\"600px\" height=\"50px\" border=\"0\" valign=\"top\" style=\"background-color:rgb(232 , 159, 3); text-align:center\"><p></p></td></tr><tr><td id=\"content\" width=\"600px\" height=\"200px\" padding=\"20px\" valign=\"top\"><p style=\"font-family: Arial\">Thanks for signing up to Stammtisch!<br>Here are your keys into the language learning paradise: <br><br> Username: "+username+"<br><br> Password: "+usrPassword+"<br><br> Make sure to keep those safe!</p></td></tr><tr><td id=\"footer\" width=\"600px\" height=\"50px\" border=\"0\" valign=\"top\" style=\"background-color:rgb(232 , 159, 3); text-align:center\"><p style=\"font-family: Arial, sans-serif; font-size:70%\">This email was sent to you automatically as part of signing up to the Stammtisch language learning platform. Please do not reply to this email as we are unable to respond from this email address.</p><p style=\"font-family: Arial, sans-serif; font-size:100%\">&copy;Stammtisch 2016</p></td></tr></table></td></tr></table></body>";
		String sender = "swengstammtisch@gmail.com";
		String senderPassword = "SWengTeam1";
		
		email = SendEmail.send(to, subject, message, sender, senderPassword);
		
		assertEquals(true, email);
	}
}
