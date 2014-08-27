package org.jct.spellchecker.wordsrepository.exception;

public enum SpellCheckerExceptionStatus {

	/**
	 * The language was not found in database
	 */
	UNKNOWN_LANGUAGE,
	/**
	 * Word provided is invalid
	 */
	INVALID_WORD,
	/**
	 * language provided is invalid
	 */
	INVALID_LANGUAGE

}
