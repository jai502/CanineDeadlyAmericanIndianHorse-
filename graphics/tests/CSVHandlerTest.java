package graphics.tests;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

import graphics.CSVHandler;

public class CSVHandlerTest {
	//Class Variables
	private String sourceFile = "graphics/Example.csv";		
	private double[] xPoints;
	private double[] yPoints;
	private int nPoints;

	@Before
	public void SetUP() throws IOException {
		//Get points from CSV file
		CSVHandler openCSVreader = new CSVHandler(sourceFile);
		xPoints = openCSVreader.getXPoints();
		yPoints = openCSVreader.getYPoints();
		nPoints = openCSVreader.getNPoints();
	}	
		
	@Test 
	public void TestCSVReader() {			
		//Print list of xPoints
		System.out.println("List of X Coordinates");
		for (int i = 0; i < xPoints.length; i++) {
			System.out.println(xPoints[i]);
		}			
		//Print list of yPoints
		System.out.println("List of Y Coordinates");
		for (int i = 0; i < yPoints.length; i++) {
			System.out.println(yPoints[i]);
		}			
		//Print the total number of polygon vertices
		System.out.println("Number of points");
		System.out.println(nPoints);	
		//for these asserts to pass
		//create a CSV file with the coordinates 
		//(1, 2) 
		//(3, 4)
		//(5, 6)
		//(7, 8)
		//(9, 10)
		//otherwise alter the test accordingly
		assertEquals(xPoints[0], 1.0, 0);
		assertEquals(xPoints[1], 3.0, 0);
		assertEquals(xPoints[2], 5.0, 0);
		assertEquals(xPoints[3], 7.0, 0);
		assertEquals(xPoints[4], 9.0, 0);
		assertEquals(yPoints[0], 2.0, 0);
		assertEquals(yPoints[1], 4.0, 0);
		assertEquals(yPoints[2], 6.0, 0);
		assertEquals(yPoints[3], 8.0, 0);
		assertEquals(yPoints[4], 10.0, 0);
		assertEquals(nPoints, 5);	
	}	
}