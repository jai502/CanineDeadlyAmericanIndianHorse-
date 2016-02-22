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

public class Video
{
	private int startTime, duration;
	private double xStart, yStart;
	private String sourceFile;
	private boolean loop;
	
	public Video(int myStartTime, int myDuration, double myXStart, double myYStart, String mySourceFile, boolean myLoop)
	{
		startTime = myStartTime;
		duration = myDuration;
		xStart = myXStart;
		yStart = myYStart;
		sourceFile = mySourceFile;
		loop = myLoop;
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

	public void setDuration(int duration) {	
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
}
