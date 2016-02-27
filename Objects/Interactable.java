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
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public ArrayList<Text> getTextList()
	{
		return textList;
	}
	
	public void setTextList(ArrayList<Text> textList) 
	{
		this.textList = textList;
	}
	
	public ArrayList<Shape> getShapeList() 
	{
		return shapeList;
	}
	
	public void setShapeList(ArrayList<Shape> shapeList)
	{
		this.shapeList = shapeList;
	}
	
	public ArrayList<Polygon> getPolygonList() 
	{
		return polygonList;
	}
	
	public void setPolygonList(ArrayList<Polygon> polygonList)
	{
		this.polygonList = polygonList;
	}
	
	public ArrayList<Image> getImageList() 
	{
		return imageList;
	}
	
	public void setImageList(ArrayList<Image> imageList)
	{
		this.imageList = imageList;
	}
	
	public ArrayList<Video> getVideoList()
	{
		return videoList;
	}
	
	public void setVideoList(ArrayList<Video> videoList) 
	{
		this.videoList = videoList;
	}
	
	public ArrayList<Audio> getAudioList() 
	{
		return audioList;
	}
	
	public void setAudioList(ArrayList<Audio> audioList)
	{
		this.audioList = audioList;
	}
	

	
	
	
}