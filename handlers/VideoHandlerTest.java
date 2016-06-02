package handlers;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Objects.AudioItem;
import Objects.VideoItem;


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
				VideoItem videoToHandle = new VideoItem(1,5000,0.1,0.1,"mario.mp4", false, 0.6, 0.6);
				AudioItem audioToHandle = new AudioItem(0, 3000, "Recording.mp3", false);
				
			//	VideoItem videoToHandle2 = new VideoItem(1,5000,0.1,0.1,"test.mp4", false, 0.6, 0.6);
		
			   //MediaFx player = new MediaFx(videoToHandle);
				MediaFx player2 = new MediaFx(audioToHandle);
	
			
				// Create a Stack pane
				Group root = new Group();
				// set up a scene of ratio 4:3 with a black background
				Scene scene = new Scene(root, 600, 600);
				// add canvases to the scene
		    	//root.getChildren().add(player.createContent(scene));
				root.getChildren().add(player2.createContent(scene));
				
				// setup and shows
				
				primary.setScene(scene);
				primary.sizeToScene();
				primary.show();
		
	}

}
