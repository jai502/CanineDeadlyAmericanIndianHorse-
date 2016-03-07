package handlers;

import java.io.IOException;

import Objects.PolygonItem;
import Objects.ShadingItem;
import javafx.scene.canvas.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.*;

/** 
 * <tt>PolygonHandler</tt> creates a polygon type shape from a PolygonItem object and outputs onto a canvas object. 
 * <p>
 *
 * <STRONG> Software Developers: </STRONG> <br>
 * Alex Lambert, William Ravenscoft
 * <p>
 * <STRONG> Tested by: </STRONG> <br>
 * Mark Woodward, David Snowden
 * <p>
 * <STRONG> Quality Assured by: </STRONG> <br>
 * Dyfan Davies - 04/03/16
 * <p>
 * @author Feature Teacher Ltd.
 * @version 1.0
 * @see PolygonItem
 * @see Canvas
 */
public class PolygonHandler {	
	
	// Declare variables
	private String sourceFile;
	private double[] xPoints;
	private double[] yPoints;
	private int nPoints;
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
	private double canvasWidth;
	private double canvasHeight;
	private boolean error;
	
	/** 
	 * PolygonHandler takes an item of type PolygonItem and a canvas
	 * It gets the polygon information from item and sets to variables
	 * 
	 * @throws IOException Error reading source file
	 */
	public PolygonHandler(PolygonItem item, Canvas canvas) throws IOException {
		
		// Get polygon parameters from item
		canvasWidth = canvas.getWidth();
		canvasHeight = canvas.getHeight();
		
		// Get and read source file returning polygon points
		sourceFile = item.getSourceFile();
		CSVHandler csvReader = new CSVHandler(sourceFile); 
		nPoints = csvReader.getNPoints();
		xPoints = csvReader.getXPoints();
		yPoints = csvReader.getYPoints();	
		error = csvReader.getError();
		
		// Redefine points proportional to canvas dimensions
		for (int i = 0; i < xPoints.length; i++) {
			xPoints[i] *= canvasWidth;
		}		
		for (int i = 0; i < xPoints.length; i++) {
			yPoints[i] *= canvasHeight;
		}		
		
		// Get colour information
		lineColour = item.getLineColour();
		fillColour = item.getFillColour();
		shading = item.getShading();
		
		// Boolean gradient reset to flat fill colour
		gradient = false;
		
		// Check to see if there are points in source file
		if (error == false & nPoints == 0) {
			Alert noPointsAlert = new Alert(AlertType.WARNING);
			noPointsAlert.setTitle("Warning");
			noPointsAlert.setContentText("There a no vertices defined in source file.\nPolygon will not be drawn.");
			noPointsAlert.showAndWait();
		}
		
		// Check to see if line colour has been set
		if (lineColour == null) {
			Alert noLineAlert = new Alert(AlertType.WARNING);
			noLineAlert.setTitle("Warning");
			noLineAlert.setContentText("The polygon line colour is not defined.\nLine colour will default to black.");
			noLineAlert.showAndWait();
			// Set default colour
			lineColour = Color.BLACK;
		}
		
		// Check to see if fill colour has been set
		if (fillColour == null & shading == null) {
			Alert noFillAlert = new Alert(AlertType.WARNING);
			noFillAlert.setTitle("Warning");
			noFillAlert.setContentText("The polygon fill colour is not defined.\nFill colour will default to black.");
			noFillAlert.showAndWait();
			
			// Set default colour
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
				Alert nullColourAlert = new Alert(AlertType.WARNING);
				nullColourAlert.setTitle("Warning");
				nullColourAlert.setContentText("A gradient has been set without specifying colour1 or colour2.\nThe polygon fill colour will be transparent");
				nullColourAlert.showAndWait();
			}	
			
			// Error shown when gradient coordinates are undefined
			else if (x1 == x2 && y1 == y2) {
				Alert noGradientAlert = new Alert(AlertType.WARNING);
				noGradientAlert.setTitle("Warning");
				noGradientAlert.setContentText("A gradient has been set with identical colour coordinates.\nThe polygon will have a flat fill colour");
				noGradientAlert.showAndWait();
			}				
			
			// Create colour linear gradient with shading information
			stops = new Stop[] {new Stop(0, colour1), new Stop(1, colour2)};
			linearGradient = new LinearGradient(x1, y1, x2, y2, true, CycleMethod.NO_CYCLE, stops);
			
			// Set fill content to gradient
			gradient = true;
		}		
	}
	
	/** 
	 * Method takes graphics context and canvas inputs. 
	 * It sets parameter information and draws the polygon onto the canvas. 
	 * 
	 * @param graphicsContext Graphics context of canvas
	 * @param canvas Canvas object to change
	 * @return canvas
	 */
	public Canvas createPolygon(GraphicsContext graphicsContext, Canvas canvas) {
		
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
		
		// Fill the polygon
		graphicsContext.fillPolygon(xPoints, yPoints, nPoints);
		graphicsContext.strokePolygon(xPoints, yPoints, nPoints);
		
		// Return the canvas
		return canvas;		
    }
}
