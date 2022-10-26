package edu.csus.csc131.typeahead.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.csus.csc131.typeahead.data.Trie;

@Service
public class SuggestionServiceImpl implements SuggestionService {
	private Trie trie;
	
	SuggestionServiceImpl(Trie trie) {
		this.trie = trie;
	}
	
	public List<String> getSuggestions(String prefix) {
		return trie.getSuggestions(prefix);
	}
	
	public void update(Path path) throws IOException {
		trie.update(path);
	}

}
