import java.util.Scanner;
import java.util.StringTokenizer;

public class LogisticDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str;
		System.out.println("Enter the number of iterations, regularization factor and the learning rate");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String string = scanner.nextLine();		
		StringTokenizer stringtokenizer = new StringTokenizer(string, " ");
		LogisticVariables logisticvariables = new LogisticVariables();
		LogisticVariables logisticvariables2 = new LogisticVariables();
		while(stringtokenizer.hasMoreTokens()){
			str = stringtokenizer.nextToken();
			if (str.equals(null)){
				logisticvariables.number_iterations = 100;
				logisticvariables.lambda = 10;
				logisticvariables.learning_rate = 0.01;
				logisticvariables2.number_iterations = 100;
				logisticvariables2.lambda = 10;
				logisticvariables2.learning_rate = 0.01;
			} else {
					if (logisticvariables.number_iterations == 0){
						if(Integer.parseInt(str) == 0){
							logisticvariables.number_iterations = 100;
							logisticvariables2.number_iterations = 100;
						} else {
							logisticvariables.number_iterations = (Integer.parseInt(str));
							logisticvariables2.number_iterations = (Integer.parseInt(str));
						}
					} else if (logisticvariables.lambda == 0){
						if (Integer.parseInt(str) == 0){
							logisticvariables.lambda = 10;
							logisticvariables2.lambda = 10;
						} else{
							logisticvariables.lambda = Integer.parseInt(str);
							logisticvariables2.lambda = Integer.parseInt(str);
						}				
					} else if (logisticvariables.learning_rate == 0.0){
						if (Double.parseDouble(str) == 0.0){
							logisticvariables.learning_rate = 0.01;
							logisticvariables2.learning_rate = 0.01;
						} else{
							logisticvariables.learning_rate = Double.parseDouble(str);
							logisticvariables2.learning_rate = Double.parseDouble(str);
						}				
					}
		  }
			
		}		
		System.out.println("\nAccuracy before removing the stopwords");
		LogisticFileReader logisticfilereader = new LogisticFileReader();
		LogisticFileReader logisticfilereader2 = new LogisticFileReader();
		logisticfilereader = LogisticFileReader.WordBuilder(logisticfilereader2);
		LogisticProcess.weightcalculation(logisticfilereader, logisticvariables);
		LogisticResult.ResultPublish(logisticfilereader);
		System.out.println("\nAccuracy after removing the stopwords");
		LogisticStopWords.stopwords(logisticfilereader2, logisticvariables2);
	}

}
