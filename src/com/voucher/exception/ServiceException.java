/**
 * 
 */
package com.voucher.exception;

/**
 *
 */
public class ServiceException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4718617098358098827L;

	/**
	 * 
	 */
	public ServiceException() {
	}

	/**
	 * @param message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
