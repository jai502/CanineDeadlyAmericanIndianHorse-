/*
 * (C) Stammtisch
 * First version created by: Callum Silver
 * Date of first version: 26th May 2016
 * 
 * Last version by: Callum Silver
 * Date of last update: 26th May 2016
 * Version number: 1.0
 * 
 * Commit date: 26th May 2016
 * Description: This class tests the presentation XML file creator from user inputs
 */

package Parsers;

import java.util.ArrayList;


import Objects.DocumentInfo;
import Objects.Presentation;
import Objects.SlideItem;
import Objects.TextItem;
import Objects.VideoItem;


public class XMLCreatorTest {

	public XMLCreatorTest() {
		super();
	}

	public static void main(String[] args) {

		Presentation pres = new Presentation();
		DocumentInfo docInfo = new DocumentInfo();
		ArrayList<SlideItem> slidelist = new ArrayList<SlideItem>();
		SlideItem slide1 = new SlideItem();
		SlideItem slide2 = new SlideItem();
		TextItem text1 = new TextItem();
		TextItem text2 = new TextItem();
		VideoItem video1 = new VideoItem();

		// missing parameters
		// TODO test needs updating
		// XMLCreator creator = new XMLCreator();

		docInfo.setAuthor("Goosey mcGooseface");
		docInfo.setComment("hello");
		docInfo.setTitle("My travels");
		docInfo.setVersion("1.0");

		// text Attributes (ystart, xstart, startTime, Duration, font, fontsize, font colour)
		text1.setyStart("0.5");
		text1.setxStart("0.5");
		text1.setStartTime("3000");
		text1.setDuration("3000");
		text1.setFont("comic sans");
		text1.setFontSize("12");
		text1.setFontColour("FFFFFF");
		text1.setText("hello and welcome");

		text2.setyStart("0.3");
		text2.setxStart("0.2");
		text2.setStartTime("3000");
		text2.setDuration("1000");
		text2.setFont("wingdings");
		text2.setFontSize("15");
		text2.setFontColour("FAFFAF");
		text2.setText("Goodbye");

		video1.setDuration("4000");
		video1.setStartTime("0");
		video1.setxStart("0.5");
		video1.setyStart("0.5");
		video1.setSourceFile("files/mario.mp4");
		video1.setLoop("false");

		slide1.setDuration("2000");
		slide1.setNextSlide("2");
		slide1.setSlideId("1");

		slide2.setDuration("2000");
		slide2.setNextSlide("0");
		slide2.setSlideId("2");

		//slide1.addVideo(video1);
		slide1.addText(text1);
		slide1.addText(text2);

		//slide2.addVideo(video1);
		slide2.addText(text1);
		slide2.addText(text2);

		slidelist.add(slide1);
		slidelist.add(slide2);

		pres.setSlides(slidelist);

		pres.setDocInfo(docInfo);

		// TODO test needs updating
		// creator.createXML(pres, false, false, false, false, true, true, false);

	}
}

