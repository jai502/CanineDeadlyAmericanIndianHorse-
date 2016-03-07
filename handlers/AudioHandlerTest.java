package handlers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AudioHandlerTest extends Application {

	public static void main (String[] args) {
		
		launch(args);
		
	}
	//build and test the control panel
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		primaryStage.setTitle("Audio");
		
		BorderPane bp = new BorderPane();
		
		AudioHandler audioHandler = new AudioHandler("files/Recording.mp3");

		
		bp.setBottom(audioHandler);
		
		Scene scene = new Scene(bp, 800, 600);
		
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
		
}
