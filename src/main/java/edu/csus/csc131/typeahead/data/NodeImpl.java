package edu.csus.csc131.typeahead.data;

import java.util.ArrayList;
import java.util.List;


public class NodeImpl implements Node {

    private ArrayList<NodeImpl> children;  // Stores the children nodes of this.
    private int count;  // Stores number of words that end in the trie on this node.
    private char letter;  // Letter that the trie node represents.
    private boolean isWord;  // Tracks if this node ends a valid word.
  
    /**
	 * Base constructor simply initializes letter to null.
	 */	
    public NodeImpl() {
        this.children = new ArrayList<NodeImpl>();
        this.count = 0;
        this.letter = '\0';
        this.isWord = false;
    }
    
    public NodeImpl(char letter) {
        this.children = new ArrayList<NodeImpl>();
        this.count = 0;
        this.letter = letter;
        this.isWord = false;
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
    public Node getChild(Character c) {
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
        return this.getSuggetionHelper(new ArrayList<String>(), "");
    }
    
    /**
	 * Recursive depth-first search on all the valid post-fixes that end words starting from current node.
	 */	
    private ArrayList<String> getSuggetionHelper(ArrayList<String> words, String postfix) {

        String newPostfix = postfix + this.letter;  // Build the current post-fix with the current letter.
        
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
	 * Returns the number of words end at the current node in the trie.
	 */	
    public int getCount() {
        return this.count;
    }
    
    /**
	 * Increments count member by 1.
	 */	
    public void incrementCount() {
        this.count++;
    }
}