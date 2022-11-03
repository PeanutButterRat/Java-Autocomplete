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
        if (this.getChild(c) == null) {
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

    private ArrayList<String> getSuggetionHelper(ArrayList<String> words, String postfix) {

        String newPostfix = postfix + this.letter;

        if (this.isWord) {
            words.add(newPostfix);
        }

        for (NodeImpl child : this.children) {
             child.getSuggetionHelper(words, newPostfix);
        }

        return words;
    }

    public char getLetter() {
        return this.letter;
    }

    public int getCount() {
        return this.count;
    }

    public void incrementCount() {
        this.count++;
    }
}