package handlers;

import java.util.ArrayList;

import Objects.Presentation;
import Parsers.XMLParser;
import javafx.application.Application;
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
		pres = parser.getPresentation();
		ArrayList<StackPane> slides = new ArrayList();
		SlideHandler sh = new SlideHandler();

		slides = sh.compilePresentation(pres);
		 System.out.println("\n Number of slides according to this test: "+slides.size());		 
		 
		StackPane root = slides.get(0);
		 System.out.println("\ntest 1");

		Scene scene = new Scene(root, 800, 600, Color.BLACK);
		 System.out.println("\ntest 2");

		primary.setScene(scene);
		 System.out.println("\ntest 3");

		primary.show();
	}

}
