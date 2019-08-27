package com.kpc.eos.core.util;

public class MathUtil {
	public static String round(String a, int n){
		int s = 1;
		String result;
		for (int i = 0;i < n;i ++)
			s *= 10;
		int temp = Math.round(Float.valueOf(a) * s);
		float c = (float)temp / s;
		result = String.valueOf(c);
		if (c == 0) {
			result = "0";
		}
		
		return result;
	}
	
	public static Double getDouble(Object val, Boolean isNullToZero)
	{
		Double ret = null;
		
		if (val != null)
		{
			try {
				if (val instanceof String)
				{
					ret = Double.valueOf((String)val);
				}
				else if (val instanceof Integer)
				{
					ret = ((Integer) val).doubleValue();
				}
				else if (val instanceof Double)
				{
					ret = (Double) val;
				} 
				else 
				{
					ret = Double.valueOf((String)val);
				}
			} catch (Exception e) {
				ret = null;
			}
		}
		
		if (isNullToZero != null && isNullToZero == true) 
		{
			return ret == null? new Double(0.0) : ret; 
		}
		return ret;
	}
}
