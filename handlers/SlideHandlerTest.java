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
		ArrayList<StackPane> slides = new ArrayList();
		SlideHandler sh = new SlideHandler();


		sh.compilePresentation(pres);
		slides  = sh.getSlides();
		
		 System.out.println("Number of slides according to this test: "+slides.size());
		 for(int i=0; i<slides.size();i++)
			 System.out.println("To string of slides("+i+"): "+slides.get(i).toString());
		
		StackPane root = slides.get(4);
		Scene scene = new Scene(root, 800, 600, Color.BLACK);

		primary.setScene(scene);
		primary.show();
	}

}
