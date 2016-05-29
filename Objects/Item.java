
package Objects;

public abstract class Item {
	//Class variables
	public int startTime;
	public int duration;
	public int interactableSlide; // (-1 for non-interactables and target slide (0-#) for interactables.

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

	public void setDuration(String duration) 
	{
		try
		{
			this.duration = Integer.parseInt(duration);
		}
		catch(Exception e)
		{
			this.duration = 0;
		}
	}

	public int getInteractableSlide() {
		return interactableSlide;
	}

	public void setInteractableSlide(int interactableSlide) {
		this.interactableSlide = interactableSlide;
	}

	public void setInteractableSlide(String interactableSlide)
	{
		try
		{
			this.interactableSlide = Integer.parseInt(interactableSlide);
		}
		catch(Exception e)
		{
			this.duration = 0;
		}

	}
}
