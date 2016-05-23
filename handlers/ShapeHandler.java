package handlers;

import javafx.scene.canvas.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.*;
import Objects.ShapeItem;
import Objects.ShadingItem;

/** 
 * <tt>ShapeHandler</tt> class handles shapeItem information.
 * <p>
 * It takes the shape information and outputs graphics to a canvas
 * <p>
 * 
 * <STRONG> Software Developers: </STRONG> <br>
 * Alex Lambert
 * William Ravenscroft
 * <p>
 * <STRONG> Tested by: </STRONG> <br>
 * Mark Woodward, David Snowden
 * <p>
 * <STRONG> Quality Assured by: </STRONG> <br>
 * Dyfan Davies - 04/03/16
 * <p>
 * @author Feature Teacher Ltd.
 * @version 1.0
 * @see ShapeItem
 * @see Canvas
 */
public class ShapeHandler {	
	
	// Declare variables
	private float positionX;
	private float positionY;
	private String shapeType;	
	private float width;
	private float height;
	private Color lineColour;	
	private Color fillColour;
	private ShadingItem shading;
	private float x1;
	private float y1;
	private Color colour1;
	private float x2;
	private float y2;
	private Color colour2;
	private boolean gradient;	
	private Stop[] stops;
	private LinearGradient linearGradient;
	private double arcWidth;
	private double arcHeight;	
	private double canvasWidth;
	private double canvasHeight;	
	
	/** 
	 * ShapeHandler takes an item of type ShapeItem and a canvas. 
	 * It gets the shape information from item and sets to variables
	 *   
	 * @param item ShapeItem input to give information about shape to be drawn
	 * @param canvas Canvas for shape to be drawn on
	 */
	public ShapeHandler(ShapeItem item, Canvas canvas) {
		
		// Get shape parameters from item		
		canvasWidth = canvas.getWidth();
		canvasHeight = canvas.getHeight();	
		positionX = item.getxStart();
		positionY = item.getyStart();
		shapeType = item.getType();
		width = item.getWidth();
		height = item.getHeight();
		
		// Get colour information
		lineColour = item.getLineColour();		
		fillColour = item.getFillColour();
		shading = item.getShading();
		
		// Boolean gradient reset to flat fill colour
		gradient = false;
		
		// Check to see if shape type has been set
		if (shapeType == null) {
			
			// Alert if no shape type set 
			Alert shapeTypeAlert = new Alert(AlertType.WARNING);
			shapeTypeAlert.setTitle("Warning");
			shapeTypeAlert.setContentText("The shape type has not been specified.\nShape will not be drawn.");
			shapeTypeAlert.showAndWait();			
		}	
		
		// Check to see if shape dimensions have been set
		if (width == 0.0f) {
			
			// Alert if shape width is zero
			Alert widthAlert = new Alert(AlertType.WARNING);
			widthAlert.setTitle("Warning");
			widthAlert.setContentText("The shape width is zero.\nShape will be one dimensional.");
			widthAlert.showAndWait();
			
		} else if (height == 0.0f) {
			
			// Alert if shape height is zero 
			Alert heightAlert = new Alert(AlertType.WARNING);
			heightAlert.setTitle("Warning");
			heightAlert.setContentText("The shape height is zero.\nShape will be one dimensional.");
			heightAlert.showAndWait();
			
		} else if (width == 0.0f && height == 0.0f) {
			
			// Alert if both shape width and height are zero
			Alert dimensionsAlert = new Alert(AlertType.WARNING);
			dimensionsAlert.setTitle("Warning");
			dimensionsAlert.setContentText("The shape width and height are zero.\nShape will not be visible.");
			dimensionsAlert.showAndWait();
		}
		
		// Check to see if line colour has been set
		if (lineColour == null) {
			
			// Alert if line colour is not set 
			Alert noLineAlert = new Alert(AlertType.WARNING);
			noLineAlert.setTitle("Warning");
			noLineAlert.setContentText("The shape line colour is not defined.\nLine colour will default to black.");
			noLineAlert.showAndWait();
			
			// Set default colour to black if line colour has not been set 
			lineColour = Color.BLACK;
		}
		
		// Check to see if fill colour has been set
		if (fillColour == null & shading == null) {
			
			// Alert if fill colour has not been set
			Alert noFillAlert = new Alert(AlertType.WARNING);
			noFillAlert.setTitle("Warning");
			noFillAlert.setContentText("The shape fill colour is not defined.\nFill colour will default to black.");
			noFillAlert.showAndWait();
			
			// Set default colour to black if fill colour has not been set
			fillColour = Color.BLACK;
		}
		
		// Check to see if gradient has been set
		if (shading != null) {
			x1 = shading.getxOne();
			y1 = shading.getyOne();
			x2 = shading.getxTwo();
			y2 = shading.getyTwo();
			colour1 = shading.getColourOne();
			colour2 = shading.getColourTwo();	
			
			// Error shown when gradient colours are undefined
			if (colour1 == null && colour2 == null) {
				
				// Alert when gradient colours are undefined
				Alert nullColourAlert = new Alert(AlertType.WARNING);
				nullColourAlert.setTitle("Warning");
				nullColourAlert.setContentText("A gradient has been set without specifying colour1 or colour2.\nThe shape fill colour will be transparent");
				nullColourAlert.showAndWait();
			}	
			
			// Error shown when gradient coordinates are undefined
			else if (x1 == x2 && y1 == y2) {
				
				// Alert when gradient is set with identical coordinates
				Alert noGradientAlert = new Alert(AlertType.WARNING);
				noGradientAlert.setTitle("Warning");
				noGradientAlert.setContentText("A gradient has been set with identical coordinates.\nThe graphic will have a flat fill colour");
				noGradientAlert.showAndWait();
			}				
			
			// Create colour linear gradient with shading information
			stops = new Stop[] {new Stop(0, colour1), new Stop(1, colour2)};
			linearGradient = new LinearGradient(x1, y1, x2, y2, true, CycleMethod.NO_CYCLE, stops);
			
			// Set fill content to gradient
			gradient = true;
		}				
		
		// Defined arc parameters
		arcWidth = 10;
		arcHeight = 10;
	}
	
