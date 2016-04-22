package gui;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/*
 * (C) Stammtisch
 * First version created by: Mathew Gould & Alexander Stassis (Design Team)
 * Date of first version: 21/02/16
 * 
 * Last version by: Mathew Gould & Alexander Stassis (Design Team)
 * Date of last update:  25/03/16
 * Version number: 1
 * 
 * Commit date: 28/03/16
 * Description: Designing the Main GUI Class which will lead to other GUIs
 * This class currently is still in implementation
 * NOTE: Browsing works for specific xml file! Now need to rearrange gui to continue 
 * 		 from login page and sign up page 
 */

import javax.imageio.ImageIO;
import Objects.Presentation;
import Parsers.XMLParser;
import encryptionRSA.RSAEncryptDecrypt;
import encryptionRSA.Serializer;
import handlers.SlideHandler;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.*;
//import javafx.beans.InvalidationListener;
//import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.util.Callback;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

public class MainGuiPagination extends Application 
{	
	/* variables for the primary stage */
	private Stage window;
	private Scene mainMenu, logInMenu, signUpMenu, presentationMenu;
	private int width = 900;
	private int height = 600;

	/* variables for addMainGridItems() method */
	private Button signUp, logIn;
	private Text welcomeMessage;

	/* variables for addLoginGridItems() method */
	private Button btnLogIn, btnGoBack1;
	private Label userName1, passWord1;
	private TextField textFieldName;
	private PasswordField textFieldPassword1;
	private Text messageLogIn, response1;
	private String sUsernameLogin, sPasswordLogin;
	//private ArrayList<String> inputData1 = new ArrayList<String>();

	/* variables for addSignupGridItems() method */
	private Button btnRegister, btnGoBack2;
	private Label firstName, surName, dateOfBirth, email, confirmEmail, userName2, passWord2, confirmPassword; 
	private TextField textFieldFirstName, textFieldSurname, textFieldDateOfBirth, textFieldEmail, textFieldConfirmEmail, textFieldUsername;
	private PasswordField textFieldPassword2, textFieldConfirmPassword;
	private Text messageSignUp, response2;
	private String sFirstName, sSurname, sDateOfBirth, sEmail, sConfirmEmail, sUsername, sPassword, sConfirmPassword;
	//private ArrayList<String> inputData2 = new ArrayList<String>();

	/* variables for presentation scene */
	private SlideHandler sh = new SlideHandler();
	private String filename1, filename2, filename3, xmlPathname, parsingFileName;
	private Presentation tempPres = new Presentation();
	private XMLParser parser;
	private HBox buttonControls;
	private BorderPane presentationLayout;
	private Pagination pagination;

	/* variables for menuItems() method */
	private MenuBar menuBar;
	private ImageView myImage = new ImageView();
	private File selectedFile;
	private FileChooser browseFiles = new FileChooser(); // For browsing files

	/* variables for openFile() method */
	private Desktop desktop = Desktop.getDesktop();

	/* variables for createPage() method */
	//	private AnchorPane pageBox;
	//	private Canvas canvas;
	//	private Canvas canvasTest;
	//	private GraphicsContext gc;
	//	private Image[] images = createContent();

	/* variables for createContent() method */
	//	private ImageView image;
	//	private boolean x = false;

	// Constructor
	public MainGuiPagination(){

	}

	public static void main(String[] args) 
	{
		// Required for JavaFX to run
		launch(args);
	}

	// Override the init() method
	@Override
	public void init()
	{
		System.out.println("Setting up/initialising GUI now");
	}

	// Required method to run the JavaFX code
	@Override
	public void start(Stage primaryStage) throws IOException 
	{
		initialiseGUI(primaryStage);
	}

	// Override the stop() method
	@Override
	public void stop()
	{
		System.out.println("Stopping/Closing GUI Now!");
	}

