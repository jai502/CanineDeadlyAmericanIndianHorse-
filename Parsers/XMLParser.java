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
	private String newContent;
	private Text currentText;
	private Shape currentShape;
	private Polygon currentPolygon;
	private Image currentImage;
	private Video currentVideo;
	private Audio currentAudio;
	private Interactable currentInteractable;
	private Shading currentShading;
	private boolean isInteractable;
	private DocumentInfo currentDocInfo;
	private Defaults currentDefaults;
	private Slide currentSlide;
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
	}
	
	@Override
    public void endElement(String uri, String localName, String qName) throws SAXException 
    {
		if (qName.equalsIgnoreCase("presentation"))
		{
			currentPres.setSlides(slideList);
		}
		
		if (qName.equalsIgnoreCase("documentInfo"))
		{
			currentPres.setDocInfo(currentDocInfo);
			currentDocInfo = null;
		}
		
		if (qName.equalsIgnoreCase("defaults"))
		{
			currentPres.setDefaults(currentDefaults);
			currentDefaults = null;
		}
		
		if (qName.equalsIgnoreCase("slide"))
		{
			slideList.add(currentSlide);
			currentSlide = null;
		}
		
		// Document info setting
		
		if (qName.equalsIgnoreCase("Title"))
		{
			currentDocInfo.setTitle(newContent);
		}
		
		if (qName.equalsIgnoreCase("Author"))
		{
			currentDocInfo.setAuthor(newContent);
		}
		
		if (qName.equalsIgnoreCase("Version"))
		{
			currentDocInfo.setVersion(newContent);
		}
		
		if (qName.equalsIgnoreCase("Comment"))
		{
			currentDocInfo.setComment(newContent);
		}
		
		// Defaults setting
		
		if (qName.equalsIgnoreCase("backgroundColour"))
		{
			if (currentDefaults.getBackground()==null)
			{
				currentDefaults.setBackground(newContent);
			}
			else
			{
				currentSlide.setBackgroundColour(newContent);
			}
		}
		
		if (qName.equalsIgnoreCase("font"))
		{
			currentDefaults.setFont(newContent);
		}
		
		if (qName.equalsIgnoreCase("fontSize"))
		{
			currentDefaults.setFontSize(newContent);
		}
		
		if (qName.equalsIgnoreCase("fontColour"))
		{
			currentDefaults.setFontColour(newContent);
		}
		
		if (qName.equalsIgnoreCase("lineColour"))
		{
			currentDefaults.setLineColour(newContent);
		}
		
		if (qName.equalsIgnoreCase("fillColour"))
		{
			currentDefaults.setFillColour(newContent);
		}
		//
		// Slides setting
		
		if (qName.equalsIgnoreCase("text"))
		{
			if (isInteractable == true){
				currentText.setText(newContent);
				currentInteractable.getTextList().add(currentText);
			}
			currentText.setText(newContent);
		}
		
		if (qName.equalsIgnoreCase("shape"))
		{
			if (isInteractable == true){
				currentInteractable.setShape(currentShape);
			}
		}
		
		if (qName.equalsIgnoreCase("text"))
		{
			if (isInteractable == true){
				currentText.setText(newContent);
				currentInteractable.setText(currentText);
			}
			currentText.setText(newContent);
		}
		
		//TODO finish from here
		
		
		
		
		
			
		
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException 
	{
		newContent = new String(ch, start, length);

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
