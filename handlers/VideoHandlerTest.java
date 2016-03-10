package handlers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Objects.VideoItem;
import handlers.Video;

public class VideoHandlerTest extends Application{

	public VideoHandlerTest() {
		super();
	}

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primary) throws Exception {
		// Creates video of videoItem type (start time, duration, x start, y start, sourcefile, loop?);
				VideoItem videoToHandle = new VideoItem(0,0,0,0,"files/test.mp4", true);
		
				Video player = new Video(videoToHandle);
	
			
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
