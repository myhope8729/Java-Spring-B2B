<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<%@ page import="com.kpc.eos.core.controller.BaseController"%>

<div class="admin_body">
	<h3 class="page_title">${info.info}</h3>
	<h6 class="text-center">${info.createDate}</h6>
	
	<div class="item_content">
		<c:forEach var="item" items="${infoList}">		
			<c:if test="${item.dtype == 'PI0002' }">
			<c:if test="${item.showmark eq 'Y' }">
				<div class="row">
					<div class="col-lg-12">
					<div class="item_widget">
						${item.pnote}
					</div>
					</div>
				</div>
			</c:if>
			</c:if>
			<c:if test="${item.dtype == 'PI0003' and item.totalCount > 0}">
				<div class="item_widget">
					<div class="table block-table">
						<div class="row">
							<c:forEach var="detail" items="${item.detailList}">						
								<c:if test="${detail.showYn eq 'Y' }">
									<div class="block-cell col-lg-${item.totalCount}">
										<img src='/uploaded/info_pic/${detail.url}' width="100%" />
									</div>
								</c:if>						
							</c:forEach>
						</div>
					</div>
				</div>
			</c:if>
		</c:forEach>
	</div>
</div>