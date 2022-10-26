package edu.csus.csc131.typeahead.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Trie {
	static Logger logger = LoggerFactory.getLogger(Trie.class);	
	private Node root;
	private final Path backupFilePath;
	
	public Trie(String backupFile) throws IOException {
		this.backupFilePath = Paths.get(backupFile);
		restore();
	}
	
	protected Node getRoot() {
		return root;
	}
	
	private void restore() throws IOException {
		logger.trace("restore started");
		
		String content = Files.readString(backupFilePath);
		root = deSerialize(content);
		
		logger.trace("restore completed");
	}
	
	private void backup() throws IOException {
		logger.trace("backup started");
		
		String content = serialize();
		Files.writeString(backupFilePath, content, StandardOpenOption.WRITE);
		
		logger.trace("backup completed");		
	}
	
	public void update(Path path) throws IOException {
		logger.trace("update started");		
		
		String content = Files.readString(path);
		root = buildTree(content);
		backup();
		
		logger.trace("update completed");
	}	
	
	abstract Node buildTree(String str);
	
	abstract String serialize();
	
	abstract Node deSerialize(String str);
	
	public abstract List<String> getSuggestions(String prefix);		
}
