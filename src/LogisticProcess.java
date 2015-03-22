import java.util.*;

public class LogisticProcess {
	public static void weightcalculation(LogisticFileReader logisticfilereader, LogisticVariables logisticvariables){		
      float weight_per_file = 0.0f;
	  float probability_zero = 0.0f;
	  float probability_one = 0.0f;
	  float new_weight = 0.0f;
	  double exponent = 0.0;
	  float weight_individual_word = 0.0f, probability_distribution = 0.0f, mid_weight = 0.0f;
	  for (int i = 0; i < logisticvariables.number_iterations; i++){
		for(String filepath: logisticfilereader.File_Boolean.keySet()){
			Map<String,Integer> vocabulary = new HashMap<String, Integer>();
			if (logisticfilereader.Vocabulary_Spam.containsKey(filepath)){
				vocabulary = logisticfilereader.Vocabulary_Spam.get(filepath);
			} else if (logisticfilereader.Vocabulary_Ham.containsKey(filepath)){
				vocabulary = logisticfilereader.Vocabulary_Ham.get(filepath);
			} else {
				continue;
			}
			for(String word: logisticfilereader.Word_Life.keySet()){
				if (vocabulary.containsKey(word)){
					weight_per_file = weight_per_file + logisticfilereader.Word_Life.get(word) * vocabulary.get(word);
				} else {
					weight_per_file = weight_per_file + logisticfilereader.Word_Life.get(word) * 0.0f;
				}
			}
			try{				
				exponent = (double)(logisticfilereader.weight_initial + weight_per_file);
				probability_zero = (float) (1/(1 + Math.exp(exponent)));
				probability_one = 1 - probability_zero;
				
			} 
			catch(Exception e){
				probability_zero = 0.0f;
				probability_one = 1 - probability_zero;
			}
			logisticvariables.file_probability_zero.put(filepath, probability_zero);
			logisticvariables.file_probability_one.put(filepath, probability_one);
			weight_per_file = 0.0f;
			exponent = 0.0;
			probability_zero = 0.0f;
			probability_one = 0.0f;
		}
		for(String word: logisticfilereader.Word_Life.keySet()){
			for(String filepath: logisticfilereader.File_Boolean.keySet()){
				Map<String,Integer> vocabulary = new HashMap<String, Integer>();
				if (logisticfilereader.Vocabulary_Spam.containsKey(filepath)){
					vocabulary = logisticfilereader.Vocabulary_Spam.get(filepath);
				} else if (logisticfilereader.Vocabulary_Ham.containsKey(filepath)){
					vocabulary = logisticfilereader.Vocabulary_Ham.get(filepath);
				} else {
					continue;
				}
				if(vocabulary.containsKey(word)){
					weight_individual_word = vocabulary.get(word) * logisticfilereader.Word_Life.get(word);
				} else {
					weight_individual_word = logisticfilereader.Word_Life.get(word) * 0.0f;
				}
				probability_distribution = logisticfilereader.File_Boolean.get(filepath) - logisticvariables.file_probability_one.get(filepath);
				mid_weight = mid_weight + weight_individual_word * probability_distribution;
			}
			try {
				new_weight = (float) (logisticfilereader.Word_Life.get(word) + logisticvariables.learning_rate * mid_weight 
						 - logisticvariables.learning_rate * logisticvariables.lambda * logisticfilereader.Word_Life.get(word));
			} catch(Exception e){
				new_weight = 0.0f;
			}			
			logisticfilereader.Word_Life.put(word, new_weight);
			weight_individual_word = 0.0f;
			mid_weight = 0.0f;
			probability_distribution = 0.0f;
			new_weight = 0.0f;
		}
	  }	
	}
}
