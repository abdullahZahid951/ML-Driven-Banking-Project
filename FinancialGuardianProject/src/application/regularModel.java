package application;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class regularModel implements MLModel{

    int monthNo;
    double investedAmt;
    Object[][] dataSet = null;
    controlPanel cp;
    private double[] weights; // Weights for the simple logistic regression
    
    
    public regularModel(double investedAmt)
    {
    	 cp = new controlPanel();
         LocalDate currentDate = LocalDate.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         String formattedDate = currentDate.format(formatter);
         this.investedAmt = investedAmt;
         this.monthNo = currentDate.getMonthValue();

         this.weights = new double[3];
         
         
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
            int numEpochs = 1000; // Adjust the number of epochs as needed
            trainModel(numEpochs);

            // Now use the trained model for prediction
            predictedValue = predict(monthNo, investedAmt);
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
                        ((Number) dataPoint[1]).doubleValue()
                        
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
    
    
    public int predict(int monthNo,double investedAmt) {
        // Add a bias term (intercept) to the features
        double[] featuresWithBias = {1.0, monthNo, investedAmt};

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
