package edu.csus.csc131.typeahead.data;

import java.util.List;

/**
 * Trie Node interface
 *
 */
public interface Node {

	/**
	 * @return true if the node represents a word, false otherwise
	 */
	boolean isWord();

	/**
	 * Set the node to represent a word or not
	 * 
	 */
	void setWord(boolean isWord);
	
	/**
	 * Return the child node associated with the given character
	 */	
	Node getChild(Character c);

	/**
	 * If there is a child node associated with the given character,
	 * return false. Otherwise, add a child Node for the given 
	 * character and return true.
	 */
	boolean addChild(Character c);

	/**
	 * If there is a child node associated with the given character,
	 * remove it and return true. Otherwise, return false.
	  */
	boolean removeChild(Character c);

	/**
	 * Return top suggestions for a prefix ends at the current node
	 */	
	List<String> getSuggestions();
	
    /**
	 * Returns the number of words that end at the current node.
	 */	
	int getCount();
	
	
	/**
	 * Converts node to a string representation for serialization.
	 */	
	String toString();
}