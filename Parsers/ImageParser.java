/*
* (C) Stammtisch
* First version created by: Joseph Ingleby
* Date of first version: 19th February 2016
* 
* Last version by: Joseph Ingleby
* Date of last update: 19th February 2016
* Version number: 0.4.1
* 
* Commit date: 19th February 2016
* Description: This class parses an xml file for an image and returns it for later use.
* This class will read a presentation slide and load all images it parses and their metadata into a linked list.
*/


package Parsers;

import org.w3c.dom.*;

import javax.imageio.ImageIO;
import javax.xml.parsers.*;
import java.awt.Image;
import java.io.*;
import java.util.LinkedList;

public class ImageParser
{
	private LinkedList<ParsedImage> parsedImages;
;
		
	public ImageParser(String xmlFileName, int slideNumber)
	{
		parseImage(xmlFileName, slideNumber);
	}
	
	public boolean parseImage(String xmlFileName, int slideNumber)
	{
		try
		{
			File toParse = new File(xmlFileName);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(toParse);
			document.getDocumentElement().normalize();
			
			NodeList nodeList = document.getElementsByTagName("image");
			//NodeList nodeList = document.getElementsByTagName("slide");
			//nodeList = ((Document) nodeList).getElementsByTagName("image");
			
			for(int i=0; i<nodeList.getLength(); i++) 
			{
				Node node = nodeList.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE)
				{
					ParsedImage tempImage = new ParsedImage();
					Element element = (Element) node;
					tempImage.setParsedImage(ImageIO.read(new File(element.getAttribute("sourceFile"))));
					tempImage.setStartTime(Integer.parseInt(element.getElementsByTagName("starttime").item(0).getTextContent()));
					tempImage.setDuration(Integer.parseInt(element.getElementsByTagName("duration").item(0).getTextContent()));
					tempImage.setxStart(Double.parseDouble(element.getElementsByTagName("xstart").item(0).getTextContent()));
					tempImage.setyStart(Double.parseDouble(element.getElementsByTagName("ystart").item(0).getTextContent()));
					tempImage.setWidth(Double.parseDouble(element.getElementsByTagName("width").item(0).getTextContent()));
					tempImage.setHeight(Double.parseDouble(element.getElementsByTagName("height").item(0).getTextContent()));
					parsedImages.add(tempImage);
				} 
			}
			return true;
      } 
		catch(Exception e) 
      {
         e.printStackTrace();
         return false;
      }
	}

	public LinkedList<ParsedImage> getParsedImages() {
		return parsedImages;
	}

	public void setParsedImages(LinkedList<ParsedImage> parsedImages) {
		this.parsedImages = parsedImages;
	}
}
