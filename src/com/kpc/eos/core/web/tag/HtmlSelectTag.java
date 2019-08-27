/**
 * Filename		: HtmlSelect.java
 * Description	:
 * 
 * 2017
 */
package com.kpc.eos.core.web.tag;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.kpc.eos.core.util.MessageUtil;

/**
 * Filename		: HtmlSelect.java
 * Description	:
 * 2017
 * @author		: RKRK
 */
public class HtmlSelectTag extends TagSupport
{
	
	/**
	 * Field		: serialVersionUID
	 * Description	:
	 * 2017
	 */
	private static final long serialVersionUID = 4334622422078231744L;

	private static Logger logger = Logger.getLogger(HtmlSelectTag.class);
		
	private String cssClass;
	private String name;
	private String id;
	private String customAttr;
	private List<Object> items;
	private String itemValue;
	private String itemLabel;
	
	private String selValue;
	
	private String isEmty;
	private String emptyValue;
	private String emptyLabel;
	
	private String modelAttr;
	
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			
			StringBuffer sb = new StringBuffer();
			sb.append("<select");
			
			if (id != null) 
			{
				sb.append(String.format(" id='%s'", id));
			}
			
			if (name != null) 
			{
				sb.append(String.format(" name='%s'", name));
			}
			
			if (cssClass != null) 
			{
				sb.append(String.format(" class='%s'", cssClass));
			}
			
			if (customAttr != null) 
			{
				sb.append(" " + customAttr);
			}
			
			sb.append(">\n");
			
			if (StringUtils.isNotEmpty(isEmty) && "true".equals(isEmty)) 
			{
				sb.append(String.format("<option value='%s'>%s</option>", emptyValue==null? "" : emptyValue, emptyLabel==null? "" : MessageUtil.getMessage(emptyLabel) ));
			}
			
			if (items != null && items.size() > 0) 
			{
				for(Object obj : items) 
				{
					String key, value, selected;
					
					key = (String)getFieldValue(obj, itemValue);
					value = (String)getFieldValue(obj, itemLabel);
					
					selected = (key!=null && key.equals(this.selValue))? " selected='selected'" : "";
					
					// model attributes
					String htmlModelAttr = getModelAttrHtml(obj);
					
					sb.append(String.format("<option value='%s' %s %s>%s</option>\n", key, selected, htmlModelAttr, value));
				}
			}
			sb.append("</select>");
			
			out.println(sb);
		} catch (Exception e) {
			if (logger.isDebugEnabled())
				logger.debug("", e);
				
			throw new JspException("Html Select Tag render fail.",  e);
		} finally {
			this.release();
		}
        
		return SKIP_BODY;
	}
	
	public void setItemValue(String value) 
	{
		this.itemValue = value;
	}
	
	public void setItemLabel(String value) 
	{
		this.itemLabel = value;
	}
	
	public void setCssClass(String value) 
	{
		this.cssClass = value;
	}
	
	public void setCustomAttr(String value) 
	{
		System.out.println("\n\n\n=====================\n");
		System.out.println(value);
		this.customAttr = value;
	}
	
	public void setModelAttr(String value) 
	{
		this.modelAttr = value;
	}
	
	public String getModelAttrHtml(Object obj) 
	{
		if (StringUtils.isNotEmpty(this.modelAttr)) 
		{
			StringBuilder sb = new StringBuilder();
			StringTokenizer st = new StringTokenizer(modelAttr, ",");
			while ( st.hasMoreElements() )
			{
				String fieldName = st.nextToken().trim();
				String value = (String) getFieldValue(obj, fieldName);
				sb.append(String.format(" %s='%s'", fieldName, value));
			}
			return sb.toString();
		}
		return "";
	}
	
	public void setSelValue(String value) 
	{
		this.selValue = value;
	}
	
	public void setEmptyValue(String value) 
	{
		this.emptyValue = value;
	}
	
	public void setEmptyLabel(String value) 
	{
		this.emptyLabel = value;
	}
	
	public void setIsEmpty(String value) 
	{
		this.isEmty = value;
	}
	
	public void setId(String value) 
	{
		this.id = value;
	}
	
	public void setName(String value) 
	{
		this.name = value;
	}
	
	public void setItems(List items)
	{
		this.items = items;
	}
	
	public static Object getFieldValue(Object obj, String fieldName)
	{
		Object ret = null;
		try
		{
			if (obj instanceof HashMap) 
			{
				Map objMap = (Map)obj;
				if (objMap.containsKey(fieldName)) {
					return objMap.get(fieldName);
				}
				else
				{
					return null;
				}
			}
			
			boolean oriAccessible = true;
			
	        Field field;
			field = obj.getClass().getDeclaredField(fieldName);
			
			if (!field.isAccessible())
			{
	            field.setAccessible(true);
	            oriAccessible = false;
	        }
			
			ret = field.get(obj);
			
			if (!oriAccessible) {
	            field.setAccessible(oriAccessible);
	        }
		} 
		catch (Exception e)
		{
			logger.error(">>> Error on getting a field value : " + e.getMessage(), e);
		}
		if (ret == null) ret = "";
		
		return ret;
	}
}
