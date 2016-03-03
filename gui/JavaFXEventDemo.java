package gui;
// Demonstrate JavaFX events and buttons

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;

public class JavaFXEventDemo extends Application {

	Label response;
	FlowPane rootNode;

	public static void main(String[] args) {
		// Start the JavaFX application by calling launch()
		launch(args);
	}
	
	public FlowPane getRoot(){
		return rootNode;
	}

	// Override the init() method
	public void init(){
		System.out.println("Inside the init() method now");
	}

	// Override the start() method
	public void start(Stage myStage){

		// Give the stage a title
		myStage.setTitle("Use a JavaFX buttons and events");

		/* Create a root node. In this case a flow layout 
		 * is used, with vertical and horizontal gaps of 10
		 */
		rootNode = new FlowPane(10, 10);

		// Centre the controls in the scene
		rootNode.setAlignment(Pos.CENTER);

		// Create a scene
		Scene myScene = new Scene(rootNode, 300, 200);

		// Set the scene on the stage
		myStage.setScene(myScene);

		// Create a label - Currently displays default message
		response = new Label("Push a Button!");

		// Create two push buttons
		Button btnUp = new Button("Up");
		btnUp.setId("up");
		Button btnDown = new Button("Down");

		// Handle the action events for the Up button
		btnUp.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				response.setText("You pressed Up");
			}
		});

		// Handle the action events for the Down button
		btnDown.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				response.setText("You pressed Down");
			}
		});

		// Add the label and buttons to the scene graph
		rootNode.getChildren().addAll(btnUp, btnDown, response);

		// Show the stage and it's scene
		myStage.show();
	}

	// Override the stop() method
	public void stop(){
		System.out.println("Inside the stop() method now");
	}

}
