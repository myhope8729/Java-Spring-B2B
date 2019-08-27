package com.kpc.eos.core.util;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class FormatUtil
{
	public static void main(String[] args) {
		System.out.println(formatPhone("03202413543"));
	}

	public static String nvl(String strTarget, String strDest)
	{
		String retValue = null;
		
		if ( strTarget == null || strTarget.equals( "" ) )
			retValue = strDest;
		return retValue;
	}
	

	public static String nvl(String strTarget)
	{
		return nvl( strTarget, "" );
	}
	
	public static boolean isNull(String strTarget)
	{
		boolean retValue = false;
		
		if ( strTarget == null )
			retValue = true;
		else
			retValue = false;
		return retValue;
	}
	
    public static boolean isEmail(String email) {
        if (email==null) return false;
        boolean b = Pattern.matches(
            "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", 
            email.trim());
        return b;
    }

	public static String cutText(String strTarget, int nLimit, boolean bDot)
	{
		// System.out.println("strTarget :" + strTarget);
		if ( strTarget == null || strTarget.equals( "" ) )
		{
			return strTarget;
		}
		
		String retValue = null;
		
		int nLen = strTarget.length();
		int nTotal = 0;
		int nHex = 0;
		

		String strDot = "";
		
		if ( bDot )
			strDot = "...";
		
		for (int i = 0 ; i < nLen ; i++)
		{
			nHex = (int)strTarget.charAt( i );
			nTotal += Integer.toHexString( nHex ).length() / 2;
			// System.out.println("nTotal :" + nTotal + ", nLimit :" + nLimit);
			if ( nTotal > nLimit )
			{
				// System.out.println("1");
				retValue = strTarget.substring( 0, i ) + strDot;
				break;
			}
			else if ( nTotal == nLimit )
			{
				if ( i == (nLen - 1) )
				{
					// System.out.println("2");
					retValue = strTarget.substring( 0, i - 1 ) + strDot;
					break;
				}
				// System.out.println("3");
				retValue = strTarget.substring( 0, i + 1 ) + strDot;
				break;
			}
			else
			{
				// System.out.println("4");
				retValue = strTarget;
			}
		}
		return retValue;
	}
	

	public static int indexOfIgnore(String strTarget, String strDest, int nPos)
	{
		if ( strTarget == null || strTarget.equals( "" ) )
			return -1;
		
		strTarget = strTarget.toLowerCase();
		strDest = strDest.toLowerCase();
		
		return strTarget.indexOf( strDest, nPos );
	}
	
	public static int indexOfIgnore(String strTarget, String strDest)
	{
		return indexOfIgnore( strTarget, strDest, 0 );
	}
	

	public static String replace(String strTarget, String strOld, String strNew, boolean bIgnoreCase, boolean bOnlyFirst)
	{
		if ( strTarget == null || strTarget.equals( "" ) )
			return strTarget;
		
		StringBuffer objDest = new StringBuffer( "" );
		int nLen = strOld.length();
		int strTargetLen = strTarget.length();
		int nPos = 0;
		int nPosOld = 0;
		
		if ( bIgnoreCase == true )
		{ 
			while( (nPos = indexOfIgnore( strTarget, strOld, nPosOld )) >= 0 )
			{
				objDest.append( strTarget.substring( nPosOld, nPos ) );
				objDest.append( strNew );
				nPosOld = nPos + nLen;
				if ( bOnlyFirst == true )	 
					break;
			}
		}
		else
		{  
			while( (nPos = strTarget.indexOf( strOld, nPosOld )) >= 0 )
			{
				objDest.append( strTarget.substring( nPosOld, nPos ) );
				objDest.append( strNew );
				nPosOld = nPos + nLen;
				if ( bOnlyFirst == true )
					break;
			}
		}
		
		if ( nPosOld < strTargetLen )
			objDest.append( strTarget.substring( nPosOld, strTargetLen ) );
		
		return objDest.toString();
	}
	
	public static String replaceAll(String strTarget, String strOld, String strNew)
	{
		return replace( strTarget, strOld, strNew, false, false );
	}
	

	public static String removeFormat(String strTarget)
	{
		if ( strTarget == null || strTarget.equals( "" ) )
			return strTarget;
		
		return strTarget.replaceAll( "[$|^|*|+|?|/|:|\\-|,|.|\\s]", "" );
	}
	

	public static String removeComma(String strTarget)
	{
		if ( strTarget == null || strTarget.equals( "" ) )
			return strTarget;
		
		return strTarget.replaceAll( "[,|\\s]", "" );
	}
	

	public static String removeHTML(String strTarget)
	{
		if ( strTarget == null || strTarget.equals( "" ) )
			return strTarget;
		
		return strTarget.replaceAll( "<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "" );
	}
	
	public static String padValue(String strTarget, String strDest, int nSize, boolean bLeft)
	{
		if ( strTarget == null )
			return strTarget;
		
		String retValue = null;
		
		StringBuffer objSB = new StringBuffer();
		
		int nLen = strTarget.length();
		int nDiffLen = nSize - nLen;
		System.out.println();
		for (int i = 0 ; i < nDiffLen ; i++)
		{
			objSB.append( strDest );
		}
		
		if ( bLeft == true )  
			retValue = objSB.toString() + strTarget;
		else
			retValue = strTarget + objSB.toString();
		
		return retValue;
	}
	


	public static String padLeft(String strTarget, String strDest, int nSize)
	{
		return padValue( strTarget, strDest, nSize, true );
	}
	

	public static String padLeft(String strTarget, int nSize)
	{
		return padValue( strTarget, " ", nSize, true );
	}
	

	public static String padRight(String strTarget, String strDest, int nSize)
	{
		return padValue( strTarget, strDest, nSize, false );
	}
	

	public static String padRight(String strTarget, int nSize)
	{
		return padValue( strTarget, " ", nSize, false );
	}
	
	public static String encodingHTML(String strTarget)
	{
		if ( strTarget == null || strTarget.equals( "" ) )
			return strTarget;
		
		strTarget = strTarget.replaceAll( "<br>", "\r\n" );
		strTarget = strTarget.replaceAll( "<q>", "'" );
		strTarget = strTarget.replaceAll( "&quot;", "\"" );
		
		strTarget = strTarget.replaceAll( "<BR>", "\r\n" );
		strTarget = strTarget.replaceAll( "<Q>", "'" );
		strTarget = strTarget.replaceAll( "&QUOT;", "\"" );
		return strTarget;
	}
	
	public static String decodingHTML(String strTarget)
	{
		if ( strTarget == null || strTarget.equals( "" ) )
			return strTarget;
		
		strTarget = strTarget.replaceAll( "\r\n", "<br>" );
		strTarget = strTarget.replaceAll( "'", "<q>" );
		strTarget = strTarget.replaceAll( "\"", "&quot;" );
		
		strTarget = strTarget.replaceAll( "\r\n", "<BR>" );
		strTarget = strTarget.replaceAll( "'", "<Q>" );
		strTarget = strTarget.replaceAll( "\"", "&QUOT;" );
		return strTarget;
	}
	
	public static String formatMoney(String strTarget)
	{
		if ( strTarget == null && strTarget.trim().length() == 0 )
			return "0";
		
		strTarget = removeComma( strTarget );
		
		String strSign = strTarget.substring( 0, 1 );
		if ( strSign.equals( "+" ) || strSign.equals( "-" ) )
		{  
			strSign = strTarget.substring( 0, 1 );
			strTarget = strTarget.substring( 1 );
		}
		else
		{
			strSign = "";
		}
		
		String strDot = "";
		if ( strTarget.indexOf( "." ) != -1 )
		{  
			int nPosDot = strTarget.indexOf( "." );
			strDot = strTarget.substring( nPosDot, strTarget.length() );
			strTarget = strTarget.substring( 0, nPosDot );
		}
		
		StringBuffer objSB = new StringBuffer( strTarget );
		int nLen = strTarget.length();
		for (int i = nLen ; 0 < i ; i -= 3)  
		{
			objSB.insert( i, "," );
		}
		return strSign + objSB.substring( 0, objSB.length() - 1 ) + strDot;
	}
	

	public static String round(String strTarget, int nDotSize)
	{
		if ( strTarget == null || strTarget.trim().length() == 0 )
			return strTarget;
		
		String strDot = null; 
		
		int nPosDot = strTarget.indexOf( "." );
		if ( nPosDot == -1 )
		{ 
			strDot = (nDotSize == 0) ? padValue( "", "0", nDotSize, false ) : "." + padValue( "", "0", nDotSize, false );
		}
		else
		{  
		
			String strDotValue = strTarget.substring( nPosDot + 1 );  
			strTarget = strTarget.substring( 0, nPosDot );  
			
			if ( strDotValue.length() >= nDotSize )
			{  
				strDot = "." + strDotValue.substring( 0, nDotSize );
			}
			else
			{  
				strDot = "." + padValue( strDotValue, "0", nDotSize, false );
			}
		}
		return strTarget + strDot;
	}
	
	public static String formatDate(String strTarget)
	{
		String strValue = removeFormat( strTarget );
		
		if ( strValue.length() != 8 )
			return strTarget;
		
		StringBuffer objSB = new StringBuffer( strValue );
		objSB.insert( 4, "-" );
		objSB.insert( 7, "-" );
		
		return objSB.toString();
	}
	
	public static String formatJuminNo(String strTarget)
	{
		String strValue = removeFormat( strTarget );
		if(strValue.length() != 13)
			return null;
		
		strValue = strValue.substring(0, 7) + "******";
		
		StringBuffer objSB = new StringBuffer( strValue );
		objSB.insert( 6, "-" );
		
		return objSB.toString();
	}

	public static String formatPhone(String strTarget)
	{
		String strValue = removeFormat( strTarget );
		int nLength = strValue.length();
		
		if ( nLength < 9 || nLength > 12 ) // 9 ~ 12
			return strTarget;
		
		StringBuffer objSB = new StringBuffer( strValue );
		
		if ( strValue.startsWith( "02" ) == true )
		{  
			if ( nLength == 9 )
			{
				objSB.insert( 2, "-" );
				objSB.insert( 6, "-" );
			}
			else
			{
				objSB.insert( 2, "-" );
				objSB.insert( 7, "-" );
			}
		}
		else
		{  
			if ( nLength == 10 )
			{
				objSB.insert( 3, "-" );
				objSB.insert( 7, "-" );
			}
			else
			{	 
				objSB.insert( 3, "-" );
				objSB.insert( 8, "-" );
			}
		}
		return objSB.toString();
	}
	
	public static String formatZIP(String strTarget)
	{
		String strValue = removeFormat( strTarget );
		
		if ( strValue.length() != 6 )
			return strTarget;
		
		StringBuffer objSB = new StringBuffer( strValue );
		objSB.insert( 3, "-" );
		
		return objSB.toString();
	}
	
	
	public static String toFirstLowerCase(String str) 
	{
		if (str == null || str.length() < 1) return str;
		return Character.toLowerCase(str.charAt(0)) +  str.substring(1);
	}
	
	public static String toFirstUpperCase(String str) 
	{
		if (StringUtils.isEmpty(str)) return str;
		return Character.toUpperCase(str.charAt(0)) +  str.substring(1);
	}
	
	
} // End- Class StringUtil
