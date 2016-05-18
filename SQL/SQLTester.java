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
		Presentation pres = new Presentation();;
		
		//Connect to the database on the SQL server
		Connection presCon = SQLServer.connect(server, port, presentationDatabase);
		
		//Set the presenation metadata
		pres.setAuthor(null);
		pres.setTitle(null);
		pres.setLanguage(null);
		pres.setTagOne(null);
		pres.setTagTwo(null);
		pres.setTagThree(null);
		pres.setTagFour(null);
		pres.setTagFive(null);
		
		QueryPresentations.addPresentation(presCon, presTable, pres); //Add the presentation to the SQL presentation table
		QueryPresentations.deletePresentation(presCon, presTable, pres.getTitle(), pres.getAuthor()); //Delete the specified presentation from the SQL presentation table
		
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
		QueryUsers.deleteUser(userCon, userTable, "test123", "pass"); //Delete user which matches the username and password fields from the SQL user account table
		
		boolean result = QueryUsers.checkUser(userCon, userTable, user1); //Search the user account table for the user account defined
		System.out.println("User found? " + result);
	}	
}
