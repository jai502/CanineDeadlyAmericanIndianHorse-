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
	
	public Shading() 
	{
		super();
	}

	public double getxOne()
	{
		return xOne;
	}
	public void setxOne(double xOne) {
		this.xOne = xOne;
	}
	
	public void setxOne(String xOne)
	{
		this.xOne = Double.parseDouble(xOne);
	}
	
	public double getxTwo() 
	{
		return xTwo;
	}
	public void setxTwo(double xTwo)
	{
		this.xTwo = xTwo;
	}
	
	public void setxTwo(String xTwo)
	{
		this.xTwo = Double.parseDouble(xTwo);
	}
	public double getyOne() 
	{
		return yOne;
	}
	public void setyOne(double yOne)
	{
		this.yOne = yOne;
	}
	public void setyOne(String yOne)
	{
		this.yOne = Double.parseDouble(yOne);
	}
	public double getyTwo() {
		return yTwo;
	}
	public void setyTwo(double yTwo) {
		this.yTwo = yTwo;
	}
	public void setyTwo(String yTwo)
	{
		this.yTwo = Double.parseDouble(yTwo);
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
			if(colour.startsWith("#"))
				colour.equals(colour.substring(1));
			String r = colour.substring(0,2);
			String g = colour.substring(2,4);
			String b = colour.substring(4,6);
			int rInt = Integer.parseInt(r, 16);
			int gInt = Integer.parseInt(g, 16);
			int bInt = Integer.parseInt(b,  16);
			this.colourOne = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nNo colour set for shading colour one");
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
			if(colour.startsWith("#"))
				colour.equals(colour.substring(1));
			String r = colour.substring(0,2);
			String g = colour.substring(2,4);
			String b = colour.substring(4,6);
			int rInt = Integer.parseInt(r, 16);
			int gInt = Integer.parseInt(g, 16);
			int bInt = Integer.parseInt(b,  16);
			this.colourTwo = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nNo colour set for shading colour two");
		}
	}
}
