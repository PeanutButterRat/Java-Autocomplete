package edu.csus.csc131.typeahead.service;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {

	/**
	 * Store the input file, whose contents should be used for 
	 * future type suggestions
	 * 
	 * @param file	input file
	 */
	void store(MultipartFile file);

}