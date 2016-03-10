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
	private float xStart, yStart;
	private String sourceFile;
	private boolean loop;
	
	public VideoItem(int myStartTime, int myDuration, float myXStart, float myYStart, String mySourceFile, boolean myLoop)
	{
		xStart = myXStart;
		yStart = myYStart;
		sourceFile = mySourceFile;
		loop = myLoop;
	}

	public VideoItem() {
		super();
	}

	public float getxStart() 
	{
		return xStart;
	}

	public void setxStart(float xStart) 
	{
		this.xStart = xStart;
	}
	
	public void setxStart(String xStart)
	{
		this.xStart = Float.parseFloat(xStart);
	}

	public float getyStart() 
	{
		return yStart;
	}

	public void setyStart(float yStart) 
	{
		this.yStart = yStart;
	}
	
	public void setyStart(String yStart)
	{
		this.yStart = Float.parseFloat(yStart);
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
