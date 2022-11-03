package edu.csus.csc131.typeahead.data;

import java.util.ArrayList;
import java.util.List;

//ToDo: provide real implementation for this class
public class NodeImpl implements Node {
	
	private ArrayList<NodeImpl> children;
	private int count;
	private char letter;
	private boolean isWord;
	
	
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
		for (NodeImpl node : this.children) {
			if (node.getLetter() == c) {
				return node;
			}
		}
		return null;
	}	
	
	@Override
	public boolean addChild(Character c) {
		return false;
	}
	
	@Override
	public boolean removeChild(Character c) {
		return false;
	}
	
	@Override
	public List<String> getSuggestions() {
		return new ArrayList<String>();
	}
	
	public char getLetter() {
		return this.letter;
	}
	
	public int getCount() {
		return this.count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
}
