/**
 * (C) Stammtisch
 * First version created by: Jonathan Bones & P.Mills
 * Date of first version: 18/05/2016
 * 
 * Last version by: Jonathan Bones & P.Mills
 * Date of last update: 18/05/2016
 * Version number: 1.0
 * 
 * Commit date: 18/05/2016
 * Description: Class to aid SQL statement creation
 */

package SQL;

public class SQLTools 
{
	public static String testNull(String testString)
	{
		String outputString;
		
		if(testString == null)
		{
			outputString = null;
		}
		else
		{
			outputString = "'" + testString +"'";
		}
		return outputString;
	}
}
