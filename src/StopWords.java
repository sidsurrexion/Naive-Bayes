import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class StopWords {
	public static void Classification(FileReader filereader, FileReader filereader1){
		int count = 0;
		String filedirectory = new File("").getAbsolutePath();
		String filepath = filedirectory + "\\" + "stopwords.txt";
		String set;
		BufferedReader reader = null;
	    FileInputStream fis = null;
	    try{
	    	fis = new FileInputStream(filepath); 
            reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            StringTokenizer stringtokenizer = null;
            while ((line = reader.readLine()) != null){
            	stringtokenizer = new StringTokenizer(line, " ");
            	while(stringtokenizer.hasMoreTokens()){
            		count++;
            		set = stringtokenizer.nextToken();
            		filereader.StopWords.put(set, count);
            		if(filereader.Vocabulary_Spam.containsKey(set)){
            			filereader.spam_numbers = filereader.spam_numbers - filereader.Vocabulary_Spam.get(set);
            			filereader.Vocabulary_Spam.remove(set);
            		}
            		if(filereader.Vocabulary_Ham.containsKey(set)){
            			filereader.ham_numbers = filereader.ham_numbers - filereader.Vocabulary_Ham.get(set);
            			filereader.Vocabulary_Ham.remove(set);
            		}
            		if(filereader.Word_Life.containsKey(set)){
            			filereader.Word_Life.remove(set);
            		}
            	}
            }
            
            SpamCheck.SpamDetermination(filereader, filereader1);
    		
    		HamCheck.HamDetection(filereader, filereader1);
    		
	    }
	    catch (Exception e){
        	// In case of any error
        	e.printStackTrace();
        }
	}
}
