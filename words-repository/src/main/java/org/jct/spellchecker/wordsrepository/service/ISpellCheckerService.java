package org.jct.spellchecker.wordsrepository.service;

import org.jct.spellchecker.wordsrepository.exception.SpellCheckerInvalidParameterException;

public interface ISpellCheckerService {

	boolean check(String shortCode, String name)
			throws SpellCheckerInvalidParameterException;

	boolean addWord(String shortCode, String name)
			throws SpellCheckerInvalidParameterException;

}