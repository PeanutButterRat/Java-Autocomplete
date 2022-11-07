package edu.csus.csc131.typeahead.data;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

//ToDo: Add test cases
class TrieImplTest {

	@Test
	void testSerialize() {
	}

	@Test
	void testDeSerialize() {
	}

	@Test
	void testGetSuggestions_1() {
		ArrayList<String> suggestions = new ArrayList<>();
		String prefix = "";
		String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\r\n"
				+ "Curabitur sit amet ex pretium, ultrices metus sit amet, elementum mi.\r\n"
				+ "Cras luctus dapibus scelerisque. Interdum et malesuada fames ac ante ipsum primis in faucibus.\r\n"
				+ "Sed vel tortor scelerisque, ultricies tellus sed, mattis nisl. Morbi sit amet magna nec nisl commodo laoreet ac id diam.\r\n"
				+ "Praesent lacinia, massa ac congue tincidunt, odio mi venenatis elit, at venenatis ex nisl sit amet turpis.\r\n"
				+ "Sed aliquet pretium finibus. Nulla eget lobortis nulla.";
		
		String[] words = {"lorem", "ipsum", "dolor", "sit", "amet"};  // Expected values.
		for (String word : words) {
			suggestions.add(word);
		}
		
		TrieImpl trie = new TrieImpl();
		trie.buildTestTree(text);
		assertEquals(suggestions, trie.getSuggestions(prefix));
	}
	
	@Test
	void testGetSuggestions_2() {
		ArrayList<String> suggestions = new ArrayList<>();
		String prefix = "";
		String text = "FiRst,SeCoNd, ThriRD,foUTH, FifTH";
		
		String[] words = {"first", "second", "third", "fourth", "fifth"};  // Expected values.
		for (String word : words) {
			suggestions.add(word);
		}
		
		TrieImpl trie = new TrieImpl();
		trie.buildTestTree(text);
		assertEquals(suggestions, trie.getSuggestions(prefix));
	}
	
	@Test
	void testGetSuggestions_3() {
		ArrayList<String> suggestions = new ArrayList<>();  // Expected values.
		String prefix = "prefix_is_too_long";
		String text = "";
		
		TrieImpl trie = new TrieImpl();
		trie.buildTestTree(text);
		assertEquals(suggestions, trie.getSuggestions(prefix));
	}
	
	@Test
	void testGetSuggestions_4() {
		ArrayList<String> suggestions = new ArrayList<>();  // Expected values.
		String prefix = "";
		String text = "app apple apples application apricot appearance apprentice";
		
		String[] words = {"app", "apple", "apples", "application", "apricot"};  // Expected values.
		for (String word : words) {
			suggestions.add(word);
		}
		
		TrieImpl trie = new TrieImpl();
		trie.buildTestTree(text);
		assertEquals(suggestions, trie.getSuggestions(prefix));
	}
}
