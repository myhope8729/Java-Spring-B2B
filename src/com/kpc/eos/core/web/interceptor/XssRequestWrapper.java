package com.kpc.eos.core.web.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class XssRequestWrapper extends HttpServletRequestWrapper {

	public XssRequestWrapper(HttpServletRequest httpservletrequest) {
		super(httpservletrequest);
	}

	public String[] getParameterValues(String s) {
		String as[] = super.getParameterValues(s);
		if (as == null)
			return null;
		int i = as.length;
		String as1[] = new String[i];
		for (int j = 0; j < i; j++) {
			as1[j] = cleanXSS(as[j]);
		}

		return as1;
	}

	public String getParameter(String s) {
		String s1 = super.getParameter(s);
		if (s1 == null) {
			return null;
		} else {
			return cleanXSS(s1);
		}
	}

	public String getHeader(String s) {
		String s1 = super.getHeader(s);
		if (s1 == null) {
			return null;
		} else {
			if (isXssFilterException(s) == false) {
				return s1;
			}
			return cleanXSS(s1);
		}
	}

	private boolean isXssFilterException(String paramName) {
//		String uri = getRequestURI();
//		int index = 1;
//		Pattern rulePattern = Pattern.compile("([^:]+):(.*)");
//		while(true){
//			String xssExceptionStr = Config.getString("security.xss-exception.rule"+index);	
//			index++;
//			if (xssExceptionStr == null){
//				break;
//			}
//			
//			Matcher m = rulePattern.matcher(xssExceptionStr);
//			if(m.find() == false) {
//				continue;
//			}
//			
//			String urlPrefix = m.group(1);
//			if (uri == null || uri.contains(urlPrefix) ==false){
//				continue;
//			}
//
//			String[] paramNames = m.group(2).split(",");
//			for (String param : paramNames) {
//				if (paramName.equals(param)){
//					return true;
//				}
//			}
//		}		
		
		return false;
	}

	private String cleanXSS(String s) {
//		return escapeHTML(XssFilter.getInstance().doFilter(s));
		return escapeHTML(s);
	}

	public static final String escapeHTML(String s) {
//		if ("false".equalsIgnoreCase(Config.getString("security.escapeHTML"))) {
//			return s;
//		}
		StringBuffer sb = new StringBuffer();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			switch (c) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case 'à':
				sb.append("&agrave;");
				break;
			case 'À':
				sb.append("&Agrave;");
				break;
			case 'â':
				sb.append("&acirc;");
				break;
			case 'Â':
				sb.append("&Acirc;");
				break;
			case 'ä':
				sb.append("&auml;");
				break;
			case 'Ä':
				sb.append("&Auml;");
				break;
			case 'å':
				sb.append("&aring;");
				break;
			case 'Å':
				sb.append("&Aring;");
				break;
			case 'æ':
				sb.append("&aelig;");
				break;
			case 'Æ':
				sb.append("&AElig;");
				break;
			case 'ç':
				sb.append("&ccedil;");
				break;
			case 'Ç':
				sb.append("&Ccedil;");
				break;
			case 'é':
				sb.append("&eacute;");
				break;
			case 'É':
				sb.append("&Eacute;");
				break;
			case 'è':
				sb.append("&egrave;");
				break;
			case 'È':
				sb.append("&Egrave;");
				break;
			case 'ê':
				sb.append("&ecirc;");
				break;
			case 'Ê':
				sb.append("&Ecirc;");
				break;
			case 'ë':
				sb.append("&euml;");
				break;
			case 'Ë':
				sb.append("&Euml;");
				break;
			case 'ï':
				sb.append("&iuml;");
				break;
			case 'Ï':
				sb.append("&Iuml;");
				break;
			case 'ô':
				sb.append("&ocirc;");
				break;
			case 'Ô':
				sb.append("&Ocirc;");
				break;
			case 'ö':
				sb.append("&ouml;");
				break;
			case 'Ö':
				sb.append("&Ouml;");
				break;
			case 'ø':
				sb.append("&oslash;");
				break;
			case 'Ø':
				sb.append("&Oslash;");
				break;
			case 'ß':
				sb.append("&szlig;");
				break;
			case 'ù':
				sb.append("&ugrave;");
				break;
			case 'Ù':
				sb.append("&Ugrave;");
				break;
			case 'û':
				sb.append("&ucirc;");
				break;
			case 'Û':
				sb.append("&Ucirc;");
				break;
			case 'ü':
				sb.append("&uuml;");
				break;
			case 'Ü':
				sb.append("&Uuml;");
				break;
			case '®':
				sb.append("&reg;");
				break;
			case '©':
				sb.append("&copy;");
				break;
			case '€':
				sb.append("&euro;");
				break;
	
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}
}
