import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class HamCheck {
	public static void HamDetection(FileReader filereader, FileReader filereader1){
		int count = 0 , counter = 0, word_size = filereader.Word_Life.size();
		double file_counter = 0.0, ham_counter = 0.0, ham_accuracy = 0.0;
		double spam_sum = 0.0, ham_sum = 0.0;
		double spam_probability_sum = 0.0, ham_probability_sum = 0.0;
		String filedirectory = new File("").getAbsolutePath();
		filedirectory = filedirectory + "\\" + "test" + "\\" + "ham"; 
		String filepath;
		String set;
		File file = null;
		BufferedReader reader = null;
	    FileInputStream fis = null;
	    String[] paths;
	    String punctuation = "[\\p{Punct}]";
		String numbers = "[0-9]";
		String subject = "Subject";
		String from = "From";
		String to = "To";
		String re = "re";
		Pattern pattern = null;
		Pattern p2 = null;
		pattern = Pattern.compile(punctuation);
		p2 = Pattern.compile(numbers);
		Matcher matcher = null;
		Matcher m2 = null;		
		Boolean bool;
	    try{
	     file = new File(filedirectory);
	     paths = file.list();
	     for(String path:paths){
	    	 Map<String, Integer> Data_Ham = new HashMap<String, Integer>();
	    	 file_counter++;
	    	 filepath = filedirectory + "\\" + path;
	    	 fis = new FileInputStream(filepath); 
             reader = new BufferedReader(new InputStreamReader(fis));
             
             String line;
             StringTokenizer stringtokenizer = null;
             while ((line = reader.readLine()) != null){
             	stringtokenizer = new StringTokenizer(line, ".:; ");
             	while(stringtokenizer.hasMoreTokens()){
             		set = stringtokenizer.nextToken();
             		matcher = pattern.matcher(set);
             		m2 = p2.matcher(set);
             		if (set.equals(null)){
            			continue;
            		} else if (set.equalsIgnoreCase(subject)){
             			continue;
             		} else if (set.equalsIgnoreCase(from)){
             			continue;
             		} else if (set.equalsIgnoreCase(to)){
             			continue;
             		}else if (set.equalsIgnoreCase(re)){
             			continue;
             		}
             		if (!filereader.StopWords.isEmpty() && filereader.StopWords.containsKey(set)){
           				continue;
             		}else if (matcher.find() || m2.find()){
             			continue;
             		} else{
             			if (Data_Ham.isEmpty()){
             				count++;
             				Data_Ham.put(set,count);
             			} else {
             				bool = Data_Ham.containsKey(set);
             				if(bool == true){
             					count = Data_Ham.get(set);
             					count++;
             					Data_Ham.put(set, count);
             				}else{
             					count++;
             					Data_Ham.put(set, count);
             				}
             			}
             			count = 0;                     
             		}
             	}
             }
             if (spam_sum == 0.0 && ham_sum == 0.0){
        	    	spam_sum = Math.log(filereader.probability_spam);
        	 	    ham_sum = Math.log(filereader.probability_spam);
         	 } 
        	 for (String data_ham : Data_Ham.keySet()){
        		 count = Data_Ham.get(data_ham);
        		 bool = filereader.Vocabulary_Spam.containsKey(data_ham);
        		 if (bool == true){
        			counter =  filereader.Vocabulary_Spam.get(data_ham);
        			spam_probability_sum = (((double)(counter + filereader1.k))/((double)(filereader.spam_numbers) + word_size));
        		 } else {
        			 spam_probability_sum = (((double)(filereader1.k))/((double)(filereader.spam_numbers) + word_size));
        		 }
        		 bool = filereader.Vocabulary_Ham.containsKey(data_ham);
        		 if (bool == true){
        			counter =  filereader.Vocabulary_Ham.get(data_ham);
        			ham_probability_sum = (((double)(counter + filereader1.k))/((double)(filereader.ham_numbers) + word_size));
        		 } else {
        			ham_probability_sum = (((double)(filereader1.k))/((double)(filereader.ham_numbers) + word_size));
        		 }
        		 spam_probability_sum = Math.pow(spam_probability_sum, (double)count);
        		 ham_probability_sum = Math.pow(ham_probability_sum, (double)count);
        		 spam_sum = spam_sum + Math.log(spam_probability_sum);
        		 ham_sum = ham_sum + Math.log(ham_probability_sum);
        	 }
        	 if (ham_sum > spam_sum){
        		 ham_counter++;
        	 }
        	 spam_sum = 0.0;
        	 ham_sum = 0.0;
        	 spam_probability_sum = 0.0;
        	 ham_probability_sum = 0.0;             
	     }
	     ham_accuracy = (ham_counter/file_counter)*100;
	     System.out.println("The accuracy over "+file_counter+"  ham files is: "+ham_accuracy+" %\n");
	    }
	    catch (Exception e){
        	// In case of any error
        	e.printStackTrace();
        }
	}
}
