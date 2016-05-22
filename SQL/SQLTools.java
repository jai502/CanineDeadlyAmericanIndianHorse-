/**
 * (C) Stammtisch
 * First version created by: Jonathan Bones & P.Mills
 * Date of first version: 18/05/2016
 * 
 * Last version by: Jonathan Bones & P.Mills
 * Date of last update: 21/05/2016
 * Version number: 1.2
 * 
 * Commit date: 21/05/2016
 * Description: Class to aid SQL statement creation
 */

package SQL;

import java.sql.*;

public class SQLTools 
{
  //============================================================================
	//Method for adapting the SQL statement with quotation marks,
	//dependent on whether the string is null or not
	//============================================================================
	public static String testNull(String testString)
	{
		String outputString;
		
		if(testString == null)
		{
			//return null if the string is null
			outputString = null;
		}
		else
		{
		  //Append '' marks around the string if not null
			outputString = "'" + testString +"'";  
		}
		return outputString;
	}
	
  //============================================================================
	//Method for obtaining the ID of a presentation on the SQL presentation table
	//============================================================================
	public static Integer checkPresID(Connection con, Presentation pres)
	{
		Statement command = null; //SQL command
		Integer presID = new Integer(0);//The presentation ID number
		
		//Table location of presentations on the SQL server
		String presentationsTable = "testpresentations";
		ResultSet data; //The result set returned from the SQL server
		
		//The SQL statement for checking for a specified presentation on the server
		String sqlCheckPres = "SELECT id FROM " + presentationsTable
				+ " WHERE title= '" + pres.getTitle() 
				+ "' AND author= '" + pres.getAuthor() + "'";
		
		try {
			//Create the SQL command and execute the query
			command = con.createStatement();
			data = command.executeQuery(sqlCheckPres);
			
			while(data.next())
			{
				//Obtain the presentation ID from the query result
				presID = data.getInt("id");
				System.out.println("Returned id is: " + presID);
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			if (command != null)
      {
      	try
      	{
      		command.close();
      	} 
      	catch (SQLException e) 
      	{
      		// Failed to close command
      		e.printStackTrace();
      	} 
      }
		}
		return presID;
	}
	
	//============================================================================
	//Method for obtaining the ID of a user on the SQL user table
	//============================================================================
	public static Integer checkUserID(Connection con, User user)
	{
		Statement command = null; //SQL command
		Integer userID = new Integer(0);//The presentation ID number
		
		//Table location of presentations on the SQL server
		String userTable = "users";
		ResultSet data; //The result set returned from the SQL server
		
		//The SQL statement for checking for a specified presentation on the server
		String sqlCheckUser = "SELECT id FROM " + userTable
				+ " WHERE username= '" + user.getUsername() +  "'";
		
		try {
			//Create the SQL command and execute the query
			command = con.createStatement();
			data = command.executeQuery(sqlCheckUser);
			
			while(data.next())
			{
				//Obtain the presentation ID from the query result
				userID = data.getInt("id");
				System.out.println("Returned id is: " + userID);
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			if (command != null)
      {
      	try
      	{
      		command.close();
      	} 
      	catch (SQLException e) 
      	{
      		// Failed to close command
      		e.printStackTrace();
      	} 
      }
		}
		return userID;
	}
	
	
  //======================================================================================
	//Method for checking the existing rating of a presentation
	//======================================================================================
	public static Integer checkPreviousRating(int presID, Connection con, User user)
	{
		Statement command = null;
		String table = user.getUsername() + "_tracking";
		String checkRatingSQL = "SELECT userrating FROM " + table + " WHERE presentationid = " + presID;
		Integer userRating = new Integer(0);
		
		try {
			command = con.createStatement();
			ResultSet data = command.executeQuery(checkRatingSQL);
			
			while(data.next())
			{
				userRating = data.getInt("userrating");
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			if (command != null)
      {
      	try
      	{
      		command.close();
      	} 
      	catch (SQLException e) 
      	{
      		// Failed to close command
      		e.printStackTrace();
      	} 
      }
		}
			return userRating;
	}
}
