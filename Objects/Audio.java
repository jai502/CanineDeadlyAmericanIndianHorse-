/*/
* (C) Stammtisch
* First version created by: Joseph Ingleby & Callum Silver
* Date of first version: 22nd February 2016
* 
* Last version by: Joseph Ingleby & Callum Silver
* Date of last update: 22nd February 2016
* Version number: 1.0
* 
* Commit date: 22nd February 2016
* Description: This class holds the information for audio files received from the xml document.
*/

package Objects;

public class Audio {
	private int startTime, duration;
	private String sourceFile;
	private boolean loop;
	
	public Audio(int startTime, int duration, String sourceFile, boolean loop) 
	{
		super();
		this.startTime = startTime;
		this.duration = duration;
		this.sourceFile = sourceFile;
		this.loop = loop;
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
