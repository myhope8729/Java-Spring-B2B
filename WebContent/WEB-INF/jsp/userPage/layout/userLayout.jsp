<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title>${hostUser.webName}</title>
<%@ include file="/WEB-INF/jsp/common/commonHead.jspf" %>

<body>
<div id="errorDiv" style="display:none;"><ul></ul></div>
<div class="wrapper">
	
	<div class="bg-white">
	<%-- Start GNB Area --%>
	<tiles:insertAttribute name="top"/>
	<%-- End GNB Area --%>
	</div>

	<div class="container_body">
		<div class="container">
			<div class="f_clear bg-white" id="bodyarea">
				
				<div id="wrapper">
					<div id="content">
						
						<%-- Start Content Area --%>
						<tiles:insertAttribute name="content" />
						<%-- End Content Area --%>
			
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<%-- Start Footer Area --%>
	<tiles:insertAttribute name="bottom" />
	<%-- End Footer Area --%>
</div>

</body>
</html>
