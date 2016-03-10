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
* Description: This class holds the information for InteractableItem files received from the xml document.
*/

package Objects;

import java.util.ArrayList;

public class InteractableItem extends Item 
{
	private int targetSlide;
	private ArrayList<TextItem> textList;
	private ArrayList<ShapeItem> shapeList;
	private ArrayList<PolygonItem> polygonList;
	private ArrayList<ImageItem> imageList;
	private ArrayList<VideoItem> videoList;
	private ArrayList<AudioItem> audioList;
		
	public InteractableItem(ArrayList<TextItem> textList, ArrayList<ShapeItem> shapeList,
			ArrayList<PolygonItem> polygonList, ArrayList<ImageItem> imageList,
			ArrayList<VideoItem> videoList, ArrayList<AudioItem> audioList) {
		super();
		this.textList = textList;
		this.shapeList = shapeList;
		this.polygonList = polygonList;
		this.imageList = imageList;
		this.videoList = videoList;
		this.audioList = audioList;
	}


	public InteractableItem() {
		textList = new ArrayList<TextItem>();
		shapeList = new ArrayList<ShapeItem>();
		polygonList = new ArrayList<PolygonItem>();
		imageList = new ArrayList<ImageItem>();
		videoList = new ArrayList<VideoItem>();
		audioList = new ArrayList<AudioItem>();
	}
	
	
	public int getTargetSlide() {
		return targetSlide;
	}


	public void setTargetSlide(int targetSlide) {
		this.targetSlide = targetSlide;
	}
	
	public void setTargetSlide(String targetSlide)
	{

		this.targetSlide = Integer.parseInt(targetSlide);

	}
	
	public ArrayList<TextItem> getTextList()
	{
		return textList;
	}
	
	public void addText (TextItem text)
	{
		textList.add(text);
	}
	
	public ArrayList<ShapeItem> getShapeList() 
	{
		return shapeList;
	}
	
	public void addShape (ShapeItem shape)
	{
		shapeList.add(shape);
	}
	
	public ArrayList<PolygonItem> getPolygonList() 
	{
		return polygonList;
	}
	
	public void addPolygon(PolygonItem polygon) {
		polygonList.add(polygon);
	}
	
	public ArrayList<ImageItem> getImageList() 
	{
		return imageList;
	}
	
	public void addImage (ImageItem image)
	{
		imageList.add(image);
	}
	
	public ArrayList<VideoItem> getVideoList()
	{
		return videoList;
	}
	
	public void addVideo (VideoItem video)
	{
		videoList.add(video);
	}
	
	public ArrayList<AudioItem> getAudioList() 
	{
		return audioList;
	}
	
	public void addAudio (AudioItem audio)
	{
		audioList.add(audio);
	}
	

	
	
	
}
