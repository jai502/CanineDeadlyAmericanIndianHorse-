package gui;

import gui.MainGUI;

/*
 * (C) Stammtisch
 * First version created by: Mathew Gould & Alexander Stassis (Design Team)
 * Date of first version: 21/02/16
 * 
 * Last version by: Mathew Gould & Alexander Stassis (Design Team)
 * Date of last update:  29/02/16
 * Version number: 1
 * 
 * Commit date: 29/02/16
 * Description: Designing the Main GUI Class which will lead to other GUIs
 * This class currently is in implementation and the methods will be put into
 * another class?? 
 * 
 * NOTE: Comments still need to be improved!
 * 		 Menu Items need to be implemented more.
 */

import javax.imageio.ImageIO;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.util.Callback;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class MainGUI extends Application 
{	
	/* variables for the primary stage */
	private Stage window;
	private Scene mainMenu, logInMenu, signUpMenu, presentationMenu;

	/* variables for addMainGridItems() method */
	private Button signUp, logIn;
	private Text welcomeMessage;

	/* variables for addLoginGridItems() method */
	private Button btnLogIn, btnGoBack1;
	private Label userName1, passWord1;
	private TextField textFieldName;
	private PasswordField textFieldPassword1;
	private Text messageLogIn, response1;

	/* variables for addSignupGridItems() method */
	private Button btnRegister, btnGoBack2;
	private Label firstName, surName, email, confirmEmail, userName2, passWord2, confirmPassword; 
	private TextField textFieldFirstName, textFieldSurname, textFieldEmail, textFieldConfirmEmail, textFieldUsername;
	private PasswordField textFieldPassword2, textFieldConfirmPassword;
	private Text messageSignUp, response2;

	/* variables for menuItems() method */
	private MenuBar menuBar;
	private ImageView myImage = new ImageView();
	private File selectedFile;
	private FileChooser browseFiles = new FileChooser(); // For browsing files

	/* variables for openFile() method */
	private Desktop desktop = Desktop.getDesktop();

	/* variables for createPage() method */
	private Image[] images = createContent();

	/* variables for createContent() method */
	private ImageView image;
	private boolean x = false;

	// Constructor
	public MainGUI(){

	}

	public static void main(String[] args) 
	{
		launch(args);
	}

	// Override the init() method
	@Override
	public void init()
	{
		System.out.println("Setting up/initialising windows now");
	}

	@Override
	public void start(Stage primaryStage) 
	{
		// Assign window variable as the primary stage
		window = primaryStage;

		// Create menu bar objects ready to add to the Scenes
		MenuBar mainMenuBar = menuItems(); // Main Menu
		MenuBar loginMenuBar = menuItems(); // Login Menu
		MenuBar signupMenuBar = menuItems(); // Sign Up Menu
		MenuBar presentationMenuBar = menuItems(); // Presentation Menu

		/********** Main Menu Screen **************/
		// Create a root node called menuLayout which uses BorderPane
		BorderPane menuLayout = new BorderPane();
		menuLayout.setId("menuLayout"); // rootNode id for Main Menu Scene in CSS
		// Add the root node to the scene
		mainMenu = new Scene(menuLayout, 900, 600);

		// Load style.ccs from same directory to provide the styling for the scenes
		menuLayout.getStylesheets().add(MainGUI.class.getResource("style.css").toExternalForm());

		// Create the grid items
		GridPane controls1 = addMainGridItems();

		// Add the menu and buttons to the root node
		menuLayout.setTop(mainMenuBar);
		menuLayout.setCenter(controls1);

		// As Default, Display Main Menu at first
		window.setTitle("Main Menu");
		window.setScene(mainMenu);

		/*******************************************/

		/************* Login Screen ****************/

		// Create a root node called loginLayout which uses BorderPane
		BorderPane loginLayout = new BorderPane();
		loginLayout.setId("loginLayout"); // rootNode id for LogIn Scene in CSS
		// Add the root node to the scene
		logInMenu = new Scene(loginLayout, 900, 600);

		// Load style.ccs from same directory to provide the styling for the scenes
		loginLayout.getStylesheets().add(MainGUI.class.getResource("style.css").toExternalForm());

		// ready to add to logInMenu scene
		GridPane controls2 = addLoginGridItems();

		// Add the menu and textfields and buttons to the root node
		loginLayout.setTop(loginMenuBar);
		loginLayout.setCenter(controls2);

		/****************************************/

		/********* Sign Up screen ***************/
		// Create a root node called loginLayout which uses BorderPane
		BorderPane signupLayout = new BorderPane();
		signupLayout.setId("signupLayout"); // rootNode id for Sign Up Scene in CSS
		// Add the root node to the scene
		signUpMenu = new Scene(signupLayout, 900, 600);

		// Load style.ccs from same directory to provide the styling for the scenes
		signupLayout.getStylesheets().add(MainGUI.class.getResource("style.css").toExternalForm());

		// ready to add to logInMenu scene
		GridPane controls3 = addSignupGridItems();

		// Add the menu, textfields and buttons to the root node
		signupLayout.setTop(signupMenuBar);
		signupLayout.setCenter(controls3);

		/****************************************/

		/********* Presentation screen **********/
		// Create a root node called loginLayout which uses BorderPane
		BorderPane presentationLayout = new BorderPane();
		presentationLayout.setId("presentationLayout"); // rootNode id for presentation Scene in CSS
		// Add the root node to the scene
		presentationMenu = new Scene(presentationLayout, 900, 600);

		// Load style.ccs from same directory to provide the styling for the scenes
		presentationLayout.getStylesheets().add(MainGUI.class.getResource("style.css").toExternalForm());

		// Auto adjust file to be displayed to the window size
		//myImage.fitWidthProperty().bind(window.widthProperty()); 
		//myImage.fitHeightProperty().bind(window.heightProperty()); 

		/* Pagination part */

		final Pagination pagination;
		final int numOfPage = images.length;
		// Create pagination with desired number of pages
		pagination = new Pagination(numOfPage);

		// Set style of page controls
		pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);

		// Style pagination area
		//pagination.getStylesheets().add(Main.class.getResource("pagination.css").toExternalForm());


		// Create pagefactory controls
		pagination.setPageFactory(new Callback<Integer, Node>() {
			@Override
			public Node call(Integer pageIndex) {
				return createPage(pageIndex);
			}
		});

		// FullScreen
		presentationLayout.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent ke)
			{
				if (ke.getCode().equals(KeyCode.F))
				{
					x = true;
					window.setFullScreen(true);
				}
				else if (ke.getCode().equals(KeyCode.ESCAPE))
				{	
					x = false;
				}
			}
		});

		// Change slide with mouse events
		presentationLayout.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.isPrimaryButtonDown()) {
					pagination.setCurrentPageIndex(pagination.getCurrentPageIndex()+1);
				}
				if (mouseEvent.isSecondaryButtonDown()) {
					pagination.setCurrentPageIndex(pagination.getCurrentPageIndex()-1);
				}
			}
		});

		//	BorderPane controls4 = paginationItems();

		// Add the menu and file to the root node
		presentationLayout.setTop(presentationMenuBar);
		//presentationLayout.setCenter(myImage);
		presentationLayout.setCenter(pagination);
		//presentationLayout.setCenter(controls4);

		/****************************************/

		// Show all GUI Visibility to true
		window.show();
	}

	// Override the stop() method
	@Override
	public void stop()
	{
		System.out.println("Closing Windows Now!");
	}

	/* Method to add menu items and buttons for all GUIs */
	public MenuBar menuItems()
	{
		menuBar = new MenuBar();

		// Set the title of the dialogue window
		// browseFiles.setTitle("Open File");

		// File Menu \\
		Menu fileMenu = new Menu("File");
		MenuItem openFile = new MenuItem("Open...");

		// Event handler for Browsing A File
		openFile.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Please open a file...");

				// Set extension filters
				FileChooser.ExtensionFilter extFilterJPG = 
						new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
				FileChooser.ExtensionFilter extFilterjpg = 
						new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
				FileChooser.ExtensionFilter extFilterPNG = 
						new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
				FileChooser.ExtensionFilter extFilterpng = 
						new FileChooser.ExtensionFilter("png files (*.png)", "*.png");

				// Add extension files to the file chooser
				browseFiles.getExtensionFilters().addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);

				// Assign a File object as the file chooser
				selectedFile = browseFiles.showOpenDialog(window);

				// Select a file and add it to myImage
				if(selectedFile != null)
				{	
					window.setTitle("Presentation");
					window.setScene(presentationMenu); // Change scene to presentationMenu
					String filename = selectedFile.getName(); // get the name of selected file
					System.out.println("File selected: " + filename); // display the details
					//openFile(selectedFile);
					try {
						BufferedImage bufferedImage = ImageIO.read(selectedFile);
						WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
						myImage.setImage(image);
					} catch (IOException ex) {
						Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
				else
				{
					System.out.println("File selection cancelled!");
				}
			}
		});

		//fileMenu.getItems().add(new SeparatorMenuItem());
		MenuItem settings = new MenuItem("Settings...");

		MenuItem goToPresentation = new MenuItem("Go To Presentation...");

		// Go back to main menu
		goToPresentation.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent e) 
			{
				window.setTitle("Presentation");
				window.setScene(presentationMenu);
			}

		});

		MenuItem goBack = new MenuItem("Go Back...");

		// Go back to main menu
		goBack.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent e) 
			{
				window.setTitle("Main Menu");
				window.setScene(mainMenu);
			}

		});

		//fileMenu.getItems().add(new SeparatorMenuItem());
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
		fileMenu.getItems().addAll(openFile, settings, goToPresentation, goBack, new SeparatorMenuItem(), exit);		

		// Community Menu \\
		Menu communityMenu = new Menu("Community");

		// Help Menu \\
		Menu helpMenu = new Menu("Help");

		// Add All Menu Bar Items to the actual Menu Bar
		menuBar.getMenus().addAll(fileMenu, communityMenu, helpMenu);

		return menuBar;
	}


	/* Method to create testing page for pagination in presentation viewer */
	/*	public VBox createPage(int pageIndex) 
	{
		VBox pageBox = new VBox();
		Label pageLabel = new Label("HERE WILL BE SLIDE " + (pageIndex+1));
		pageBox.getChildren().add(myImage);
		return pageBox;
	}*/

	/* Method for opening a file in the browser with normal OS procedure
	private void openFile(File file) {
		try {
			desktop.open(file);
		} catch (IOException ex) {
			Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	 */


	// Method to create testing page for pagination in presentation viewer
	public AnchorPane createPage(int pageIndex) {

		//Image[] images = createContent();

		AnchorPane pageBox;

		pageBox = new AnchorPane();
		//Label pageLabel = new Label("HERE WILL BE SLIDE " + (pageIndex+1));
		//pageBox.getChildren().add(pageLabel);

		image = new ImageView(images[pageIndex]);

		if(x == true){

			image.fitWidthProperty().bind(window.widthProperty());
			image.fitHeightProperty().bind(window.heightProperty());
		}
		if(x == false){

			image.fitWidthProperty().bind(pageBox.widthProperty());
			image.fitHeightProperty().bind(pageBox.heightProperty());
		}

		pageBox.getChildren().add(image);

		return pageBox;
	}

	// method to create pagination content
	public Image[] createContent(){
		Image[] images = new Image[3];

		//Images for our pages
		for (int i = 0; i < 3; i++) {
			images[i] = new Image(MainGUI.class.getResource("animal" + (i + 1) + ".jpg").toExternalForm(), false);
		}
		return images;
	}
	/* Method for GridPane for Main Menu */
	public GridPane addMainGridItems()
	{
		// Create a root node called grid. In this case a grid pane layout 
		// is used, with vertical and horizontal gaps of 50 
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

		// create an HBox for the buttons
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(35);
		//hbox.setStyle("-fx-background-color: #336699;");

		// Create Sign Up and Login buttons
		signUp = new Button("Sign Up");
		signUp.setPrefSize(150, 50);
		signUp.setId("signUp"); // id for CSS file
		//signUp.setStyle("-fx-font: 22 arial; -fx-base: #BAE98A;");

		logIn = new Button("Login");
		logIn.setPrefSize(150, 50);
		logIn.setId("logIn"); // id for CSS file
		//logIn.setStyle("-fx-font: 22 arial; -fx-base: #BAE98A;");

		/*
		Button presentation = new Button("Presentation");
		presentation.setPrefSize(150, 50);
		presentation.setId("presentation"); // id for CSS file */

		// Adding buttons to the hbox
		hbox.getChildren().addAll(signUp, logIn);
		//hbox.getChildren().addAll(signUp, logIn, presentation);

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
		/*	presentation.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				window.setTitle("Presentation Example");
				window.setScene(presentationMenu);
			}
		}); */

		return grid;
	} 

	/* Method for GridPane items for login Menu */
	public GridPane addLoginGridItems()
	{
		// Create a root node called grid. In this case a grid pane layout 
		// is used, with vertical and horizontal gaps of 10
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
		userName1 = new Label("Username: ");
		grid.add(userName1, 0, 1);
		passWord1 = new Label("Password: ");
		grid.add(passWord1, 0, 2);

		// Create the textfields for username and password
		// and adding to the rootNode
		textFieldName = new TextField();
		grid.add(textFieldName, 1, 1);
		textFieldPassword1 = new PasswordField();
		grid.add(textFieldPassword1, 1, 2);

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
				textFieldPassword1.getText();
			}
		}); 

		return grid;
	}

	/* Method for GridPane items for Sign Up Menu */
	public GridPane addSignupGridItems(){
		// Create a root node called grid. In this case a grid pane layout 
		// is used, with vertical and horizontal gaps of 10
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
		firstName = new Label("First Name: ");
		grid.add(firstName, 0, 1);
		surName = new Label("Surname: ");
		grid.add(surName, 0, 2);
		email = new Label("Email: ");
		grid.add(email, 0, 3);
		confirmEmail = new Label("Confirm Email: ");
		grid.add(confirmEmail, 0, 4);
		userName2 = new Label("Username: ");
		grid.add(userName2, 0, 5);
		passWord2 = new Label("Password: ");
		grid.add(passWord2, 0, 6);
		confirmPassword = new Label("Confirm Password: ");
		grid.add(confirmPassword, 0, 7);

		// Create the textfields for First Name, Surname Name, email,
		// username and password, etc..and adding to the rootNode
		textFieldFirstName = new TextField();
		grid.add(textFieldFirstName, 1, 1);
		textFieldSurname = new TextField();
		grid.add(textFieldSurname, 1, 2);
		textFieldEmail = new TextField();
		grid.add(textFieldEmail, 1, 3);
		textFieldConfirmEmail = new TextField();
		grid.add(textFieldConfirmEmail, 1, 4);
		textFieldUsername = new TextField();
		grid.add(textFieldUsername, 1, 5);
		textFieldPassword2 = new PasswordField();
		grid.add(textFieldPassword2, 1, 6);
		textFieldConfirmPassword = new PasswordField();
		grid.add(textFieldConfirmPassword, 1, 7);

		// Creating a Button for Registering and going back to main menu
		btnRegister = new Button("Register");
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
				textFieldPassword2.getText();
				textFieldConfirmPassword.getText();

			}
		}); 

		return grid;
	}

}
