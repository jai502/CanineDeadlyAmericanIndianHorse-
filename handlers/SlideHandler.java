package handlers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import Objects.*;

public class SlideHandler {
	
	ArrayList<StackPane> setupPres;
	Presentation pres;

	public SlideHandler() {
		super();
	}

	public ArrayList<StackPane> compilePresentation(Presentation presentation) throws IOException {
		this.pres = presentation;
		for (int i = 0; i < (presentation.getSlides().size()); i++)
		{
			setupPres = new ArrayList<StackPane>();			
			StackPane pane = new StackPane();
			for (int x = 0; x < presentation.getSlides().get(i).getTextList().size(); x++)
        	{
				
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getImageList().size(); x++)
        	{
				ImageHandler imageHandler = new ImageHandler();
				Canvas imageCanvas = imageHandler.drawCanvas(presentation.getSlides().get(i).getImageList().get(x), 800, 600);
				pane.getChildren().addAll(imageCanvas);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getShapeList().size(); x++)
        	{
				GraphicsHandler graphicsHandler = new GraphicsHandler();
				Canvas shapeCanvas = graphicsHandler.drawCanvas(presentation.getSlides().get(i).getShapeList().get(x), 800, 600);
				pane.getChildren().addAll(shapeCanvas);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getPolygonList().size(); x++)
        	{
				GraphicsHandler graphicsHandler = new GraphicsHandler();
				Canvas polygonCanvas = graphicsHandler.drawCanvas(presentation.getSlides().get(i).getPolygonList().get(x), 800, 600);
				pane.getChildren().addAll(polygonCanvas);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getVideoList().size(); x++)
        	{
				Video videoHandler = new Video(presentation.getSlides().get(i).getVideoList().get(x));
				pane.getChildren().addAll(videoHandler);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getAudioList().size(); x++)
        	{
				AudioHandler audioHandler = new AudioHandler(presentation.getSlides().get(i).getAudioList().get(x));
				pane.getChildren().addAll(audioHandler);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getInteractableList().size(); x++)
        	{
				
        	}
			setupPres.add(pane);
		}
		
		return setupPres;
		
	}

}
