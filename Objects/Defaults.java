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

import java.awt.Color;
import java.awt.Font;

public class Defaults {
	private Color background, fontColour, lineColour, fillColour;
	private Font font;
	private int	fontSize;
	
	public Defaults(Color myBackground, Color myFontColour, Color myLineColour, Color myFillColour, Font myFont, int myFontSize){
		background = myBackground;
		fontColour = myFontColour;
		lineColour = myLineColour;
		fillColour = myFillColour;
		font = myFont;
		fontSize = myFontSize;
	}
	
	public Color getBackground() 
	{
		return background;
	}
	public void setBackground(Color background) 
	{
		this.background = background;
	}
	public Color getFontColour() 
	{
		return fontColour;
	}
	public void setFontColour(Color fontColour) 
	{
		this.fontColour = fontColour;
	}
	public Color getLineColour() 
	{
		return lineColour;
	}
	public void setLineColour(Color lineColour) 
	{
		this.lineColour = lineColour;
	}
	public Color getFillColour() 
	{
		return fillColour;
	}
	public void setFillColour(Color fillColour) 
	{
		this.fillColour = fillColour;
	}
	public Font getFont() 
	{
		return font;
	}
	public void setFont(Font font) 
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
	
}
