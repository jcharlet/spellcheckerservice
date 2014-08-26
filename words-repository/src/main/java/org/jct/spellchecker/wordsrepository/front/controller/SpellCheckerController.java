package org.jct.spellchecker.wordsrepository.front.controller;

import org.jct.spellchecker.wordsrepository.exception.SpellCheckerInvalidParameterException;
import org.jct.spellchecker.wordsrepository.front.model.SpellCheckerResponse;
import org.jct.spellchecker.wordsrepository.service.ISpellCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpellCheckerController {

	private static final String CHECK_ACTION = "check";
	private static final String ADD_ACTION = "add";
	@Autowired
	ISpellCheckerService spellCheckerService;

	@RequestMapping("/check")
	public @ResponseBody
	SpellCheckerResponse check(@RequestParam(required = true) String language,
			@RequestParam(required = true) String word)
			throws SpellCheckerInvalidParameterException {
		return new SpellCheckerResponse(word, CHECK_ACTION,
				spellCheckerService.check(language, word));
	}

	@RequestMapping("/add")
	public @ResponseBody
	SpellCheckerResponse add(
			@RequestHeader(value = "language", required = true) String language,
			@RequestParam(required = true) String word)
			throws SpellCheckerInvalidParameterException {
		return new SpellCheckerResponse(word, ADD_ACTION,
				spellCheckerService.addWord(language, word));
	}

}
