package graphics.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import graphics.Gradient;
import graphics.ShapeItem;
import javafx.scene.paint.Color;

public class ShapeItemTest {

	//class variables 
	private int startTime;
	private int duration;
	private int targetSlide;
	private float x;
	private float y;
	private boolean interactable;
	private Gradient gradient = new Gradient();
	private Gradient shading = new Gradient();
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
		shading.setX1(12);
		shading.setX2(15); 
		shading.setY1(18);
		shading.setY2(21);
		shading.setColour1("00FF00");
		shading.setColour2("FF00FF");
		
		//set polygon item variables
		item.setStartTime(32);
		item.setDuration(43);
		item.setTargetSlide(2);
		item.setX(25);
		item.setY(10);
		item.setInteractable(false);
		item.setShapeType("circle");
		item.setWidth(14);
		item.setHeight(17);
		item.setFillColour("00FF00");
		item.setLineColour("FFFFFF");
		item.setShading(shading);
		
		//get polygon variables
		startTime = item.getStartTime();
		duration = item.getDuration();
		targetSlide = item.getTargetSlide();
		x = item.getX();
		y = item.getY();
		interactable = item.isInteractable();
		shapeType = item.getShapeType();
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
