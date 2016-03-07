package graphics.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Objects.ShadingItem;
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
		ShadingItem gradient = new ShadingItem();
		//set item variables
		gradient.setxOne(12);
		gradient.setxTwo(15); 
		gradient.setyOne(18);
		gradient.setyTwo(21);
		gradient.setColourOne("00FF00");
		gradient.setColourTwo("FF00FF");
		//get item variables
		X1 = gradient.getxOne();
		X2 = gradient.getxTwo();
		Y1 = gradient.getyOne();
		Y2 = gradient.getyTwo();
		colour1 = gradient.getColourOne();
		colour2 = gradient.getColourTwo();
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
