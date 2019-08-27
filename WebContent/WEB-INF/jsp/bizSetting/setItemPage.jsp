<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>
<script type="text/javascript" src="<c:url value="/js/lib/plugins/tiny_mce/tiny_mce_src.js"/>"></script>
<script type="text/javascript">
var preview = ${isPreview};
var hostId = "${hostUserId}";
</script>

<div class="admin_body">
	<h3 class="page_title">设置商品详情</h3>
<form class="form-horizontal" role="form" action="<%= BaseController.getCmdUrl("UserItem", "saveItemPage") %>" id="itemPageForm" method="post" enctype="multipart/form-data">	
	<div class="action_bar row">
		<div class="col-md-6 text-left">
			<label>商品名称:&nbsp;</label>${itemName},&nbsp;
			<label>商品编码:&nbsp;</label>${itemNo}
		</div>
		<div class="col-md-6 text-right">
			<button type="button" class="btn btn-primary mgl10" id="saveItemPage"><qc:message code="system.common.btn.save"/></button>
			<a type="button" class="btn btn-primary mgl10" id="previewItemPage" target="_blank"><qc:message code="system.common.btn.preview"/></a>
			<button type="button" class="btn btn-primary mgl10" id="addTextRow"><qc:message code="system.common.btn.product.textrow"/></button>
			<button type="button" class="btn btn-primary mgl10" id="addPictureRow"><qc:message code="system.common.btn.product.imagerow"/></button>
			<a type="button" class="btn btn-default mgl10" href="UserItem.do?cmd=userItemList"><qc:message code="system.common.btn.back"/></a>
		</div>
	</div>
				
	<div class="slideshow">
		<div class="panel panel-default">
			<div class="panel-heading">幻灯片设置</div>
			<div class="panel-body">
				<input type="hidden" name="line[]" value="0"/>
				<input type="hidden" name="type[]" value="WT0001"/>
				<c:set var="listSize" value="0"></c:set>
				<c:forEach var="slideImg" items="${itemSlideImgList}">
					<c:forEach begin="${listSize}" end="3" var="imgInx">
						<c:if test="${imgInx < slideImg.colNo}">
							<div class="col-md-3">
								<div class="row">
									<div class="fileinput fileinput-new col-md-12" data-provides="fileinput">
										<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width:100%;height: 150px;">
										</div>
										<img src='/uploaded/itemwidget/noImage_300x200.gif'  class="noImage fileinput-new" data-trigger="fileinput"/>
										<div>
											<span class="default btn-file">
											<span class="fileinput-new btn btn-primary"><qc:message code="system.common.btn.selectimage"/></span>
											<span class="fileinput-exists btn btn-info"><qc:message code="system.common.btn.change"/></span>
											<input type="file" name="imgFile[0][${imgInx}]" id="imgFile${0}_${imgInx}">
											</span>
											<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput"><qc:message code="system.common.btn.delete"/></a>
										</div>
									</div>
								</div>
							</div>
							<c:set var="listSize" value="${listSize + 1}"></c:set>
						</c:if>
						<c:if test="${imgInx == slideImg.colNo }">
							<div class="col-md-3">
								<div class="row">
									<div class="fileinput col-md-12 fileinput-exists" data-provides="fileinput">
										<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width:100%;height: 150px;">
											<c:if test="${not empty slideImg.widgetContent}">
											<img src='/uploaded/itemwidget/${slideImg.widgetContent}' />
											</c:if>
										</div>
										<img src='/uploaded/itemwidget/noImage_300x200.gif'  class="noImage fileinput-new" data-trigger="fileinput"/>
										<div>
											<span class="default btn-file">
											<span class="fileinput-new btn btn-primary"><qc:message code="system.common.btn.selectimage"/></span>
											<span class="fileinput-exists btn btn-info"><qc:message code="system.common.btn.change"/></span>
											<input type="file" name="imgFile[${slideImg.lineNo}][${listSize}]" id="imgFile${slideImg.lineNo}_${imgCnt}">
											</span>
											<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput"><qc:message code="system.common.btn.delete"/></a>
										</div>
									</div>
								</div>
							</div>
							<c:set var="listSize" value="${listSize + 1}"></c:set>
						</c:if>
					</c:forEach>
				</c:forEach>
				<c:if test="${listSize < 4 }">
				<c:forEach begin="0" end="${3 - listSize}" var="imgInx">
					<div class="col-md-3">
						<div class="row">
							<div class="fileinput fileinput-new col-md-12" data-provides="fileinput">
								<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width:100%;height: 150px;">
								</div>
								<img src='/uploaded/itemwidget/noImage_300x200.gif'  class="noImage fileinput-new" data-trigger="fileinput"/>
								<div>
									<span class="default btn-file">
									<span class="fileinput-new btn btn-primary"><qc:message code="system.common.btn.selectimage"/></span>
									<span class="fileinput-exists btn btn-info"><qc:message code="system.common.btn.change"/></span>
									<input type="file" name="imgFile[0][${listSize + imgInx}]" id="imgFile0_${listSize + imgInx}">
									</span>
									<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput"><qc:message code="system.common.btn.delete"/></a>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
	
	<div class="item_content">
		<div class="panel panel-default">
			<div class="panel-heading">内容设置</div>
			<div class="panel-body">
			<c:set var="lineNo" value="0"></c:set>
			<c:set var="imgCnt" value="0"></c:set>
			<c:set var="widgetType" value="WT0001"></c:set>
			<c:forEach var="itemWidget" items="${itemWidgetList}">
				<c:if test="${lineNo != itemWidget.lineNo}">
					<c:if test="${widgetType == 'WT0003'}">
						<c:if test="${imgCnt < 4 }">
						<c:forEach begin="0" end="${3 - imgCnt}" var="imgInx">
							<div class="col-md-3">
								<div class="row">
									<div class="fileinput fileinput-new col-md-12" data-provides="fileinput">
										<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width:100%;height: 150px;">
										</div>
										<img src='/uploaded/itemwidget/noImage_300x200.gif'  class="noImage fileinput-new" data-trigger="fileinput"/>
										<div>
											<span class="default btn-file">
											<span class="fileinput-new btn btn-primary"><qc:message code="system.common.btn.selectimage"/></span>
											<span class="fileinput-exists btn btn-info"><qc:message code="system.common.btn.change"/></span>
											<input type="file" name="imgFile[${lineNo}][${imgCnt + imgInx}]" id="imgFile${lineNo}_${imgCnt + imgInx}">
											</span>
											<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput"><qc:message code="system.common.btn.delete"/></a>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						</c:if>
						</div> <!-- Image Row End ImgRow-->
					</c:if>
					<c:set var="widgetType" value="${itemWidget.widgetType}"></c:set>
					<c:set var="imgCnt" value="0"></c:set>
				</c:if>
				<c:if test="${itemWidget.widgetType == 'WT0002' }">
				<div class="text_row well fade in" id="line_${itemWidget.lineNo}">
					<button type="button" class="editText"><i class="fa fa-pencil"></i></button>
					<button type="button" class="close" data-dismiss="alert"><i class="fa fa-times"></i></button>
					<div class="text_content">
						${itemWidget.widgetContent }
					</div>
				</div>
				<c:set var="lineNo" value="${itemWidget.lineNo}"></c:set>
				</c:if>
				<c:if test="${itemWidget.widgetType == 'WT0003' }">
					<c:if test="${lineNo != itemWidget.lineNo}">
					<div class="image_row well fade in"  id="line_${itemWidget.lineNo}">
						<button type="button" class="close" data-dismiss="alert"><i class="fa fa-times"></i></button>
					<c:set var="lineNo" value="${itemWidget.lineNo}"></c:set>
					</c:if>
					<c:forEach begin="${imgCnt}" end="3" var="imgInx">
						<c:if test="${imgInx < itemWidget.colNo}">
							<div class="col-md-3">
								<div class="row">
									<div class="fileinput fileinput-new col-md-12" data-provides="fileinput">
										<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width:100%;height: 150px;">
										</div>
										<img src='/uploaded/itemwidget/noImage_300x200.gif'  class="noImage fileinput-new" data-trigger="fileinput"/>
										<div>
											<span class="default btn-file">
											<span class="fileinput-new btn btn-primary"><qc:message code="system.common.btn.selectimage"/></span>
											<span class="fileinput-exists btn btn-info"><qc:message code="system.common.btn.change"/></span>
											<input type="file" name="imgFile[${lineNo}][${imgInx}]" id="imgFile${lineNo}_${imgInx}">
											</span>
											<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput"><qc:message code="system.common.btn.delete"/></a>
										</div>
									</div>
								</div>
							</div>
							<c:set var="imgCnt" value="${imgCnt + 1}"></c:set>
						</c:if>
						<c:if test="${imgInx == itemWidget.colNo }">
							<div class="col-md-3">
								<div class="row">
									<div class="fileinput col-md-12 fileinput-exists" data-provides="fileinput">
										<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width:100%;height: 150px;">
											<c:if test="${not empty itemWidget.widgetContent}">
											<img src='/uploaded/itemwidget/${itemWidget.widgetContent}' />
											</c:if>
										</div>
										<img src='/uploaded/itemwidget/noImage_300x200.gif'  class="noImage fileinput-new" data-trigger="fileinput"/>
										<div>
											<span class="default btn-file">
											<span class="fileinput-new btn btn-primary"><qc:message code="system.common.btn.selectimage"/></span>
											<span class="fileinput-exists btn btn-info"><qc:message code="system.common.btn.change"/></span>
											<input type="file" name="imgFile[${itemWidget.lineNo}][${imgCnt}]" id="imgFile${itemWidget.lineNo}_${imgCnt}">
											</span>
											<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput"><qc:message code="system.common.btn.delete"/></a>
										</div>
									</div>
								</div>
							</div>
							<c:set var="imgCnt" value="${imgCnt + 1}"></c:set>
						</c:if>
					</c:forEach>
				</c:if>
			</c:forEach>
			<c:if test="${widgetType == 'WT0003'}">
				<c:if test="${imgCnt < 4 }">
				<c:forEach begin="0" end="${3 - imgCnt}" var="imgInx">
					<div class="col-md-3">
						<div class="row">
							<div class="fileinput fileinput-new col-md-12" data-provides="fileinput">
								<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width:100%;height: 150px;">
								</div>
								<img src='/uploaded/itemwidget/noImage_300x200.gif'  class="noImage fileinput-new" data-trigger="fileinput"/>
								<div>
									<span class="default btn-file">
									<span class="fileinput-new btn btn-primary"><qc:message code="system.common.btn.selectimage"/></span>
									<span class="fileinput-exists btn btn-info"><qc:message code="system.common.btn.change"/></span>
									<input type="file" name="imgFile[${lineNo}][${imgCnt + imgInx}]" id="imgFile${lineNo}_${imgCnt + imgInx}">
									</span>
									<a href="javascript:;" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput"><qc:message code="system.common.btn.delete"/></a>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
				</c:if>
				</div> <!-- Image Row End ImgRow-->
			</c:if>
			<input type="hidden" id="currentLineNo" value="${lineNo}" />
			</div>
		</div>
	</div>
	<input type="hidden" name="itemId" id="itemId" value="${itemId}" />
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
				<textarea class="form-control" name="editor1" id="text-editor" rows="6" style="max-width: 100%; width: 100%;"></textarea>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn blue" id="textSave"><qc:message code="system.common.btn.save"/></button>
				<button type="button" class="btn default" data-dismiss="modal"><qc:message code="system.common.btn.close"/></button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>