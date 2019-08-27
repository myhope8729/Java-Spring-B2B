<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<script type="text/javascript" src="<c:url value="/js/lib/plugins/tiny_mce/tiny_mce_src.js"/>"></script>

<div class="admin_body">
	
	<form class="form-horizontal" role="form"  action="<%= BaseController.getCmdUrl("Info", "saveInfoPic") %>" id="infoPicForm" method="post" enctype="multipart/form-data">	
		<input type="hidden" name="billId" id="billId" value="${info.billId}" /> 
		
		<div class="action_bar row">
			<div class="col-sm-3 col-xs-12">
				<h3 class="page_title alignL" style="padding: 3px 0 0 0">${info.info }</h3>
			</div>
			<div class="col-sm-9 col-xs-12 text-right">
				<button type="button" class="btn btn-primary" id="saveInfoPic"><qc:message code="system.common.btn.save"/></button>
				<a class="btn btn-default" target="_blank" href="<%= BaseController.getCmdUrl("Info", "viewInfoPage") %>&billId=${info.billId}&hostUserId=${info.hostUserId}"><qc:message code="system.common.btn.info.preview"/></a>
				<button type="button" class="btn btn-primary mgl20" id="addTextRow"><qc:message code="system.common.btn.product.textrow"/></button>
				<button type="button" class="btn btn-primary" id="addPictureRow"><qc:message code="system.common.btn.product.imagerow"/></button>
				<a class="btn btn-default mgl20" href="<%= BaseController.getCmdUrl("Info", "infoList") %>"><qc:message code="system.common.btn.back"/></a>
			</div>
		</div>
		
		<div class="item_content">
			<div class="panel panel-default">
				<div class="panel-heading">页面内容</div>
				<div class="panel-body">
					<c:set var="lineId" value="1"></c:set>
					
					<c:forEach var="item" items="${infoList}">
						
						<c:if test="${item.dtype == 'PI0002' }">
							<div class="text_row well fade in" id="line_${item.lineId}">
								<button type="button" class="editText"><i class="fa fa-pencil"></i></button>
								<button type="button" class="close" data-dismiss="alert"><i class="fa fa-times"></i></button>
								<div class="text_content">
									${item.pnote }
								</div>
								<div class="showMarkWrap checkbox">
									<label>
										<input type="checkbox" name="showMark[${lineId}]" id="showMark${lineId}" <c:if test="${item.showmark eq 'Y' }"> checked="checked"</c:if> />
										显示文字
									</label>
								</div>
							</div>
						</c:if>
						
						<c:if test="${item.dtype == 'PI0003' }">
							<div class="image_row well fade in"  id="line_${item.lineId}">
								<button type="button" class="close" data-dismiss="alert"><i class="fa fa-times"></i></button>
								<c:set var="lineId" value="${item.lineId}"></c:set>
							
								<c:set var="imgCnt" value="0"></c:set>
								<c:forEach begin="${imgCnt}" end="3" var="imgInx">
									<c:set var="exists" value="0"></c:set>
									
									<c:forEach var="detail" items="${item.detailList}">
										<c:if test="${(imgCnt+1) eq detail.dno}">
											<div class="col-lg-3 col-md-6 col-sm-6">
												<div class="row">
													<div class="fileinput col-lg-12 fileinput-exists" data-provides="fileinput">
														<div class="fileinput-preview thumbnail" data-trigger="fileinput">
															<c:if test="${not empty detail.url}">
															<img src='/uploaded/info_pic/${detail.url}' onerror="javascript:this.src='/uploaded/itemwidget/noImage_300x200.gif'"  />
															</c:if>
														</div>
														<img src='/uploaded/itemwidget/noImage_300x200.gif'  class="noImage fileinput-new" data-trigger="fileinput"/>
														<div>
															<span class="default btn-file">
																<span class="fileinput-new btn btn-primary"><qc:message code="system.common.btn.selectimage"/></span>
																<span class="fileinput-exists btn btn-info"><qc:message code="system.common.btn.change"/></span>
																<input type="file" name="imgFile[${item.lineId}][${imgCnt}]" id="imgFile${item.lineId}_${imgCnt}" />
															</span>
															<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput"><qc:message code="system.common.btn.delete"/></a>
														</div>
														<div class="showMarkWrap checkbox"><label>
															<input type="checkbox" name="showMark[${lineId}][${imgCnt}]" id="showMark${lineId}_${imgCnt}" <c:if test="${detail.showYn eq 'Y' }"> checked="checked"</c:if> />显示图片
														</label></div>
													</div>
												</div>
											</div>			
											<c:set var="exists" value="1"></c:set>
										</c:if>
									</c:forEach>
									
									<c:if test="${exists eq '0'}">
										<div class="col-lg-3 col-md-6 col-sm-6">
											<div class="row">
												<div class="fileinput fileinput-new col-lg-12" data-provides="fileinput">
													<div class="fileinput-preview thumbnail" data-trigger="fileinput">
													</div>
													<img src='/uploaded/itemwidget/noImage_300x200.gif'  class="noImage fileinput-new" data-trigger="fileinput"/>
													<div>
														<span class="default btn-file">
															<span class="fileinput-new btn btn-primary"><qc:message code="system.common.btn.selectimage"/></span>
															<span class="fileinput-exists btn btn-info"><qc:message code="system.common.btn.change"/></span>
															<input type="file" name="imgFile[${lineId}][${imgInx}]" id="imgFile${lineId}_${imgInx}">
														</span>
														<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput"><qc:message code="system.common.btn.delete"/></a>
													</div>
													<div class="showMarkWrap checkbox"><label>
														<input type="checkbox" name="showMark[${lineId}][${imgCnt}]" id="showMark${lineId}_${imgCnt}"  <c:if test="${detail.showYn eq 'Y' }"> checked="checked"</c:if>/>显示图片
													</label></div>
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
</div>

<input type="hidden" id="addState" value="add" />
<div class="modal fade" id="textModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
				<h4 class="modal-title">请输入文字内容</h4>
			</div>
			<div class="modal-body">
				<textarea class="form-control" name="editor1" id="text-editor" rows="6" style="max-width:100%;width: 100%;"></textarea>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn blue" id="textSave"><qc:message code="system.common.btn.save"/></button>
				<button type="button" class="btn default" data-dismiss="modal"><qc:message code="system.common.btn.close"/></button>
			</div>
		</div>
	</div>
</div>