
package com.kpc.eos.core.web.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * SendContentView
 * =================
 * Description : 
 * Methods :
 */
public class SendContentView extends AbstractView {

	private static final DecimalFormat	DCF	= new DecimalFormat("##############");
	
	private String fileNameCharSet;
	
	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		File 			file;
		FileInputStream	inputStream = null;
		OutputStream	os = null;
		
		if (model.get("file") instanceof File)
			file	= (File) model.get("file");
		else
			file	= new File((String) model.get("file"));
		
		inputStream	= new FileInputStream(file);
		try {
			byte[]			buf;
			int				rsize;
			
			String contentType	= getServletContext().getMimeType(file.getName());
			if (StringUtils.isBlank(contentType)) { 
				contentType	= "application/unknown";
			}
			
			String saveAs		= (String)model.get("saveAs");
			if (saveAs == null) {
				saveAs	= file.getName();
			}
			
			if (fileNameCharSet != null) {
				saveAs = URLEncoder.encode(saveAs, fileNameCharSet).replaceAll("\\+","%20");
			}
			
			buf	= new byte[1024];
			response.setHeader("Content-Length", DCF.format(file.length()));
			response.setContentType(contentType);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + saveAs + "\"");
			System.out.println("FileName=" + saveAs);
			
			os	= response.getOutputStream();
			try {
				while ((rsize = inputStream.read(buf, 0, buf.length)) != -1) {
					os.write(buf, 0, rsize);
				}
				os.flush();
            } catch (SocketException ignore) {
			}
		} finally {
			if (inputStream != null)
				inputStream.close();
			
			if (os != null)
				os.close();
		}
		
	}

	/**
	 * Indicate the character set of file name.
	 * @param charSet
	 */
	public void setFileNameCharSet(String charSet) {
		this.fileNameCharSet	= charSet;
	}
}
