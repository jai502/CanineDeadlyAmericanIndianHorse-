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

public class PolygonItem {
	
	private int startTime , duration;
	private Color lineColour, fillColour;
	private String sourceFile;
	private ShadingItem shading;
	
	public PolygonItem(int myStartTime, int myDuration, Color myLineColour, Color myFillColour, String mySourceFile, ShadingItem myShading)
	{
		startTime = myStartTime;
		duration = myDuration;
		lineColour = myLineColour;
		fillColour = myFillColour;
		sourceFile = mySourceFile;
		shading = myShading;
	}
	
	public PolygonItem() {
		super();
	}

	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public void setStartTime(String startTime) 
	{
		this.startTime = Integer.parseInt(startTime);
	}
	
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
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
	
	public Color getLineColour() {
		return lineColour;
	}
	public void setLineColour(Color lineColour) {
		this.lineColour = lineColour;
	}
	
	public void setLineColour(String colour)
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
			this.lineColour = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nNo colour set for PolygonItem line colour");
		}
	}
	
	public Color getFillColour() {
		return fillColour;
	}
	public void setFillColour(Color fillColour) {
		this.fillColour = fillColour;
	}
	
	public void setFillColour(String colour)
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
			this.fillColour = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nNo colour set for PolygonItem fill colour");
		}
	}
	
	public String getSourceFile() {
		return sourceFile;
	}
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	public ShadingItem getShading() {
		return shading;
	}
	public void setShading(ShadingItem shading) {
		this.shading = shading;
	}
	

}
