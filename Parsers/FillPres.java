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
	
	public Presentation fillPresentation(Presentation pres, String user, SlideItem slide) {
		
		ArrayList<SlideItem> xmlSlideList = new ArrayList<SlideItem>();
		DocumentInfo docInfo = new DocumentInfo("title",user,"1.0","Created presentation by: " + user);
		DefaultsItem defaults = new DefaultsItem();
		ImageItem backgroundImage = new ImageItem(0,0,1f,1f,1f,1f);
		
		
		// Create text boxes and centre line
		Color textBox = new Color(1,1,1,1);
		Color line = new Color(0,0,0,0);
		ShapeItem centreLine  = new ShapeItem(0,0,0.49f,0f,0.02f,1f,"rectangle",line , line);
		ShapeItem textBoxOne = new ShapeItem(0,0,0.1f,0.1f,0.4f,0.3f,"rectangle",textBox , textBox);
		ShapeItem textBoxTwo = new ShapeItem(0,0,0.1f,0.6f,0.4f,0.3f,"rectangle",textBox , textBox);
		
		//Set defaults
		defaults.setBackground("FFFFFF");
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
			backgroundImage.setSourceFile("files/background1");
			break;
		case 1:
			backgroundImage.setSourceFile("files/background2");
			break;
		case 2:
			backgroundImage.setSourceFile("files/background3");
			break;
		case 3:
			backgroundImage.setSourceFile("files/background4");
			break;
		case 4:
			backgroundImage.setSourceFile("files/background5");
			break;
		case 5:
			backgroundImage.setSourceFile("files/background6");
			break;
		case 6:
			backgroundImage.setSourceFile("files/background7");
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
