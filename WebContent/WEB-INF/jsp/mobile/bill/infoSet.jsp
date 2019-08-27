<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div data-role="header" data-theme="b" >
    <h3 class="page_title">${info.info}</h3>
	<a data-role="button" data-icon="back" data-iconpos="left" data-theme="b" data-inline="true" class="ui-btn-right" href="<%= BaseController.getCmdUrl("Info", "infoList") %>"><qc:message code="system.common.btn.back"/></a>
</div>

<!-- <div role="main" class="ui-content"> -->

	<form class="admin-form" action="<%= BaseController.getCmdUrl("Info", "saveInfoPic") %>" id="infoPicForm" method="post" enctype="multipart/form-data">	
		<input type="hidden" name="billId" id="billId" value="${info.billId}" /> 
		
		<div class="form-group">
			<div class="col-sm-3">
				<button type="button" class="btn btn-primary" data-theme="b" data-icon="check" id="saveInfoPic"><qc:message code="system.common.btn.save"/></button>
			</div>
			<div class="col-sm-3 alignC">
				<a data-role="button" data-iconpos="left" class="btn btn-orange" data-mini="true" data-inline="true" target="_blank" href="<%= BaseController.getCmdUrl("Info", "viewInfoPage") %>&billId=${info.billId}&hostUserId=${info.hostUserId}"><qc:message code="system.common.btn.info.preview"/></a>
			</div>
			<div class="col-sm-6">
				<div class="custom_block2">
					<div class="ui-block-a">			
						<button type="button" data-icon="plus"  data-mini="true" id="addTextRow"><qc:message code="system.common.btn.product.textrow"/><i class="fa fa-pencil-square-o text-success mgl10"></i></button>
					</div>
					<div class="ui-block-b">	
						<button type="button" data-icon="plus"  data-mini="true"  id="addPictureRow"><qc:message code="system.common.btn.product.imagerow"/><i class="fa fa-picture-o text-success mgl10"></i></button>			
					</div>
				</div>
			</div>
		</div>
		
		<div class="item_content">
			<div class="panel panel-default">
				<div class="panel-heading alignC">页面内容</div>
				<div class="panel-body">
					<c:set var="lineId" value="1"></c:set>
					
					<c:forEach var="item" items="${infoList}">
						
						<c:if test="${item.dtype == 'PI0002' }">
							<div class="text_row well fade in" id="line_${item.lineId}">
								<button type="button" data-mini="true" data-theme="b"  data-icon="edit" data-iconpos="notext"  class="editText"></button>
								<button type="button" data-mini="true" data-theme="b"  data-icon="delete" data-iconpos="notext" class="close" data-dismiss="alert"></button>
								<div class="text_content">
									${item.pnote }
								</div>
								<div class="actioon_bar">
									<label>
										<input type="checkbox" name="showMark[${lineId}]" id="showMark${lineId}" <c:if test="${item.showmark eq 'Y' }"> checked="checked"</c:if> />
										显示文字
									</label>
								</div>
							</div>
						</c:if>
						
						<c:if test="${item.dtype == 'PI0003' }">
							<div class="image_row well fade in"  id="line_${item.lineId}">
								<button type="button" data-mini="true" data-theme="b" class="close"  data-icon="delete" data-iconpos="notext" data-dismiss="alert"></button>
								<c:set var="lineId" value="${item.lineId}"></c:set>
							
								<c:set var="imgCnt" value="0"></c:set>
								<c:forEach begin="${imgCnt}" end="3" var="imgInx">
									<c:set var="exists" value="0"></c:set>
									
									<c:forEach var="detail" items="${item.detailList}">
										<c:if test="${(imgCnt+1) eq detail.dno}">
											<div class="col-xs-6">
												<div class="row">
													<div class="fileinput fileinput-exists col-xs-12 pd5" data-provides="fileinput">
														<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width:100%;height:130px;">
															<c:if test="${not empty detail.url}">
															<input type="hidden" name="fileFlg${item.lineId}_${imgCnt}" value="1" />
															<img src='/uploaded/info_pic/${detail.url}' />
															</c:if>
															<c:if test="${empty detail.url}">
																<img src='/uploaded/itemwidget/noImage_300x200.gif' />
															</c:if>
														</div>
														<img src='/uploaded/itemwidget/noImage_300x200.gif'  class="noImage1 fileinput-new" data-trigger="fileinput"/>
														<div>
															<span class="default btn-file">
																<span class="fileinput-new btn btn-primary"><qc:message code="system.common.btn.selectimage"/></span>
																<span class="fileinput-exists btn btn-info"><qc:message code="system.common.btn.change"/></span>
																<input type="file" name="imgFile[${item.lineId}][${imgCnt}]" id="imgFile${item.lineId}_${imgCnt}" />
															</span>
															<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput"><qc:message code="system.common.btn.delete"/></a>
														</div>
															<label>
																<input type="checkbox"  name="showMark[${lineId}][${imgCnt}]" id="showMark${lineId}_${imgCnt}" <c:if test="${detail.showYn eq 'Y' }"> checked="checked"</c:if> />显示图片
															</label>
													</div>
												</div>
											</div>			
											<c:set var="exists" value="1"></c:set>
										</c:if>
									</c:forEach>
									
									<c:if test="${exists eq '0'}">
										<div class="col-xs-6">
											<div class="row">
												<div class="fileinput fileinput-new col-xs-12 pd5" data-provides="fileinput">
													<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width:100%;height:130px;">
													</div>
													<img src='/uploaded/itemwidget/noImage_300x200.gif'  class="noImage1 fileinput-new" data-trigger="fileinput"/>
													<div>
														<span class="default btn-file">
															<span class="fileinput-new btn btn-primary"><qc:message code="system.common.btn.selectimage"/></span>
															<span class="fileinput-exists btn btn-info"><qc:message code="system.common.btn.change"/></span>
															<input type="file" name="imgFile[${lineId}][${imgInx}]" id="imgFile${lineId}_${imgInx}">
														</span>
														<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput"><qc:message code="system.common.btn.delete"/></a>
													</div>
													<label>
														<input type="checkbox" name="showMark[${lineId}][${imgCnt}]" id="showMark${lineId}_${imgCnt}"  <c:if test="${detail.showYn eq 'Y' }"> checked="checked"</c:if>/>显示图片
													</label> 
												</div>
											</div>
										</div>
									</c:if>
									
									<c:set var="imgCnt" value="${imgCnt + 1}"></c:set>
									
								</c:forEach>
							</div>
						</c:if>
						<c:set var="lineId" value="${lineId + 1}"></c:set>
					</c:forEach>
					
					<input type="hidden" id="currentLineNo" value="${lineId}" />
				</div>
			</div>
		</div>
		<input type="hidden" name="isPreview" id="isPreview" value="false" />
	</form>
<!-- </div> -->

<input type="hidden" id="addState" value="add" />

<div class="modal fade" id="textModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" data-role="none" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">请输入文字内容</h4>
			</div>
			<div class="modal-body">
				<textarea class="form-control" data-role="none" name="editor1" id="text-editor" rows="6"></textarea>
			</div>
			<div class="modal-footer">
				<button data-role="button" data-mini="true" data-theme="a" data-inline="true" class="pull-left" id="textSave"><qc:message code="system.common.btn.save"/></button>
				<button data-role="button" data-mini="true" data-theme="a" data-inline="true" class="pull-right" data-dismiss="modal"><qc:message code="system.common.btn.close"/></button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>