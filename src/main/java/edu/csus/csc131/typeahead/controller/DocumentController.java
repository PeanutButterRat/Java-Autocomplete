package edu.csus.csc131.typeahead.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.csus.csc131.typeahead.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "document", description = "Typeahead document API")
@RestController
@RequestMapping("api/v1/typeahead/documents")
public class DocumentController {
	private DocumentService documentService;

	DocumentController(DocumentService documentService) {
		this.documentService = documentService;
	}

	@Operation(summary = "Add a document", tags = {"document" })
	@PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void updateSuggestions(@RequestPart(value = "file") final MultipartFile file) {
		documentService.store(file);
	}

}
