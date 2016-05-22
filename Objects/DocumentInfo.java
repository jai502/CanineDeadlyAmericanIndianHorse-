/*
* (C) Stammtisch
* First version created by: Joseph Ingleby & Callum Silver
* Date of first version: 22nd February 2016
* 
* Last version by: Joseph Ingleby & Callum Silver
* Date of last update: 22nd February 2016
* Version number: 1
* 
* Commit date: 
* Description: This class contains information about a presentation
*/

package Objects;

public class DocumentInfo {

	private String title, author, version, comment;
	
	public DocumentInfo(String myTitle, String myAuthor, String myVersion, String myComment)
	{
		title = myTitle;
		author = myAuthor;
		version = myVersion;
		comment = myComment;
	}

	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}

	public String getAuthor() 
	{
		return author;
	}

	public void setAuthor(String author) 
	{
		this.author = author;
	}

	public String getVersion() 
	{
		return version;
	}

	public void setVersion(String version) 
	{
		this.version = version;
	}

	public String getComment() 
	{
		return comment;
	}

	public void setComment(String comment) 
	{
		this.comment = comment;
	}
	
	
}
