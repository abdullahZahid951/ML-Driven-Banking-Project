package application;


public class LoanFactory {
	
	public LoanManagment createLoans(String LoanType, Object... constructorArgs) {
        if ("Regular".equalsIgnoreCase(LoanType)) {
            return new regularLoan( (String) constructorArgs[0] , (double)constructorArgs[1]   ); 
        } else if ("Premium".equalsIgnoreCase(LoanType)) {
            return new premiumLoan((String) constructorArgs[0] , (double)constructorArgs[1] ); // Assuming constructorArgs are monthNo, profitRate, investedAmt, daysToHoldStocks
        } else {
            throw new IllegalArgumentException("Invalid Loan type: " + LoanType);
        }
    }
}
