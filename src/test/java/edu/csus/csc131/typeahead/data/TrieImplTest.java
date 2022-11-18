package edu.csus.csc131.typeahead.data;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;


class TrieImplTest {
	
	public static final String FILEPATH = "data/backup/trie.txt";
	
	@Test
	void testSerialize_1() throws IOException {
		String text = "cat";
		TrieImpl trie = new TrieImpl(FILEPATH);
		trie.setRoot(trie.buildTree(text));
		
		String answer = "{\0:0:[{c:0:[{a:0:[{t:1:[]}]}]}]}";
		
		assertEquals(answer, trie.getRoot().toString());
	}
	
	@Test
	void testSerialize_2() throws IOException {
		String text = "tha thb thc thd";
		TrieImpl trie = new TrieImpl(FILEPATH);
		trie.setRoot(trie.buildTree(text));
		
		String answer = "{\0:0:[{t:0:[{h:0:[{a:1:[]},{b:1:[]},{c:1:[]},{d:1:[]}]}]}]}";
		
		assertEquals(answer, trie.getRoot().toString());
	}

	@Test
	void testDeSerialize_1() throws IOException {
		String text = "Supercalifragilisticexpialidocious";
		String prefix = "Supercalifragilisticexpi";
		
		TrieImpl trie = new TrieImpl(FILEPATH);
		trie.setRoot(trie.buildTree(text));
		ArrayList<String> suggestions = (ArrayList<String>) trie.getSuggestions(prefix);
		
		
		trie.setRoot(new NodeImpl(trie.serialize()));  // Convert the trie into a string and then deserialize it.
		
		assertEquals(suggestions, trie.getSuggestions(prefix));  // Check if the suggestions are the same.
	}
	
	@Test
	void testDeSerialize_2() throws IOException {
		String text = "FiRst";
		String prefix = "f";
		
		TrieImpl trie = new TrieImpl(FILEPATH);
		trie.setRoot(trie.buildTree(text));
		ArrayList<String> suggestions = (ArrayList<String>) trie.getSuggestions(prefix);
		
		
		trie.setRoot(new NodeImpl(trie.serialize()));  // Convert the trie into a string and then deserialize it.
		assertEquals(suggestions, trie.getSuggestions(prefix));  // Check if the suggestions are the same.
	}

	@Test
	void testGetSuggestions_1() throws IOException {
		ArrayList<String> suggestions = new ArrayList<>();
		String prefix = "l";
		String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\r\n"
				+ "Curabitur sit amet ex pretium, ultrices metus sit amet, elementum mi.\r\n"
				+ "Cras luctus dapibus scelerisque. Interdum et malesuada fames ac ante ipsum primis in faucibus.\r\n"
				+ "Sed vel luctus tortor scelerisque, ultricies tellus lorem sed, mattis nisl. Morbi sit amet magna luctus nec nisl commodo laoreet ac id diam.\r\n"
				+ "Praesent lacinia, massa ac congue tincidunt, odio mi venenatis elit, at venenatis ex nisl sit amet turpis.\r\n"
				+ "Sed aliquet pretium finibus. Nulla eget lobortis nulla lorem.";
		
		String[] words = {"lorem", "luctus", "lacinia", "laoreet", "lobortis"};  // Expected values.
		for (String word : words) {
			suggestions.add(word);
		}
		
		TrieImpl trie = new TrieImpl(FILEPATH);
		trie.setRoot(trie.buildTree(text));
		assertEquals(suggestions, trie.getSuggestions(prefix));
	}
	
	@Test
	void testGetSuggestions_2() throws IOException {
		ArrayList<String> suggestions = new ArrayList<>();
		String prefix = "f";
		String text = "FiRst SeCoNd ThriRD foUrTH FifTH";
		
		String[] words = {"fifth",  "first", "fourth"};  // Expected values.
		for (String word : words) {
			suggestions.add(word);
		}
		
		TrieImpl trie = new TrieImpl(FILEPATH);
		trie.setRoot(trie.buildTree(text));
		assertEquals(suggestions, trie.getSuggestions(prefix));
	}
	
	@Test
	void testGetSuggestions_3() throws IOException {
		ArrayList<String> suggestions = new ArrayList<>();  // Expected values.
		String prefix = "prefix_is_too_long";
		String text = "this is a sample text: prefix_is_too_lon";
		
		TrieImpl trie = new TrieImpl(FILEPATH);
		trie.setRoot(trie.buildTree(text));
		assertEquals(suggestions, trie.getSuggestions(prefix));
	}
	
	@Test
	void testGetSuggestions_4() throws IOException {
		ArrayList<String> suggestions = new ArrayList<>();  // Expected values.
		String prefix = "app";
		String text = "app apple apples application apricot appearance apprentice";
		
		String[] words = {"app", "appearance", "apple", "apples", "application"};  // Expected values.
		for (String word : words) {
			suggestions.add(word);
		}
		
		TrieImpl trie = new TrieImpl(FILEPATH);
		trie.setRoot(trie.buildTree(text));
		assertEquals(suggestions, trie.getSuggestions(prefix));
	}
	
	@Test
	void testGetSuggestions_5() throws IOException {
		ArrayList<String> suggestions = new ArrayList<>();  // Expected values.
		String prefix = "th";
		String text = "the them these those that that that them you me two th";
		
		String[] words = {"that", "them", "the", "these", "those"};  // Expected values.
		for (String word : words) {
			suggestions.add(word);
		}
		
		TrieImpl trie = new TrieImpl(FILEPATH);
		trie.setRoot(trie.buildTree(text));
		assertEquals(suggestions, trie.getSuggestions(prefix));
	}
	
	@Test
	void testGetSuggestions_6() throws IOException {
		ArrayList<String> suggestions = new ArrayList<>();  // Expected values.
		String prefix = "how";
		String text = "howitzer howlo howl whose howto howitzer howard howard howitzer whodunnit hown whoisit";
		
		String[] words = {"howitzer", "howard", "howl", "howlo", "hown"};  // Expected values.
		for (String word : words) {
			suggestions.add(word);
		}
		
		TrieImpl trie = new TrieImpl(FILEPATH);
		trie.setRoot(trie.buildTree(text));
		assertEquals(suggestions, trie.getSuggestions(prefix));
	}
}
