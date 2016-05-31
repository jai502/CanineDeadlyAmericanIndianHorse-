
/**
 * (C) Stammtisch
 * First version created by: Mathew Gould & Alexander Stassis (Design Team)
 * Date of first version: 21/02/16
 * 
 * Last version by: Mathew Gould & Alexander Stassis (Design Team)
 * Date of last update:  24/05/16
 * Version number: 1
 * 
 * Commit date: 24/05/16
 * Description: Designing the Main GUI Class which will lead to other GUIs
 * This class currently is still in implementation
 * NOTE:
 */

package gui;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;

import org.omg.CORBA.SystemException;

import Objects.AudioItem;
import Objects.DocumentInfo;
import Objects.Presentation;
import Objects.SlideItem;
import Objects.TextItem;
import Objects.VideoItem;
import Parsers.FillPres;
import Parsers.XMLCreator;
import Parsers.XMLParser;
import SQL.SQLHandler;
import client.ServerRequestHandler;
import encryptionRSA.RSAEncryptDecrypt;
import encryptionRSA.Serializer;
import handlers.MediaFx;
import handlers.SlideHandler;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//import javafx.beans.InvalidationListener;
//import javafx.beans.Observable;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.*;
import javafx.util.Callback;
import searchDetails.SearchDetails;
import zipping.Zipper;

import com.RequestObject;
import com.User;
import com.PresentationShell;

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
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import Objects.Presentation;
import Parsers.XMLParser;
//import javafx.beans.InvalidationListener;
//import javafx.beans.Observable;

public class MainGuiPagination extends Application {
	private ServerRequestHandler com;
	private boolean showCommentsMenuBar = false;
	private boolean showCreatePresMenuBar = false;
	private boolean showUserMenuBar = false;
	private User user = new User();
	private PresentationShell presentationShell = new PresentationShell();
	private PresentationShell presentationLoad;
	// final ListView<String[]> listView = new ListView<String[]>();
	private final ListView<String> searchView = new ListView<String>();
	private final ListView<String> commentsView = new ListView<String>();
	private ObservableList<String> observableListSearch, observableListComments;
	private ArrayList<String> searchList = new ArrayList<String>();
	private ArrayList<String> idList = new ArrayList<String>();
	private boolean logout = true;

	/* variables for the primary stage */
	private Stage window;
	private Scene mainMenu, logInMenu, signUpMenu, userScreenMenu, presentationMenu, createPresentationMenu,
			commentsMenu;
	private int width = 880;
	private int height = 660;
	private MenuItem exit;

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

	// private ArrayList<String> inputData1 = new ArrayList<String>();

	/* variables for addSignupGridItems() method */
	private Button btnRegister, btnGoBack2;
	private Label firstName, surName, dateOfBirth, email, confirmEmail, userName2, passWord2, confirmPassword;
	private TextField textFieldFirstName, textFieldSurname, textFieldDateOfBirth, textFieldEmail, textFieldConfirmEmail,
			textFieldUsername;
	private PasswordField textFieldPassword2, textFieldConfirmPassword;
	private Text messageSignUp, response2;
	private String sFirstName, sSurname, sEmail, sConfirmEmail, sUsername, sPassword, sConfirmPassword;
	private LocalDate sDateOfBirth;
	private SignupDetails signupDetails = new SignupDetails();
	private static String serverHost = "fuckthepws.ddns.net";
	private static int serverPort = 26656;
	private static String id;
	// private ArrayList<String> inputData2 = new ArrayList<String>();

	/* variables for addUserGridItems() method */
	private BorderPane userScreenLayout;
	private Button btnLogOut, btnSearch, btnReset, btnLoadPres;
	private Label uFirstName, uSurName, uUserName;
	private TextField textFieldTitle, textFieldAuthor, textFieldLanguage;
	private Text messageLogOut, messageUser, response3;
	private String title, author, language;
	private int presentationIndex, toolTipIndex;
	private String presentationID;
	private PresentationShell loadPresentation = new PresentationShell();
	private ArrayList<String[]> searchResults = new ArrayList<String[]>(); // Define
																			// an
																			// arraylist
																			// for
																			// the
																			// search
																			// results

	/* variables for the createPresentationMenu */
	private Presentation createdPres;
	private GridPane gridForCreation;
	private BorderPane createPresentationScreenLayout;
	private Button btnNext, btnCreate, btnOpenMediaDty;
	private Label titleLabel, languageLabel, mediaLanguage, mediaTranslation, startTime, endTime;
	private TextField titleField, languageField, startTimeField, endTimeField;
	private TextArea mediaLanguageText, mediaTranslationText;
	private VideoItem videoItem = new VideoItem();
	private AudioItem audioItem = new AudioItem();
	private ArrayList<SlideItem> xmlSlideList = new ArrayList<SlideItem>();
	private SlideItem xmlSlide = new SlideItem();
	private FileChooser browseMediaFiles = new FileChooser();
	
	
	//private File selectedMediaFile;
	private String mediaPathname;
	private boolean containsVideo = false;
	private boolean containsAudio = false;
	private String titleCreated;
	private MediaFx videoPlayer;
	private AnchorPane anchorForMedia;

	/* variables for the commentsMenu */
	private BorderPane commentsScreenLayout;
	private Text messageRating, messageSubmit;
	private Button btnLike, btnDislike, btnSubmit, btnGoBackToPres;
	private int rating = 0;
	private String writtenComments;
	private TextArea commentsToWrite = new TextArea();
	private ArrayList<String> commentsList = new ArrayList<String>();
	private ArrayList<String[]> commentResults = new ArrayList<String[]>();

	/* variables for the Scroll Pane */
	private ScrollPane scrollPane = new ScrollPane();

	/* variables for presentation scene */
	private SlideHandler sh = new SlideHandler();
	private String filename1, filename2, filename3, xmlPathname, parsingFileName;
	private Presentation tempPres = new Presentation();
	private XMLParser parser;
	private HBox buttonControls;
	private BorderPane presentationLayout;
	private Pagination pagination;
	private double stackWidth;
	private double stackHeight;
	private StackPane slidePane = new StackPane();

	/* variables for menuItems() method */
	private MenuBar menuBar;
	private ImageView myImage = new ImageView();
	private File selectedFile, oldSelected;
	private FileChooser browseFiles = new FileChooser(); // For browsing files

	/* variables for openFile() method */
	private Desktop desktop = Desktop.getDesktop();
	private boolean delete;
	private Integer presentationIdOld;

	/* variables for createPage() method */
	// private AnchorPane pageBox;
	// private Canvas canvas;
	// private Canvas canvasTest;
	// private GraphicsContext gc;
	// private Image[] images = createContent();

	/* variables for createContent() method */
	// private ImageView image;
	// private boolean x = false;

	// Constructor
	public MainGuiPagination() {

	}

	public static void main(String[] args) {
		// Required for JavaFX to run
		launch(args);
	}

	// Override the init() method
	@Override
	public void init() {
		System.out.println("Setting up/initialising GUI now");
		Zipper.makeFolder("temp/");
		com = new ServerRequestHandler(serverPort, serverHost);
		com.start();

	}

	// Required method to run the JavaFX code
	@Override
	public void start(Stage primaryStage) throws IOException {
		initialiseGUI(primaryStage);
	}

	// Override the stop() method
	@Override
	public void stop() {
		Zipper.deleteFolder("temp" + File.separator);
		com.stop();
		System.out.println("Stopping/Closing GUI Now!");
		System.exit(0);
	}

