package application;

import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.Properties;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Registration {
	String name , email ;
	String address;
	double initialAmmountDeposited ;
	private int cnic, AccNum, pin ;
	boolean gender ;
	String DOB, DateWhenAccCreated ;
	boolean premiumAcc = false ;
	boolean regularAcc = false ;
	String UserId ;
	
	
	public int getterForPin()
	{
		return this.pin ;
	}
	
	
	public int getterForAccNo()
	{
		return this.AccNum ;
	}
	
	
	public Registration(String UserId ,String name, String address, 
						double initialAmmountDeposited, 
						int cnic, boolean gender,boolean premiumAcc, 
						boolean regularAcc, String email,
						String DOB )
	{
		this.UserId = UserId ;
		this.name = name ;
		this.address = address ;
		this.initialAmmountDeposited = initialAmmountDeposited ;
		this.cnic = cnic ;
		this.gender = gender ;
		this.email =email ;
		this.DOB = DOB;
		this.premiumAcc = premiumAcc ;
		this.regularAcc = regularAcc ;
		this.pin = pinGenerator();
		this.AccNum = accNoGenerator() ;
		
		LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.DateWhenAccCreated = currentDate.format(formatter); 
	}
	
	public void createThisAccount()
	{
		String url = "jdbc:mysql://localhost:3306/FinancialGuardianDataBase";
        String uname = "root";
        String pass = "scifi123";
        
        try (Connection con = DriverManager.getConnection(url, uname, pass)) {

           
            String insertQuery = "INSERT INTO BankAccount (UserId, name, email, address, initialAmountDeposited, cnic, AccNum, pin, gender, DOB, DateWhenAccCreated, premiumAcc, regularAcc)"
            		+ "		      VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery)) {
            	preparedStatement.setString(1, this.UserId);
            	preparedStatement.setString(2, this.name);
                preparedStatement.setString(3, this.email);
                preparedStatement.setString(4, this.address);
                preparedStatement.setDouble(5, this.initialAmmountDeposited);
                preparedStatement.setInt(6, this.cnic);
                preparedStatement.setInt(7, this.AccNum);
                preparedStatement.setInt(8, this.pin);
                preparedStatement.setBoolean(9, this.gender);
                preparedStatement.setDate(10, java.sql.Date.valueOf(this.DOB));
                
                preparedStatement.setDate(11, java.sql.Date.valueOf(this.DateWhenAccCreated));
                
                
                preparedStatement.setBoolean(12, this.premiumAcc);
                preparedStatement.setBoolean(13, this.regularAcc);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Data inserted successfully.");
                } else {
                    System.out.println("Data insertion failed.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
   
        
        
        
        try (Connection con = DriverManager.getConnection(url, uname, pass)) {

            
            String insertQuery = "INSERT INTO accountActivity (UserId, AccNum, description, amountInvolved, DOBOfActivity)"
            		+ "		      VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery)) {
            	preparedStatement.setString(1,this.UserId) ;
            	preparedStatement.setInt(2, this.AccNum);
                preparedStatement.setString(3, "Initial Amount Deposited When Account Created");
                preparedStatement.setDouble(4, this.initialAmmountDeposited);
                preparedStatement.setDate(5, java.sql.Date.valueOf(this.DateWhenAccCreated));
                
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Data inserted successfully.");
                } else {
                    System.out.println("Data insertion failed.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try (Connection con = DriverManager.getConnection(url, uname, pass)) {

            
            String insertQuery = "INSERT INTO currentBalance (UserId, AccNum, CurrentAmount)"
            		+ "		      VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = con.prepareStatement(insertQuery)) {
            	preparedStatement.setString(1,this.UserId) ;
            	preparedStatement.setInt(2, this.AccNum);
                preparedStatement.setDouble(3, this.initialAmmountDeposited);
                
                int rowsAffected = preparedStatement.executeUpdate();

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
	
	public int pinGenerator()
	{
	
		Set<Integer> usedNumbers = new HashSet<>();
        Random random = new Random();
        int randomNumber;

        do {
            randomNumber = 1000 + random.nextInt(9000); // Generates a random number between 1000 and 9999
        } while (!usedNumbers.add(randomNumber));

        return randomNumber;
			
	}
	
	public int accNoGenerator()
	{
		Set<Integer> usedNumbers = new HashSet<>();
        Random random = new Random();
        int randomNumber;

        do {
            randomNumber = 100000000 + random.nextInt(999999999); 
        } while (!usedNumbers.add(randomNumber));

        return randomNumber;

	}
	
	
	public void ShowMyFinalDetails()
	{
		
		
		
		
	}
	
	
	public void sendAccConfirmationToEmail()
	{
		        
    }
		
}