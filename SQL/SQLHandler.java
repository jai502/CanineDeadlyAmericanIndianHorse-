/**
 * (C) Stammtisch
 * First version created by: Jonathan Bones & Peter Mills
 * Date of first version: 24/05/2016
 * 
 * Last version by: Jonathan Bones & Peter Mills
 * Date of last update: 24/05/2016
 * Version number: 1.0
 * 
 * Commit date: 24/05/2016
 * Description: Refactor of SQL classes into single handler class
 */

package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import com.PresentationShell;
import com.User;

public class SQLHandler 
{
	private String host;
	private int port;
	
	private String presentationDatabase = "presentations";
	private String presTable = "testpresentations";
	private String presCommentsTable = "_comments";
	
	private String userDatabase = "useraccounts";
	private String userTable = "users";
	
	private String userTrackingDatabase = "usertracking";
	private String userTrackingTable = "_tracking";
	
	private Connection userCon, presCon, userTrackingCon;
	
	public SQLHandler(String SQLHost, int SQLPort)
	{
		this.port = SQLPort;
		this.host = SQLHost;
		
		//Connect to the SQL databases available on the SQL server
		this.presCon = SQLServer.connect(host, port, presentationDatabase);
		this.userCon = SQLServer.connect(host, port, userDatabase);
		this.userTrackingCon = SQLServer.connect(host, port, userTrackingDatabase);
	}
	
