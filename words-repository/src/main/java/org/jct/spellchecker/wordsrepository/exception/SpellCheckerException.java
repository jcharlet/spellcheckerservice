package org.jct.spellchecker.wordsrepository.exception;

import org.springframework.stereotype.Service;

@Service
public class SpellCheckerException extends Exception {

	private static final long serialVersionUID = -8124244302307912695L;

	ExceptionStatus status;

	public SpellCheckerException() {
		super();
	}

	public SpellCheckerException(ExceptionStatus status) {
		super();
		this.status = status;
	}

	public ExceptionStatus getStatus() {
		return status;
	}


}
