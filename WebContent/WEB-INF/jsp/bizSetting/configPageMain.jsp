<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<script type="text/javascript">
	var messages = {
		deleteMsg : "<qc:message code='system.alert.reallyDelete' />"
	};
</script>
<style>
	a { color: #0000FF; }
</style>

<div class="admin_body">
	<h3 class="page_title">首页设置</h3>
	
	<div id="logo-setting" class="panel-default">		
		<div class="panel-heading">设置(logo, 标题, 颜色)</div>
		<div class="panel-body">		
			<form id="logoTopicFrm" class="form-horizontal admin-form" role="form" action="<%= BaseController.getCmdUrl("ConfigPage", "savePageMain") %>" method="post" enctype="multipart/form-data">				
				<div class="form-group row">
					<label class="col-md-4 control-label">Logo图片</label>
					<div class="col-md-4">
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
				<div class="form-group row">
					<label class="col-md-4 control-label">标题</label>
					<div class="col-md-4">
						<input type="text" name="topic1" value="${loginUser.topic1}" class="form-control" validate="required: false">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-md-4 control-label">说明</label>
					<div class="col-md-4">
						<input type="text" name="topic2" value="${loginUser.topic2}" class="form-control" validate="required: false">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-md-4 control-label"><span class="required">*</span>主颜色</label>
					<div class="col-md-4">
						<input type="text" name="mainColor" value="${loginUser.mainColor}"	class="jscolor form-control" validate="required: true">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-md-4 control-label"><span class="required">*</span>背景颜色</label>
					<div class="col-md-4">
						<input type="text" name="bkColor" value="${loginUser.bkColor}" class="jscolor form-control" validate="required: true">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-md-4 control-label"></label>
					<div class="col-md-4">
						<input type="submit" class="btn btn-primary" value="<qc:message code="system.common.btn.save" />">
					</div>
				</div>
			</form>
		</div>
	</div>		
	<div id="slider-setting" class="panel panel-default">
		<div class="panel-heading">幻灯片设置</div>
		<div class="block-table">
			<c:forEach var="banner" items="${bannerList}">
		    	<div class="block-cell border-white">
			    	<a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageBanner") %>&bannerId=${banner.bannerId}">			
						<c:choose>
							<c:when test="${banner.showMark == 'Y'}">
								<img width="100%" height="150" src="<c:url value="/uploaded/pagebanner/${banner.bannerImgPath}"/>" onerror="javascript:this.src='/images/sc.png'">
							</c:when>
							<c:otherwise>
								<img width="100%" height="150" src="<c:url value="/images/del.jpg"/>" onerror="javascript:this.src='/images/sc.png'">
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
			<span class="ftR mobile-view"><i class="fa fa-mobile"></i>&nbsp;手机端设置</span>
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
									<span class='ftR add-col'><i class='fa fa-plus-square '></i>&nbsp;添加分区</span>
									<span class='ftR del-block'><i class='fa fa-minus-circle '></i>&nbsp;删除区域</span>
								</div>
								<div class='row_container'>
				</c:if>
								<c:if test="${(row_num != detail.rowNum && detail.colNum != 0) || col_num != detail.colNum}">
									<c:set var="col_width" value="10"/>
									<c:if test="${not empty detail.widthPc }">
										<c:set var="col_width" value="${detail.widthPc }"/>
									</c:if>
									<c:set var="total_width" value="${total_width + col_width }" />
									
									<c:set var="col_mobile" value="33.3"/>
									<c:if test="${not empty detail.widthMob and detail.widthMob > '33.3'}">
										<c:set var="col_mobile" value="${detail.widthMob }"/>
									</c:if>
									<div class='col <c:if test="${total_width > 100 }">clear</c:if>' 
										<c:if test="${not empty detail.widthPc }"> data-width="${detail.widthPc }" </c:if> 
										<c:if test="${not empty detail.widthMob }"> data-mobile="${detail.widthMob }" </c:if>
										data-row="${detail.rowNum }" data-col="${detail.colNum }" style='width:${col_width}%;'>
									<c:if test="${total_width > 100 }">
										<c:set var="total_width" value="${col_width}"/>
									</c:if>
										<div class='well'>
											<div class='col_header'>
												<span class='ftR del-col'><i class='fa fa-times '></i></span>
												<span class='ftR add-cell'><i class='fa fa-plus '></i></span>
												<input class='col-width ftR' name='col-width' readonly value='${col_width}'>
												<input class='col-mobile ftR' name='col-mobile' readonly value='${col_mobile}'>
											</div>
											<div class='col_container'>
								</c:if>
												<div class='cell_row' data-row="${detail.rowNum }" data-col="${detail.colNum }" data-cell="${detail.cellNum }">
													<div class='del-cell'><i class='fa fa-times'></i></div>
													<div class='cell_content'>
													<c:if test="${'PI0001' eq detail.detailType}">
														<a href='<%= BaseController.getCmdUrl("ConfigPage", "configPageDetail") %>&detailId=${detail.detailId}' class='cell_edit'>添加+</a>
													</c:if>
													<c:if test="${'PI0002' eq detail.detailType}">
														<div class="border-bottom-grey pd5" style="background-color:#${detail.bgColor}">
															<a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageDetail") %>&detailId=${detail.detailId}" style="color:#${detail.fontColor};">
																<c:if test="${'3' eq detail.fontSize}"><span>${detail.fontNote}<span style="color:#0000ff;padding-left:10px;">(文字)</span></span></c:if>
																<c:if test="${'2' eq detail.fontSize}"><span class="font-12">${detail.fontNote}<span style="color:#0000ff;padding-left:10px;">(文字)</span></span></c:if>
																<c:if test="${'4' eq detail.fontSize}"><span class="font-16">${detail.fontNote}<span style="color:#0000ff;padding-left:10px;">(文字)</span></span></c:if>										
							                  				</a>
							               				</div>
													</c:if>
													<c:if test="${'PI0003' eq detail.detailType}">
														<a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageDetail") %>&detailId=${detail.detailId}">
															<img border="0" height="50" src="/uploaded/pagedetail/${detail.detailImgPath}" onerror="javascript:this.src='/images/sc.png'">
															<div class="detail">(图片)</div>
														</a>
													</c:if>
													<c:if test="${'PI0004' eq detail.detailType}">
														<a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageDetail") %>&detailId=${detail.detailId}">
															<img border="0" height="50" src="/uploaded/useritem/${detail.userItem.itemImgPath}" onerror="javascript:this.src='/images/rm.jpg'">
															<div class="detail">(商品)${detail.userItem.itemName}</div>
														</a>
													</c:if>
													<c:if test="${'PI0005' eq detail.detailType}">
														<div class="border-bottom-grey pd5" style="background-color:#${detail.titleFontBgColor}">
															<a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageDetail") %>&detailId=${detail.detailId}" style="color:#${detail.titleFontColor};">									
																<c:if test="${'3' eq detail.titleFontSize}"><span>${detail.infoType}<span style="color:#0000ff;padding-left:10px;">(栏目信息)</span></span></c:if>
																<c:if test="${'2' eq detail.titleFontSize}"><span class="font-12">${detail.infoType}<span style="color:#0000ff;padding-left:10px;">(栏目信息)</span></span></c:if>
																<c:if test="${'4' eq detail.titleFontSize}"><span class="font-16">${detail.infoType}<span style="color:#0000ff;padding-left:10px;">(栏目信息)</span></span></c:if>
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