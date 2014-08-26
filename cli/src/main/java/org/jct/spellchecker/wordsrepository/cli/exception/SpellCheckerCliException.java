package org.jct.spellchecker.wordsrepository.cli.exception;


public class SpellCheckerCliException extends
 RuntimeException {

	private static final long serialVersionUID = -2459656305841174348L;

	private SpellCheckerCliExceptionStatus exceptionStatus;

	public SpellCheckerCliException() {
		super();
	}

	public SpellCheckerCliException(
			SpellCheckerCliExceptionStatus exceptionStatus, Throwable cause) {
		super(cause);
		this.exceptionStatus = exceptionStatus;
	}

	public SpellCheckerCliException(
			SpellCheckerCliExceptionStatus exceptionStatus) {
		super();
		this.exceptionStatus = exceptionStatus;
	}

	public SpellCheckerCliExceptionStatus getExceptionStatus() {
		return exceptionStatus;
	}

}
