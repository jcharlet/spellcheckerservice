package org.jct.spellchecker.wordsrepository.cli.service;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Service methods to be used by the Command Line Interface
 * 
 */
public interface ISpellCheckerCliService {

	/**
	 * Parse a file by its filename and returns a unique and ordered list of the
	 * words it contains
	 * 
	 * @param fileName
	 * @return
	 */
	LinkedHashSet<String> parseFile(String fileName);

	/**
	 * Read a file by its filename and return the text it contains
	 * 
	 * 
	 * @param fileName
	 * @return
	 */
	String readFile(String fileName);

	/**
	 * Detect the language from a list of words
	 * 
	 * @param listOfWords
	 * @return
	 */
	String detectLanguage(Set<String> listOfWords);

	/**
	 * Check a list of words an returns a list of all words that were not found
	 * by the common repository
	 * 
	 * @param uniqueWords
	 * @param language
	 * @return
	 */
	LinkedHashSet<String> checkWords(Set<String> uniqueWords, String language);

	/**
	 * add a word to the common repository
	 * 
	 * @param language
	 * @param word
	 * @return
	 */
	Boolean addWord(String language, String word);

}