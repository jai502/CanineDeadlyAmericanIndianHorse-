/**
 * (C) Stammtisch
 * First version created by: Peter Mills
 * Date of first version: 29/02/16
 *
 * Last updated by: Jonathan Bones & Peter Mills
 * Date of last update: 29/02/16
 * Version Number: 1.1
 *
 * Commit date: 18/05/2016
 * Description: SQL server handling class
 */

package SQL;

import java.sql.*;

public class SQLServer 
{
	private static String username = "bot1";
	private static String password = "canine_horse";	
	private static String connectionString = "jdbc:mysql://192.168.1.64:3306/userdetails";
	
	// Connect  to the server defined by
	public static Connection connect(String server, int port, String database)
	{	
		Connection connection = null;
		Statement command = null;
		
		// Construct connection command string
		connectionString = "jdbc:mysql://" + server + ":" + port + "/" + database;
		
		try 
		{
			// Create connection SQL command
			connection = DriverManager.getConnection(connectionString, username, password);
			// Execute connection SQL command
			command = connection.createStatement();

		} 
		catch (SQLException e) 
		{
			System.out.println("Unable to connect to SQL server");
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
					e.printStackTrace();// Failed to close command
				} 
			}
		}
		
		return connection;
	}
}
