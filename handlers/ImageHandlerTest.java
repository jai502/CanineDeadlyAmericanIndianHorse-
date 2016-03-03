package handlers;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
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
	public void start(Stage primary) throws Exception {
		Images myImage = new Images("handlers/5q.bmp", 0, 0, 0, 0, 500, 500);
		ImageHandler myHandler = new ImageHandler(myImage);
		
		StackPane rootNode = new StackPane();
		Scene scene = new Scene(rootNode);
		rootNode.getChildren().add(myHandler.getCanvas());
		primary.setScene(scene);
		primary.show();
		
		
	}

}
