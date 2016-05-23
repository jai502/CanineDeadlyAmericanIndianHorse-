package handlers;

//Imports
import java.io.IOException;
import Objects.ShadingItem;
import Objects.PolygonItem;
import Objects.ShadingItem;
import Objects.ShapeItem;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/** 
 * <tt>GraphicsHandlerExamples</tt> demonstrates the GraphicsHandler <p>
 * 2 circles, 1 rectangle, 1 rounded rectangle and a polygon are drawn using the GraphicsHandler
 * with custom gradient colours. 
 * <p>
 * 
 * <STRONG> Software Developers: </STRONG> <br>
 * Mark Woodward, David Snowden
 * <p>
 * <STRONG> Quality Assured by: </STRONG> <br>
 * Dyfan Davies - 04/03/16
 * <p>
 * @author Feature Teacher Ltd.
 * @version 1.0
 * @see GraphicsHandler
 * @see Item
 * @see Canvas
 */
 
public class GraphicsHandlerExamples extends Application {
	
	// New shading for defining gradient fills for shapes 
	private ShadingItem rectangleShading = new ShadingItem();	
	private ShadingItem circleShading = new ShadingItem();	
	private ShadingItem roundedRectangleShading = new ShadingItem();	
	private ShadingItem polygonShading = new ShadingItem();		
	
	// Main JavaFX method
    public static void main(String[] args) {
        launch(args);
    }
    
    /** 
	 * start method creates names a stage, defines a canvas, calls the GraphicsHandler to create several shapes then displays the scene
	 * 
	 * @param Stage is the top level JavaFX container
	 * @throws IOException Exception thrown when reading CSV file incorrectly
	 */
    
    @SuppressWarnings("unused")
    @Override
    
