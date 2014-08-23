package org.jct.spellchecker.wordsrepository.controller;

import org.jct.spellchecker.wordsrepository.exception.SpellCheckerInvalidParameterException;
import org.jct.spellchecker.wordsrepository.service.ISpellCheckerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpellCheckerController {

	@Autowired
	ISpellCheckerService spellCheckerService;

	@RequestMapping("/check")
	public Boolean check(@RequestParam(required = true) String language,
			@RequestParam(required = true) String word)
			throws SpellCheckerInvalidParameterException {
		return spellCheckerService.check(language, word);
	}

	@RequestMapping("/add")
	public Boolean add(
			@RequestHeader(value = "language", required = true) String language,
			@RequestParam(required = true) String word)
			throws SpellCheckerInvalidParameterException {
		return spellCheckerService.addWord(language, word);
	}
}
