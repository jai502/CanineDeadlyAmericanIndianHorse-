package gui;
import gui.JavaFXEventDemo;
import javafx.application.Application;
import javafx.scene.control.Label;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class Testing_GUI_2 {
	
	JavaFXEventDemo main;
	//FlowPane flowpane = new FlowPane();
	//Label label = new Label("Push a Button!");

	@Before
	public void setUp() throws Exception {
		main = new JavaFXEventDemo();
	}
	
//	//@Override
//	public void setupStage() throws Throwable{
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				Application.launch(JavaFXEventDemo.class);
//			}
//		})
//		.start();
//	}

	@Test
	public void test() {
		//fail("Not yet implemented");
//		assertEquals(flowpane, main.getRoot());
		//myStage.setTitle("Use a JavaFX buttons and events");
		assertEquals("Push a Button!", main.response.getText());
	}

}