	/* Method which initialises and creates all the GUI */ 
	private boolean initialiseGUI(Stage stage) throws IOException {

		// Assign window variable as the primary stage
		window = stage;

		// Create menu bar objects ready to add to the Scenes
		MenuBar mainMenuBar = menuItems(); // Main Menu
		MenuBar loginMenuBar = menuItems(); // Login Menu
		MenuBar signupMenuBar = menuItems(); // Sign Up Menu
		MenuBar presentationMenuBar = menuItems(); // Presentation Menu

		/******************** Main Menu Screen ************************/
		// Create a root node called menuLayout which uses BorderPane
		BorderPane menuLayout = new BorderPane();
		menuLayout.setId("menuLayout"); // rootNode id for Main Menu Scene in gui_style.gui_style.css
		// Add the root node to the scene
		mainMenu = new Scene(menuLayout, width, height);

		// Load style.ccs from same directory to provide the styling for the scenes
		menuLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// Create the grid items
		GridPane controls1 = addMainGridItems();

		// Add the menu and buttons to the root node
		menuLayout.setTop(mainMenuBar);
		menuLayout.setCenter(controls1);

		// As Default, Display Main Menu at first
		window.setTitle("Main Menu");
		window.setScene(mainMenu);

		/**************************************************************/

		/*********************** Login Screen *************************/

		// Create a root node called loginLayout which uses BorderPane
		BorderPane loginLayout = new BorderPane();
		loginLayout.setId("loginLayout"); // rootNode id for LogIn Scene in gui_style.css
		// Add the root node to the scene
		logInMenu = new Scene(loginLayout, width, height);

		// Load gui_style.ccs from same directory to provide the styling for the scenes
		loginLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// ready to add to logInMenu scene
		GridPane controls2 = addLoginGridItems();

		// Add the menu and textfields and buttons to the root node
		loginLayout.setTop(loginMenuBar);
		loginLayout.setCenter(controls2);

		/*************************************************************/

		/******************** Sign Up screen *************************/
		// Create a root node called loginLayout which uses BorderPane
		BorderPane signupLayout = new BorderPane();
		signupLayout.setId("signupLayout"); // rootNode id for Sign Up Scene in gui_style.css
		// Add the root node to the scene
		signUpMenu = new Scene(signupLayout, width, height);

		// Load gui_style.ccs from same directory to provide the styling for the scenes
		signupLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// ready to add to logInMenu scene
		GridPane controls3 = addSignupGridItems();

		// Add the menu, textfields and buttons to the root node
		signupLayout.setTop(signupMenuBar);
		signupLayout.setCenter(controls3);

		/**************************************************************/

		/******************* Presentation screen **********************/
		// Create a root node called loginLayout which uses BorderPane
		presentationLayout = new BorderPane();
		presentationLayout.setId("presentationLayout"); // rootNode id for Presentation Scene in gui_style.css
		// Add the root node to the scene
		presentationMenu = new Scene(presentationLayout, width, height);

		// Load style.ccs from same directory to provide the styling for the scenes
		presentationLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// Create extra presentation controls
		buttonControls = controls();

		// create a temporary presentation
		//tempPres = new Presentation();

		// Add menu bar to presentation screen
		presentationLayout.setTop(presentationMenuBar);
		BorderPane.setAlignment(buttonControls, Pos.CENTER);
		presentationLayout.setBottom(buttonControls);

		/* FullScreen for presentation menu */
		presentationMenu.setOnKeyPressed(new EventHandler<KeyEvent>()
		{
			@Override
			public void handle(KeyEvent ke)
			{
				if (ke.getCode().equals(KeyCode.F))
				{
					//x = true;
					window.setFullScreen(true);
				}
				else if (ke.getCode().equals(KeyCode.ESCAPE))
				{	
					//x = false;
					window.setFullScreen(false);
				}
			}
		});

		/*************************************************/

		// Set all GUI Visibility to true
		window.show();

		return true;
	}

