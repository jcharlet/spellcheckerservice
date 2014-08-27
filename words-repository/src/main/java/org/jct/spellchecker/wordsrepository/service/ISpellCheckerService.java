package org.jct.spellchecker.wordsrepository.service;


public interface ISpellCheckerService {

	/**
	 * check whether a word is known in the common dictionary
	 * 
	 * @param shortCode
	 * @param name
	 * @return
	 */
	boolean check(String shortCode, String name);

	/**
	 * add a word to the dictionary
	 * 
	 * @param shortCode
	 * @param name
	 * @return
	 */
	boolean addWord(String shortCode, String name);

}