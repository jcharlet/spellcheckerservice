package org.jct.spellchecker.wordsrepository.exception;

import java.security.InvalidParameterException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid parameter")
public class SpellCheckerInvalidParameterException extends
		InvalidParameterException {

	private static final long serialVersionUID = -2459656305841174348L;

	public SpellCheckerInvalidParameterException() {
		super();
	}

	public SpellCheckerInvalidParameterException(String msg) {
		super(msg);
	}

}
