package handlers;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import Objects.Images;

public class ImageHandler
{

	private Images imageToHandle;	
	Canvas canvas = new Canvas();
	
	public ImageHandler(Images imageToHandle) 
	{
		super();
		this.imageToHandle = imageToHandle;
		createCanvas();
	}

	public void createCanvas()
	{
		Image toDisplay = generateImage();
		String tempString;
		if(toDisplay.equals(null)){
			tempString = "null";
		}
		else
		{
				tempString = toDisplay.toString();
		}
		System.out.println("\ntoDisplay value: "+ tempString);
		System.out.println("\nReading: "+getImageToHandle().getSourceFile());
		GraphicsContext gContext = getCanvas().getGraphicsContext2D();
		gContext.drawImage(toDisplay, 0, 0);
	}
	
	public void setCanvasSize(int sizeOfSceneX, int sizeOfSceneY)
	{
		getCanvas().setWidth(sizeOfSceneX*getImageToHandle().getWidth());
		getCanvas().setHeight(sizeOfSceneY*getImageToHandle().getHeight());
	}
	
	public int getCanvasXPosition(int sizeOfSceneX)
	{
		return (int) (sizeOfSceneX*getImageToHandle().getxStart());
	}
	
	public int getCanvasYPosition(int sizeOfSceneY)
	{
		return (int)(sizeOfSceneY*getImageToHandle().getyStart());
	}
	
	private Image generateImage() 
	{
		return new Image(getImageToHandle().getSourceFile());
	}
	
	public int getCanvasDuration()
	{
		return getImageToHandle().getDuration();
	}

	public int getCanvasStartTime()
	{
		return getImageToHandle().getStartTime();
	}

	
	public Images getImageToHandle() 
	{
		return imageToHandle;
	}

	public void setImageToHandle(Images imageToHandle) 
	{
		this.imageToHandle = imageToHandle;
	}

	public Canvas getCanvas() 
	{
		return canvas;
	}

	public void setCanvas(Canvas canvas)
	{
		this.canvas = canvas;
	}

}
