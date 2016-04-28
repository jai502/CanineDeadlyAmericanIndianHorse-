import java.sql.*;

/*
*	First version created by:	Peter Mills
*	Date of first version:		29/02/16
*
*	Last updated by:			Peter Mills
*	Date of last update:		29/02/16
*	Version Number:				1
*
*	Commit date:	
*	Description: 	SQL server handling classes
*/

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
		
		try {
			// Create connection SQL command
			connection = DriverManager.getConnection(connectionString, username, password);
			// Execute connection SQL command
			command = connection.createStatement();

		} catch (SQLException e) {
			System.out.println("Unable to connect to SQL server");
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
		
		return connection;
	
	}
	
	// Add a user using function argument values
	public static void addUser(Connection con, String table, String username, String password, String email, String dob) 
	{
		Statement command = null;
		
		try {
			// Create new JDBC statement
			command = con.createStatement();
			// Execute SQL INSERT command
			command.executeUpdate("INSERT INTO " + table  
									+ " (username, password, email, dob) "
									+ "VALUES ("
									+	"'"	+ username	+ "', "
									+	"'"	+ password	+ "', "
									+	"'" + email		+ "', "
									+	"'" + dob		+ "' "
									+ ")" 
								);
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
	}
	
	// Delete the user with username and password matching function argument values
	public static void deleteUser(Connection con, String table, String username, String password)
	{
		Statement command = null;

		try {
			// Create new JDBC statement
			command = con.createStatement();
			// Execute SQL DELETE FROM command
			command.executeUpdate("DELETE FROM " + table 
									+ " WHERE username = "
									+ "('" + username + "')"
									+ " AND password = "
									+ "('" + password + "')"
								);
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
	public static boolean checkUser(Connection con, String table, String username, String email)
	{
		Statement command = null;
		
		Boolean result = false;
		ResultSet data;
		String query = null;
		
		try {
			// Create new JDBC statement
			command = con.createStatement();
			// Execute SQL SELECT FROM command
			data = command.executeQuery("SELECT id, username FROM " + table
											+ " WHERE username = "
											+ "('" + username + "')"
											+ " OR email = "
											+ "('" + email + "')"
										);
			// Extract data from results set
			 while(data.next())
			 {
				 // Check if fetched username is equal to expected username
				 if (username.equals(data.getString("username")))
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
	
	public static void main(String[] args) 
	{
		Connection con = null;
		
		// SQL 
		String server = "stammtischsql.ddns.net";
		int port = 3306;
		String database = "useraccounts";
		String table = "users";
		Date test;
		
		
		con = connect(server, port, database);
		
		//addUser(con, table, "test123", "pass", "email@gmail.com", "1990-01-01");
		//deleteUser(con, table, "test123", "pass");
		
		boolean result = checkUser(con, table, "test123", "email@gmail.com");
		System.out.println("User found? " + result);
	}

}
