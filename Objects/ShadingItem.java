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
* Description: This class holds the information for ShadingItem received from the xml document.
*/

package Objects;

import javafx.scene.paint.Color;

public class ShadingItem {
	private float xOne, xTwo, yOne, yTwo;
	private Color colourOne, colourTwo;
	
	
	public ShadingItem (float myXOne, float myXTwo, float myYOne, float myYTwo, Color myColourOne, Color myColourTwo)
	{
		xOne = myXOne;
		xTwo = myXTwo; 
		yOne = myYOne; 
		yTwo = myYTwo;
		colourOne = myColourOne;
		colourTwo = myColourTwo;
	}
	
	public ShadingItem() 
	{
		super();
	}

	public float getxOne()
	{
		return xOne;
	}
	public void setxOne(float xOne) {
		this.xOne = xOne;
	}
	
	public void setxOne(String xOne)
	{
		this.xOne = Float.parseFloat(xOne);
	}
	
	public float getxTwo() 
	{
		return xTwo;
	}
	public void setxTwo(float xTwo)
	{
		this.xTwo = xTwo;
	}
	
	public void setxTwo(String xTwo)
	{
		this.xTwo = Float.parseFloat(xTwo);
	}
	public float getyOne() 
	{
		return yOne;
	}
	public void setyOne(float yOne)
	{
		this.yOne = yOne;
	}
	public void setyOne(String yOne)
	{
		this.yOne = Float.parseFloat(yOne);
	}
	public float getyTwo() {
		return yTwo;
	}
	public void setyTwo(float yTwo) {
		this.yTwo = yTwo;
	}
	public void setyTwo(String yTwo)
	{
		this.yTwo = Float.parseFloat(yTwo);
	}
	public Color getColourOne() {
		return colourOne;
	}
	public void setColourOne(Color colourOne) {
		this.colourOne = colourOne;
	}
	
	public void setColourOne(String colour)
	{
		try
		{
			this.colourOne = Color.web(colour);
		}
		catch(Exception e){
			System.out.println("\nNo colour set for ShadingItem colour one");
		}
	}
	
	public Color getColourTwo() {
		return colourTwo;
	}
	public void setColourTwo(Color colourTwo) {
		this.colourTwo = colourTwo;
	}
	
	public void setColourTwo(String colour)
	{
		try
		{
			this.colourTwo = Color.web(colour);
		}
		catch(Exception e){
			System.out.println("\nNo colour set for ShadingItem colour two");
		}
	}
}
