package handlers;

import java.util.ArrayList;

import Objects.Presentation;
import Parsers.XMLParser;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SlideHandlerTest extends Application {

	public SlideHandlerTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primary) throws Exception 
	{

		Presentation pres = new Presentation();
		XMLParser parser = new XMLParser();
		parser.parseXML("PWS/pwsTest.xml");
		pres = parser.getPresentation();
		SlideHandler sh = new SlideHandler();		
		Group initialRoot = new Group();
		Scene firstScene = new Scene(initialRoot, 800, 600);
		Group root = sh.getSlideStack(pres, 2, 800, 600, firstScene);
		Scene lastScene = new Scene(root, 800, 600);
		

		primary.setScene(lastScene);
		primary.show();
	}

}
