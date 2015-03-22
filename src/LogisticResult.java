import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogisticResult {
	public static void ResultPublish(LogisticFileReader logisticfilereader){
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
		filedirectory2 = filedirectory + "\\" + "test" + "\\" + "ham";
	    filedirectory = filedirectory + "\\" + "test" + "\\" + "spam";        
	    File file = null;
	    File file2 = null;
	    BufferedReader reader = null;
	    FileInputStream fis = null;
	    String[] paths;
	    String[] ham_paths;
	    float probability_distribution = 0.0f;
	    float weight_distribution = 0.0f;
	    float accuracy = 0.0f;	    
	   
	    int count = 0;
	    float file_counter = 0.0f , spam_counter = 0.0f , ham_counter = 0.0f;
	    double exponent;
	    Boolean bool;
	    try{
	    	// Creating the files
        	file = new File(filedirectory);	
        	file2 = new File(filedirectory2);
        	
        	// Array of all files and directory
        	paths = file.list();
        	ham_paths = file2.list();
        	for (String path: paths){
        		Map<String, Integer> Data_Spam = new HashMap<String, Integer>();
        		file_counter++;
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
                		if(!logisticfilereader.StopWords.isEmpty() && logisticfilereader.StopWords.containsKey(set)){
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
                			if (Data_Spam.isEmpty()){
                				count++;
                				Data_Spam.put(set, count);
                			} else {
                				bool = Data_Spam.containsKey(set);
                				if(bool == true){
                					count = Data_Spam.get(set);
                 					count++;
                 					Data_Spam.put(set, count);
                					}
                				else{
                					count++;
                 					Data_Spam.put(set, count);
                				}
                			}
                			count = 0;
                		}
                	}
                }
                for(String data_spam: Data_Spam.keySet()){
                	if (logisticfilereader.Word_Life.containsKey(data_spam)){
                		weight_distribution = weight_distribution + Data_Spam.get(data_spam) * logisticfilereader.Word_Life.get(data_spam);
                	} else {
                		weight_distribution = weight_distribution + Data_Spam.get(data_spam) * 0.0f;
                	}
                }
                exponent = (double)(logisticfilereader.weight_initial + weight_distribution);
                try{
                	probability_distribution = (float)(1 / (1 + Math.exp(exponent)));
                } catch (Exception E){
                	probability_distribution = 0.0f;
                }                
                if (probability_distribution >= 0.5){
                	spam_counter++;
                }
                exponent = 0.0;
                weight_distribution = 0.0f;
                probability_distribution = 0.0f;
        	}
        	accuracy = (float)((spam_counter / file_counter) * 100);
        	System.out.println("The accuracy over spam files is: "+accuracy);
        	accuracy = 0.0f;
        	file_counter = 0;
        	
        	for (String path: ham_paths){
        		Map<String, Integer> Data_Ham  = new HashMap<String, Integer>();
        		file_counter++;
        		filepath_ham = filedirectory2 + "\\" + path;
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
                		if(!logisticfilereader.StopWords.isEmpty() && logisticfilereader.StopWords.containsKey(set)){
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
                		} else {
                			if (Data_Ham.isEmpty()){
                				count++;
                				Data_Ham.put(set, count);
                			} else {
                				bool = Data_Ham.containsKey(set);
                				if(bool == true){
                					count = Data_Ham.get(set);
                 					count++;
                 					Data_Ham.put(set, count);
                					}
                				else{
                					count++;
                 					Data_Ham.put(set, count);
                				}
                			}
                			count = 0;
                		}
                	}
                }
                for(String data_ham: Data_Ham.keySet()){
                	if (logisticfilereader.Word_Life.containsKey(data_ham)){
                		weight_distribution = weight_distribution + Data_Ham.get(data_ham) * logisticfilereader.Word_Life.get(data_ham);
                	} else {
                		weight_distribution = weight_distribution + Data_Ham.get(data_ham) * 0.0f;
                	}                	
                }
                exponent = (double)(logisticfilereader.weight_initial + weight_distribution);
                try{
                	probability_distribution = (float)(1 / (1 + Math.exp(exponent)));
                } catch (Exception E){
                	probability_distribution = 0.0f;
                }
                
                if (probability_distribution >= 0.5){
                	ham_counter++;
                }
                exponent = 0.0;
                weight_distribution = 0.0f;
                probability_distribution = 0.0f;
        	}
        	accuracy = (float)((ham_counter / file_counter) * 100);
        	System.out.println("The accuracy over ham files is: "+accuracy);
        	accuracy = 0.0f;
        	file_counter = 0;
	    }
	    catch (Exception e){
	    	// In case of any error
        	e.printStackTrace();
	    }
	}
}
