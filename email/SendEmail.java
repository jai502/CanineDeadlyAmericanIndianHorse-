/**
* (C) Stammtisch
* First version created by: D.Dreyfus
* Date of first version: 06.05.2016
* 
* Last version by: J.White
* Date of last update: 23.05.2016
* Version number: 1.2.1
* 
* Commit date: 
* Description: Module to allow for sending of Emails upon user registration
* boolean return added in version 1.2.1 for ease of testing
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
import javax.swing.JOptionPane;

public class SendEmail 
{
	//inputs:to,subject,message,sender,sender password
    public static boolean send(String to, String sub,String msg, final String user_email, final String password) 
    {
    	boolean emailSent; 
    	//added for ease of testing
    	//not ideal as testing shouldn't change source code
    	
        Properties properties = new Properties();
        String host = "smtp.gmail.com";		//define host
        int port = 587;		//define port

        //Set up the system for sending email
        properties.put("mail.smtp.starttls.enable", "true");	//allows encryption/use of plain text
        properties.put("mail.smtp.host", host);		//set host
        properties.put("mail.smtp.socketFactory.port", port);	//set port
        properties.put("mail.smtp.sender.address", user_email);	//sets sender email as input
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
              return new PasswordAuthentication(user_email, password);
          }
        };
        //check PasswordAuthentication holds correct values

        //Get the default session stuff
        Session session = Session.getDefaultInstance(properties, authenticator);
        Message message = new MimeMessage(session);

        //Set email contents
        try 
        {
            message.setFrom(new InternetAddress(user_email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Welcome to Stammtisch!");
            
            //Create the email content using HTML
            message.setContent(msg, "text/html");
            
            //Send the message
            Transport.send(message);
            System.out.println("Email Sent!");
            
            emailSent = true;
        } 
        
        catch (MessagingException e) 
        {
        	emailSent = false;
            JOptionPane.showMessageDialog(null,"Something happened!");
            throw new RuntimeException(e);
        }
        
        return emailSent;
    }
}