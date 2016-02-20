package Parsers;

import java.awt.Image;

public class ParsedImage {
	
	private Image parsedImage;
	private int startTime, duration;
	private double xStart, yStart, width, height;

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
