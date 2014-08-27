package org.jct.spellchecker.wordsrepository.cli.ws.client;

/**
 * Client providing methods to check and complete the common repository of words
 * 
 */
public interface ISpellCheckerClient {

	/**
	 * Check whether a word exists in the common repository
	 * 
	 * @param language
	 * @param word
	 * @return
	 */
	Boolean check(String language, String word);

	/**
	 * add a word to the common repository
	 * 
	 * @param language
	 * @param word
	 * @return
	 */
	Boolean add(String language, String word);

}