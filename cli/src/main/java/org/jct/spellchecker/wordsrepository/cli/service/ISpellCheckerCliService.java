package org.jct.spellchecker.wordsrepository.cli.service;

import java.util.LinkedHashSet;

public interface ISpellCheckerCliService {

	LinkedHashSet<String> parseFile(String fileName);

	String readFile(String fileName);

	String detectLanguage(LinkedHashSet<String> listOfWords);

	LinkedHashSet<String> checkWords(LinkedHashSet<String> uniqueWords);

}