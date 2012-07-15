package com.voucher.exception;

public class PersistenceException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4478600768540581338L;

	/**
	 * 
	 */
	public PersistenceException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public PersistenceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PersistenceException(Throwable cause) {
		super(cause);
	}

}
