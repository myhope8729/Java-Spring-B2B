
package com.kpc.eos.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;

/**
 * DateUtil
 * =================
 * Description : date utility
 * Methods :
 */
public class DateUtil {

	public static final String DATE_PATTERN = "yyyy-MM-dd";
	
	/**
	 * dateCheck
	 * ===================
	 * get the data diff
	 * @param startDay
	 * @param endDay
	 * @param gubun
	 * @return
	 */
	public static long dateCheck(String startDay, String endDay){
		return dateCheck(startDay, endDay, "\\.");
	}
	
	public static long dateCheck(String startDay, String endDay, String gubun){
		
		Calendar td = Calendar.getInstance();
		Calendar dd = Calendar.getInstance();
		
		String[] start = startDay.split(gubun);
		String[] end = endDay.split(gubun);
		
		td.set(Integer.parseInt(start[0]), Integer.parseInt(start[1]), Integer.parseInt(start[2]));
		dd.set(Integer.parseInt(end[0]), Integer.parseInt(end[1]), Integer.parseInt(end[2]));

		long resultTime = dd.getTime().getTime() - td.getTime().getTime();  
		long resultDay = resultTime /(1000*60*60*24); 

		return resultDay;
		
	}
	
	/**
	 * addDate
	 * ===================
	 * @param strDate
	 * @param field
	 * @param amount
	 * @return
	 * 예) DateUtil.addDate("20100103", Calendar.YEAR, 1);
	 */
	public static String addDate(String strDate, int field, int amount) {
		return addDate(strDate, field, amount, DATE_PATTERN);
	}
	
	/**
	 * addDate
	 * ===================
	 * @param strDate
	 * @param field
	 * @param amount
	 * @param pattern
	 * @return
	 */
	public static String addDate(String strDate, int field, int amount, String pattern) {
    	
    	Date date = convertStringToDate(strDate, pattern);
    	if (date == null)
    		return "";
    	
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        
        return FastDateFormat.getInstance(pattern).format(calendar);
    }    
    
    /**
     * convertStringToDate
     * ===================
     * @param strDate
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate, String pattern) {
    	
		SimpleDateFormat 	df 	 = null;
		Date 				date = null;
		  
		df = new SimpleDateFormat(pattern);
		
		try {
			date = df.parse(strDate);
		} catch(ParseException pe) {
			return null;
		}
		
		return date;
	}
    
    /**
     * getToday
     * ===================
     * @return
     */
    public static String getToday() {
    	return getToday(DATE_PATTERN);
    }
    
    /**
     * getToday
     * ===================
     * @param pattern
     * @return
     */
    public static String getToday(String pattern) {
    	return FastDateFormat.getInstance(pattern).format(new Date());
    }
    
    /**
     * getLastDayOfMonth
     * ===================
     * @param strDate
     * @return
     */
    public static String getLastDayOfMonth(String strDate) {
    	return getLastDayOfMonth(strDate, DATE_PATTERN);
    }
    
    /**
     * getLastDayOfMonth
     * ===================
     * @param strDate
     * @param pattern
     * @return
     */
    public static String getLastDayOfMonth(String strDate, String pattern) {
    	Date date = convertStringToDate(strDate, pattern);
    	if (date == null)
    		return "";
    	
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        
    	return FastDateFormat.getInstance(pattern).format(calendar);
    }
    
    /**
     * getDayOfWeek
     * ===================
     * @param strDate
     * @return
     */
    public static int getDayOfWeek(String strDate) {
    	return getDayOfWeek(strDate, DATE_PATTERN);
    }
    
    /**
     * getDayOfWeek
     * ===================
     * @param strDate
     * @param pattern
     * @return
     */
    public static int getDayOfWeek(String strDate, String pattern) {
    	Date date = convertStringToDate(strDate, pattern);
    	if (date == null)
    		return -1;
    	
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
    	return calendar.get(Calendar.DAY_OF_WEEK);
    }
    
    
    public static void main(String[] args) {
    	Calendar td = Calendar.getInstance();  
		Calendar dd = Calendar.getInstance();
		td.set(2011, 3, 13); //  
		dd.set(2011, 3, 17); //  
		
		long resultTime = dd.getTime().getTime() - td.getTime().getTime(); //  
		long resultDay = resultTime /(1000*60*60*24);//  
		System.out.println(resultDay);
	}
}
