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
		super();
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

	public void setNextSlide(String string) {
		this.nextSlide = Integer.parseInt(string);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(String string) {
		this.duration = Integer.parseInt(string);
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
			String r = colour.substring(0,1);
			String g = colour.substring(2,3);
			String b = colour.substring(4,5);
			int rInt = Integer.parseInt(r, 16);
			int gInt = Integer.parseInt(g, 16);
			int bInt = Integer.parseInt(b,  16);
			backgroundColour = new Color(rInt,gInt,bInt);
		}
		catch(Exception e){
			System.out.println("\nAn error occured when setting colour");
		}
	}

	public ArrayList<Text> getTextList() {
		return textList;
	}

	public void setTextList(ArrayList<Text> textList) {
		this.textList = textList;
	}

	public ArrayList<Shape> getShapeList() {
		return shapeList;
	}

	public void setShapeList(ArrayList<Shape> shapeList) {
		this.shapeList = shapeList;
	}

	public ArrayList<Polygon> getPolygonList() {
		return polygonList;
	}

	public void setPolygonList(ArrayList<Polygon> polygonList) {
		this.polygonList = polygonList;
	}

	public ArrayList<Image> getImageList() {
		return imageList;
	}

	public void setImageList(ArrayList<Image> imageList) {
		this.imageList = imageList;
	}

	public ArrayList<Video> getVideoList() {
		return videoList;
	}

	public void setVideoList(ArrayList<Video> videoList) {
		this.videoList = videoList;
	}

	public ArrayList<Audio> getAudioList() {
		return audioList;
	}

	public void setAudioList(ArrayList<Audio> audioList) {
		this.audioList = audioList;
	}

	public ArrayList<Interactable> getInteractableList() {
		return interactableList;
	}

	public void setInteractableList(ArrayList<Interactable> interactableList) {
		this.interactableList = interactableList;
	}

	public void setSlideId(String string) {
		this.slideId =Integer.parseInt(string);
	}

	
	
}
