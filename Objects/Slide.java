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
* Description: This class holds the information for shapes received from the xml document.
*/

package Objects;

import java.awt.Color;

public class Slide {
	private int slideId, nextSlide, duration;
	private Color backgroundColor;
	private Text text;
	private Shape shape;
	private Polygon polygon;
	private Image image;
	private Video video;
	private Audio audio;
	private Interactable interactable;
	
	public Slide(int slideId, int nextSlide, int duration,Color backgroundColor, Text text, Shape shape, Polygon polygon, Image image, Video video, Audio audio, Interactable interactable) 
	{
		super();
		this.slideId = slideId;
		this.nextSlide = nextSlide;
		this.duration = duration;
		this.backgroundColor = backgroundColor;
		this.text = text;
		this.shape = shape;
		this.polygon = polygon;
		this.image = image;
		this.video = video;
		this.audio = audio;
		this.interactable = interactable;
	}

	public int getSlideId()
	{
		return slideId;
	}

	public void setSlideId(int slideId) 
	{
		this.slideId = slideId;
	}

	public int getNextSlide()
	{
		return nextSlide;
	}

	public void setNextSlide(int nextSlide)
	{
		this.nextSlide = nextSlide;
	}

	public int getDuration() 
	{
		return duration;
	}

	public void setDuration(int duration) 
	{
		this.duration = duration;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		this.backgroundColor = backgroundColor;
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

	public Audio getAudio()
	{
		return audio;
	}

	public void setAudio(Audio audio)
	{
		this.audio = audio;
	}

	public Interactable getInteractable()
	{
		return interactable;
	}

	public void setInteractable(Interactable interactable) 
	{
		this.interactable = interactable;
	}
	
}
