package edu.csus.csc131.typeahead.data;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

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
	void testGetSuggestions() throws IOException {
		String document = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
				+ "Curabitur sit amet ex pretium, ultrices metus sit amet, elementum mi. "
				+ "Cras luctus dapibus scelerisque. Interdum et malesuada fames ac ante ipsum primis in faucibus. "
				+ "Sed vel tortor scelerisque, ultricies tellus sed, mattis nisl. Morbi sit amet magna nec nisl commodo laoreet ac id diam. "
				+ "Praesent lacinia, massa ac congue tincidunt, odio mi venenatis elit, at venenatis ex nisl sit amet turpis. "
				+ "Sed aliquet pretium finibus. Nulla eget lobortis nulla.";
		
		TrieImpl trie = new TrieImpl(document);
	}

}
