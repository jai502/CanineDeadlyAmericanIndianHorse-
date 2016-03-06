package handlers;

import Objects.Images;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import videoHandler.Video;

public class VideoHandlerTest extends Application{

	public VideoHandlerTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primary) throws Exception {
		// Creates Image of Images type (Sourcefile, start time, duration, x start, y start, width, height);
				Video player = new Video(0,0,0,0,"test.mp4", false);
	
			
				// Create a Stack pane
				StackPane root = new StackPane();
				// set up a scene of ratio 4:3 with a black background
				Scene scene = new Scene(root, 600, 600, Color.BLACK);
				// add canvases to the scene
				root.getChildren().addAll(player);
				// setup and shows
				primary.setScene(scene);
				primary.show();
		
	}

}
