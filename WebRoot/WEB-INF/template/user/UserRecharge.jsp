<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>帐户充值_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <link rel="stylesheet" type="text/css" href="/css/comm.css" />
    <link rel="stylesheet" type="text/css" href="/css/member.css" />
  </head>
  
<body>
<div class="h5-1yyg-v1" id="loadingPicBlock">
        <!-- <header class="g-header">
            <div class="head-l">
                <a href="/user/index.html" class="z-HReturn"><s></s><b>返回</b></a>
            </div>
            <div class="recharge-nav">
                <a href="/user/UserRecharge.html" class="btn-left curr">网银充值</a>
                <a href="/user/userCardRecharge.html" class="btn-right">充值卡充值</a>
            </div>
            <div class="head-r">
                <a href="/user/UserBalance.html" class="z-RCornerBtn"><i></i>帐户明细</a>
            </div>
        </header> -->
        
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>网银充值</h1>
	</div>
	<!-- <div class="m-detail-menu" style="z-index: 9999">
	    <a id="moreBtn" style="margin-top: 6px" href="javascript:void(0)">
	    <i class="ico ico-more"></i>
	    <i class="ico ico-dot" style="display:none"></i></a>
	    <div class="m-detail-menu-wrap" style="display:none">
	        <i class="ico ico-arrow ico-arrow-transGray"></i>
	        <ul class="m-detail-menu-list">
	            <li><a class="item" href="/user/userCardRecharge.html">充值卡充值</a></li>
	            <li class="last"><a class="item" href="/user/UserBalance.html">帐户明细</a></li>
	        </ul>
	    </div>
	</div> -->
        
        <div class="g-Total gray9">您的当前余额：<span class="orange arial">${user.balance }</span>元</div>
        <section class="clearfix g-member">
            <div class="g-Recharge">
                <ul id="ulOption">
                    <li money="10"><a href="javascript:;" class="z-sel">10元<s></s></a></li>
                    <li money="50"><a href="javascript:;">50元<s></s></a></li>
                    <li money="100"><a href="javascript:;">100元<s></s></a></li>
                    <li money="500"><a href="javascript:;">500元<s></s></a></li>
                    <li money="1000"><a href="javascript:;">1000元<s></s></a></li>
                    <li><b>
                        <input type="text" class="z-init" placeholder="输入金额" maxlength="8" /><s></s></b></li>
                </ul>
            </div>
            <article class="clearfix mt10 m-round g-pay-ment g-bank-ct">
                <ul id="ulBankList">
                    <li class="gray6">选择网银充值<em class="orange">10.00</em>元</li>
                <%
					if(ApplicationListenerImpl.sysConfigureJson.getWeixinStatus()==0){
				%>
				    <li class="gray9" payName="Swift" payText="微信支付"><i class="z-bank-Round"><s></s></i>微信支付</li>
				<%
					}
				%>
				<%
					if(ApplicationListenerImpl.sysConfigureJson.getAlipayStatus()==0){
				%>
					<li class="gray9" payName="Alipay" payText="支付宝"><i class="z-bank-Round"><s></s></i>支付宝</li>
				<%
					}
				%>
				<%
					if(ApplicationListenerImpl.sysConfigureJson.getYunPayStatus()==0){
				%>
	                <li class="gray9" payName="YunPay" payText="云支付"><i class="z-bank-Round"><s></s></i>云支付</li>
	            <%
					}
				%>
	            <%
					if(ApplicationListenerImpl.sysConfigureJson.getIapppayStatus()==0){
				%>
	                <li class="gray9" payName="IAppPay" payText="爱贝支付"><i class="z-bank-Round"><s></s></i>爱贝支付</li>
	            <%
					}
				%>
	            <%
					if(ApplicationListenerImpl.sysConfigureJson.getJubaoPayStatus()==0){
				%>
	                <li class="gray9" payName="JubaoPay" payText="聚宝云计费"><i class="z-bank-Round"><s></s></i>聚宝云计费</li>
	            <%
					}
				%>
                    <%-- <li class="gray9"><i class="z-bank-Roundsel"><s></s></i>京东支付</li>
                    <li class="gray9"><i class="z-bank-Round"><s></s></i>招商银行</li>
                    <li class="gray9"><i class="z-bank-Round"><s></s></i>银联在线</li> --%>
                </ul>
            </article>
            <div class="mt10 f-Recharge-btn">
                <a id="btnSubmit" href="javascript:;" class="orgBtn">确认充值</a>
            </div>
        </section>
    <link type="text/css" rel="stylesheet" href="/css/pagedialog.css?data=20131121" />
    <script language="javascript" type="text/javascript" src="/js/pagedialog.js?data=20131121"></script>
    <script language="javascript" type="text/javascript" src="/js/recharge.js?data=201510291"></script>
    <div class="clear_process"></div>
    <div class="pageDialogBG" id="pageDialogBG"></div>
	<div class="pageDialogBorder" id="pageDialogBorder"></div>
	<div class="pageDialog" id="pageDialog">
	<div class="pageDialogClose" id="pageDialogClose" title="关闭"></div>
	<div class="pageDialogMain" id="pageDialogMain">&nbsp;</div>
	</div>
</body>
</html>
