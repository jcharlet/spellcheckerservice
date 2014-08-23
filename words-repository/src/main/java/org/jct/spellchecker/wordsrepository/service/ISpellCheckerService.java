package org.jct.spellchecker.wordsrepository.service;

import org.jct.spellchecker.wordsrepository.exception.SpellCheckerException;

public interface ISpellCheckerService {

	boolean check(String shortCode, String name) throws SpellCheckerException;

	boolean addWord(String shortCode, String name) throws SpellCheckerException;

}