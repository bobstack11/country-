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
    <link rel="stylesheet" type="text/css" href="/css/security.css" />
  </head>
  
<body>
<div class="h5-1yyg-v1">
	<!-- <header class="g-header">
		<div class="head-l">
			<a href="/user/index.html" class="z-HReturn"><s></s><b>返回</b></a>
		</div>
		<div class="recharge-nav">
	    	<a href="/user/UserRecharge.html" class="btn-left">网银充值</a>
	        <a href="/user/userCardRecharge.html" class="btn-right curr">充值卡充值</a>
	    </div>
	    <div class="head-r">
	    	<a href="/user/UserBalance.html" class="z-RCornerBtn"><i></i>帐户明细</a>
	    </div>
	</header> -->
	
	<div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>充值卡充值</h1>
	</div>
	<div class="m-detail-menu" style="z-index: 9999">
	    <a id="moreBtn" style="margin-top: 6px" href="javascript:void(0)"><i class="ico ico-more"></i><i class="ico ico-dot" style="display:none"></i></a>
	    <div class="m-detail-menu-wrap" style="display:none">
	        <i class="ico ico-arrow ico-arrow-transGray"></i>
	        <ul class="m-detail-menu-list">
	            <li><a class="item" href="/user/UserRecharge.html">网银充值</a></li>
	            <li class="last"><a class="item" href="/user/UserBalance.html">帐户明细</a></li>
	        </ul>
	    </div>
	</div>

<input type="hidden" value="${userId }" id="userId"/>
<section class="secrityBg">
         <div class="g-Total gray9">您的当前余额：<span class="orange arial">${user.balance }</span>元</div>
	<div class="securityCode recharge-con clearfix">
    	<ul>
        	<li><input id="txtCard" type="text" maxlength="14" placeholder="请输入12位纯数字卡号" class="rText"><em>充值卡号</em></li>	
            <!-- <li><input id="txtPwd" type="text" maxlength="8" placeholder="请输入8位充值卡密码" class="rText"><em>充值密码</em></li> -->
            <li id="li_info" class="gray3" ></li>
            <li><a id="btnSubmit" href="javascript:;" class="grayBtn">确认充值</a></li>
        </ul>
    </div>
</section>
<script language="javascript" type="text/javascript" src="/js/cardrecharge.js"></script>
</body>
</html>
