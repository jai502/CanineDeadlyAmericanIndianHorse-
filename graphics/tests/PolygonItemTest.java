package graphics.tests;

import static org.junit.Assert.*;

import Objects.ShadingItem;
import Objects.PolygonItem;
import javafx.scene.paint.Color;

import org.junit.Before;
import org.junit.Test;

public class PolygonItemTest {

	private int startTime;
	private int duration;
	private int targetSlide;
	private boolean interactable;
	private ShadingItem gradient = new ShadingItem();
	private ShadingItem shading = new ShadingItem();
	private String sourceFile;
	private Color fillColour;
	private Color lineColour;
	
	@Before
	public void setUp() { 
		PolygonItem item = new PolygonItem();
		 
		//set gradient item
		shading.setxOne(12);
		shading.setxTwo(15);
		shading.setyOne(18);
		shading.setyTwo(21);
		shading.setColourOne("00FF00");
		shading.setColourTwo("FF00FF");
		
		//set polygon item variables
		item.setStartTime(32);
		item.setDuration(43);
		item.setSourceFile("Test.csv");
		item.setShading(shading);
		item.setFillColour("00F0F0");
		item.setLineColour("FFFFFF");
		
		//get variables from item
		startTime = item.getStartTime();
		duration = item.getDuration();
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
