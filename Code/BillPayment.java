package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//import financialGuardianApp.controlPanel;

public class BillPayment {
	
	String UserId ;
	int BillId ;
	String billType ;
	double BillAmm ;
	controlPanel cp ;
    int year , month ;
	
	
	
	BillPayment( String UserId , int BillId , 	double BillAmm ,String billType  )
	{
		this.UserId = UserId ;
		this.BillAmm = BillAmm ;
		this.BillId = BillId ;
		this.billType = billType ;
		
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
		
        this.month = currentDate.getMonthValue(); 
        this.year = currentDate.getYear();
        
        cp = new controlPanel(this.UserId) ;
        
		System.out.println( this.month    ) ;  
		System.out.println( this.year    ) ;  
		
	}
	public void Pay_Bill()
	{
		
		
		cp.payTheBill(this.UserId , this.BillId, this.BillAmm , this.billType, this.month, this.year);
		
		
		
	}
	
	public boolean checkWhetherIsBillIsAlreadyPayedForTheMonthOr()
	{
		
		if( ! cp.CheckBillPayedOrnot(  this.UserId ,  this.billType, this.month, this.year    ) )
		{
			return true ;
		}
		else
		{
			return false ;
		}
		
		 
		
	}
	
	
	
}