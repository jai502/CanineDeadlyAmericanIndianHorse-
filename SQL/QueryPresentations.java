/**
 * (C) Stammtisch
 * First version created by: Jonathan Bones & Peter Mills
 * Date of first version: 17/05/2016
 * 
 * Last version by: Jonathan Bones & Peter Mills
 * Date of last update: 22/05/2016
 * Version number: 1.3
 * 
 * Commit date: 22/05/2016
 * Description: Access to presentation database
 */

package SQL;

import java.sql.*;
import java.util.ArrayList;
import SQL.Presentation;
import SQL.SQLTools;

public class QueryPresentations
{
	
	public static void addPresentation(Connection con, String table,  Presentation pres)
	{
		PreparedStatement command = null;
		//Set rating to a new presentation as 0
		String sqlAdd = "INSERT INTO " + table + " (title, languagetype, author, tagone, tagtwo, tagthree, tagfour, tagfive, totalrating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0)";
	
		try 
		{
			//Add the presentation data into the statment placeholders (?)
			command = con.prepareStatement(sqlAdd);
			command.setString(1, pres.getTitle());
			command.setString(2, pres.getLanguage());
			command.setString(3, pres.getAuthor());
			command.setString(4, pres.getTagOne());
			command.setString(5, pres.getTagTwo());
			command.setString(6, pres.getTagThree());
			command.setString(7, pres.getTagFour());
			command.setString(8, pres.getTagFive());
			
			int row = command.executeUpdate(); //Add the presentation into the SQL table
			System.out.println("Presentation successfully added to SQL table with code: " + row);
		}
		catch (SQLException e) 
		{
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
		
		//Create a linked Comments table for the newly created presentation
		int presID = SQLTools.checkPresID(con, pres);
		createUserCommentsTable(con, presID);
		
	}
	
	//======================================================================================================================
	// Method for creating user comments table linked to each new presentation creation
	//======================================================================================================================
	public static void createUserCommentsTable(Connection con, int presID)
	{
		Statement command = null;
		String sqlComments = "CREATE TABLE " + presID + "_comments (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ " userid INT NOT NULL, username VARCHAR(100) NOT NULL,"
				+ " comment VARCHAR(500), timeleft TIMESTAMP)";
		
		try {
			command = con.createStatement();
			command.executeUpdate(sqlComments);
			System.out.println("Successfully created comments table for presentation with ID: " + presID);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
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
	}
	
	public static void deletePresentation(Connection con, String table, String title, String author)
	{
		PreparedStatement command = null;
		String sqlDelete = "DELETE FROM " + table + " WHERE title = (?) AND author = (?)"; //SQL code for delete
		
		try 
		{
			command = con.prepareStatement(sqlDelete); //Prepare the statement for execution
			
			//Update the ? placeholders with the required fields
			command.setString(1, title);
			command.setString(2, author);
			
			int rows = command.executeUpdate(); //Execute the deletion
			System.out.println("Presentation successfully deleted from SQL server with code: " + rows);
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			//If statement had been initialised then flush
			if (command != null) 
			{
				try 
				{
					command.close();
				}
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static ArrayList<String[]> searchPresentation(Connection con, String table, Presentation pres)
	{		
		//Note - this is susceptible to malicious attacks - ensure that semicolons are checked first in the input data!
		Statement command = null;
		
		String sqlSearch = "SELECT title, author, languagetype FROM " + table
				+ " WHERE title = COALESCE(" + SQLTools.testNull(pres.getTitle()) + ", title)"
				+ " OR author = COALESCE(" + SQLTools.testNull(pres.getAuthor()) + ", author)"
				+ " OR languagetype = COALESCE(" + SQLTools.testNull(pres.getLanguage()) + ", languagetype)"
				+ " OR tagone = COALESCE(" + SQLTools.testNull(pres.getTagOne()) + ", tagone)"
				+ " OR tagtwo = COALESCE(" + SQLTools.testNull(pres.getTagTwo()) + ", tagtwo)"
				+ " OR tagthree = COALESCE(" + SQLTools.testNull(pres.getTagThree()) + ", tagthree)"
				+ " OR tagfour = COALESCE(" + SQLTools.testNull(pres.getTagFour()) + ", tagfour)"
				+ " OR tagfive = COALESCE(" + SQLTools.testNull(pres.getTagFive()) + ", tagfive)";
		
		ArrayList<String[]> searchResults = new ArrayList<String[]>();
		ResultSet data;
		
		try 
		{
			command = con.createStatement();
			data = command.executeQuery(sqlSearch);
			int index = 0;
			
			while(data.next())
			{
				String[] searchResult = new String[3];
				searchResult[0] = data.getString("title");
				searchResult[1] = data.getString("author");
				searchResult[2] = data.getString("languagetype");
				
				searchResults.add(index, searchResult);
				
				index++; //increment the index
			}
			
		}
		catch (SQLException e) 
		{
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
		return searchResults;
	}
	
	public static void createPresentationStats(Connection con, Presentation pres)
	{
		//Careful of injection!
		Statement command = null;
		String sqlComments = "CREATE TABLE " + pres.getTitle() + "_comments ("
				+ "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ " commentcontent VARCHAR(200) NOT NULL,"
				+ " user VARCHAR(100) NOT NULL,"
				+ " commenttimestamp TIMESTAMP NOT NULL);";
		
		try {
			command = con.createStatement();
			command.executeUpdate(sqlComments);
			System.out.println("Successfully created comments table for presentation: " + pres.getTitle());
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
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
		
	}
	
	public static void addComment(Connection presCon, Connection userCon, int presID, User user, String comment)
	{
		int userID = SQLTools.checkUserID(userCon, user);
		PreparedStatement command = null;
		String sqlComment = "INSERT INTO " + presID + "_comments (userid, username, comment) VALUES (?, ?, ?)";
		
		try 
		{
			command = presCon.prepareStatement(sqlComment);
			command.setInt(1, userID);
			command.setString(2, user.getUsername());
			command.setString(3, comment);
			
			command.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally {
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
	}
	
	public static void removeComment(Connection presCon, int userID, int presID, int commentID)
	{
		Statement command = null;
		String sqlRemove = "DELETE FROM " + presID + "_comments WHERE userid = " + userID + " AND id = " + commentID;
		
		try {
			command = presCon.createStatement();
			command.executeUpdate(sqlRemove);
			System.out.println("[INFO] Comment with id: " + commentID + " was successfully removed from presentation:" + presID);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
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
		
	}
	
	public static ArrayList<String[]> searchComments(Connection con, int presID)
	{		
		//Note - this is susceptible to malicious attacks - ensure that semicolons are checked first in the input data!
		Statement command = null;
		ArrayList<String[]> searchResults = new ArrayList<String[]>();
		ResultSet data;
		
		try 
		{
			command = con.createStatement();
			data = command.executeQuery("SELECT username, comment, timeleft FROM "+ presID +"_comments");
			int index = 0;
			
			while(data.next())
			{
				String[] searchResult = new String[3];
				
				searchResult[0] = data.getString("username");
				searchResult[1] = data.getString("comment");
				searchResult[2] = data.getTimestamp("timeleft").toString();
				
				searchResults.add(index, searchResult);
				
				index++; //increment the index
			}
			
		}
		catch (SQLException e) 
		{
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
		return searchResults;
	}
}
