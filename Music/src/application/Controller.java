package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;
import javafx.fxml.Initializable;

public class Controller implements Initializable{
	@FXML
	private Pane pane;
	@FXML
	private Label songLabel;
	@FXML
	private Button playButton, pauseButton, resetButton, previousButton, nextButton;
	//@FXML
	//private ComboBox<String> speedBox;
	@FXML
	private ProgressBar songProgressBar;
	private Media media;
	
	private MediaPlayer mediaPlayer;
	
	private File directory,dir;
	private File[] files;
	private File[] file;
	private ArrayList<File> songs;
	private ArrayList<File> img;
	@FXML
	private Slider volumeSlider;
	private int songNumber;
	private Timer timer;
	private TimerTask task;
	private boolean running;
	
	@FXML
	private Button myWelButton;
	
	@FXML
	private ImageView myImageView;
	private Image myImage;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		songs = new ArrayList<File>();
		directory = new File("music");
		
		files = directory.listFiles();
		
		if(files!=null) {
			for(File file : files) {
				songs.add(file);
			}
		}
		
		img = new ArrayList<File>();
		dir = new File("image");
		file = dir.listFiles();
		
		if(file!=null) {
			for(File f:file) {
				img.add(f);
			}
		}
		
		media = new Media(songs.get(songNumber).toURI().toString());
		
		mediaPlayer = new MediaPlayer(media);
		
		myImage = new Image(img.get(songNumber).toURI().toString());
		
		myImageView.setImage(myImage);
		
		songLabel.setText(songs.get(songNumber).getName());
		
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				mediaPlayer.setVolume(volumeSlider.getValue()*0.01);
			}
		});
		
		songProgressBar.setStyle("-fx-accent: #383838");
	}
	
	public void beginTimer() {
		timer = new Timer();
		task = new TimerTask() {
			public void run() {
				running = true;
				double current = mediaPlayer.getCurrentTime().toSeconds();
				double end = media.getDuration().toSeconds();
				songProgressBar.setProgress(current/end);
				if(current/end==1) {
					cancelTimer();
					nextMedia();
				}
			}
		};
		
		timer.schedule(task,0,1000);
	}
	
	public void cancelTimer() {
		running = false;
		timer.cancel();
	}
	
	public void playMedia() {
		beginTimer();
		mediaPlayer.setVolume(volumeSlider.getValue()*0.01);
		mediaPlayer.play();
	}
	public void pauseMedia() {
		cancelTimer();
		mediaPlayer.pause();
	}	
	public void resetMedia() {
		songProgressBar.setProgress(0);
		mediaPlayer.seek(Duration.seconds(0));
	}
	public void nextMedia() {
		songNumber = (songNumber+1)%(songs.size());//back to song 0 if last is reached
		mediaPlayer.stop();
		if(running)cancelTimer();
		media = new Media(songs.get(songNumber).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		myImage = new Image(img.get(songNumber).toURI().toString());		
		myImageView.setImage(myImage);
		songLabel.setText(songs.get(songNumber).getName());	
		mediaPlayer.setVolume(volumeSlider.getValue()*0.01);
		mediaPlayer.play();
		beginTimer();
	}
	public void previousMedia() {
		songNumber = (songs.size()-1+songNumber)%(songs.size());//back to song n if last is reached
		mediaPlayer.stop();
		if(running)cancelTimer();
		media = new Media(songs.get(songNumber).toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		myImage = new Image(img.get(songNumber).toURI().toString());
		myImageView.setImage(myImage);
		songLabel.setText(songs.get(songNumber).getName());
		mediaPlayer.setVolume(volumeSlider.getValue()*0.01);
		mediaPlayer.play();
		beginTimer();
	}
	
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void darkMode(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Sample.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void lightMode(ActionEvent event) throws IOException {
		mediaPlayer.stop();
		Parent root = FXMLLoader.load(getClass().getResource("/application/Light.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	

	
}
   