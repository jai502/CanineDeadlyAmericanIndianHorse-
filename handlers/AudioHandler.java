


package handlers;

import java.io.File;

import Objects.AudioItem;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

//main class for creating the audio controls
public class AudioHandler extends HBox {
	
	private MediaPlayer mp; //
	private boolean repeat;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private String fileName;
    private Duration duration;
	private Slider timeSlider;
	private Label playTime;
	private Label volumeLabel;
	private Slider volumeSlider;
	private Button playButton;
	
	public AudioHandler(AudioItem audio)
	{
		
		this.fileName = audio.getSourceFile();
		this.repeat = audio.isLoop();
		File file = new File(fileName);
		
		Media media = new Media(file.toURI().toString());
		
		mp = new MediaPlayer(media);
		mp.setAutoPlay(true);
		
		mediaControls();
		
	}
	//building the control panel
	public void mediaControls(){
		
	    playButton = new Button(">");
		
		playButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				Status status = mp.getStatus();
				
				if (status == Status.UNKNOWN || status == Status.HALTED){
					return;
					
				}
					if (status == Status.READY || status == Status.PAUSED || status == Status.STOPPED)
					{
						if (atEndOfMedia) {
			                mp.seek(mp.getStartTime());
			                atEndOfMedia = false;
			             }
			             mp.play();
			             } else {
			               mp.pause();
			             }
						
					}			
			
		});
		
		mp.currentTimeProperty().addListener(new InvalidationListener() 
        {
            public void invalidated(Observable ov) {
                updateValues();
            }
        });
 
        mp.setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
                    mp.pause();
                    stopRequested = false;
                } else {
                    playButton.setText("||");
                }
            }
        });
 
        mp.setOnPaused(new Runnable() {
            public void run() {
                System.out.println("onPaused");
                playButton.setText(">");
            }
        });
 
        mp.setOnReady(new Runnable() {
            public void run() {
                duration = mp.getMedia().getDuration();
                updateValues();
            }
        });
 
        mp.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        mp.setOnEndOfMedia(new Runnable() {
            public void run() {
                if (!repeat) {
                    playButton.setText(">");
                    stopRequested = true;
                    atEndOfMedia = true;
                }
                else {
                	 mp.seek(Duration.ZERO);
                }
            }
       });
		
		this.getChildren().add(playButton);	
		
		Label spacer = new Label("      ");
		this.getChildren().add(spacer);
		
		Label timeLabel = new Label("Time:  ");
		this.getChildren().add(timeLabel);
		//set up the time slider
		timeSlider = new Slider();
		HBox.setHgrow(timeSlider, Priority.ALWAYS);
		timeSlider.setMinWidth(50);
		timeSlider.setMaxWidth(Double.MAX_VALUE);
		
		
		timeSlider.valueProperty().addListener(new InvalidationListener() {
		    public void invalidated(Observable ov) {
		       if (timeSlider.isValueChanging()) {
		       // multiply duration by percentage calculated by slider position
		          mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
		       }
		    }
		});
		
		this.getChildren().add(timeSlider);
		//Add Timer
		playTime = new Label();
		playTime.setPrefWidth(130);
		playTime.setMinWidth(50);
		this.getChildren().add(playTime);
		
		volumeLabel = new Label("Vol:  ");
		this.getChildren().add(volumeLabel);
		
		// Add Volume slider
		volumeSlider = new Slider();        
		volumeSlider.setPrefWidth(70);
		volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
		volumeSlider.setMinWidth(30);
		
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
		    public void invalidated(Observable ov) {
		       if (volumeSlider.isValueChanging()) {
		           mp.setVolume(volumeSlider.getValue() / 100.0);
		       }
		    }
		});
		
		this.getChildren().add(volumeSlider);
		
		
		
	}
	
	protected void updateValues() {
		  if (playTime != null && timeSlider != null && volumeSlider != null) {
		     Platform.runLater(new Runnable() {
		        public void run() {
		          Duration currentTime = mp.getCurrentTime();
		          playTime.setText(formatTime(currentTime, duration));
		          timeSlider.setDisable(duration.isUnknown());
		          if (!timeSlider.isDisabled() 
		            && duration.greaterThan(Duration.ZERO) 
		            && !timeSlider.isValueChanging()) {
		              timeSlider.setValue(currentTime.divide(duration).toMillis()
		                  * 100.0);
		          }
		          if (!volumeSlider.isValueChanging()) {
		            volumeSlider.setValue((int)Math.round(mp.getVolume() 
		                  * 100));
		          }
		        }
		     });
		  }
		}
	// function for the control timer, to determine where it is
	private static String formatTime(Duration elapsed, Duration duration) {
		
		   int intElapsed = (int)Math.floor(elapsed.toSeconds());
		   int elapsedHours = intElapsed / (60 * 60);
		   
		   
		   if (elapsedHours > 0) {
		       intElapsed -= elapsedHours * 60 * 60;
		   }
		   
		   int elapsedMinutes = intElapsed / 60;
		   int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;
		 
		   if (duration.greaterThan(Duration.ZERO)) {
		      int intDuration = (int)Math.floor(duration.toSeconds());
		      int durationHours = intDuration / (60 * 60);
		      if (durationHours > 0) {
		         intDuration -= durationHours * 60 * 60;
		      }
		      int durationMinutes = intDuration / 60;
		      int durationSeconds = intDuration - durationHours * 60 * 60 - 
		          durationMinutes * 60;
		      if (durationHours > 0) {
		         return String.format("%d:%02d:%02d/%d:%02d:%02d", 
		            elapsedHours, elapsedMinutes, elapsedSeconds,
		            durationHours, durationMinutes, durationSeconds);
		      } else {
		          return String.format("%02d:%02d/%02d:%02d",
		            elapsedMinutes, elapsedSeconds,durationMinutes, 
		                durationSeconds);
		      }
		      } else {
		          if (elapsedHours > 0) {
		             return String.format("%d:%02d:%02d", elapsedHours, 
		                    elapsedMinutes, elapsedSeconds);
		            } else {
		                return String.format("%02d:%02d",elapsedMinutes, 
		                    elapsedSeconds);
		            }
		        }
		    }
	
}
