package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class premiumModel implements MLModel {
	 int monthNo;
	    double profitRate;
	    double investedAmt;
	    int daysToHoldStocks;
	    Object[][] dataSet = null;
	    controlPanel cp;
	    private double[] weights; // Weights for the simple logistic regression

	    public premiumModel() {

	    }

	    public premiumModel( double profitRate, double investedAmt, int daysToHoldStocks) {
	        cp = new controlPanel();
	        LocalDate currentDate = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formattedDate = currentDate.format(formatter);
	        this.profitRate = profitRate;
	        this.investedAmt = investedAmt;
	        this.daysToHoldStocks = daysToHoldStocks;
	        this.monthNo = currentDate.getMonthValue();

	        this.weights = new double[5]; // Assuming 4 features + bias term
	    }

	    @Override
	    public void fetchingDataSet() {
	        this.dataSet = cp.fetchDataForPremiumModel();
	    }

	    @Override
	    public int predictionViaModel() {
	    	int predictedValue = 0 ;
	    	try {
	            fetchingDataSet(); // Fetch the dataset

	            // Train the model for a specified number of epochs
	            int numEpochs = 10000; // Adjust the number of epochs as needed
	            trainModel(numEpochs);

	            // Now use the trained model for prediction
	            predictedValue = predict(monthNo, profitRate, investedAmt, daysToHoldStocks);
	            //System.out.println("Predicted value: " + predictedValue);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    	return predictedValue ;
	    }
	    @Override
	    public void trainModel(int numEpochs) {
	        for (int epoch = 0; epoch < numEpochs; epoch++) {
	            for (Object[] dataPoint : dataSet) {
	                int actualLabel = (int) dataPoint[dataPoint.length - 1];
	                int predictedLabel = predict(
	                        ((Number) dataPoint[0]).intValue(),
	                        ((Number) dataPoint[1]).doubleValue(),
	                        ((Number) dataPoint[2]).doubleValue(),
	                        ((Number) dataPoint[3]).intValue()
	                );
	                updateWeights(dataPoint, predictedLabel, actualLabel);
	            }
	        }
	    }
	    @Override
	    public void updateWeights(Object[] dataPoint, int predictedLabel, int actualLabel) {
	        double learningRate = 0.01; 

	        // Add a bias term (intercept) to the features
	        double[] featuresWithBias = new double[dataPoint.length];
	        featuresWithBias[0] = 1.0; 

	        for (int i = 1; i < dataPoint.length; i++) {
	            // Convert the data point to double (assuming it's a numeric value)
	            featuresWithBias[i] = convertToDouble(dataPoint[i]);
	        }

	        // Update weights based on the gradient
	        for (int i = 0; i < weights.length; i++) {
	            weights[i] -= learningRate * (predictedLabel - actualLabel) * featuresWithBias[i];
	        }
	    }
	    @Override
	    public double convertToDouble(Object value) {
	        if (value instanceof Number) {
	            return ((Number) value).doubleValue();
	        } else {
	            
	            throw new IllegalArgumentException("Unsupported data type: " + value.getClass());
	        }
	    }
	    
	    public int predict(int monthNo, double profitRate, double investedAmt, int daysToHoldStocks) {
	        // Add a bias term (intercept) to the features
	        double[] featuresWithBias = {1.0, monthNo, profitRate, investedAmt, daysToHoldStocks};

	        // Calculate the weighted sum
	        double weightedSum = 0;
	        for (int i = 0; i < featuresWithBias.length; i++) {
	            weightedSum += weights[i] * featuresWithBias[i];
	        }

	        
	        double probability = sigmoid(weightedSum);

	        // Make a binary decision based on the probability
	       // System.out.println(probability) ;
	        return (probability >= 0.5) ? 1 : 0;
	    }
	    @Override
	    public double sigmoid(double x) {
	        return 1.0 / (1.0 + Math.exp(-x));
	    }

}
