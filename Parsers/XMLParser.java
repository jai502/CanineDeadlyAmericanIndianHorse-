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

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler {

	private String inputFile = "PWS/PWSTest.xml";
	private String fileLocation;
	private String newContent;
	private boolean ShapeOrPolyShade = false; // true for shape, false for poly
	private Text currentText;
	private Shape currentShape;
	private Polygon currentPolygon;
	private Image currentImage;
	private Video currentVideo;
	private Audio currentAudio;
	private Interactable currentInteractable;
	private Shading currentShading;
	private boolean isInteractable = false;
	private DocumentInfo currentDocInfo;
	private Defaults currentDefaults;
	private Slide currentSlide;
	private Presentation currentPres;
	private ArrayList<Slide> slideList;
	private int defaultsSet = 0;
	
	private ArrayList<Text> textList;

	
	

	public XMLParser() {

		try 
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(inputFile, this);
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

	public void startFile() throws SAXException 
	{
		System.out.println("Started parsing: " + getInputFile());
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException 
	{
		newContent = new String(ch, start, length);

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
			slideList = new ArrayList<Slide>();
		}
		
		if (qName.equalsIgnoreCase("documentInfo"))
		{
			currentDocInfo = new DocumentInfo();
			System.out.println("hey");
		}
		if (qName.equalsIgnoreCase("defaults"))
		{
			currentDefaults = new Defaults();
			System.out.println("hey again");
			defaultsSet = 0;
		}
		if (qName.equalsIgnoreCase("slide"))
		{
			currentSlide = new Slide();
			System.out.println("New slide");
			currentSlide.setSlideId(attributes.getValue("slideID"));
			currentSlide.setNextSlide(attributes.getValue("nextSlide"));
			currentSlide.setDuration(attributes.getValue("duration"));
			System.out.println("Next slide:" + currentSlide.getNextSlide());
		}
		
		if (qName.equalsIgnoreCase("text"))
		{
			currentText = new Text();
			currentText.setStartTime(attributes.getValue("starttime"));
			currentText.setDuration(attributes.getValue("duration"));
			currentText.setxStart(attributes.getValue("xstart"));
			currentText.setyStart(attributes.getValue("ystart"));
			currentText.setFont(attributes.getValue("font"));
			currentText.setFontSize(attributes.getValue("fontsize"));
			currentText.setFontColour(attributes.getValue("fontcolour"));
		}
		
		if (qName.equalsIgnoreCase("Shape"))
		{
			currentShape = new Shape();
			currentShape.setStartTime(attributes.getValue("starttime"));
			currentShape.setDuration(attributes.getValue("duration"));
			currentShape.setxStart(attributes.getValue("xstart"));
			currentShape.setyStart(attributes.getValue("ystart"));
			currentShape.setType(attributes.getValue("type"));
			currentShape.setWidth(attributes.getValue("width"));
			currentShape.setHeight(attributes.getValue("height"));
			currentShape.setLineColour(attributes.getValue("lineColour"));
			currentShape.setFillColour(attributes.getValue("fillColour"));
		
			ShapeOrPolyShade = true;
		}
		
		if (qName.equalsIgnoreCase("polygon"))
		{
			currentPolygon = new Polygon();
			currentPolygon.setStartTime(attributes.getValue("starttime"));
			currentPolygon.setSourceFile(attributes.getValue("sourceFile"));
			currentPolygon.setDuration(attributes.getValue("duration"));
			currentPolygon.setLineColour(attributes.getValue("lineColour"));
			currentPolygon.setFillColour(attributes.getValue("fillColour"));
			
			ShapeOrPolyShade = false;
		}
		
		if (qName.equalsIgnoreCase("image"))
		{
			currentImage = new Image();
			currentImage.setStartTime(attributes.getValue("starttime"));
			currentImage.setDuration(attributes.getValue("duration"));
			currentImage.setSourceFile(attributes.getValue("sourceFile"));
			currentImage.setxStart(attributes.getValue("xstart"));
			currentImage.setyStart(attributes.getValue("ystart"));
			currentImage.setWidth(attributes.getValue("width"));
			currentImage.setHeight(attributes.getValue("height"));
		}
		
		if (qName.equalsIgnoreCase("video"))
		{
			currentVideo = new Video();
			currentVideo.setStartTime(attributes.getValue("starttime"));
			currentVideo.setDuration(attributes.getValue("duration"));
			currentVideo.setxStart(attributes.getValue("xstart"));
			currentVideo.setyStart(attributes.getValue("ystart"));
			currentVideo.setSourceFile(attributes.getValue("sourceFile"));
			currentVideo.setLoop(attributes.getValue("loop"));
		}
		
		if (qName.equalsIgnoreCase("audio"))
		{
			currentAudio = new Audio();
			currentAudio.setStartTime(attributes.getValue("starttime"));
			currentAudio.setDuration(attributes.getValue("duration"));
			currentAudio.setSourceFile(attributes.getValue("sourceFile"));
			currentAudio.setLoop(attributes.getValue("loop"));
		}
		
		if (qName.equalsIgnoreCase("interactable"))
		{
			currentInteractable = new Interactable();
			isInteractable = true;
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
			System.out.println("doc info set");
		}
		
		if (qName.equalsIgnoreCase("defaults"))
		{
			currentPres.setDefaults(currentDefaults);
			currentDefaults = null;
			defaultsSet = 1;
			System.out.println("defaults set");
		}
		
		if (qName.equalsIgnoreCase("slide"))
		{
			currentSlide.setTextList(textList);
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
			if (defaultsSet == 0)
			{
				currentDefaults.setBackground(newContent);
			}
			else
			{
				System.out.println("new content: " + newContent + "Qname" + qName + currentSlide.getNextSlide());
				
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
		
		// Slides setting
		
		if (qName.equalsIgnoreCase("text"))
		{
			if (isInteractable == true){
				currentText.setText(newContent);
				currentInteractable.getTextList().add(currentText);
				currentText = null;
			}
			else
			{
				currentText.setText(newContent);
				System.out.println("new content contains: " + newContent + currentText.getText());
				System.out.println("duration :" + currentText.getDuration()+ "Font:"+ currentText.getFontSize());
				textList.add(currentText);
				
				currentText = null;
			}
		}
		
		if (qName.equalsIgnoreCase("shape"))
		{
			if (isInteractable == true)
			{
				currentInteractable.getShapeList().add(currentShape);
			}
			
			currentSlide.getShapeList().add(currentShape);
			
		}
		
		if (qName.equalsIgnoreCase("polygon"))
		{
			if (isInteractable == true)
			{
				currentInteractable.getPolygonList().add(currentPolygon);
			}
			currentSlide.getPolygonList().add(currentPolygon);
		}
		
		if (qName.equalsIgnoreCase("shading"))
		{
			if (ShapeOrPolyShade == true) // if current shading is for a shape
			{
				currentShape.setShading(currentShading);
			}
			else
			{
				currentPolygon.setShading(currentShading);
			}
		}
		
		if (qName.equalsIgnoreCase("image"))
		{
			if (isInteractable == true)
			{
				currentInteractable.getImageList().add(currentImage);
			}
			System.out.println(currentSlide.getNextSlide() + "hello:" + currentImage.getSourceFile());
			
			currentSlide.addImage(currentImage);
		}
		
		if (qName.equalsIgnoreCase("Audio"))
		{
			if (isInteractable == true)
			{
				currentInteractable.getAudioList().add(currentAudio);
			}
			currentSlide.getAudioList().add(currentAudio);
		}
		
		if (qName.equalsIgnoreCase("Video"))
		{
			if (isInteractable == true)
			{
				currentInteractable.getVideoList().add(currentVideo);
			}
			currentSlide.getVideoList().add(currentVideo);
		}
		
		if (qName.equalsIgnoreCase("Interactable"))
		{
			currentSlide.getInteractableList().add(currentInteractable);
			isInteractable = false;
		}
		
		if (qName.equalsIgnoreCase("Presentation"))
		{
			currentPres.setSlides(slideList);
			
		}
			
			
		
	}
	
	
	public void endDocument() throws SAXException 
	{
        System.out.println("Finished parsing, stored presentation with " + slideList.size() + " slides.");
	}
	public Presentation getPresentation(String fileLocation, String fileName)
	{
		setInputFile(fileName);
		setFileLocation(fileLocation);
		
		return currentPres;
		
	}


}
