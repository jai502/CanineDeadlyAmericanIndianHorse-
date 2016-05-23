/*
* (C) Stammtisch
* First version created by: Joseph Ingleby & Callum Silver
* Date of first version: 3rd March 2016
* 
* Last version by: Joseph Ingleby & Callum Silver
* Date of last update: 3rd March 2016
* Version number: 1.0
* 
* Commit date: 3rd March 2016
* Description: This class is a visual test for the image handler which draws two test images on a scene
*/
package handlers;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Objects.*;

public class ImageHandlerTest extends Application{

	public ImageHandlerTest(){
		super();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primary) throws Exception 
	{
		
		// Creates Image of Images type (Sourcefile, start time, duration, x start, y start, width, height);
		ImageItem imageToHandleHouse = new ImageItem("files/house.jpg",0 ,0, 0f, 0f, 1f, 1f);
		ImageItem imageToHandleDog = new ImageItem("files/dog.png",0 ,0, 0.7f, 0.6f, 0.4f, 0.3f);
	
		// Create a Stack pane
		StackPane root = new StackPane();
		// set up a scene of ratio 4:3 with a black background
		Scene scene = new Scene(root, 800, 600, Color.BLACK);
		// Instantiate an image handler
		ImageHandler imageHandler = new ImageHandler();
		// get the two canvases from image handler by passing (Images, scene width, height)
		Canvas imageCanvasHouse = imageHandler.drawCanvas(imageToHandleHouse, 800, 600);
		Canvas imageCanvasDog = imageHandler.drawCanvas(imageToHandleDog, 800, 600);
		// add canvases to the scene
		root.getChildren().addAll(imageCanvasHouse, imageCanvasDog);
		// setup and show
		primary.setScene(scene);
		primary.show();
		
	}

}
