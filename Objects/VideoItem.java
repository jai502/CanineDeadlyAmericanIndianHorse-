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

public class VideoItem
{
	private int startTime, duration;
	private double xStart, yStart;
	private String sourceFile;
	private boolean loop;
	
	public VideoItem(int myStartTime, int myDuration, double myXStart, double myYStart, String mySourceFile, boolean myLoop)
	{
		startTime = myStartTime;
		duration = myDuration;
		xStart = myXStart;
		yStart = myYStart;
		sourceFile = mySourceFile;
		loop = myLoop;
	}

	public VideoItem() {
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
	
	public void setStartTime(String startTime) 
	{
		this.startTime = Integer.parseInt(startTime);
	}

	public int getDuration() 
	{
		return duration;
	}

	public void setDuration(int duration) {	
		this.duration = duration;
	}
	
	public void setDuration(String duration) {
		try
		{
			this.duration = Integer.parseInt(duration);
		}
		catch(Exception e)
		{
			this.duration = 0;
		}
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
