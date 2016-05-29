/*
 * (C) Stammtisch
 * First version created by: Callum Silver
 * Date of first version: 7th March 2016
 * 
 * Last version by: Callum Silver
 * Date of last update: 6th May 2016
 * Version number: 3.0
 * 
 * Commit date: 7th March 2016
 * Description: This class compiles all of the slides and includes the
 * ability to time them using a time line.
 * It also includes the updated media handler (6th May)
 * 
 */
package handlers;

import java.io.IOException;
import java.util.ArrayList;

import com.sun.prism.paint.Color;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
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

		// Adding a TimeLine
		Timeline timeline = new Timeline();

		// Slide background

		Canvas backgroundCanvas = new Canvas(width, height);
		GraphicsContext gc= backgroundCanvas.getGraphicsContext2D();
		gc.setFill(presentation.getSlides().get(i).getBackgroundColour());
		gc.fillRect(0,0,backgroundCanvas.getWidth(),backgroundCanvas.getHeight());

		AnchorPane backgroundAnchor = new AnchorPane();
		backgroundAnchor.getChildren().add(backgroundCanvas);
		slidePane.getChildren().addAll(backgroundAnchor);





		for (int x = 0; x < presentation.getSlides().get(i).getImageList().size(); x++)
		{
			ImageHandler imageHandler = new ImageHandler();
			Canvas imageCanvas = imageHandler.drawCanvas(presentation.getSlides().get(i).getImageList().get(x), width, height);

			// First be not visible;
			imageCanvas.setVisible(false);
			// Ensure the image will be shown, even if there is no start time
			if(presentation.getSlides().get(i).getImageList().get(x).getStartTime() == 0)
			{
				imageCanvas.setVisible(true);
			}
			// Add start time to the timeline
			KeyValue visible_value_start = new KeyValue(imageCanvas.visibleProperty(), true);
			KeyFrame visible_start_time = new KeyFrame(Duration.millis(presentation.getSlides().get(i).getImageList().get(x).getStartTime()), visible_value_start);
			System.out.println("imageCanvas "  + x + " start time: " + presentation.getSlides().get(i).getImageList().get(x).getStartTime());
			timeline.getKeyFrames().add(visible_start_time);
			// Add "Duration" or end time to the timeline
			if((presentation.getSlides().get(i).getImageList().get(x).getDuration() > 0))
			{
				KeyValue visible_value_end = new KeyValue(imageCanvas.visibleProperty(), false);
				KeyFrame visible_end_time = new KeyFrame(Duration.millis(presentation.getSlides().get(i).getImageList().get(x).getStartTime() + 
						presentation.getSlides().get(i).getImageList().get(x).getDuration()), visible_value_end);
				System.out.println("imageCanvas " + x + " duration time: " + presentation.getSlides().get(i).getImageList().get(x).getDuration());
				timeline.getKeyFrames().add(visible_end_time);
			}

			AnchorPane anchor = new AnchorPane();
			anchor.getChildren().add(imageCanvas);  
			slidePane.getChildren().addAll(anchor);
		}
		for (int x = 0; x < presentation.getSlides().get(i).getPolygonList().size(); x++)
		{
			GraphicsHandler graphicsHandler = new GraphicsHandler();
			Canvas polygonCanvas = graphicsHandler.drawCanvas(presentation.getSlides().get(i).getPolygonList().get(x), width, height);

			// First be not visible;
			polygonCanvas.setVisible(false);
			// Ensure the image will be shown, even if there is no start time
			if(presentation.getSlides().get(i).getPolygonList().get(x).getStartTime() == 0)
			{
				polygonCanvas.setVisible(true);
			}
			// Add start time to the timeline
			KeyValue visible_value_start = new KeyValue(polygonCanvas.visibleProperty(), true);
			KeyFrame visible_start_time = new KeyFrame(Duration.millis(presentation.getSlides().get(i).getPolygonList().get(x).getStartTime()), visible_value_start);
			System.out.println("polygonCanvas "  + x + " start time: " + presentation.getSlides().get(i).getPolygonList().get(x).getStartTime());
			timeline.getKeyFrames().add(visible_start_time);
			// Add "Duration" or end time to the timeline
			if((presentation.getSlides().get(i).getPolygonList().get(x).getDuration() > 0))
			{
				KeyValue visible_value_end = new KeyValue(polygonCanvas.visibleProperty(), false);
				KeyFrame visible_end_time = new KeyFrame(Duration.millis(presentation.getSlides().get(i).getPolygonList().get(x).getStartTime() + 
						presentation.getSlides().get(i).getPolygonList().get(x).getDuration()), visible_value_end);
				System.out.println("polygonCanvas " + x + " duration time: " + presentation.getSlides().get(i).getPolygonList().get(x).getDuration());
				timeline.getKeyFrames().add(visible_end_time);
			}

			AnchorPane anchor = new AnchorPane();
			anchor.getChildren().add(polygonCanvas);
			slidePane.getChildren().addAll(anchor);
		}
		
		for (int x = 0; x < presentation.getSlides().get(i).getShapeList().size(); x++)
		{

			GraphicsHandler graphicsHandler = new GraphicsHandler();
			Canvas shapeCanvas = graphicsHandler.drawCanvas(presentation.getSlides().get(i).getShapeList().get(x), width, height);

			// First be not visible;
			shapeCanvas.setVisible(false);
			// Ensure the image will be shown, even if there is no start time
			if(presentation.getSlides().get(i).getShapeList().get(x).getStartTime() == 0)
			{
				shapeCanvas.setVisible(true);
			}
			// Add start time to the timeline
			KeyValue visible_value_start = new KeyValue(shapeCanvas.visibleProperty(), true);
			KeyFrame visible_start_time = new KeyFrame(Duration.millis(presentation.getSlides().get(i).getShapeList().get(x).getStartTime()), visible_value_start);
			System.out.println("shapeCanvas "  + x + " start time: " + presentation.getSlides().get(i).getShapeList().get(x).getStartTime());
			timeline.getKeyFrames().add(visible_start_time);
			// Add "Duration" or end time to the timeline
			if((presentation.getSlides().get(i).getShapeList().get(x).getDuration() > 0))
			{
				KeyValue visible_value_end = new KeyValue(shapeCanvas.visibleProperty(), false);
				KeyFrame visible_end_time = new KeyFrame(Duration.millis(presentation.getSlides().get(i).getShapeList().get(x).getStartTime() + 
						presentation.getSlides().get(i).getShapeList().get(x).getDuration()), visible_value_end);
				System.out.println("shapeCanvas " + x + " duration time: " + presentation.getSlides().get(i).getShapeList().get(x).getDuration());
				timeline.getKeyFrames().add(visible_end_time);
			}

			AnchorPane anchor = new AnchorPane();
			anchor.getChildren().add(shapeCanvas);
			slidePane.getChildren().addAll(anchor);
		}
		for (int x = 0; x < presentation.getSlides().get(i).getTextList().size(); x++)
		{
			TextHandler textHandler = new TextHandler();
			textHandler.setWindowSize(width,height);
			TextFlow textFlow = textHandler.setText(presentation.getSlides().get(i).getTextList().get(x));
			textFlow.setTextAlignment(TextAlignment.JUSTIFY);

			// First be not visible;
			textFlow.setVisible(false);
			// Ensure the image will be shown, even if there is no start time
			if((presentation.getSlides().get(i).getTextList().get(x).getStartTime() <= 0))
			{
				textFlow.setVisible(true);
			}
			// Add start time to the timeline
			KeyValue visible_value_start = new KeyValue(textFlow.visibleProperty(), true);
			KeyFrame visible_start_time = new KeyFrame(Duration.millis(presentation.getSlides().get(i).getTextList().get(x).getStartTime()), visible_value_start);
			System.out.println("textFlow " + x + " start time: " + presentation.getSlides().get(i).getTextList().get(x).getStartTime());
			timeline.getKeyFrames().add(visible_start_time);
			// Add "Duration" or end time to the timeline
			if((presentation.getSlides().get(i).getTextList().get(x).getDuration() > 0))
			{
				KeyValue visible_value_end = new KeyValue(textFlow.visibleProperty(), false);
				KeyFrame visible_end_time = new KeyFrame(Duration.millis(presentation.getSlides().get(i).getTextList().get(x).getStartTime() + 
						presentation.getSlides().get(i).getTextList().get(x).getDuration()), visible_value_end);
				System.out.println("textFlow " + x + " duration time: " + presentation.getSlides().get(i).getTextList().get(x).getDuration());
				timeline.getKeyFrames().add(visible_end_time);
			}

			AnchorPane anchor = new AnchorPane();
			anchor.getChildren().add(textFlow);
			slidePane.getChildren().addAll(anchor);

		}
		for (int x = 0; x < presentation.getSlides().get(i).getVideoList().size(); x++)
		{

			//TODO add timing
			MediaFx videoPlayer = new MediaFx (presentation.getSlides().get(i).getVideoList().get(x), 0.3, 0.3);
			AnchorPane anchor = new AnchorPane();
			Group group = new Group();
			anchor.getChildren().add(group);
			group.getChildren().add(videoPlayer.createContent(scene));

			slidePane.getChildren().addAll(anchor);
		}
		for (int x = 0; x < presentation.getSlides().get(i).getAudioList().size(); x++)
		{
			//TODO add timing
			MediaFx audioPlayer = new MediaFx(presentation.getSlides().get(i).getAudioList().get(x));
			Group group = new Group();
			AnchorPane anchor = new AnchorPane();
			group.getChildren().add(audioPlayer.createContent(scene));
			anchor.getChildren().add(group);
			slidePane.getChildren().addAll(anchor);
		}
		
		timeline.play();


		return slidePane;
	}
}

