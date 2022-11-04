package edu.csus.csc131.typeahead.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		
		logger.trace("buildTree completed");
		return new NodeImpl();
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
		return new NodeImpl();
	}

	@Override
	public List<String> getSuggestions(String prefix) {
		logger.trace("getSuggestions started");
		
		List <String> suggestions = new ArrayList<String>();
		Node current = this.getRoot();
		
		// Iterate though the given prefix, searching for the parent node to dfs upon.
		for (int i = 0; i < prefix.length(); i++) {
			current = current.getChild(prefix.charAt(i));
			if (current == null) return suggestions;  // There are no words starting with the given prefix, return.
		}
		suggestions = current.getSuggestions();
		
		logger.trace("getSuggestions completed");		
		return suggestions;
	}

}
