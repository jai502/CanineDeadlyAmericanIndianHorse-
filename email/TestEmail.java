package email;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestEmail extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage = new Stage();
		primaryStage.setTitle("A-mail");
		
		VBox bp = new VBox();
		
		Scene scene = new Scene(bp, 600, 800);
		
		EmailInit testEmail = new EmailInit();
		
		testEmail.sneakyAids(bp);
		
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();
		
	}
	
	

}
