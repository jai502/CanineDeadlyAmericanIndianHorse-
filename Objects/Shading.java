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
* Description: This class holds the information for shading received from the xml document.
*/

package Objects;

import java.awt.Color;

public class Shading {
	private double xOne, xTwo, yOne, yTwo;
	private Color colourOne, colourTwo;
	
	public Shading(double myXOne, double myXTwo, double myYOne, double myYTwo, Color myColourOne, Color myColourTwo)
	{
		xOne = myXOne;
		xTwo = myXTwo; 
		yOne = myYOne; 
		yTwo = myYTwo;
		colourOne = myColourOne;
		colourTwo = myColourTwo;
	}
	
	public double getxOne() {
		return xOne;
	}
	public void setxOne(double xOne) {
		this.xOne = xOne;
	}
	public double getxTwo() {
		return xTwo;
	}
	public void setxTwo(double xTwo) {
		this.xTwo = xTwo;
	}
	public double getyOne() {
		return yOne;
	}
	public void setyOne(double yOne) {
		this.yOne = yOne;
	}
	public double getyTwo() {
		return yTwo;
	}
	public void setyTwo(double yTwo) {
		this.yTwo = yTwo;
	}
	public Color getColourOne() {
		return colourOne;
	}
	public void setColourOne(Color colourOne) {
		this.colourOne = colourOne;
	}
	public Color getColourTwo() {
		return colourTwo;
	}
	public void setColourTwo(Color colourTwo) {
		this.colourTwo = colourTwo;
	}
}
