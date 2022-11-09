package edu.csus.csc131.typeahead.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//ToDo: provide real implementation for this class
@Component
public class TrieImpl extends Trie {
	private static Logger logger = LoggerFactory.getLogger(TrieImpl.class);	

	public TrieImpl(@Value("${backup.file}") String backupFilePath) throws IOException {
		super(backupFilePath);
	}

	@Override
	Node buildTree(String str) {
		logger.trace("buildTree started");
		StringTokenizer st = new StringTokenizer(str," ");
		int wordCount = st.countTokens();
		String arr[];
		arr = new String[wordCount];
		String words = String.valueOf(wordCount);
		logger.trace("Number of words in the document: " + words);
		//Loads the Tokens into an array
		for(int i =0; i < wordCount; i++) {
			arr[i] = st.nextToken();
		}
		
		int freq = 0;
		String res = null;
		String topWord[];
		topWord = new String[5];
		int topWordCount = 0;
		
		for (int i = 0; i < wordCount; i++) {
	        int count = 0;
	        for (int j = i + 1; j < wordCount; j++) {
	            if (arr[j] == arr[i]) {
	                count++;
	            }
	        }
		
	        if (count >= freq) {
	            res = arr[i];
	            freq = count;
	            
	            if(topWordCount <= 4) {
	            	topWord[topWordCount] = res;
	            }
	            else {
	            	topWordCount = 0;
	            	topWord[topWordCount] = res;
	            }
	        }
	    }
		
		
		logger.trace("The Most common words are: "+ topWord[0]+ topWord[1]+ topWord[2]+ topWord[3]+ topWord[3]);
		logger.trace("buildTree completed");
		return new NodeImpl('\0');
	}

	@Override
	String serialize() {
		logger.trace("serialize started");		

		logger.trace("serialize completed");		
		return "test" + System.currentTimeMillis();
	}

	@Override
	Node deSerialize(String str) {
		logger.trace("deSerialize started");		
		
		logger.trace("deSerialize completed");	
		return new NodeImpl('\0');
	}

	@Override
	public List<String> getSuggestions(String prefix) {
		logger.trace("getSuggestions started");	
		List <String> words =new ArrayList<String>();
		Node working = this.getRoot();
		for(int i =0; i<prefix.length();i++) {
			working=working.getChild(prefix.charAt(i));
			if (working == null) return words;
		}
		words = working.getSuggestions();
		logger.trace("getSuggestions completed");		
		return words;
	}

}
