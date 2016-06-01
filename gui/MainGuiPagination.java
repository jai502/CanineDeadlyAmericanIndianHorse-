
/**
 * (C) Stammtisch
 * First version created by: Mathew Gould (mg859) & Alexander Stassis (as1574) (Design Team)
 * Date of first version: 21/02/16
 * 
 * Last version by: Alexander Stassis (as1574), Mathew Gould (mg859) and Callum Silver (cds515)
 * Date of last update:  31/05/16
 * Version number: 10
 * 
 * Commit date: 01/06/16
 * 
 * Description: This class creates the GUI of the application.
 * It consists of a main menu screen, a sign up and log in screen, 
 * a user homepage where the user can search for presentations and load locally browsed
 * presentations or downloaded presentations. Presentations are viewed in 
 * the presentation scene. The user can create his/her own presentations saved as xml files
 * in a 'Temp' folder in the project directory which will also contain the relative media file.
 * A comments and ratings screen is also available to the users if they wish to view 
 * or make comments and ratings. A Menu Bar is used to provide user navigation between 
 * the screens, as well as easy to find buttons found in the GUI.
 */

package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/****** Objects Package Imports ******/
import Objects.AudioItem;
import Objects.Presentation;
import Objects.SlideItem;
import Objects.TextItem;
import Objects.VideoItem;
/************************************/

/****** Parsers Package Imports *****/
import Parsers.FillPres;
import Parsers.XMLCreator;
import Parsers.XMLParser;
/************************************/

/****** Client Package Import ******/
import client.ServerRequestHandler;
/**********************************/

/****** Handlers Package Imports ******/
import handlers.MediaFx;
import handlers.SlideHandler;
/*************************************/

/******** Com Package Imports ********/
import com.User;
import com.PresentationShell;
import zipping.Zipper;
/*************************************/

/************ JavaFX Imports *********************/
import javafx.application.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.util.Callback;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/*********************************************/

/* The MainGuiPagination Class produces all of the GUI for the Stammtisch Application */
public class MainGuiPagination extends Application 
{
	/* Variables for connecting to the Stammtisch Server */
	private static String defaultHost = "stammsvr.servebeer.com";
	private static int defaultPort = 26656;
	private static String host;
	private static int port;
	private ServerRequestHandler com;
	
	/* Variables for the primary stage */
	private Stage window;
	private Scene mainMenu, logInMenu, signUpMenu, userScreenMenu;
	private Scene presentationMenu, createPresentationMenu, commentsMenu;
	private int width = 880;
	private int height = 660;

	/* Variables for addMainGridItems() method */
	private Button signUp, logIn;
	private Text welcomeMessage;

	/* Variables for addLoginGridItems() method */
	private Button btnLogIn, btnGoBack1;
	private Label userName1, passWord1;
	private TextField textFieldName;
	private PasswordField textFieldPassword1;
	private Text messageLogIn, response1;
	private String sUsernameLogin, sPasswordLogin;

	/* variables for addSignupGridItems() method */
	private User user = new User();
	private Button btnRegister, btnGoBack2;
	private Label dateOfBirth, email, userName2, passWord2;
	private TextField textFieldEmail, textFieldUsername;
	private PasswordField textFieldPassword2;
	private Text messageSignUp, response2;
	private String sEmail, sUsername, sPassword;
	private LocalDate sDateOfBirth;

	/* variables for addUserGridItems() method */
	private BorderPane userScreenLayout;
	private Button btnLogOut, btnSearch, btnReset, btnLoadPres;
	private TextField textFieldTitle, textFieldAuthor, textFieldLanguage;
	private Text messageUser, response3;
	private String title, author, language;
	private int presentationIndex, toolTipIndex;
	private ObservableList<String> observableListSearch;
	private ArrayList<String> idList = new ArrayList<String>();
	private ArrayList<String> searchList = new ArrayList<String>();
	private PresentationShell presentationShell = new PresentationShell();
	private PresentationShell presentationLoad;
	private String presentationID;
	private ArrayList<String[]> searchResults = new ArrayList<String[]>(); 
	
	/* Variables for the searchDetails() method */
	private final ListView<String> searchView = new ListView<String>();

	/* Variables for the addCreatePresentationGridItems() methods */
	private GridPane gridForCreation;
	private TextField titleField, languageField, startTimeField, endTimeField;
	private Presentation createdPres;
	private Button btnNext, btnCreate, btnOpenMediaDty;
	private Label mediaLanguage, mediaTranslation, startTime, endTime;
	private TextArea mediaLanguageText, mediaTranslationText;
	private VideoItem videoItem = new VideoItem();
	private AudioItem audioItem = new AudioItem();
	private SlideItem xmlSlide = new SlideItem();
	private FileChooser browseMediaFiles = new FileChooser();
	private BorderPane createPresentationScreenLayout;
	private String mediaPathname;
	private boolean containsVideo = false;
	private boolean containsAudio = false;
	private String titleCreated;
	private AnchorPane anchorForMedia;
	
	/* Variables for the destroyVid() method */
	private MediaFx videoPlayer;

	/* Variables for the addCommentScreenGridItems() method */
	private Text messageRating, messageSubmit;
	private Button btnLike, btnDislike, btnSubmit, btnGoBackToPres;
	private int rating = 0;
	private String writtenComments;

	/* Variables for presentation scene */
	private SlideHandler sh = new SlideHandler();
	private String xmlPathname, parsingFileName;
	private Presentation tempPres = new Presentation();
	private XMLParser parser;
	private BorderPane presentationLayout;
	private Pagination pagination;
	private StackPane slidePane = new StackPane();

	/* Variables for the Comments Scene */
	private BorderPane commentsScreenLayout;
	
	/* Variables for menuItems() method */
	private MenuBar menuBar;
	private MenuItem exit;
	private File selectedFile;
	private FileChooser browseFiles = new FileChooser();	
	private boolean showCommentsMenuBar = false;
	private boolean showCreatePresMenuBar = false;
	private boolean showUserMenuBar = false; 
	private ObservableList<String> observableListComments;
	private ArrayList<String> commentsList = new ArrayList<String>();
	private ArrayList<String[]> commentResults = new ArrayList<String[]>();
		
	/* Variables for the commentsDetails() method */
	private final ListView<String> commentsView = new ListView<String>();
	
	/* Variables for the commentsEdit() method */
	private TextArea commentsToWrite = new TextArea();
	

	// Constructor
	public MainGuiPagination() {}

	public static void main(String[] args)
	{
		// Required for JavaFX to run
		launch(args);
	}
	
	// Loads the port and host from a local config file (ac1362 - alexander cramb)
	public void loadConfig(String path)
	{
		port = defaultPort;
		host = defaultHost;
		
		// create file object and check that file exists
		File cfg = new File(path);
		
		// if file doesn't exist, create and write default values
		if(!cfg.exists())
		{
			PrintWriter fos;
			try 
			{
				fos = new PrintWriter(cfg);
			} 
			catch (FileNotFoundException e) 
			{
				System.out.println("Could not load config file: " +  path);
				e.printStackTrace();
				return;
			}
			
			fos.println("serverPort: " + defaultPort);
			fos.println("serverHost: " + defaultHost);
			
			// log, close the file stream, return
			System.out.println("Created Config File '" + path +  "' with default values" );
			fos.close();
			return;
		}
		
		// create a file input stream
		FileInputStream fis;
		try 
		{
			fis = new FileInputStream(cfg);
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Could not create config file: " +  path);
			e.printStackTrace();
			return;
		}
		
		// use scanner to iterate over all lines in the file
		Scanner lineScanner = new Scanner(fis);
		while(lineScanner.hasNextLine())
		{
			// parse line, delimit with spaces
			String line = lineScanner.nextLine();
			String[] lineContents = line.split("\\s+"); 
			
			// lines need a 
			if (lineContents.length > 1)
			{
				switch(lineContents[0])
				{
				case "serverPort:":
					try {
						port = Integer.parseInt(lineContents[1]);
					} catch (Exception e) {}
					break;
				
				case "serverHost:":
					host = lineContents[1];
					break;
					
				default:
					System.out.println("Invalid config file line. '" + lineContents[1] + "' not recognised");
					break;
				}
			}
			else
			{
				// if the line does not contain enough stuff-
				// print an instructional message for the benefit of the user (marker)
				System.out.println("Config file parameter malformed. Did you miss a space somehwere?");
			}
		}
		
		lineScanner.close();
	}

	// Override the init() method
	@Override
	public void init() 
	{
		System.out.println("Setting up/initialising GUI now");
		// Make a new 'temp' folder in the project directory
		Zipper.makeFolder("temp/");
		
		// load the config file
		loadConfig("client.cfg");
		
		com = new ServerRequestHandler(port, host);
		com.start();

	}

