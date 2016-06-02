/**
* (C) Stammtisch
* First version created by: D.Dreyfus
* Date of first version: 06/05/2016
* 
* Last version by: J.White
* Date of last update: 27/05/2016
* Version number: 1.3
* 
* Commit date: 27/05/2016
* Description: Module to allow for sending of Emails upon user registration
* and now avoids throwing exception when invalid email is sent due to use of EmailChecker
*/

package email;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail 
{
	//inputs:to,subject,message,sender,sender password
    public static boolean send(String to, String sub,String msg, final String from, final String password) 
    {
    	//definition of variables
        Properties properties = new Properties();
        String host = "smtp.gmail.com";
        int port = 587;
        boolean sent = false;
        //use EmailChecker class to check for presence of @ and . in strings
        boolean userEmail = EmailChecker.checker(to);
        boolean senderEmail = EmailChecker.checker(from);
        
        //Set up the system for sending email
        properties.put("mail.smtp.starttls.enable", "true");	//allows encryption/use of plain text
        properties.put("mail.smtp.host", host);		//set host
        properties.put("mail.smtp.socketFactory.port", port);	//set port
        properties.put("mail.smtp.sender.address", from);	//sets sender email as input
        properties.put("mail.smtp.auth", "true");		//authentication
        properties.put("mail.smtp.debug", "true");		//debugging
        properties.put("mail.smtp.EnableSSL.enable","true");	//connects to ssl server

        //Code here was taken from
        //Reference http://stackoverflow.com/questions/386083/must-issue-a-starttls-command-first-sending-email-with-java-and-google-apps
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        
        //Authentication of sender details
        Authenticator authenticator = new Authenticator() 
        {
          public PasswordAuthentication getPasswordAuthentication()
          {
              return new PasswordAuthentication(from, password);
          }
        };
        //check PasswordAuthentication holds correct values

        //Get the default session stuff
        Session session = Session.getDefaultInstance(properties, authenticator);
        Message message = new MimeMessage(session);

        //If emails contain correct characters, messaging try catch is allowed
        //if characters aren't present try catch is avoided and therefore no exception is thrown
        if (userEmail && senderEmail == true)
        {
        	//Set email contents
        	try 
            {
        		message.setFrom(new InternetAddress(from));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject("Welcome to Stammtisch!");
                
                //Create the email content using HTML
                message.setContent(msg, "text/html");
                
                //Send the message
                Transport.send(message);
                System.out.println("Email Sent!");
                
                sent = true;
            } 
            
        	//avoided unless error that can't be recognized by EmailChecker occurs
            catch (MessagingException e) 
            {
                System.out.println("Something happened!");
                throw new RuntimeException(e);
            }
        }
        //if invalid email email doesn't send
        else System.out.println("Unable to send");
        
        return sent;
    }
}