	/* Method which will add the panes (each slide) to the presentation screen */
	private StackPane presentationCanvas() throws IOException{

		Image img = new Image(getClass().getResource("animal1.jpg").openStream());
		Canvas canvas = new Canvas(presentationLayout.getWidth(), presentationLayout.getHeight());
		GraphicsContext gc = canvas.getGraphicsContext2D();
		// Draw the image to the canvas
		gc.drawImage(img, 0, 0, presentationLayout.getWidth(), presentationLayout.getHeight());

		/**** Binding and Resize attributes to the canvas ****/
		canvas.widthProperty().bind(presentationLayout.widthProperty());
		canvas.heightProperty().bind(presentationLayout.heightProperty());

		final ResizeChangeListener resizeChangeListener = new ResizeChangeListener(presentationLayout, gc, img);

		canvas.widthProperty().addListener(resizeChangeListener);
		canvas.heightProperty().addListener(resizeChangeListener);

		/******************************************************/

		/* Mouse event handler for the canvas */
		canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

			// Add mouse event handler to the images part of the pagination
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.isPrimaryButtonDown()) {
					pagination.setCurrentPageIndex(pagination.getCurrentPageIndex()+1);
				}
				if (mouseEvent.isSecondaryButtonDown()) {
					pagination.setCurrentPageIndex(pagination.getCurrentPageIndex()-1);
				}

