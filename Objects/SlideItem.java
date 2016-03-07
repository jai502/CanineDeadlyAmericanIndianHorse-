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

public class SlideItem {
	private int slideId, nextSlide, duration;
	private Color backgroundColour;
	private ArrayList<TextItem> textList;
	private ArrayList<ShapeItem> shapeList;
	private ArrayList<PolygonItem> polygonList;
	private ArrayList<ImageItem> imageList;
	private ArrayList<VideoItem> videoList;
	private ArrayList<AudioItem> audioList;
	private ArrayList<InteractableItem> interactableList;
	
	public SlideItem(){
		
		textList = new ArrayList<TextItem>();
		shapeList = new ArrayList<ShapeItem>();
		polygonList = new ArrayList<PolygonItem>();
		imageList = new ArrayList<ImageItem>();
		videoList = new ArrayList<VideoItem>();
		audioList = new ArrayList<AudioItem>();
		interactableList = new ArrayList<InteractableItem>();
	}
	
	public SlideItem(int slideId, int nextSlide, int duration,
			Color backgroundColour, ArrayList<TextItem> textList,
			ArrayList<ShapeItem> shapeList, ArrayList<PolygonItem> polygonList,
			ArrayList<ImageItem> imageList, ArrayList<VideoItem> videoList,
			ArrayList<AudioItem> audioList, ArrayList<InteractableItem> interactableList) {
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

	public ArrayList<TextItem> getTextList() {
		return textList;
	}

	public void addText (TextItem text)
	{
		textList.add(text);
	}

	public ArrayList<ShapeItem> getShapeList() {
		return shapeList;
	}

	public void addShape (ShapeItem shape)
	{
		shapeList.add(shape);
	}

	public ArrayList<PolygonItem> getPolygonList() {
		return polygonList;
	}

	public void addPolygon(PolygonItem polygon) {
		polygonList.add(polygon);
	}

	public ArrayList<ImageItem> getImageList() {
		return imageList;
	}
	
	public void addImage (ImageItem image)
	{
		imageList.add(image);
	}

	public ArrayList<VideoItem> getVideoList() {
		return videoList;
	}

	public void addVideo (VideoItem video)
	{
		videoList.add(video);
	}

	public ArrayList<AudioItem> getAudioList() {
		return audioList;
	}
	
	public void addAudio (AudioItem audio)
	{
		audioList.add(audio);
	}

	public ArrayList<InteractableItem> getInteractableList() {
		return interactableList;
	}

	public void addInteractable (InteractableItem interactable)
	{
		interactableList.add(interactable);
	}

	public void setSlideId(String string) {
		this.slideId =Integer.parseInt(string);
	}

	
	
}
