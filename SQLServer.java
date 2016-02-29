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
	private static String username = "root";
	private static String password = "canine_horse";
	
	
	private static String connectionString = "jdbc:mysql://localhost:3306/userdetails";
	
	private static Connection connection;
	private static Statement command;
	private static ResultSet data;
	
	// Connect  to the server defined by
	public static void connect(String server, int port, String database)
	{	
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
		}
	
	}
	
	// Add a user using function argument values
	public static void addUser(String table, String username, String password, String email, String dob) 
	{
		try {
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
		}
	}
	
	// Delete the user with username and password matching function argument values
	public static void deleteUser(String table, String username, String password)
	{
		try {
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
		}
		
	}
	
	public static void main(String[] args) 
	{
		// SQL 
		String server = "localhost";
		int port = 3306;
		String database = "userdetails";
		String table = "test";
		Date test;
		
		
		connect(server, port, database);
		
		//addUser("user", "test123", "pass", "email@gmail.com", "1990-01-01");
		//deleteUser("user", "test123", "pass");
		
	}

}
