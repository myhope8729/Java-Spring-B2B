<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script type="text/javascript">
	var jsonObj = ${jsonObj};
	var messages = {
		itemAlreadyOnHome : "<qc:message code='useritem.item.isonhome' />"	
	};
</script>

<div class="admin_body">
	<h3 class="page_title">内容设置</h3>
	
	<form id="detailFrm" name="detailFrm" class="form-horizontal admin-form" role="form" action="<%= BaseController.getCmdUrl("ConfigPage", "savePageDetail") %>" method="post" enctype="multipart/form-data">
		<div class="form-group row">
			<label class="col-md-4 control-label"><span class="required">*</span>类别</label>
			<div class="col-md-4">
			    <qc:codeList var="dtypes" codeGroup="PI0000" />
				<select name="detailType" class="form-control">
					<c:forEach var="dtype" items="${dtypes}">
						<option value="${dtype.codeId}" <c:if test="${dtype.codeId eq detail.detailType}">selected</c:if>>${dtype.codeName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div id="product-detail" class="d_type PI0004">
			<div class="form-group row">
				<label class="col-md-4 control-label">商品</label>
				<div class="col-md-4">
					<div class="input-group">
						<span class="input-group-btn">
							<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#productModal"><i class="fa fa-search"></i> 选择商品</button>
						</span>
						<input type="hidden" name="productId" value="<c:if test="${detail.detailType eq 'PI0004' and not empty detail.userItem}">${detail.userItem.itemId}</c:if>"/>
						<input type="text" name="product-name" class="form-control" readonly value="<c:if test="${detail.detailType eq 'PI0004' }">${detail.userItem.itemName }(编码：${detail.userItem.itemNo })</c:if>"/>
					</div>
				</div>
			</div>
		</div>
		<div id="pic-detail" class="d_type PI0003">
			<div class="form-group row">
				<label class="col-md-4 control-label">图片</label>
				<div class="col-md-4">
					<div class="fileinput 
						<c:if test="${not empty detail.detailImgPath}">fileinput-exists</c:if>
						<c:if test="${empty detail.detailImgPath}">fileinput-new</c:if>" data-provides="fileinput">
						<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 100%; height: 150px;">	
							<c:if test="${detail.detailType eq 'PI0003' }">					
								<img height="140" src="<c:url value="/uploaded/pagedetail/${detail.detailImgPath}"/>" onerror="javascript:this.src='/images/sc.png'">
							</c:if>						
							<c:if test="${detail.detailType != 'PI0003' }">
								<img height="140" src="<c:url value="/uploaded/pagebanner/noImage_300x200.gif"/>" onerror="javascript:this.src='/images/sc.png'">
							</c:if>
						</div>
						<div>
							<span class="default btn-file">
						    	<span class="fileinput-new btn btn-primary">选择图片</span>
						    	<span class="fileinput-exists btn btn-info">变更</span>
						    	<input type="file" name="detailImgFile">
						    </span>
						    <a href="#" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput">删除</a>
						</div>
					</div>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-4 control-label">图片说明</label>
				<div class="col-md-4">
					<input type="text" name="picNote" value="${detail.picNote}" class="form-control" validate="required: false">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-4 control-label"><span class="required">*</span>图片比例(%)</label>
				<div class="col-md-4">
					<input type="text" name="picSize" value="${detail.picSize}" class="form-control" validate="required: true"
					onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" />
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-4 control-label"><span class="required">*</span>图片边框颜色</label>
				<div class="col-md-4">
					<input type="text" name="picBdColor" value="${detail.picBdColor}" class="form-control jscolor" validate="required: true">
				</div>
			</div>
		</div>
		<div id="font-detail" class="d_type PI0002">
			<div class="form-group row">
				<label class="col-md-4 control-label"><span class="required">*</span>文字内容</label>
				<div class="col-md-4">
					<input type="text" name="fontNote" value="${detail.fontNote}" class="form-control" validate="required: true">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-4 control-label"><span class="required">*</span>字体大小</label>
				<div class="col-md-4">
					<select name="fontSize" class="form-control">
						<option value="3" <c:if test="${'3' eq detail.fontSize}">selected</c:if>>3</option>
						<option value="2" <c:if test="${'2' eq detail.fontSize}">selected</c:if>>2</option>
				     	<option value="4" <c:if test="${'4' eq detail.fontSize}">selected</c:if>>4</option>					     	
				    </select>				
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-4 control-label"><span class="required">*</span>字体颜色</label>
				<div class="col-md-4">
					<input type="text" name="fontColor" value="${detail.fontColor}" class="form-control jscolor" validate="required: true">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-4 control-label"><span class="required">*</span>背景颜色</label>
				<div class="col-md-4">
					<input type="text" name="bgColor" value="${detail.bgColor}" class="form-control jscolor" validate="required: true">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-4 control-label"><span class="required">*</span>显示小点</label>
				<div class="col-md-4">
					<input type="checkbox" name="liMark" value="Y" <c:if test="${'Y' eq detail.liMark}">checked</c:if> />				
				</div>			
			</div>
			<div class="form-group row">
				<label class="col-md-4 control-label"><span class="required">*</span>电脑端不显示</label>
				<div class="col-md-4">
					<input type="checkbox" name="mobOnly" value="Y" <c:if test="${'Y' eq detail.mobOnly}">checked</c:if> />				
				</div>
			</div>
		</div>
		<div id="info-detail" class="d_type PI0005">
			<div class="form-group row">
				<label class="col-md-4 control-label"><span class="required">*</span>栏目类别</label>
				<div class="col-md-4">
					<select name="infoType" class="form-control">
						<option value="" selected>==请选择==</option>
						<c:forEach var="docType" items="${docTypeList}">
							<option value="${docType.c1}" <c:if test="${docType.c1 eq detail.infoType}">selected</c:if>>${docType.c1}</option>		
						</c:forEach>																	     	
				    </select>
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-4 control-label"><span class="required">*</span>标题字体颜色</label>
				<div class="col-md-4">
					<input type="text" name="titleFontColor" value="${detail.titleFontColor}" class="form-control jscolor" validate="required: true">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-4 control-label"><span class="required">*</span>标题字体大小</label>
				<div class="col-md-4">
					<select name="titleFontSize" class="form-control">
						<option value="3" <c:if test="${'3' eq detail.titleFontSize}">selected</c:if>>3</option>
						<option value="2" <c:if test="${'2' eq detail.titleFontSize}">selected</c:if>>2</option>
				     	<option value="4" <c:if test="${'4' eq detail.titleFontSize}">selected</c:if>>4</option>					     	
				    </select>				
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-4 control-label"><span class="required">*</span>标题背景颜色</label>
				<div class="col-md-4">
					<input type="text" name="titleFontBgColor" value="${detail.titleFontBgColor}" class="form-control jscolor" validate="required: true">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-md-4 control-label">显示记录数</label>
				<div class="col-md-4">
					<input type="text" name="infoRecNum" value="${detail.infoRecNum}" class="form-control" validate="required: false"
					onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" />
				</div>
			</div>
		</div>
		<div class="form-group row">
			<label class="col-md-4 control-label"><span class="required">*</span>链接到</label>
			<div class="col-md-4">
			    <qc:codeList var="links" codeGroup="UR0000" />
				<select name="urlType" class="form-control">
					<c:forEach var="link" items="${links}">
						<option value="${link.codeId}" <c:if test="${link.codeId eq detail.urlType}">selected</c:if>>${link.codeName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="form-group row url-link">
			<label class="col-md-4 control-label">链接内容</label>
			<div class="col-md-4">
				<input type="text" name="url" value="${detail.url}" class="form-control" validate="required: false">
			</div>
		</div>
		<div class="form-group row product-group">
			<label class="col-md-4 control-label">链接内容</label>
			<div class="col-md-4">
				<select name="productGroup" class="form-control">
					<option value="">==请选择商品组==</option>
					<c:if test="${not empty productGroupList }">
					<c:forEach var="group" items="${productGroupList}">
						<option value="${group.seqNo }" <c:if test="${detail.url eq group.seqNo }">selected</c:if>>${group.c1}</option>
					</c:forEach>
					</c:if>
				</select>
			</div>
		</div>
		<!-- div class="form-group row">
			<label class="col-md-4 control-label"><span class="required">*</span>电脑端-本列宽度(%)</label>
			<div class="col-md-4">
				<input type="text" name="widthPc" value="${detail.widthPc}" class="form-control" validate="required: true"
				onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" />				
			</div>			
		</div>
		<div class="form-group row">
			<label class="col-md-4 control-label"><span class="required">*</span>手机端-本列宽度(%)</label>
			<div class="col-md-4">
				<input type="text" name="widthMob" value="${detail.widthMob}" class="form-control" validate="required: true"
				onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" />
			</div>
		</div-->
		<div class="form-group row">
			<label class="col-md-4 control-label"><span class="required">*</span>边框颜色</label>
			<div class="col-md-4">
				<input type="text" name="bdColor" value="${detail.bdColor}" class="form-control jscolor" validate="required: true">
			</div>
		</div>
		<div class="form-group row">
			<div class="col-md-offset-4 col-md-4">
				<input type="checkbox" name="mobMark" value="Y" <c:if test="${'Y' eq detail.mobMark}">checked</c:if> />手机页面自适应				
			</div>
		</div>
		<div class="form-group row">
			<div class="col-md-offset-4 col-md-4">
				<input type="submit" class="btn btn-primary" value="<qc:message code="system.common.btn.save" />">
				<a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageMain") %>" class="btn btn-default pull-right">
					<qc:message code="system.common.btn.back" />
				</a>
			</div>			
		</div>
		<input type="hidden" name="detailId" value="${detail.detailId}" />
		<input type="hidden" name="userId" value="${detail.userId}"/>
		<input type="hidden" name="rowNum" value="${detail.rowNum}"/>
		<input type="hidden" name="colNum" value="${detail.colNum}"/>
		<input type="hidden" name="cellNum" value="${detail.cellNum}"/>
		<input type="hidden" name="detailImgPath" value="${detail.detailImgPath}" />
		
	</form>
	<div class="modal fade" id="productModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="false" data-backdrop="">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-times"></i></button>
					<h4 class="modal-title">请选择商品</h4>
				</div>
				<div class="modal-body">
					<form name="userItemFrm" class="form-horizontal" id="userItemFrm" method="post">
						<div class="row">
							<div class="text-left col-sm-6 col-xs-12">
								<div class="form-group">
									<c:if test="${not empty categoryList}">
										<div class="col-xs-6">
											<select name="category" id="category" class="form-control col-xs-6">
												<option value="">===分类===</option>
												<c:forEach var="category" items="${categoryList}">
													<option value="${category}" <c:if test="${sc.category == category}">selected</c:if>>
														<c:if test='${category == "-1"}'>未分类</c:if>
														<c:if test='${category != "-1"}'>${category}</c:if>
													</option>
												</c:forEach>
											</select>
										</div>
									</c:if>
									<div class="col-xs-6">
										<qc:codeList var="states" codeGroup="ST0000" />
										<select id="state" name="state" class="form-control col-xs-6">
											<option value="" <c:if test="${sc.state == ''}">selected</c:if>>==状态==</option>
											<c:forEach var="state" items="${states}">
												<option value="${state.codeId}" <c:if test="${sc.state == state.codeId}">selected</c:if>>${state.codeName}</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="text-right col-sm-6 col-xs-6">
								<div class="form-group">
									<label for="chelp" class="col-xs-3 control-label text-right">关键字</label>
									<div class="col-sm-6 col-xs-5">
										<input type="text" class="form-control" name="chelp" id="chelp" value="${sc.chelp}"/>
									</div>
									<div class="col-sm-3 col-xs-4">
										<button type="button" id="search" class="btn btn-primary"><qc:message code="system.common.btn.search"/></button>
									</div>
								</div>
							</div>
						</div>
					</form>
					
					<div class="clear">
						<table id="product-grid"></table>
						<div id="product-gridpager"></div>
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" id="selectItem"><qc:message code="system.common.btn.select"/></button>
					<button type="button" class="btn default" data-dismiss="modal"><qc:message code="system.common.btn.close"/></button>
				</div>
			</div>
		</div>
	</div>
</div>
