/*
 * (C) Stammtisch
 * First version created by: Joseph Ingleby & Callum Silver
 * Date of first version: 22nd February 2016
 * 
 * Last version by: Joseph Ingleby & Callum Silver
 * Date of last update: 28th February 2016
 * Version number: 0.1
 * 
 * Commit date: 28th February 2016
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

	private String readingFile;
	private String newContent;
	private String text = "";
	private boolean ShapeOrPolyShade = false; // true for ShapeItem, false for PolygonItem
	private TextItem currentText;
	private ShapeItem currentShape;
	private PolygonItem currentPolygon;
	private ImageItem currentImage;
	private VideoItem currentVideo;
	private AudioItem currentAudio;
	private InteractableItem currentInteractable;
	private ShadingItem currentShading;
	private boolean isInteractable = false;
	private DocumentInfo currentDocInfo;
	private DefaultsItem currentDefaults;
	private SlideItem currentSlide;
	private Presentation currentPres;
	private ArrayList<SlideItem> slideList;
	private boolean defaultsSet = false;
	
	

	public XMLParser() {
		
		super();
	}
	
	public void parseXML(String inputFile){
		
		readingFile = inputFile;

		try 
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(inputFile , this);
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


	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException 
	{
		newContent = new String(ch, start, length);

	}	

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException 
	{
		
		
		if (qName.equalsIgnoreCase("presentation"))
		{
			System.out.println("Started parsing: " + readingFile);
			currentPres = new Presentation();
			slideList = new ArrayList<SlideItem>();
		}
		
		if (qName.equalsIgnoreCase("documentInfo"))
		{
			currentDocInfo = new DocumentInfo();
		}
		if (qName.equalsIgnoreCase("Defaults"))
		{
			currentDefaults = new DefaultsItem();
			defaultsSet = false;
		}
		if (qName.equalsIgnoreCase("Slide"))
		{
			currentSlide = new SlideItem();
			System.out.println("New Slide...");
			System.out.println("Current Slide: " + (slideList.size() + 1));
			currentSlide.setSlideId(attributes.getValue("slideID"));
			currentSlide.setNextSlide(attributes.getValue("nextSlide"));
			currentSlide.setDuration(attributes.getValue("duration"));
		}
		
		if (qName.equalsIgnoreCase("Text"))
		{
			currentText = new TextItem();
			currentText.setStartTime(attributes.getValue("starttime"));
			currentText.setDuration(attributes.getValue("duration"));
			currentText.setxStart(attributes.getValue("xstart"));
			currentText.setyStart(attributes.getValue("ystart"));
			currentText.setFont(attributes.getValue("font"));
			currentText.setFontSize(attributes.getValue("fontsize"));
			currentText.setFontColour(attributes.getValue("fontcolour"));
			currentText.setSourceText(attributes.getValue("sourceFile"));
			currentText.setWidth(attributes.getValue("width"));
			currentText.setHeight(attributes.getValue("height"));
		}
		
		if (qName.equalsIgnoreCase("b"))
		{
			text = (text + newContent);
		}
		
		if (qName.equalsIgnoreCase("i"))
		{
			text = (text + newContent);
		}
		
		if (qName.equalsIgnoreCase("Shape"))
		{
			currentShape = new ShapeItem();
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
		
		if (qName.equalsIgnoreCase("Polygon"))
		{
			currentPolygon = new PolygonItem();
			currentPolygon.setStartTime(attributes.getValue("starttime"));
			currentPolygon.setSourceFile(attributes.getValue("sourceFile"));
			currentPolygon.setDuration(attributes.getValue("duration"));
			currentPolygon.setLineColour(attributes.getValue("lineColour"));
			currentPolygon.setFillColour(attributes.getValue("fillColour"));
			
			ShapeOrPolyShade = false;
		}
		
		if (qName.equalsIgnoreCase("Shading"))
		{
			currentShading = new ShadingItem();
			currentShading.setxOne(attributes.getValue("x1"));
			currentShading.setxTwo(attributes.getValue("x2"));
			currentShading.setyOne(attributes.getValue("y1"));
			currentShading.setyTwo(attributes.getValue("y2"));
			currentShading.setColourOne(attributes.getValue("colour1"));
			currentShading.setColourTwo(attributes.getValue("colour2"));
			
		}
		
		if (qName.equalsIgnoreCase("image"))
		{
			currentImage = new ImageItem();
			currentImage.setStartTime(attributes.getValue("starttime"));
			currentImage.setDuration(attributes.getValue("duration"));
			currentImage.setSourceFile(attributes.getValue("sourceFile"));
			currentImage.setxStart(attributes.getValue("xstart"));
			currentImage.setyStart(attributes.getValue("ystart"));
			currentImage.setWidth(attributes.getValue("width"));
			currentImage.setHeight(attributes.getValue("height"));
		}
		
		if (qName.equalsIgnoreCase("Video"))
		{
			currentVideo = new VideoItem();
			currentVideo.setStartTime(attributes.getValue("starttime"));
			currentVideo.setDuration(attributes.getValue("duration"));
			currentVideo.setxStart(attributes.getValue("xstart"));
			currentVideo.setyStart(attributes.getValue("ystart"));
			currentVideo.setSourceFile(attributes.getValue("sourceFile"));
			currentVideo.setLoop(attributes.getValue("loop"));
		}
		
		if (qName.equalsIgnoreCase("Audio"))
		{
			currentAudio = new AudioItem();
			currentAudio.setStartTime(attributes.getValue("starttime"));
			currentAudio.setDuration(attributes.getValue("duration"));
			currentAudio.setSourceFile(attributes.getValue("sourceFile"));
			currentAudio.setLoop(attributes.getValue("loop"));
		}
		
		if (qName.equalsIgnoreCase("Interactable"))
		{
			currentInteractable = new InteractableItem();
			currentInteractable.setTargetSlide(attributes.getValue("targetSlide"));
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
		
		if (qName.equalsIgnoreCase("Defaults"))
		{
			currentPres.setDefaults(currentDefaults);
			currentDefaults = null;
			defaultsSet = true;
			System.out.println("DefaultsItem set");
		}
		
		if (qName.equalsIgnoreCase("Slide"))
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
		
		// DefaultsItem setting
		
		if (qName.equalsIgnoreCase("backgroundColour"))
		{
			if (defaultsSet == false)
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
		
		// Slides setting
		
		if (qName.equalsIgnoreCase("Text"))
		{
			if (isInteractable == true){
				text = (text + newContent);
				currentText.setText(text);
				currentInteractable.addText(currentText);
				currentText = null;
				text = "";
			}
			else
			{
				text = (text + newContent);
				currentText.setText(text);
				currentSlide.addText(currentText);
				currentText = null;
				text = "";
			}
		}
		
		if (qName.equalsIgnoreCase("b"))
		{
			text = (text + "<b>" + newContent + "</b>");
		}
		
		if (qName.equalsIgnoreCase("i"))
		{
			text = (text + "<i>" + newContent + "</i>");
		}
		
		if (qName.equalsIgnoreCase("Shape"))
		{
			if (isInteractable == true)
			{
				currentInteractable.addShape(currentShape);
			}
			
			currentSlide.addShape(currentShape);
			
		}
		
		if (qName.equalsIgnoreCase("Polygon"))
		{
			if (isInteractable == true)
			{
				currentInteractable.addPolygon(currentPolygon);
			}
			currentSlide.addPolygon(currentPolygon);
		}
		
		if (qName.equalsIgnoreCase("Shading"))
		{
			if (ShapeOrPolyShade == true) // if current ShadingItem is for a ShapeItem
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
				currentInteractable.addImage(currentImage);
			}
			
			currentSlide.addImage(currentImage);
		}
		
		if (qName.equalsIgnoreCase("Audio"))
		{
			if (isInteractable == true)
			{
				currentInteractable.addAudio(currentAudio);
			}
			currentSlide.addAudio(currentAudio);
		}
		
		if (qName.equalsIgnoreCase("Video"))
		{
			if (isInteractable == true)
			{
				currentInteractable.addVideo(currentVideo);
			}
			currentSlide.addVideo(currentVideo);
		}
		
		if (qName.equalsIgnoreCase("Interactable"))
		{
			currentSlide.addInteractable(currentInteractable);
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
	
	public Presentation getPresentation()
	{
		return currentPres;
	}
	
	


}