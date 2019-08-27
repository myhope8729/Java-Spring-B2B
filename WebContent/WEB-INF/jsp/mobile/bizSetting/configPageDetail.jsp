<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<script type="text/javascript">
	var jsonObj = ${jsonObj};
	var messages = {
		itemAlreadyOnHome : "<qc:message code='useritem.item.isonhome' />"	
	};
</script>

<div data-role="header"  data-theme="b" >
    <h3 class="page_title">内容设置</h3>
    <a href="<%= BaseController.getCmdUrl("ConfigPage", "configPageMain") %>"  data-role="button" data-icon="back" data-iconpos="notext" data-theme="c" data-inline="true" class="ui-btn-right">Back</a>
</div>
	
<div role="main" class="ui-content mgt10">
	
	<form id="detailFrm" name="detailFrm" class="admin-form" action="<%= BaseController.getCmdUrl("ConfigPage", "savePageDetail") %>" method="post" enctype="multipart/form-data">
		<div class="form-group col-sm-6">
			<label class="control-label"><span class="required">*</span>类别</label>
		    <qc:codeList var="dtypes" codeGroup="PI0000" />
			<select name="detailType">
				<c:forEach var="dtype" items="${dtypes}">
					<option value="${dtype.codeId}" <c:if test="${dtype.codeId eq detail.detailType}">selected</c:if>>${dtype.codeName}</option>
				</c:forEach>
			</select>
		</div>
		<div id="product-detail" class="d_type PI0004">
			<div class="form-group col-sm-12">
				<label class="control-label">商品</label>
				<div class="input-group">
					<span class="input-group-btn">
						<button type="button" data-role="none" class="btn btn-primary" data-toggle="modal" data-target="#productModal"><i class="fa fa-search"></i> 选择商品</button>
					</span>
					<input type="hidden" name="productId" value="<c:if test="${detail.detailType eq 'PI0004' and not empty detail.userItem}">${detail.userItem.itemId}</c:if>"/>
					<input type="text" data-role="none" name="product-name" class="form-control" readonly value="<c:if test="${detail.detailType eq 'PI0004' }">${detail.userItem.itemName }(编码：${detail.userItem.itemNo })</c:if>"/>
				</div>
			</div>
		</div>
		<div id="pic-detail" class="d_type PI0003">
			<div class="form-group col-sm-12">
				<label class="control-label">图片</label>
				<div class="fileinput 
						<c:if test="${not empty detail.detailImgPath}">fileinput-exists</c:if>
						<c:if test="${empty detail.detailImgPath}">fileinput-new</c:if>" data-provides="fileinput">
					<div class="fileinput-preview thumbnail" data-trigger="fileinput" style="width: 100%; height: 150px;min-width:200px;max-width:300px">						
						<img style="width:100%;height:140px;" src="<c:url value="/uploaded/pagedetail/${detail.detailImgPath}"/>" onerror="javascript:this.src='/images/sc.png'">						
					</div>
					<div>
						<span class="default btn-file pull-left">
					    	<span class="fileinput-new btn btn-primary">选择图片</span>
					    	<span class="fileinput-exists btn btn-info">变更</span>
					    	<input type="file" name="detailImgFile">
					    </span>
					    <a href="#" class="btn btn-danger red fileinput-exists pull-right" data-dismiss="fileinput">删除</a>
					</div>
				</div>
			</div>
			<div class="form-group col-sm-6">
				<label class="control-label">图片说明</label>
				<input type="text" name="picNote" value="${detail.picNote}" class="form-control" validate="required: false">
			</div>
			<div class="form-group col-sm-6">
				<label class="control-label"><span class="required">*</span>图片比例(%)</label>
				<input type="text" name="picSize" value="${detail.picSize}" class="form-control" validate="required: true"
				onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" />
			</div>
			<div class="form-group col-sm-12">
				<label class="control-label"><span class="required">*</span>图片边框颜色</label>
				<input type="text" name="picBdColor" value="${detail.picBdColor}" class="form-control jscolor" validate="required: true">
			</div>
		</div>
		<div id="font-detail" class="d_type PI0002">
			<div class="form-group col-sm-12">
				<label class="control-label"><span class="required">*</span>文字内容</label>
				<input type="text" name="fontNote" value="${detail.fontNote}" class="form-control" validate="required: true">
			</div>
			<div class="form-group col-sm-12">
				<label control-label"><span class="required">*</span>字体大小</label>
				<select name="fontSize">
					<option value="3" <c:if test="${'3' eq detail.fontSize}">selected</c:if>>3</option>
					<option value="2" <c:if test="${'2' eq detail.fontSize}">selected</c:if>>2</option>
			     	<option value="4" <c:if test="${'4' eq detail.fontSize}">selected</c:if>>4</option>					     	
			    </select>				
			</div>
			<div class="form-group col-sm-6">
				<label class="control-label"><span class="required">*</span>字体颜色</label>
				<input type="text" name="fontColor" value="${detail.fontColor}" class="form-control jscolor" validate="required: true">
			</div>
			<div class="form-group col-sm-6">
				<label class="control-label"><span class="required">*</span>背景颜色</label>
				<input type="text" name="bgColor" value="${detail.bgColor}" class="form-control jscolor" validate="required: true">
			</div>
			<div class="form-group col-sm-6">
				<label class="control-label"><span class="required">*</span>显示小点
					<input type="checkbox" name="liMark" value="Y" <c:if test="${'Y' eq detail.liMark}">checked</c:if> />	
				</label>			
			</div>
			<div class="form-group col-sm-6">
				<label class="control-label"><span class="required">*</span>电脑端不显示
					<input type="checkbox" name="mobOnly" value="Y" <c:if test="${'Y' eq detail.mobOnly}">checked</c:if> />	
				</label>			
			</div>
		</div>
		<div id="info-detail" class="d_type PI0005">
			<div class="form-group col-sm-12">
				<label class="control-label"><span class="required">*</span>栏目类别</label>
				<select name="infoType">
					<option value="" selected>==请选择==</option>
					<c:forEach var="docType" items="${docTypeList}">
						<option value="${docType.c1}" <c:if test="${docType.c1 eq detail.infoType}">selected</c:if>>${docType.c1}</option>		
					</c:forEach>																	     	
			    </select>
			</div>
			<div class="form-group col-sm-12">
				<label class="control-label"><span class="required">*</span>标题字体颜色</label>
				<input type="text" name="titleFontColor" value="${detail.titleFontColor}" class="form-control jscolor" validate="required: true">
			</div>
			<div class="form-group col-sm-12">
				<label class="control-label"><span class="required">*</span>标题字体大小</label>
				<select name="titleFontSize">
					<option value="3" <c:if test="${'3' eq detail.titleFontSize}">selected</c:if>>3</option>
					<option value="2" <c:if test="${'2' eq detail.titleFontSize}">selected</c:if>>2</option>
			     	<option value="4" <c:if test="${'4' eq detail.titleFontSize}">selected</c:if>>4</option>					     	
			    </select>				
			</div>
			<div class="form-group col-sm-6">
				<label class="control-label"><span class="required">*</span>标题背景颜色</label>
				<input type="text" name="titleFontBgColor" value="${detail.titleFontBgColor}" class="form-control jscolor" validate="required: true">
			</div>
			<div class="form-group col-sm-6">
				<label class="control-label">显示记录数</label>
					<input type="text" name="infoRecNum" value="${detail.infoRecNum}" class="form-control" validate="required: false"
					onkeypress="if ( isNaN( String.fromCharCode(event.keyCode) )) return false;" />
			</div>
		</div>
		<div class="form-group col-sm-6">
			<label class="control-label"><span class="required">*</span>链接到</label>
		    <qc:codeList var="links" codeGroup="UR0000" />
			<select name="urlType">
				<c:forEach var="link" items="${links}">
					<option value="${link.codeId}" <c:if test="${link.codeId eq detail.urlType}">selected</c:if>>${link.codeName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group col-sm-6 url-link">
			<label class="control-label">链接内容</label>
			<input type="text" name="url" value="${detail.url}" class="form-control" validate="required: false">
		</div>
		<div class="form-group col-sm-6 product-group">
			<label class="control-label">链接内容</label>
			<select name="productGroup" class="">
				<option value="">==请选择商品组==</option>
				<c:if test="${not empty productGroupList }">
				<c:forEach var="group" items="${productGroupList}">
					<option value="${group.seqNo }" <c:if test="${detail.url eq group.seqNo }">selected</c:if>>${group.c1}</option>
				</c:forEach>
				</c:if>
			</select>
		</div>
		<div class="form-group col-sm-6">
			<label class="control-label"><span class="required">*</span>边框颜色</label>
			<input type="text" name="bdColor" value="${detail.bdColor}" class="form-control jscolor" validate="required: true">
		</div>
		<div class="form-group col-sm-12">
			<label class="control-label">
				<input type="checkbox" name="mobMark" value="Y" <c:if test="${'Y' eq detail.mobMark}">checked</c:if> />手机页面自适应
			</label>				
		</div>
		<div class="form-group col-md-12">
			<div class="custom_block_73">
				<div class="ui-block-a">
					<button type="submit"  data-mini="true"  data-theme="b" data-icon="check" ><qc:message code="system.common.btn.save"/></button>
				</div>		
				<div class="ui-block-b">
					<a class="mgr-no" href="<%= BaseController.getCmdUrl("ConfigPage", "configPageMain") %>" data-mini="true" data-role="button" data-icon="back" id="btnBack"><qc:message code="system.common.btn.back" /></a>
				</div>
			</div>
		</div>			
		
		<input type="hidden" name="detailId" value="${detail.detailId}" />
		<input type="hidden" name="userId" value="${detail.userId}"/>
		<input type="hidden" name="rowNum" value="${detail.rowNum}"/>
		<input type="hidden" name="colNum" value="${detail.colNum}"/>
		<input type="hidden" name="cellNum" value="${detail.cellNum}"/>
		<input type="hidden" name="detailImgPath" value="${detail.detailImgPath}" /> 
	</form>
	<div class="modal fade" id="productModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" data-role="none" class="close" data-dismiss="modal" aria-hidden="true"><i class="fa fa-times"></i></button>
					<h4 class="modal-title">请选择商品</h4>
				</div>
				<div class="modal-body">
					<form name="userItemFrm" class="form-horizontal" id="userItemFrm" method="post">
						<div class="search_collapse" data-mini="true" data-role="collapsible" data-theme="b" data-content-theme="a" data-collapsed="true" data-collapsed-icon="carat-d" data-expanded-icon="carat-u">
							<h3><qc:message code="system.common.btn.search" /></h3>
							<div class="action-bar row">
								<c:if test="${not empty categoryList}">
								<div class="text-left col-xs-12">	
									<select name="category" id="category">
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
								<div class="text-left col-xs-12">	
									<qc:codeList var="states" codeGroup="ST0000" />
									<select id="state" name="state" class="">
										<option value="" <c:if test="${sc.state == ''}">selected</c:if>>==状态==</option>
										<c:forEach var="state" items="${states}">
											<option value="${state.codeId}" <c:if test="${sc.state == state.codeId}">selected</c:if>>${state.codeName}</option>
										</c:forEach>
									</select>
								</div>
								<div class="text-left col-xs-12">
									<label for="state">关键字</label>
						 			<input type="text" class="form-control" name="chelp" id="chelp" value="${sc.chelp}"/>
								</div>				
							</div>	
							
							<div class="text-left custom_block_73">
								<div class="ui-grid-a">
									<div class="ui-block-a">
										<button type="button" data-mini="true"  data-theme="b" data-icon="search" id="search"><qc:message code="system.common.btn.search" /></button>
									</div>
									<div class="ui-block-b">
										<button type="button" data-mini="true"  class="btnReset" id="reset"><qc:message code="system.common.btn.reset" /></button>
									</div>
								</div>
							</div>
						</div>
					</form>
					
					<div class="table-responsive">
						<table id="product-grid"></table>
						<div id="product-gridpager"></div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="text-left custom_block_73">
						<div class="ui-grid-a">
							<div class="ui-block-a">
								<button data-mini="true"  data-theme="b"  id="selectItem"><qc:message code="system.common.btn.select"/></button>
							</div>
							<div class="ui-block-b">
								<button type="button" data-mini="true"  class="btnReset" data-dismiss="modal"><qc:message code="system.common.btn.close"/></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
