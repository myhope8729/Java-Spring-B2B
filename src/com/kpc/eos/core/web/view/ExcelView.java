package com.kpc.eos.core.web.view;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;

import com.kpc.eos.core.Constants;
import com.kpc.eos.core.exception.ServiceException;

public class ExcelView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
    	BufferedOutputStream bos = null;
    	HSSFWorkbook wb = null;
    	
		try {
			String fileName = null;
			if (model.get("fileName") == null) {
				fileName = "anonymous.xls";
			}
			fileName = (String) model.get("fileName");
			
			String mimeType = getServletContext().getMimeType(fileName);
			if (StringUtils.isBlank(mimeType)) {
				mimeType	= "application/unknown";	
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", "attachment; filename=\""	+ URLEncoder.encode(fileName, "UTF-8") + "\"");

			wb = (HSSFWorkbook) model.get(Constants.REQUEST_WORKBOOK_KEY);
			bos = new BufferedOutputStream(response.getOutputStream(), 1024);
			wb.write(bos);
			bos.flush();
        
		} catch (IOException e) {
        	throw new ServiceException("Failed.", e);
		} finally {
			if (bos != null) bos.close();
			wb = null;
		}    	
    }

}
