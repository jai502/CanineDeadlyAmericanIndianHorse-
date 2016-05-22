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


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser extends DefaultHandler{

	private String inputFile;
	private String fileLocation;
	
	public XMLParser(String inputToParse, String inputLocation)
	{
		inputFile	=	inputToParse;
		fileLocation	=	inputLocation;
		
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
	
	public void startDocument() throws SAXException
	{
		System.out.println("Started parsing: " + getInputFile());
		/*
		 * not yet finished
		 * 
		 * */
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
