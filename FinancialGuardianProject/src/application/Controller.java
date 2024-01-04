package application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class Controller implements Initializable  {
	
	@FXML
	TextField name ;
	@FXML
	TextField CNIC ;
	@FXML
	TextField EMAIL ;
	@FXML
	TextField PHONE ;
	@FXML
	TextField INITIALAMM ;
	@FXML
	TextField ADDRESS ;
	@FXML
	TextField REGUSERID ;
	@FXML
	TextField gender ;
	@FXML
	TextField PremiumOrRegualrAcc ;
	@FXML
	Label myLabel ;
	@FXML
	TextField DOB ;
	@FXML
	Button blue11 ;
	
	@FXML
	MediaPlayer mediaPlayer ;
	@FXML
	Media media ;
	@FXML
	File file ;
	
	@FXML
	MediaView mediaView1 ;
	
	 @FXML
	 ChoiceBox <String> billsBox1 ;
	String userID   ;
	int pin ;
	controlPanel cp ;
	@FXML
	TextField loginIdTextField ;
	@FXML
	Label wrongPinOrIdTextField  ;
	@FXML
	TextField pinTextField  ;
	 private Stage stage;
	 private Scene scene;
	 private Parent root;

	 
	 
		String name1 ;
		int CNIC1 ;
		String EMAIL1 ;
		int PHONE1 ;
		double INITIALAMM1 ;
		String ADDRESS1 ;
		String REGUSERID1 ;
		String gender1 ;
		String PremiumOrRegualrAcc1 ;
		String DOB1 ;
	
	public Controller()
	{
		cp = new controlPanel() ;
	}
	
	
	public void logIn(ActionEvent e) throws IOException 
	{
		
		 try {
	            userID = loginIdTextField.getText();
	            pin = Integer.parseInt(pinTextField.getText());
	            
	            if(cp.validLoginCheck( userID, pin))
	            {
	                	
	        		FXMLLoader loader = new FXMLLoader(getClass().getResource("CostumerViewWhenLoggedIn.fxml"));	
	        		root = loader.load();	

	            	FinancialGuardians fg = loader.getController(); 
	            	fg.setterForIdOFLoggedInCostumer(userID);
	            	//fg.billsBox = billsBox1 ;
	            	//fg.billsChoice =  {"Gas" , "Electricity" } ;
	            	
	            	  //root = FXMLLoader.load(getClass().getResource("CostumerViewWhenLoggedIn.fxml"));
	            	  stage = (Stage)((Node)e.getSource()).getScene().getWindow();
	            	  scene = new Scene(root);
	            	  scene.getStylesheets().add(getClass().getResource("cssForCostumerViewLoggedIn.css").toExternalForm());
	            	  stage.setScene(scene);
	            	  
	            	//logo on window screeen
	      			Image iconWindow = new Image("logo.png") ;
	      			stage.getIcons().add(iconWindow);
	      			//----------------------------------
	      			
	      			stage.setTitle("Fianancial Guardians");			
	      	        stage.setResizable(false);

	      			//------------------------------------------------------

	            	  
	            	  
	            	  
	            	  
	            	  
	            	  stage.show();

	            }
	            else {
	            	
	            	
	            	
	            	wrongPinOrIdTextField.setText("Wrong User Id Or Pin...");
	            	
	            	pinTextField.setText("");
	        		loginIdTextField.setText("");
	            	
	         
	            }
	            
	        } catch (NumberFormatException ex) {
	            
	            showPinErrorAlert();
	    		pinTextField.setText("");

	        }
	}
	
	public void reset(ActionEvent e)
	{
		pinTextField.setText("");
		loginIdTextField.setText("");
	}
	
	public void RegisterButtonToCreateUIForRegister( ActionEvent e ) throws IOException 
	{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("registrationFormUI.fxml"));	
		root = loader.load();
		
		
		
		  //root = FXMLLoader.load(getClass().getResource("CostumerViewWhenLoggedIn.fxml"));
  	  stage = (Stage)((Node)e.getSource()).getScene().getWindow();
  	  scene = new Scene(root);
  	  scene.getStylesheets().add(getClass().getResource("cssForCostumerViewLoggedIn.css").toExternalForm());
  	  stage.setScene(scene);
  	  
  	//logo on window screeen
		Image iconWindow = new Image("logo.png") ;
		stage.getIcons().add(iconWindow);
		//----------------------------------
		
		stage.setTitle("Fianancial Guardians");			
        stage.setResizable(false);

		//------------------------------------------------------

  	  
  	  
  	  
  	  
  	  
  	  stage.show();
		
		//System.out.println("Registered");
	}
	public void createAccButton(ActionEvent e ) throws IOException 
	{
		
		
		try {
			
			CNIC1 = Integer.parseInt(CNIC.getText())  ;
			PHONE1 = Integer.parseInt(PHONE.getText()) ;
			INITIALAMM1 = Double.parseDouble(INITIALAMM.getText())  ;
			
			
			name1  = name.getText() ;
			EMAIL1 = EMAIL.getText() ;
			ADDRESS1 = ADDRESS.getText()  ;
			REGUSERID1 = REGUSERID.getText() ;
			gender1 = gender.getText()  ;
			PremiumOrRegualrAcc1 = PremiumOrRegualrAcc.getText()  ;
			DOB1 = DOB.getText() ;
			//System.out.println(DOB1) ;
			
			Registration r ;
			
			if(!(cp.checkWhetherThisUserIdExistsOrNotInDB(REGUSERID1) ) && REGUSERID1 != null  )
			{
				if(gender1.toLowerCase().equals("male"))
				{
					if(PremiumOrRegualrAcc1.toLowerCase().equals("premium"))
					{
						
						r = new Registration(REGUSERID1 , name1 , ADDRESS1 , INITIALAMM1 , CNIC1,
							true, true , false ,EMAIL1 , DOB1	) ;
						r.createThisAccount();
						
						myLabel.setText(  "Account Created Successfully   " + "Acc No : " + String.valueOf(r.getterForAccNo()) + "   Pin : "  + String.valueOf(r.getterForPin()) ) ;
						
						//System.out.println(String.valueOf(r.getterForAccNo())   + " " + String.valueOf(r.getterForPin())     ) ;
						
						
						blue11.setLayoutX(1000);
						blue11.setLayoutY(1000);
						
						
					}
					else if (PremiumOrRegualrAcc1.toLowerCase().equals("regular"))
					{
						r = new Registration(REGUSERID1 , name1 , ADDRESS1 , INITIALAMM1 , CNIC1,
								true, false , true ,EMAIL1 , DOB1	) ;
						r.createThisAccount();
						//System.out.println("male " + " regular"  ) ;
						
						
						myLabel.setText(  "Account Created Successfully   " + "Acc No : " + String.valueOf(r.getterForAccNo()) + "   Pin : "  + String.valueOf(r.getterForPin()) ) ;
						
						
						
						
						
						blue11.setLayoutX(1000);
						blue11.setLayoutY(1000);
						
						
					}
					
					else
					{
						PremiumOrRegualrAcc.setText("");
						myLabel.setText("Write Either Regular Or Premium in Gender Box...") ;
					}
					
					
					
				}	
				else if(gender1.toLowerCase().equals("female"))
				{
					if(PremiumOrRegualrAcc1.toLowerCase().equals("premium"))
					{
						
						r = new Registration(REGUSERID1 , name1 , ADDRESS1 , INITIALAMM1 , CNIC1,
								false , true , false ,EMAIL1 , DOB1	) ;
						
						r.createThisAccount();
						
						
						//System.out.println("female " + " premium"  ) ;
						
						myLabel.setText(  "Account Created Successfully   " + "Acc No : " + String.valueOf(r.getterForAccNo()) + "   Pin : "  + String.valueOf(r.getterForPin()) ) ;
						
						
						
						
						blue11.setLayoutX(1000);
						blue11.setLayoutY(1000);
						
						
					}
					else if (PremiumOrRegualrAcc1.toLowerCase().equals("regular"))
					{
						
						r = new Registration(REGUSERID1 , name1 , ADDRESS1 , INITIALAMM1 , CNIC1,
								false, false , true ,EMAIL1 , DOB1	) ;
						
						r.createThisAccount();
						
						//System.out.println("female " + " regular"  ) ;
						
						myLabel.setText(  "Account Created Successfully   " + "Acc No : " + String.valueOf(r.getterForAccNo()) + "   Pin : "  + String.valueOf(r.getterForPin()) ) ;
						
						
						blue11.setLayoutX(1000);
						blue11.setLayoutY(1000);
						
					}
					
					else
					{
						PremiumOrRegualrAcc.setText("");
						myLabel.setText("Write Either Regular Or Premium in Gender Box...") ;
					}
					
					
				}
				else 
				{
					gender.setText("");
					myLabel.setText("Write Either Male Or Female in Gender Box...") ;
				}
				
				
			}
			else
			{
				myLabel.setText("User Id Taken Enter User Id Again....");
				REGUSERID.setText("");
			}
			
				
			
		}
		catch(NumberFormatException ex)
		{
			CNIC.setText("");
			PHONE.setText("");
			INITIALAMM.setText("");
			showInvalidInputErrorAlert() ;
		}
		
		
		//System.out.println("Registered1111");
		
		
	}
	private void showPinErrorAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid PIN");
        alert.setContentText("Please enter a valid number in the PIN field.");
        alert.showAndWait();
    }
	private void showInvalidInputErrorAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input In CNIC or Phone or Ammount Box");
        alert.setContentText("Please enter a Numeric Value in the CNIC or Phone or Ammount Box.");
        alert.showAndWait();
    }
	
	
	public void Back(ActionEvent e) throws IOException 
	{
		blue11.setLayoutX(494);
		blue11.setLayoutY(475);
		
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));	
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
	
	
	public void buttonTocreateRegistrationVideoUi(ActionEvent e) throws IOException
	{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UIForRegVideo.fxml"));	
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


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//file = new File("C:\\Users\\Crown Tech\\eclipse-workspace\\FinancialGuardianProject\\src\\How Ai Will Revolutionize Banking.mp4") ;
		//media = new Media(file.toURI().toString()) ;
		//mediaPlayer = new MediaPlayer(media) ;	
		//mediaView1.setMediaPlayer(mediaPlayer);
		//System.out.println("Ko") ;
	}
	
	
	public void playVid()
	{
		
	}
	public void pauseVid()
	{
		
	}
	
	
	
	
	
}
