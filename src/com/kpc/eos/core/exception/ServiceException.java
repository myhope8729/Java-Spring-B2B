
package com.kpc.eos.core.exception;

/**
 * 
 * ServiceException
 * =================
 * Description : 
 * Methods :
 */
public class ServiceException extends BaseException {

	private static final long serialVersionUID = 8103551472898603772L;

	public ServiceException(String code) {
		super(code);
	}

	public ServiceException(String code, Object[] args) {
		super(code, args);
	}

	public ServiceException(String code, Object[] args, Throwable causeThrowable) {
		super(code, args, causeThrowable);
	}
	
	public ServiceException(String code, Throwable causeThrowable) {
		super(code, causeThrowable);
	}
	
}
