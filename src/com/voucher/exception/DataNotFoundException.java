/**
 * 
 */
package com.voucher.exception;

/**
 * @author unix
 *
 */
public class DataNotFoundException extends SystemException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6168169378266606194L;

	public DataNotFoundException() {
		super();
	}

	public DataNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataNotFoundException(String message) {
		super(message);
	}

	public DataNotFoundException(Throwable cause) {
		super(cause);
	}
	
}
