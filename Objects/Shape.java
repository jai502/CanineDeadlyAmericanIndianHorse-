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
	private Shading shading;
	
	public Shape(int myStartTime, int myDuration, double myXStart, double myYStart, double myWidth, double myHeight, String myType, Color myLineColour, Color myFillColour, Shading myShading)
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
		shading = myShading;
		
	}

	public Shape() {
		super();
	}

	public int getStartTime()
	{
		return startTime;
	}

	public void setStartTime(int startTime) 
	{
		this.startTime = startTime;
	}
	
	public void setStartTime (String startTime)
	{
		this.startTime = Integer.parseInt(startTime);
	}

	public int getDuration() 
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}
	
	public void setDuration(String duration)
	{
		this.duration = Integer.parseInt(duration);
	}
	

	public double getxStart() 
	{
		return xStart;
	}

	public void setxStart(double xStart)
	{
		this.xStart = xStart;
	}
	
	public void setxStart(String xStart)
	{
		this.xStart = Double.parseDouble(xStart);
	}
	
	public double getyStart() 
	{
		return yStart;
	}

	public void setyStart(double yStart) 
	{
		this.yStart = yStart;
	}
	
	public void setyStart(String yStart)
	{
		this.xStart = Double.parseDouble(yStart);
	}
	
	public double getWidth() 
	{
		return width;
	}

	public void setWidth(double width) 
	{
		this.width = width;
	}
	
	public void setWidth(String width)
	{
		this.width = Double.parseDouble(width);
	}
	
	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height) 
	{
		this.height = height;
	}
	
	public void setHeight(String height)
	{
		this.height = Double.parseDouble(height);
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
	
	public void setLineColour(String colour)
	{
		try
		{
			if(colour.startsWith("#"))
				colour.equals(colour.substring(1));
			String r = colour.substring(0,1);
			String g = colour.substring(2,3);
			String b = colour.substring(4,5);
			int rInt = Integer.parseInt(r, 16);
			int gInt = Integer.parseInt(g, 16);
			int bInt = Integer.parseInt(b,  16);
			this.lineColour = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nAn error occured when setting colour");
		}
	}

	public Color getFillColour() 
	{
		return fillColour;
	}

	public void setFillColour(Color fillColour) 
	{
		this.fillColour = fillColour;
	}
	
	public void setFillColour(String colour)
	{
		try
		{
			if(colour.startsWith("#"))
				colour.equals(colour.substring(1));
			String r = colour.substring(0,1);
			String g = colour.substring(2,3);
			String b = colour.substring(4,5);
			int rInt = Integer.parseInt(r, 16);
			int gInt = Integer.parseInt(g, 16);
			int bInt = Integer.parseInt(b,  16);
			this.fillColour = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nAn error occured when setting colour");
		}
	}

	public Shading getShading() {
		return shading;
	}

	public void setShading(Shading shading) {
		this.shading = shading;
	}


	
	
}
