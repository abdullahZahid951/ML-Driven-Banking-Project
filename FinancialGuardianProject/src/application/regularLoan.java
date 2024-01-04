package application;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class regularLoan implements LoanManagment {
	String userId ; 
	controlPanel cp ;
	Double UserAskedLoanAmm ;
	String Date ;
	
	public regularLoan( String userId ,   Double UserAskedLoanAmm      )
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
		
		if(  cp.checkEnoughAmmPresent(50000)     )
		{
			return true ;
		}
		
		return false ;
		
		
	}
	
	
	@Override
	public void grantingUserHisLoanAndUpdatingTheDB() 
	{
		cp.addingLoanAmmToUserCurrentBalanceAndUpdatingDB(this.UserAskedLoanAmm, 0);
		
	}
}