import java.util.*;

public class LogisticVariables {
	
	Map<String, Float> file_probability_zero = new HashMap<String,Float>();
	Map<String, Float> file_probability_one = new HashMap<String, Float>();
	int number_iterations, weight_initial_low, weight_initial_high,weight_regular_low, weight_regular_high;
	int lambda;
	double learning_rate;
	
	LogisticVariables(){
		number_iterations = 0;
		lambda = 0;
		learning_rate = 0.0;
		weight_regular_low = 0;
		weight_regular_high = 0;
	}
}