	//======================================================================================================================
	// Method for adding a presentation with associated comments database
	//======================================================================================================================
	public final void addPresentation(PresentationShell pres)
	{
		PreparedStatement command = null;
		//Set rating to a new presentation as 0
		String sqlAdd = "INSERT INTO " + presTable + " (title, languagetype, author, tagone, tagtwo, tagthree, tagfour, tagfive, totalrating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0)";
	
		try 
		{
			//Add the presentation data into the statment placeholders (?)
			command = presCon.prepareStatement(sqlAdd);
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
		int presID = SQLTools.checkPresID	(presCon, pres);
		createUserCommentsTable(presID);
		
	}
	
	//======================================================================================================================
	// Method for creating user comments table linked to each new presentation creation
	//======================================================================================================================
	public final void createUserCommentsTable(int presID)
	{
		Statement command = null;
		String sqlComments = "CREATE TABLE " + presID + "_comments (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ " userid INT NOT NULL, username VARCHAR(100) NOT NULL,"
				+ " comment VARCHAR(500), timeleft TIMESTAMP)";
		
		try {
			command = presCon.createStatement();
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
	
	//======================================================================================================================
	// Method for deleting presentation
	// Note - Server side access ONLY
	//======================================================================================================================
	public final void deletePresentation(String title, String author)
	{
		PreparedStatement command = null;
		String sqlDelete = "DELETE FROM " + presTable + " WHERE title = (?) AND author = (?)";
		
		try 
		{
			command = presCon.prepareStatement(sqlDelete);
			command.setString(1, title);
			command.setString(2, author);
			
			int rows = command.executeUpdate();
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
	
	//======================================================================================================================
	// Method for searching the SQL presentation database
	//======================================================================================================================
	public final ArrayList<String[]> searchPresentation(PresentationShell pres)
	{		
		//Note - this is susceptible to malicious attacks - ensure that semicolons are checked first in the input data!
		Statement command = null;
		
		String sqlSearch = "SELECT title, author, languagetype FROM " + presTable
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
			command = presCon.createStatement();
			data = command.executeQuery(sqlSearch);
			int index = 0;
			
			while(data.next())
			{
				String[] searchResult = new String[3];
				searchResult[0] = data.getString("title");
				searchResult[1] = data.getString("author");
				searchResult[2] = data.getString("languagetype");
				searchResults.add(index, searchResult);
				
				index++;
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
	
	//======================================================================================================================
	// Method for creating comments table linked to specified presentation
	//======================================================================================================================
	public final void createLinkedComments(PresentationShell pres)
	{
		Statement command = null;
		String sqlComments = "CREATE TABLE " + pres.getTitle() + "_comments ("
				+ "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ " commentcontent VARCHAR(200) NOT NULL,"
				+ " user VARCHAR(100) NOT NULL,"
				+ " commenttimestamp TIMESTAMP NOT NULL);";
		
		try {
			command = presCon.createStatement();
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
	
	//======================================================================================================================
	// Method for adding comments linked to the specified presentation
	//======================================================================================================================
	public final void addComment(int presID, User user, String comment)
	{
		int userID = SQLTools.checkUserID(userCon, user);
		PreparedStatement command = null;
		String sqlComment = "INSERT INTO " + presID + presCommentsTable + " (userid, username, comment) VALUES (?, ?, ?)";
		
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
	
	//======================================================================================================================
	// Method for removing comments linked to the specified presentation
	// Note - server side access ONLY
	//======================================================================================================================
	public final void removeComment(int userID, int presID, int commentID)
	{
		Statement command = null;
		String sqlRemove = "DELETE FROM " + presID + presCommentsTable + " WHERE userid = " + userID + " AND id = " + commentID;
		
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
	
	//======================================================================================================================
	// Method for searching the comments database
	//======================================================================================================================
	public final ArrayList<String[]> searchComments(int presID)
	{		
		//Note - this is susceptible to malicious attacks - ensure that semicolons are checked first in the input data!
		Statement command = null;
		ArrayList<String[]> searchResults = new ArrayList<String[]>();
		ResultSet data;
		
		try 
		{
			command = presCon.createStatement();
			data = command.executeQuery("SELECT username, comment, timeleft FROM "+ presID + presCommentsTable);
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
	
	//======================================================================================================================
	// Method for adding user to the user database
	//======================================================================================================================
	public final void addUser(User user) 
	{
		PreparedStatement command = null;
		String sqlAddUsr = "INSERT INTO " + userTable + " (username, password, email, dob) VALUES (?, ?, ?, ?)";
		
		try {
			// Create new JDBC statement
			command = userCon.prepareStatement(sqlAddUsr);
			command.setString(1, user.getUsername());
			command.setString(2, user.getPassword());
			command.setString(3, user.getEmail());
			command.setString(4, user.getDob().toString());
	
			int row = command.executeUpdate();
			System.out.println("User successfully added to SQL table with code: " + row);
			
		} 
		catch (SQLException e)
		{
			System.out.println("Unable to add user");
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
		//Now create the user stats
		createUserStats(user);
	}
	
	//======================================================================================================================
	// Method for creating user tracking data associated with each user
	//======================================================================================================================
	public final void createUserStats(User user)
	{
		Statement command = null;
		String sqlStats = "CREATE TABLE " + user.getUsername() + userTrackingTable + " (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
				+ " presentationid INT NOT NULL, presentationname VARCHAR(100) NOT NULL,"
				+ " userrating INT NOT NULL, userprogress INT NOT NULL)";
		
		try
		{
			command = userTrackingCon.createStatement();
			command.executeUpdate(sqlStats);
			System.out.println("Successfully created tracking table for user: " + user.getUsername());
		}
		catch (SQLException e) 
		{
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
	}
	
	//======================================================================================================================
	//Delete the user with username and password matching function argument values
	//Note - Server side access ONLY
	//======================================================================================================================
	public final void deleteUser(String username, String password)
	{
		PreparedStatement command = null;
		String sqlDeleteUsr = "DELETE FROM " + userTable + " WHERE username = (?) AND password = (?)";
		try
		{
			// Create new JDBC statement
			command = userCon.prepareStatement(sqlDeleteUsr);
			command.setString(1, username);
			command.setString(2, password);
			int row = command.executeUpdate();
			System.out.println("User " + username + " was deleted with code: " + row);
			
		}
		catch (SQLException e) 
		{
			System.out.println("Unable to delete user");
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
	}
	
	//======================================================================================================================
	//Check if a user exists in a database
	//======================================================================================================================
	public final boolean checkUser(User user)
	{
		Statement command = null;
		
		Boolean result = false;
		ResultSet data;
		
		try 
		{
			// Create new JDBC statement
			command = userCon.createStatement();
			// Execute SQL SELECT FROM command
			data = command.executeQuery("SELECT id, username FROM " + userTable
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
		} 
		catch (SQLException e) 
		{
			System.out.println("Unable to find user");
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
		
		return result;
	}
	
	//======================================================================================================================
	//Check that the login details match those in the database
	//======================================================================================================================
	public final boolean checkLoginDetails(User user)
	{
		Statement command = null;
		Boolean result = false;
		ResultSet data;
		
		try 
		{
			// Create new JDBC statement
			command = userCon.createStatement();
			// Execute SQL SELECT FROM command
			data = command.executeQuery("SELECT id, username, password FROM " + userTable
											+ " WHERE username = "
											+ "(" + SQLTools.testNull(user.getUsername()) + ")"
											+ " AND password = "
											+ "(" + SQLTools.testNull(user.getPassword()) + ")"
										);
			// Extract data from results set
			 while(data.next())
			 {
				 // Check if fetched username is equal to expected username
				 if (user.getUsername().equals(data.getString("username")) && user.getPassword().equals(data.getString("password")))
				 {
					 result = true;
				 }
			 }
		}
		catch (SQLException e)
		{
			System.out.println("Unable to find user");
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
					e.printStackTrace();
				} 
			}
		}
		return result;
	}
	
	
	//======================================================================================================================
	// Method for creation of table with presentation and user tracking details upon first access of a presentation
	//======================================================================================================================
	public final void userFirstAccess(User user, PresentationShell pres)
	{
		Statement command = null;
		int presID = SQLTools.checkPresID(presCon, pres);
		
		String sqlFirstAccess = "INSERT INTO " + user.getUsername() + userTrackingTable
				+ " (presentationid, presentationname, userrating,userprogress)"
				+ " VALUES (" + presID + ", '" + pres.getTitle() + "', 0, 0)" ;
		try {
			command = userTrackingCon.createStatement();
			command.executeUpdate(sqlFirstAccess);
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
      		e.printStackTrace();
      	} 
      }
		}	
		System.out.println("Updated user: " + user.getUsername() + " for presentation: " + pres.getTitle());
	}
	
	//======================================================================================================================
	// Method for updating user rating of the specified presentation
	//======================================================================================================================
	public final void setUserRating(User user, PresentationShell pres, int userRating)
	{
		Statement userCommand = null;
		Statement presCommand = null;
		
		int presID = SQLTools.checkPresID(presCon, pres);
		
		int currentUserRating = SQLTools.checkPreviousRating(presID, userTrackingCon, user);
		int newUserRating = 0;
		
		if(currentUserRating == -1)
		{
			switch(userRating)
			{
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
			switch(userRating)
			{
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
				switch(userRating)
				{
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
		
		String updateUserRating = "UPDATE " + user.getUsername() + userTrackingTable
				+ " SET userrating = " + userRating 
				+ " WHERE presentationid = " + presID 
				+ " AND presentationname = '" + pres.getTitle() + "'";
		
		try {
			userCommand = userTrackingCon.createStatement();
			userCommand.executeUpdate(updateUserRating);
			System.out.println("Updated user tracking, now updating global rating...");
		}
		catch (SQLException e) 
		{
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
      		e.printStackTrace();
      	} 
      }
		}
		
		String updateGlobalRating = "UPDATE " + presTable + " SET totalrating = totalrating+" + newUserRating + " WHERE id = " + presID;
		
		try 
		{
			presCommand = presCon.createStatement();
			presCommand.executeUpdate(updateGlobalRating);
			System.out.println("Updated global rating");
		}
		catch (SQLException e)
		{
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
      		e.printStackTrace();
      	} 
      }
		}
	}
	
	/**
	 * @return the presTable
	 */
	public String getPresTable() {
		return presTable;
	}
	/**
	 * @param presTable the presTable to set
	 */
	public void setPresTable(String presTable) {
		this.presTable = presTable;
	}
	/**
	 * @return the presCommentsTable
	 */
	public String getPresCommentsTable() {
		return presCommentsTable;
	}
	/**
	 * @param presCommentsTable the presCommentsTable to set
	 */
	public void setPresCommentsTable(String presCommentsTable) {
		this.presCommentsTable = presCommentsTable;
	}
	/**
	 * @return the userTable
	 */
	public String getUserTable() {
		return userTable;
	}
	/**
	 * @param userTable the userTable to set
	 */
	public void setUserTable(String userTable) {
		this.userTable = userTable;
	}
	/**
	 * @return the userTrackingTable
	 */
	public String getUserTrackingTable() {
		return userTrackingTable;
	}
	/**
	 * @param userTrackingTable the userTrackingTable to set
	 */
	public void setUserTrackingTable(String userTrackingTable) {
		this.userTrackingTable = userTrackingTable;
	}
}