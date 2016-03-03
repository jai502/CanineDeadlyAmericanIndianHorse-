/*
* (C) Stammtisch
* First version created by: Joseph Ingleby & Callum Silver
* Date of first version: 2nd March 2016
* 
* Last version by: Joseph Ingleby & Callum Silver
* Date of last update: 3rd March 2016
* Version number: 2.0
* 
* Commit date: 3rd March 2016
* Description: This class holds takes an Images Object and returns a canvas containing the image in 
* the correct position.
*/
package handlers;

// Imports
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import Objects.Images;

public class ImageHandler
{
	private Images imageToHandle;
	
	//constructors
	public ImageHandler()
	{
		super();
	}
	
	//returns a canvas that contains the image
	public Canvas drawCanvas(Images imageToHandle , int sceneSizeX, int sceneSizeY) 
	{
		this.imageToHandle = imageToHandle;
		
		// set up a new canvas
		Canvas imageCanvas = new Canvas(sceneSizeX, sceneSizeY);
		GraphicsContext gContext = imageCanvas.getGraphicsContext2D();
		Image image = generateImage();
		// draw image (image, x start, y start, width, height)
		gContext.drawImage(image, getXPosition(sceneSizeX), getYPosition(sceneSizeY), getWidth(sceneSizeX), getHeight(sceneSizeY));
		
		return imageCanvas;
	}
	
	//this method gets the starting X position of the image for use in the canvas
	public double getXPosition(int sizeOfSceneX)
	{
		return (sizeOfSceneX*imageToHandle.getxStart());
	}

	//this method gets the starting Y position of the image for use in the canvas
	public double getYPosition(int sizeOfSceneY)
	{
		return (sizeOfSceneY*imageToHandle.getyStart());
	}
	
	//this method gets the width of the image for use in the canvas
	public double getWidth(int sizeOfSceneX)
	{
		return ((sizeOfSceneX*imageToHandle.getWidth()));
	}

	//this method gets the height of the image for use in the canvas
	public double getHeight(int sizeOfSceneY)
	{
		return ((sizeOfSceneY*imageToHandle.getHeight()));
	}
	
	//this method will read the source file and create the image
	private Image generateImage() 
	{
		return new Image(imageToHandle.getSourceFile());
	}

}
