<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>编辑个人资料_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <link href="/css/layoutcommodity.css?data=150930" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="/css/layoutwap.css?data=150930" />
    <link rel="stylesheet" type="text/css" href="/css/layoutsetup.css?data=150930" />
    <link rel="stylesheet" type="text/css" href="/css/header.css?data=150930" />
    <link rel="stylesheet" type="text/css" href="/css/comm.css" />
    <link rel="stylesheet" type="text/css" href="/css/security.css" />
<style type="text/css">
body {
	min-width: 100%;
}
.info {
	clear: both;
	margin: 30px 0 0 15px;
}
textarea.info_txtarea2 {
	border: #ccc 1px solid;
	width: 200px;
	height: 51px;
	overflow: auto;
	padding: 3px;
	font-size: 12px;
	line-height: 20px;
	font-family: Arial, Helvetica, sans-serif, "宋体"
}
.order_shipAddr h5 {
	width:100%;
	padding:0px;
}
</style>
</head>
  
<body>
<div class="h5-1yyg-v1">
    
<!-- 栏目页面顶部 -->

	<%-- <header class="g-header">
        <div class="head-l">
	        <a href="/user/index.html"  class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>个人设置</h2>
        <div class="head-r">
	        <a href="/user/index.html" class="z-Member"></a>
        </div>
    </header> --%>
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>个人设置</h1>
	</div>
	
	<section>
	<input type="hidden" value="${userId }" id="userId"/>
	<div class="secrityBg update-pwd clearfix">
    	<dl>
        	<dt class="gray3">手机： <c:choose>  
								<c:when test="${user.phone!=null}">
									<b>${user.phone }</b>
									<c:choose>
										<c:when test="${user.mobileCheck==0}">(已验证)</c:when>
										<c:otherwise>(未验证)</c:otherwise>
									</c:choose>
									<a title="修改" href="/user/MobileCheck.html">修改</a>
								</c:when>
								<c:otherwise>
									未绑定 <a title="绑定" href="/user/MobileBind.html">马上绑定</a>
								</c:otherwise>
							</c:choose></dt>
            <dd><input id="txtName" name="txtName" type="text" value="${user.userName }" maxlength="20" placeholder="请输入昵称"><em>昵称</em>
            	<input type="hidden" value="${user.userName }" id="hidOldName" name="hidOldName" />
            </dd>
            <dd><input id="txtPhone" name="txtPhone" type="text" value="${user.mobilePhone }" maxlength="20" placeholder="请输入备用电话"><em>备用电话</em></dd>
            <dd><input id="txtQQ" name="txtQQ" type="text" value="${user.qq }" placeholder="请输入QQ号"><em>QQ</em></dd>
            <dd><textarea onpropertychange="if(value.length&gt;100) value=value.substr(0,100)" id="txtSignature" name="txtSignature" placeholder="你的个性签名">${user.signature }</textarea><em>签名</em></dd>
        </dl>
        <p><a id="butSaveSubmit" href="javascript:;" class="orgBtn">保 存</a></p>
    </div>
</section>
	
</div>
    <script language="javascript" type="text/javascript" src="/js/modify.js?data=20131121"></script>
    <script language="javascript" type="text/javascript" src="/js/pagedialog.js?data=20131121"></script>
</body>
</html>
