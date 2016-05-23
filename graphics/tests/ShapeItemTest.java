package graphics.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Objects.ShadingItem;
import Objects.ShapeItem;
import javafx.scene.paint.Color;

public class ShapeItemTest {

	//class variables 
	private int startTime;
	private int duration;
	private int targetSlide;
	private float x;
	private float y;
	private boolean interactable;
	private ShadingItem gradient = new ShadingItem();
	private ShadingItem shading = new ShadingItem();
	private String shapeType;
	private float width;
	private float height;
	private Color fillColour;
	private Color lineColour;
	
	@Before
	public void setUp() throws Exception {
		//create new shape item to be tested
		ShapeItem item = new ShapeItem();
		
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
		item.setxStart(25);
		item.setyStart(10);
		item.setType("circle");
		item.setWidth(14);
		item.setHeight(17);
		item.setFillColour("00FF00");
		item.setLineColour("FFFFFF");
		item.setShading(shading);
		
		//get polygon variables
		startTime = item.getStartTime();
		duration = item.getDuration();
		x = item.getxStart();
		y = item.getyStart();
		shapeType = item.getType();
		width = item.getWidth();
		height = item.getHeight();
		fillColour = item.getFillColour();
		lineColour = item.getLineColour();
		gradient = item.getShading();
	}

	@Test
	public void test() {
		assertEquals(startTime, 32, 0);
		assertEquals(duration, 43, 0);
		assertEquals(targetSlide, 2, 0);
		assertEquals(x, 25, 0);
		assertEquals(y, 10, 0);
		assertEquals(interactable, false);
		assertEquals(shapeType, "circle");
		assertEquals(width, 14, 0);
		assertEquals(height, 17, 0);
		assertEquals(fillColour, Color.web("00FF00"));
		assertEquals(lineColour, Color.web("FFFFFF"));
		assertEquals(gradient, shading);
	}

}
