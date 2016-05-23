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
* Description: This class holds the information for imagefiles received from the xml document.
*/

package Objects;

public class ImageItem extends Item
{
	private String sourceFile;
	private float xStart, yStart, height, width;
	
	public ImageItem(String mySourceFile, int myStartTime, int myDuration, float myXStart, float myYStart, float myHeight, float myWidth)
	{
		sourceFile = mySourceFile; 
		xStart = myXStart; 
		yStart = myYStart; 
		height = myHeight; 
		width = myWidth;
	}
	
	public ImageItem() {
		super();
	}

	public String getSourceFile() {
		return sourceFile;
	}
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	
	public float getxStart() {
		return xStart;
	}
	
	public void setxStart(String xStart)
	{
		this.xStart = Float.parseFloat(xStart);
	}
	
	public void setxStart(float xStart) {
		this.xStart = xStart;
	}
	public float getyStart() {
		return yStart;
	}
	public void setyStart(float yStart) {
		this.yStart = yStart;
	}
	
	public void setyStart(String yStart)
	{
		this.yStart = Float.parseFloat(yStart);
	}
	
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setHeight(String height)
	{
		this.height = Float.parseFloat(height);
	}
	
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public void setWidth(String width)
	{
		this.width = Float.parseFloat(width);
	}
}

