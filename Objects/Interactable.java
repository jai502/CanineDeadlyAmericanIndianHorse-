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
* Description: This class holds the information for interactable files received from the xml document.
*/

package Objects;

import java.util.ArrayList;

public class Interactable 
{
	private int targetSlide;
	private ArrayList<Text> textList;
	private ArrayList<Shape> shapeList;
	private ArrayList<Polygon> polygonList;
	private ArrayList<Image> imageList;
	private ArrayList<Video> videoList;
	private ArrayList<Audio> audioList;
		
	public Interactable(ArrayList<Text> textList, ArrayList<Shape> shapeList,
			ArrayList<Polygon> polygonList, ArrayList<Image> imageList,
			ArrayList<Video> videoList, ArrayList<Audio> audioList) {
		super();
		this.textList = textList;
		this.shapeList = shapeList;
		this.polygonList = polygonList;
		this.imageList = imageList;
		this.videoList = videoList;
		this.audioList = audioList;
	}


	public Interactable() {
		textList = new ArrayList<Text>();
		shapeList = new ArrayList<Shape>();
		polygonList = new ArrayList<Polygon>();
		imageList = new ArrayList<Image>();
		videoList = new ArrayList<Video>();
		audioList = new ArrayList<Audio>();
	}
	
	
	public int getTargetSlide() {
		return targetSlide;
	}


	public void setTargetSlide(int targetSlide) {
		this.targetSlide = targetSlide;
	}
	
	public void setTargetSlide(String targetSlide)
	{
		try
		{
			this.targetSlide = Integer.parseInt("targetSlide");
		}
		catch(Exception e)
		{
			this.targetSlide = 0;
		}
		
	}
	
	public ArrayList<Text> getTextList()
	{
		return textList;
	}
	
	public void addText (Text text)
	{
		textList.add(text);
	}
	
	public ArrayList<Shape> getShapeList() 
	{
		return shapeList;
	}
	
	public void addShape (Shape shape)
	{
		shapeList.add(shape);
	}
	
	public ArrayList<Polygon> getPolygonList() 
	{
		return polygonList;
	}
	
	public void addPolygon(Polygon polygon) {
		polygonList.add(polygon);
	}
	
	public ArrayList<Image> getImageList() 
	{
		return imageList;
	}
	
	public void addImage (Image image)
	{
		imageList.add(image);
	}
	
	public ArrayList<Video> getVideoList()
	{
		return videoList;
	}
	
	public void addVideo (Video video)
	{
		videoList.add(video);
	}
	
	public ArrayList<Audio> getAudioList() 
	{
		return audioList;
	}
	
	public void addAudio (Audio audio)
	{
		audioList.add(audio);
	}
	

	
	
	
}
