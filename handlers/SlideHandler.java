package handlers;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.prism.paint.Color;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import Objects.*;

public class SlideHandler {
	
	private Presentation pres;
	
	public SlideHandler() {
		super();
	}

	public StackPane getSlideStack(Presentation presentation, int i, int width, int height) throws IOException {
		this.pres = presentation;
		System.out.println("\nSlides in presentation according to handler: " +presentation.getSlides().size());
			StackPane slidePane = new StackPane();
			for (int x = 0; x < presentation.getSlides().get(i).getTextList().size(); x++)
        	{
				TextHandler textHandler = new TextHandler();
				textHandler.setWindowSize(width,height);
				TextFlow textFlow = textHandler.setText(presentation.getSlides().get(i).getTextList().get(x));
				textFlow.setTextAlignment(TextAlignment.JUSTIFY);
				AnchorPane anchor = new AnchorPane();
				anchor.getChildren().add(textFlow);
				slidePane.getChildren().addAll(anchor);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getImageList().size(); x++)
        	{
				ImageHandler imageHandler = new ImageHandler();
				Canvas imageCanvas = imageHandler.drawCanvas(presentation.getSlides().get(i).getImageList().get(x), width, height);
				AnchorPane anchor = new AnchorPane();
				anchor.getChildren().add(imageCanvas);
				slidePane.getChildren().addAll(anchor);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getPolygonList().size(); x++)
        	{
				GraphicsHandler graphicsHandler = new GraphicsHandler();
				Canvas polygonCanvas = graphicsHandler.drawCanvas(presentation.getSlides().get(i).getPolygonList().get(x), width, height);
				AnchorPane anchor = new AnchorPane();
				anchor.getChildren().add(polygonCanvas);
				slidePane.getChildren().addAll(anchor);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getVideoList().size(); x++)
        	{
				Video videoHandler = new Video(presentation.getSlides().get(i).getVideoList().get(x));
				slidePane.getChildren().add(videoHandler);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getAudioList().size(); x++)
        	{
				AudioHandler audioHandler = new AudioHandler(presentation.getSlides().get(i).getAudioList().get(x));
				slidePane.getChildren().add(audioHandler);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getShapeList().size(); x++)
        	{
				GraphicsHandler graphicsHandler = new GraphicsHandler();
				Canvas shapeCanvas = graphicsHandler.drawCanvas(presentation.getSlides().get(i).getShapeList().get(x), width, height);
				AnchorPane anchor = new AnchorPane();
				anchor.getChildren().add(shapeCanvas);
				slidePane.getChildren().addAll(anchor);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getInteractableList().size(); x++)
        	{
				
        	}
			return slidePane;
		}

}