	// Start method for JavaFX
    public void start(Stage primaryStage) throws IOException {        
    	
    	// Set stage title and create new Canvas
    	primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();      
        
        // Return Graphics Context associated with canvas       
        
        // Add the canvas to the group        
        
        // Create a new GraphicsHandler to create a Circle,Rectangle,RoundRect and Polygon Item. 
        // The arguments of GraphicsHandler are GraphicsHandler(Item, Canvas) (see JavaDoc)
        // where Item is the Item to draw and Canvas is the canvas to draw it on.         
        GraphicsHandler graphicsHandlerRect = new GraphicsHandler();
        GraphicsHandler graphicsHandlerCircle1 = new GraphicsHandler(); 
//        GraphicsHandler graphicsHandlerCircle2 = new GraphicsHandler(createCircleItem2(), 600,800); 
//        GraphicsHandler graphicsHandlerRoundRect = new GraphicsHandler(createRoundRectItem(), 600,800);
//        GraphicsHandler graphicsHandlerPolygon = new GraphicsHandler(createPolygonItem(), 600,800);
        
        Canvas rectangleCanvas = graphicsHandlerRect.drawCanvas(createRectItem(), 600,600);
        Canvas circle1Canvas = graphicsHandlerCircle1.drawCanvas(createCircleItem1(), 600,600);
        
        root.getChildren().addAll(rectangleCanvas, circle1Canvas);
        
        // Set the Scene on the top level container Stage and display
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    
    /** 
	 * createRectItem method defines a new ShapeItem and sets the required parameters to create a rectangle. 
	 * @return The new rectangle Item with with set parameters
	 */
    public ShapeItem createRectItem() {
    	
    	// Create a new ShapeItem
    	ShapeItem rectangle = new ShapeItem();  
    	
    	// Set the gradient parameters (see documentation on setting Gradients)
		rectangleShading.setxOne(0);
		rectangleShading.setxTwo(1); 
		rectangleShading.setyOne(0);
		rectangleShading.setyTwo(1);
		rectangleShading.setColourOne("393092");
		System.out.println(rectangleShading.getColourOne());
		rectangleShading.setColourTwo("4500FF");   
		
		// Set Item parameters
		rectangle.setStartTime(0);
		rectangle.setDuration(0);
		rectangle.setxStart(0.15f);
		rectangle.setyStart(0.15f);
		rectangle.setType("rectangle");
		rectangle.setWidth(0.6f);
		rectangle.setHeight(0.6f);
		rectangle.setFillColour("000000");
		rectangle.setLineColour("000000");
		rectangle.setShading(rectangleShading);	
		
		// Return the item once parameters have been set
		return rectangle;
    }
    
    /** 
	 * createCircleItem method defines a new ShapeItem and sets the required parameters to create a circle. 
	 * @return The new circle1 Item with with set parameters
	 */
    public ShapeItem createCircleItem1() {   
    	
    	// Create a new ShapeItem
    	ShapeItem circle1 = new ShapeItem();  
    	
    	// Set the gradient parameters (see documentation on setting Gradients)
		circleShading.setxOne(0);
		circleShading.setxTwo(1); 
		circleShading.setyOne(0);
		circleShading.setyTwo(1);
		circleShading.setColourOne("FFCC00");
		circleShading.setColourTwo("ED8B03"); 
		
		// Set Item parameters
		circle1.setStartTime(0);
		circle1.setDuration(0);
		circle1.setxStart(0.2f);
		circle1.setyStart(0.2f);
		circle1.setType("circle");
		circle1.setWidth(0.4f);
		circle1.setHeight(0.4f);
		circle1.setFillColour("000000");
		circle1.setLineColour("800000");
		circle1.setShading(circleShading);
		
		// Return the item once parameters have been set 
		return circle1;			
    }   
   
    /** 
	 * createCircleItem2 method defines a new ShapeItem and sets the required parameters to create a second circle. 
	 * @return The new circle2 Item with with set parameters
	 */
    public ShapeItem createCircleItem2() { 
    	
    	// Create a new ShapeItem
    	ShapeItem circle2 = new ShapeItem(); 
    	
		// Set Item parameters
		circle2.setStartTime(0);
		circle2.setDuration(0);
		circle2.setxStart(0.47f);
		circle2.setyStart(0.47f);
		circle2.setType("circle");
		circle2.setWidth(0.2f);
		circle2.setHeight(0.2f);
		circle2.setFillColour("000000");
		circle2.setLineColour("800000");
		
		// Use predefined shading parameters
		circle2.setShading(circleShading);
		
		// Return the second circle Item
		return circle2;			
    }
    
    /** 
 	 * createRoundRectItem method defines a new ShapeItem and sets the required parameters to create a round rectangle. 
 	 * @return The new roundRect Item with with set parameters
 	 */
    public ShapeItem createRoundRectItem() {
    	
    	// Create a new ShapeItem
    	ShapeItem roundRect = new ShapeItem();   
    	
    	// Set the gradient parameters (see documentation on setting Gradients)
		roundedRectangleShading.setxOne(0);
		roundedRectangleShading.setxTwo(1); 
		roundedRectangleShading.setyOne(0);
		roundedRectangleShading.setyTwo(1);
		roundedRectangleShading.setColourOne("A63286");
		roundedRectangleShading.setColourTwo("FF00FF");  
		
		// Set Item parameters
		roundRect.setStartTime(0);
		roundRect.setDuration(0);
		roundRect.setxStart(0.7f);
		roundRect.setyStart(0.7f);
		roundRect.setType("rounded rectangle");
		roundRect.setWidth(0.2f);
		roundRect.setHeight(0.2f);
		roundRect.setFillColour("000000");
		roundRect.setLineColour("000000");
		roundRect.setShading(roundedRectangleShading);	
		
		// Return the roundRect Item
		return roundRect;
    }
    
    /** 
 	 * createPolygonItem method defines a new ShapeItem and sets the required parameters to create a new Polygon item. 
 	 * @return The new Polygon Item with with set parameters
 	 */
    public PolygonItem createPolygonItem() {
    	
    	// Create a new PolygonItem
    	PolygonItem polygon = new PolygonItem();    	
    	
    	// Set the gradient parameters (see documentation on setting Gradients)
		polygonShading.setxOne(0);
		polygonShading.setxTwo(1); 
		polygonShading.setyOne(0);
		polygonShading.setyTwo(1);
		polygonShading.setColourOne("47B475");
		polygonShading.setColourTwo("00FFF0");  
		
		// Set Item parameters
		polygon.setStartTime(0);
		polygon.setDuration(0);  
		
        // For Polygon items, as per the spec and PWS, the list of points are defined in a CSV file. 
        // Check the documentation for an example source file. 
		polygon.setSourceFile("files/Pentagon.csv"); 
		polygon.setFillColour("02B8BE");
		polygon.setLineColour("000000");
		polygon.setShading(polygonShading); 
		
    	// Return the polygonItem
		return polygon;
    }
}
