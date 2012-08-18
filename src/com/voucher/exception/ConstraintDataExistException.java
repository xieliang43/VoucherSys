package com.voucher.exception;

public class ConstraintDataExistException extends PersistenceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6200372691948955057L;

	/**
	 * 
	 */
	public ConstraintDataExistException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConstraintDataExistException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ConstraintDataExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ConstraintDataExistException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
