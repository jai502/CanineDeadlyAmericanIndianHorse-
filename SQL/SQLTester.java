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
		SQLHandler sqlHandler = new SQLHandler();
		
		/*
		//===========================================================================
		//Add a presentation to the SQL presentations table
		//===========================================================================
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
		
		sqlHandler.addPresentation(pres);
		*/
		
		/*
		//===========================================================================
		//Delete the presentation from the SQL presentations table
		//===========================================================================
		Presentation pres = new Presentation();
		pres.setTitle("presentation");
		sqlHandler.deletePresentation(pres.getTitle(), pres.getAuthor());
		*/
		
		/*
		//===========================================================================
		//Search the presentations table for a specific presentation
		//===========================================================================
		Presentation pres = new Presentation();
		pres.setTitle("presentation");
		
		ArrayList<String[]> searchResults = new ArrayList<String[]>(); //Define an arraylist for the search results
		searchResults = sqlHandler.searchPresentation(pres);
		
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
		
		/*
		//===========================================================================
		//Add a user to the user database
		//===========================================================================
		User user1 = new User(); //Example test data for user account
		user1.setUsername("Tangents4Life");
		user1.setEmail("myemail@email.com");
		user1.setPassword("A secret word");
		user1.setDob("1800-01-01");
		
		sqlHandler.addUser(user1);
		*/
		
		/*
		//===========================================================================
		//Delete the user from the database
		//===========================================================================
		User user2 = new User();
		user2.setUsername("DeleteMe");
		sqlHandler.deleteUser(user2.getUsername(), user2.getPassword());
		*/
		
		/*
		//===========================================================================
		//Check if a user exists on the database
		//===========================================================================
		boolean result = QueryUsers.checkUser(userCon, userTable, user1); //Search the user account table for the user account defined
			System.out.println("User found? " + result);
		*/

		/*
		//===========================================================================
		//Check the user login details
		//===========================================================================
		User user2 = new User();
		user2.setUsername("CheckLogin");
		boolean check = sqlHandler.checkLoginDetails(user2);
		System.out.println("Result of the login details check was: " + check);
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
		//===========================================================================
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
		
		sqlHandler.userFirstAccess(user1, pres);
		*/
		
		/*
	  //===========================================================================
		// Update User rating of specified presentation
		//===========================================================================
		
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
		sqlHandler.addUser(user1);
		sqlHandler.addUser(user2);
		sqlHandler.addUser(user3);

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
		sqlHandler.userFirstAccess(user1, pres);
		sqlHandler.userFirstAccess(user2, pres);
		sqlHandler.userFirstAccess(user3, pres);
		
		//Rate the presentation from each user - the globalrating variable is the sum of these
		sqlHandler.setUserRating(user1, pres, -1);//Indicates dislike
		sqlHandler.setUserRating(user2, pres, 1);//Indicates like
		sqlHandler.setUserRating(user3, pres, 0);//Indicates no rating
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
		sqlHandler.addComment(presID, user1, "What is going on?");
		sqlHandler.addComment(presID, user2, "Great presentation!");
		sqlHandler.addComment(presID, user3, "Really helped!");
		*/
		
		/*
		//===========================================================================
		// Delete particular comment from the comments table
		//===========================================================================
		int userID = SQLTools.checkUserID(userCon, user1);
		int commentID = 2; //This will be provided to the admin
		sqlHandler.removeComment(userID, presID, commentID);
		*/
		
		/*
		//===========================================================================
		// Display all comments in the comments table
		//===========================================================================
		ArrayList<String[]> searchResults = new ArrayList<String[]>();
		searchResults = sqlHandler.searchComments(presID);
		
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
