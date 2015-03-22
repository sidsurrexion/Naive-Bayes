import java.io.*;
import java.util.*;
import java.util.regex.*;

public class FileReader {
	double probability_spam, probability_ham;
	int spam_numbers, ham_numbers;
	Map<String, Integer> Word_Life = new HashMap<String, Integer>();
    Map<String, Integer> Vocabulary_Spam = new HashMap<String, Integer>();
    Map<String, Integer> Vocabulary_Ham = new HashMap<String, Integer>();
    Map<String, Integer> StopWords = new HashMap<String, Integer>();
    int k = 0;
    
	FileReader(){
		probability_spam = 0.0;
		probability_ham = 0.0;
		spam_numbers = 0;
		ham_numbers = 0;
	}	

	@SuppressWarnings("resource")
	public static FileReader VocabularyBuilder() {
		// TODO Auto-generated method stub
		FileReader filereader = new FileReader();
		String punctuation = "[\\p{Punct}]";
		String numbers = "[0-9]";
		String subject = "Subject";
		String from = "From";
		String to = "To";
		String re = "re";
		String set;
		Pattern pattern = null;
		Pattern p2 = null;
		pattern = Pattern.compile(punctuation);
		p2 = Pattern.compile(numbers);
		Matcher matcher = null;
		Matcher m2 = null;
		String filedirectory = new File("").getAbsolutePath();
		String filedirectory2;
		String filepath_spam, filepath_ham;
		filedirectory2 = filedirectory + "\\" + "train" + "\\" + "ham";
	    filedirectory = filedirectory + "\\" + "train" + "\\" + "spam";        
	    File file = null;
	    File file2 = null;
	    BufferedReader reader = null;
	    FileInputStream fis = null;
	    String[] paths;
	    String[] ham_paths;
	    Map<String, Integer> Word_Life = new HashMap<String, Integer>();
	    Map<String, Integer> Vocabulary_Spam = new HashMap<String, Integer>();
	    Map<String, Integer> Vocabulary_Ham = new HashMap<String, Integer>();
	    int count = 0, counter = 0, s = 0, r = 0;
	    double probability_spam = 0.0, probability_ham = 0.0;
	    int spam_numbers = 0, ham_numbers = 0;
	    Boolean bool;
        try{
        	
        	// Creating the files
        	file = new File(filedirectory);	
        	file2 = new File(filedirectory2);
        	
        	// Array of all files and directory
        	paths = file.list();
        	ham_paths = file2.list();
        	
        	s = paths.length;
        	r = ham_paths.length;
        	
        	probability_spam = (double)s/(double)(s+r);
        	probability_ham = (double)r/(double)(r+s);
        	
        	filereader.probability_spam = probability_spam;
        	filereader.probability_ham = probability_ham;
        	
        	for(String path:paths){
        		filepath_spam = filedirectory + "\\" + path;
        		fis = new FileInputStream(filepath_spam); 
                reader = new BufferedReader(new InputStreamReader(fis));
                
                // Read the first line of the file
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
                		}else if (matcher.find() || m2.find()){
                			continue;
                		} else{
                			spam_numbers++;
                			if (Vocabulary_Spam.isEmpty()){
                				count++;
                				Vocabulary_Spam.put(set,count);
                			} else {
                				bool = Vocabulary_Spam.containsKey(set);
                				if(bool == true){
                					count = Vocabulary_Spam.get(set);
                					count++;
                					Vocabulary_Spam.put(set, count);
                				}else{
                					count++;
                					Vocabulary_Spam.put(set, count);
                				}
                			}
                			count = 0;
                			if (Word_Life.isEmpty()){
                				Word_Life.put(set, counter++);
                			}else{
                				if(Word_Life.containsKey(set)){
                					continue;
                				}else{
                					Word_Life.put(set, counter++);
                				}
                			}
                			
                		}
                	}
                }
                filepath_spam = "";
        	}
        	
        	for(String ham_path:ham_paths){
        		filepath_ham = filedirectory2 + "\\" + ham_path;
        		fis = new FileInputStream(filepath_ham); 
                reader = new BufferedReader(new InputStreamReader(fis));
                
                // Read the first line of the file
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
                		}else
                		if (matcher.find() || m2.find()){
                			continue;
                		} else{
                			ham_numbers++;
                			if (Vocabulary_Ham.isEmpty()){
                				count++;
                				Vocabulary_Ham.put(set,count);
                			} else {
                				bool = Vocabulary_Ham.containsKey(set);
                				if(bool == true){
                					count = Vocabulary_Ham.get(set);
                					count++;
                					Vocabulary_Ham.put(set, count);
                				}else{
                					count++;
                					Vocabulary_Ham.put(set, count);
                				}
                			}
                			count = 0;
                			if (Word_Life.isEmpty()){
                				Word_Life.put(set, counter++);
                			}else{
                				if(Word_Life.containsKey(set)){
                					continue;
                				}else{
                					Word_Life.put(set, counter++);
                				}
                			}
                			
                		}
                	}
                }
                filepath_ham = "";
        	}        	
        	
        } catch (Exception e){
        	// In case of any error
        	e.printStackTrace();
        }
        filereader.ham_numbers = ham_numbers;
        filereader.spam_numbers = spam_numbers;
        filereader.Vocabulary_Ham = Vocabulary_Ham;
        filereader.Vocabulary_Spam = Vocabulary_Spam;
        filereader.Word_Life = Word_Life;
        return filereader;
	}
}
