/**
* (C) Stammtisch
* First version created by: D.Dreyfus
* Date of first version: 06.05.2016
* 
* Last version by: J.Bones
* Date of last update: 09.05.2016
* Version number: 1.2
* 
* Commit date: 09.05.2016
* Description: Module to allow for sending of Emails upon user registration
 */

package email;

public class TestEmail {

	public static void main(String[] args) {
		
		String to = "jb1237@york.ac.uk"; //User email to be placed here
		String subject = "Welcome to Stammtisch!";
		
		//Method of retrieval of user data is required here
    String username = "Retrieve username here";
    String usrPassword = "Retrieve password here";
    
    //HTML email message
		String message = "<head><title></title></head><body><table><tr><td><table><tr><td id=\"header\" width=\"600px\" height=\"50px\" border=\"0\" valign=\"top\" style=\"background-color:rgb(232 , 159, 3); text-align:center\"><p></p></td></tr><tr><td id=\"content\" width=\"600px\" height=\"200px\" padding=\"20px\" valign=\"top\"><p style=\"font-family: Arial\">Thanks for signing up to Stammtisch!<br>Here are your keys into the language learning paradise: <br><br> Username: "+username+"<br><br> Password: "+usrPassword+"<br><br> Make sure to keep those safe!</p></td></tr><tr><td id=\"footer\" width=\"600px\" height=\"50px\" border=\"0\" valign=\"top\" style=\"background-color:rgb(232 , 159, 3); text-align:center\"><p style=\"font-family: Arial, sans-serif; font-size:70%\">This email was sent to you automatically as part of signing up to the Stammtisch language learning platform. Please do not reply to this email as we are unable to respond from this email address.</p><p style=\"font-family: Arial, sans-serif; font-size:100%\">&copy;Stammtisch 2016</p></td></tr></table></td></tr></table></body>";
		
		//Email details of sending account
    String sender = "swengstammtisch@gmail.com";
    String senderPassword = "SWengTeam1";
		
		SendEmail.send(to, subject, message, sender, senderPassword);
	}
}
