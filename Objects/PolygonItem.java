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

import javafx.scene.paint.Color;

public class PolygonItem extends Item
{
	
	private Color lineColour, fillColour;
	private String sourceFile;
	private ShadingItem shading;
	
	public PolygonItem(int myStartTime, int myDuration, Color myLineColour, Color myFillColour, String mySourceFile, ShadingItem myShading)
	{
		lineColour = myLineColour;
		fillColour = myFillColour;
		sourceFile = mySourceFile;
		shading = myShading;
	}
	
	public PolygonItem() {
		super();
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
			this.lineColour = Color.web(colour);
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
			this.fillColour = Color.web(colour);
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
