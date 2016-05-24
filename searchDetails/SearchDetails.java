package searchDetails;


/*
 * (C) Stammtisch
 * First version created by: Mathew Gould & Alexander Stassis (Design Team)
 * Date of first version: 24/05/16
 * 
 * Last version by: Mathew Gould & Alexander Stassis (Design Team)
 * Date of last update:  24/05/16
 * Version number: 1
 * 
 * Commit date: 24/05/16
 * Description: Class which stores the data from the Search Results
 */

import java.io.Serializable;

public class SearchDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	private String title, author, language;

	public SearchDetails()
	{
	}

	// return the title credentials
	public String getTitle()
	{
		return title;
	}

	// return the author credentials
	public String getAuthor()
	{
		return author;
	}
	
	// return the language credentials
	public String getLanguage()
	{
		return language;
	}

	// Sets the title details
	public void setTitle(String setTitle)
	{
		this.title = setTitle;
	}

	// Sets the author details
	public void setAuthor(String setAuthor)
	{
		this.author = setAuthor;
	}

	// Sets the language details
	public void setLanguage(String setLanguage)
	{
		this.language = setLanguage;
	}

}
