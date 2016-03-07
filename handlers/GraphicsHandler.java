package handlers;

import java.io.IOException;

import Objects.Item;
import Objects.PolygonItem;
import Objects.ShapeItem;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/** 
 * <tt>GraphicsHandler</tt> class handles graphics information to output graphics on a canvas.
 * <p>
 * It can be generated directly as a GraphicsHandler object or return a canvas.
 * <p>
 * 
 * <STRONG> Software Developers: </STRONG> <br>
 * William Ravenscroft
 * Alex Lambert
 * <p>
 * <STRONG> Tested by: </STRONG> <br>
 * Mark Woodward, David Snowden
 * <p>
 * <STRONG> Quality Assured by: </STRONG> <br>
 * Dyfan Davies - 04/03/16
 * <p>
 * 
 * @author Feature Teacher Ltd.
 * @version 1.0 
 * @see Canvas
 * @see Item
 * @see PolygonItem
 * @see ShapeItem
 * @see ShapeHandler 
 * @see PolygonHandler
 */
public class GraphicsHandler {	
	
	// Declare variables
	private Canvas graphicsCanvas;
	private Item returnItem;
	
	/** 
	 * GraphicsHandler method creates a visual object as a node in JavaFX
	 * which can be put onto any JavaFX Pane object 
	 * 
	 * @param item can be either PolygonItem or ShapeItem
	 * @param canvas is the Canvas input to the object where the graphics will be
	 * @throws IOException Exception thrown when reading CSV file incorrectly
	 */
	public GraphicsHandler(Item item, Canvas canvas) throws IOException {
		//set return item for return methods
		returnItem = item;
		// Get canvas graphics
		GraphicsContext gc = canvas.getGraphicsContext2D();
		// Decide what type of graphics item to handle
		if (item instanceof PolygonItem){
			// Cast item to polygonItem
			PolygonItem polygonItem = (PolygonItem) item;
			PolygonHandler polygonHandler = new PolygonHandler(polygonItem, canvas);
			// Set canvas graphics
			graphicsCanvas = polygonHandler.createPolygon(gc, canvas);
		} 
		else if (item instanceof ShapeItem){
			// Cast item to shapeItem
			ShapeItem shapeItem = (ShapeItem) item;
			ShapeHandler shapeHandler = new ShapeHandler(shapeItem, canvas);
			// Set canvas graphics
			graphicsCanvas = shapeHandler.createShape(gc, canvas);
		}		
	}
	
	/** 
	 * Return method for graphicsCanvas returns the canvas such that the object can be added to
	 *
	 * @return graphicsCanvas
	 */
	public Canvas getGraphicsCanvas(){	
		return graphicsCanvas;
	}
	
	/** 
	 * Return method to return start time of graphics
	 * 
	 * @return startTime
	 */
	public int getStartTime(){
		int startTime = returnItem.getStartTime();
		return startTime;
	}
	
	/** 
	 * Return method to return duration of graphics
	 * 
	 * @return duration
	 */
	public int getDuration(){
		int duration = returnItem.getDuration();
		return duration;
	}
	
	/** 
	 * Return method to return Target Slide of graphics
	 * 
	 * @return targetSlide
	 */
//	public int getTargetSlide(){
//		int targetSlide =  returnItem.getTargetSlide();
//		return targetSlide;
//	}
	
	/** 
	 * Return method to return if graphics are interactable
	 * 
	 * @return interactable
	 */
//	public boolean isInteractable(){
//		boolean interactable = returnItem.isInteractable();
//		return interactable;
//	}
}
