/*
* (C) Stammtisch
* First version created by: Joseph Ingleby & Callum Silver
* Date of first version: 22nd February 2016
* 
* Last version by: Joseph Ingleby & Callum Silver
* Date of last update: 22nd February 2016
* Version number: 1.0
* 
* Commit date: 22nd February 2016
* Description: This class holds the information for shapes received from the xml document.
*/

package Objects;

import java.awt.Color;

public class Shape
{
	private int startTime, duration;
	private double xStart, yStart, width, height;
	private String type;
	private Color lineColour, fillColour;
	
	public Shape(int myStartTime, int myDuration, double myXStart, double myYStart, double myWidth, double myHeight, String myType, Color myLineColour, Color myFillColour)
	{
		startTime = myStartTime;
		duration = myDuration;
		xStart = myXStart;
		yStart = myYStart;
		width = myWidth;
		height = myHeight;
		type = myType;
		lineColour = myLineColour;
		fillColour = myFillColour;	
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

	public String getType() 
	{
		return type;
	}

	public void setType(String type) 
	{
		this.type = type;
	}

	public Color getLineColour()
	{
		return lineColour;
	}

	public void setLineColour(Color lineColour) 
	{
		this.lineColour = lineColour;
	}

	public Color getFillColour() 
	{
		return fillColour;
	}

	public void setFillColour(Color fillColour) 
	{
		this.fillColour = fillColour;
	}
	
	
}
