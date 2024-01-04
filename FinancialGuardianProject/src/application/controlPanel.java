package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class controlPanel {
	
	private String targetedUserId  ;
	
	public controlPanel(  )
	{
		
	}
	
	
	public controlPanel( String targetedUserId  )
	{
		this.targetedUserId = targetedUserId ;
	}
	
	public void changePin()
	{
		
	}
	
	public boolean checkWhetherThisUserIdExistsOrNotInDB(String UserId)
	{
		
		String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
        
		
        boolean isValid = false;

        String selectQuery = "SELECT * FROM BankAccount WHERE UserId = ?";
        try (Connection con = DriverManager.getConnection(url, uname, pass);
                PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {

               preparedStatement.setString(1, UserId);

               try (ResultSet resultSet = preparedStatement.executeQuery()) {
                   if (resultSet.next()) {
                       // User with the given UserId and pin exists
                       isValid = true;
                   }
               }

           } catch (SQLException e) {
               e.printStackTrace();
           }

           return isValid;
	}
	public boolean CheckBillPayedOrnot(String UserId , 	String billType , int month  , int year)
	{
        String sql = "SELECT COUNT(*) FROM billPaymentActivity WHERE UserId = ? AND BillType = ? AND Month = ? AND Year = ?";
        String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
        
        try (
            // Establish a connection
            Connection connection = DriverManager.getConnection(url, uname, pass);

            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            // Set the parameters for the statement
            preparedStatement.setString(1, UserId);
            preparedStatement.setString(2, billType);
            preparedStatement.setInt(3, month);
            preparedStatement.setInt(4, year);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check the result
            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                return rowCount > 0; // If rowCount > 0, the record exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return false in case of an error
        return false;

	}
	public boolean checkEnoughAmmPresent(double billAmm )
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

            preparedStatement.setString(1,this.targetedUserId  );
            

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


		if (currentAmount  >= billAmm)
		{
			return true ;
		}	
		
		return false ;

	}
	public void currentBal()
	{
		
	}
	public void withdrawMoney(double AmmWithdrawn )
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

            preparedStatement.setString(1,targetedUserId );
            

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

        
        currentAmount -= AmmWithdrawn ;
        
        String updateQuery = "UPDATE currentBalance SET CurrentAmount = ? WHERE UserId = ?";

        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1,currentAmount - 30 );
            preparedStatement.setString(2, targetedUserId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                
            	
            	
            	

                
                try (Connection con1 = DriverManager.getConnection(url, uname, pass)) {

                    
                    String insertQuery = "INSERT INTO accountActivity (UserId, AccNum, description, amountInvolved, DOBOfActivity)"
                    		+ "		      VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement preparedStatement1 = con1.prepareStatement(insertQuery)) {
                    	preparedStatement1.setString(1,targetedUserId) ;
                    	preparedStatement1.setInt(2, Acc);
                        preparedStatement1.setString(3, "Amount Of "  + String.valueOf(AmmWithdrawn)  +  " Withdrawn  And Tax Of Rs. 30 Rupees Also Deducted. ");
                        preparedStatement1.setDouble(4,AmmWithdrawn + 30 );
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
	
	public void payTheBill(String UserId ,int BillId ,double BillAmmDeposited , 
			String billType , int month  , int year   )
	{
		
		String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
        
		
		
		
		
		
		String selectQuery = "SELECT CurrentAmount, AccNum FROM currentBalance WHERE UserId = ?";
        double currentAmount = 0.0;
        int Acc = 0 ;
        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {

            preparedStatement.setString(1,targetedUserId );
            

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

        
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String DateWhenAccCreated = currentDate.format(formatter); 
        
        
        
       // currentAmount -= BillAmmDeposited - 50  ;
        //currentAmount -= 100 ; 
	
        String updateQuery = "UPDATE currentBalance SET CurrentAmount = ? WHERE UserId = ?";

        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1,currentAmount - BillAmmDeposited - 50 );
            preparedStatement.setString(2, targetedUserId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                
                
                try (Connection con1 = DriverManager.getConnection(url, uname, pass)) {

                    
                    String insertQuery = "INSERT INTO accountActivity (UserId, AccNum, description, amountInvolved, DOBOfActivity)"
                    		+ "		      VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement preparedStatement1 = con1.prepareStatement(insertQuery)) {
                    	preparedStatement1.setString(1,targetedUserId) ;
                    	preparedStatement1.setInt(2, Acc);
                        preparedStatement1.setString(3, billType + " Bill Payed Of Amm " + String.valueOf(BillAmmDeposited) + " and also Rs.50 Deducted for Online Bill Payment"  );
                        preparedStatement1.setDouble(4,BillAmmDeposited + 50  );
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


             // SQL query for insertion
                String sql = "INSERT INTO billPaymentActivity (UserId, BillId, BillType, BillAmount, Month, Year) VALUES (?, ?, ?, ?, ?, ?)";

                try (
                    // Establish a connection
                    Connection connection = DriverManager.getConnection(url, uname, pass);
                    
                    // Prepare the statement
                		PreparedStatement  preparedStatement1 = connection.prepareStatement(sql)
                ) {
                    // Set the parameters for the statement
                    preparedStatement1.setString(1, UserId);
                    preparedStatement1.setInt(2, BillId);
                    preparedStatement1.setString(3, billType);
                    preparedStatement1.setDouble(4, BillAmmDeposited);
                    preparedStatement1.setInt(5, month);
                    preparedStatement1.setInt(6, year);

                    // Execute the update
                    int rowsAffected = preparedStatement1.executeUpdate();

                    // Check if the insertion was successful
                    if (rowsAffected > 0) {
                        System.out.println("Data inserted successfully.");
                    } else {
                        System.out.println("Failed to insert data.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                      	
            	
            } else {
                
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}
	
	public Object[][] AccessMyMoneyHistory(int month)
	{
        String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
        String selectQuery = "SELECT * FROM accountActivity WHERE UserId = ? AND MONTH(DOBOfActivity) = ?";

        int noOfRows = 0;

        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, targetedUserId);
            preparedStatement.setInt(2, month);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    noOfRows++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //------------------------------------------------------------
        System.out.println();
        System.out.println("Number of Rows: " + noOfRows);
        System.out.println();
        //------------------------------------------------------------

        Object[][] resultArray = new Object[noOfRows][5];

        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, targetedUserId);
            preparedStatement.setInt(2, month);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int row = 0;
                while (resultSet.next()) {
                    // Process each row of the result set
                    String activityUserId = resultSet.getString("UserId");
                    int accNum = resultSet.getInt("AccNum");
                    String description = resultSet.getString("description");
                    double amountInvolved = resultSet.getDouble("amountInvolved");
                    String dobOfActivity = resultSet.getString("DOBOfActivity");

                    // Store the retrieved data in the array
                    resultArray[row][0] = activityUserId;
                    resultArray[row][1] = accNum;
                    resultArray[row][2] = description;
                    resultArray[row][3] = amountInvolved;
                    resultArray[row][4] = dobOfActivity;

                    row++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultArray;

				
		
	}

	
	public String MessageToFinancialGuardian( String usersMsg   )
	{
		 
		
		
		
		
		
		return " Message Sent Successfully......" ;
	}
	
	public boolean validLoginCheck( String UserId, int pin   )
	{
		String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
        
		
        boolean isValid = false;

        String selectQuery = "SELECT * FROM BankAccount WHERE UserId = ? AND pin = ?";

        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, UserId);
            preparedStatement.setInt(2, pin);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // User with the given UserId and pin exists
                    isValid = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValid;
        
        
        
        
        
        
 	}
	
	
	
	
	public void DepositMoney( double AmmDeposited  )
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

            preparedStatement.setString(1,targetedUserId );
            

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

        
        currentAmount += AmmDeposited ;
        
        String updateQuery = "UPDATE currentBalance SET CurrentAmount = ? WHERE UserId = ?";

        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1,currentAmount);
            preparedStatement.setString(2, targetedUserId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                
                try (Connection con1 = DriverManager.getConnection(url, uname, pass)) {

                    
                    String insertQuery = "INSERT INTO accountActivity (UserId, AccNum, description, amountInvolved, DOBOfActivity)"
                    		+ "		      VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement preparedStatement1 = con1.prepareStatement(insertQuery)) {
                    	preparedStatement1.setString(1,targetedUserId) ;
                    	preparedStatement1.setInt(2, Acc);
                        preparedStatement1.setString(3, "Amount Of "  + String.valueOf(AmmDeposited)  +  " Deposited");
                        preparedStatement1.setDouble(4,AmmDeposited );
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

	public  Object[][]  fetchDataForRegularModel()
	{
		String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
	    String uname = "root";
	    String pass = "scifi123";

	    Object[][] dataArray = null;

	    try {
	        // Establish a connection to the database
	        Connection connection = DriverManager.getConnection(url, uname, pass);

	        // SQL query to select all rows from the dataForRegularModel table
	        String sql = "SELECT * FROM dataForRegularModel";

	        // Create a PreparedStatement
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);

	        // Execute the query and get the result set
	        ResultSet resultSet = preparedStatement.executeQuery();

	        // Get the metadata to determine the number of columns
	        int columnCount = resultSet.getMetaData().getColumnCount();

	        // List to store rows
	        List<Object[]> rows = new ArrayList<>();

	        // Iterate through the result set and store each row in the list
	        while (resultSet.next()) {
	            Object[] row = new Object[columnCount];
	            for (int i = 1; i <= columnCount; i++) {
	                row[i - 1] = resultSet.getObject(i);
	            }
	            rows.add(row);
	        }

	        // Convert the list to a two-dimensional array
	        dataArray = new Object[rows.size()][];
	        rows.toArray(dataArray);

	        // Close resources
	        resultSet.close();
	        preparedStatement.close();
	        connection.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return dataArray;
	}
	
	public  Object[][]  fetchDataForPremiumModel()
	{

		String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
        
        Object[][] dataArray = null ;
		
		
		try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(url, uname, pass);

            // SQL query to select all rows from the dataForPremiumModel table
            String sql = "SELECT * FROM dataForPremiumModel";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Execute the query and get the result set
            ResultSet resultSet = preparedStatement.executeQuery();

            // Get the metadata to determine the number of columns
            int columnCount = resultSet.getMetaData().getColumnCount();

            // List to store rows
            List<Object[]> rows = new ArrayList<>();

            // Iterate through the result set and store each row in the list
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                rows.add(row);
            }

            // Convert the list to a two-dimensional array
            dataArray = new Object[rows.size()][];
            rows.toArray(dataArray);

            // Close resources
            resultSet.close();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataArray;
	}

	
	public void addingLoanAmmToUserCurrentBalanceAndUpdatingDB( double AmmDeposited , int loanType  )
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

            preparedStatement.setString(1,targetedUserId );
            

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

        
        currentAmount += AmmDeposited ;
        
        String updateQuery = "UPDATE currentBalance SET CurrentAmount = ? WHERE UserId = ?";

        try (Connection con = DriverManager.getConnection(url, uname, pass);
             PreparedStatement preparedStatement = con.prepareStatement(updateQuery)) {

            preparedStatement.setDouble(1,currentAmount);
            preparedStatement.setString(2, targetedUserId);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                
            	
            	
            	

                
                try (Connection con1 = DriverManager.getConnection(url, uname, pass)) {

                    
                    String insertQuery = "INSERT INTO accountActivity (UserId, AccNum, description, amountInvolved, DOBOfActivity)"
                    		+ "		      VALUES (?, ?, ?, ?, ?)";

                    try (PreparedStatement preparedStatement1 = con1.prepareStatement(insertQuery)) {
                    	preparedStatement1.setString(1,targetedUserId) ;
                    	preparedStatement1.setInt(2, Acc);
                        preparedStatement1.setString(3, "Amount Of "  + String.valueOf(AmmDeposited)  +  " Loan Granted To You");
                        preparedStatement1.setDouble(4,AmmDeposited );
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

		
        
        
        if (loanType  == 1  )
        {
        	 try (Connection con1 = DriverManager.getConnection(url, uname, pass)) {

                 
                 String insertQuery = "INSERT INTO tableForManagingLoans (UserId, wholeAmmOfLoanToReturned, LoanAmmReturned, dateWhenLoanIsTaken)"
                 		+ "		      VALUES (?, ?, ?, ?)";

                 try (PreparedStatement preparedStatement1 = con1.prepareStatement(insertQuery)) {
                 	preparedStatement1.setString(1,targetedUserId) ;
                 	preparedStatement1.setDouble(2,AmmDeposited * 1.3 );
                     preparedStatement1.setDouble(3,0.0);
                     preparedStatement1.setDate(4, java.sql.Date.valueOf(DateWhenAccCreated));
                     
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
        }
        else if (loanType  == 0  )
        {
        	
        	
        	try (Connection con1 = DriverManager.getConnection(url, uname, pass)) {

                
                String insertQuery = "INSERT INTO tableForManagingLoans (UserId, wholeAmmOfLoanToReturned, LoanAmmReturned, dateWhenLoanIsTaken)"
                		+ "		      VALUES (?, ?, ?, ?)";

                try (PreparedStatement preparedStatement1 = con1.prepareStatement(insertQuery)) {
                	preparedStatement1.setString(1,targetedUserId) ;
                	preparedStatement1.setDouble(2,AmmDeposited * 1.5 );
                    preparedStatement1.setDouble(3,0.0);
                    preparedStatement1.setDate(4, java.sql.Date.valueOf(DateWhenAccCreated));
                    
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

        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        }
        
		
	}
	
	public boolean CheckWhetherUserHasAlreadyTakenLoanOrNot(String UserId)
	{
		
		String sql = "SELECT COUNT(*) FROM tableForManagingLoans WHERE UserId = ? ";
        String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
        
        try (
            // Establish a connection
            Connection connection = DriverManager.getConnection(url, uname, pass);

            // Prepare the statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            // Set the parameters for the statement
            preparedStatement.setString(1, UserId);
            
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();

            // Check the result
            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                return rowCount > 0; // If rowCount > 0, the record exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

		
		return false ;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