	/* Method which initialises and creates all the GUI */
	private boolean initialiseGUI(Stage stage) throws IOException {

		// Assign window variable as the primary stage
		window = stage;

		window.getIcons().add(new Image("files/icon.png"));

		// Create menu bar objects ready to add to the Scenes - Crashes if
		// trying to add same one
		// MenuBar mainMenuBar = menuItems(); // Main Menu
		// MenuBar loginMenuBar = menuItems(); // Login Menu
		// MenuBar signupMenuBar = menuItems(); // Sign Up Menu
		// MenuBar userScreenMenuBar = menuItems(); // User Screen Menu
		// MenuBar presentationCreateMenuBar = menuItems(); // Presentation Menu
		// MenuBar presentationMenuBar = menuItems(); // Presentation Menu
		// MenuBar commentsMenuBar = menuItems(); // Presentation Menu

		/******************** Main Menu Screen ************************/

		// Create a root node called menuLayout which uses BorderPane
		BorderPane menuLayout = new BorderPane();

		showCommentsMenuBar = false;
		showCreatePresMenuBar = false;
		showUserMenuBar = false;

		menuLayout.setId("menuLayout"); // rootNode id for Main Menu Scene in
										// gui_style.gui_style.css
		// Add the root node to the scene
		mainMenu = new Scene(menuLayout, width, height);

		// Load style.ccs from same directory to provide the styling for the
		// scenes
		menuLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// Create the grid items
		GridPane controls1 = addMainGridItems();

		// Add the menu and buttons to the root node
		MenuBar mainMenuBar = menuItems(); // Main Menu
		menuLayout.setTop(mainMenuBar);
		menuLayout.setCenter(controls1);

		// As Default, Display Main Menu at first
		window.setTitle("Main Menu");
		window.setScene(mainMenu);

		/**************************************************************/

		/*********************** Login Screen *************************/
		// Create a root node called loginLayout which uses BorderPane
		BorderPane loginLayout = new BorderPane();

		showCommentsMenuBar = false;
		showCreatePresMenuBar = false;
		showUserMenuBar = false;

		loginLayout.setId("loginLayout"); // rootNode id for LogIn Scene in
											// gui_style.css
		// Add the root node to the scene
		logInMenu = new Scene(loginLayout, width, height);

		// Load gui_style.ccs from same directory to provide the styling for the
		// scenes
		loginLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// ready to add to logInMenu scene
		GridPane controls2 = addLoginGridItems();

		// Add the menu and textfields and buttons to the root node
		// loginLayout.setTop(loginMenuBar);
		loginLayout.setCenter(controls2);

		/*************************************************************/

		/******************** Sign Up screen *************************/
		// Create a root node called loginLayout which uses BorderPane
		BorderPane signupLayout = new BorderPane();

		showCommentsMenuBar = false;
		showCreatePresMenuBar = false;
		showUserMenuBar = false;

		signupLayout.setId("signupLayout"); // rootNode id for Sign Up Scene in
											// gui_style.css

		// Add the root node to the scene
		signUpMenu = new Scene(signupLayout, width, height);

		// Load gui_style.ccs from same directory to provide the styling for the
		// scenes
		signupLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// ready to add to logInMenu scene
		GridPane controls3 = addSignupGridItems();

		// Add the menu, textfields and buttons to the root node
		// signupLayout.setTop(signupMenuBar);
		signupLayout.setCenter(controls3);

		/**************************************************************/

		/******************* User Screen ******************************/
		// Create a root node called loginLayout which uses BorderPane
		userScreenLayout = new BorderPane();

		showCommentsMenuBar = false;
		showCreatePresMenuBar = false;
		showUserMenuBar = true;

		userScreenLayout.setId("userScreenLayout"); // rootNode id for
													// Presentation Scene in
													// gui_style.css
		// Add the root node to the scene
		userScreenMenu = new Scene(userScreenLayout, width, height, Color.BLACK);
		// Load style.ccs from same directory to provide the styling for the
		// scenes
		userScreenLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// ready to add to userScreenMenu scene
		GridPane userMenu = addUserGridItems();
		// ScrollPane userMenuScroll = addUserScrollItems();
		Group searchResults = searchDetails();
		// Add menu bar to User screen
		MenuBar userScreenMenuBar = menuItems(); // User Screen Menu
		userScreenLayout.setTop(userScreenMenuBar);
		userScreenLayout.setLeft(userMenu);
		// userScreenLayout.setRight(searchDetails());

		/**************************************************************/

		/******************* Create Presentation Screen ***************/
		// Create a root node called loginLayout which uses BorderPane
		createPresentationScreenLayout = new BorderPane();

		showCommentsMenuBar = false;
		showCreatePresMenuBar = true;
		showUserMenuBar = true;

		createPresentationScreenLayout.setId("createPresentationScreenLayout"); 
		
		// Add the root node to the scene
		createPresentationMenu = new Scene(createPresentationScreenLayout, width, height, Color.BLACK);
		// Load style.ccs from same directory to provide the styling for the
		// scenes
		createPresentationScreenLayout.getStylesheets()
				.add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());
		//
		// // ready to add to userScreenMenu scene
		GridPane createMenu = addCreatePresentationGridItems();
		// //Group searchResults = searchDetails();
		// // Add menu bar to User screen

		MenuBar presentationCreateMenuBar = menuItems(); // Presentation Menu
		createPresentationScreenLayout.setTop(presentationCreateMenuBar);
		createPresentationScreenLayout.setCenter(createMenu);

		/**************************************************************/

		/******************* Presentation screen **********************/
		// Create a root node called loginLayout which uses BorderPane
		presentationLayout = new BorderPane();

		showCommentsMenuBar = true;
		showCreatePresMenuBar = false;
		showUserMenuBar = true;

		presentationLayout.setId("presentationLayout"); 

		// Add the root node to the scene
		presentationMenu = new Scene(presentationLayout, width, height, Color.BLACK);
		// Load style.ccs from same directory to provide the styling for the
		// scenes
		presentationLayout.getStylesheets().add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		// create a temporary presentation
		// tempPres = new Presentation();

		// Add menu bar to presentation screen
		MenuBar presentationMenuBar = menuItems(); // Presentation Menu
		presentationLayout.setTop(presentationMenuBar);

