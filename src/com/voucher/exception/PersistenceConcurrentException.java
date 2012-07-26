package com.voucher.exception;

public class PersistenceConcurrentException extends PersistenceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5549403294796157556L;

	/**
	 * 
	 */
	public PersistenceConcurrentException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PersistenceConcurrentException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public PersistenceConcurrentException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public PersistenceConcurrentException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
