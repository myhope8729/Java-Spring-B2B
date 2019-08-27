/**
 * Filename		: FormValidatorUtil.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.core.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Filename		: FormValidationUtils.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
public class FormValidationUtils extends ValidationUtils
{
	Errors errors = null;
	
	public FormValidationUtils(Errors errors)
	{
		this.errors = errors;
	}
	
	public void setErrors(Errors errors)
	{
		this.errors = errors; 
	}
	
	public Errors getErrors()
	{
		return this.errors; 
	}
	
	
	public void rejectIfEmptyOrWhitespace(String[] fields, String errorCode, String defaultMsg)
	{
		for (String field : fields)
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, errorCode, defaultMsg);
		}
	}
	
	public void rejectIfEmptyOrWhitespace(String[] fields, String errorCode, Object[] errArgs, String defaultMsg)
	{
		for (String field : fields)
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, errorCode, errArgs, defaultMsg);
		}
	}
	
	public void rejectIfEmptyOrWhitespace(String[] fields, String errorCode, Object[] errArgs)
	{
		for (String field : fields)
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, errorCode, errArgs);
		}
	}
	
	public void rejectIfEmptyOrWhitespace(String[] fields, String errorCode)
	{
		for (String field : fields)
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, errorCode);
		}
	}
}
