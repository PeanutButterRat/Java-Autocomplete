package edu.csus.csc131.typeahead.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface SuggestionService {
	/**
	 * Return top 5 suggestions for the input prefix
	 * 
	 * @param prefix input prefix
	 * @return top 5 most frequently appeared words in non-increasing frequency order.
	 * If two words have the same frequency, return in lexicographical order.
	 */
	List<String> getSuggestions(String prefix);
	
	/**
	 * Update the type suggestion data with the input file
	 * 
	 * @param path input file path
	 * @throws IOException
	 */
	void update(Path path) throws IOException;
}