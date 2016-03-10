package handlers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import Objects.*;

public class SlideHandler {
	
	private ArrayList<StackPane> slideList = new ArrayList<StackPane>();
	private Presentation pres;
	
	public SlideHandler() {
		super();
	}

	public void compilePresentation(Presentation presentation) throws IOException {
		setPres(presentation);
		System.out.println("\nSlides in presentation according to handler: " +presentation.getSlides().size());
		for (int i = 0; i < (presentation.getSlides().size()); i++)
		{
			StackPane tempPane = new StackPane();
			for (int x = 0; x < presentation.getSlides().get(i).getTextList().size(); x++)
        	{
				TextHandler textHandler = new TextHandler();
				textHandler.setWindowSize(800,600);
				TextFlow textFlow = textHandler.setText(presentation.getSlides().get(i).getTextList().get(x));
				textFlow.setPrefWidth(100);
				textFlow.setTextAlignment(TextAlignment.JUSTIFY);
				tempPane.getChildren().addAll(textFlow);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getImageList().size(); x++)
        	{
				ImageHandler imageHandler = new ImageHandler();
				Canvas imageCanvas = imageHandler.drawCanvas(presentation.getSlides().get(i).getImageList().get(x), 800, 600);
				tempPane.getChildren().addAll(imageCanvas);
				System.out.println(presentation.getSlides().get(i).getImageList().get(x));
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getPolygonList().size(); x++)
        	{
				GraphicsHandler graphicsHandler = new GraphicsHandler();
				Canvas polygonCanvas = graphicsHandler.drawCanvas(presentation.getSlides().get(i).getPolygonList().get(x), 800, 600);
				tempPane.getChildren().addAll(polygonCanvas);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getVideoList().size(); x++)
        	{
				Video videoHandler = new Video(presentation.getSlides().get(i).getVideoList().get(x));
				tempPane.getChildren().addAll(videoHandler);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getAudioList().size(); x++)
        	{
				AudioHandler audioHandler = new AudioHandler(presentation.getSlides().get(i).getAudioList().get(x));
				tempPane.getChildren().addAll(audioHandler);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getShapeList().size(); x++)
        	{
				GraphicsHandler graphicsHandler = new GraphicsHandler();
				Canvas shapeCanvas = graphicsHandler.drawCanvas(presentation.getSlides().get(i).getShapeList().get(x), 800, 600);
				tempPane.getChildren().addAll(shapeCanvas);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getInteractableList().size(); x++)
        	{
				
        	}
			slideList.add(i,tempPane);
		}
		
	}

	public Presentation getPres() {
		return pres;
	}

	public void setPres(Presentation pres) {
		this.pres = pres;
	}

	public ArrayList<StackPane> getSlides() {
		return slideList;
	}

}
