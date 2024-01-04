package application;

public interface MLModel {
	 
	int predictionViaModel() ;
	 void fetchingDataSet() ;
	 void trainModel(int numEpochs) ;
	 void updateWeights(Object[] dataPoint, int predictedLabel, int actualLabel) ;
	 double convertToDouble(Object value) ;
	 //int predict(int monthNo, double profitRate, double investedAmt, int daysToHoldStocks) ;
	 double sigmoid(double x) ;
}
