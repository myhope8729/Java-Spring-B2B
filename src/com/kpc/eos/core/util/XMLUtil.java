package com.kpc.eos.core.util;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.kpc.eos.core.exception.ServiceException;

public class XMLUtil {
	private static XPathFactory factory;
	private static XPath xpath;
	private static DocumentBuilderFactory dbf;
	private static DocumentBuilder db;
	private static Document doc;

	static
	{
		try {
			factory = XPathFactory.newInstance();
		    xpath = factory.newXPath();
		    dbf  = DocumentBuilderFactory.newInstance();
			db   = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static String parseFromXml(String xml, String path) {
		String result = "";
		try
		{			
			// Check and open XML document
			StringReader stringReader = new java.io.StringReader(xml);
			InputSource inputSource = new InputSource(stringReader);
			doc = db.parse(inputSource);
		    XPathExpression expr = xpath.compile(path);
		    result = (String)expr.evaluate(doc, XPathConstants.STRING);
		}
		catch(Exception e)
		{
			throw new ServiceException("XML Parsing Error.");
		}
		
		return result;
	}
	
	public static String getCountFromXml(String xml, String path) {
		String result = "";
		try
		{			
			// Check and open XML document
			StringReader stringReader = new java.io.StringReader(xml);
			InputSource inputSource = new InputSource(stringReader);
			doc = db.parse(inputSource);
		    XPathExpression expr = xpath.compile("count(" + path + ")");
		    result = (String)expr.evaluate(doc, XPathConstants.STRING);
		}
		catch(Exception e)
		{
			throw new ServiceException("XML Parsing Error.");
		}
		
		return result;
	}
}
