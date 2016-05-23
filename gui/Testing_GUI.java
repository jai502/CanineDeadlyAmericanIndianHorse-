package gui;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;

//import static org.junit.Assert.*;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

public class Testing_GUI extends GuiTest {

	//	@org.junit.Test
	//	public void test() {
	//		fail("Not yet implemented");
	//	}

	JavaFXEventDemo main = new JavaFXEventDemo();
	//FlowPane result = new FlowPane();


	@Override
	public Parent getRootNode() {
		//JavaFXEventDemo main = new JavaFXEventDemo();
		//main.rootNode.getStylesheets().add("gui/style.css");
		return main.rootNode;
		//return null;
	}

	//	@Override
	//	public Parent getRootNode() {
	//		final Button btn = new Button();
	//		btn.setId("btn");
	//		btn.setText("Hello World");
	//		btn.setOnAction(new EventHandler<ActionEvent>() {
	//			@Override
	//			public void handle(ActionEvent actionEvent) {
	//				btn.setText( "was clicked" );
	//			}
	//		});
	//		return btn;
	//	}

	//	@Override
	//	protected Parent getRootNode() {
	//		FXMLLoader fxmlLoader = new FXMLLoader();
	//		try (InputStream inputStream = getClass()
	//				.getResourceAsStream(FXML_FILE)) {
	//			return fxmlLoader.load(inputStream);
	//		} catch (IOException e) {
	//			throw new IllegalStateException(e);
	//		}
	//	}

	@Override
	public void setupStage() throws Throwable {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Application.launch(JavaFXEventDemo.class);
			}
		})
		.start();
	}

	@Test
	public void testButtonClick(){
		Button button = ((Button) find("#up")); // requires your button to be tagged with setId("button")
		//Button button = (Button) main.rootNode.lookup("#up");
		click(button);
		//verifyThat( "#logIn", hasText("was clicked") );
	}


}