		/* FullScreen for presentation menu */
		presentationMenu.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.F)) {
					// x = true;
					window.setFullScreen(true);
				} else if (ke.getCode().equals(KeyCode.ESCAPE)) {
					// x = false;
					window.setFullScreen(false);
				}
			}
		});

		/*************************************************/

		/******************* Comments Screen ************************/
		// Create a root node called loginLayout which uses BorderPane
		commentsScreenLayout = new BorderPane();

		showCommentsMenuBar = false;
		showCreatePresMenuBar = false;
		showUserMenuBar = true;

		commentsScreenLayout.setId("commentsScreenLayout"); // rootNode id for
															// Presentation
															// Scene in
															// gui_style.css
		// Add the root node to the scene
		commentsMenu = new Scene(commentsScreenLayout, width, height, Color.BLACK);
		// Load style.ccs from same directory to provide the styling for the
		// scenes
		commentsScreenLayout.getStylesheets()
				.add(MainGuiPagination.class.getResource("gui_style.css").toExternalForm());

		GridPane commentsGrid = addCommentScreenGridItems();
		Group commentsResults = commentsDetails();
		TextArea inputComments = commentsEdit();

		VBox vBox = new VBox();
		vBox.getChildren().addAll(commentsResults, inputComments);

		// Add menu bar to User screen
		MenuBar commentsMenuBar = menuItems(); // Presentation Menu

		commentsScreenLayout.setTop(commentsMenuBar);
		commentsScreenLayout.setLeft(commentsGrid);
		commentsScreenLayout.setRight(vBox);

		/**************************************************************/

		// Set all GUI Visibility to true
		window.show();

		return true;
	}

	/*
	 * Method which will add the panes (each slide) to the presentation screen
	 */
	// private StackPane presentationCanvas() throws IOException {
	//
	// Image img = new
	// Image(getClass().getResource("animal1.jpg").openStream());
	// Canvas canvas = new Canvas(presentationLayout.getWidth(),
	// presentationLayout.getHeight());
	// GraphicsContext gc = canvas.getGraphicsContext2D();
	// // Draw the image to the canvas
	// gc.drawImage(img, 0, 0, presentationLayout.getWidth(),
	// presentationLayout.getHeight());
	//
	// /**** Binding and Resize attributes to the canvas ****/
	// canvas.widthProperty().bind(presentationLayout.widthProperty());
	// canvas.heightProperty().bind(presentationLayout.heightProperty());
	//
	// final ResizeChangeListener resizeChangeListener = new
	// ResizeChangeListener(presentationLayout, gc, img);
	//
	// canvas.widthProperty().addListener(resizeChangeListener);
	// canvas.heightProperty().addListener(resizeChangeListener);
	//
	// /******************************************************/
	//
	// // Make a new root node
	// StackPane pane = new StackPane();
	// // Add the canvas to the root node
	// pane.getChildren().add(canvas);
	//
	// return pane;
	// }

	/* Method which will create extra controls for the presentation screen */
	// private HBox controls() {
	// HBox controls = new HBox(8); // Spacing of 8
	//
	// Button next = new Button("Next");
	// next.setPrefSize(100, 50);
	// next.setId("Next"); // String ID for gui_style.css
	//
	// Button previous = new Button("Previous");
	// previous.setPrefSize(100, 50);
	// previous.setId("Previous"); // String ID for gui_style.css
	//
	// // Add the buttons to the HBox
	// controls.getChildren().addAll(next, previous);
	//
	// // Event handler for the Next Button
	// next.setOnAction(new EventHandler<ActionEvent>() {
	// @Override
	// public void handle(ActionEvent e) {
	// // Increase PageIndex by 1
	// pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() + 1);
	// }
	// });
	//
	// // Event handler for the Previous Button
	// previous.setOnAction(new EventHandler<ActionEvent>() {
	// @Override
	// public void handle(ActionEvent e) {
	// // Decrease PageIndex by 1
	// pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() - 1);
	// }
	// });
	//
	// return controls;
	// }

	/* Method to add menu items and buttons for all GUIs */
	public MenuBar menuItems() {
		// Instantiate the menu bar
		menuBar = new MenuBar();

		// File Menu \\
		Menu fileMenu = new Menu("File");

		// fileMenu.getItems().add(new SeparatorMenuItem());
		MenuItem settings = new MenuItem("Settings...");

		// MenuItem goToPresentation = new MenuItem("Go To Presentation...");

		/*
		 * // Go to presentation goToPresentation.setOnAction(new
		 * EventHandler<ActionEvent>() { public void handle(ActionEvent e) { //
		 * Parsing of the pws xml file parser = new XMLParser();
		 * //parser.parseXML("PWS/pwsTest.xml"); parser.parseXML(newFileName);
		 * tempPres = parser.getPresentation(); // Creates Pagination Layout
		 * pagination = new Pagination(tempPres.getSlides().size(), 0);
		 * pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET); //
		 * Create the pagination page pagination.setPageFactory(new
		 * Callback<Integer, Node>() {
		 * 
		 * @Override public Node call(Integer pageIndex) { try { return
		 * sh.getSlideStack(tempPres, pageIndex, width-200, height-150); } catch
		 * (IOException e) { return null; } }});
		 * presentationLayout.setCenter(pagination);
		 * window.setTitle("Presentation"); window.setScene(presentationMenu); }
		 * });
		 */

		MenuItem goToUserScreen = new MenuItem("Go To User Screen...");

		// Go to user screen without network comms for testing
		goToUserScreen.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				tempPres = null;
				System.out.println("Presentation screen is now cleared");
				window.setTitle("User Menu");
				window.setScene(userScreenMenu);
			}

		});

		// Go back to main menu
		MenuItem goBack = new MenuItem("Go Back To Main Menu...");

		goBack.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				tempPres = null;
				System.out.println("Presentation screen is now cleared");
				window.setTitle("Main Menu");
				window.setScene(mainMenu);
			}

		});

		// fileMenu.getItems().add(new SeparatorMenuItem());

		// Close System
		exit = new MenuItem("Exit...");

		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				
				Zipper.deleteFolder("temp" + File.separator);
				
				com.logoutFromServer();
				com.stop();
				System.exit(0);
			}

		});

		// Add all File Menu Items to File Bar
		fileMenu.getItems().addAll(settings, goToUserScreen, goBack, new SeparatorMenuItem(), exit);

		// Presentation Menu \\
		Menu presentationMenu = new Menu("Presentation");

		MenuItem openFile = new MenuItem("Open...");

		// Event handler for Browsing A File
		openFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Please select a file to open...");
				
				destroyVid();
				
				// Set extension filters
				FileChooser.ExtensionFilter extFilterXML = new FileChooser.ExtensionFilter("PWS files (*.XML)",
						"*.XML");
				FileChooser.ExtensionFilter extFilterxml = new FileChooser.ExtensionFilter("pws files (*.xml)",
						"*.xml");

				// Add extension files to the file chooser
				browseFiles.getExtensionFilters().addAll(extFilterxml, extFilterXML);

				// Assign a File object as the file chooser - open the system
				// dialogue
				selectedFile = browseFiles.showOpenDialog(window);

				// Open the PWS selected xml file and change the scene to
				// presentation scene
				// with a pagination layout
				openSelectedFile(selectedFile);
			}
		});

		MenuItem createPresentation = new MenuItem("Create Presentation...");

		// Event handler for Browsing A File
		createPresentation.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Please create a presentation...");
				tempPres = null;
				//createdPres = null;
				window.setTitle("Create Presentation Menu");
				window.setScene(createPresentationMenu);
			}
		});

		// Add all File Menu Items to File Bar
		presentationMenu.getItems().addAll(openFile, new SeparatorMenuItem(), createPresentation);

		// Help Menu \\
		Menu comments = new Menu("End and go to Rating");

		// for debugging purposes
		MenuItem commentsScreen = new MenuItem("Go to comments and rating menu...");

		// Event handler for Browsing A File
		commentsScreen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				// /************* Client/Server Communication ***************/
				// commentResults =
				// com.searchForPresentation(presentationShell);
				// /*********************************************************/
				//
				// int x = 0;
				//
				// for (int i = 0; i < commentResults.size(); i++)
				// {
				// for(x = 0; x < commentResults.get(i).length; x++)
				// {
				// if (x == 0)
				// {
				// commentsList.add("Title: " + commentResults.get(i)[x] +
				// "\n");
				// }
				// else if (x == 1)
				// {
				// commentsList.add(commentsList.get(i) + "Author: " +
				// commentResults.get(i)[x]+ "\n");
				// commentsList.remove(i);
				// }
				// else if (x == 2)
				// {
				// commentsList.add(commentsList.get(i) + "Language: " +
				// commentResults.get(i)[x]);
				// commentsList.remove(i);
				// }
				// }
				// }
				//
				// // userScreenLayout.setRight(searchDetails());
				// System.out.println(commentsList);
				//
				// observableListSearch =
				// FXCollections.observableList(commentsList);

				tempPres = null;
				window.setTitle("Comments Menu");
				window.setScene(commentsMenu);
			}
		});

		comments.getItems().add(commentsScreen);

		// Help Menu \\
		Menu createPresScreen = new Menu("Go Back");

		// for debugging purposes
		MenuItem createPres = new MenuItem("Go to homepage...");

		// Event handler for Browsing A File
		createPres.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				//createPresentationMenu
				destroyVid();
				window.setTitle("User Screen Menu");
				window.setScene(userScreenMenu);
			}
		});

		createPresScreen.getItems().add(createPres);

		if (showCommentsMenuBar == true) {
			// Add All Menu Bar Items to the actual Menu Bar
			menuBar.getMenus().addAll(fileMenu, presentationMenu, comments);
		} else if (showCreatePresMenuBar == true) {
			// Add All Menu Bar Items to the actual Menu Bar
			menuBar.getMenus().addAll(fileMenu, presentationMenu, createPresScreen);
		} else if (showUserMenuBar == true) {
			// Add All Menu Bar Items to the actual Menu Bar
			menuBar.getMenus().addAll(fileMenu, presentationMenu);
		} else {
			menuBar.getMenus().addAll(fileMenu);
		}

		return menuBar;
	}

	// Method for opening a file in the browser with normal OS procedure
	/*
	 * private void openFile(File file) { try { desktop.open(file); } catch
	 * (IOException ex) {
	 * Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex); }
	 * }
	 */

	/* Method for creating the Pagination Page count and indexes */
	private void pageTurn() {
		// Create the pagination pages
		pagination.setPageFactory(new Callback<Integer, Node>() {
			@Override
			public Node call(Integer pageIndex) {
				try {
					// TODO stopping the media (it still plays when you close the pres too
						sh.stop();
						System.out.println("media Stopped");
						
					if ((pagination.getCurrentPageIndex() + 1) == pagination.getPageCount()) {
						System.out.println("You have reached the end of the presentation");
					}
					
					slidePane = sh.getSlideStack(tempPres, pageIndex, width, height - 100, presentationMenu);

					/* Mouse event handler for the canvas */
					slidePane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
						// Add mouse event handler to the images part of the
						// pagination
						@Override
						public void handle(MouseEvent mouseEvent) {
							if (mouseEvent.isPrimaryButtonDown()) {
								sh.stop();
								pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() + 1);
							}
							if (mouseEvent.isSecondaryButtonDown()) {
								sh.stop();
								pagination.setCurrentPageIndex(pagination.getCurrentPageIndex() - 1);
							}

							mouseEvent.consume();
						}
					});

					return slidePane;

				} catch (IOException e) {
					return null;
				}
			}
		});
	}

	/*
	 * Method for selecting a PWS xml file and if not null, return the string
	 * name of the xml file and pass it into the parser Local BROWSING
	 */
	private File openSelectedFile(File xmlFile) {

		if (xmlFile != null) {
//
//			// Temporary folder clearing
//
//			if (oldSelected == null) {
//				delete = false;
//			} else if (xmlFile.getAbsolutePath() != oldSelected.getAbsolutePath()) {
//				System.out.println(oldSelected.getAbsolutePath());
//				System.out.println(xmlFile.getAbsolutePath());
//				delete = true;
//			} else {
//				System.out.println(oldSelected.getAbsolutePath());
//				System.out.println(xmlFile.getAbsolutePath());
//				delete = false;
//			}
//
//			if (delete == true) {
//				if (oldSelected.getParent().contains("temp") && (presentationIdOld != null)) {
//					Zipper.deleteFolder("temp" + File.separator + presentationIdOld);
//					System.out.println("DELETED");
//					delete = false;
//				}
//			}

			window.setTitle("Presentation");
			// Change scene to presentationMenu
			window.setScene(presentationMenu);

			// filename1 = new String("PWS/");

			// filename1 = xmlFile.getParent(); // get the directory
			// filename2 = new String("/");
			// filename3 = xmlFile.getName(); // get the filename
			xmlPathname = xmlFile.getAbsolutePath();
			// parsingFileName = filename1 + filename2 + filename3; //
			// concatenate full path
			parsingFileName = xmlPathname;
			// display the details
			System.out.println("File selected: " + parsingFileName);

			// Parse the pws xml file
			parser = new XMLParser();
			// parser.parseXML("PWS/pwsTest.xml");
			parser.parseXML(parsingFileName);
			tempPres = parser.getPresentation();

			// Creates Pagination Layout
			pagination = new Pagination(tempPres.getSlides().size(), 0);
			System.out.println("Pagination Page Count is: " + pagination.getPageCount());
			// Setting the style of the pagination
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);

			pageTurn();
			System.out.println("Current Page Index: " + pagination.getCurrentPageIndex());

			// Add the pagination to the presentation scene
			presentationLayout.setCenter(pagination);
			window.setTitle("Presentation");
			window.setScene(presentationMenu);

			oldSelected = xmlFile;

		} else {
			System.out.println("File selection cancelled!");
		}

		return xmlFile;
	}

	/*
	 * Method for selecting a PWS xml file and if not null, return the string
	 * name of the xml file and pass it into the parser
	 */
	private File openSelectedMediaFile(File mediaFile) {

		if (mediaFile != null) {
			if ((mediaFile.getName().endsWith(".mp4")) || (mediaFile.getName().endsWith(".MP4"))) {
				mediaPathname = mediaFile.getAbsolutePath();
				System.out.println("File selected: " + mediaPathname);

				videoItem.setSourceFile(mediaPathname);

				videoItem.setxStart(0.6);
				videoItem.setyStart(0.1);

				videoItem.setLoop(false);
				videoItem.setStartTime(0);

				containsVideo = true;
				containsAudio = false;

				// Add the pagination to the presentation scene
				window.setTitle("Create Presentation Menu");
				// Change scene to presentationMenu
				window.setScene(createPresentationMenu);

			}

			else if ((mediaFile.getName().endsWith(".wav")) || (mediaFile.getName().endsWith(".WAV"))
					|| (mediaFile.getName().endsWith(".mp3")) || (mediaFile.getName().endsWith(".MP3"))) {
				mediaPathname = mediaFile.getAbsolutePath();
				System.out.println("File selected: " + mediaPathname);

				audioItem.setSourceFile(mediaPathname);
				// TODO Add these to audio handler
				/*
				 * might not need these audioItem.setxStart(0.5);
				 * audioItem.setyStart(0.5); audioItem.setHeight(height);
				 * audioItem.setWidth(stackWidth);
				 */

				audioItem.setLoop(false);
				audioItem.setStartTime(0);

				containsVideo = false;
				containsAudio = true;

				// Add the pagination to the presentation scene
				window.setTitle("Create Presentation Menu");
				// Change scene to presentationMenu
				window.setScene(createPresentationMenu);

			} else {
				System.out.println("You have not selected a suitable file!");
			}
		} else {
			System.out.println("File selection cancelled!");
		}

		return mediaFile;
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
	public Image[] createContent() {
		Image[] images = new Image[3];

		// Images for our pages
		for (int i = 0; i < 3; i++) {
			images[i] = new Image(MainGuiPagination.class.getResource("animal" + (i + 1) + ".jpg").toExternalForm(),
					false);
		}

		return images;
	}

	/* Method for GridPane for Main Menu */
	public GridPane addMainGridItems() {
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
		// hbox.setStyle("-fx-background-color: #336699;");

		// Create Sign Up and Login buttons
		signUp = new Button("Sign Up");
		signUp.setPrefSize(150, 50);
		signUp.setId("signUp"); // id for gui_style.css file
		// signUp.setStyle("-fx-font: 22 arial; -fx-base: #BAE98A;");

		logIn = new Button("Login");
		logIn.setPrefSize(150, 50);
		logIn.setId("logIn"); // id for gui_style.css file
		// logIn.setStyle("-fx-font: 22 arial; -fx-base: #BAE98A;");

		/*
		 * Button presentation = new Button("Presentation");
		 * presentation.setPrefSize(150, 50);
		 * presentation.setId("presentation"); // id for gui_style.css file
		 */

		// Adding buttons to the hbox
		hbox.getChildren().addAll(signUp, logIn);
		// hbox.getChildren().addAll(signUp, logIn, presentation);

		// Adding hbox with the buttons in it to the rootNode
		grid.add(hbox, 1, 1);

		// Event handler for SignUp Button
		signUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				window.setTitle("Sign Up");
				window.setScene(signUpMenu);
			}
		});

		// Event handler for Login Button
		logIn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				window.setTitle("Login");
				window.setScene(logInMenu);
			}
		});

		// Event handler for presentation Button
		/*
		 * presentation.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent e) { window.setTitle(
		 * "Presentation Example"); window.setScene(presentationMenu); } });
		 */

		return grid;
	}

	/* Method for GridPane items for login Menu */
	public GridPane addLoginGridItems() {
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
		// messageLogIn.setFill(Color.DARKCYAN);
		// messageLogIn.setFont(Font.font("Arial", FontWeight.BOLD, 30));
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
		btnGoBack1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
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
		btnLogIn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				sUsernameLogin = textFieldName.getText();
				sPasswordLogin = textFieldPassword1.getText();

				// Create new LoginDetails class
				// LoginDetails loginDetails = new LoginDetails();

				// Check if any of the textfields are null
				if (sUsernameLogin.equals("") || sPasswordLogin.equals("")) {
					response1.setText("Textfields are empty!");
					response1.setFill(Color.RED);
				}

				// Store the data and send to mysql database to check validity
				else {
					// Set the username and password fields in local
					// LoginDetails class
					user.setUsername(sUsernameLogin);
					user.setPassword(sPasswordLogin);

					response1.setText("Logging in, please wait");
					response1.setFill(Color.BLACK);

					/***** Client/Server Communication *****/
					boolean loginSuccessful = com.loginToServer(user);
					System.out.println("User login was: " + loginSuccessful);
					/**************************************/

					if (loginSuccessful == true) {
						logout = false;
						response1.setText("");
						window.setTitle("User Menu Screen");
						window.setScene(userScreenMenu);
					} else {
						window.setTitle("Login Screen");
						window.setScene(logInMenu);
						logout = true;
						response1.setText("Error in input, please try again!");
						response1.setFill(Color.RED);
						textFieldUsername.clear();
						textFieldPassword1.clear();
					}

				}
			}
		});

		// Event handler to get text from the text field
		// when button is pressed.
		textFieldPassword1.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					sUsernameLogin = textFieldName.getText();
					sPasswordLogin = textFieldPassword1.getText();

					// Create new LoginDetails class
					// LoginDetails loginDetails = new LoginDetails();

					// Check if any of the textfields are null
					if (sUsernameLogin.equals("") || sPasswordLogin.equals("")) {
						response1.setText("Textfields are empty!");
						response1.setFill(Color.RED);
					}

					// Store the data and send to mysql database to check
					// validity
					else {
						// Set the username and password fields in local
						// LoginDetails class
						user.setUsername(sUsernameLogin);
						user.setPassword(sPasswordLogin);

						response1.setText("Logging in, please wait");
						response1.setFill(Color.BLACK);

						/***** Client/Server Communication *****/
						boolean loginSuccessful = com.loginToServer(user);
						System.out.println("User login was: " + loginSuccessful);
						/**************************************/

						if (loginSuccessful == true) {
							logout = false;
							response1.setText("");
							window.setTitle("User Menu Screen");
							window.setScene(userScreenMenu);
						} else {
							window.setTitle("Login Screen");
							window.setScene(logInMenu);
							logout = true;
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
	public GridPane addSignupGridItems() {

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
		// messageSignUp.setFill(Color.DARKCYAN);
		// messageSignUp.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		grid.add(messageSignUp, 0, 0, 2, 1);

		// Create the labels for First Name, Surname Name, email,
		// username and password, etc..and adding to the rootNode

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

		final DatePicker datePicker = new DatePicker(LocalDate.now());
		datePicker.setOnAction(event -> {
			sDateOfBirth = datePicker.getValue();
			System.out.println("Selected date: " + sDateOfBirth);
		});

		// textFieldDateOfBirth = new TextField();
		// textFieldDateOfBirth.setPromptText("Enter Date of Birth");
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
		btnGoBack2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
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
		btnRegister.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				sEmail = textFieldEmail.getText();
				sUsername = textFieldUsername.getText();
				sPassword = textFieldPassword2.getText();

				// Check if any of the textfields are null
				if (sDateOfBirth.equals("") || sEmail.equals("") || sUsername.equals("") || sPassword.equals("")) {
					response2.setText("Textfields are empty!");
					response2.setFill(Color.RED);
				} else {
					// Set the username and password fields in local
					// SignUpDetails class
					user.setUsername(sUsername);
					user.setPassword(sPassword);
					user.setEmail(sEmail);
					user.setDob(sDateOfBirth);
				}

				/**** Client/Server Communication *****/
				String signUpSuccessful = com.signUp(user);
				/**************************************/
				if (signUpSuccessful == null) {
					System.out.println("Signup was successful");
					response2.setText("");
					logout = false;
					window.setTitle("Login Screen");
					window.setScene(logInMenu);
				} else {
					System.out.println(signUpSuccessful);
					logout = true;
					window.setTitle("Sign Up Screen");
					window.setScene(signUpMenu);
					response1.setText("Error in input, please try again!");
					response1.setFill(Color.RED);
					textFieldUsername.clear();
					textFieldPassword2.clear();
					textFieldEmail.clear();
				}

				// window.setTitle("User Screen Menu");
				// window.setScene(userScreenMenu);

				// System.out.println(inputData.get(0) + ", " +
				// inputData.get(1));
				// response2.setText("Registering with us, please wait");
				// response2.setFill(Color.BLACK);
			}

			// // Some input did not match, clear textfelds and try again
			// else
			// {
			// response2.setText("Error in input, please try again!");
			// response2.setFill(Color.RED);
			// textFieldEmail.clear();
			// textFieldConfirmEmail.clear();
			// textFieldPassword2.clear();
			// textFieldConfirmPassword.clear();
			// }

		});

		return grid;
	}

	/* Method for GridPane items for User Screen Menu */
	public GridPane addUserGridItems() {

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
		messageUser = new Text("Explore Presentations");
		messageUser.setId("messageUser"); // Id for gui_style.css
		messageUser.setFill(Color.ALICEBLUE);
		messageUser.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		grid.add(messageUser, 0, 0, 2, 1);

		textFieldTitle = new TextField();
		textFieldTitle.setPromptText("Search by Title");
		grid.add(textFieldTitle, 1, 1);
		textFieldAuthor = new TextField();
		textFieldAuthor.setPromptText("Search by Author");
		grid.add(textFieldAuthor, 1, 2);
		textFieldLanguage = new TextField();
		textFieldLanguage.setPromptText("Search by Language");
		grid.add(textFieldLanguage, 1, 3);

		textFieldLanguage.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					System.out.println("HELLO");
				}
			}
		});

		// Creating a Button for Registering and going back to main menu
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

		// Adding hbArea with the button in it to the rootNode
		grid.add(hbArea, 1, 9);

		// Add a response after pressing the button
		response3 = new Text();
		grid.add(response3, 1, 4);

		// Event handler for btnLogout
		btnLogOut.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				logout = true;
				com.logoutFromServer();
				textFieldName.clear();
				textFieldPassword1.clear();
				window.setTitle("Main Menu");
				window.setScene(mainMenu);
				response3.setText("");
			}
		});

		// Event handler for btnSearch
		btnSearch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				title = textFieldTitle.getText();
				author = textFieldAuthor.getText();
				language = textFieldLanguage.getText();

				// Set the username and password fields in local SignUpDetails
				// class
				presentationShell.setTitle(title);
				presentationShell.setAuthor(author);
				presentationShell.setLanguage(language);

				/************* Client/Server Communication ***************/
				searchResults = com.searchForPresentation(presentationShell);
				/*********************************************************/

				response3.setText("Searching for results");
				response3.setFill(Color.ALICEBLUE);

				// ArrayList<String> searchList = new ArrayList<String>();
				searchList.clear();
				idList.clear();

				for (int i = 0; i < searchResults.size(); i++) {
					for (int x = 0; x < searchResults.get(i).length; x++) {
						if (x == 0) {
							idList.add(searchResults.get(i)[x]);
						} else if (x == 1) {
							searchList.add("Title: " + searchResults.get(i)[x] + "\n");
						} else if (x == 2) {
							searchList.add(searchList.get(i) + "Author: " + searchResults.get(i)[x] + "\n");
							searchList.remove(i);
						} else if (x == 3) {
							searchList.add(searchList.get(i) + "Language: " + searchResults.get(i)[x]);
							searchList.remove(i);
						}
					}
				}

				// userScreenLayout.setRight(searchDetails());
				System.out.println(searchList);

				observableListSearch = FXCollections.observableList(searchList);

				for (int i = 0; i < searchResults.size(); i++) {
					System.out.print("Presentation " + (i + 1) + " is: ");
					for (int j = 0; j < 4; j++) {
						switch (j) {
						case 0:
							System.out.print(" '" + searchResults.get(i)[j] + "' ");
							// listView.setItems(searchResults.get(i)[j]);
							break;
						case 1:
							System.out.print("by " + searchResults.get(i)[j] + " ");
							break;
						case 2:
							System.out.println(" (" + searchResults.get(i)[j] + ") ");
							break;
						case 3:
							System.out.println(" (" + searchResults.get(i)[j] + ") ");
							break;
						}
					}
				}

				searchView.setVisible(true);
				userScreenLayout.setRight(searchDetails());
			}

		});

		textFieldTitle.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {

					title = textFieldTitle.getText();
					author = textFieldAuthor.getText();
					language = textFieldLanguage.getText();

					// Set the username and password fields in local
					// SignUpDetails class
					presentationShell.setTitle(title);
					presentationShell.setAuthor(author);
					presentationShell.setLanguage(language);

					/************* Client/Server Communication ***************/
					searchResults = com.searchForPresentation(presentationShell);
					/*********************************************************/

					response3.setText("Searching for results");
					response3.setFill(Color.ALICEBLUE);

					// ArrayList<String> searchList = new ArrayList<String>();
					searchList.clear();
					idList.clear();

					for (int i = 0; i < searchResults.size(); i++) {
						for (int x = 0; x < searchResults.get(i).length; x++) {
							if (x == 0) {
								idList.add(searchResults.get(i)[x]);
							} else if (x == 1) {
								searchList.add("Title: " + searchResults.get(i)[x] + "\n");
							} else if (x == 2) {
								searchList.add(searchList.get(i) + "Author: " + searchResults.get(i)[x] + "\n");
								searchList.remove(i);
							} else if (x == 3) {
								searchList.add(searchList.get(i) + "Language: " + searchResults.get(i)[x]);
								searchList.remove(i);
							}
						}
					}

					// userScreenLayout.setRight(searchDetails());
					System.out.println(searchList);

					observableListSearch = FXCollections.observableList(searchList);

					for (int i = 0; i < searchResults.size(); i++) {
						System.out.print("Presentation " + (i + 1) + " is: ");
						for (int j = 0; j < 4; j++) {
							switch (j) {
							case 0:
								System.out.print(" '" + searchResults.get(i)[j] + "' ");
								// listView.setItems(searchResults.get(i)[j]);
								break;
							case 1:
								System.out.print("by " + searchResults.get(i)[j] + " ");
								break;
							case 2:
								System.out.println(" (" + searchResults.get(i)[j] + ") ");
								break;
							case 3:
								System.out.println(" (" + searchResults.get(i)[j] + ") ");
								break;
							}
						}
					}

					searchView.setVisible(true);
					userScreenLayout.setRight(searchDetails());
				}
			}

		});

		textFieldAuthor.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {

					title = textFieldTitle.getText();
					author = textFieldAuthor.getText();
					language = textFieldLanguage.getText();

					// Set the username and password fields in local
					// SignUpDetails class
					presentationShell.setTitle(title);
					presentationShell.setAuthor(author);
					presentationShell.setLanguage(language);

					/************* Client/Server Communication ***************/
					searchResults = com.searchForPresentation(presentationShell);
					/*********************************************************/

					response3.setText("Searching for results");
					response3.setFill(Color.ALICEBLUE);

					// ArrayList<String> searchList = new ArrayList<String>();
					searchList.clear();
					idList.clear();

					for (int i = 0; i < searchResults.size(); i++) {
						for (int x = 0; x < searchResults.get(i).length; x++) {
							if (x == 0) {
								idList.add(searchResults.get(i)[x]);
							} else if (x == 1) {
								searchList.add("Title: " + searchResults.get(i)[x] + "\n");
							} else if (x == 2) {
								searchList.add(searchList.get(i) + "Author: " + searchResults.get(i)[x] + "\n");
								searchList.remove(i);
							} else if (x == 3) {
								searchList.add(searchList.get(i) + "Language: " + searchResults.get(i)[x]);
								searchList.remove(i);
							}
						}
					}

					// userScreenLayout.setRight(searchDetails());
					System.out.println(searchList);

					observableListSearch = FXCollections.observableList(searchList);

					for (int i = 0; i < searchResults.size(); i++) {
						System.out.print("Presentation " + (i + 1) + " is: ");
						for (int j = 0; j < 4; j++) {
							switch (j) {
							case 0:
								System.out.print(" '" + searchResults.get(i)[j] + "' ");
								// listView.setItems(searchResults.get(i)[j]);
								break;
							case 1:
								System.out.print("by " + searchResults.get(i)[j] + " ");
								break;
							case 2:
								System.out.println(" (" + searchResults.get(i)[j] + ") ");
								break;
							case 3:
								System.out.println(" (" + searchResults.get(i)[j] + ") ");
								break;
							}
						}
					}

					searchView.setVisible(true);
					userScreenLayout.setRight(searchDetails());
				}
			}

		});

		textFieldLanguage.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {

					title = textFieldTitle.getText();
					author = textFieldAuthor.getText();
					language = textFieldLanguage.getText();

					// Set the username and password fields in local
					// SignUpDetails class
					presentationShell.setTitle(title);
					presentationShell.setAuthor(author);
					presentationShell.setLanguage(language);

					/************* Client/Server Communication ***************/
					searchResults = com.searchForPresentation(presentationShell);
					/*********************************************************/

					response3.setText("Searching for results");
					response3.setFill(Color.ALICEBLUE);

					// ArrayList<String> searchList = new ArrayList<String>();
					searchList.clear();
					idList.clear();

					for (int i = 0; i < searchResults.size(); i++) {
						for (int x = 0; x < searchResults.get(i).length; x++) {
							if (x == 0) {
								idList.add(searchResults.get(i)[x]);
							} else if (x == 1) {
								searchList.add("Title: " + searchResults.get(i)[x] + "\n");
							} else if (x == 2) {
								searchList.add(searchList.get(i) + "Author: " + searchResults.get(i)[x] + "\n");
								searchList.remove(i);
							} else if (x == 3) {
								searchList.add(searchList.get(i) + "Language: " + searchResults.get(i)[x]);
								searchList.remove(i);
							}
						}
					}

					// userScreenLayout.setRight(searchDetails());
					System.out.println(searchList);

					observableListSearch = FXCollections.observableList(searchList);

					for (int i = 0; i < searchResults.size(); i++) {
						System.out.print("Presentation " + (i + 1) + " is: ");
						for (int j = 0; j < 4; j++) {
							switch (j) {
							case 0:
								System.out.print(" '" + searchResults.get(i)[j] + "' ");
								// listView.setItems(searchResults.get(i)[j]);
								break;
							case 1:
								System.out.print("by " + searchResults.get(i)[j] + " ");
								break;
							case 2:
								System.out.println(" (" + searchResults.get(i)[j] + ") ");
								break;
							case 3:
								System.out.println(" (" + searchResults.get(i)[j] + ") ");
								break;
							}
						}
					}

					searchView.setVisible(true);
					userScreenLayout.setRight(searchDetails());
				}
			}

		});

		// Event handler for btnLoadPres
		btnLoadPres.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				presentationIndex = searchView.getSelectionModel().getSelectedIndex();
				presentationID = idList.get(presentationIndex);
				System.out.println("Presentation ID is: " + presentationID);

				presentationLoad = new PresentationShell();

				presentationLoad.setId(Integer.parseInt(searchResults.get(presentationIndex)[0]));
				presentationLoad.setTitle(searchResults.get(presentationIndex)[1]);
				presentationLoad.setAuthor(searchResults.get(presentationIndex)[2]);
				presentationLoad.setLanguage(searchResults.get(presentationIndex)[3]);

				// TODO loadng downloaded pres
				File download = new File("temp" + File.separator + presentationLoad.getId().toString() + ".pws");
				if (!download.exists()) {

					/************* Client/Server Communication ***************/
					com.getPresentation(presentationLoad, "temp" + File.separator);
					/*********************************************************/
				}
				// Unzip file
				try {
					Zipper.deleteFolder("temp" + File.separator + "tempPres" + File.separator);
				} catch (Exception e1) {
					System.out.println("Unable to delete folder...");
					e1.printStackTrace();
				}
				try {
					Zipper.makeFolder("temp" + File.separator + "tempPres");
					
				}
				catch (Exception e1){
					System.out.println("Unable to create folder...");
				}
				try {
					System.out.println("temp" + File.separator + presentationLoad.getId().toString() + ".pws");
					Zipper.unzip(("temp" + File.separator + presentationLoad.getId().toString() + ".pws"),
							"temp");
				}
				catch (Exception e1){
					System.out.println("Unable to zip folder...");
					e1.printStackTrace();
				}

				xmlPathname = "temp" + File.separator + "presentation.xml";

				File xmlFile = new File("temp" + File.separator + "tempPres" + File.separator + "presentation.xml");
				selectedFile = xmlFile;

				openSelectedFile(selectedFile);
				// presentation ID Old for deleting temp folders
				presentationIdOld = presentationLoad.getId();
			}
		});

		// Event handler for btnReset
		btnReset.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				textFieldTitle.clear();
				textFieldAuthor.clear();
				textFieldLanguage.clear();
				searchList.clear();
				idList.clear();
				response3.setText("");
				userScreenLayout.setRight(searchDetails());
				searchView.setVisible(false);
			}
		});

		return grid;
	}

	public Group searchDetails() {
		Group listGroup = new Group();
		searchView.setId("listView");
		searchView.setPrefHeight(userScreenLayout.getHeight());
		searchView.setPrefWidth(userScreenLayout.getWidth() / 2);
		System.out.println(observableListSearch);
		searchView.setOpacity(0.7);

		final StringProperty hoveredItem = new SimpleStringProperty(null);

		searchView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> listView) {
				final ListCell<String> listCell = new ListCell<String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setText(null);
						} else {
							setText(item);
						}
					}
				};

				// or register a change listener with the hover property
				listCell.hoverProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						if (newValue) {
							hoveredItem.set(listCell.getItem());
							toolTipIndex = listCell.getIndex();
							if (toolTipIndex < searchResults.size()) {
								Tooltip t = new Tooltip("Rating: " + searchResults.get(toolTipIndex)[4]);
								t.getStyleClass().add("ttip");
								listCell.setTooltip(t);
							}

						} else {
							hoveredItem.set(null);
						}
					}
				});

				return listCell;
			}
		});

		searchView.setItems(observableListSearch);

		// searchView.getSelectionModel().getSelectedIndex();
		searchView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listGroup.getChildren().add(searchView);
		return listGroup;
	}

	/* Method for GridPane items for User Screen Menu */
	public GridPane addCreatePresentationGridItems() {

		// Create a root node called grid. In this case a grid pane layout
		// is used, with vertical and horizontal gaps of 10
		gridForCreation = new GridPane();
		gridForCreation.setHgap(10);
		gridForCreation.setVgap(10);
		
//		anchorForMedia = new AnchorPane();
//		gridForCreation.add(anchorForMedia, 1, 2);
		
		VBox vBox1 = new VBox(10);
		VBox vBox2 = new VBox(10);

		// Centre the controls in the scene
		gridForCreation.setAlignment(Pos.TOP_CENTER);

		// Add padding
		gridForCreation.setPadding(new Insets(25, 25, 25, 25));

		titleField = new TextField();
		titleField.setPrefSize(250, 10);
		titleField.setPromptText("Enter The Title of the Presentation");
		//grid.add(titleField, 0, 0);
		
		languageField = new TextField();
		languageField.setPrefSize(180, 10);
		languageField.setPromptText("Enter The Language Used");
		//grid.add(languageField, 1, 0);
		
//		titleLabel = new Label("Title:");
//		titleLabel.setId("titleLabel"); // Id for gui_style.css
//		// grid.add(startTime, 0, 2);
//		languageLabel = new Label("Language:");
//		languageLabel.setId("languageLabel"); // Id for gui_style.css
		
//		grid.add(titleField, 1, 0);
//		grid.add(languageField, 3, 0);
		
		// Create the Default message
		mediaLanguage = new Label("Video Language");
		// mediaLanguage.setTextFill(Color.web("#0076a3"));
		mediaLanguage.setId("mediaLanguage"); // Id for gui_style.css
		// videoLanguage.setFill(Color.ALICEBLUE);
		mediaLanguage.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		gridForCreation.add(mediaLanguage, 0, 1, 3, 1);

		mediaLanguageText = new TextArea();
		mediaLanguageText.setPromptText("Enter Video Language Text");
		mediaLanguageText.setPrefSize(100, 250);
		gridForCreation.add(mediaLanguageText, 0, 2);

		startTime = new Label("Start Time:");
		startTime.setId("startTime"); // Id for gui_style.css
		// grid.add(startTime, 0, 2);
		endTime = new Label("End Time:");
		endTime.setId("endTime"); // Id for gui_style.css
		// grid.add(endTime, 2, 2);

		startTimeField = new TextField();

		// force the field to be numeric only
		startTimeField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					startTimeField.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (startTimeField.getText().length() > 3) {
					String s = startTimeField.getText().substring(0, 3);
					startTimeField.setText(s);
				}
			}
		});

		startTimeField.setPromptText("Enter Start Time");
		// grid.add(startTimeField, 1, 2);

		endTimeField = new TextField();

		// force the field to be numeric only
		endTimeField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					endTimeField.setText(newValue.replaceAll("[^\\d]", ""));
				}
				if (endTimeField.getText().length() > 3) {
					String s = startTimeField.getText().substring(0, 3);
					endTimeField.setText(s);
				}
			}
		});

		endTimeField.setPromptText("Enter End Time");
		// grid.add(endTimeField, 3, 2);

		mediaTranslation = new Label("Video Translation");
		mediaTranslation.setId("mediaTranslation"); // Id for gui_style.css
		// videoLanguage.setFill(Color.ALICEBLUE);
		mediaTranslation.setFont(Font.font("Arial", FontWeight.BOLD, 30));
		gridForCreation.add(mediaTranslation, 0, 4, 3, 1);

		mediaTranslationText = new TextArea();
		mediaTranslationText.setPromptText("Enter Video Translation Text");
		mediaTranslationText.setPrefSize(100, 250);
		gridForCreation.add(mediaTranslationText, 0, 5);

		btnNext = new Button("Set ");
		btnNext.setId("btnNext");
		// grid.add(btnNext, 0, 5);
		btnOpenMediaDty = new Button("Add Media");
		btnOpenMediaDty.setId("btnOpenMediaDty");
		//btnOpenMediaDty.setPrefSize(200, 200);
		// grid.add(btnOpenVideoDty, 1, 5);
		btnCreate = new Button("Create");
		btnCreate.setId("btnCreate");
		// grid.add(btnCreate, 2, 5);

		vBox1.setAlignment(Pos.CENTER_LEFT);
