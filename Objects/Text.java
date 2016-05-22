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
* Description: This class holds the information for text received from the xml document.
*/
package Objects;

import java.awt.Color;
import java.awt.Font;

public class Text {
	private int startTime, duration, fontSize;
	private double xStart, yStart;
	private Font font;
	private Color fontColour;
	private String text;
 
 public Text(int myStartTime, int myDuration, int myFontSize, double myXStart, double myYStart, Font myFont, Color myFontColour, String myText)
 {
	 startTime = myStartTime;
	 duration = myDuration;
	 fontSize =  myFontSize;
	 xStart = myXStart;
	 yStart = myYStart;
	 font = myFont;
	 fontColour = myFontColour;
	 text = myText;
 }
 
public int getStartTime()
{
	return startTime;
}
public void setStartTime(int startTime) 
{
	this.startTime = startTime;
}
public int getDuration()
{
	return duration;
}
public void setDuration(int duration) 
{
	this.duration = duration;
}
public int getFontSize() 
{
	return fontSize;
}
public void setFontSize(int fontSize) 
{
	this.fontSize = fontSize;
}
public double getxStart() 
{
	return xStart;
}
public void setxStart(double xStart) 
{
	this.xStart = xStart;
}
public double getyStart() 
{
	return yStart;
}
public void setyStart(double yStart) 
{
	this.yStart = yStart;
}
public Font getFont() 
{
	return font;
}
public void setFont(Font font) 
{
	this.font = font;
}
public Color getFontColour() 
{
	return fontColour;
}
public void setFontColour(Color fontColour) 
{
	this.fontColour = fontColour;
}
public String getText()
{
	return text;
}
public void setText(String text) 
{
	this.text = text;
} 
 
}
