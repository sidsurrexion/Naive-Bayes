import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LogisticStopWords {
 public static void stopwords(LogisticFileReader logisticfilereader, LogisticVariables logisticvariables){
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
        		logisticfilereader.StopWords.put(set, count);
        	}
        }
       LogisticFileReader logisticfilereader2 = new LogisticFileReader();
       logisticfilereader2 = LogisticFileReader.WordBuilder(logisticfilereader);
	   LogisticProcess.weightcalculation(logisticfilereader2, logisticvariables);
	   LogisticResult.ResultPublish(logisticfilereader2);
	} catch (Exception E){
		// In case of any error
    	E.printStackTrace();
	}
 }
}
