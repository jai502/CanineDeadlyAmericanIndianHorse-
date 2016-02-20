/*
* (C) Stammtisch
* First version created by: Joseph Ingleby
* Date of first version: 19th February 2016
* 
* Last version by: Joseph Ingleby
* Date of last update: 19th February 2016
* Version number: 0.3
* 
* Commit date: 19th February 2016
* Description: This class parses an xml file for an image and returns it for later use.
*/


package Parsers;

import org.w3c.dom.*;

import javax.imageio.ImageIO;
import javax.xml.parsers.*;
import java.awt.Image;
import java.io.*;

public class ImageParser
{
	private Image parsedImage;
	private int startTime, duration;
	private double xStart, yStart, width, height;
		
	public ImageParser(String xmlFileName)
	{
		parseImage(xmlFileName);
	}
	
	public boolean parseImage(String xmlFileName)
	{
		try
		{
			File toParse = new File(xmlFileName);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(toParse);
			document.getDocumentElement().normalize();
			
			NodeList nodeList = document.getElementsByTagName("imageType");
			for (int i= 0; i< nodeList.getLength(); i++) 
			{
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					Element element = (Element) node;
					setParsedImage(ImageIO.read(new File(element.getAttribute("sourceFile"))));
					setStartTime(Integer.parseInt(element.getElementsByTagName("starttime").item(0).getTextContent()));
					setDuration(Integer.parseInt(element.getElementsByTagName("duration").item(0).getTextContent()));
					setxStart(Double.parseDouble(element.getElementsByTagName("xstart").item(0).getTextContent()));
					setyStart(Double.parseDouble(element.getElementsByTagName("ystart").item(0).getTextContent()));
					setWidth(Double.parseDouble(element.getElementsByTagName("width").item(0).getTextContent()));
					setHeight(Double.parseDouble(element.getElementsByTagName("height").item(0).getTextContent()));
				} 
			}
			return true;
      } 
		catch (Exception e) 
      {
         e.printStackTrace();
         return false;
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
