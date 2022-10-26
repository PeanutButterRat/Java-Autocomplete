package edu.csus.csc131.typeahead.data;

import java.util.ArrayList;
import java.util.List;

//ToDo: provide real implementation for this class
public class NodeImpl implements Node {
	public NodeImpl() {
		
	}
	
	@Override
	public boolean isWord() {
		return false;
	}

	@Override
	public void setWord(boolean isWord) {
	}
	
	@Override
	public Node getChild(Character c) {
		// TODO Auto-generated method stub
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

}
