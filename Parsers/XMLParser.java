/*
 * (C) Stammtisch
 * First version created by: Joseph Ingleby & Callum Silver
 * Date of first version: 22nd February 2016
 * 
 * Last version by: Joseph Ingleby & Callum Silver
 * Date of last update: 22nd February 2016
 * Version number: 0.1
 * 
 * Commit date: 22nd February 2016
 * Description: This class parses an xml file
 */

package Parsers;

import Objects.*;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler {

	private String inputFile;
	private String fileLocation;
	DocumentInfo currentDocInfo;
	private Defaults currentDefaults;
	private Slide currentSlide;
	private String currentPresSubElement;
	private String currentDocSubElement;
	private String currentDefSubElement;
	private String currentSlideSubElement;
	private Presentation currentPres;
	private ArrayList<Slide> slideList;
	

	public XMLParser(String inputToParse, String inputLocation) {
		inputFile = inputToParse;
		fileLocation = inputLocation;

		try 
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(getFileLocation() + getInputFile(), this);
		} 
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (SAXException saxe)
		{
			saxe.printStackTrace();
		} 
		catch (IOException ioe) 
		{
			ioe.printStackTrace();
		}
	}

	public void startFile() throws SAXException 
	{
		System.out.println("Started parsing: " + getInputFile());
		slideList = new ArrayList<Slide>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException 
	{
		String elementName = localName;
		if ("".equals(elementName))
		{
			elementName = qName;
		}
		
		
		if (elementName.equalsIgnoreCase("presentation"))
		{
			currentPres = new Presentation();
			slideList = new ArrayList<>();
		}
		
		else if (qName.equalsIgnoreCase("documenInfo"))
		{
			currentDocInfo = new DocumentInfo();
		}
		else if (qName.equalsIgnoreCase("defaults"))
		{
			currentDefaults = new Defaults();
		}
		else if (qName.equalsIgnoreCase("slide"))
		{
			currentSlide = new Slide();
			currentSlide.setSlideId(attributes.getValue("slideID"));
			currentSlide.setNextSlide(attributes.getValue("nextSlide"));
			currentSlide.setDuration(attributes.getValue("duration"));
		}
//		
//		
//		switch (elementName)
//		{
//		case "documentInfo" :
//			currentDocInfo = new DocumentInfo();
//			break;
//		case "Title" :
//			currentDocSubElement = "Title";
//			break;
//		case "Author" :
//			currentDocSubElement = "Author";
//			break;
//		case "Version" :
//			currentDocSubElement = "Version";
//			break;
//		case "Comment" :
//			currentDocSubElement = "Comment";
//			break;
//			
//		
//		case "efaults" :
//			currentDefaults = new Defaults();
//			break;
//		case "backgroundColour" :
//			currentDefSubElement = "backgroundColour";
//			break;
//		case "font" :
//			currentDefSubElement = "font";
//			break;
//		case "fontSize" :
//			currentDefSubElement = "fontSize";
//			break;
//		case "fontColour" :
//			currentDefSubElement = "fontColour";
//			break;
//		case "lineColour" :
//			currentDefSubElement = "lineColour";
//			break;
//		case "fillColour" :
//			currentDefSubElement = "fillColour";
//			break;
//			
//			
//		case "slide" :
//			currentSlide = new Slide();
//			break;
//		case "backgroundColour" :
//			currentSlideSubElement = "backgroundColour";
//			break;
//		case "text" :
//			currentSlideSubElement = "text";
//			break;
//		case "shape" :
//			currentSlideSubElement = "shape";
//			break;
//		case "polygon" :
//			currentSlideSubElement = "polygon";
//			break;
//		case "image" :
//			currentSlideSubElement = "image";
//			break;
//		case "video" :
//			currentSlideSubElement = "video";
//			break;
//		case "audio" :
//			currentSlideSubElement = "uudio";
//			break;
//		case "interactable" :
//			currentSlideSubElement = "interactable";
//			break;
//			
//			
//		}
//		
//		if (qName.equals("documentInfo")) 
//		{
//			presentation.getDocInfo().setTitle(attributes.getValue("Title"));
//			presentation.getDocInfo().setAuthor(attributes.getValue("Author"));
//			presentation.getDocInfo().setVersion(attributes.getValue("Version"));
//			presentation.getDocInfo().setComment(attributes.getValue("Comment"));
//		}
//		if (elementName.equals("defaults")) 
//		{
//			presentation.getDefaults().setBackground(attributes.getValue("backgroundColour"));
//			presentation.getDefaults().setFont(attributes.getValue("font"));
//			presentation.getDefaults().setFontSize(attributes.getValue("fontsize"));
//			presentation.getDefaults().setFontColour(attributes.getValue("fontColour"));
//			presentation.getDefaults().setLineColour(attributes.getValue("lineColour"));
//			presentation.getDefaults().setFillColour(attributes.getValue("fillColour"));
//				
//		}
//		if (elementName.equals("slide"))
//		{
//			Slide slideTemp = new Slide();
//			slideTemp.setSlideId(attributes.getValue("slideID"));
//			slideTemp.setNextSlide(attributes.getValue("nextSlide"));
//			slideTemp.setDuration(attributes.getValue("duration"));
//			slideTemp.setBackgroundColor(attributes.getValue("backgroundColor"));
//			
//			
//			
//			
//			
//			
//			presentation.getSlides().add(slideTemp);			
//		}
//
	}

	public String getInputFile() {
		return inputFile;
	}

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

}
