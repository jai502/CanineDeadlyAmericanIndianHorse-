package handlers;

import java.util.ArrayList;

import javafx.scene.layout.StackPane;
import Objects.*;

public class SlideHandler {
	
	ArrayList<StackPane> setupPres;
	Presentation pres;

	public SlideHandler(Presentation presentation) {
		super();
	}

	private ArrayList<StackPane> compilePresentation(Presentation presentation) {
		this.pres = presentation;
		for (int i = 0; i < (presentation.getSlides().size()); i++)
		{
			
		}
		setupPres = new ArrayList<StackPane>();
		
		return setupPres;
		
	}

}
