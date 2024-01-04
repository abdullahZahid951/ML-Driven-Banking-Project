package application;


public class MLModelFactory {
	public MLModel createMLModel(String modelType, Object... constructorArgs) {
        if ("Regular".equalsIgnoreCase(modelType)) {
            return new regularModel( (double) constructorArgs[0]); 
        } 
        
        else if ("Premium".equalsIgnoreCase(modelType)) {
            return new premiumModel((double) constructorArgs[0], (double) constructorArgs[1], (int) constructorArgs[2]); // Assuming constructorArgs are monthNo, profitRate, investedAmt, daysToHoldStocks
        } 
        
        else {
            throw new IllegalArgumentException("Invalid model type: " + modelType);
        }
    }
}
