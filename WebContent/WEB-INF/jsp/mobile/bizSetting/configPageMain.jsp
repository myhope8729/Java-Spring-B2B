<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script type="text/javascript">
	var messages = {
		deleteMsg : "<qc:message code='system.alert.reallyDelete' />"
	};
</script>

<div data-role="header"  data-theme="b" >
    <h3 class="page_title">首页设置</h3>
</div>
	
<div role="main" class="ui-content">
	
	<div id="logo-setting" class="panel-default">		
		<div class="panel-heading">设置(logo, 标题, 颜色)</div>
		<div class="panel-body">		
			<form id="logoTopicFrm" class="admin-form" action="<%= BaseController.getCmdUrl("ConfigPage", "savePageMain") %>" method="post" enctype="multipart/form-data">				
				<div class="form-group">
					<label class="control-label">Logo图片</label>
					<div class="col-md-12 alignC">
						<div class="fileinput 
							<c:if test="${not empty loginUser.logoImgPath}">fileinput-exists</c:if>
							<c:if test="${empty loginUser.logoImgPath}">fileinput-new</c:if>" data-provides="fileinput">
							<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 150px; height: 150px;">
								<img width="140" height="140" src="<c:url value="/uploaded/userlogo/${loginUser.logoImgPath}"/>" 
									onerror="javascript:this.src='/images/noImage_140x140.gif'">
							</div>
							<div>
								<span class="default btn-file">
							    	<span class="fileinput-new btn btn-primary">选择图片</span>
							    	<span class="fileinput-exists btn btn-info">变更</span>
							    	<input type="file" name="logoImgFile" id="logoImgFile">
							    </span>
							    <a href="#" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput">删除</a>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="control-label">标题</label>
					<input placeholder="标题" data-clear-btn="true" type="text" name="topic1" value="${loginUser.topic1}" class="form-control" validate="required: false">
				</div>
				<div class="form-group">
					<label class="control-label">说明</label>
					<input placeholder="说明" data-clear-btn="true" type="text" name="topic2" value="${loginUser.topic2}" class="form-control" validate="required: false">
				</div>
				<div class="form-group">
					<label class="control-label"><span class="required">*</span>主颜色 (只为PC端)</label>
					<input type="text" name="mainColor" value="${loginUser.mainColor}"	class="jscolor form-control" validate="required: true">
				</div>
				<div class="form-group">
					<label class="control-label"><span class="required">*</span>背景颜色 (只为PC端)</label>
					<input type="text" name="bkColor" value="${loginUser.bkColor}" class="jscolor form-control" validate="required: true">
				</div>
				<div class="form-group alignC">
					<label class="control-label"></label>
					<button type="submit" class="w150 mg0-auto" data-mini="true"  data-theme="b" data-icon="check" ><qc:message code="system.common.btn.save"/></button>
				</div>
			</form>
		</div>
	</div>		
	<div id="slider-setting" class="panel panel-default">
		<div class="panel-heading">幻灯片设置</div>
		<div class="block-table">
			<c:forEach var="banner" items="${bannerList}">
		    	<div class="col-sm-2 col-xs-6 border-white pd-none alignC">
			    	<a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageBanner") %>&bannerId=${banner.bannerId}">			
						<c:choose>
							<c:when test="${banner.showMark == 'Y'}">
								<img width="150px" height="50px" src="<c:url value="/uploaded/pagebanner/${banner.bannerImgPath}"/>" onerror="javascript:this.src='/images/sc.png'">
							</c:when>
							<c:otherwise>
								<img width="50px" height="50px" src="<c:url value="/images/del.jpg"/>" onerror="javascript:this.src='/images/sc.png'">
							</c:otherwise>
						</c:choose>
					</a>
			    </div>
			</c:forEach>
		</div>
	</div>
	<div id="detail-setting" class="panel-default">
		<div class="panel-heading">页面内容设置
			<span class="ftR add-row"><i class="fa fa-plus-circle"></i>&nbsp;添加区域</span>
		</div>
		<div class="panel-body">
			<c:set var="col_num" value="0"/>
			<c:set var="row_num" value="0"/>
			<c:forEach var="detail" items="${detailList}">
				<c:if test="${(row_num != detail.rowNum && detail.colNum != 0 && row_num != 0) || (col_num != detail.colNum && col_num != 0)}">
									</div>
								</div>
							</div>
				</c:if>
		<c:if test="${row_num != detail.rowNum && row_num != 0}">
						</div>
					</div>
				</div>
			</div>
		</c:if>	
		<c:if test="${row_num != detail.rowNum}">
    		<c:set var="total_width" value="0"/>
			<div class='row' data-row="${detail.rowNum }">
				<div class='well'>
					<div class='row'>
						<div class='row_header'>
							<span class='ftR add-col'><i class='fa fa-plus-square '></i></span>
							<span class='ftR del-block'><i class='fa fa-minus-circle '></i></span>
						</div>
						<div class='row_container'>
		</c:if>
						<c:if test="${(row_num != detail.rowNum && detail.colNum != 0) || col_num != detail.colNum}">
							<c:set var="col_mobile" value="33.3"/>
							<c:if test="${not empty detail.widthMob and detail.widthMob > 33.3}">
								<c:set var="col_mobile" value="${detail.widthMob }"/>
							</c:if>
							<div class='col <c:if test="${total_width > 100 }">clear</c:if>' 
								<c:if test="${not empty detail.widthMob }"> data-mobile="${detail.widthMob }" </c:if>
								data-row="${detail.rowNum }" data-col="${detail.colNum }" style='width:${col_mobile}%;'>
							<c:if test="${total_width > 100 }">
								<c:set var="total_width" value="${col_mobile}"/>
							</c:if>
								<div class='well'>
									<div class='col_header'>
										<span class='ftR del-col'><i class='fa fa-times '></i></span>
										<span class='ftR add-cell'><i class='fa fa-plus '></i></span>
										<input class='col-mobile clear ftR' name='col-mobile' readonly value='${col_mobile}' data-role="none">
									</div>
									<div class='col_container'>
						</c:if>						
										<div class='cell_row' data-row="${detail.rowNum }" data-col="${detail.colNum }" data-cell="${detail.cellNum }">
											<div class='del-cell'><i class='fa fa-times'></i></div>
											<div class='cell_content'>
											<c:if test="${'PI0001' eq detail.detailType}">
												<a class="mg0-auto" data-theme="a" data-role="button" data-icon="plus" data-mini="true" data-iconpos="notext" href="<%= BaseController.getCmdUrl("ConfigPage", "configPageDetail") %>&detailId=${detail.detailId}" class="cell_edit">添加</a>
											</c:if>
											
											<c:if test="${'PI0002' eq detail.detailType}">
												<div class="pda5 alignC">
													<a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageDetail") %>&detailId=${detail.detailId}" style="color:#${detail.fontColor};">
														<c:if test="${'3' eq detail.fontSize}"><span>${detail.fontNote}</span></c:if>
														<c:if test="${'2' eq detail.fontSize}"><span class="font-12">${detail.fontNote}</span></c:if>
														<c:if test="${'4' eq detail.fontSize}"><span class="font-16">${detail.fontNote}</span></c:if>										
					                  				</a>
					               				</div>
											</c:if>
							
											<c:if test="${'PI0003' eq detail.detailType}">
												<div class="alignC">
													<a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageDetail") %>&detailId=${detail.detailId}">
														<img border="0" height="50" src="/uploaded/pagedetail/${detail.detailImgPath}" onerror="javascript:this.src='/images/sc.png'">
													</a>
					              				</div>
											</c:if>
													
											<c:if test="${'PI0004' eq detail.detailType}">
												<div class="alignC">
													<a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageDetail") %>&detailId=${detail.detailId}">
														<img border="0" height="50" src="/uploaded/useritem/${detail.userItem.itemImgPath}" onerror="javascript:this.src='/images/rm.jpg'">
													</a>
					              				</div>
											</c:if>
													
											<c:if test="${'PI0005' eq detail.detailType}">
												<div class="pd5 alignC" style="background-color:#${detail.titleFontBgColor}">
													<a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageDetail") %>&detailId=${detail.detailId}" style="color:#${detail.titleFontColor};">									
														<c:if test="${'3' eq detail.titleFontSize}"><span>${detail.infoType}</span></c:if>
														<c:if test="${'2' eq detail.titleFontSize}"><span class="font-12">${detail.infoType}</span></c:if>
														<c:if test="${'4' eq detail.titleFontSize}"><span class="font-16">${detail.infoType}</span></c:if>
					                 				</a>
					               				</div>
											</c:if>
											</div>
										</div>
										<c:set var="row_num" value="${detail.rowNum}"/>
										<c:set var="col_num" value="${detail.colNum}"/>
		</c:forEach>
						<c:if test="${col_num != 0}">			
									</div>
								</div>
							</div>
						</c:if>
		<c:if test="${row_num != 0}">
						</div>
					</div>
				</div>
			</div>
		</c:if>
		</div>
	</div>
</div>
