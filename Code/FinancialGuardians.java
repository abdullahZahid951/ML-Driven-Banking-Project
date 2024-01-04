package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.sun.tools.javac.Main;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import financialGuardianApp.controlPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class FinancialGuardians implements Initializable {
	
	 @FXML
     TextField ammountDeposited ;
	 @FXML
	 Label uIdLabel ;
	 @FXML
	 TextField ammountToSent ;
	 @FXML
	 TextField ReceiverAcc ;
	 @FXML
	 Label statusForMoneyTransfered ;
	 @FXML
	 TextField monthInput ;
	 
	 MLModelFactory factory ;
	 
	 @FXML
	 TextField billType ;
	 @FXML
	 TextField Genders ;
	 @FXML
	 TextField PremiumOrRegualarAcc ;
	 
	 @FXML
	 TextField BillAmm ;
	 @FXML
	 TextField BillId ;
	 
	 @FXML
	 Label labelForBillClass ;
	 @FXML
	 ChoiceBox <String> billsBox ; 
	 @FXML
	 Label labelForUserId ;
	 
	 @FXML
	 TextField withdrawnAmm ;
	 @FXML
	 Label statusLabelForWithdraw ;
	 
	 @FXML
	 Label AccNoLabel ;
	 @FXML
	 Label LabelForBalance ;
	 
	 
	 
	 @FXML
	 Label labelForRegualrModel ;
	 
	 @FXML
	 Label labelForPremiumClass ;
	 
	 
	 @FXML
	 TextField AmmForPremiumModel ;
	 
	 @FXML
	 TextField profitForPremiumModel ;
	 @FXML
	 TextField daysForPremiumModel ;
	 
	 
	 @FXML
	 TextField premiumLoanAmm ;
	 
	 double premiumLoanAmm1 ;

	 
	 @FXML
	 TextField regularLoanAmm ;
	 
	 double regularLoanAmm1 ;

	 
	 @FXML
	 Label statusForPremiumLoan ;
	 

	 @FXML
	 Label statusForRegularLoan ;
	 
	 
	 
	 
	 
	 
	 
	 double AmmForPremiumModel1 , profitForPremiumModel1 ;
	 int daysForPremiumModel1 ;
	 
	 
	 
	 @FXML
	 TextField AmmForRegularModel ;
	 
	 
	 double AmmForRegularModel1 ;
	 
	 
	 
	 
	 
	 String billsChoice[]  ; 
	 
	 double BillAmm1 ; int BillId1 ; 
	 String IdOFLoggedInCostumer ; 
	 String BILLTYPE ;
	 double ammDepositedInDouble , ammTosent , ammWithdrawninDouble; 
   	 int recieverAccNo ;
	 private Stage stage;
	 private Scene scene;
	 private Parent root;
     controlPanel cp ;
	 moneyTransfer mt ;
	 BillPayment b ;
	 int monthDate ;
	
	void setterForIdOFLoggedInCostumer(String Id )
	{
		this.IdOFLoggedInCostumer = Id ;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		
	}
	
	public void ButtontocreateinfoUI(ActionEvent e)  throws IOException
	{
		//root = FXMLLoader.load(getClass().getResource("DepositMoneyUIPage.fxml"));
	  	  
			//--------------------------
		    //labelForUserId.setText(IdOFLoggedInCostumer);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("infoUIPage.fxml"));	
			root = loader.load();	

	    	FinancialGuardians fg = loader.getController(); 
	    	fg.setterForIdOFLoggedInCostumer(IdOFLoggedInCostumer);
			//--------------------------
			
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
	
	
	public void premiumAdviseButton(ActionEvent e)  throws IOException
	{
		try {
			
			  AmmForPremiumModel1 = Double.parseDouble(AmmForPremiumModel.getText()) ;

			  profitForPremiumModel1 =  Double.parseDouble(profitForPremiumModel.getText()) ;
			  daysForPremiumModel1 = Integer.parseInt(daysForPremiumModel.getText()) ;
			  factory = new MLModelFactory();
			  MLModel premiumModel = factory.createMLModel("Premium", profitForPremiumModel1, AmmForPremiumModel1, daysForPremiumModel1);
			  
			  if(premiumModel.predictionViaModel() == 1  )
			  {
				  labelForPremiumClass.setText("For Max Profit Invest In Delux Steels.");
			  } 
			  else if (premiumModel.predictionViaModel() == 0)
			  {
				  labelForPremiumClass.setText("For Max Profit Invest In Solid Trust Limited.");
			  }
			  
			 //System.out.println(AmmForPremiumModel1);
			 //System.out.println(profitForPremiumModel1);
			 //System.out.println(daysForPremiumModel1) ;
			 
		}
		catch(NumberFormatException ex)
		{
			AmmForPremiumModel.setText("");
			profitForPremiumModel.setText("");
			daysForPremiumModel.setText("");
			showInvalidAlertErrorInmoneyTransfer() ;
		}
		
		
		
	}
	public void regularAdviseButton(ActionEvent e)  throws IOException
	{
		
		try {
			AmmForRegularModel1 = Double.parseDouble(AmmForRegularModel.getText()) ;
			factory = new MLModelFactory();
			MLModel regularModel = factory.createMLModel("Regular",AmmForRegularModel1);
			if(regularModel.predictionViaModel() == 1  )
			  {
				labelForRegualrModel.setText("For Max Profit Invest In Delux Steels.");
			  } 
			  else if (regularModel.predictionViaModel() == 0)
			  {
				  labelForRegualrModel.setText("For Max Profit Invest In Solid Trust Limited.");
			  }
		}
		catch(NumberFormatException ex)
		{
			AmmForRegularModel.setText("");
			showInvalidAlertErrorInmoneyTransfer() ;

		}
		
		
	}
	
	
	
	
	
	
	
	

	public void showInfoButton(ActionEvent e)  throws IOException
	{
		try {
			labelForUserId.setText(IdOFLoggedInCostumer);
	        String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
	        String uname = "root";
	        String pass = "scifi123";

			
			try {
	            // Establish a connection to the database
	            Connection connection = DriverManager.getConnection(url, uname, pass);

	            // SQL query to select AccNum and CurrentAmount from the currentBalance table
	            String sql = "SELECT AccNum, CurrentAmount FROM currentBalance WHERE UserId = ?";

	            // Create a PreparedStatement
	            PreparedStatement preparedStatement = connection.prepareStatement(sql);

	            // Set the parameter (UserId) for the query
	            preparedStatement.setString(1, IdOFLoggedInCostumer);

	            // Execute the query and get the result set
	            ResultSet resultSet = preparedStatement.executeQuery();

	            // Iterate through the result set and print AccNum and CurrentAmount
	            while (resultSet.next()) {
	                int accNum = resultSet.getInt("AccNum");
	                double currentAmount = resultSet.getDouble("CurrentAmount");
	                
	                AccNoLabel.setText(String.valueOf(accNum));
	    			LabelForBalance.setText("Rs. "+String.valueOf(currentAmount));
	                
	                
	                //System.out.println("AccNum: " + accNum + ", CurrentAmount: " + currentAmount);
	            }

	            // Close resources
	            resultSet.close();
	            preparedStatement.close();
	            connection.close();

	        } catch (SQLException e11) {
	            e11.printStackTrace();
	        }
			

			
		
	    }
		catch(NumberFormatException ex)
		{
			
		
	
	
	        
	        
		}        
	        
	}      
	public void hideInfoButton(ActionEvent e)  throws IOException
	{
		try {
			labelForUserId.setText("");
			AccNoLabel.setText("");
			LabelForBalance.setText("");
		}
		catch(NumberFormatException ex)
		{
			
		}
	}
	
	
	
	
	
	
	
	public void checkBalance()
	{
		
	}
	
	public void Withdraw()
	{
		
	}
	public void buttonToCreateUIPagesForAIBasedInvestment(ActionEvent e)  throws IOException
	{
		String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
        boolean premiumAcc = false ;
        
        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(url, uname, pass);

            // SQL query to select premiumAcc from the BankAccount table based on UserId
            String sql = "SELECT premiumAcc FROM BankAccount WHERE UserId = ?";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set the parameter (UserId) for the query
            preparedStatement.setString(1, IdOFLoggedInCostumer);

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the result set has a row
            if (resultSet.next()) {
                // Retrieve the premiumAcc value
                 premiumAcc = resultSet.getBoolean("premiumAcc");

                //System.out.println("Premium Account Status for User: " + premiumAcc);
            } else {
                //System.out.println("User not found or no premium account status available.");
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        if(premiumAcc)
        {
        	//root = FXMLLoader.load(getClass().getResource("DepositMoneyUIPage.fxml"));
  	  	  
			//--------------------------
		    //labelForUserId.setText(IdOFLoggedInCostumer);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PremiumModelUIPage.fxml"));	
			root = loader.load();	

	    	FinancialGuardians fg = loader.getController(); 
	    	fg.setterForIdOFLoggedInCostumer(IdOFLoggedInCostumer);
			//--------------------------
			
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

        }
        else if ( !premiumAcc   )
        {
        	//root = FXMLLoader.load(getClass().getResource("DepositMoneyUIPage.fxml"));
    	  	  
			//--------------------------
		    //labelForUserId.setText(IdOFLoggedInCostumer);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RegularModelUIPage.fxml"));	
			root = loader.load();	

	    	FinancialGuardians fg = loader.getController(); 
	    	fg.setterForIdOFLoggedInCostumer(IdOFLoggedInCostumer);
			//--------------------------
			
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
        }
        
        
        
		
		
	}
	public void DepositMoney(ActionEvent e)  throws IOException
	{
		
		
		//root = FXMLLoader.load(getClass().getResource("DepositMoneyUIPage.fxml"));
  	  
		//--------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource("DepositMoneyUIPage.fxml"));	
		root = loader.load();	

    	FinancialGuardians fg = loader.getController(); 
    	fg.setterForIdOFLoggedInCostumer(IdOFLoggedInCostumer);
		//--------------------------
		
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
		
		
		
		
		
		
		//System.out.println("Deposit Money") ;
		//System.out.println(IdOFLoggedInCostumer) ;
	}
	
	
	
	public void whatToDoWhenDepositButtonIsPressed(ActionEvent e)
	{
		
		 try {
			 ammDepositedInDouble = Double.parseDouble(ammountDeposited.getText()) ;
			 //System.out.println(ammDepositedInDouble) ;
			 
			 //System.out.println(IdOFLoggedInCostumer) ;
			 
			 
			 cp = new controlPanel (IdOFLoggedInCostumer) ;
			 
			 cp.DepositMoney(ammDepositedInDouble);
			 
			 uIdLabel.setText("Money Deposit Successful...");
			 
		 } catch (NumberFormatException ex) {
	            
			 ammountDeposited.setText("");
	            showPinErrorAlert();		
	     }
	}
	
	public void transferMoneyButtonTocreateUIPage(ActionEvent e) throws IOException
	{
		//--------------------------
				FXMLLoader loader = new FXMLLoader(getClass().getResource("TransferMoneyUIPage.fxml"));	
				root = loader.load();	

		    	FinancialGuardians fg = loader.getController(); 
		    	fg.setterForIdOFLoggedInCostumer(IdOFLoggedInCostumer);
				//--------------------------
				
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
	
	public void ButtonToTransferMoney(ActionEvent e) throws IOException
	{
		
		try {
			
			ammTosent = Double.parseDouble((ammountToSent.getText())) ;
			recieverAccNo = Integer.parseInt(ReceiverAcc.getText()) ;
			
			
			mt = new moneyTransfer(IdOFLoggedInCostumer , recieverAccNo , ammTosent )  ; 
			
			
			if(mt.validatingEnoughAmmountPresent())
			{
				
				mt.sendMoney();
				statusForMoneyTransfered.setText("Money Transfer Succesful,");
				//System.out.println(ammTosent) ;
				//System.out.println(recieverAccNo) ;
			}
			else
			{
				statusForMoneyTransfered.setText("Insufficient Funds First Deposit Funds");
			}
			
			
			
			
			
			
			
			
			//System.out.println(ammTosent) ;
			//System.out.println(recieverAccNo) ;
			
		}
		catch (NumberFormatException ex) {
            
			ammountToSent.setText("");
			ReceiverAcc.setText("");
	        
			
			showInvalidAlertErrorInmoneyTransfer();		
	     }
		
	}
	
	
	
	public void ButtonToGenerateUIPageForMoneyHistory(ActionEvent e ) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UIForMoneyHistory.fxml"));	
		root = loader.load();	

    	FinancialGuardians fg = loader.getController(); 
    	fg.setterForIdOFLoggedInCostumer(IdOFLoggedInCostumer);
		//--------------------------
		
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
	
	
	
	
	public void  ButtonToShowHistory(ActionEvent e)  throws IOException
	{
		try {
			
			monthDate =  Integer.parseInt(monthInput.getText()) ;
			
			
			
			cp = new controlPanel(IdOFLoggedInCostumer) ;
	        
	        Object[][] result = cp.AccessMyMoneyHistory(monthDate) ;
	        
			
	        Stage primaryStage = new Stage()  ; 
	        
	        TableView<ObservableList<Object>> tableView = new TableView<>();

	        // Add columns to the TableView with names
	        String[] columnNames = {"User ID", "Account Number", "Description", "Amount Involved", "Date of Activity"};
	        for (int i = 2; i < 5; i++) {
	            final int index = i;
	            TableColumn<ObservableList<Object>, Object> column = new TableColumn<>(columnNames[i]);
	            column.setCellValueFactory(param ->
	                    new ReadOnlyObjectWrapper<>(param.getValue().get(index))
	            );
	            tableView.getColumns().add(column);
	        }

	        // Populate the TableView with data from the result array
	        for (Object[] row : result) {
	            ObservableList<Object> rowData = FXCollections.observableArrayList(row);
	            tableView.getItems().add(rowData);
	        }

	        // Create a Scene and set it in the Stage
	        Scene scene = new Scene(tableView);
	        primaryStage.setScene(scene);

	        // Set the title of the Stage
	        primaryStage.setTitle("Money History Table");
	        Image iconWindow = new Image("logo.png") ;
	        primaryStage.getIcons().add(iconWindow);
	        // Show the Stage
	        primaryStage.show();
	        
	        
	        
	        
			

	        /*for (Object[] row : result) {
	            for (Object value : row) {
	                System.out.print(value + "\t");
	            }
	            System.out.println();
	        }*/
			
			
			
			
			
			//System.out.println(monthDate) ;
			
			
		}
		catch (NumberFormatException ex) {
			
			showDateErrorAlert() ;
			monthInput.setText("");
			
		}
		
	}
	
	
	public void BillPayment()
	{
		
	}
	
	
	public void ButtonToCreateWithdrawUI(ActionEvent e) throws IOException
	{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("WithdrawUIPage.fxml"));	
		root = loader.load();	

    	FinancialGuardians fg = loader.getController(); 
    	fg.setterForIdOFLoggedInCostumer(IdOFLoggedInCostumer);
		//--------------------------
		
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
	
	
	public void transactionHistory(ActionEvent e )
	{
		
	}
	
	public void logOut(ActionEvent e)  throws IOException
	{
		
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
	
	public void backButton(ActionEvent e) throws IOException
	{
		//--------------------------
				FXMLLoader loader = new FXMLLoader(getClass().getResource("CostumerViewWhenLoggedIn.fxml"));	
				root = loader.load();	

		    	FinancialGuardians fg = loader.getController(); 
		    	fg.setterForIdOFLoggedInCostumer(IdOFLoggedInCostumer);
				//--------------------------
				
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
	
	public void withdrawButton(ActionEvent e) throws IOException
	{
		try {
		
			ammWithdrawninDouble = Double.parseDouble(withdrawnAmm.getText()) ;
			
			
			
			
			 cp = new controlPanel(IdOFLoggedInCostumer) ;
			 	
			 if(cp.checkEnoughAmmPresent(ammWithdrawninDouble))
				{
					cp.withdrawMoney(ammWithdrawninDouble) ;
					statusLabelForWithdraw.setText("Money Withdrawn Successfully...");
					withdrawnAmm.setText("");
				}
			 else {
				 statusLabelForWithdraw.setText("You Dont Enough Funds First Deposit Some Money") ;
			 }
			
			
			
			
			//System.out.println(ammWithdrawninDouble) ;
			
			
		}
		catch(NumberFormatException ex)
		{
			withdrawnAmm.setText("");
			showPinErrorAlert();
		}
		
	}
	
	
	
	public void buttonToCreateLoanManagement(ActionEvent e) throws IOException
	{
		
				
		String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
        boolean premiumAcc = false ;
        
        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(url, uname, pass);

            // SQL query to select premiumAcc from the BankAccount table based on UserId
            String sql = "SELECT premiumAcc FROM BankAccount WHERE UserId = ?";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Set the parameter (UserId) for the query
            preparedStatement.setString(1, IdOFLoggedInCostumer);

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if the result set has a row
            if (resultSet.next()) {
                // Retrieve the premiumAcc value
                 premiumAcc = resultSet.getBoolean("premiumAcc");

                //System.out.println("Premium Account Status for User: " + premiumAcc);
            } else {
                //System.out.println("User not found or no premium account status available.");
            }

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        if(premiumAcc)
        {
        	//root = FXMLLoader.load(getClass().getResource("DepositMoneyUIPage.fxml"));
    	  	  
			//--------------------------
		    //labelForUserId.setText(IdOFLoggedInCostumer);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("premiumLoanManagement.fxml"));	
			root = loader.load();	

	    	FinancialGuardians fg = loader.getController(); 
	    	fg.setterForIdOFLoggedInCostumer(IdOFLoggedInCostumer);
			//--------------------------
			
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

        	
        }
        else if (!premiumAcc)
        {
        	
        	//root = FXMLLoader.load(getClass().getResource("DepositMoneyUIPage.fxml"));
  	  	  
			//--------------------------
		    //labelForUserId.setText(IdOFLoggedInCostumer);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("regularLoanManagment.fxml"));	
			root = loader.load();	

	    	FinancialGuardians fg = loader.getController(); 
	    	fg.setterForIdOFLoggedInCostumer(IdOFLoggedInCostumer);
			//--------------------------
			
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

           

        	
        }
		
		
	}
	
	
	public void buttonToCreateBillPaymentUI(ActionEvent e) throws IOException
	{
		//--------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource("UIForBillPayment.fxml"));	
		root = loader.load();	

    	FinancialGuardians fg = loader.getController(); 
    	fg.setterForIdOFLoggedInCostumer(IdOFLoggedInCostumer);
		//--------------------------
		
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
	
	
	public void PayMyBillButton(ActionEvent e) throws IOException
	{
		
		try {
			
			BillAmm1  = Double.parseDouble(BillAmm.getText()) ; 
			BillId1 = Integer.parseInt(BillId.getText());
			BILLTYPE =billType.getText() ;
			
			
			if(BILLTYPE.toLowerCase().equals("gas") || 
					BILLTYPE.toLowerCase().equals("water") ||	
					BILLTYPE.toLowerCase().equals("electricity") ||
					BILLTYPE.toLowerCase().equals("internet") || 
					BILLTYPE.toLowerCase().equals("telephone"))
			{
				
				
				 b = new BillPayment(IdOFLoggedInCostumer , BillId1 , BillAmm1 , BILLTYPE.toLowerCase()   ) ;
				 cp = new controlPanel(IdOFLoggedInCostumer) ;
				
				
					if(b.checkWhetherIsBillIsAlreadyPayedForTheMonthOr())
					{
						
						if(cp.checkEnoughAmmPresent(b.BillAmm))
							{
							
							    b.Pay_Bill() ;
							    labelForBillClass.setText("Bill Payed Successfully....") ;
							}
						else
							{
								labelForBillClass.setText("Not Enough Funds To Pay The Bill. First Deposit Money");
								//System.out.println() ;
							}
					}
					else
					{
						labelForBillClass.setText("You Already payed "  + b.billType + " Bill For This Month." );
						//System.out.println() ;
					}
			        
			        
				
				
				//System.out.println( BillAmm1 )  ;  
				//System.out.println( BillId1  )  ;
				//System.out.println( BILLTYPE.toLowerCase()  )  ;

			}
			else
			{
				billType.setText("");
			}
			
			
			
			
						
		}
		catch(NumberFormatException ex)
		{
			showInvalidAlertErrorInmoneyTransfer() ;
			BillId.setText("");
			BillAmm.setText("");
		}
		
		
	}
	
	
	public void applyForPremiumLoanButton(ActionEvent e) throws IOException
	{
		
		
		try {
			premiumLoanAmm1 = Double.parseDouble(premiumLoanAmm.getText()) ;
			
			cp = new controlPanel() ;
			
			
			
			
			if(!cp.CheckWhetherUserHasAlreadyTakenLoanOrNot(IdOFLoggedInCostumer)) 
		     {
				 if( 1000000 >=   premiumLoanAmm1)
				 {
					 LoanFactory factory = new LoanFactory();
		   		     LoanManagment premiumLoan = factory.createLoans("Premium", IdOFLoggedInCostumer ,premiumLoanAmm1) ;
		   		     	if ( premiumLoan.checkWhetherUserAccHasEnoughAmm())
						{
							premiumLoan.grantingUserHisLoanAndUpdatingTheDB();
							 statusForPremiumLoan.setText("Loan Granted Succesfully...") ;
						}
						else
						{
							 statusForPremiumLoan.setText("You Must Have Atleast Rs. 80,000 in your Acc to get this Premium Loan.") ;
						}
					 
				 }
				 else {
					    
					    statusForPremiumLoan.setText("We can only grant you loan upto Rs. 1000000");
						premiumLoanAmm.setText("");
				 }
				
		     }
			else {
				statusForPremiumLoan.setText("You already Have taken the loan...."  );
				premiumLoanAmm.setText("");
  	 
		     }
			
			
			
			
			
			
			
			
			
			//System.out.println( premiumLoanAmm1 ) ;
			//statusForPremiumLoan
 		}
		catch(NumberFormatException ex)
		{
			premiumLoanAmm.setText("");
			showInvalidAlertErrorInmoneyTransfer() ;
		}
		
		
		
		
		//System.out.println("Premium loan Buttton") ;		
		
	}
	
	
	

	public void applyForRegularLoanButton(ActionEvent e) throws IOException
	{
		try {
			regularLoanAmm1 = Double.parseDouble(regularLoanAmm.getText()) ;
			cp = new controlPanel() ;
			
			//statusForRegularLoan
			
			if(!cp.CheckWhetherUserHasAlreadyTakenLoanOrNot(IdOFLoggedInCostumer)) 
		     {
				 if( 500000 >=   regularLoanAmm1)
				 {
					 LoanFactory factory = new LoanFactory();
		   		     LoanManagment regularLoan = factory.createLoans("Regular", IdOFLoggedInCostumer ,regularLoanAmm1) ;
		   		     	if ( regularLoan.checkWhetherUserAccHasEnoughAmm())
						{
		   		     		 regularLoan.grantingUserHisLoanAndUpdatingTheDB();
		   		     		statusForRegularLoan.setText("Loan Granted Succesfully...") ;
						}
						else
						{
							statusForRegularLoan.setText("You Must Have Atleast Rs. 50,000 in your Acc to get this Premium Loan.") ;
						}
					 
				 }
				 else {
					    
					 statusForRegularLoan.setText("We can only grant you loan upto Rs. 500000");
					    regularLoanAmm.setText("");
				 }
				
		     }
			else {
				statusForRegularLoan.setText("You already Have taken the loan...."  );
				regularLoanAmm.setText("");
 	 
		     }
			
			//System.out.println( regularLoanAmm1 ) ;
			
 		}
		catch(NumberFormatException ex)
		{
			regularLoanAmm.setText("");
			showInvalidAlertErrorInmoneyTransfer() ;
		}
		
		//System.out.println("regular loan Buttton") ;		
		
	}
	
	
	
	
	private void showPinErrorAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText("Please enter a Valid Amount in Integer/Double in Above Field");
        alert.showAndWait();
    }
	
	private void showInvalidAlertErrorInmoneyTransfer() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input In Either Field ");
        alert.setContentText("Please enter a Valid Amount in Integer/Double in Above Fields");
        alert.showAndWait();
    }
	
	private void showDateErrorAlert() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Input");
        alert.setContentText("Please enter a Valid Integer from 1 to 12 in Above Field");
        alert.showAndWait();
    }
	
	
}
