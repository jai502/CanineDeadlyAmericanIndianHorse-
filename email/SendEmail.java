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

    public static void send(String to, String sub,String msg, final String user_email, final String password) 
    {
        Properties properties = new Properties();
        String host = "smtp.gmail.com";
        int port = 587;

      //Set up the system for sending email
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.sender.address", user_email);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.debug", "true");
        properties.put("mail.smtp.EnableSSL.enable","true");

        //Code here was taken from
        //Reference http://stackoverflow.com/questions/386083/must-issue-a-starttls-command-first-sending-email-with-java-and-google-apps
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        
        Authenticator authenticator = new Authenticator() {
          public PasswordAuthentication getPasswordAuthentication(){
              return new PasswordAuthentication(user_email, password);
          }
      };

      //Get the default session stuff
      Session session = Session.getDefaultInstance(properties, authenticator);
      Message message = new MimeMessage(session);

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
        } catch (MessagingException e) 
        {
            JOptionPane.showMessageDialog(null,"Something happened!");
            
            throw new RuntimeException(e);
        }
        
    }
}