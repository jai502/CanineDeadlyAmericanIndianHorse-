/*/
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
import java.io.IOException;

import Parsers.ReadTextFile;

public class Text {
	private int startTime, duration, fontSize;
	private double xStart, yStart;
	private String font;
	private Color fontColour;
	private String text;
	private String sourceText;
 
	 public Text(int myStartTime, int myDuration, int myFontSize, double myXStart, double myYStart, String myFont, Color myFontColour, String myText)
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
	 

	public Text() {
		super();
	}

	public int getStartTime()
	{
		return startTime;
	}
	
	public void setStartTime(String startTime) 
	{
		this.startTime = Integer.parseInt(startTime);
	}
	
	public void setStartTime(int startTime) 
	{
		this.startTime = startTime;
	}
	
	public int getDuration()
	{
		return duration;
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
	
	public void setFontSize(String fontSize)
	{
		try
		{
			this.fontSize = Integer.parseInt(fontSize);
		}
		catch(Exception e)
		{
			this.fontSize = 0;
		}
	}
	
	public double getxStart() 
	{
		return xStart;
	}
	
	public void setxStart(double xStart) 
	{
		this.xStart = xStart;
	}
	
	public void setxStart(String xStart)
	{
		this.xStart = Double.parseDouble(xStart);
	}
	public double getyStart() 
	{
		return yStart;
	}
	public void setyStart(double yStart) 
	{
		this.yStart = yStart;
	}
	
	public void setyStart(String yStart)
	{
		this.yStart = Double.parseDouble(yStart);
	}
	
	public String getFont() 
	{
		return font;
	}
	public void setFont(String font) 
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
	
	public void setFontColour (String colour)
	{
		try
		{
			if(colour.startsWith("#")){
				colour.equals(colour.substring(1));
			}
			String r = colour.substring(0,2);
			String g = colour.substring(2,4);
			String b = colour.substring(4,6);
			int rInt = Integer.parseInt(r, 16);
			int gInt = Integer.parseInt(g, 16);
			int bInt = Integer.parseInt(b,  16);
			fontColour = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nNo colour set for font colour");
		}
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setText(String text) 
	{
		this.text = text;
	}


	public String getSourceText() {
		return sourceText;
	}


	public void setSourceText(String sourceFile)
	{
		if (sourceFile != null){
			try
			{
				ReadTextFile reader = new ReadTextFile();
				this.sourceText = reader.ReadFile(sourceFile);
				
				
			}
			catch (IOException e)
			{
				
				System.out.println("Error source file for text");
			}
		}
		else
		{
			System.out.println("No source file for text");
		}
		
	} 
 
}
