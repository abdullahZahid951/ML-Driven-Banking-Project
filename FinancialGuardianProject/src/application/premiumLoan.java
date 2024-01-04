package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class premiumLoan implements LoanManagment {
	
	String userId ; 
	controlPanel cp ;
	Double UserAskedLoanAmm ;
	String Date ;
	
	public premiumLoan( String userId ,   Double UserAskedLoanAmm      )
	{
		
		this.userId = userId ;
		this.UserAskedLoanAmm = UserAskedLoanAmm ;
		this.cp = new controlPanel(userId) ;
		
		LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.Date =currentDate.format(formatter); 
		
		
        
        
	}
	
	
	
	@Override
	public boolean checkWhetherUserAccHasEnoughAmm()
	{
		
		if(  cp.checkEnoughAmmPresent(80000)     )
		{
			return true ;
		}
		
		return false ;
		
		
	}
	
	
	@Override
	public void grantingUserHisLoanAndUpdatingTheDB() 
	{
		cp.addingLoanAmmToUserCurrentBalanceAndUpdatingDB(this.UserAskedLoanAmm, 1);
		
	}
	
	
}
