package com.voucher.exception;

public class DataExistException extends AppException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1437897930841749481L;

	/**
	 * 
	 */
	public DataExistException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DataExistException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public DataExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public DataExistException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
