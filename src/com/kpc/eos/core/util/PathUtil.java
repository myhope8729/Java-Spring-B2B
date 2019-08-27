package com.kpc.eos.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PathUtil {
	static Pattern goodsIdFormat = Pattern.compile("GD(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})");

	public static String goodsSubPath(String goodsId) {
		if (goodsId == null) {
			return null;
		}
		Matcher m = goodsIdFormat.matcher(goodsId);
		if (m.find() == false) {
			return null;
		}
		StringBuffer buf = new StringBuffer("/");
		for (int i = 1; i <= 6; i++) {
			buf.append(m.group(i)).append('/');
		}

		return buf.toString();
	}

	static int SEPARATION_POINT = 4;

	public static String subPath() {
		return subPath(new Date());
	}

	public static String subPath(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("/yyyy/MM/dd/");
		return format.format(date);
	}

}
