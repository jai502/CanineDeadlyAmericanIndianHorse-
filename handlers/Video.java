package handlers;

/*
 * Author: Oliver Rushton
 * Group: 4
 * Date finished: 03/03/2016
 * Description: This module instantiates a video player with video control functionality
 * 				and resizes the MediaView to fit its Pane, whilst maintaining aspect ratio 
 * 				and centring the MediaView
 */

import java.io.File;

import Objects.VideoItem;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class Video extends Pane{
	
	private int startTime, duration;
	private float x, y;
	private String sourceFile;
	private boolean loop;
	
	private MediaPlayer mediaPlayer;
	private MediaView mediaView;
	private boolean endTransitionFinished = true;
	private VideoControls vc;
	
	public Video(VideoItem video) {
		this.startTime = video.getStartTime();
		this.duration = video.getDuration();
		this.x = video.getxStart();
		this.y = video.getyStart();
		this.sourceFile = video.getSourceFile();
		this.loop = video.isLoop();
		//retrieves video file from source folder
	    final File f = new File(sourceFile);
	    try {
	    	Media media = new Media(f.toURI().toString()); //writes file as URI string to pass to media
	    	mediaPlayer = new MediaPlayer(media);
	    } catch (MediaException m) {
	    	System.out.println("The source File '" + f + "' does not exist in the relative path: src/videos/" + sourceFile);
	    }
		setupPlayer();
	}
	
	private void setupPlayer() {
		//set style of media player - needs to be re-written by each group
		this.setStyle("-fx-background-color: black; -fx-border-color: red; -fx-padding: 20");
		
		mediaPlayer.setAutoPlay(true);
		mediaView = new MediaView(mediaPlayer);
	    
		//bind mediaView width to that of its containers width
		mediaView.fitWidthProperty().bind(this.widthProperty());
		mediaView.fitHeightProperty().bind(this.heightProperty());
		//preserve aspect ratio and set scaling to smooth	
	    mediaView.setPreserveRatio(true);
	    mediaView.setSmooth(true);
	    createSizeListeners(); //adjusts mediaView dimensions to fit container
	    this.getChildren().add(mediaView);
	    
	    
	    //instantiate video controls
		vc = new VideoControls(mediaPlayer,loop);
		vc.prefWidthProperty().bind(this.widthProperty());
		vc.setVisible(false);
		this.getChildren().add(vc);
		
	}
	//returns Pane object
	private Pane getPane() {
		return this;
	}
	
	private void createSizeListeners() {
		//container width listener
		this.widthProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
				adjustMediaDim();
				
			}
		});
		//container height listener
		this.heightProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
				adjustMediaDim();
			}
		});
		
		//instantiate event handlers to show/hide video controls
		this.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (endTransitionFinished) {
					vc.setVisible(true);
					vc.setLayoutY(getPane().getHeight() - 70); //always appear off from off bottom of screen
					//appear transition
					FadeTransition appear = new FadeTransition(Duration.millis(350), vc);
					appear.setCycleCount(1);
					appear.setFromValue(0.0);
					appear.setToValue(1.0);
					appear.play();
					endTransitionFinished = false;
				}
				
			}
		});
		
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				//disappear transition
				FadeTransition fade = new FadeTransition(Duration.millis(350), vc);
				fade.setCycleCount(1);
				fade.setFromValue(1.0);
				fade.setToValue(0.0);
				fade.play();
				//at end of transition sets video controls invisible
				fade.setOnFinished(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e2) {
						vc.setVisible(false);
						endTransitionFinished = true;	
					}
				});
			}
		});
		//adjusts the MediaView to be in centre of screen
		mediaPlayer.setOnPlaying(new Runnable() {
			public void run() {
				adjustMediaDim();
			}
		});
		
	}
	
	private void adjustMediaDim() {
		Platform.runLater(new Runnable() {
            public void run() {
            	//if container width or height is larger than media view port, position media in middle of screen
	           	if (getPane().getWidth() > mediaView.getBoundsInParent().getWidth()) {
	           		mediaView.setLayoutX((getPane().getWidth() - mediaView.getBoundsInParent().getWidth())/2);
	           		mediaView.setLayoutY(0);
	           	} else if (getPane().getHeight() > mediaView.getBoundsInParent().getHeight()) {
	           		mediaView.setLayoutY((getPane().getHeight() - mediaView.getBoundsInParent().getHeight())/2);
	           		mediaView.setLayoutX(0);
	           	}  
            }
		 });
	}
	
	//getter methods required by contract
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getStartTime() {
		return startTime;
		// TODO Auto-generated constructor
	}
	
	public int getEndTime() {
		if (loop) {
			return -1;
		} else {
			return (startTime + duration);
		}
	}
	//media control methods required by contract
	public void play() {
		vc.play();
	}
	
	public void stop() {
		vc.stop();
	}
	
}
