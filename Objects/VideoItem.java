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
* Description: This class holds the information for video files received from the xml document.
*/

package Objects;

public class VideoItem extends Item
{
	private double xStart, yStart, width, height;
	private String sourceFile;
	private boolean loop;
	
	public VideoItem(int myStartTime, int myDuration, double myXStart, double myYStart, String mySourceFile, boolean myLoop, double myWidth, double myHeight)
	{
		width = myWidth;
		height = myHeight;
		xStart = myXStart;
		yStart = myYStart;
		sourceFile = mySourceFile;
		loop = myLoop;
	}

	public VideoItem() {
		super();
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
		this.yStart = Double.parseDouble(yStart);
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

	public String getSourceFile() 
	{
		return sourceFile;
	}

	public void setSourceFile(String sourceFile) 
	{
		this.sourceFile = sourceFile;
	}

	public boolean isLoop() 
	{
		return loop;
	}

	public void setLoop(boolean loop) 
	{
		this.loop = loop;
	}
	
	
	// issues caused when parsing string to boolean, resolved with this
	public void setLoop(String loop) 
	{
		if (loop.equalsIgnoreCase("true"))
		{
			this.loop = true;
		}
		else if (loop.equalsIgnoreCase("false"))
		{
			this.loop = false;
		}
		else
		{
			this.loop = false;
		}
		
	}
}
