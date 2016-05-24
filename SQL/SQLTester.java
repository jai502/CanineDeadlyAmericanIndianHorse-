/**
 * (C) Stammtisch
 * First version created by: Jonathan Bones & Peter Mills
 * Date of first version: 17/05/2016
 * 
 * Last version by: Jonathan Bones & Peter Mills
 * Date of last update: 22/05/2016
 * Version number: 1.3
 * 
 * Commit date: 22/05/2015
 * Description: Simple class for testing SQL presentation and user account databases
 *
 */

package SQL;

import java.sql.*;
import java.util.ArrayList;
import com.*;

import com.Presentation;

public class SQLTester 
{
	
	public static void main(String[] args)
	{
		String server = "stammtischsql.ddns.net";
		int port = 3306;
		String presentationDatabase = "presentations";
		String presTable = "testpresentations";
		
		//Connect to the database on the SQL server
		Connection presCon = SQLServer.connect(server, port, presentationDatabase);
		
		/*
		//===========================================================================
		//Add a presentation to the SQL presentations table
		
		Presentation pres = new Presentation();
		//Set the presenation metadata
		pres.setAuthor("Le bleu");
		pres.setTitle("Saturday Sweng");
		pres.setLanguage("French");
		pres.setTagOne(null);
		pres.setTagTwo(null);
		pres.setTagThree(null);
		pres.setTagFour(null);
		pres.setTagFive(null);
		//QueryPresentations.addPresentation(presCon, presTable, pres); //Add the presentation to the SQL presentation table
		*/
		
		/*
		//===========================================================================
		//Delete the presentation from the SQL presentations table
		QueryPresentations.deletePresentation(presCon, presTable, pres.getTitle(), pres.getAuthor()); //Delete the specified presentation from the SQL presentation table
		*/
		
		/*
		//===========================================================================
		//Search the presentations table for a specific presentation
		ArrayList<String[]> searchResults = new ArrayList<String[]>(); //Define an arraylist for the search results
		searchResults = QueryPresentations.searchPresentation(presCon, presTable, pres); //search the presenation table for the specified presentation
		
		//Print out the presentation list currently available on the SQL presentations table
		for(int i = 0; i<searchResults.size(); i++)
		{
      System.out.print("Presentation " + (i+1) + " is: ");
      for (int j = 0; j < 3; j++)
      {
      	switch(j)
      	{
	      	case 0:
	      		System.out.print(" '" + searchResults.get(i)[j] + "' ");
	      		break;
	      	case 1:
	      		System.out.print("by " + searchResults.get(i)[j] + " ");
	      		break;
	      	case 2:
	      		System.out.println(" (" + searchResults.get(i)[j] + ") ");
	      		break;
      	}
      }
  	}
		*/
		
		
		//===========================================================================
		//Add a user to the user database
		String userDatabase = "useraccounts";
		String userTable = "users";
		Connection userCon = SQLServer.connect(server, port, userDatabase);
		User user1 = new User();
		
		//Example test data for user account
		user1.setUsername("Tangents4Life");
		user1.setEmail("myemail@email.com");
		user1.setPassword("A secret word");
		user1.setDob("1800-01-01");
		boolean result = QueryUsers.checkUser(userCon, userTable, user1);
		System.out.println("Result of check was: " + result);
		//QueryUsers.addUser(userCon, userTable, user1); //Add user to the SQL user account table
		
		
		/*
		//===========================================================================
		//Delete the user from the database
		QueryUsers.deleteUser(userCon, userTable, "test123", "pass"); //Delete user which matches the username and password fields from the SQL user account table
		*/
		
		/*
		//===========================================================================
		//Check if a user exists on the database
		boolean result = QueryUsers.checkUser(userCon, userTable, user1); //Search the user account table for the user account defined
			System.out.println("User found? " + result);
		*/
		
		/*
		//===========================================================================
		//Create a field in the user stats table for the specified user
		String statsDatabase = "usertracking";
		Connection userStatsCon = SQLServer.connect(server, port, statsDatabase);
		QueryUsers.createUserStats(userCon, user1);
		*/
		
		/*
		//===========================================================================
		//Test for presentation first Access:
		String statsDatabase = "usertracking";
		Connection userTrackingCon = SQLServer.connect(server, port, statsDatabase);
		User user1 = new User();
		user1.setUsername("Tangents4Life");
		user1.setEmail("myemail@email.com");
		user1.setPassword("A secret word");
		user1.setDob("1800-01-01");
		
		Presentation pres = new Presentation();
		pres.setAuthor("Le bleu");
		pres.setTitle("Saturday Sweng");
		pres.setLanguage("French");
		pres.setTagOne(null);
		pres.setTagTwo(null);
		pres.setTagThree(null);
		pres.setTagFour(null);
		pres.setTagFive(null);
		
		QueryUsers.userFirstAccess(userTrackingCon, presCon, user1, pres);
		*/
		
		/*
	  //===========================================================================
		// Update User rating of specified presentation
		//===========================================================================
		String userDatabase = "useraccounts";
		String userTable = "users";
		Connection userCon = SQLServer.connect(server, port, userDatabase);
		
		String statsDatabase = "usertracking";
		Connection userTrackingCon = SQLServer.connect(server, port, statsDatabase);
		
		//Create three users for testing of the user rating system
		//N.B. Spaces or semicolons are forbidden characters for the usernames
		User user1 = new User();
		user1.setUsername("Tangents4Life");
		user1.setEmail("myemail@email.com");
		user1.setPassword("A secret word");
		user1.setDob("1800-01-01");
		
		User user2 = new User();
		user2.setUsername("felix");
		user2.setEmail("felix@gmail.com");
		user2.setPassword("hush hush");
		user2.setDob("2001-05-12");
		
		User user3 = new User();
		user3.setUsername("Topcat");
		user3.setEmail("topcat@yahoo.cat");
		user3.setPassword("Kitten");
		user3.setDob("1961-09-27");
		
		//Add the users to the database which also creates each tracking table
		QueryUsers.addUser(userCon, userTrackingCon, userTable, user1);
		QueryUsers.addUser(userCon, userTrackingCon, userTable, user2);
		QueryUsers.addUser(userCon, userTrackingCon, userTable, user3);

		//Create a presentation to be rated by the users
		Presentation pres = new Presentation();
		pres.setAuthor("Le bleu");
		pres.setTitle("Saturday Sweng");
		pres.setLanguage("French");
		pres.setTagOne(null);
		pres.setTagTwo(null);
		pres.setTagThree(null);
		pres.setTagFour(null);
		pres.setTagFive(null);
		
		//Signal that each user has accessed the presentation
		QueryUsers.userFirstAccess(userTrackingCon, presCon, user1, pres);
		QueryUsers.userFirstAccess(userTrackingCon, presCon, user2, pres);
		QueryUsers.userFirstAccess(userTrackingCon, presCon, user3, pres);
		
		//Rate the presentation from each user - the globalrating variable is the sum of these
		QueryUsers.setUserRating(userTrackingCon, presCon, user1, pres, -1); //Indicates dislike
		QueryUsers.setUserRating(userTrackingCon, presCon, user2, pres, 1); //Indicates like
		QueryUsers.setUserRating(userTrackingCon, presCon, user3, pres, 0); //Indicates no rating
		*/
		
		/*
		//===========================================================================
		// Add comments into the comments table
		//===========================================================================
		User user1 = new User();
		user1.setUsername("Tangents4Life");
		user1.setEmail("myemail@email.com");
		user1.setPassword("A secret word");
		user1.setDob("1800-01-01");
		
		User user2 = new User();
		user2.setUsername("felix");
		user2.setEmail("felix@gmail.com");
		user2.setPassword("hush hush");
		user2.setDob("2001-05-12");
		
		User user3 = new User();
		user3.setUsername("Topcat");
		user3.setEmail("topcat@yahoo.cat");
		user3.setPassword("Kitten");
		user3.setDob("1961-09-27");
		
		String userDatabase = "useraccounts";
		String userTable = "users";
		Connection userCon = SQLServer.connect(server, port, userDatabase);
		
		Presentation pres = new Presentation();
		pres.setAuthor("Le bleu");
		pres.setTitle("Saturday Sweng");
		pres.setLanguage("French");
		pres.setTagOne(null);
		pres.setTagTwo(null);
		pres.setTagThree(null);
		pres.setTagFour(null);
		pres.setTagFive(null);
		
		int presID = SQLTools.checkPresID(presCon, pres); //Obtain the presentation ID
		
		//Append comments to the comments table for the specified presentation
		QueryPresentations.addComment(presCon, userCon, presID, user1, "What is going on?");
		QueryPresentations.addComment(presCon, userCon, presID, user2, "Great presentation!");
		QueryPresentations.addComment(presCon, userCon, presID, user3, "Really helped!");
		*/
		
		/*
		//===========================================================================
		// Delete particular comment from the comments table
		//===========================================================================
		int userID = SQLTools.checkUserID(userCon, user1);
		int commentID = 2; //This will be provided to the admin
		QueryPresentations.removeComment(presCon, userID, presID, commentID);
		*/
		
		/*
		//===========================================================================
		// Display all comments in the comments table
		//===========================================================================
		ArrayList<String[]> searchResults = new ArrayList<String[]>();
		searchResults = QueryPresentations.searchComments(presCon, presID);
		
		//Print out the comments list currently available on the SQL presentation table
			for(int i = 0; i<searchResults.size(); i++)
			{
	      System.out.print("Presentation " + (i+1) + " is: ");
	      for (int j = 0; j < 3; j++)
	      {
	      	switch(j)
	      	{
		      	case 0:
		      		System.out.print("'" + searchResults.get(i)[j] + "' ");
		      		break;
		      	case 1:
		      		System.out.print("says '" + searchResults.get(i)[j] + "' ");
		      		break;
		      	case 2:
		      		System.out.println(" (" + searchResults.get(i)[j] + ") ");
		      		break;
	      	}
	      }
			}
			*/
	}	
}
