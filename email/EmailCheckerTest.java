/*
* (C) Stammtisch
* First version created by: J.White
* Date of first version: 27/05/2016
* 
* Last version by: J.White
* Date of last update: 27/05/2016
* Version number: 1.0
* 
* Commit date: 27/05/2016
* Description: Test for EmailChecker class
*/

package email;

import static org.junit.Assert.*;

import org.junit.Test;

public class EmailCheckerTest {
	
	private boolean check;

	//test correct email returns true
	@Test
	public void testCorrectEmail()
	{
		String recipient = "jww521@york.ac.uk";
		
		check = EmailChecker.checker(recipient);
		
		assertEquals(true, check);
	}
	
	//test email with no @ symbol returns false
	@Test
	public void testNoAt()
	{
		String recipient = "jww521york.ac.uk";
		
		check = EmailChecker.checker(recipient);
		
		assertEquals(false, check);
	}

	//test email with no . returns false
	@Test
	public void testNoDot()
	{
		String recipient = "jww521@yorkacuk";
		
		check = EmailChecker.checker(recipient);
		
		assertEquals(false, check);
	}
	
	//test email with no @ symbol or . returns false
	@Test
	public void testNoAtOrDot()
	{
		String recipient = "jww521yorkacuk";
		
		check = EmailChecker.checker(recipient);
		
		assertEquals(false, check);
	}
}
