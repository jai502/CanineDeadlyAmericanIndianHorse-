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
		parser.parseXML("PWS/pwsTest.xml");

		pres = parser.getPresentation();
		 System.out.println("\ntest 1");

		ArrayList<StackPane> slides = new ArrayList();
		 System.out.println("\ntest 2");

		SlideHandler sh = new SlideHandler();
		 System.out.println("\ntest 3");

		slides = sh.compilePresentation(pres);
		 System.out.println("\ntest 4");

		StackPane root = slides.get(0);
		 System.out.println("\ntest 5");

		Scene scene = new Scene(root, 800, 600, Color.BLACK);
		 System.out.println("\ntest 6");

		primary.setScene(scene);
		 System.out.println("\ntest 7");

		primary.show();
	}

}
