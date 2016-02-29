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
import java.util.ArrayList;

public class Slide {
	private int slideId, nextSlide, duration;
	private Color backgroundColour;
	private ArrayList<Text> textList;
	private ArrayList<Shape> shapeList;
	private ArrayList<Polygon> polygonList;
	private ArrayList<Image> imageList;
	private ArrayList<Video> videoList;
	private ArrayList<Audio> audioList;
	private ArrayList<Interactable> interactableList;
	
	public Slide(){
		
		textList = new ArrayList<Text>();
		shapeList = new ArrayList<Shape>();
		polygonList = new ArrayList<Polygon>();
		imageList = new ArrayList<Image>();
		videoList = new ArrayList<Video>();
		audioList = new ArrayList<Audio>();
		interactableList = new ArrayList<Interactable>();
	}
	
	public Slide(int slideId, int nextSlide, int duration,
			Color backgroundColour, ArrayList<Text> textList,
			ArrayList<Shape> shapeList, ArrayList<Polygon> polygonList,
			ArrayList<Image> imageList, ArrayList<Video> videoList,
			ArrayList<Audio> audioList, ArrayList<Interactable> interactableList) {
		super();
		this.slideId = slideId;
		this.nextSlide = nextSlide;
		this.duration = duration;
		this.backgroundColour = backgroundColour;
		this.textList = textList;
		this.shapeList = shapeList;
		this.polygonList = polygonList;
		this.imageList = imageList;
		this.videoList = videoList;
		this.audioList = audioList;
		this.interactableList = interactableList;
	}



	public int getSlideId()
	{
		return slideId;
	}

	public int getNextSlide() {
		return nextSlide;
	}

	public void setNextSlide(String nextSlide) {
		try
		{
			this.nextSlide = Integer.parseInt(nextSlide);
		}
		catch(Exception e)
		{
			this.nextSlide = 0;
		}
		
	}

	public int getDuration() {
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

	public Color getBackgroundColour() {
		return backgroundColour;
	}

	public void setBackgroundColour(Color backgroundColour) {
		this.backgroundColour = backgroundColour;
	}
	
	public void setBackgroundColour(String colour) {
		try
		{
			if(colour.startsWith("#"))
				colour.equals(colour.substring(1));
			String r = colour.substring(0,2);
			String g = colour.substring(2,4);
			String b = colour.substring(4,6);
			int rInt = Integer.parseInt(r, 16);
			int gInt = Integer.parseInt(g, 16);
			int bInt = Integer.parseInt(b,  16);
			backgroundColour = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nNo colour set for slide background colour");
		}
	}

	public ArrayList<Text> getTextList() {
		return textList;
	}

	public void addText (Text text)
	{
		textList.add(text);
	}

	public ArrayList<Shape> getShapeList() {
		return shapeList;
	}

	public void addShape (Shape shape)
	{
		shapeList.add(shape);
	}

	public ArrayList<Polygon> getPolygonList() {
		return polygonList;
	}

	public void addPolygon(Polygon polygon) {
		polygonList.add(polygon);
	}

	public ArrayList<Image> getImageList() {
		return imageList;
	}
	
	public void addImage (Image image)
	{
		imageList.add(image);
	}

	public ArrayList<Video> getVideoList() {
		return videoList;
	}

	public void addVideo (Video video)
	{
		videoList.add(video);
	}

	public ArrayList<Audio> getAudioList() {
		return audioList;
	}
	
	public void addAudio (Audio audio)
	{
		audioList.add(audio);
	}

	public ArrayList<Interactable> getInteractableList() {
		return interactableList;
	}

	public void addInteractable (Interactable interactable)
	{
		interactableList.add(interactable);
	}

	public void setSlideId(String string) {
		this.slideId =Integer.parseInt(string);
	}

	
	
}