	// Required method to run the JavaFX code
	@Override
	public void start(Stage primaryStage) throws IOException 
	{
		// Run the method which will create the Graphical User Interface
		initialiseGUI(primaryStage);
	}

	// Override the stop() method
	@Override
	public void stop()
	{
		// Delete everything in the project directory 'temp' folder
		Zipper.deleteFolder("temp" + File.separator);
		
		// Disconnect Client/Server Communications
		com.stop();
		System.out.println("Stopping/Closing GUI Now!");
		
		// Close System
		System.exit(0);
	}

	/* Method which initializes and creates all the GUI */
	private boolean initialiseGUI(Stage stage) throws IOException 
	{
		// Assign window variable as the Primary Stage top level Container
		window = stage;
		
		// set the window to not be resizeable
		window.setResizable(false);

		// Import the Company Icon Logo in order to be displayed on the Windows Task Bar
		// and at the top of the application GUI
		window.getIcons().add(new Image("files/icon.png"));

		/******************** Main Menu Screen ************************/

		// Create a root node called menuLayout which uses BorderPane
		BorderPane menuLayout = new BorderPane();

		// boolean values required to change the MenuBar Display,
		// depending on what Screen the user is currently on
		showCommentsMenuBar = false;
		showCreatePresMenuBar = false;
		showUserMenuBar = false;

		// rootNode id for menuLayout in the gui_style.css file
		menuLayout.setId("menuLayout"); 
		// Create a new Scene out of the rootNode
		mainMenu = new Scene(menuLayout, width, height);

		// Load the gui_style.ccs from the same directory to provide the styling for the scenes
		menuLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// Create the grid items for the Main Menu
		GridPane controls1 = addMainGridItems();

		// Add the menu bar and controls to the root node
		MenuBar mainMenuBar = menuItems(); 
		menuLayout.setTop(mainMenuBar);
		menuLayout.setCenter(controls1);

		// On initialization, display the Main Menu first
		window.setTitle("Main Menu");
		window.setScene(mainMenu);

		/**************************************************************/

		/*********************** Login Screen *************************/
		
		// Create a root node called loginLayout which uses BorderPane
		BorderPane loginLayout = new BorderPane();

		// boolean values required to change the MenuBar Display,
		// depending on what Screen the user is currently on
		showCommentsMenuBar = false;
		showCreatePresMenuBar = false;
		showUserMenuBar = false;

		// rootNode id for menuLayout in the gui_style.css file
		loginLayout.setId("loginLayout"); 
		// Create a new Scene out of the rootNode
		logInMenu = new Scene(loginLayout, width, height);

		// Load the gui_style.ccs from the same directory to provide the styling for the scenes
		loginLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// Create the grid items for the Log In Menu
		GridPane controls2 = addLoginGridItems();
		
		// Add the menu bar and controls to the root node
		loginLayout.setCenter(controls2);

		/*************************************************************/

		/******************** Sign Up screen *************************/
		
		// Create a root node called signUpLayout which uses BorderPane
		BorderPane signupLayout = new BorderPane();

		// boolean values required to change the MenuBar Display,
		// depending on what Screen the user is currently on
		showCommentsMenuBar = false;
		showCreatePresMenuBar = false;
		showUserMenuBar = false;

		// rootNode id for signupLayout in the gui_style.css file
		signupLayout.setId("signupLayout");

		// Add the root node to the scene
		signUpMenu = new Scene(signupLayout, width, height);

		// Load the gui_style.ccs from the same directory to provide the styling for the scenes
		signupLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// Create the grid items for the Sign Up Menu
		GridPane controls3 = addSignupGridItems();

		// Add the menu bar and controls to the root node
		signupLayout.setCenter(controls3);

		/*******************************************************************/

		/************************ User Screen ******************************/
		
		// Create a root node called userScreenLayout which uses BorderPane
		userScreenLayout = new BorderPane();

		// boolean values required to change the MenuBar Display,
		// depending on what Screen the user is currently on
		showCommentsMenuBar = false;
		showCreatePresMenuBar = false;
		showUserMenuBar = true;

		// rootNode id for userScreenLayout in the gui_style.css file
		userScreenLayout.setId("userScreenLayout"); 
		// Add the root node to the scene
		userScreenMenu = new Scene(userScreenLayout, width, height, Color.BLACK);

		// Load the gui_style.ccs from the same directory to provide the styling for the scenes		
		userScreenLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// Create the grid items for the User Screen Menu
		GridPane userMenu = addUserGridItems();
		
		// Add the menu bar and controls to the root node
		MenuBar userScreenMenuBar = menuItems(); 
		userScreenLayout.setTop(userScreenMenuBar);
		userScreenLayout.setLeft(userMenu);

		/*****************************************************************/

		/******************* Create Presentation Screen ******************/
		
		// Create a root node called createPresentationScreenLayout which uses BorderPane
		createPresentationScreenLayout = new BorderPane();

		// boolean values required to change the MenuBar Display,
		// depending on what Screen the user is currently on
		showCommentsMenuBar = false;
		showCreatePresMenuBar = true;
		showUserMenuBar = true;

		// rootNode id for createPresentationScreenLayout in the gui_style.css file
		createPresentationScreenLayout.setId("createPresentationScreenLayout"); 
		
		// Create the grid items for the User Screen Menu
		createPresentationMenu = new Scene(createPresentationScreenLayout, width, height, Color.BLACK);

		// Load the gui_style.ccs from the same directory to provide the styling for the scenes	
		createPresentationScreenLayout.getStylesheets().add(MainGuiPagination.
				class.getResource("gui_style.css").toExternalForm());
		
		// Create the grid items for the Create Presentation Screen 
		GridPane createMenu = addCreatePresentationGridItems();

		// Add the menu bar and controls to the root node
		MenuBar presentationCreateMenuBar = menuItems();
		createPresentationScreenLayout.setTop(presentationCreateMenuBar);
		createPresentationScreenLayout.setCenter(createMenu);

		/**************************************************************/

		/******************* Presentation screen **********************/
		
		// Create a root node called presentationLayout which uses BorderPane
		presentationLayout = new BorderPane();

		// boolean values required to change the MenuBar Display,
		// depending on what Screen the user is currently on
		showCommentsMenuBar = true;
		showCreatePresMenuBar = false;
		showUserMenuBar = true;

		// rootNode id for presentationLayout in the gui_style.css file
		presentationLayout.setId("presentationLayout"); 

		// Add the root node to the scene
		presentationMenu = new Scene(presentationLayout, width, height, Color.BLACK);

		// Load the gui_style.ccs from the same directory to provide the styling for the scenes	
		presentationLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// Add the menu bar and controls to the root node
		MenuBar presentationMenuBar = menuItems(); // Presentation Menu
		presentationLayout.setTop(presentationMenuBar);

		/* FullScreen for presentation menu */
		presentationMenu.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent ke) 
			{
				if (ke.getCode().equals(KeyCode.F)) 
				{
					window.setFullScreen(true);
				} 
				else if (ke.getCode().equals(KeyCode.ESCAPE)) 
				{
					window.setFullScreen(false);
				}
			}
		});

		/************************************************************/

		/******************* Comments Screen ************************/
		// Create a root node called loginLayout which uses BorderPane
		commentsScreenLayout = new BorderPane();

		// boolean values required to change the MenuBar Display,
		// depending on what Screen the user is currently on
		showCommentsMenuBar = false;
		showCreatePresMenuBar = false;
		showUserMenuBar = true;

		// rootNode id for commentsScreenLayout in the gui_style.css file
		commentsScreenLayout.setId("commentsScreenLayout");
		
		// Add the root node to the scene
		commentsMenu = new Scene(commentsScreenLayout, width, height, Color.BLACK);

		// Load the gui_style.ccs from the same directory to provide the styling for the scenes
		commentsScreenLayout.getStylesheets()
				.add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		
		// Create the grid items for the Comments Screen
		GridPane commentsGrid = addCommentScreenGridItems();

		// Add menu bar to User screen
		MenuBar commentsMenuBar = menuItems();
		commentsScreenLayout.setTop(commentsMenuBar);
		commentsScreenLayout.setLeft(commentsGrid);

		/**************************************************************/

		// Set all GUI Visibility to true
		window.show();

		return true;
	}

	/* Method to add menu items and menu bar buttons for all GUI Screens */
	public MenuBar menuItems() 
	{
		// Instantiate the menu bar
		menuBar = new MenuBar();

		/******************* File Menu ************************/
		
		// Instantiate a fileMenu Menu
		Menu fileMenu = new Menu("File");

		// Instantiate a MenuItem which will take the user to the user screen
		// without logging in. This was used for testing and debugging purposes
		MenuItem goToUserScreen = new MenuItem("Go To User Screen...");

		// Go to user screen without network communications for testing
		goToUserScreen.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent e) 
			{
				// Destroy the temporary presentation
				tempPres = null;
				System.out.println("Presentation screen is now cleared");
				
				// Set the window to the appropriate screen
				window.setTitle("User Menu");
				window.setScene(userScreenMenu);
			}

		});

		// Go back to the Main Menu - used for testing and debugging
		MenuItem goBack = new MenuItem("Go Back To Main Menu...");

		goBack.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent e) 
			{
				// Destroy the temporary presentation
				tempPres = null;
				System.out.println("Presentation screen is now cleared");
				
				// Set the window to the appropriate screen
				window.setTitle("Main Menu");
				window.setScene(mainMenu);
			}

		});

		// Close the System
		exit = new MenuItem("Exit...");

		exit.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent t) {
				
				// Delete the 'temp' folder in the project directory
				Zipper.deleteFolder("temp" + File.separator);
				
				// Diconnect the Server communications
				com.logoutFromServer();
				com.stop();
				
				// Close the system
				System.exit(0);
			}
		});

		// Add all File Menu Items to the File Bar
		fileMenu.getItems().addAll(goToUserScreen, goBack, new SeparatorMenuItem(), exit);

		/****************************************************************/
		
		/********************* Presentation Menu ***********************/
		
		// Presentation Menu 
		Menu presentationMenu = new Menu("Presentation");

		// Open File Menu Item
		MenuItem openFile = new MenuItem("Open...");

		// Event handler for Local Browsing of a File
		openFile.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent e) 
			{
				System.out.println("Please select a file to open...");
				
				// Destroy the previously selected Media Player
				destroyVid();
				
				// Set extension filters for the search
				FileChooser.ExtensionFilter extFilterXML = new FileChooser.ExtensionFilter("PWS files (*.XML)", "*.XML");
				FileChooser.ExtensionFilter extFilterxml = new FileChooser.ExtensionFilter("pws files (*.xml)", "*.xml");

				// Add extension filters to the file chooser
				browseFiles.getExtensionFilters().addAll(extFilterxml, extFilterXML);

				// Assign a File object as the file chooser - open the system dialogue
				selectedFile = browseFiles.showOpenDialog(window);

				// Open the PWS selected xml file and change the scene to
				// presentation scene with a pagination layout
				openSelectedFile(selectedFile);
			}
		});

		// Create Presentation Menu Item
		MenuItem createPresentation = new MenuItem("Create Presentation...");

		// Event handler for Create Presentation Menu Item
		createPresentation.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				System.out.println("Please create a presentation...");
				// Destroy the temporary presentation
				tempPres = null;

				// Set the window to the appropriate screen
				window.setTitle("Create Presentation Menu");
				window.setScene(createPresentationMenu);
			}
		});

		// Add all Presentation Menu Items to Presentation Menu Bar
		presentationMenu.getItems().addAll(openFile, new SeparatorMenuItem(), createPresentation);

		// Comments Menu
		Menu comments = new Menu("End and go to Rating");

		// Go to the comments screen
		MenuItem commentsScreen = new MenuItem("Go to comments and rating menu...");

		// Event handler for going to the comments screen - this will only
		// appear when the user is viewing a presentation
		commentsScreen.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				
				// Destroy the media player to prevent it from
				// playing in the background
				sh.stop();
				
				/************* Client/Server Communication ***************/
				// Obtain the comments for the presentation viewed
				commentResults = com.getComments(presentationLoad);
				/*********************************************************/
				 
				// If there are no comments, do not search for the arraylist index
				 if(commentResults.isEmpty())
				 {
					 System.out.println("There are no comments");
				 }
				 // else enter the arrayList and search the index 
				 else
				 {
					 // clear the previous comments
					 commentsList.clear();
					 
					 // Search the arraylist for all the comments
					 for (int i = 0; i < commentResults.size(); i++) 
					 {
						 commentsList.add("Username: " + commentResults.get(i)[0] + "\n");
						 commentsList.add("Comment: " + commentResults.get(i)[1] + "\n");
						 commentsList.add(commentResults.get(i)[2]);
					 }
				 }

				 System.out.println(commentsList);

				// set the commentsList to an observableList in order to be used in a listView 
				observableListComments = FXCollections.observableList(commentsList);
				
				// Create the comments listView and the input comments TextArea
				Group commentsResults = commentsDetails();
				TextArea inputComments = commentsEdit();

				// Create a vbox to add the comments listView and the input comments TextArea
				VBox vBox = new VBox();
				vBox.getChildren().addAll(commentsResults, inputComments);

				// set the vbox to the right of the commentsScreenLayout
				commentsScreenLayout.setRight(vBox);
				
				// Destroy the temporary presentations
				tempPres = null;
				window.setTitle("Comments Menu");
				window.setScene(commentsMenu);
			}
		});

		
		// Add the commentsScreen Menu Item to the comments Menu bar
		comments.getItems().add(commentsScreen);
		
		/****************************************************************/

		/*** Go Back to HomePage from the create presentation Screen ***/
		
		// createpresScreen
		Menu createPresScreen = new Menu("Go Back");

		// Go back to the user homepage 
		MenuItem createPres = new MenuItem("Go to homepage...");

		// Event handler for Browsing A File
		createPres.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				// Destroy the previously selected media player
				destroyVid();
				
				// set the appropriate scene
				window.setTitle("User Screen Menu");
				window.setScene(userScreenMenu);
			}
		});

		// Add the createPres Menu Item to the createPresScreen Menu bar
		createPresScreen.getItems().add(createPres);

		/****************************************************************/
		
		// boolean conditions which will set the appropriate Menu Bar depending
		// on what scene the user is viewing
		if (showCommentsMenuBar == true) 
		{
			menuBar.getMenus().addAll(fileMenu, presentationMenu, comments);
		} 
		else if (showCreatePresMenuBar == true) 
		{
			menuBar.getMenus().addAll(fileMenu, presentationMenu, createPresScreen);
		} 
		else if (showUserMenuBar == true) 
		{
			menuBar.getMenus().addAll(fileMenu, presentationMenu);
		} 
		else 
		{
			// the default main menu
			menuBar.getMenus().addAll(fileMenu);
		}

		return menuBar;
	}

	/* Method for creating the Pagination Page count and indexes */
	private void pageTurn() 
	{
		// Create the pagination pages
		pagination.setPageFactory(new Callback<Integer, Node>() 
		{
			@Override
			public Node call(Integer pageIndex) 
			{
				try 
				{
					// Stop the media player from playing when entering a new page
					sh.stop();
					System.out.println("Media Stopped");

					// Identify when the last page of the presentation is reached
					if ((pagination.getCurrentPageIndex() + 1) == pagination.getPageCount()) 
					{
						System.out.println("You have reached the end of the presentation");
					}

					// Create a new StackPane slide for each page of the pagination
					slidePane = sh.getSlideStack(tempPres, pageIndex, width, height - 100, presentationMenu);

					/* Mouse event handler for the slidePane */
					slidePane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() 
					{
						@Override
						public void handle(MouseEvent mouseEvent) 
						{
							if (mouseEvent.isPrimaryButtonDown()) 
							{
								// Stop the media player
								sh.stop();
								// Increment page
								pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() + 1);
							}
							if (mouseEvent.isSecondaryButtonDown()) 
							{
								// Stop the media player
								sh.stop();
								// Decrement page
								pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() - 1);
							}
							mouseEvent.consume();
						}
					});

					return slidePane;

				} catch (IOException e) 
				{
					return null;
				}
			}
		});
	}

	/* Method for selecting a PWS xml file and if not null, return the string
	   name of the xml file and pass it into the xml parser Local BROWSING
	 */
	private File openSelectedFile(File xmlFile) 
	{

		// Check if the xml file is not null
		if (xmlFile != null) 
		{
			// Change scene to presentationMenu
			window.setTitle("Presentation");
			window.setScene(presentationMenu);

			// get the absolute path name of the xml file
			xmlPathname = xmlFile.getAbsolutePath();

			// rename the path name variable
			parsingFileName = xmlPathname;
			// display the details
			System.out.println("File selected: " + parsingFileName);

			// Parse the pws xml file
			parser = new XMLParser();
			parser.parseXML(parsingFileName);
			
			// Create a temporary presentation
			tempPres = parser.getPresentation();

			// Create the Pagination Layout
			pagination = new Pagination(tempPres.getSlides().size(), 0);
			System.out.println("Pagination Page Count is: " + pagination.getPageCount());
			
			// Setting the style of the pagination
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);

			// create the pagination which will contain the presentation
			pageTurn();
			System.out.println("Current Page Index: " + pagination.getCurrentPageIndex());

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

	/* Method for selecting a media file and adding it to a slide 
	   when creating presentations */
	private File openSelectedMediaFile(File mediaFile) 
	{
		// Check if the media file is null
		if (mediaFile != null) 
		{
			// Check if the media file is an mp4
			if ((mediaFile.getName().endsWith(".mp4")) || (mediaFile.getName().endsWith(".MP4"))) 
			{
				// get the media file's location
				mediaPathname = mediaFile.getAbsolutePath();
				System.out.println("File selected: " + mediaPathname);
				videoItem.setSourceFile(mediaPathname);

				// Set the x and y coordinates of the media file
				videoItem.setxStart(0.6);
				videoItem.setyStart(0.1);

				// Stop the media file from looping and the video should
				// start automatically when the presentation is viewed
				videoItem.setLoop(false);
				videoItem.setStartTime(0);

				// boolean variables which will tell the slide if it contains audio and video
				containsVideo = true;
				containsAudio = false;

				// Add the pagination to the presentation scene
				window.setTitle("Create Presentation Menu");
				// Change scene to presentationMenu
				window.setScene(createPresentationMenu);

			}
			// Check if the media file is an mp3 or a wav
			else if ((mediaFile.getName().endsWith(".wav")) || (mediaFile.getName().endsWith(".WAV"))
					|| (mediaFile.getName().endsWith(".mp3")) || (mediaFile.getName().endsWith(".MP3"))) {
				mediaPathname = mediaFile.getAbsolutePath();
				System.out.println("File selected: " + mediaPathname);
				
				// get the media file's location
				audioItem.setSourceFile(mediaPathname);
				
				// Stop the media file from looping and the audio should
				// start automatically when the presentation is viewed
				audioItem.setLoop(false);
				audioItem.setStartTime(0);

				// boolean variables which will tell the slide if it contains audio and video
				containsVideo = false;
				containsAudio = true;

				// Add the pagination to the presentation scene
				window.setTitle("Create Presentation Menu");
				// Change scene to presentationMenu
				window.setScene(createPresentationMenu);

			} 
			else 
			{
				// Print to console of the file selected is unsuitable for debugging
				System.out.println("You have not selected a suitable file!");
			}
		} 
		else 
		{
			// Print to console if there was no file selected
			System.out.println("File selection cancelled!");
		}

		return mediaFile;
	}

	/* Method for GridPane for Main Menu layout */
	public GridPane addMainGridItems() 
	{
		// Create a root node called grid. In this case a grid pane layout
		// is used, with vertical and horizontal gaps of 50
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(50);

		// Centre the controls in the scene
		grid.setAlignment(Pos.CENTER);

		// Add padding to the gridpane for aesthetic purposes
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Create the Default message that the user will be welcomed with
		// in terms of font, colour and position
		welcomeMessage = new Text("Try Our Interactive Language Learning Experience");
		welcomeMessage.setFill(Color.BLACK);
		welcomeMessage.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		grid.add(welcomeMessage, 0, 0, 2, 1);

		// Create a HBox for the buttons
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(35);

		// Create Sign Up and Login buttons
		signUp = new Button("Sign Up");
		signUp.setPrefSize(150, 50);
		//id for the sign up button the gui_style.css file
		signUp.setId("signUp"); 

		logIn = new Button("Login");
		logIn.setPrefSize(150, 50);
		
		// Set id for the log in button the gui_style.css file
		logIn.setId("logIn");

		// Adding buttons to the hbox on the welcome screen
		hbox.getChildren().addAll(signUp, logIn);

		// Adding the hbox with the buttons in it to the rootNode (home screen)
		grid.add(hbox, 1, 1);

		// Create an event handler for SignUp Button that moves
		// the user to the sign up menu screen
		signUp.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				window.setTitle("Sign Up");
				window.setScene(signUpMenu);
			}
		});

		// Create an event handler for Login Button that moves
		// the user to the login menu
		logIn.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				window.setTitle("Login");
				window.setScene(logInMenu);
			}
		});
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

		// Add padding forr aesthetic purposes
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Create the Default message on the login screen in terms of
		// font, size and position
		messageLogIn = new Text("Welcome Back");
		messageLogIn.setId("messageLogIn");
		grid.add(messageLogIn, 0, 0, 2, 1);

		// Create the labels for username and password
		// and adding to the login screen
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

		// Creating a HBox area to add the buttons to
		HBox hbArea = new HBox(10);
		hbArea.setAlignment(Pos.BOTTOM_RIGHT);
		hbArea.getChildren().addAll(btnLogIn, btnGoBack1);

		// Add event handler for go back button to initial
		// application welcome screen
		btnGoBack1.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				window.setTitle("Main Menu");
				window.setScene(mainMenu);
			}
		});

		// Adding hbox with the button in it to login screen
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
				// Set place-holders for username and password fields
				sUsernameLogin = textFieldName.getText();
				sPasswordLogin = textFieldPassword1.getText();

				// Check if any of the textfields are null
				if (sUsernameLogin.equals("") || sPasswordLogin.equals("")) 
				{
					response1.setText("Textfields are empty!");
					response1.setFill(Color.RED);
				}

				// Store the data and send to mysql database to check validity
				else 
				{
					// Set the username and password fields
					user.setUsername(sUsernameLogin);
					user.setPassword(sPasswordLogin);
					
					// Update the user on the progress
					response1.setText("Logging in, please wait");
					response1.setFill(Color.BLACK);
					
					// Send the login request to the server
					/***** Client/Server Communication *****/
					boolean loginSuccessful = com.loginToServer(user);
					System.out.println("User login was: " + loginSuccessful);
					/**************************************/
					
					// Check for successful login to the server
					// and move on to homes-screen if true
					if (loginSuccessful == true) 
					{
						response1.setText("");
						window.setTitle("User Menu Screen");
						window.setScene(userScreenMenu);
					} 
					// Send the user back to the login page if
					// access was denied
					else 
					{
						window.setTitle("Login Screen");
						window.setScene(logInMenu);
						response1.setText("Error in input, please try again!");
						response1.setFill(Color.RED);
						textFieldUsername.clear();
						textFieldPassword1.clear();
					}
				}
			}
		});

		// Event handler to get text from the text field.
		textFieldPassword1.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent ke) 
			{
				if (ke.getCode().equals(KeyCode.ENTER)) 
				{
					sUsernameLogin = textFieldName.getText();
					sPasswordLogin = textFieldPassword1.getText();

					// Check if any of the textfields are null
					if (sUsernameLogin.equals("") || sPasswordLogin.equals("")) 
					{
						response1.setText("Textfields are empty!");
						response1.setFill(Color.RED);
					}

					// Store the data and send to mysql database to check
					// validity of the details
					else 
					{
						// Set the username and password fields
						user.setUsername(sUsernameLogin);
						user.setPassword(sPasswordLogin);

						// Update the user on the progress
						response1.setText("Logging in, please wait");
						response1.setFill(Color.BLACK);
						
						// Check the outcome of the login attempt
						/***** Client/Server Communication *****/
						boolean loginSuccessful = com.loginToServer(user);
						System.out.println("User login was: " + loginSuccessful);
						/***************************************/

						// Change to user home screen if the 
						// login was successful
						if (loginSuccessful == true) 
						{
							response1.setText("");
							window.setTitle("User Menu Screen");
							window.setScene(userScreenMenu);
						}
						// Send the user back to the login page if
						// access was denied
						else 
						{
							window.setTitle("Login Screen");
							window.setScene(logInMenu);
							response1.setText("Error in input, please try again!");
							response1.setFill(Color.RED);
							textFieldUsername.clear();
							textFieldPassword1.clear();
						}
					}
				}
			}
		});

		return grid;
	}

	/* Method for GridPane items for Sign Up Menu */
	public GridPane addSignupGridItems() 
	{
		// Create a root node called grid. In this case a grid pane layout
		// is used, with vertical and horizontal gaps of 10
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		// Centre the controls in the scene
		grid.setAlignment(Pos.CENTER);

		// Add padding to the grid for aesthetic purposes
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Create the Default message
		messageSignUp = new Text("An Exciting Experience!");
		// Set id for the sign up text the gui_style.css file
		messageSignUp.setId("messageSignUp");
		grid.add(messageSignUp, 0, 0, 2, 1);

		// Create the labels for First Name, Surname Name, email,
		// username and password and adding to the rootNode
		userName2 = new Label("Username: ");
		grid.add(userName2, 0, 1);
		passWord2 = new Label("Password: ");
		grid.add(passWord2, 0, 2);
		email = new Label("Email: ");
		grid.add(email, 0, 3);
		dateOfBirth = new Label("Date of Birth: ");
		grid.add(dateOfBirth, 0, 4);

		// Create the textfields for First Name, Surname Name, email,
		// username and password, etc..and adding to the rootNode
		textFieldUsername = new TextField();
		textFieldUsername.setPromptText("Enter Username");
		grid.add(textFieldUsername, 1, 1);
		textFieldPassword2 = new PasswordField();
		textFieldPassword2.setPromptText("Enter password of your choice");
		grid.add(textFieldPassword2, 1, 2);
		textFieldEmail = new TextField();
		textFieldEmail.setPromptText("Enter valid Email address");
		grid.add(textFieldEmail, 1, 3);

		// Add a date-picker for the user to easily 
		// select their DOB
		final DatePicker datePicker = new DatePicker(LocalDate.now());
		datePicker.setOnAction(event -> 
		{
			sDateOfBirth = datePicker.getValue();
			System.out.println("Selected date: " + sDateOfBirth);
		});
		// Add the date-picker to the grid
		grid.add(datePicker, 1, 4);

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
				// Get and assign the users details to variables
				sEmail = textFieldEmail.getText();
				sUsername = textFieldUsername.getText();
				sPassword = textFieldPassword2.getText();

				// Check if any of the textfields are null
				if (sDateOfBirth.equals("") || sEmail.equals("") || sUsername.equals("") || sPassword.equals("")) 
				{
					response2.setText("Textfields are empty!");
					response2.setFill(Color.RED);
				} 
				else 
				{
					// Set the username and password fields in local
					// SignUpDetails class
					user.setUsername(sUsername);
					user.setPassword(sPassword);
					user.setEmail(sEmail);
					user.setDob(sDateOfBirth);
				}
				
				// Check the server for a successful sign up
				/**** Client/Server Communication *****/
				String signUpSuccessful = com.signUp(user);
				/**************************************/
				
				// Check if the signup was successful and move
				// the user on if so
				if (signUpSuccessful == null) 
				{
					System.out.println("Signup was successful");
					response2.setText("");
					window.setTitle("Login Screen");
					window.setScene(logInMenu);
				} 
				// Keep the user on the sign up screen if the sign up 
				// was unsuccessful
				else 
				{
					System.out.println(signUpSuccessful);
					window.setTitle("Sign Up Screen");
					window.setScene(signUpMenu);
					response1.setText("Error in input, please try again!");
					response1.setFill(Color.RED);
					textFieldUsername.clear();
					textFieldPassword2.clear();
					textFieldEmail.clear();
				}
			}
		});

		return grid;
	}

	/* Method for GridPane items for User Screen Menu */
	public GridPane addUserGridItems() 
	{
		// Create a root node called grid. In this case a grid pane layout
		// is used, with vertical and horizontal gaps of 10
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		// Centre the controls in the scene
		grid.setAlignment(Pos.CENTER);

		// Add padding to the grid for aesthetic purposes
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Create the Default message
		messageUser = new Text("Explore Presentations");
		// Set id for the messageUser text text the gui_style.css file
		messageUser.setId("messageUser"); 
		messageUser.setFill(Color.ALICEBLUE);
		messageUser.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		grid.add(messageUser, 0, 0, 2, 1);

		// Create the search fields
		textFieldTitle = new TextField();
		textFieldTitle.setPromptText("Search by Title");
		grid.add(textFieldTitle, 1, 1);
		textFieldAuthor = new TextField();
		textFieldAuthor.setPromptText("Search by Author");
		grid.add(textFieldAuthor, 1, 2);
		textFieldLanguage = new TextField();
		textFieldLanguage.setPromptText("Search by Language");
		grid.add(textFieldLanguage, 1, 3);

		// Creating Buttons for searching, resetting the search fields, 
		// logging out and loading presentations
		btnLogOut = new Button("Log Out");
		btnLogOut.setId("btnLogOut");
		btnSearch = new Button("Search");
		btnSearch.setId("btnSearch");
		btnReset = new Button("Reset Search");
		btnReset.setId("btnReset");
		btnLoadPres = new Button("Load Presentation");
		btnLoadPres.setId("btnLoadPres");

		// Creating a HBox area to add the buttons to
		HBox hbArea = new HBox(10);
		hbArea.setAlignment(Pos.BOTTOM_RIGHT);
		hbArea.getChildren().addAll(btnSearch, btnReset, btnLoadPres, btnLogOut);

		// Adding hbArea with the buttons in it to the grid
		grid.add(hbArea, 1, 9);

		// Add the response to the grid
		response3 = new Text();
		grid.add(response3, 1, 4);

		// Event handler for btnLogout
		btnLogOut.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				/*** Client/Server Communication ***/
				// Send the server a logout request
				com.logoutFromServer();
				/***********************************/
				
				// Clear the text fields in the user screen
				textFieldName.clear();
				textFieldPassword1.clear();
				window.setTitle("Main Menu");
				window.setScene(mainMenu);
				response3.setText("");
			}
		});

		// Event handler for btnSearch
		btnSearch.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				// Obtain the text from the text fields
				title = textFieldTitle.getText();
				author = textFieldAuthor.getText();
				language = textFieldLanguage.getText();

				// Pass the information to the presentation shell 
				presentationShell.setTitle(title);
				presentationShell.setAuthor(author);
				presentationShell.setLanguage(language);

				/************* Client/Server Communication ***************/
				// Send a request to the server to search for results
				searchResults = com.searchForPresentation(presentationShell);
				/*********************************************************/

				// Add a response
				response3.setText("Searching for results");
				response3.setFill(Color.ALICEBLUE);

				// Clear the previously searched results
				searchList.clear();
				idList.clear();

				// Cycle through the arraylist<String[]> searchresults and return the results
				for (int i = 0; i < searchResults.size(); i++) 
				{
					for (int x = 0; x < searchResults.get(i).length; x++) 
					{
						if (x == 0) 
						{
							idList.add(searchResults.get(i)[x]);
						} 
						else if (x == 1) 
						{
							searchList.add("Title: " + searchResults.get(i)[x] + "\n");
						} 
						else if (x == 2) 
						{
							searchList.add(searchList.get(i) + "Author: " + searchResults.get(i)[x] + "\n");
							searchList.remove(i);
						} 
						else if (x == 3) 
						{
							searchList.add(searchList.get(i) + "Language: " + searchResults.get(i)[x]);
							searchList.remove(i);
						}
					}
				}

				// Print the results to the console 
				System.out.println(searchList);

				// Make the searchList observable so that it can be used in a ListView 
				observableListSearch = FXCollections.observableList(searchList);

				// Make the search results visible and apply them to the 
				// right side of the user screen 
				searchView.setVisible(true);
				userScreenLayout.setRight(searchDetails());
			}

		});

		// Event handler for the textFieldTitle
		textFieldTitle.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent ke) 
			{
				if (ke.getCode().equals(KeyCode.ENTER)) 
				{
					title = textFieldTitle.getText();
					author = textFieldAuthor.getText();
					language = textFieldLanguage.getText();

					// Set the username and password fields in local
					// SignUpDetails class
					presentationShell.setTitle(title);
					presentationShell.setAuthor(author);
					presentationShell.setLanguage(language);

					/************* Client/Server Communication ***************/
				    // Send a request to the server to search for results
					searchResults = com.searchForPresentation(presentationShell);
					/*********************************************************/

					// Add the response
					response3.setText("Searching for results");
					response3.setFill(Color.ALICEBLUE);

					// Clear the previously searched list
					searchList.clear();
					idList.clear();

					// Cycle through the arraylist<String[]> searchresults and return the results
					for (int i = 0; i < searchResults.size(); i++) 
					{
						for (int x = 0; x < searchResults.get(i).length; x++) 
						{
							if (x == 0) 
							{
								idList.add(searchResults.get(i)[x]);
							}
							else if (x == 1) 
							{
								searchList.add("Title: " + searchResults.get(i)[x] + "\n");
							} 
							else if (x == 2) 
							{
								searchList.add(searchList.get(i) + "Author: " + searchResults.get(i)[x] + "\n");
								searchList.remove(i);
							} 
							else if (x == 3) 
							{
								searchList.add(searchList.get(i) + "Language: " + searchResults.get(i)[x]);
								searchList.remove(i);
							}
						}
					}

					// print the results to the console
					System.out.println(searchList);

					// Make the list observable so that it can be added to the list view
					observableListSearch = FXCollections.observableList(searchList);

					// make the list visible and set it to the right of the user screen
					searchView.setVisible(true);
					userScreenLayout.setRight(searchDetails());
				}
			}

		});

		// Event handler for the textFieldAuthor
		textFieldAuthor.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent ke) 
			{
				if (ke.getCode().equals(KeyCode.ENTER)) 
				{

					// Obtain the text from the text fields
					title = textFieldTitle.getText();
					author = textFieldAuthor.getText();
					language = textFieldLanguage.getText();

					// Pass the details to the presentation shell 
					presentationShell.setTitle(title);
					presentationShell.setAuthor(author);
					presentationShell.setLanguage(language);

					/************* Client/Server Communication ***************/
					// Send the server a search request
					searchResults = com.searchForPresentation(presentationShell);
					/*********************************************************/

					// Add a response
					response3.setText("Searching for results");
					response3.setFill(Color.ALICEBLUE);

					// Clear the previously searched list
					searchList.clear();
					idList.clear();

					// Cycle through the arraylist<String[]> searchResults and return the results
					for (int i = 0; i < searchResults.size(); i++) 
					{
						for (int x = 0; x < searchResults.get(i).length; x++) 
						{
							if (x == 0) 
							{
								idList.add(searchResults.get(i)[x]);
							} 
							else if (x == 1) 
							{
								searchList.add("Title: " + searchResults.get(i)[x] + "\n");
							} 
							else if (x == 2) 
							{
								searchList.add(searchList.get(i) + "Author: " + searchResults.get(i)[x] + "\n");
								searchList.remove(i);
							} 
							else if (x == 3) 
							{
								searchList.add(searchList.get(i) + "Language: " + searchResults.get(i)[x]);
								searchList.remove(i);
							}
						}
					}

					// Print the search results to the console
					System.out.println(searchList);

					// Make the search list observable so that it can be added to the list view
					observableListSearch = FXCollections.observableList(searchList);

					// Pass the details to the presentation shell
					searchView.setVisible(true);
					userScreenLayout.setRight(searchDetails());
				}
			}
		});
		
		// Event handler for the textFieldLanguage
		textFieldLanguage.setOnKeyPressed(new EventHandler<KeyEvent>() 
		{
			@Override
			public void handle(KeyEvent ke) 
			{
				if (ke.getCode().equals(KeyCode.ENTER)) 
				{
					// Obtain the text from the text fields 
					title = textFieldTitle.getText();
					author = textFieldAuthor.getText();
					language = textFieldLanguage.getText();

					// Pass the details to the presentation shell
					presentationShell.setTitle(title);
					presentationShell.setAuthor(author);
					presentationShell.setLanguage(language);

					/************* Client/Server Communication ***************/
					// Send the server a search request
					searchResults = com.searchForPresentation(presentationShell);
					/*********************************************************/

					// Add a response
					response3.setText("Searching for results");
					response3.setFill(Color.ALICEBLUE);

					// Clear the previously searched results
					searchList.clear();
					idList.clear();

					// Cycle through the arraylist<String[]> searchresults and return the results
					for (int i = 0; i < searchResults.size(); i++) 
					{
						for (int x = 0; x < searchResults.get(i).length; x++) 
						{
							if (x == 0) 
							{
								idList.add(searchResults.get(i)[x]);
							} 
							else if (x == 1) 
							{
								searchList.add("Title: " + searchResults.get(i)[x] + "\n");
							} 
							else if (x == 2) 
							{
								searchList.add(searchList.get(i) + "Author: " + searchResults.get(i)[x] + "\n");
								searchList.remove(i);
							} 
							else if (x == 3) 
							{
								searchList.add(searchList.get(i) + "Language: " + searchResults.get(i)[x]);
								searchList.remove(i);
							}
						}
					}

					// Print the search results to the console
					System.out.println(searchList);

					// Make the search list observale so that it can be added to the list view
					observableListSearch = FXCollections.observableList(searchList);

					// make the list view visible and add it to the right of the user screen
					searchView.setVisible(true);
					userScreenLayout.setRight(searchDetails());
				}
			}

		});

		// Event handler for btnLoadPres
		btnLoadPres.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{				
				// Get the index of the selected presentation in the search pane
				// and the ID of the presentation store in the SQL database
				presentationIndex = searchView.getSelectionModel().getSelectedIndex();
				presentationID = idList.get(presentationIndex);
				System.out.println("Presentation ID is: " + presentationID);

				// make a new presentation shell object
				presentationLoad = new PresentationShell();

				// Assign the searched items to the new presentation shell object
				presentationLoad.setId(Integer.parseInt(searchResults.get(presentationIndex)[0]));
				presentationLoad.setTitle(searchResults.get(presentationIndex)[1]);
				presentationLoad.setAuthor(searchResults.get(presentationIndex)[2]);
				presentationLoad.setLanguage(searchResults.get(presentationIndex)[3]);

				/************* Client/Server Communication ***************/
				// Send the server a request to receive download a presentation
				com.getPresentation(presentationLoad, "temp" + File.separator);
				/*********************************************************/
				
				try 
				{
					// First delete the files in the temp folder from the project directory
					Zipper.deleteFolder("temp" + File.separator + "tempPres");
					System.out.println("Deleted temp folder contents");
				} 
				catch (Exception e1) 
				{
					// Print out if an exception occurs 
					System.out.println("Unable to delete folder...");
					e1.printStackTrace();
				}
				try 
				{	
					// make a new tempPres folder				
					Zipper.makeFolder("temp" + File.separator + "tempPres");
					System.out.println("Deleted temp folder contents");
				}
				catch (Exception e1)
				{
					System.out.println("Unable to create folder...");
				}
				try 
				{
					// Try to unzip the temp folder
					System.out.println("temp" + File.separator + presentationLoad.getId().toString() + ".pws");
					Zipper.unzip(("temp" + File.separator + presentationLoad.getId().toString() + ".pws"), "temp");
				}
				catch (Exception e1)
				{
					System.out.println("Unable to zip folder...");
					e1.printStackTrace();
				}

				// Obtain the xml filepath
				xmlPathname = "temp" + File.separator + "presentation.xml";

				// Make a new xmlFile object
				File xmlFile = new File("temp" + File.separator + "tempPres" + File.separator + "presentation.xml");
				selectedFile = xmlFile;

				// open the selected file
				openSelectedFile(selectedFile);
				
			}
		});

		// Event handler for btnReset
		btnReset.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				// Clear the textFields
				textFieldTitle.clear();
				textFieldAuthor.clear();
				textFieldLanguage.clear();
				searchList.clear();
				idList.clear();
				response3.setText("");

				// Make the search results invisible as there will be a new search
				userScreenLayout.setRight(searchDetails());
				searchView.setVisible(false);
			}
		});

		return grid;
	}

	/* Method for adding the ListView for the Search Results */
	public Group searchDetails() 
	{
		// Make a new Group object
		Group listGroup = new Group();
		// Set id for the messageUser text text the gui_style.css file
		searchView.setId("listView");
		// Set the preferred dimensions for the listView
		searchView.setPrefHeight(userScreenLayout.getHeight());
		searchView.setPrefWidth(userScreenLayout.getWidth() / 2);
		System.out.println(observableListSearch);
		searchView.setOpacity(0.7);

		// Make a StringProperty object to enable the Tool Tip to work
		final StringProperty hoveredItem = new SimpleStringProperty(null);

		// Create the contents of the ListView searchView
		searchView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() 
		{
			@Override
			public ListCell<String> call(ListView<String> listView) 
			{
				final ListCell<String> listCell = new ListCell<String>() 
				{
					@Override
					public void updateItem(String item, boolean empty) 
					{
						super.updateItem(item, empty);
						if (empty) 
						{
							setText(null);
						} 
						else 
						{
							setText(item);
						}
					}
				};

				// Use a change listener to be used with the tool tip for the hover property
				listCell.hoverProperty().addListener(new ChangeListener<Boolean>() 
				{
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,Boolean newValue)
					{
						if (newValue) 
						{
							// Get the items in the list cell and assign them to the hoveredItem object
							hoveredItem.set(listCell.getItem());
							// Assign the listCell's index to the toolTipIndex 
							toolTipIndex = listCell.getIndex();
							
							// Check the index of the searchResults arrayList<String[]> 
							if (toolTipIndex < searchResults.size())
							{
								// Make a new Tooltip to display the ratings from the search results
								Tooltip t = new Tooltip("Rating: " + searchResults.get(toolTipIndex)[4]);
								// Assign the style of the Tooltip
								t.getStyleClass().add("ttip");
								// Assign the Tooltip to each listCell of the searhView 
								listCell.setTooltip(t);
							}
						} 
						else 
						{
							hoveredItem.set(null);
						}
					}
				});

				return listCell;
			}
		});

		// Assign the observable list observableListSearch to the ListView searchView
		searchView.setItems(observableListSearch);

		// Add the searchView to the Group listGroup
		searchView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listGroup.getChildren().add(searchView);
		return listGroup;
	}

	/* Method for GridPane items for the Create Presentation Screen Menu */
	public GridPane addCreatePresentationGridItems() 
	{
		// Create a root node called grid. In this case a grid pane layout
		// is used, with vertical and horizontal gaps of 10
		gridForCreation = new GridPane();
		gridForCreation.setHgap(10);
		gridForCreation.setVgap(10);

		// Centre align the grid in the scene
		gridForCreation.setAlignment(Pos.TOP_CENTER);

		// Add padding to the grid
		gridForCreation.setPadding(new Insets(25, 25, 25, 25));

		// Create the text field for the title of the presentation
		titleField = new TextField();
		titleField.setPrefSize(250, 10);
		titleField.setPromptText("Enter The Title of the Presentation");

		// Create the text field for the language used in the presentation
		languageField = new TextField();
		languageField.setPrefSize(180, 10);
		languageField.setPromptText("Enter The Language Used");		
		
		// Create the Label for the language used in the media 
		mediaLanguage = new Label("Video Language");
		// Set id for the mediaLanguage label in the gui_style.css file
		mediaLanguage.setId("mediaLanguage");
		mediaLanguage.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		// Add the label to the grid
		gridForCreation.add(mediaLanguage, 0, 1, 3, 1);

		// Create a new TextArea for the language of the media
		mediaLanguageText = new TextArea();
		mediaLanguageText.setPromptText("Enter Video Language Text");
		mediaLanguageText.setPrefSize(100, 250);
		//mediaLanguageText.wrapTextProperty(); - this is a bug, text does not wrap but it should
		// Add mediaLanguagetext to the grid
		gridForCreation.add(mediaLanguageText, 0, 2);

		// Create a label for the start time
		startTime = new Label("Start Time:");
		// Set id for startTime for the gui_style.css file
		startTime.setId("startTime"); 
		endTime = new Label("End Time:");
		// Set id for the endTime for the gui_style.css file
		endTime.setId("endTime"); 

		// Create a new startTimeField TextField
		startTimeField = new TextField();
		startTimeField.setPromptText("Enter Start Time");

		// Add a listener to the text field to make it numeric only
		startTimeField.textProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) 
				{
					startTimeField.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (startTimeField.getText().length() > 3) 
				{
					// Allow only three characters
					String s = startTimeField.getText().substring(0, 3);
					startTimeField.setText(s);
				}
			}
		});

		// Create a new startTimeField TextField
		endTimeField = new TextField();
		endTimeField.setPromptText("Enter End Time");

		// Add a listener to the text field to make it numeric only
		endTimeField.textProperty().addListener(new ChangeListener<String>() 
		{
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) 
			{
				if (!newValue.matches("\\d*")) 
				{
					endTimeField.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (endTimeField.getText().length() > 3) 
				{
					// Allow only three characters
					String s = startTimeField.getText().substring(0, 3);
					endTimeField.setText(s);
				}
			}
		});

		// Make a new label for the language translation text of the media file 
		mediaTranslation = new Label("Video Translation");
		
		// Set id for the mediaTraslation for the gui_style.css file
		mediaTranslation.setId("mediaTranslation");
		mediaTranslation.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		// Add the label to the grid
		gridForCreation.add(mediaTranslation, 0, 4, 3, 1);

		// Create a new TextArea for the language translation of the media
		mediaTranslationText = new TextArea();
		mediaTranslationText.setPromptText("Enter Video Translation Text");
		mediaTranslationText.setPrefSize(100, 250);
		// Add the mediaTranslationtext to the grid
		gridForCreation.add(mediaTranslationText, 0, 5);

		// Create the buttons for inserting the items into the slide,
		// for opening a media file and for creating a new xml presentation
		btnNext = new Button("Set");
		// Set id for btnNext for the gui_style.css file
		btnNext.setId("btnNext");

		btnOpenMediaDty = new Button("Add Media");
		// Set id for btnOpenMediaDty for the gui_style.css file
		btnOpenMediaDty.setId("btnOpenMediaDty");

		btnCreate = new Button("Create");
		// Set id for btnCreate for the gui_style.css file
		btnCreate.setId("btnCreate");		
		
		// Create the VBox which will contain the titleField and the languageField
		VBox vBox1 = new VBox(10);
		// Add titleField and languageField to vBox1
		vBox1.setAlignment(Pos.CENTER_LEFT);
		vBox1.getChildren().addAll(titleField, languageField);
		gridForCreation.add(vBox1, 0, 0);
		
		// Create a HBox to add startTime and startTimeField
		HBox hBox1 = new HBox(10);
		hBox1.setAlignment(Pos.CENTER_LEFT);
		hBox1.getChildren().addAll(startTime, startTimeField);
		
		// Create a HBox to add endtime and endTimeField
		HBox hBox2 = new HBox(10);
		hBox2.setAlignment(Pos.CENTER_RIGHT);
		hBox2.getChildren().addAll(endTime, endTimeField);

		// Creating a HBox to add btnNext, btnCreate and btnOpenMediaDty
		HBox hBox3 = new HBox(10);
		hBox3.setAlignment(Pos.CENTER_LEFT);
		hBox3.getChildren().addAll(btnNext, btnCreate, btnOpenMediaDty);
		// Add hBox3 to the grid
		gridForCreation.add(hBox3, 0, 6);
		
		// Create the VBox which will contain hbox1 and hbox2
		VBox vBox2 = new VBox(10);
		vBox2.setAlignment(Pos.CENTER_LEFT);
		vBox2.getChildren().addAll(hBox1, hBox2);
		// Add vBox2 to the grid
		gridForCreation.add(vBox2, 0, 3);


		// Event handler for btnNext
		btnNext.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) 
			{
				// Check if any of the text fields are empty before proceeding
				if ((titleField.getText().isEmpty() || languageField.getText().isEmpty() || 
						startTimeField.getText().isEmpty() || endTimeField.getText().isEmpty() 
						|| mediaLanguageText.getText().isEmpty() || mediaTranslationText.getText().isEmpty())) 
				{
					// Do Nothing
					System.out.println("You have empty fields, please fill in");
				} 
				else 
				{
					// Obtain the text from the text fields 
					titleField.getText();
					languageField.getText();
					mediaLanguageText.getText();
					mediaTranslationText.getText();
					startTimeField.getText();
					endTimeField.getText();

					// Print the text to the console
					System.out.println("Your title is: " + titleField.getText());
					System.out.println("Your language is: " + languageField.getText());
					System.out.println("Your video text is: " + mediaLanguageText.getText());
					System.out.println("Your translation text is: " + mediaTranslationText.getText());
					System.out.println("Start time is: " + startTimeField.getText());
					System.out.println("End time is: " + endTimeField.getText());

					// Create new TextItem objects which will contain the text from the text fields
					TextItem videoText = new TextItem();
					TextItem transText = new TextItem();

					// Get the title of the created presentation
					titleCreated = titleField.getText();

					// Add the created presentation text to the TextItems
					videoText.setText(mediaLanguageText.getText());
					transText.setText(mediaTranslationText.getText());
					
					// Set the start time for the text in seconds
					videoText.setStartTime(Integer.parseInt(startTimeField.getText()) * 1000);
					transText.setStartTime(Integer.parseInt(startTimeField.getText()) * 1000);
					
					// Set the duration of the text as the difference between the start time and the end time
					videoText.setDuration(
							(Integer.parseInt(endTimeField.getText()) - (Integer.parseInt(startTimeField.getText())))
							* 1000);
					System.out.println("Video Text Duration: " + videoText.getDuration());
					transText.setDuration(
							(Integer.parseInt(endTimeField.getText()) - (Integer.parseInt(startTimeField.getText())))
							* 1000);
					System.out.println("Video TranslationText Duration: " + transText.getDuration());

					// Set the dimensions for the videoText and transText
					videoText.setHeight(0.3f);
					videoText.setWidth(0.4f);
					videoText.setxStart(0.1f);
					videoText.setyStart(0.1f);

					transText.setHeight(0.3f);
					transText.setWidth(0.4f);
					transText.setxStart(0.1f);
					transText.setyStart(0.6f);

					// Set the Font of the text
					videoText.setFont("SansSerif");
					transText.setFont("SansSerif");

					// Add the text to the xmlSlide
					xmlSlide.addText(videoText);
					xmlSlide.addText(transText);

					// Clear the text fields for the next input
					mediaLanguageText.clear();
					mediaTranslationText.clear();
					startTimeField.clear();
					endTimeField.clear();

				}
				
				// Stay on the same scene
				window.setTitle("Create Presentation Menu");
				window.setScene(createPresentationMenu);
			}
		});

		// Event handler for Browsing A Media File
		btnOpenMediaDty.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Please select a media file to open...");

				// Set extension filters - User can only select mp4, wav or mp3
				FileChooser.ExtensionFilter extFilterMP4 = new FileChooser.ExtensionFilter("MP4 files (*.MP4)", "*.MP4");
				FileChooser.ExtensionFilter extFiltermp4 = new FileChooser.ExtensionFilter("mp4 files (*.mp4)", "*.mp4");
				FileChooser.ExtensionFilter extFilterWAV = new FileChooser.ExtensionFilter("WAV files (*.WAV)",	"*.WAV");
				FileChooser.ExtensionFilter extFilterwav = new FileChooser.ExtensionFilter("wav files (*.wav)",	"*.wav");
				FileChooser.ExtensionFilter extFilterMP3 = new FileChooser.ExtensionFilter("MP3 files (*.MP3)",	"*.MP3");
				FileChooser.ExtensionFilter extFiltermp3 = new FileChooser.ExtensionFilter("mp3 files (*.mp3)",	"*.mp3");

				// Add the extension files to the file chooser
				browseMediaFiles.getExtensionFilters().addAll(extFilterMP4, extFiltermp4, extFilterWAV, extFilterwav,
						extFilterMP3, extFiltermp3);
				
				// Assign a File object as the file chooser - open the system dialogue
				File selectedMediaFile = browseMediaFiles.showOpenDialog(window);

				// Open the PWS selected xml file and change the scene to presentation scene with a pagination layout
				openSelectedMediaFile(selectedMediaFile);
				
				// Destroy the previously selected video in the create presentation screen
				destroyVid();
				
				// Create a new VideoItem whch the media player can be created from
				VideoItem videoLoad = new VideoItem();
				videoLoad = videoItem;
				videoLoad.setxStart(0);
				videoLoad.setyStart(0);
				videoPlayer = new MediaFx(videoLoad, 0.3, 0.3);

				// Create a Group object which will hold the media player
				Group group = new Group();
				group.getChildren().add(videoPlayer.createContent(createPresentationMenu));
				
				// Create an AnchorPane to add the media player to 
				anchorForMedia = new AnchorPane();
				AnchorPane.setTopAnchor(group, 0.0);
				AnchorPane.setBottomAnchor(group, 1.0);
				AnchorPane.setLeftAnchor(group, 1.0);
				AnchorPane.setRightAnchor(group, 0.0);
				anchorForMedia.getChildren().add(group);
				
				// Add the anchorpane to the grid
				gridForCreation.add(anchorForMedia, 1, 2);
			}
		});

		// Event handler for btnCreate
		btnCreate.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				// create a new presentation object called createdPres
				createdPres = new Presentation();

				// Create a new presentationShell object
				PresentationShell presShell = new PresentationShell();
				
				// Add the details of the created presentation to the presentation shell
				presShell.setAuthor(sUsernameLogin);
				presShell.setLanguage(languageField.getText());
				presShell.setTitle(titleField.getText());
				
				/*** Server/Client Communication ***/
				// Send a request to the server to upload the presentation shell
				XMLCreator xmlCreator = new XMLCreator(com, presShell);
				/***********************************/

				// Check if the media file selected was an mp4 and add it to the presentation slide
				if (containsVideo == true) 
				{
					xmlSlide.addVideo(videoItem);
				} 
				// Check if the media file selected was an mp3 or wav and add it to the presentation slide
				else if (containsAudio == true) 
				{
					xmlSlide.addAudio(audioItem);
				}

				// Create the presentation object
				FillPres fp = new FillPres();
				createdPres = fp.fillPresentation(createdPres, sUsernameLogin, xmlSlide, titleCreated);

				// Create the XML file associated to the presentation object
				xmlCreator.createXML(createdPres, true, true, true, false, false, true, containsVideo, containsAudio);
				
			}
		});

		return gridForCreation;
	}

	/* Method for GridPane items for the Comments Screen Menu */
	public GridPane addCommentScreenGridItems() 
	{

		// Create a root node called grid. In this case a grid pane layout
		// is used, with vertical and horizontal gaps of 10
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		// Centre the controls in the scene
		grid.setAlignment(Pos.CENTER);

		// Add padding to the grid
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Create the Default message
		messageRating = new Text("Please give your rating");
		// Set id for messageRating for the gui_style.css file
		messageRating.setId("messageRating"); 		
		messageRating.setFill(Color.ALICEBLUE);
		messageRating.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		// Add messageRating to the grid
		grid.add(messageRating, 1, 0, 1, 1);

		// Creating buttons for rating up or down
		Image thumbsUp = new Image(getClass().getResourceAsStream("thumb_up.png"));
		btnLike = new Button("Like", new ImageView(thumbsUp));
		btnLike.setPrefSize(120, 50);
		// Set id for thumbsUp for the gui_style.css file
		btnLike.setId("btnLike");

		Image thumbsDown = new Image(getClass().getResourceAsStream("thumb_down.png"));
		btnDislike = new Button("Dislike", new ImageView(thumbsDown));
		btnDislike.setPrefSize(120, 50);
		// Set id for thumbsDown for the gui_style.css file
		btnDislike.setId("btnDislike");

		// Creating buttons for submitting comments and for returning back to the presentation
		btnSubmit = new Button("Submit");
		btnSubmit.setPrefSize(120, 50);
		// Set id for btnSubmit for the gui_style.css file
		btnSubmit.setId("btnSubmit");

		btnGoBackToPres = new Button("Go Back");
		btnGoBackToPres.setPrefSize(120, 50);
		// Set id for btngobackToPres for the gui_style.css file
		btnGoBackToPres.setId("btnGoBackToPres");

		// Creating a VBox area to add the buttons to
		VBox vbArea = new VBox(10);
		vbArea.setAlignment(Pos.CENTER);
		vbArea.getChildren().addAll(btnLike, btnDislike, btnSubmit, btnGoBackToPres);

		// Adding vbArea with the button in it to the rootNode
		grid.add(vbArea, 1, 5);

		// Create a background canvas so that messageSubmit can be viewed
		Canvas backgroundCanvas = new Canvas((commentsMenu.getWidth() / 2) + 30, commentsMenu.getHeight() / 8);
		GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
		gc.setFill(Color.rgb(0, 0, 0, 0.35));
		gc.fillRect(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());

		messageSubmit = new Text("Hit submit to upload your ratings and comments!");
		// Set id for messageSubmit for the gui_style.css file
		messageSubmit.setId("messageSubmit"); 
		messageSubmit.setFill(Color.ALICEBLUE);
		messageSubmit.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		// Add the background canvas and messageSubmit to the grid
		grid.add(backgroundCanvas, 0, 6, 2, 1);
		grid.add(messageSubmit, 0, 6, 2, 1);

		// Event handler for btnLike
		btnLike.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				// Set the rating to +1
				rating = 1;
				
				/*** Client/Server Communication ***/
				// Send the server an up vote to be stored in the SQL database
				com.sendVote(presentationLoad, rating);
				/***********************************/
			}
		});

		// Event handler for btnDislike
		btnDislike.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				// Set the rating to -1
				rating = -1;
				
				/*** Client/Server Communication ***/
				// Send the server a down vote to be stored in the SQL database
				com.sendVote(presentationLoad, rating);
				/***********************************/
			}
		});

		// Event handler for btnSubmit
		btnSubmit.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				// Get the written comments from the text area
				writtenComments = commentsToWrite.getText();
				System.out.println(writtenComments);
				
				/*** Client/Server Communication ***/
				// Send the comments to the server to be stored in the SQL database
				com.addComment(presentationLoad, writtenComments);
				/***********************************/
			}
		});

		// Event handler for btnGoBackToPres
		btnGoBackToPres.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent e) 
			{
				// Reload the presentation the user was viewing
				openSelectedFile(selectedFile);
			}
		});

		return grid;
	}

	/* Method for creating the ListView which will contain the comments of a viewed presentaton */
	private Group commentsDetails()
	{
		// Make a new Group node 
		Group listGroup = new Group();
		// Set id for the commentsView for the gui_style.css file
		commentsView.setId("listView");
		commentsView.setPrefHeight(commentsScreenLayout.getHeight() * 0.75);
		commentsView.setPrefWidth(commentsScreenLayout.getWidth() / 2);
		commentsView.setOpacity(0.7);
		System.out.println(observableListComments);

		// Set the observable list observableListComments to the ListView commentsView
		commentsView.setItems(observableListComments);

		// Add the commentsView to the listgroup
		listGroup.getChildren().add(commentsView);
		return listGroup;
	}

	/* Method for creating the TextArea which will be used for users to add comments to a viewed presentation */
	private TextArea commentsEdit() 
	{
		commentsToWrite.setPromptText("Please add your comments");
		// Set the dimensions for the commentsToWrite TextArea
		commentsToWrite.setMinHeight(commentsScreenLayout.getHeight() / 4);
		commentsToWrite.setMinWidth(commentsScreenLayout.getWidth() / 2);

		return commentsToWrite;
	}
	
	/* Method to destroy the MediaFx player from the create presentation screen */
	private void destroyVid()
	{
		try
		{
			// Destroy the MediaFx Player
			videoPlayer.stop();
			// remove the media player from the create presentation screen grid pane
			gridForCreation.getChildren().remove(anchorForMedia);
		}
		catch(Exception e)
		{
			System.out.println("No media to destroy");
		}
	}
}