/*
* (C) Stammtisch
* First version created by: Joseph Ingleby
* Date of first version: 19th February 2016
* 
* Last version by: Joseph Ingleby
* Date of last update: 19th February 2016
* Version number: 0.1
* 
* Commit date:
* Description: This class parses an xml file for an image and returns it for later use.
*/


package Parsers;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.awt.Image;
import java.io.*;

public class imageParser
{
	
	private Image parsedImage;
	private String sourceFile;
	private int startTime, duration;
	private double xStart, yStart, width, height;
		
	public void parseImage(String fileName)
	{
		try
		{
			File toParse = new File(fileName);
		
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(toParse);
			document.getDocumentElement().normalize();
			
			NodeList nodeList = document.getElementsByTagName("imageType");
			for (int i= 0; i< nodeList.getLength(); i++) 
			{
				Node node = nodeList.item(i);
				System.out.println("\nCurrent Element :" + node.getNodeName());
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
               Element element = (Element) node;
               System.out.println("Source File: "+ element.getAttribute("sourceFile"));
               System.out.println("Start Time : " + element.getElementsByTagName("starttime").item(0).getTextContent());
               System.out.println("Duration : " + element.getElementsByTagName("duration").item(0).getTextContent());
               System.out.println("XStart : " + element.getElementsByTagName("xstart").item(0).getTextContent());
               System.out.println("YStart : " + element.getElementsByTagName("ystart").item(0).getTextContent());
               System.out.println("Width: " + element.getElementsByTagName("width").item(0).getTextContent());
               System.out.println("Height: " + element.getElementsByTagName("height").item(0).getTextContent());
            }
         }
      } 
		catch (Exception e) 
      {
         e.printStackTrace();
      }
	}
	


	public Image getParsedImage() 
	{
		return parsedImage;
	}

	public void setParsedImage(Image parsedImage) 
	{
		this.parsedImage = parsedImage;
	}

	public String getSourceFile()
	{
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) 
	{
		this.sourceFile = sourceFile;
	}

	public int getStartTime() 
	{
		return startTime;
	}

	public void setStartTime(int startTime)
	{
		this.startTime = startTime;
	}

	public int getDuration() 
	{
		return duration;
	}

	public void setDuration(int duration) 
	{
		this.duration = duration;
	}

	public double getxStart() 
	{
		return xStart;
	}

	public void setxStart(double xStart) 
	{
		this.xStart = xStart;
	}

	public double getyStart() 
	{
		return yStart;
	}

	public void setyStart(double yStart) 
	{
		this.yStart = yStart;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width) 
	{
		this.width = width;
	}

	public double getHeight() 
	{
		return height;
	}

	public void setHeight(double height) 
	{
		this.height = height;
	}
}
