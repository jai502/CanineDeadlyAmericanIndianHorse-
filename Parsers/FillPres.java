package Parsers;

import java.util.ArrayList;
import java.util.Random;

import Objects.DefaultsItem;
import Objects.DocumentInfo;
import Objects.ImageItem;
import Objects.Presentation;
import Objects.ShapeItem;
import Objects.SlideItem;
import javafx.scene.paint.Color;

public class FillPres {
	

	public FillPres(){
		super();
	}
	
	public Presentation fillPresentation(Presentation pres, String user, SlideItem slide, String titleCreated) {
		
		ArrayList<SlideItem> xmlSlideList = new ArrayList<SlideItem>();
		DocumentInfo docInfo = new DocumentInfo(titleCreated ,user ,"1.0" ,"Created presentation by: " + user);
		DefaultsItem defaults = new DefaultsItem();
		ImageItem backgroundImage = new ImageItem(0,0,0f,0f,1f,1f);
		
		
		// Create text boxes and centre line
		Color textBox = new Color(1,1,1,1);
		Color line = new Color(0,0,0,1);
		
		ShapeItem centreLine  = new ShapeItem(0,0,0.52f,0f,0.01f,1f,"rectangle",line , textBox);
		ShapeItem textBoxOne = new ShapeItem(0,0,0.1f,0.1f,0.4f,0.3f,"rectangle",line , textBox);
		ShapeItem textBoxTwo = new ShapeItem(0,0,0.1f,0.6f,0.4f,0.3f,"rectangle",line , textBox);
		
		//Set defaults
		defaults.setBackground("000000");
		defaults.setFillColour("FFFFFF");
		defaults.setLineColour("000000");
		defaults.setFont("SansSerif");
		defaults.setFontSize("16");
		defaults.setFontColour("000000");
		
		// generate random background image
		Random randomGenerator = new Random();
		int randomPic = randomGenerator.nextInt(6);
		
		switch (randomPic)
		{
		case 0:
			backgroundImage.setSourceFile("files/background1.jpg");
			break;
		case 1:
			backgroundImage.setSourceFile("files/background2.jpg");
			break;
		case 2:
			backgroundImage.setSourceFile("files/background3.jpg");
			break;
		case 3:
			backgroundImage.setSourceFile("files/background4.jpg");
			break;
		case 4:
			backgroundImage.setSourceFile("files/background5.jpg");
			break;
		case 5:
			backgroundImage.setSourceFile("files/background6.jpg");
			break;
		case 6:
			backgroundImage.setSourceFile("files/background7.jpg");
			break;
		default:
			System.out.println("Something went wrong when generating background");
			break;
		}
		
		// add them all to the document
		slide.addImage(backgroundImage);
		slide.addShape(textBoxOne);
		slide.addShape(textBoxTwo);
		slide.addShape(centreLine);
		xmlSlideList.add(slide);
		
		pres.setSlides(xmlSlideList);
		pres.setDefaults(defaults);
		pres.setDocInfo(docInfo);
		
		return pres;
	}

}
