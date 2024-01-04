package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class moneyTransfer {
	String userId , receiverId = "Forign User"  ; 
	int tID ;
	int senderAcc , receiverAcc ;
	double amm ;
	
	
	public  moneyTransfer(String userId , int receiverAcc  , double amm)
	{
		this.userId = userId ;
		//this.senderAcc = senderAcc ;
		this.receiverAcc = receiverAcc  ; 
		this.amm =amm ;
		this.tID = tIDGenerator() ;  

		String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
		
		 try {
	            // Establish a connection to the database
	            Connection connection = DriverManager.getConnection(url, uname, pass);

	            // SQL query to retrieve AccNum based on UserId
	            String sql = "SELECT AccNum FROM currentBalance WHERE UserId = ?";

	            // Create a PreparedStatement with the parameterized query
	            PreparedStatement preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.setString(1, this.userId );

	            // Execute the query
	            ResultSet resultSet = preparedStatement.executeQuery();

	            // Check if any results were returned
	            if (resultSet.next()) {
	                // Retrieve the AccNum from the result set
	            	this.senderAcc = resultSet.getInt("AccNum");
	                System.out.println("AccNum for UserId " + this.userId  + ": " + this.senderAcc);
	            } else {
	                System.out.println("No account found for UserId " + this.userId);
	            }

	            // Close the resources
	            resultSet.close();
	            preparedStatement.close();
	            connection.close();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		
			
		 url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
         uname = "root";
         pass = "scifi123";
        
		
        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(url, uname, pass);

            // SQL query to retrieve UserId based on AccNum
            String sql = "SELECT UserId FROM currentBalance WHERE AccNum = ?";
            
            // Create a PreparedStatement with the parameterized query
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, this.receiverAcc);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if any results were returned
            if (resultSet.next()) {
                // Retrieve the UserId from the result set
                this.receiverId = resultSet.getString("UserId");
                System.out.println("UserId for AccNum " + this.receiverAcc + ": " + this.receiverId);
            } else {
                System.out.println("Forign User's AccNum " + this.receiverAcc);
            }

            // Close the resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    

     
		
		//System.out.println(this.tID) ;
	}
	
	public boolean  validatingEnoughAmmountPresent()
	{
		
		
		String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
        
        
        
        
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String DateWhenAccCreated = currentDate.format(formatter); 
        
        
        
        String selectQuery = "SELECT CurrentAmount, AccNum FROM currentBalance WHERE UserId = ?";
        double currentAmount = 0.0;
        int Acc = 0 ;
        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {

            preparedStatement.setString(1,this.userId  );
            

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve the CurrentAmount from the result set
                    currentAmount = resultSet.getDouble("CurrentAmount");
                    Acc = resultSet.getInt("AccNum");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


		if (currentAmount  >= this.amm )
		{
			return true ;
		}	
		
		return false ;
	}
	
	
	
	public void sendMoney()
	{
		
		String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
        
        
        
        
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String DateWhenAccCreated = currentDate.format(formatter); 
        
        String selectQuery = "SELECT CurrentAmount, AccNum FROM currentBalance WHERE UserId = ?";
        double currentAmount = 0.0;
        int Acc = 0 ;
        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {

            preparedStatement.setString(1,this.userId );
            

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve the CurrentAmount from the result set
                    currentAmount = resultSet.getDouble("CurrentAmount");
                    Acc = resultSet.getInt("AccNum");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        currentAmount -= this.amm ;

		
		
        
        String updateQuery = "UPDATE currentBalance SET CurrentAmount = ? WHERE UserId = ?";

        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1,currentAmount);
            preparedStatement.setString(2, this.userId );

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                
                try (Connection con1 = DriverManager.getConnection(url, uname, pass)) {

                    
                    String insertQuery = "INSERT INTO accountActivity (UserId, AccNum, description, amountInvolved, DOBOfActivity)"
                    		+ "		      VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement preparedStatement1 = con1.prepareStatement(insertQuery)) {
                    	preparedStatement1.setString(1,this.userId ) ;
                    	preparedStatement1.setInt(2, this.senderAcc);
                        preparedStatement1.setString(3, "Amount Of "  + String.valueOf(this.amm)  +  " sent to Acc No " + String.valueOf(this.receiverAcc) );
                        preparedStatement1.setDouble(4,this.amm );
                        preparedStatement1.setDate(5, java.sql.Date.valueOf(DateWhenAccCreated));
                        
                        int rowsAffected = preparedStatement1.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Data inserted successfully.");
                        } else {
                            System.out.println("Data insertion failed.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

   	            	
            } else {
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        
         currentDate = LocalDate.now();
         formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		 DateWhenAccCreated = currentDate.format(formatter); 
        
         selectQuery = "SELECT CurrentAmount, AccNum FROM currentBalance WHERE UserId = ?";
         currentAmount = 0.0;
         Acc = 0 ;
        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {

            preparedStatement.setString(1,this.receiverId );
            

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Retrieve the CurrentAmount from the result set
                    currentAmount = resultSet.getDouble("CurrentAmount");
                    Acc = resultSet.getInt("AccNum");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        
        currentAmount += this.amm ;
        
        
        updateQuery = "UPDATE currentBalance SET CurrentAmount = ? WHERE UserId = ?";

        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1,currentAmount);
            preparedStatement.setString(2, this.receiverId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                
                try (Connection con1 = DriverManager.getConnection(url, uname, pass)) {

                    
                    String insertQuery = "INSERT INTO accountActivity (UserId, AccNum, description, amountInvolved, DOBOfActivity)"
                    		+ "		      VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement preparedStatement1 = con1.prepareStatement(insertQuery)) {
                    	preparedStatement1.setString(1,this.receiverId) ;
                    	preparedStatement1.setInt(2, this.receiverAcc);
                        preparedStatement1.setString(3, "Amount Of "  + String.valueOf(this.amm)  +  " is received from Acc No "  + String.valueOf(this.senderAcc)  );
                        preparedStatement1.setDouble(4,this.amm );
                        preparedStatement1.setDate(5, java.sql.Date.valueOf(DateWhenAccCreated));
                        
                        int rowsAffected = preparedStatement1.executeUpdate();

                        if (rowsAffected > 0) {
                            System.out.println("Data inserted successfully.");
                        } else {
                            System.out.println("Data insertion failed.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

   	            	
            } else {
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
		
		
		
		
		
	}
	
	public int tIDGenerator()
	{
	
		Set<Integer> usedNumbers = new HashSet<>();
        Random random = new Random();
        int randomNumber;

        do {
            randomNumber = 78546 + random.nextInt(9000); // Generates a random number between 1000 and 9999
        } while (!usedNumbers.add(randomNumber));

        return randomNumber;
			
	}

	
}
