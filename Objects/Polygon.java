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
* Description: This class holds the information for polygons received from the xml document.
*/

package Objects;

import java.awt.Color;

public class Polygon {
	
	private int startTime , duration;
	private Color lineColour, fillColour;
	private String sourceFile;
	private Shading shading;
	
	public Polygon(int myStartTime, int myDuration, Color myLineColour, Color myFillColour, String mySourceFile, Shading myShading)
	{
		startTime = myStartTime;
		duration = myDuration;
		lineColour = myLineColour;
		fillColour = myFillColour;
		sourceFile = mySourceFile;
		shading = myShading;
	}
	
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Color getLineColour() {
		return lineColour;
	}
	public void setLineColour(Color lineColour) {
		this.lineColour = lineColour;
	}
	public Color getFillColour() {
		return fillColour;
	}
	public void setFillColour(Color fillColour) {
		this.fillColour = fillColour;
	}
	public String getSourceFile() {
		return sourceFile;
	}
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	public Shading getShading() {
		return shading;
	}
	public void setShading(Shading shading) {
		this.shading = shading;
	}
	

}
