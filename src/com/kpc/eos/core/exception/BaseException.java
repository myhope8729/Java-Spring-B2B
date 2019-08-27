
package com.kpc.eos.core.exception;

import org.apache.log4j.Logger;

import com.kpc.eos.core.util.MessageUtil;

/**
 * 
 * BaseException
 * =================
 * Description : 
 * Methods :
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -4925772626396172013L;
	private Logger logger = Logger.getLogger(this.getClass()); 
    
    private String key;
    private Object[] args;
    
    /**
     * 
     * BaseException.java Constructor
     *
     * @param code
     */
    public BaseException(String code) {
        this.key = code;
        if (logger.isDebugEnabled()) {
            logger.debug(getMessage());
        }
    }
    
    public BaseException(String code, Object[] args) {
        this.key = code;
        this.args = args;
        if (logger.isDebugEnabled()) {
            logger.debug(getMessage());
        }
    }
    
    public BaseException(String code, Throwable causeThrowable) {
        super(causeThrowable);
        this.key = code;
        if (logger.isDebugEnabled()) {
            logger.debug(getMessage(), causeThrowable);
        }
    }
    
    public BaseException(String code, Object[] args, Throwable causeThrowable) {
        super(causeThrowable);
        this.key = code;
        this.args = args;
        if (logger.isDebugEnabled()) {
            logger.debug(getMessage(), causeThrowable);
        }
    }
    
    public String getMessage() {
    	if (this.args != null) {
    		return MessageUtil.getMessage(this.key, this.args);
    	}
        return MessageUtil.getMessage(this.key);
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
    
    
}
