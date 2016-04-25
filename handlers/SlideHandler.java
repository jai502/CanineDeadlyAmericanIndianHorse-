package handlers;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.prism.paint.Color;

import javafx.scene.Group;
import javafx.scene.Scene;
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

	public StackPane getSlideStack(Presentation presentation, int i, double myWidth, double myHeight, Scene scene) throws IOException {
		this.pres = presentation;
		double reducedHeight = (myHeight-60);
		double reducedWidth = (myWidth-5);
		int width = (int) Math.floor(reducedWidth);
		int height =(int) Math.floor(reducedHeight);
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
				MediaFx videoPlayer = new MediaFx (presentation.getSlides().get(i).getVideoList().get(x));
				AnchorPane anchor = new AnchorPane();
				Group group = new Group();
				anchor.getChildren().add(group);
				group.getChildren().add(videoPlayer.createContent(scene));
				
				slidePane.getChildren().addAll(anchor);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getAudioList().size(); x++)
        	{
				MediaFx audioPlayer = new MediaFx(presentation.getSlides().get(i).getAudioList().get(x));
				Group group = new Group();
				AnchorPane anchor = new AnchorPane();
				group.getChildren().add(audioPlayer.createContent(scene));
				anchor.getChildren().add(group);
				slidePane.getChildren().addAll(anchor);
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
