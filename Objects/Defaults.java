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
	
	//public Defaults(Color myBackground, Color myFontColour, Color myLineColour, Color myFillColour, Font myFont, int myFontSize){
	//	background = myBackground;
	//	fontColour = myFontColour;
	//	lineColour = myLineColour;
	//	fillColour = myFillColour;
	//	font = myFont;
	//	fontSize = myFontSize;
	//}/
	
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
			if(colour.startsWith("#"))
				colour.equals(colour.substring(1));
			String r = colour.substring(0,1);
			String g = colour.substring(2,3);
			String b = colour.substring(4,5);
			int rInt = Integer.parseInt(r, 16);
			int gInt = Integer.parseInt(g, 16);
			int bInt = Integer.parseInt(b,  16);
			background = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nAn error occured when setting colour");
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
			if(colour.startsWith("#"))
				colour.equals(colour.substring(1));
			String r = colour.substring(0,1);
			String g = colour.substring(2,3);
			String b = colour.substring(4,5);
			int rInt = Integer.parseInt(r, 16);
			int gInt = Integer.parseInt(g, 16);
			int bInt = Integer.parseInt(b,  16);
			fontColour = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nAn error occured when setting colour");
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
			if(colour.startsWith("#"))
				colour.equals(colour.substring(1));
			String r = colour.substring(0,1);
			String g = colour.substring(2,3);
			String b = colour.substring(4,5);
			int rInt = Integer.parseInt(r, 16);
			int gInt = Integer.parseInt(g, 16);
			int bInt = Integer.parseInt(b,  16);
			lineColour = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nAn error occured when setting colour");
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
			if(colour.startsWith("#"))
				colour.equals(colour.substring(1));
			String r = colour.substring(0,1);
			String g = colour.substring(2,3);
			String b = colour.substring(4,5);
			int rInt = Integer.parseInt(r, 16);
			int gInt = Integer.parseInt(g, 16);
			int bInt = Integer.parseInt(b,  16);
			fillColour = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nAn error occured when setting colour");
		}
	}
	public Font getFont() 
	{
		return font;
	}
	public void setFont(Font font) 
	{
		this.font = font;
	}
	
	public void setFont(String myFont)
	{
		try{
			font = Font.getFont(myFont);	
		}
		catch(Exception e){
			System.out.println("\nAn error occured when setting the font");
		}
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
