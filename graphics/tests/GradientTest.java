package graphics.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import graphics.Gradient;
import javafx.scene.paint.Color;



public class GradientTest {
	
	private float X1;
	private float X2;
	private float Y1;
	private float Y2;
	private Color colour1;
	private Color colour2;
	
	@Before
	public void setUp() {
		Gradient gradient = new Gradient();
		//set item variables
		gradient.setX1(12);
		gradient.setX2(15); 
		gradient.setY1(18);
		gradient.setY2(21);
		gradient.setColour1("00FF00");
		gradient.setColour2("FF00FF");
		//get item variables
		X1 = gradient.getX1();
		X2 = gradient.getX2();
		Y1 = gradient.getY1();
		Y2 = gradient.getY2();
		colour1 = gradient.getColour1();
		colour2 = gradient.getColour2();
	} 

	@Test
	public void test() {
		//test variables 
		assertEquals(X1, 12, 0);
		assertEquals(X2, 15, 0);
		assertEquals(Y1, 18, 0);
		assertEquals(Y2, 21, 0);
		assertEquals(colour1, Color.web("00FF00"));
		assertEquals(colour2, Color.web("FF00FF"));
	}

}
