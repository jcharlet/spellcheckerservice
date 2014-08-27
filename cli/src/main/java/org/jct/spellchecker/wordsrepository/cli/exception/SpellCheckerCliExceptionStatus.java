package org.jct.spellchecker.wordsrepository.cli.exception;

public enum SpellCheckerCliExceptionStatus {
	/**
	 * the file could not be found by its path or filename
	 */
	FILE_NOT_FOUND,
	/**
	 * an error occured while reading the file
	 */
	READING_ERROR,
	/**
	 * an error occured while contacting a distant server
	 */
	WS_CLIENT_ERROR,
	/**
	 * an error occured while trying to detect the language of a text
	 */
	DETECT_LANGUAGE_ERROR,
	/**
	 * Invalid parameters were provided. can happen only on add request at the
	 * moment
	 */
	INVALID_PARAM_TO_ADD_REQUEST, SPELL_CHECKER_LANGUAGE_NOT_FOUND

}
