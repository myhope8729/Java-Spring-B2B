
package com.kpc.eos.core.validation;

import java.lang.reflect.Field;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kpc.eos.core.exception.ServiceException;

// TODO: Auto-generated Javadoc
/**
 * The Class InstantValidator.
 */
public class InstantValidator {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(InstantValidator.class);

	/** The Constant VALIDATE_SUCESS. */
    public	static final String	VALIDATE_SUCESS = "S";

    /** The Constant VALIDATE_FAIL. */
    public	static final String	VALIDATE_FAIL = "F";

    public static void checkBeanMandatory(Object target, String[] members) {
        checkBeanMandatory(target, members, null);
    }

    public static void checkBeanMandatory(Object target, String[] members, String[] memberNames) {
        if (logger.isDebugEnabled()) {
            logger.debug("** Start checBeanMandatory TargetObject : " + target + ", members : " + members);
        }
        if (target == null) {
            throw new ServiceException("system.common.valid.error.null");
        }
        if (members == null || members.length == 0) {
            return;
        }
        if(memberNames == null){
            memberNames = members;
        }
        if (memberNames.length < members.length) {
            memberNames = (String[]) ArrayUtils.addAll(memberNames, ArrayUtils.subarray(members, memberNames.length,
                    members.length));
        }

        String data = null;
        for (int i=0; i<members.length; i++){
            try {
                boolean oriAccessible = true;
                Field field = target.getClass().getDeclaredField(members[i]);
                if(!field.isAccessible()){
                    field.setAccessible(true);
                    oriAccessible = false;
                }
                if(field.getType().equals(String.class)){
                    data = (String)field.get(target);
                } else {
                    Object temp = field.get(target);
                    if (temp == null) {
                        data = null;
                    } else {
                        data = String.valueOf(temp);
                    }
                }
                if (StringUtils.isEmpty(data)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug(">>> Checking Mandatory - member: " + members[i]
                                + ", result: << FAIL >>"
                                + ", type: " + field.getType().getName());
                    }

                    StringBuilder sb = new StringBuilder();
                    sb.append(target.getClass().getName()).append(".").append(members[i]);
                    sb.append("(").append(memberNames[i]).append(")");
                    throw new ServiceException("system.common.valid.error.required", new Object[]{ sb.toString()  });
                }
                if (logger.isDebugEnabled()) {
                    logger.debug(">>> Checking Mandatory - member: " + members[i]
                            + ", result: << SUCCESS >>"
                            + ", type: " + field.getType().getName()
                            + ", value: " + data);
                }
                if (!oriAccessible) {
                    field.setAccessible(oriAccessible);
                }

            } catch (ServiceException le) {
                throw le;
            } catch (Exception e) {
                logger.error(">>> Mandatory validation fail : " + e.getMessage(), e);

                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName()).append(".").append(members[i]);
                sb.append("(").append(memberNames[i]).append(")");
                throw new ServiceException("system.common.valid.error.required", new Object[] { sb.toString() }, e);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("** End checkBeanMandatory");
        }
    }
}
