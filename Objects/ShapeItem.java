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
* Description: This class holds the information for shapes received from the xml document.
*/

package Objects;

import javafx.scene.paint.Color;

public class ShapeItem extends Item
{
	private float xStart, yStart, width, height;
	private String type;
	private Color lineColour, fillColour;
	private ShadingItem shading;
	
	public ShapeItem(int myStartTime, int myDuration, float myXStart, float myYStart, float myWidth, float myHeight, String myType, Color myLineColour, Color myFillColour, ShadingItem myShading)
	{
		xStart = myXStart;
		yStart = myYStart;
		width = myWidth;
		height = myHeight;
		type = myType;
		lineColour = myLineColour;
		fillColour = myFillColour;
		shading = myShading;
		
	}

	public ShapeItem() {
		super();
	}
	

	public float getxStart() 
	{
		return xStart;
	}

	public void setxStart(float xStart)
	{
		this.xStart = xStart;
	}
	
	public void setxStart(String xStart)
	{
		this.xStart = Float.parseFloat(xStart);
	}
	
	public float getyStart() 
	{
		return yStart;
	}

	public void setyStart(float yStart) 
	{
		this.yStart = yStart;
	}
	
	public void setyStart(String yStart)
	{
		this.yStart = Float.parseFloat(yStart);
	}
	
	public float getWidth() 
	{
		return width;
	}

	public void setWidth(float width) 
	{
		this.width = width;
	}
	
	public void setWidth(String width)
	{
		this.width = Float.parseFloat(width);
	}
	
	public float getHeight()
	{
		return height;
	}

	public void setHeight(float height) 
	{
		this.height = height;
	}
	
	public void setHeight(String height)
	{
		this.height = Float.parseFloat(height);
	}

	public String getType() 
	{
		return type;
	}

	public void setType(String type) 
	{
		this.type = type;
	}

	public Color getLineColour()
	{
		return lineColour;
	}

	public void setLineColour(Color lineColour) 
	{
		this.lineColour = lineColour;
	}
	
	public void setLineColour(String colour)
	{
		try
		{
			this.lineColour = Color.web(colour);
		}
		catch(Exception e){
			System.out.println("\nNo colour set for ShapeItem line colour");
		}
	}

	public Color getFillColour() 
	{
		return fillColour;
	}

	public void setFillColour(Color fillColour) 
	{
		this.fillColour = fillColour;
	}
	
	public void setFillColour(String colour)
	{
		try
		{
			this.fillColour = Color.web(colour);
		}
		catch(Exception e){
			System.out.println("\nNo colour set for ShapeItem fill colour");
		}
	}

	public ShadingItem getShading() {
		return shading;
	}

	public void setShading(ShadingItem shading) {
		this.shading = shading;
	}


	
	
}
