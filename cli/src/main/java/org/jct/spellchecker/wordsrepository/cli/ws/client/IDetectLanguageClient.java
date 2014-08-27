package org.jct.spellchecker.wordsrepository.cli.ws.client;

/**
 * Client to detect the language of a text
 * 
 */
public interface IDetectLanguageClient {

	/**
	 * Detect the language from a text
	 * 
	 * @param text
	 * @return
	 */
	String detectLanguage(String text);

}