package edu.csus.csc131.typeahead.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentServiceImpl implements DocumentService {
	private Logger logger = LoggerFactory.getLogger(DocumentServiceImpl.class);	
	private final Path docRoot;
	private SuggestionServiceImpl suggestionService;

	public DocumentServiceImpl(@Value("${document.root}") String documentRoot, SuggestionServiceImpl suggestionService) {
		this.docRoot = Paths.get(documentRoot);
		this.suggestionService = suggestionService;
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path destFile = this.docRoot.resolve(Paths.get(file.getOriginalFilename())).normalize()
					.toAbsolutePath();
			if (!destFile.getParent().equals(this.docRoot.toAbsolutePath())) {
				// This is a security check
				throw new StorageException("Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destFile, StandardCopyOption.REPLACE_EXISTING);
				Thread updateThread = new Thread(()->{
					try {
						suggestionService.update(destFile);
					} catch (IOException e) {
						logger.error("Failed to update suggestion data", e);
						throw new StorageException("Failed to update suggestion data.");
					}
				});
				updateThread.start();
			} 
		} catch (Exception e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

}