package graphics.tests;

import static org.junit.Assert.*;

import graphics.Gradient;
import graphics.PolygonItem;
import javafx.scene.paint.Color;

import org.junit.Before;
import org.junit.Test;

public class PolygonItemTest {

	private int startTime;
	private int duration;
	private int targetSlide;
	private boolean interactable;
	private Gradient gradient = new Gradient();
	private Gradient shading = new Gradient();
	private String sourceFile;
	private Color fillColour;
	private Color lineColour;
	
	@Before
	public void setUp() { 
		PolygonItem item = new PolygonItem();
		 
		//set gradient item
		shading.setX1(12);
		shading.setX2(15);
		shading.setY1(18);
		shading.setY2(21);
		shading.setColour1("00FF00");
		shading.setColour2("FF00FF");
		
		//set polygon item variables
		item.setStartTime(32);
		item.setDuration(43);
		item.setTargetSlide(1);
		item.setInteractable(true);
		item.setSourceFile("Test.csv");
		item.setShading(shading);
		item.setFillColour("00F0F0");
		item.setLineColour("FFFFFF");
		
		//get variables from item
		startTime = item.getStartTime();
		duration = item.getDuration();
		targetSlide = item.getTargetSlide();
		interactable = item.isInteractable();
		sourceFile = item.getSourceFile();
		gradient = item.getShading();
		fillColour = item.getFillColour();
		lineColour = item.getLineColour();
	}

	@Test
	public void test() {
		assertEquals(startTime, 32, 0);
		assertEquals(duration, 43, 0); 
		assertEquals(targetSlide, 1, 0);
		assertEquals(interactable, true);
		assertEquals(sourceFile, "Test.csv");
		assertEquals(gradient, shading);
		assertEquals(fillColour, Color.web("00F0F0"));
		assertEquals(lineColour, Color.web("FFFFFF"));
	}

}
