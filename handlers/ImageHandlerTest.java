package handlers;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
		
		
		StackPane rootNode = new StackPane();
		Scene scene = new Scene(rootNode);
		Images myImage = new Images("handlers/house.jpg", 0, 0, 0, 0, 500 , 500);
		ImageHandler myHandler = new ImageHandler(myImage);
	//	Canvas canvas = myHandler.createCanvas();
		
	//	rootNode.getChildren().add(canvas);
		primary.setScene(scene);
		primary.show();
		
		
	}

}
