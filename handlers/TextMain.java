/**
* (C) Stammtisch
* First version created by: J.Bones
* Date of first version: 06.03.2016
* 
* Last version by: J.Bones
* Date of last update: 
* Version number: 1.0
* 
* Commit date: 06.03.2016
* Description: Simple main function to allow for testing of the Text Handler class
 */

package handlers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import handlers.TextHandler;
import Objects.TextItem;

public class TextMain extends Application {
		Stage window;
		int windowHeight=600;
		int windowWidth=800;
		

		public static void main(String[] args)
		{
			//default launch method for javafx applications
			launch(args);
		}

		@Override
		public void start(Stage primaryStage) throws Exception 
		{
			//Assign the primary stage to the window
			window = primaryStage;
			window.setTitle("Text viewer");
			window.setHeight(windowHeight);//Set default height & width for window
			window.setWidth(windowWidth);

			//Message to be displayed by the application for testing purposes
			String message = "I am some <b>bold</b> and <i>italic</i> text and " +
					"some more <b>of that bold</b> and <i>of that italic</i> text. "+
					"This is a test for <b>PWS</b> testing <i>Good job</i>.";

			//Declare the variables in order to test TextHandler functionality
			String fontFamily = "serif";
			int fontSize = 20;
			Color fontColour = Color.BLACK;
			float xStart = 0.5f;
			float yStart = 0.5f;
			
			//Create the text item
			TextItem textItem = new TextItem(0, 0, fontSize, xStart, yStart, fontFamily, fontColour, message);

			//Declare the new textHandler object and create text on screen
			TextHandler textHandler = new TextHandler();
			textHandler.setWindowSize(windowWidth, windowHeight); //To allow for positioning on screen
			TextFlow textFlow = textHandler.setText(textItem);
			

			//Set the preferred width of the text object
			textFlow.setPrefWidth(200);
			textFlow.setTextAlignment(TextAlignment.JUSTIFY); //set text alignment of the text object

			//Create a group and add that to the root scene
			Group group = new Group();  
			group.getChildren().add(textFlow);
			Scene scene = new Scene(group, 800, 600);

			//Show the window
			window.setScene(scene);
			window.show();
		}

}
