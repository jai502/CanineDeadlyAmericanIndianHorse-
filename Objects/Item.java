
package Objects;

public abstract class Item {
	//Class variables
	private int startTime;
	private int duration;

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
		try
		{
			this.startTime = Integer.parseInt(startTime);
		}
		catch(Exception e)
		{
			this.startTime = 0;
		}
	}

	public int getDuration() 
	{
		return duration;
	}

	public void setDuration(int duration)
	{
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
	
}
