import java.io.*;
import java.util.*;
import java.util.regex.*;

public class LogisticFileReader {
	Map<String, Float> Word_Life = new HashMap<String, Float>();
    Map<String, Integer> File_Boolean = new HashMap<String, Integer>();
    Map<String, Map<String,Integer>> Vocabulary_Spam = new HashMap<String, Map<String,Integer>>();
    Map<String, Map<String,Integer>> Vocabulary_Ham = new HashMap<String, Map<String,Integer>>();
    Map<String, Integer> StopWords = new HashMap<String, Integer>();
   float weight_initial = 0.0f;
    
	@SuppressWarnings("resource")
	public static LogisticFileReader WordBuilder(LogisticFileReader logisticfilereader2){
		int spam = 1, ham = 0;
		float weight = 0.0f;
		float weight_initial = 0.0f;
		String punctuation = "[\\p{Punct}]";
		String numbers = "[0-9]";	
		String set;
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
	    Map<String, Float> Word_Life = new HashMap<String, Float>();
	    Map<String, Integer> File_Boolean = new HashMap<String, Integer>();
	    Map<String, Map<String,Integer>> Vocabulary_Spam = new HashMap<String, Map<String,Integer>>();
	    Map<String, Map<String,Integer>> Vocabulary_Ham = new HashMap<String, Map<String,Integer>>();
	    int count = 0;
	    Boolean bool;
	    try{
	    	// Creating the files
        	file = new File(filedirectory);	
        	file2 = new File(filedirectory2);
        	
        	// Array of all files and directory
        	paths = file.list();
        	ham_paths = file2.list();
        	
        	for(String path:paths){
        		filepath_spam = filedirectory + "\\" + path;
        		File_Boolean.put(filepath_spam, spam);
        		fis = new FileInputStream(filepath_spam); 
                reader = new BufferedReader(new InputStreamReader(fis));
                
                // Read the first line of the file
                String line;
                StringTokenizer stringtokenizer = null;
                while ((line = reader.readLine()) != null){
                	stringtokenizer = new StringTokenizer(line, ".:;, ");
                	while(stringtokenizer.hasMoreTokens()){
                		set = stringtokenizer.nextToken();
                		matcher = pattern.matcher(set);
                		m2 = p2.matcher(set);
                		if(!logisticfilereader2.StopWords.isEmpty() && logisticfilereader2.StopWords.containsKey(set)){
                			continue;
                		}else if (set.equals(null)){
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
                			if (Vocabulary_Spam.isEmpty()){
                				count++;
                				Map<String, Integer> vocab_builder = new HashMap<String, Integer>();
                				vocab_builder.put(set, count);
                				Vocabulary_Spam.put(filepath_spam, vocab_builder);
                			} else {
                				bool = Vocabulary_Spam.containsKey(filepath_spam);
                				if(bool == true){
                					Map<String, Integer> vocab_builder = new HashMap<String, Integer>();
                					vocab_builder = Vocabulary_Spam.get(filepath_spam);
                					if(vocab_builder.containsKey(set)){
                						count = vocab_builder.get(set);
                						count++;
                						vocab_builder.put(set, count);
                						Vocabulary_Spam.put(filepath_spam, vocab_builder);
                					} else{
                						count++;
                						vocab_builder.put(set, count);
                						Vocabulary_Spam.put(filepath_spam, vocab_builder);
                					}
                				}else{
                					Map<String, Integer> vocab_builder = new HashMap<String, Integer>();
                					count++;
                					vocab_builder.put(set, count);
                					Vocabulary_Spam.put(filepath_spam, vocab_builder);
                				}
                			}
                			count = 0;
                			if (Word_Life.isEmpty()){
                				weight = (float)(Math.random()*40);
                				Word_Life.put(set, weight);
                			}else{
                				if(Word_Life.containsKey(set)){
                					continue;
                				}else{
                					weight = (float)(Math.random()*40);
                					Word_Life.put(set, weight);
                				}
                			}
                			
                		}
                	}
                }
                filepath_spam = "";
        	}
        	
        	for(String path:ham_paths){
        		filepath_ham = filedirectory2 + "\\" + path;
        		File_Boolean.put(filepath_ham, ham);
        		fis = new FileInputStream(filepath_ham); 
                reader = new BufferedReader(new InputStreamReader(fis));
                
                // Read the first line of the file
                String line;
                StringTokenizer stringtokenizer = null;
                while ((line = reader.readLine()) != null){
                	stringtokenizer = new StringTokenizer(line, ".:;, ");
                	while(stringtokenizer.hasMoreTokens()){
                		set = stringtokenizer.nextToken();
                		matcher = pattern.matcher(set);
                		m2 = p2.matcher(set);
                		if(!logisticfilereader2.StopWords.isEmpty() && logisticfilereader2.StopWords.containsKey(set)){
                			continue;
                		}else if (set.equals(null)){
                			continue;
                		}else if (set.equalsIgnoreCase(subject)){
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
                			if (Vocabulary_Ham.isEmpty()){
                				count++;
                				Map<String, Integer> vocab_builder = new HashMap<String, Integer>();
                				vocab_builder.put(set, count);
                				Vocabulary_Ham.put(filepath_ham, vocab_builder);
                			} else {
                				bool = Vocabulary_Ham.containsKey(filepath_ham);
                				if(bool == true){
                					Map<String, Integer> vocab_builder = new HashMap<String, Integer>();
                					vocab_builder = Vocabulary_Ham.get(filepath_ham);
                					if(vocab_builder.containsKey(set)){
                						count = vocab_builder.get(set);
                						count++;
                						vocab_builder.put(set, count);
                						Vocabulary_Ham.put(filepath_ham, vocab_builder);
                					} else{
                						count++;
                						vocab_builder.put(set, count);
                						Vocabulary_Ham.put(filepath_ham, vocab_builder);
                					}
                				}else{
                					count++;
                					Map<String, Integer> vocab_builder = new HashMap<String, Integer>();
                					vocab_builder.put(set, count);
                					Vocabulary_Ham.put(filepath_ham, vocab_builder);
                				}
                			}
                			count = 0;
                			if (Word_Life.isEmpty()){
                				weight = (float)(Math.random()*40);
                				Word_Life.put(set, weight);
                			}else{
                				if(Word_Life.containsKey(set)){
                					continue;
                				}else{
                					weight = (float)(Math.random()*40);
                					Word_Life.put(set, weight);
                				}
                			}
                			
                		}
                	}
                }
                filepath_ham = "";
        	}
	    }
	    catch (Exception e){
        	// In case of any error
        	e.printStackTrace();
        }
	    LogisticFileReader logisticfilereader = new LogisticFileReader();
	    logisticfilereader.Vocabulary_Spam = Vocabulary_Spam;
	    logisticfilereader.Vocabulary_Ham = Vocabulary_Ham;
	    logisticfilereader.Word_Life = Word_Life;
	    logisticfilereader.File_Boolean = File_Boolean;
	    logisticfilereader.StopWords = logisticfilereader2.StopWords;
	    weight_initial = (float)(Math.random()*2);
	    logisticfilereader.weight_initial = weight_initial;
	    return logisticfilereader;
	}
}
