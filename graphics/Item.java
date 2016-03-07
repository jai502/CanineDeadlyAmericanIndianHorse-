
package graphics;

/** 
 * Class that returns generic information about the item to be handled
 * whether it is as polygon or shape
 * <p>
 * <STRONG> Software Developers: </STRONG> <br>
 * Jake Apsey, William Ravenscroft
 * <p>
 * <STRONG> Tested by: </STRONG> <br>
 * Mark Woodward, David Swowden
 * <p>
 * <STRONG> Quality Assured by: </STRONG> <br>
 * Dyfan Davies - 04/03/16
 * <p>
 * @author Feature Teacher Ltd.
 * @version 1.0
 */

public abstract class Item {
	//Class variables
	private int startTime;
	private int duration;
	private boolean interactable;
	private int targetSlide;

	/**
	 * Method sets start time of item
	 * @param startTime integer start time
	 */
	public void setStartTime(int startTime){
		this.startTime = startTime;
	}
	
	/**
	 * Method returns start time of item
	 * @return startTime integer start time
	 */
	public int getStartTime(){
		return startTime;
	}
	
	/**
	 * Method sets duration of item
	 * @param duration - integer value
	 */
	public void setDuration(int duration){
		this.duration = duration;
	}
	
	/**
	 * Method returns duration of item
	 * @return duration - integer value
	 */
	public int getDuration(){
		return duration;
	}
	
	/**
	 * Method returns interactable as true if item is interactable
	 * @return interactable - boolean
	 */
	public boolean isInteractable() {
		return interactable;
	}

	/**
	 * Method sets interactable as true if item is interactable
	 * @return interactable - boolean
	 */
	public void setInteractable(boolean interactable) {
		this.interactable = interactable;
	}
	
	/**
	 * Method returns targetSlide of item
	 * @return targetSlide - integer value
	 */	
	public int getTargetSlide() {
		return targetSlide;
	}
	
	/**
	 * Method sets targetSlide of item
	 * @param targetSlide - integer value
	 */	
	public void setTargetSlide(int targetSlide) {
		this.targetSlide = targetSlide;
	}
}
