package edu.csus.csc131.typeahead.data;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.lang.Character;
import java.util.HashMap;


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
		
		NodeImpl root = new NodeImpl();  // Root of the trie.		
		ArrayList<String> words = new ArrayList<>();  // Words in the document.
		
		// Iterate through the entire string, separating words by non-alphanumeric characters.
		int start = 0;
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isLetterOrDigit(str.charAt(i))) {
				words.add(str.substring(start, i));
				
				// Find the next alphanumeric character to start the next string on.
				start = i + 1;
				while (start < str.length() && !Character.isLetterOrDigit(str.charAt(start))) {
					start++;
				}
				
				i = start;  // i will point to one position after start on the next iteration.
			}
		}
		
		// Add the last word if applicable.
		if (str.length() - start > 0) {
			words.add(str.substring(start, str.length()));
		}
		
		// Iterate over each word in the document, formatting it, and adding it to the trie.
		for (String word : words) {
			if(word.length() >= 3) {  // All valid words must be at least 3 characters in length.
				this.addWord(word.toLowerCase(), root);
			}
		}

		logger.trace("buildTree completed");
		
		return root;
	}
	
	private void addWord(String word,NodeImpl root) {
		char letter = word.charAt(0);
		String slice = word.substring(1, word.length());  // Remove the first letter from the prefix (recursive step).
		
		root.addChild(letter);
		NodeImpl next = root.getChild(letter);  // Next node build the trie from.
		
		// Base case: reached the end of the word.
		if(slice.length() == 0) {
			next.incrementCount();
			next.setWord(true);
			return;
		}
		
		this.addWord(slice, next);
	}

	@Override
	String serialize() {
		logger.trace("serialize started");		
		
		String data = getRoot().toString();
		
		logger.trace("serialize completed");		
		return data;
	}

	@Override
	Node deSerialize(String str) {
		logger.trace("deSerialize started");
		
		NodeImpl root = new NodeImpl(str);
		
		logger.trace("deSerialize completed");	
		return root;
	}
	
	@Override
	public List<String> getSuggestions(String prefix) {
		logger.trace("getSuggestions started");
		
		List<String> suggestions = new ArrayList<String>();
		Node current = this.getRoot();
		
		// Iterate though the given prefix, searching for the parent node to dfs upon.
		for (int i = 0; i < prefix.length(); i++) {
			current = current.getChild(prefix.charAt(i));
			if (current == null) return suggestions;  // There are no words starting with the given prefix, return.
		}
		
		suggestions = current.getSuggestions();
		

		// Remove the first letter of the prefix because it has already been added to the suggestion.
		String shavedPrefix = prefix.substring(0, prefix.length() - 1);
		for(int i = 0; i < suggestions.size(); i++) {
			suggestions.set(i, shavedPrefix + suggestions.get(i));
		}
		
		// Groom the results.
		suggestions.sort(null);  // First sort the words alphabetically.
		bubbleSortByOccurrence(suggestions);  // Stable sort by occurrence to finalize the order.
		
		// Extract the top 5 results if there are more than five results.
		if (suggestions.size() > 5) {
			suggestions = suggestions.subList(0, 5);  // Extract the top 5 results if there are more than five results.
		}
		
		logger.trace("getSuggestions completed");		
		return suggestions;
	}
	
	
	/**
	 * Sorts the suggestions extracted from the trie by the occurrence of each word in place.
	 */
	private void bubbleSortByOccurrence(List<String> words) {
		HashMap<String, Integer> occurrences = new HashMap<>();
		
		// Get the occurrence of  each word within the trie.
		for (String word : words) {
			occurrences.put(word, getWordOccurrence(word));
		}
		
		// Bubble sort the occurrences while maintaining the corresponding order in words.
		for (int count = 0; count < words.size() - 1; count++) {
			for (int i = 0; i < words.size() - 1; i++) {
				String first = words.get(i);
				String second = words.get(i + 1);
				
				if (occurrences.get(first) < occurrences.get(second)) {
					Collections.swap(words, i, i + 1);
				}
			}
		}
	}
	
	
	/**
	 * Returns the number of occurrences of the given word within the trie.
	 */
	private int getWordOccurrence(String word) {
		Node current = this.getRoot();
		
		for (int i = 0; i < word.length(); i++) {
			current = current.getChild(word.charAt(i));
			if (current == null) return 0;
		}
		
		return current.getCount();
	}
}
