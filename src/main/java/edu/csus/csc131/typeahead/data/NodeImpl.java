package edu.csus.csc131.typeahead.data;

import java.util.ArrayList;
import java.util.List;


public class NodeImpl implements Node {

    private ArrayList<NodeImpl> children = new ArrayList<NodeImpl>();  // Stores the children nodes of this.
    private int count = 0;  // Stores number of words that end in the trie on this node.
    private char letter;  // Letter that the trie node represents.
    private boolean isWord = false;  // Tracks if this node ends a valid word.

    public NodeImpl() {
    	this.letter = '\0';
    }
    
    public NodeImpl(char letter) {
        this.letter = letter;
    }
    
    
    public NodeImpl(String string) {
    	if (string.isEmpty()) {  // Backup is empty, return empty node.
    		this.letter = '\0';
    		this.count = 0;
    		this.isWord = false;
    		return;
    	}
    	
    	String slice = string.substring(1, string.length() - 1);
        String[] sections = slice.split(":", 3);

        // Store the node's data.
        this.letter = sections[0].charAt(0);
        this.count = Integer.parseInt(sections[1]);
        
        // If there are instances of the last word ending at the given node, it is a word.
        if (this.count > 0) {
        	this.isWord = true;
        }
        
        String childrenSubstring = sections[2].substring(1, sections[2].length());  // Array of nodes in string format minus the opening bracket '['.
        int braceCount = 0;  // Tracks the number of braces like a stack.
        ArrayList<String> children = new ArrayList<>();  // String representations of the children nodes within the array.
        int start = 0;  // Tracks the starting index of the child substring.
        
        for (int end = 0; end < childrenSubstring.length(); end++) {
            char character = childrenSubstring.charAt(end);

            if (character == ']' && braceCount == 0) {  // Found the end of the array, no more children.
                break;
            }
            else if (character == '{') {  // Found an opening brace, could be a child or nested child.
                braceCount++;
            }

            if (character == '}') {  // Found a closing brace, either a child or nested child definition ends here.
                braceCount--;

                if (braceCount == 0) {  // If the stack is empty, the closing brace signified a child, not a nested one. Add this to children.
                    children.add(childrenSubstring.substring(start, end + 1));
                    start = end + 2;  // Skip the possible comma delimiter.
                }
            }
        }
        
        // Recursively parse the nested children.
        for (String childString : children) {
        	this.children.add(new NodeImpl(childString));
        }
    }
    
    @Override
    public boolean isWord() {
        return this.isWord;
    }

    @Override
    public void setWord(boolean isWord) {
        this.isWord = isWord;
    }
    
    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public NodeImpl getChild(Character c) {
    	// Iterate over the children to see if any of them contain the target letter.
        for (NodeImpl node : this.children) {
            if (node.getLetter() == c) {
                return node;
            }
        }
        return null;
    }

    @Override
    public boolean addChild(Character c) {
        if (this.getChild(c) == null) {  // If there is no child already associated with c, then add a new node with character c.
            this.children.add(new NodeImpl(c));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeChild(Character c) {
        return this.children.remove(this.getChild(c));
    }

    @Override
    public List<String> getSuggestions() {
        ArrayList<String> suggestions = this.getSuggetionHelper(new ArrayList<String>(), "");
        return suggestions;
    }
    
    /**
	 * Recursive depth-first search on all the valid post-fixes that end words starting from current node.
	 */	
    private ArrayList<String> getSuggetionHelper(ArrayList<String> words, String postfix) {

        String newPostfix = postfix;  // Build the current post-fix with the current letter.
        
        if(this.letter != '\0') {newPostfix = newPostfix + this.letter;}
        
        if (this.isWord) {
            words.add(newPostfix);
        }
        
        for (NodeImpl child : this.children) {  // Continue building the list out recursively.
             words = child.getSuggetionHelper(words, newPostfix);
        }

        return words;
    }
    
    /**
	 * Returns the letter associated with the node.
	 */	
    public char getLetter() {
        return this.letter;
    }
    
    /**
	 * Increments count member by 1.
	 */	
    public void incrementCount() {
        this.count++;
    }
    
    /**
	 * Sets count member.
	 */	
    public void setCount(int count) {
    	this.count = count;
    }
    
    public String toString() {
    	// General format: {letter:count:[children]}
    	String data = String.format("{%c:%d:[", getLetter(), getCount());
    	
    	// Add the children.
    	if (!children.isEmpty()) {
        	for (NodeImpl child : this.children) {
        		data += child.toString() + ",";
        	}
        	data = data.substring(0, data.length() - 1);  // Remove the trailing comma for the last child.
    	}
    	
    	data += "]}";  // Add the enclosure.
    	
    	return data;
    }
}