//		hbFirst.getChildren().addAll(titleLabel, titleField, languageLabel, languageField);
		vBox1.getChildren().addAll(titleField, languageField);
		gridForCreation.add(vBox1, 0, 0);
		
		HBox hBox1 = new HBox(10);
		hBox1.setAlignment(Pos.CENTER_LEFT);
//		hbFirst.getChildren().addAll(titleLabel, titleField, languageLabel, languageField);
		hBox1.getChildren().addAll(startTime, startTimeField);
		
		// Creating a HBox area to add the labels and textfields
		HBox hBox2 = new HBox(10);
		hBox2.setAlignment(Pos.CENTER_RIGHT);
		hBox2.getChildren().addAll(endTime, endTimeField);

		// Creating a HBox area to add the labels and textfields
		HBox hBox3 = new HBox(10);
		hBox3.setAlignment(Pos.CENTER_LEFT);
		hBox3.getChildren().addAll(btnNext, btnCreate, btnOpenMediaDty);
		
		vBox2.setAlignment(Pos.CENTER_LEFT);
//		hbFirst.getChildren().addAll(titleLabel, titleField, languageLabel, languageField);
		vBox2.getChildren().addAll(hBox1, hBox2);
		gridForCreation.add(vBox2, 0, 3);

		//grid.add(btnOpenMediaDty, 1, 3);
		//grid.add(hBox1, 0, 3);
		//grid.add(hBox2, 1, 3);
		gridForCreation.add(hBox3, 0, 6);

		// Event handler for btnNext
		btnNext.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) 
			{
				if ((titleField.getText().isEmpty() || languageField.getText().isEmpty() || 
						startTimeField.getText().isEmpty() || endTimeField.getText().isEmpty() 
						|| mediaLanguageText.getText().isEmpty() || mediaTranslationText.getText().isEmpty())) 
				{
					// Do Nothing!!
					System.out.println("You have empty fields, please fill in");
				} 
				else 
				{
					titleField.getText();
					languageField.getText();
					mediaLanguageText.getText();
					mediaTranslationText.getText();
					startTimeField.getText();
					endTimeField.getText();

					System.out.println("Your title is: " + titleField.getText());
					System.out.println("Your language is: " + languageField.getText());
					System.out.println("Your video text is: " + mediaLanguageText.getText());
					System.out.println("Your translation text is: " + mediaTranslationText.getText());
					System.out.println("Start time is: " + startTimeField.getText());
					System.out.println("End time is: " + endTimeField.getText());

					TextItem videoText = new TextItem();
					TextItem transText = new TextItem();

					titleCreated = titleField.getText();

					videoText.setText(mediaLanguageText.getText());
					transText.setText(mediaTranslationText.getText());
					videoText.setStartTime(Integer.parseInt(startTimeField.getText()) * 1000);
					transText.setStartTime(Integer.parseInt(startTimeField.getText()) * 1000);
					videoText.setDuration(
							(Integer.parseInt(endTimeField.getText()) - (Integer.parseInt(startTimeField.getText())))
							* 1000);
					System.out.println("Video Text Duration: " + videoText.getDuration());
					transText.setDuration(
							(Integer.parseInt(endTimeField.getText()) - (Integer.parseInt(startTimeField.getText())))
							* 1000);
					System.out.println("Video TranslationText Duration: " + transText.getDuration());

					videoText.setHeight(0.3f);
					videoText.setWidth(0.4f);
					videoText.setxStart(0.1f);
					videoText.setyStart(0.1f);

					transText.setHeight(0.3f);
					transText.setWidth(0.4f);
					transText.setxStart(0.1f);
					transText.setyStart(0.6f);

					videoText.setFont("SansSerif");
					transText.setFont("SansSerif");

					xmlSlide.addText(videoText);
					xmlSlide.addText(transText);

					// xmlSlideList.add(xmlSlide);
					// Clear for next input
					mediaLanguageText.clear();
					mediaTranslationText.clear();
					startTimeField.clear();
					endTimeField.clear();

				}
				window.setTitle("Create Presentation Menu");
				window.setScene(createPresentationMenu);
			}
		});

		// Event handler for Browsing A File
		btnOpenMediaDty.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				System.out.println("Please select a media file to open...");

				// Set extension filters
				FileChooser.ExtensionFilter extFilterMP4 = new FileChooser.ExtensionFilter("MP4 files (*.MP4)",
						"*.MP4");
				FileChooser.ExtensionFilter extFiltermp4 = new FileChooser.ExtensionFilter("mp4 files (*.mp4)",
						"*.mp4");
				FileChooser.ExtensionFilter extFilterWAV = new FileChooser.ExtensionFilter("WAV files (*.WAV)",
						"*.WAV");
				FileChooser.ExtensionFilter extFilterwav = new FileChooser.ExtensionFilter("wav files (*.wav)",
						"*.wav");
				FileChooser.ExtensionFilter extFilterMP3 = new FileChooser.ExtensionFilter("MP3 files (*.MP3)",
						"*.MP3");
				FileChooser.ExtensionFilter extFiltermp3 = new FileChooser.ExtensionFilter("mp3 files (*.mp3)",
						"*.mp3");

				// Add extension files to the file chooser
				browseMediaFiles.getExtensionFilters().addAll(extFilterMP4, extFiltermp4, extFilterWAV, extFilterwav,
						extFilterMP3, extFiltermp3);
				
				

				// Assign a File object as the file chooser - open the system
				// dialogue
				File selectedMediaFile = browseMediaFiles.showOpenDialog(window);

				// Open the PWS selected xml file and change the scene to
				// presentation scene
				// with a pagination layout
				openSelectedMediaFile(selectedMediaFile);
				
				destroyVid();
				
				anchorForMedia = new AnchorPane();
				gridForCreation.add(anchorForMedia, 1, 2);
				
				VideoItem videoLoad = new VideoItem();
				videoLoad = videoItem;
				videoLoad.setxStart(0);
				videoLoad.setyStart(0);
				videoPlayer = new MediaFx(videoLoad, 0.3, 0.3);
				//AnchorPane anchor = new AnchorPane();
				Group group = new Group();
				//anchor.getChildren().add(group);
				group.getChildren().add(videoPlayer.createContent(createPresentationMenu));
				AnchorPane.setTopAnchor(group, 0.0);
				AnchorPane.setBottomAnchor(group, 1.0);
				AnchorPane.setLeftAnchor(group, 1.0);
				AnchorPane.setRightAnchor(group, 0.0);
				anchorForMedia.getChildren().add(group);
			}
		});
		//
		// Event handler for btnCreate
		btnCreate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				createdPres = new Presentation();

				XMLCreator creator = new XMLCreator(com);

				if (containsVideo == true) {
					xmlSlide.addVideo(videoItem);
				} else if (containsAudio == true) {
					xmlSlide.addAudio(audioItem);
				}

				FillPres fp = new FillPres();
				createdPres = fp.fillPresentation(createdPres, sUsernameLogin, xmlSlide, titleCreated);
				

				PresentationShell presShell = new PresentationShell();
				presShell.setAuthor(sUsernameLogin);
				presShell.setLanguage(languageField.getText());
				presShell.setTitle(titleField.getText());

				creator.createXML(createdPres, true, true, true, false, false, true, containsVideo, containsAudio, presShell);
				
				
				
			}
		});

		return gridForCreation;
	}

	/* Method for GridPane items for User Screen Menu */
	public GridPane addCommentScreenGridItems() {

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
		messageRating = new Text("Please give your rating");
		messageRating.setId("messageRating"); // Id for gui_style.css
		// messageRating.setFill(Color.color(0.443, 0.196, 1.0));
		
		messageRating.setFill(Color.ALICEBLUE);
		messageRating.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		grid.add(messageRating, 1, 0, 1, 1);

		// Creating buttons for rating up or down
		Image thumbsUp = new Image(getClass().getResourceAsStream("thumb_up.png"));
		btnLike = new Button("Like", new ImageView(thumbsUp));
		btnLike.setPrefSize(120, 50);
		btnLike.setId("btnLike");

		Image thumbsDown = new Image(getClass().getResourceAsStream("thumb_down.png"));
		btnDislike = new Button("Dislike", new ImageView(thumbsDown));
		btnDislike.setPrefSize(120, 50);
		btnDislike.setId("btnDislike");

		btnSubmit = new Button("Submit");
		btnSubmit.setPrefSize(120, 50);
		btnSubmit.setId("btnSubmit");

		btnGoBackToPres = new Button("Go Back");
		btnGoBackToPres.setPrefSize(120, 50);
		btnGoBackToPres.setId("btnGoBackToPres");

		// Creating a HBox area to add the buttons to
		VBox vbArea = new VBox(10);
		vbArea.setAlignment(Pos.CENTER);
		vbArea.getChildren().addAll(btnLike, btnDislike, btnSubmit, btnGoBackToPres);

		// Adding hbArea with the button in it to the rootNode
		grid.add(vbArea, 1, 5);

		// PerspectiveTransform pt = new PerspectiveTransform();

		Canvas backgroundCanvas = new Canvas((commentsMenu.getWidth() / 2) + 30, commentsMenu.getHeight() / 8);
		GraphicsContext gc = backgroundCanvas.getGraphicsContext2D();
		gc.setFill(Color.rgb(0, 0, 0, 0.35));
		gc.fillRect(0, 0, backgroundCanvas.getWidth(), backgroundCanvas.getHeight());

		messageSubmit = new Text("Hit submit to upload your ratings and comments!");
		messageSubmit.setId("messageSubmit"); // Id for gui_style.css
		messageSubmit.setFill(Color.ALICEBLUE);
		messageSubmit.setFont(Font.font("Arial", FontWeight.BOLD, 20));

		grid.add(backgroundCanvas, 0, 6, 2, 1);
		grid.add(messageSubmit, 0, 6, 2, 1);

		// Event handler for btnLogout
		btnLike.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				rating = 1;
				// Need to assign this rating value to some
				// kind of presentation object
			}
		});

		// Event handler for btnSearch
		btnDislike.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				rating = -1;
				// Need to assign this rating value to some
				// kind of presentation object
			}
		});

		// Event handler for btnLogout
		btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				writtenComments = commentsToWrite.getText();
				System.out.println(writtenComments);
				// Need to assign the text to the presentation
				// object and send that to the server
			}
		});

		// Event handler for btnLogout
		btnGoBackToPres.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				openSelectedFile(selectedFile);
			}
		});

		return grid;
	}

	/* Comments for the ratings page */
	public Group commentsDetails() {
		Group listGroup = new Group();
		commentsView.setId("listView");
		commentsView.setPrefHeight(commentsScreenLayout.getHeight() * 0.75);
		commentsView.setPrefWidth(commentsScreenLayout.getWidth() / 2);
		commentsView.setOpacity(0.7);
		// System.out.println(observableList);

		// searchView.getSelectionModel().getSelectedIndex();
		commentsView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// commentsView.setItems(observableListComments);

		listGroup.getChildren().add(commentsView);
		return listGroup;
	}

	/* Comments to write for the ratings page */
	public TextArea commentsEdit() {
		// commentsToWrite = new TextArea();
		commentsToWrite.setPromptText("Please add your comments");
		commentsToWrite.setMinHeight(commentsScreenLayout.getHeight() / 4);
		commentsToWrite.setMinWidth(commentsScreenLayout.getWidth() / 2);
		// commentsToWrite.setPrefHeight(commentsScreenLayout.getHeight()/2);
		// commentsToWrite.setPrefWidth(commentsScreenLayout.getWidth()/2);

		return commentsToWrite;
	}
	
	public void destroyVid()
	{
		try{
		videoPlayer.stop();
		gridForCreation.getChildren().remove(anchorForMedia);
		}
		catch(Exception e){
			System.out.println("No media to destroy");
		}
	}

}