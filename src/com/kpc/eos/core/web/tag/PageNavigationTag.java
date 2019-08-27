
package com.kpc.eos.core.web.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;

import com.kpc.eos.core.model.PagingModel;
import com.kpc.eos.core.util.CodeUtil;
import com.kpc.eos.model.system.CodeModel;

/**
 * PageNavigationTag
 * =================
 * Description : 
 * Methods :
 */
public class PageNavigationTag extends PrintBeanTag {

	private static final long serialVersionUID = -1820383240246213384L;

	private PagingModel pagingModel;
	/**
	 * click event
	 * default : movePage
	 */
	private String functionName;
	/**
	 * search model
	 */
	private Object bean;
	
	public void setPagingModel(PagingModel pagingModel) {
		this.pagingModel = pagingModel;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public void setPrintBean(Object printBean) {
		this.bean = printBean;
	}
	
	@Override
	public int doStartTag() throws JspException {
		
		try {
			if (StringUtils.isBlank(functionName)) {
				this.functionName = "movePage";
			}
			
			pageContext.getOut().print(renderPageNavigation());

		} catch (Exception e) {
			throw new JspException("Page Navigation Tag render fail.",  e);
		} finally {
			this.release();
		}

		return EVAL_PAGE;
	}

	private String renderPageNavigation() throws Exception {
		
		StringBuffer sb = new StringBuffer();
		
		int pageNo = pagingModel.getPage(); 
		if (pageNo < 1)
			pageNo = 1;
		
		//paging
		sb.append("<div class=\"paging_area\">");
		if (pagingModel.getPrevPageNo() > 0) {
			sb.append("<button class=\"arrow\" onclick=\"").append(functionName).append("(").append(pagingModel.getPrevPageNo()).append(");\">&lt;</button>");
		}
		
		for (int no = pagingModel.getFromPageNo(); no <= pagingModel.getToPageNo(); no++) {
			sb.append("<button class=\"");
			if (no == pageNo) {
				sb.append(" selected");
			}
			if (no == pagingModel.getToPageNo()) {
				sb.append(" last");
			}
			sb.append("\"");
			sb.append(" onclick=\"").append(functionName).append("(").append(no).append(");\" ");
			sb.append(">");
			sb.append(no);
			sb.append("</button>");
		}
		if (pagingModel.getNextPageNo() > 0) {
			sb.append("<button class=\"arrow\" onclick=\"").append(functionName).append("(").append(pagingModel.getNextPageNo()).append(");\">&gt;</button>");
		}
		sb.append("&nbsp;&nbsp;");
		
		//List<CodeModel> codes = CodeUtil.getCodeList("SY014");
		List<CodeModel> codes = new ArrayList<CodeModel>();
		if (codes.size() > 0) {
			sb.append("<select id=\"rowPerPage\" onchange=\"").append(functionName).append("(1, this.value);\">");
			for (CodeModel code : codes) {
				sb.append("<option value=\"").append(code.getCodeName()).append("\"");
				if (pagingModel.getRows() > 0 && pagingModel.getRows() == Integer.parseInt(code.getCodeName())) {
					sb.append(" selected=\"selected\"");
				}
				sb.append(">").append(code.getCodeName()).append("</option>");
			}
			sb.append("</select>");
		}
		sb.append("</div>");

		sb.append("<form id=\"pageNaviFrm\" name=\"pageNaviFrm\" method=\"post\" style=\"display:none;\">");
		if (this.bean != null) {
			sb.append(super.printBean("", this.bean));
		} else {
			sb.append(super.printBean("page.", pagingModel));
		}
		sb.append("</form>");
		
		return sb.toString();
	}
	
	@Override
	public void release() {
		this.pagingModel = null;
		this.functionName = null;
		this.bean = null;
		super.release();
	}
	
}
