package com.kpc.eos.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import sun.misc.BASE64Encoder;

import com.kpc.eos.core.exception.ServiceException;

public class HttpUtil {
	public static String urlEncode(String url)
	{
		String encodeUrl = "";
		try {
			encodeUrl = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		encodeUrl = encodeUrl.replace("%3A", ":");
		encodeUrl = encodeUrl.replace("%2F", "/");
		encodeUrl = encodeUrl.replace("%3F", "?");
		encodeUrl = encodeUrl.replace("%3D", "=");
		encodeUrl = encodeUrl.replace("%26", "&");

		return encodeUrl;
	}
	
	public static String getResponseFromURL(String url, String encType) throws Exception {
		URL urlInstance;
		String resultStr = "";
		URLConnection conn;
		try {
			url = urlEncode(url);
			urlInstance = new URL(url);
			conn = urlInstance.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(2000);
			conn.setUseCaches(false);
			InputStreamReader bis = new InputStreamReader(conn.getInputStream(), encType);
			char buffer[] = new char[1024];
			int readCnt = 0;
			while((readCnt = bis.read(buffer)) >0 ) {
				resultStr += new String(buffer, 0, readCnt);
			}
			buffer = null;
			bis.close();
		} catch (MalformedURLException e) {
			throw new ServiceException("Exception.", e);
		} catch (IOException e) {
			throw new ServiceException("Exception.", e);
		} finally {

		}
		
		return resultStr;
	}

	public static String getResponseFromPostRequest(String url, String postData) throws Exception {
		URL urlInstance;
		String resultStr = "";
		HttpURLConnection conn;
		try {
			url = urlEncode(url);
			urlInstance = new URL(url);
			conn = (HttpURLConnection)urlInstance.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(2000);
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
		    String param    = postData; 
		    OutputStream out_stream = conn.getOutputStream();
		    out_stream.write( param.getBytes("UTF-8") );
		    out_stream.flush();
		    out_stream.close();

		    InputStream is      = null;
		    BufferedReader in   = null;

		    is  = conn.getInputStream();
		    in  = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8 * 1024);

		    String line = null;
		    StringBuffer buff   = new StringBuffer();

		    while ( ( line = in.readLine() ) != null )
		    {
		        buff.append(line + "\n");
		    }
		    
		    resultStr    = buff.toString().trim();

		} catch (MalformedURLException e) {
			throw new ServiceException("Exception.", e);
		} catch (IOException e) {
			throw new ServiceException("Exception.", e);
		} 
		
		return resultStr;
	}
	
	public static String getResponseFromPostRequest2(String url, String postData) throws Exception {
		URL urlInstance;
		String resultStr = "";
		HttpURLConnection conn;
		try {
			url = urlEncode(url);
			urlInstance = new URL(url);
			conn = (HttpURLConnection)urlInstance.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(2000);
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			
		    String param    = postData; 
		    OutputStream out_stream = conn.getOutputStream();
		    out_stream.write( param.getBytes() );
		    out_stream.flush();
		    out_stream.close();

		    InputStream is      = null;
		    BufferedReader in   = null;

		    is  = conn.getInputStream();
		    in  = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8 * 1024);

		    String line = null;
		    StringBuffer buff   = new StringBuffer();

		    while ( ( line = in.readLine() ) != null )
		    {
		        buff.append(line + "\n");
		    }
		    
		    resultStr    = buff.toString().trim();

		} catch (MalformedURLException e) {
			throw new ServiceException("Exception.", e);
		} catch (IOException e) {
			throw new ServiceException("Exception.", e);
		} 
		
		return resultStr;
	}
	
	
}
