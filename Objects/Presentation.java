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
* Description: This class holds the information for presentations received from the xml document.
*/

package Objects;

import java.util.ArrayList;

public class Presentation 
{
	private	DocumentInfo docInfo;
	private Defaults defaults;
	private ArrayList slides = new ArrayList<Slide>() ;
	
	public Presentation(DocumentInfo docInfo, Defaults defaults,ArrayList slides)
	{
		super();
		this.docInfo = docInfo;
		this.defaults = defaults;
		this.slides = slides;
	}

	public DocumentInfo getDocInfo() 
	{
		return docInfo;
	}

	public void setDocInfo(DocumentInfo docInfo) 
	{
		this.docInfo = docInfo;
	}

	public Defaults getDefaults() 
	{
		return defaults;
	}

	public void setDefaults(Defaults defaults) 
	{
		this.defaults = defaults;
	}

	public ArrayList getSlides()
	{
		return slides;
	}

	public void setSlides(ArrayList slides)
	{
		this.slides = slides;
	}
	
	
	
}
