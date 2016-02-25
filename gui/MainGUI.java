package gui;

import java.io.File;

/*
 * (C) Stammtisch
 * First version created by: Mathew Gould & Alexander Stassis (Design Team)
 * Date of first version: 21/02/16
 * 
 * Last version by: 24/02/16
 * Date of last update: 
 * Version number: 1
 * 
 * Commit date: 25/02/16
 * Description: Designing the Main GUI Class which will lead to other GUIs
 * This class currently is in implementation and the methods will be put into
 * another class?? 
 * 
 * NOTE: Global Private Variables need to be changed. Comments still need to be improved!
 * 		 Menu Items need to be implemented more.
 */

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.*;

public class MainGUI extends Application 
{

	private Stage window;
	private Scene mainMenu, logInMenu, signUpMenu, presentationMenu;
	// Default message label variable
	//private Label welcomeMessage;
	/* variables for Main Menu */
	/* variables for Login Menu */
	/* variables for SignUp Menu */
	private Label userName, passWord;
	private TextField textFieldName;
	private PasswordField textFieldPassword;
	private Text welcomeMessage, messageLogIn, messageSignUp, response1, response2;
	private Button btnLogIn, btnGoBack1, btnGoBack2;

	public static void main(String[] args) 
	{
		launch(args);
	}

	// Override the init() method
	@Override
	public void init()
	{
		System.out.println("Inside the init() method now");
	}

	@Override
	public void start(Stage primaryStage) 
	{

		// Assign window as the primary stage
		window = primaryStage;

		// Display Main Menu at first
		window.setTitle("Main Menu");

		// Create menu bar objects ready to add to Scenes
		MenuBar menu = menuItems(); // Main Menu
		MenuBar menu2 = menuItems(); // Login Menu
		MenuBar menu3 = menuItems(); // Sign Up Menu
		MenuBar menu4 = menuItems(); // Presentation Menu

		/********** Main Menu Screen **************/
		// Create a root node called menuLayout which
		// uses BorderPane
		BorderPane menuLayout = new BorderPane();
		mainMenu = new Scene(menuLayout, 900, 600);
		menuLayout.setId("menuLayout"); // rootNode id for Main Menu Scene in CSS

		// Load style.ccs from same directory to provide the styling for the scenes
		menuLayout.getStylesheets().add(MainGUI.class.getResource("style.css").toExternalForm());

		// Create the grid items
		GridPane text = addMainGridItems();

		// Add the menu and buttons to the root node
		menuLayout.setTop(menu);
		menuLayout.setCenter(text);

		// Set initial scene to main menu after creating
		// main menu gui parts
		window.setScene(mainMenu);

		/*******************************************/

		/************* Login Screen ****************/

		// Create a root node called loginLayout which
		// uses BorderPane
		BorderPane loginLayout = new BorderPane();
		loginLayout.setId("loginLayout"); // rootNode id for LogIn Scene in CSS
		// Create a scene
		logInMenu = new Scene(loginLayout, 900, 600);
		// Create grid pane object with text fields and buttons 

		// Load style.ccs from same directory to provide the styling for the scenes
		loginLayout.getStylesheets().add(MainGUI.class.getResource("style.css").toExternalForm());

		// ready to add to logInMenu scene
		GridPane text2 = addLoginGridItems();

		// Add the menu and textfields and buttons to the root node
		loginLayout.setTop(menu2);
		loginLayout.setCenter(text2);

		/****************************************/

		/********* Sign Up screen ***************/
		// Create a root node called loginLayout which
		// uses BorderPane
		BorderPane signupLayout = new BorderPane();
		signupLayout.setId("signupLayout"); // rootNode id for Sign Up Scene in CSS
		// Create a scene
		signUpMenu = new Scene(signupLayout, 900, 600);
		// Create grid pane object with text fields and buttons 

		// Load style.ccs from same directory to provide the styling for the scenes
		signupLayout.getStylesheets().add(MainGUI.class.getResource("style.css").toExternalForm());

		// ready to add to logInMenu scene
		GridPane text3 = addSignupGridItems();

		// Add the menu and textfields and buttons to the root node
		signupLayout.setTop(menu3);
		signupLayout.setCenter(text3);

		/****************************************/

		/********* Presentation screen **********/
		// Create a root node called loginLayout which
		// uses BorderPane
		BorderPane presentationLayout = new BorderPane();
		presentationLayout.setId("presentationLayout"); // rootNode id for presentation Scene in CSS
		// Create a scene
		presentationMenu = new Scene(presentationLayout, 900, 600);
		// Create grid pane object with text fields and buttons 

		// Load style.ccs from same directory to provide the styling for the scenes
		presentationLayout.getStylesheets().add(MainGUI.class.getResource("style.css").toExternalForm());

		// ready to add to logInMenu scene
		//GridPane controls4 = addPresentationGridItems();

		// Add the menu and textfields and buttons to the root node
		presentationLayout.setTop(menu4);
		//presentationLayout.setCenter(controls4);

		/****************************************/

		// Show all GUI Visibility to true
		window.show();
	}