				mouseEvent.consume();
			}
		});

		// Make a new root node
		StackPane pane = new StackPane();
		// Add the canvas to the root node
		pane.getChildren().add(canvas);

		return pane;
	}

	/* Method which will create extra controls for the presentation screen */
	private HBox controls(){
		HBox controls = new HBox(8); // Spacing of 8

		Button next = new Button("Next");
		next.setPrefSize(100, 50);
		next.setId("Next"); // String ID for gui_style.css

		Button previous = new Button("Previous");
		previous.setPrefSize(100, 50);
		previous.setId("Previous"); // String ID for gui_style.css

		// Add the buttons to the HBox
		controls.getChildren().addAll(next, previous);

		// Event handler for the Next Button
		next.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				// Increase PageIndex by 1
				pagination.setCurrentPageIndex(pagination.getCurrentPageIndex()+1);
			}
		});

		// Event handler for the Previous Button
		previous.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				// Decrease PageIndex by 1
				pagination.setCurrentPageIndex(pagination.getCurrentPageIndex()-1);
			}
		});

		return controls;
	}

	/* Method to add menu items and buttons for all GUIs */
	public MenuBar menuItems()
	{
		// Instantiate the menu bar
		menuBar = new MenuBar();

		// File Menu \\
		Menu fileMenu = new Menu("File");
		MenuItem openFile = new MenuItem("Open...");

		// Event handler for Browsing A File
		openFile.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Please select a file to open...");

				// Set extension filters
				FileChooser.ExtensionFilter extFilterXML = new FileChooser.ExtensionFilter("PWS files (*.XML)", "*.XML");
				FileChooser.ExtensionFilter extFilterxml = new FileChooser.ExtensionFilter("pws files (*.xml)", "*.xml");

				// Add extension files to the file chooser
				browseFiles.getExtensionFilters().addAll(extFilterxml, extFilterXML);

				// Assign a File object as the file chooser - open the system dialogue
				selectedFile = browseFiles.showOpenDialog(window);

				// Open the PWS selected xml file and change the scene to presentation scene
				// with a pagination layout
				openSelectedFile(selectedFile);
			}
		});

		//fileMenu.getItems().add(new SeparatorMenuItem());
		MenuItem settings = new MenuItem("Settings...");

		MenuItem goToPresentation = new MenuItem("Go To Presentation...");

		/*	// Go to presentation
		goToPresentation.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent e) 
			{
				// Parsing of the pws xml file
				parser = new XMLParser();
				//parser.parseXML("PWS/pwsTest.xml");
				parser.parseXML(newFileName);
				tempPres = parser.getPresentation();

				// Creates Pagination Layout
				pagination = new Pagination(tempPres.getSlides().size(), 0);
				pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);

				// Create the pagination page
				pagination.setPageFactory(new Callback<Integer, Node>() {
					@Override
					public Node call(Integer pageIndex) {
						try {
							return sh.getSlideStack(tempPres, pageIndex, width-200, height-150);
						} catch (IOException e) {
							return null;
						}
					}});

				presentationLayout.setCenter(pagination);
				window.setTitle("Presentation");
				window.setScene(presentationMenu);
			}

		});
		 */

		MenuItem goBack = new MenuItem("Go Back...");

		// Go back to main menu
		goBack.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent e) 
			{
				tempPres = null;
				System.out.println("Presentation screen is now cleared");
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

	// Method for opening a file in the browser with normal OS procedure
	/*	private void openFile(File file) {
		try {
			desktop.open(file);
		} catch (IOException ex) {
			Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	 */

	/* Method for selecting a PWS xml file and if not null, return the string name of the 
	   xml file and pass it into the parser  */
	private File openSelectedFile(File xmlFile){		

		if(xmlFile != null)
		{	
			window.setTitle("Presentation");
			// Change scene to presentationMenu
			window.setScene(presentationMenu);
			
			//filename1 = new String("PWS/");
			
			//filename1 = xmlFile.getParent(); // get the directory
			//filename2 = new String("/"); 
			//filename3 = xmlFile.getName(); // get the filename
			xmlPathname = xmlFile.getAbsolutePath();
			//parsingFileName = filename1 + filename2 + filename3; // concatenate full path
			parsingFileName = xmlPathname;
			// display the details
			System.out.println("File selected: " + parsingFileName); 

			// Parse the pws xml file
			parser = new XMLParser();
			//parser.parseXML("PWS/pwsTest.xml");
			parser.parseXML(parsingFileName);
			tempPres = parser.getPresentation();

			// Creates Pagination Layout 
			pagination = new Pagination(tempPres.getSlides().size(), 0);
			// Setting the style of the pagination
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);

			// Create the pagination pages
			pagination.setPageFactory(new Callback<Integer, Node>() {
				@Override
				public Node call(Integer pageIndex) {
					try {
						return sh.getSlideStack(tempPres, pageIndex, width-200, height-150);
					} catch (IOException e) {
						return null;
					}
				}});

			// Add the pagination to the presentation scene
			presentationLayout.setCenter(pagination);
			window.setTitle("Presentation");
			window.setScene(presentationMenu);

		}
		else
		{
			System.out.println("File selection cancelled!");
		}

		return xmlFile;
	}


	/* Inner Class for allowing the Resizing of Canvas objects */
	private static class ResizeChangeListener implements ChangeListener<Number> {

		private final Pane parent;
		private final GraphicsContext context;
		private final Image img;

		public ResizeChangeListener(Pane parent, GraphicsContext context, Image image) {
			this.parent = parent;
			this.context = context;
			this.img = image;
		}

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			final double width = parent.getWidth();
			final double height = parent.getHeight();
			context.clearRect(0, 0, width, height);
			context.drawImage(img, 0, 0, width, height);
		}
	}

	/* method to create pagination content of Image array */
	public Image[] createContent()
	{
		Image[] images = new Image[3];

		//Images for our pages
		for (int i = 0; i < 3; i++) {
			images[i] = new Image(MainGuiPagination.class.getResource("animal" + (i + 1) + ".jpg").toExternalForm(), false);
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
		signUp.setId("signUp"); // id for gui_style.css file
		//signUp.setStyle("-fx-font: 22 arial; -fx-base: #BAE98A;");

		logIn = new Button("Login");
		logIn.setPrefSize(150, 50);
		logIn.setId("logIn"); // id for gui_style.css file
		//logIn.setStyle("-fx-font: 22 arial; -fx-base: #BAE98A;");

		/*
		Button presentation = new Button("Presentation");
		presentation.setPrefSize(150, 50);
		presentation.setId("presentation"); // id for gui_style.css file */

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
		textFieldName.setPromptText("Enter Username");
		grid.add(textFieldName, 1, 1);
		textFieldPassword1 = new PasswordField();
		textFieldPassword1.setPromptText("Enter password");
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
				sUsernameLogin = textFieldName.getText();
				sPasswordLogin = textFieldPassword1.getText();

				// Create new LoginDetails class
				LoginDetails loginDetails = new LoginDetails();
				
				// Check if any of the textfields are null
				if(sUsernameLogin.equals("") || sPasswordLogin.equals(""))
				{	
					response1.setText("Textfields are empty!");
					response1.setFill(Color.RED);
				}	
				// Store the data and send to mysql database to check validity
				else 
				{
					// Set the username and password fields in local LoginDetails class
					loginDetails.setUsername(sUsernameLogin);
					loginDetails.setPassword(sPasswordLogin);

//					String x = (String) loginDetails.getUsername();
//					String y = (String) loginDetails.getPassword();
//					
//					System.out.println("Username is: " + x);
//					System.out.println("Password is: " + y);
					
					//System.out.println(inputData.get(0) + ", " + inputData.get(1));
					response1.setText("Logging in, please wait");
					response1.setFill(Color.BLACK);
				}								
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
		messageSignUp.setId("messageSignUp"); // Id for gui_style.css
		//messageSignUp.setFill(Color.DARKCYAN);
		//messageSignUp.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		grid.add(messageSignUp, 0, 0, 2, 1);

		// Create the labels for First Name, Surname Name, email,
		// username and password, etc..and adding to the rootNode
		firstName = new Label("First Name: ");
		grid.add(firstName, 0, 1);
		surName = new Label("Surname: ");
		grid.add(surName, 0, 2);
		dateOfBirth = new Label("Date of Birth: ");
		grid.add(dateOfBirth, 0, 3);
		email = new Label("Email: ");
		grid.add(email, 0, 4);
		confirmEmail = new Label("Confirm Email: ");
		grid.add(confirmEmail, 0, 5);
		userName2 = new Label("Username: ");
		grid.add(userName2, 0, 6);
		passWord2 = new Label("Password: ");
		grid.add(passWord2, 0, 7);
		confirmPassword = new Label("Confirm Password: ");
		grid.add(confirmPassword, 0, 8);

		// Create the textfields for First Name, Surname Name, email,
		// username and password, etc..and adding to the rootNode
		textFieldFirstName = new TextField();
		textFieldFirstName.setPromptText("Enter First Name");
		grid.add(textFieldFirstName, 1, 1);
		textFieldSurname = new TextField();
		textFieldSurname.setPromptText("Enter Surame");
		grid.add(textFieldSurname, 1, 2);
		textFieldDateOfBirth = new TextField();
		textFieldDateOfBirth.setPromptText("Enter Date of Birth");
		grid.add(textFieldDateOfBirth, 1, 3);
		textFieldEmail = new TextField();
		textFieldEmail.setPromptText("Enter valid Email address");
		grid.add(textFieldEmail, 1, 4);
		textFieldConfirmEmail = new TextField();
		textFieldConfirmEmail.setPromptText("Confirm Email address");
		grid.add(textFieldConfirmEmail, 1, 5);
		textFieldUsername = new TextField();
		textFieldUsername.setPromptText("Enter Username");
		grid.add(textFieldUsername, 1, 6);
		textFieldPassword2 = new PasswordField();
		textFieldPassword2.setPromptText("Enter password of your choice");
		grid.add(textFieldPassword2, 1, 7);
		textFieldConfirmPassword = new PasswordField();
		textFieldConfirmPassword.setPromptText("Confirm password");
		grid.add(textFieldConfirmPassword, 1, 8);

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
		grid.add(hbArea, 1, 9);

		// Add a response after pressing the button
		response2 = new Text();
		grid.add(response2, 1, 10);

		// Event handler to get text from the text field 
		// when button is pressed.
		btnRegister.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				sFirstName = textFieldFirstName.getText();
				sSurname = textFieldSurname.getText();
				sDateOfBirth = textFieldDateOfBirth.getText();
				sEmail = textFieldEmail.getText();
				sConfirmEmail = textFieldConfirmEmail.getText();
				sUsername = textFieldUsername.getText();
				sPassword = textFieldPassword2.getText();
				sConfirmPassword = textFieldConfirmPassword.getText();
				
				SignupDetails signupDetails = new SignupDetails();
				RSAEncryptDecrypt rsaEncryptDecrypt = new RSAEncryptDecrypt();
				Serializer serializer = new Serializer();
				byte[] serializedSignupDetails = null;
				byte[] encryptedData = null;
				
				// Check if any of the textfields are null
				if(sFirstName.equals("") || sSurname.equals("") || sDateOfBirth.equals("") || sEmail.equals("") || sConfirmEmail.equals("") 
						|| sUsername.equals("") || sPassword.equals("") || sConfirmPassword.equals(""))
				{	
					response2.setText("Textfields are empty!");
					response2.setFill(Color.RED);
				}	
				// Check if input is valid and store the data to send to mysql
				else if(sEmail.equals(sConfirmEmail) && sPassword.equals(sConfirmPassword))
				{
					// Create key generation for encryption and decryption
					try {
						rsaEncryptDecrypt.createKeys();
					} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					// Set the username and password fields in local SignUpDetails class
					signupDetails.setFirstName(sFirstName);
					signupDetails.setSurname(sSurname);	
					signupDetails.setDateOfBirth(sDateOfBirth);
					signupDetails.setEmail(sEmail);
					signupDetails.setConfirmEmail(sConfirmEmail);
					signupDetails.setUsername(sUsername);
					signupDetails.setPassword(sPassword);
					signupDetails.setConfirmPassword(sConfirmPassword);
					
					// Before Encryption
					//String a = (String) signupDetails.getFirstName();
					System.out.println("Test Object is: " + signupDetails);
					
					// Serialize signUpDetails
					try {
						serializedSignupDetails = Serializer.serialize(signupDetails);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					System.out.println("Serialized test object: " + serializedSignupDetails);
					
					try {
						encryptedData = RSAEncryptDecrypt.rsaEncrypt(serializedSignupDetails);
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
							| IllegalBlockSizeException | BadPaddingException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					// After Encryption
					//String b = (String) signupDetails.getFirstName();
					System.out.println("Encrypted and Serialized Data is: " + encryptedData);
					
					System.out.println("Now for the Decrypting!");
					
//					try {
//						encryptedData = RSAEncryptDecrypt.rsaEncrypt(serializedSignupDetails);
//					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
//							| IllegalBlockSizeException | BadPaddingException | IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
					
//					String a = (String) signupDetails.getFirstName();
//					String b = (String) signupDetails.getSurname();
//					String c = (String) signupDetails.getEmail();
//					String d = (String) signupDetails.getConfirmEmail();
//					String ee = (String) signupDetails.getUsername();
//					String f = (String) signupDetails.getPassword();
//					String g = (String) signupDetails.getConfirmPassword();
//					
//					System.out.println("Firstname is: " + a);
//					System.out.println("Surname is: " + b);
//					System.out.println("Email is: " + c);
//					System.out.println("ConfirmEmail is: " + d);
//					System.out.println("Username is: " + ee);
//					System.out.println("Password is: " + f);
//					System.out.println("ConfirmPassword is: " + g);
					
					
					
					//System.out.println(inputData.get(0) + ", " + inputData.get(1));
					response2.setText("Registering with us, please wait");
					response2.setFill(Color.BLACK);
				}
				// Some input did not match, clear textfelds and try again
				else
				{
					response2.setText("Error in input, please try again!");
					response2.setFill(Color.RED);
					textFieldEmail.clear();
					textFieldConfirmEmail.clear();
					textFieldPassword2.clear();
					textFieldConfirmPassword.clear();
				}				
			}
		}); 

		return grid;
	}

}

