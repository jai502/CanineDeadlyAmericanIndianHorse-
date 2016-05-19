/**
 * (C) Stammtisch
 * First version created by: Jonathan Bones & Peter Mills
 * Date of first version: 17/05/2016
 * 
 * Last version by: Jonathan Bones & Peter Mills
 * Date of last update: 18/05/2016
 * Version number: 1.1
 * 
 * Commit date: 18/06/2015
 * Description: Simple class for testing SQL presentation and user account databases
 *
 */

package SQL;

import java.sql.*;
import java.util.ArrayList;
import SQL.Presentation;

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
		pres.setAuthor(null);
		pres.setTitle("testDemo");
		pres.setLanguage(null);
		pres.setTagOne(null);
		pres.setTagTwo(null);
		pres.setTagThree(null);
		pres.setTagFour(null);
		pres.setTagFive(null);
		QueryPresentations.addPresentation(presCon, presTable, pres); //Add the presentation to the SQL presentation table
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
		/*
		//===========================================================================
		//Create presentation statistics data on the presentations table
		QueryPresentations.createPresentationStats(presCon, pres);
		*/
		
		/*
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
		
		QueryUsers.addUser(userCon, userTable, user1); //Add user to the SQL user account table
		*/
		
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
		pres.setAuthor("J.Everard");
		pres.setTitle("Analogue Filters");
		pres.setLanguage("Electronics");
		pres.setTagOne(null);
		pres.setTagTwo(null);
		pres.setTagThree(null);
		pres.setTagFour(null);
		pres.setTagFive(null);
		
		QueryUsers.userFirstAccess(userTrackingCon, presCon, user1, pres);
		 */
	}	
}
