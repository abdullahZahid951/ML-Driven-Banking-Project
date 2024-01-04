package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class controllerForRegVideoPage implements Initializable  {
	@FXML
	MediaPlayer mediaPlayer ;
	@FXML
	Media media ;
	@FXML
	File file ;
	
	@FXML
	MediaView mediaView1 ;
	
	
	private Stage stage;
	private Scene scene;
	private Parent root;

	
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		file = new File("C:\\Users\\Crown Tech\\eclipse-workspace\\FinancialGuardianProject\\src\\regGuide.mp4") ;
		media = new Media(file.toURI().toString()) ;
		mediaPlayer = new MediaPlayer(media) ;	
		mediaView1.setMediaPlayer(mediaPlayer);
		System.out.println("Ko") ;
	}

	public void playVid()
	{
		mediaPlayer.play(); ;
	}
	public void pauseVid()
	{
		mediaPlayer.pause();
	}
	public void Back1(ActionEvent e) throws IOException 
	{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("registrationFormUI.fxml"));	
		root = loader.load();	
		
		
		
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
  	  	scene = new Scene(root);
  	  	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
  	  	stage.setScene(scene);
  	  
  	  	//logo on window screeen
		Image iconWindow = new Image("logo.png") ;
		stage.getIcons().add(iconWindow);
		//----------------------------------
		
		stage.setTitle("Fianancial Guardians");			
        stage.setResizable(false);
        stage.show();
		
		
	}


	
	
	
	
	
	
	
	
}
