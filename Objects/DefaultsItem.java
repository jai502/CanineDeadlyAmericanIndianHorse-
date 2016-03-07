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
* Description: This class holds the information for the defaults received from the xml document.
*/

package Objects;

import javafx.scene.paint.Color;

public class DefaultsItem {
	private Color background, fontColour, lineColour, fillColour;
	private String font;
	private int	fontSize;
	
	public DefaultsItem(Color myBackground, Color myFontColour, Color myLineColour, Color myFillColour, String myFont, int myFontSize){
		background = myBackground;
		fontColour = myFontColour;
		lineColour = myLineColour;
		fillColour = myFillColour;
		font = myFont;
		fontSize = myFontSize;
	}
	
	public DefaultsItem() {
		super();
	}

	public Color getBackground() 
	{
		return background;
	}
	public void setBackground(Color background) 
	{
		this.background = background;
	}
	
	public void setBackground(String colour)
	{
		try
		{
			this.background = Color.web(colour);
		}
		catch(Exception e){
			System.out.println("\nNo colour set for default background colour");
		}
	}
	
	public Color getFontColour() 
	{
		return fontColour;
	}
	public void setFontColour(Color fontColour) 
	{
		this.fontColour = fontColour;
	}
	
	public void setFontColour(String colour)
	{
		try
		{
			this.fontColour = Color.web(colour);
		}
		catch(Exception e){
			System.out.println("\nNo colour set for default font colour");
		}
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
			System.out.println("\nNo colour set for default line colour");
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
			System.out.println("\nNo colour set for default fill colour");
		}
	}
	public String getFont() 
	{
		return font;
	}
	public void setFont(String font) 
	{
		this.font = font;
	}
	
	public int getFontSize() 
	{
		return fontSize;
	}
	public void setFontSize(int fontSize) 
	{
		this.fontSize = fontSize;
	}
	
	public void setFontSize(String fontSize) 
	{
		this.fontSize = Integer.parseInt(fontSize);
	}
	
}
