<%@page import="com.kpc.eos.model.common.SysMsg" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/tagLib.jspf"%>
<div class="footer_wrapper">
	<div class="container">
		<div class="container_footer footer-top">
			<div class="row">
				<div class="col-md-3">
					<div class="section_title">关于我们</div>
					<div class="section_content">
						<p>服务条款</p>
						<p>免责申明</p>
						<p>官方博客</p>
						<p></p>
						<div>
							<label>分享到：</label>
							<div class="bdsharebuttonbox">
								<a href="#" class="bds_more" data-cmd="more"></a>
								<a href="#" class="bds_qzone" data-cmd="qzone"></a>
								<a href="#" class="bds_tsina" data-cmd="tsina"></a>
								<a href="#" class="bds_tqq" data-cmd="tqq"></a>
								<a href="#" class="bds_renren" data-cmd="renren"></a>
								<a href="#" class="bds_weixin" data-cmd="weixin"></a>
							</div>
							<script>
								window._bd_share_config={"common":{"bdSnsKey":{},"bdText":"","bdMini":"2","bdPic":"","bdStyle":"0","bdSize":"16"},"share":{},"image":{"viewList":["qzone","tsina","tqq","renren","weixin"],"viewText":"롸權돕：","viewSize":"16"},"selectShare":{"bdContainerClass":null,"bdSelectMiniList":["qzone","tsina","tqq","renren","weixin"]}};with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='+~(-new Date()/36e5)];
							</script>
						</div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="section_title">商务合作</div>
					<div class="section_content">
						<p>友情链接</p>
						<p>合作伙伴</p>
					</div>
				</div>
				<div class="col-md-4">
					<div class="section_title">联系我们</div>
					<div class="section_content">
						<p><label>客户服务:</label>工作日09:00-18:00</p>
						<p><label>客户电话:</label>0755-26656046 13510090168</p>
						<p><label>客户 QQ:</label>516697074</p>
						<p><label>意见建议:</label>Email：service@sz-whale.com</p>
						<p><label>公司地址:</label>深圳市南山科技园科技南一路W1-A栋605A</p>
					</div>
				</div>
				<div class="col-md-2">
					<div class="section_title">手机访问</div>
					<div class="section_content"></div>
				</div>
			</div>
		</div>
		<div class="container_footer footer-bottom">
			<p>Copyright &copyright; 2013-2016 shenzhen whale information technology Co.,Ltd.</p>
			<p>深圳市唯尔信息技术有限责任公司 版权所有</p>
			<p>
				<a class="orangeLink" target="_blank" style="text-decoration: none; " href="http://www.miitbeian.gov.cn/">粤ICP备14007086号-1</a>
				<span id="cnzz_stat_icon_1254788180">
					<a target="_blank" title="站长统计" href="http://www.cnzz.com/stat/website.php?web_id=1254788180"><img border="0" hspace="0" vspace="0" src="http://icon.cnzz.com/img/pic.gif" style="border: 0px;"></a>
				</span>
		</div>
		
		
	</div>
</div>
<div id="alert_message" class="hidden"><%= SysMsg.flashMsg(request) %></div>