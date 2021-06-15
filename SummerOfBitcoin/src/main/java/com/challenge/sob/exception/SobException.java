package com.challenge.sob.exception;

public class SobException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SobException() {
		super();
	}

	public SobException(String message) {
		super(message);
	}

	public SobException(Throwable cause) {
		super(cause);
	}

	public SobException(String message, Throwable cause) {
		super(message, cause);
	}

}
