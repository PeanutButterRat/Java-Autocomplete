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
        //return suggestions.subList(0, 5);
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
}