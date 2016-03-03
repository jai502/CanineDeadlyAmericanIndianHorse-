package handlers;


import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Objects.Images;
import javax.imageio.*;

import com.sun.prism.Graphics;

public class ImageHandler  extends Application
{
	
	private Images imageToHandle = new	Images("handlers/house.jpg", 0, 0, 0, 0, 0.1 , 0.1);
	
	//constructors
	public ImageHandler(Images imageToHandle) 
	{
		super();
		this.imageToHandle = imageToHandle;
	}
	
	public ImageHandler()
	{
		super();
	}
	
	//returns a canvas that contains the image
	public Canvas establishCanvas(int x, int y) 
	{
		Canvas imageCanvas = new Canvas(x, y);
		GraphicsContext gContext = imageCanvas.getGraphicsContext2D();
		Image image = generateImage();
//		image = resizedImage(image,getCanvasXSize(x),getCanvasYSize(y));
		gContext.drawImage(resizedImage(image,getCanvasXSize(x),getCanvasYSize(y)), 0, 0);
		
		return imageCanvas;
	}
	
	//method to resize the image before it goes on the canvas
	public Image resizedImage(Image image, int canvasX, int canvasY)
	{
		ImageView iView = new ImageView(image);
		iView.setPreserveRatio(true);
		iView.setFitHeight(canvasY);
		iView.fitHeightProperty();
		Image resizedImage = iView.getImage();
		return resizedImage;

	}

	//this method will take the return the desired canvas position when given the size of the scene
	public int getCanvasXPosition(int sizeOfSceneX)
	{
		return (int) (sizeOfSceneX*getImageToHandle().getxStart());
	}
	

	//this method will take the return the desired canvas position when given the size of the scene
	public int getCanvasYPosition(int sizeOfSceneY)
	{
		return (int)(sizeOfSceneY*getImageToHandle().getyStart());
	}
	
	//this method will read the source file and create the image
	private Image generateImage() 
	{
		return new Image(this.imageToHandle.getSourceFile());

	}
	
	
	//this method will take the return the desired canvas height when given the size of the scene
	public int getCanvasYSize(int sizeOfSceneY)
	{
		return (int)(sizeOfSceneY*getImageToHandle().getHeight());
	}

	//this method will take the return the desired canvas width when given the size of the scene
	public int getCanvasXSize(int sizeOfSceneX)
	{
		return (int)(sizeOfSceneX*getImageToHandle().getWidth());
	}
	
	
	//returns the time the canvas will be displayed for
	public int getCanvasDuration()
	{
		return getImageToHandle().getDuration();
	}

	//returns the time the canvas will be start to be displayed for
	public int getCanvasStartTime()
	{
		return getImageToHandle().getStartTime();
	}

	//getter and setters
	public Images getImageToHandle() 
	{
		return imageToHandle;
	}

	public void setImageToHandle(Images imageToHandle) 
	{
		this.imageToHandle = imageToHandle;
	}


	@Override
	public void start(Stage primary) throws Exception 
	{
	
		StackPane root = new StackPane();
		Scene scene = new Scene(root, 700, 700);
		Canvas imageCanvas = establishCanvas(500, 500);
		
		root.getChildren().addAll(imageCanvas);
		primary.setScene(scene);
		primary.show();
		
	}


	
	public static void main(String[] args)
	{
		launch(args);
	}
}