	/** 
	 * Method takes graphics context and canvas inputs. 
	 * It sets parameter information and draws the shape onto the canvas
	 * 
	 * @param graphicsContext Graphics context of canvas
	 * @param canvas Canvas object to change
	 * @return canvas
	 */
	public Canvas createShape(GraphicsContext graphicsContext, Canvas canvas) {		
		
		// Set graphics information on canvas
		graphicsContext.setStroke(lineColour);
		
		// Outline width is defined here as 2.5 pixels
		graphicsContext.setLineWidth(2.5);
		
		// Determine whether to use fill colour or gradient
		if (gradient == true) {
			graphicsContext.setFill(linearGradient);				
		}
		else if (gradient == false){
			graphicsContext.setFill(fillColour);				
		}			
		
		// Determine the shape type to be drawn and draw depending on what type of shape is given
		if (shapeType != null) {
			switch(shapeType) {	
				case "rectangle":			
					graphicsContext.fillRect(positionX*canvasWidth, positionY*canvasHeight, width*canvasWidth, height*canvasHeight);
					graphicsContext.strokeRect(positionX*canvasWidth, positionY*canvasHeight, width*canvasWidth, height*canvasHeight);
					break;		
				case "circle":					
					graphicsContext.fillOval(positionX*canvasWidth, positionY*canvasHeight, width*canvasWidth, height*canvasHeight);	
					graphicsContext.strokeOval(positionX*canvasWidth, positionY*canvasHeight, width*canvasWidth, height*canvasHeight);
					break;		
				case "rounded rectangle":			
					graphicsContext.fillRoundRect(positionX*canvasWidth, positionY*canvasHeight, width*canvasWidth, height*canvasHeight, arcWidth, arcHeight);
					graphicsContext.strokeRoundRect(positionX*canvasWidth, positionY*canvasHeight, width*canvasWidth, height*canvasHeight, arcWidth, arcHeight);
					break;	
				default:
			}	
		}		
		
		// Return the canvas
		return canvas;
    }
}
