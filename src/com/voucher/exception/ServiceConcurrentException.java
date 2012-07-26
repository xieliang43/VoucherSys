package com.voucher.exception;

public class ServiceConcurrentException extends AppException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7973237850200034860L;

	/**
	 * 
	 */
	public ServiceConcurrentException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ServiceConcurrentException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ServiceConcurrentException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ServiceConcurrentException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
