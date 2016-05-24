/**
 * (C) Stammtisch
 * First version created by: Jonathan Bones & Peter Mills
 * Date of first version: 18/05/2016
 * 
 * Last version by: Jonathan Bones & Peter Mills
 * Date of last update: 21/05/2016
 * Version number: 1.2
 * 
 * Commit date: 21/05/2015
 * Description: Simple class for handling user account SQL table requests
 *
 */

package SQL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.*;

import java.sql.*;

public class QueryUsers {
//Add a user using function argument values
	public static void addUser(Connection userCon, Connection userTrackCon, String table, User user) 
	{
		PreparedStatement command = null;
		String sqlAddUsr = "INSERT INTO " + table + " (username, password, email, dob) VALUES (?, ?, ?, ?)";
		
		try {
			// Create new JDBC statement
			command = userCon.prepareStatement(sqlAddUsr);
			command.setString(1, user.getUsername());
			command.setString(2, user.getPassword());
			command.setString(3, user.getEmail());
			command.setString(4, user.getDob());
			
			int row = command.executeUpdate();
			System.out.println("User successfully added to SQL table with code: " + row);
			
		} catch (SQLException e) {
			System.out.println("Unable to add user");
			e.printStackTrace();
		} finally {
	        if (command != null) { 
	        	try {
	        		command.close();
	        	} catch (SQLException e) {
	        	// Failed to close command
				e.printStackTrace();
	        	} 
	        }
	    }
		//Now create the user stats
		createUserStats(userTrackCon, user);
	}
	
	// Delete the user with username and password matching function argument values
	public static void deleteUser(Connection con, String table, String username, String password)
	{
		PreparedStatement command = null;
		String sqlDeleteUsr = "DELETE FROM " + table + " WHERE username = (?) AND password = (?)";
		try {
			// Create new JDBC statement
			command = con.prepareStatement(sqlDeleteUsr);
			command.setString(1, username);
			command.setString(2, password);
			int row = command.executeUpdate();
			System.out.println("User " + username + " was deleted with code: " + row);
			
		} catch (SQLException e) {
			System.out.println("Unable to delete user");
			e.printStackTrace();
		} finally {
	        if (command != null) { 
	        	try {
	        		command.close();
	        	} catch (SQLException e) {
	        	// Failed to close command
				e.printStackTrace();
	        	} 
	        }
	    }
		
	}
	
	// Check if a user exists in a database
	public static boolean checkUser(Connection con, String table, User user)
	{
		Statement command = null;
		
		Boolean result = false;
		ResultSet data;
		
		try {
			// Create new JDBC statement
			command = con.createStatement();
			// Execute SQL SELECT FROM command
			data = command.executeQuery("SELECT id, username FROM " + table
											+ " WHERE username = "
											+ "(" + SQLTools.testNull(user.getUsername()) + ")"
											+ " OR email = "
											+ "(" + SQLTools.testNull(user.getEmail()) + ")"
										);
			// Extract data from results set
			 while(data.next())
			 {
				 // Check if fetched username is equal to expected username
				 if (user.getUsername().equals(data.getString("username")))
				 {
					 result = true;
				 }
			 }
		} catch (SQLException e) {
			System.out.println("Unable to find user");
			e.printStackTrace();
		} finally {
	        if (command != null) { 
	        	try {
	        		command.close();
	        	} catch (SQLException e) {
	        	// Failed to close command
				e.printStackTrace();
	        	} 
	        }
	    }
		
		return result;
	}
	
	public static void createUserStats(Connection con, User user)
	{
		//Careful of injection!
		Statement command = null;
		String sqlStats = "CREATE TABLE " + user.getUsername() + "_tracking (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ " presentationid INT NOT NULL, presentationname VARCHAR(100) NOT NULL,"
				+ " userrating INT NOT NULL, userprogress INT NOT NULL)";
		
		try {
			command = con.createStatement();
			command.executeUpdate(sqlStats);
			System.out.println("Successfully created tracking table for user: " + user.getUsername());
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
	
	//======================================================================================================================
	// Method for creation of table with presentation and user tracking details
	//======================================================================================================================
	public static void userFirstAccess(Connection userDetailsCon, Connection presCon, User user, Presentation pres)
	{
		Statement command = null;
		int presID = SQLTools.checkPresID(presCon, pres); //Check for presentation and return presentation ID
		
		String sqlFirstAccess = "INSERT INTO " + user.getUsername() 
				+ "_tracking (presentationid, presentationname, userrating,userprogress)"
				+ " VALUES (" + presID + ", '" + pres.getTitle() + "', 0, 0)" ;
		try {
			command = userDetailsCon.createStatement();
			command.executeUpdate(sqlFirstAccess);
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
		System.out.println("Updated user: " + user.getUsername() + " for presentation: " + pres.getTitle());
	}
	
  //======================================================================================================================
	// Method for updating user rating of the specified presentation
	//======================================================================================================================
	public static void setUserRating(Connection userDetailsCon, Connection presCon, User user, Presentation pres, int userRating){
		String userTrackingTable = user.getUsername() + "_tracking";
		String presentationsTable = "testpresentations";
		Statement userCommand = null;
		Statement presCommand = null;
		
		int presID = SQLTools.checkPresID(presCon, pres);
		int currentUserRating = SQLTools.checkPreviousRating(presID, userDetailsCon, user);
		int newUserRating = 0;
		
		if(currentUserRating == -1)
		{
			switch(userRating){
				case -1:
					newUserRating = 0;
					break;
				case 0:
					newUserRating = 1;
					break;
				case 1:
					newUserRating = 2;
					break;
			}
		}
		else if(currentUserRating == 0)
		{
			switch(userRating){
			case -1:
				newUserRating = -1;
				break;
			case 0:
				newUserRating = 0;
				break;
			case 1:
				newUserRating = 1;
				break;
			}
		}
		else if(currentUserRating == 1)
		{
				switch(userRating){
				case -1:
					newUserRating = -2;
					break;
				case 0:
					newUserRating = -1;
					break;
				case 1:
					newUserRating = 0;
					break;
				}
		}
		
		String updateUserRating = "UPDATE " + userTrackingTable 
				+ " SET userrating = " + userRating 
				+ " WHERE presentationid = " + presID 
				+ " AND presentationname = '" + pres.getTitle() + "'";
		
		try {
			userCommand = userDetailsCon.createStatement();
			userCommand.executeUpdate(updateUserRating);
			System.out.println("Updated user tracking, now updating global rating...");
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			if (userCommand != null)
      {
      	try
      	{
      		userCommand.close();
      	} 
      	catch (SQLException e) 
      	{
      		// Failed to close command
      		e.printStackTrace();
      	} 
      }
		}
		
		String updateGlobalRating = "UPDATE " + presentationsTable + " SET totalrating = totalrating+" + newUserRating + " WHERE id = " + presID;
		
		try {
			presCommand = presCon.createStatement();
			presCommand.executeUpdate(updateGlobalRating);
			System.out.println("Updated global rating");
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
			if (presCommand != null)
      {
      	try
      	{
      		presCommand.close();
      	} 
      	catch (SQLException e) 
      	{
      		// Failed to close command
      		e.printStackTrace();
      	} 
      }
		}
	}
}
