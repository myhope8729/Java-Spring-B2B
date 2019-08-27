/**
 * Filename		: KptValidator.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.core.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.kpc.eos.core.model.CommonModel;
import com.kpc.eos.model.dataMng.UserModel;

/**
 * Filename		: KptValidator.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
public class FormValidator implements Validator
{
	@SuppressWarnings("rawtypes")
	@Override
	public boolean supports(Class cls)
	{
		return true;
	}

	@Override
	public void validate(Object obj, Errors errors)
	{
		if (obj instanceof UserModel) {
			UserModel user = (UserModel) obj;
			if ( user!=null && user.getEmpNo() != null) 
			{
				
				errors.rejectValue("empNo", "negativevalue");
			}
		}
	}
	
}
