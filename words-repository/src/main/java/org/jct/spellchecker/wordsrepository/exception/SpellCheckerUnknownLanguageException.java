package org.jct.spellchecker.wordsrepository.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//FIXME JCT WORKAROUND unknown language handling
@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "invalid parameter")
public class SpellCheckerUnknownLanguageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SpellCheckerUnknownLanguageException() {
		super();
	}

	public SpellCheckerUnknownLanguageException(String msg) {
		super(msg);
	}

}
