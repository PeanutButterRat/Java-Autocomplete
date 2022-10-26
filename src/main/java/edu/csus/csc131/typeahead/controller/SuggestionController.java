package edu.csus.csc131.typeahead.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.csus.csc131.typeahead.service.SuggestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "suggestion", description = "Typeahead suggestion API")
@RestController
@RequestMapping("api/v1/typeahead/suggestions")
public class SuggestionController {

	private SuggestionService suggestionService;

	SuggestionController(SuggestionService suggestionService) {
		this.suggestionService = suggestionService;
	}

	@Operation(summary = "Get type suggestions", tags = { "suggestion" })
	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	List<String> getSuggestions(@RequestParam String prefix) {
		List<String> list = suggestionService.getSuggestions(prefix);
		return list;
	}

}
