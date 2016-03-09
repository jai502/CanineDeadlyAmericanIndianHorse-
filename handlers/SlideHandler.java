package handlers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import Objects.*;

public class SlideHandler {
	
	private ArrayList<StackPane> setupPres = new ArrayList<StackPane>();
	private Presentation pres;
	private StackPane tempPane = new StackPane();
	
	public SlideHandler() {
		super();
	}

	public ArrayList<StackPane> compilePresentation(Presentation presentation) throws IOException {
		setPres(presentation);
		System.out.println("\nSlides in presentation according to handler: "+presentation.getSlides().size());
		for (int i = 0; i < (presentation.getSlides().size()); i++)
		{
			for (int x = 0; x < presentation.getSlides().get(i).getTextList().size(); x++)
        	{
				
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getImageList().size(); x++)
        	{
				ImageHandler imageHandler = new ImageHandler();
				Canvas imageCanvas = imageHandler.drawCanvas(presentation.getSlides().get(i).getImageList().get(x), 800, 600);
				getTempPane().getChildren().addAll(imageCanvas);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getShapeList().size(); x++)
        	{
				GraphicsHandler graphicsHandler = new GraphicsHandler();
				Canvas shapeCanvas = graphicsHandler.drawCanvas(presentation.getSlides().get(i).getShapeList().get(x), 800, 600);
				getTempPane().getChildren().addAll(shapeCanvas);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getPolygonList().size(); x++)
        	{
				GraphicsHandler graphicsHandler = new GraphicsHandler();
				Canvas polygonCanvas = graphicsHandler.drawCanvas(presentation.getSlides().get(i).getPolygonList().get(x), 800, 600);
				getTempPane().getChildren().addAll(polygonCanvas);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getVideoList().size(); x++)
        	{
				Video videoHandler = new Video(presentation.getSlides().get(i).getVideoList().get(x));
				getTempPane().getChildren().addAll(videoHandler);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getAudioList().size(); x++)
        	{
				AudioHandler audioHandler = new AudioHandler(presentation.getSlides().get(i).getAudioList().get(x));
				getTempPane().getChildren().addAll(audioHandler);
        	}
			for (int x = 0; x < presentation.getSlides().get(i).getInteractableList().size(); x++)
        	{
				
        	}
			getSetupPres().add(i,getTempPane());
		}
		
		return getSetupPres();
		
	}

	public ArrayList<StackPane> getSetupPres() {
		return setupPres;
	}

	public void setSetupPres(ArrayList<StackPane> setupPres) {
		this.setupPres = setupPres;
	}

	public Presentation getPres() {
		return pres;
	}

	public void setPres(Presentation pres) {
		this.pres = pres;
	}

	public StackPane getTempPane() {
		return tempPane;
	}

	public void setTempPane(StackPane tempPane) {
		this.tempPane = tempPane;
	}

}
