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
* Description: This class holds the information for interactable files received from the xml document.
*/

package Objects;

public class Interactable 
{
	private Text text;
	private Shape shape;
	private Polygon polygon;
	private Image image;
	private Video video;
	
	public Interactable(Text text, Shape shape, Polygon polygon, Image image, Video video)
	{
		super();
		this.text = text;
		this.shape = shape;
		this.polygon = polygon;
		this.image = image;
		this.video = video;
	}

	public Text getText()
	{
		return text;
	}

	public void setText(Text text) 
	{
		this.text = text;
	}

	public Shape getShape() 
	{
		return shape;
	}

	public void setShape(Shape shape)
	{
		this.shape = shape;
	}

	public Polygon getPolygon()
{
		return polygon;
	}

	public void setPolygon(Polygon polygon)
	{
		this.polygon = polygon;
	}

	public Image getImage() 
	{
		return image;
	}

	public void setImage(Image image) 
	{
		this.image = image;
	}

	public Video getVideo() 
	{
		return video;
	}

	public void setVideo(Video video) 
	{
		this.video = video;
	}
	
	
	
}
