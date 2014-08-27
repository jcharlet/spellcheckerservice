package org.jct.spellchecker.wordsrepository.front.controller;

import org.jct.spellchecker.wordsrepository.front.model.SpellCheckerResponse;
import org.jct.spellchecker.wordsrepository.service.ISpellCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * WS Controller providing methods to check and complete the common repository
 * of words
 * 
 */
@RestController
public class SpellCheckerController {

	private static final String CHECK_ACTION = "check";
	private static final String ADD_ACTION = "add";
	@Autowired
	private ISpellCheckerService spellCheckerService;

	/**
	 * Check whether a word exists in the common repository
	 * 
	 * @param language
	 * @param word
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/check")
	public SpellCheckerResponse check(
			@RequestParam(required = true) String language,
			@RequestParam(required = true) String word) {
		return new SpellCheckerResponse(word, CHECK_ACTION,
				spellCheckerService.check(language, word));
	}

	/**
	 * add a word to the common repository
	 * 
	 * @param language
	 * @param word
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public SpellCheckerResponse add(
			@RequestHeader(value = "language", required = true) String language,
			@RequestParam(required = true) String word) {
		return new SpellCheckerResponse(word, ADD_ACTION,
				spellCheckerService.addWord(language, word));
	}

}
