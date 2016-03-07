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

import javafx.scene.paint.Color;
import java.io.IOException;

import Parsers.ReadTextFile;

public class TextItem extends Item
{
	private int fontSize;
	private float xStart, yStart, height, width;
	private String font;
	private Color fontColour;
	private String text;
	private String sourceText;
 
	 public TextItem(int myStartTime, int myDuration, int myFontSize, float myXStart, float myYStart, String myFont, Color myFontColour, String myText)
	 {
			 fontSize =  myFontSize;
			 xStart = myXStart;
			 yStart = myYStart;
			 font = myFont;
			 fontColour = myFontColour;
			 text = myText;
	}
	 

	public TextItem() {
		super();
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
			this.fontColour = Color.web(colour);
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
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setHeight(String height)
	{
		if (height.equals(null))
		{
			System.out.println("no height for text set");
		}
		else
		{
			this.height = Float.parseFloat(height);
		}
	}
	
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public void setWidth(String width)
	{
		if (width.equals(null))
		{
			System.out.println("no width for text set");
		}
		else
		{
			this.width = Float.parseFloat(width);
		}
		
	}
 
}
