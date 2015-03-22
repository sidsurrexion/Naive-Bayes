import java.util.Scanner;
import java.util.StringTokenizer;

public class NaiveBayesDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 0;
		String str;
		FileReader filereader = new FileReader();
		FileReader filereader1 = new FileReader();
		System.out.println("Enter the digit for Laplace Smoothing: ");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("");
		String string = scanner.nextLine();
		StringTokenizer stringtokenizer = new StringTokenizer(string);
		while(stringtokenizer.hasMoreTokens()){
			str = stringtokenizer.nextToken();
			if (str.equals(null)){
				filereader1.k = 2;
			}
			else {				
				x = Integer.parseInt(str);
				if (x == 0){
					filereader1.k = 2;
				} else{
					filereader1.k = x;
				}
			}			
		}		
		
		filereader = FileReader.VocabularyBuilder();
		
		System.out.println("Accuracy before removing stop words\n");
		
		SpamCheck.SpamDetermination(filereader, filereader1);		
			
		HamCheck.HamDetection(filereader, filereader1);
		
		System.out.println("Accuracy after removing the stop words\n");
		
		StopWords.Classification(filereader, filereader1);
		
	}

}