	// Override the stop() method
	@Override
	public void stop()
	{
		System.out.println("Inside the stop() method now");
	}

	/* Method to add menu items and buttons for all GUIs */
	public MenuBar menuItems()
	{
		MenuBar menuBar = new MenuBar();
		final FileChooser browseFiles = new FileChooser(); // For browsing files
		browseFiles.setTitle("Open File");
		
		// File Menu \\
		Menu fileMenu = new Menu("File");
		MenuItem openFile = new MenuItem("Open...");

		// Browse A File
		openFile.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Please open a file...");
				File file = browseFiles.showOpenDialog(window);				
			}
		});

		fileMenu.getItems().add(new SeparatorMenuItem());
		MenuItem settings = new MenuItem("Settings...");
		
		fileMenu.getItems().add(new SeparatorMenuItem());
		MenuItem exit = new MenuItem("Exit...");
		
		// Close System
		exit.setOnAction(new EventHandler<ActionEvent>() 
		{
		    public void handle(ActionEvent t) 
		    {
		        System.exit(0);
		    }
		});
		
		// Add all File Menu Items to File Bar
		fileMenu.getItems().addAll(openFile, settings, new SeparatorMenuItem(), exit);		
		
		// Community Menu \\
		Menu communityMenu = new Menu("Community");
		
		// Help Menu \\
		Menu helpMenu = new Menu("Help");

		
		// Add All Menu Items to Main Menu Bar
		menuBar.getMenus().addAll(fileMenu, communityMenu, helpMenu);

		return menuBar;
	}

	/* Method for GridPane for Main Menu */
	public GridPane addMainGridItems()
	{
		/* Create a root node called grid. In this case a grid pane layout 
		 * is used, with vertical and horizontal gaps of 50
		 */
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(50);

		// Centre the controls in the scene
		grid.setAlignment(Pos.CENTER);

		// Add padding
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Create the Default message
		welcomeMessage = new Text("Try Our Interactive Language Learning Experience");
		welcomeMessage.setFill(Color.BLACK);
		welcomeMessage.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		grid.add(welcomeMessage, 0, 0, 2, 1);

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(35);
		//hbox.setStyle("-fx-background-color: #336699;");

		// Create Sign Up and Login buttons
		Button signUp = new Button("Sign Up");
		signUp.setPrefSize(150, 50);
		signUp.setId("signUp"); // id for CSS file
		//signUp.setStyle("-fx-font: 22 arial; -fx-base: #BAE98A;");

		Button logIn = new Button("Login");
		logIn.setPrefSize(150, 50);
		logIn.setId("logIn"); // id for CSS file
		//logIn.setStyle("-fx-font: 22 arial; -fx-base: #BAE98A;");

		Button presentation = new Button("Presentation");
		presentation.setPrefSize(150, 50);
		presentation.setId("presentation"); // id for CSS file

		// Adding buttons to the hbox
		hbox.getChildren().addAll(signUp, logIn, presentation);

		// Adding hbox with the buttons in it to the rootNode
		grid.add(hbox, 1, 1);

		// Event handler for SignUp Button
		signUp.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				window.setTitle("Sign Up");
				window.setScene(signUpMenu);
			}
		});

		// Event handler for Login Button
		logIn.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				window.setTitle("Login");
				window.setScene(logInMenu);
			}
		});

		// Event handler for presentation Button
		presentation.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				window.setTitle("Presentation Example");
				window.setScene(presentationMenu);
			}
		});

		return grid;
	}

	/* Method for GridPane items for login Menu */
	public GridPane addLoginGridItems()
	{
		/* Create a root node called grid. In this case a grid pane layout 
		 * is used, with vertical and horizontal gaps of 10
		 */
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		// Centre the controls in the scene
		grid.setAlignment(Pos.CENTER);

		// Add padding
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Create the Default message
		messageLogIn = new Text("Welcome Back");
		messageLogIn.setId("messageLogIn");
		//messageLogIn.setFill(Color.DARKCYAN);
		//messageLogIn.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		grid.add(messageLogIn, 0, 0, 2, 1);

		// Create the labels for username and password
		// and adding to the rootNode
		Label userName = new Label("Username: ");
		grid.add(userName, 0, 1);
		Label passWord = new Label("Password: ");
		grid.add(passWord, 0, 2);

		// Create the textfields for username and password
		// and adding to the rootNode
		textFieldName = new TextField();
		grid.add(textFieldName, 1, 1);
		textFieldPassword = new PasswordField();
		grid.add(textFieldPassword, 1, 2);

		// Creating a Button for login and a response
		btnLogIn = new Button("Login");
		btnLogIn.setId("btnLogIn");
		btnGoBack1 = new Button("Go Back");
		btnGoBack1.setId("btnGoBack1");

		// Creating a HBox area to add the button to
		HBox hbArea = new HBox(10);
		hbArea.setAlignment(Pos.BOTTOM_RIGHT);
		hbArea.getChildren().addAll(btnLogIn, btnGoBack1);

		// Event handler for btnGoBack1
		btnGoBack1.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				window.setTitle("Main Menu");
				window.setScene(mainMenu);
			}
		});

		// Adding hbArea with the button in it to the rootNode
		grid.add(hbArea, 1, 4);

		// Add a response after pressing the button
		response1 = new Text();
		grid.add(response1, 1, 6);

		// Event handler to get text from the text field 
		// when button is pressed.
		btnLogIn.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				response1.setText("Logging in, please wait");
				//response1.setFill(Color.FUCHSIA);
				response1.setFill(Color.BLACK);
				textFieldName.getText();
				textFieldPassword.getText();
			}
		}); 

		return grid;
	}

	/* Method for GridPane items for Sign Up Menu */
	public GridPane addSignupGridItems(){
		/* Create a root node called grid. In this case a grid pane layout 
		 * is used, with vertical and horizontal gaps of 10
		 */
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		// Centre the controls in the scene
		grid.setAlignment(Pos.CENTER);

		// Add padding
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Create the Default message
		messageSignUp = new Text("An Exciting Experience!");
		messageSignUp.setId("messageSignUp");
		//messageSignUp.setFill(Color.DARKCYAN);
		//messageSignUp.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		grid.add(messageSignUp, 0, 0, 2, 1);

		// Create the labels for First Name, Surname Name, email,
		// username and password, etc..and adding to the rootNode
		Label firstName = new Label("First Name: ");
		grid.add(firstName, 0, 1);
		Label surName = new Label("Surname: ");
		grid.add(surName, 0, 2);
		Label email = new Label("Email: ");
		grid.add(email, 0, 3);
		Label confirmEmail = new Label("Confirm Email: ");
		grid.add(confirmEmail, 0, 4);
		Label userName = new Label("Username: ");
		grid.add(userName, 0, 5);
		Label passWord = new Label("Password: ");
		grid.add(passWord, 0, 6);
		Label confirmPassword = new Label("Confirm Password: ");
		grid.add(confirmPassword, 0, 7);

		// Create the textfields for First Name, Surname Name, email,
		// username and password, etc..and adding to the rootNode
		final TextField textFieldFirstName = new TextField();
		grid.add(textFieldFirstName, 1, 1);
		final TextField textFieldSurname = new TextField();
		grid.add(textFieldSurname, 1, 2);
		final TextField textFieldEmail = new TextField();
		grid.add(textFieldEmail, 1, 3);
		final TextField textFieldConfirmEmail = new TextField();
		grid.add(textFieldConfirmEmail, 1, 4);
		final TextField textFieldUsername = new TextField();
		grid.add(textFieldUsername, 1, 5);
		final PasswordField textFieldPassword = new PasswordField();
		grid.add(textFieldPassword, 1, 6);
		final PasswordField textFieldConfirmPassword = new PasswordField();
		grid.add(textFieldConfirmPassword, 1, 7);

		// Creating a Button for Registering and going back to main menu
		Button btnRegister = new Button("Register");
		btnRegister.setId("btnRegister");
		btnGoBack2 = new Button("Go Back");
		btnGoBack2.setId("btnGoBack2");

		// Creating a HBox area to add the buttons to
		HBox hbArea = new HBox(10);
		hbArea.setAlignment(Pos.BOTTOM_RIGHT);
		hbArea.getChildren().addAll(btnRegister, btnGoBack2);

		// Event handler for btnGoBack1
		btnGoBack2.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				window.setTitle("Main Menu");
				window.setScene(mainMenu);
			}
		});

		// Adding hbArea with the button in it to the rootNode
		grid.add(hbArea, 1, 8);

		// Add a response after pressing the button
		response2 = new Text();
		grid.add(response2, 1, 9);

		// Event handler to get text from the text field 
		// when button is pressed.
		btnRegister.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				response2.setText("Registering with us, please wait");
				response2.setFill(Color.BLACK);
				textFieldFirstName.getText();
				textFieldSurname.getText();
				textFieldEmail.getText();
				textFieldConfirmEmail.getText();
				textFieldUsername.getText();
				textFieldPassword.getText();
				textFieldConfirmPassword.getText();

			}
		}); 

		return grid;
	}


}
