package com.kpc.eos.core.xhtml;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.kpc.eos.core.exception.ServiceException;
import com.kpc.eos.core.util.HttpUtil;

/**
 * ==================================
 * 2017.11.13 
 */
public class Connection {

	/**
	 * Get result for get method's request.
	 * @param reqUrl
	 * @return
	 */
	public static Document sendReqGet(String reqUrl, String getBody){

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc = null;
		
		URL urlInstance;
		OutputStreamWriter wr = null;
		try {
			
			db = dbf.newDocumentBuilder();
			
			urlInstance = new URL(HttpUtil.urlEncode(reqUrl + getBody));
			URLConnection conn = urlInstance.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(2000);
			conn.setUseCaches(false);
				
			doc = db.parse(conn.getInputStream());
		} catch (MalformedURLException e) {
			throw new ServiceException("Exception", e);
		} catch (IOException e) {
			throw new ServiceException("Exception.", e);
		} catch (ParserConfigurationException e) {
			throw new ServiceException("Exception.", e);
		} catch (SAXException e) {
			throw new ServiceException("Exception.", e);
		} finally {
			if (wr != null) {
				try {
					wr.close();
				} catch (IOException e) {
					return null;
				}
			}
		}
		return doc;
	}
	
	/**
	 * Get result for post method's request.
	 * @param data
	 * @return
	 */
	public static Document sendReqPost(String url, String postData){

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc = null;
		
		URL urlInstance;
		OutputStreamWriter wr = null;
		HttpURLConnection conn;
		try {
			db = dbf.newDocumentBuilder();
			urlInstance = new URL(url);
			
			url = HttpUtil.urlEncode(url);
			urlInstance = new URL(url);
			conn = (HttpURLConnection)urlInstance.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(2000);
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
		    String param    = postData; //"ID="+ JspUtil.urlEncode(sNetMableID)+"&cust_no="+ JspUtil.urlEncode(sEncCustNo)+"&prom_no="+prom_no;
		    OutputStream out_stream = conn.getOutputStream();
		    out_stream.write( param.getBytes("UTF-8") );
		    out_stream.flush();
		    out_stream.close();

		    doc = db.parse(conn.getInputStream());
		    
		} catch (MalformedURLException e) {
			throw new ServiceException("Exception.", e);
		} catch (IOException e) {
			throw new ServiceException("Exception.", e);
		} catch (ParserConfigurationException e) {
			throw new ServiceException("Exception.", e);
		} catch (SAXException e) {
			throw new ServiceException("Exception.", e);
		}finally {
			if (wr != null) {
				try {
					wr.close();
				} catch (IOException e) {
					return null;
				}
			}
		}
		
		return doc;
	}

	public static HashMap<String, Object> parseResponseXML(Document doc) {

		if (doc == null )
			return null;
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		Node rootItem = doc.getElementsByTagName(ReqMessage.TAG_eos).item(0);
		NodeList list = rootItem.getChildNodes();
		
		for(int j = 0 ; j < list.getLength() ; j++) {
			Node temp = list.item(j);
			if(temp.getNodeType() == Node.ELEMENT_NODE) {
				if(temp.getFirstChild() != null) {
					String tmpNodeName = temp.getNodeName();
					result.put(tmpNodeName, temp.getTextContent());
				}
			}
		}
		
		return result;
	}
	
}
