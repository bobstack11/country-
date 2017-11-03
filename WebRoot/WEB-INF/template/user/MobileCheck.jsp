<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>手机号绑定_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <link rel="stylesheet" type="text/css" href="/css/comm.css" />
    <link rel="stylesheet" type="text/css" href="/css/security.css?v=130726" />
  </head>
  
<body>
<div class="h5-1yyg-v1" id="loadingPicBlock">
    <input name="loadDataType" type="hidden" id="loadDataType" value="0" />
    
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->

    <%-- <header class="g-header">
        <div class="head-l">
	        <a href="MemberModify.html" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>绑定手机</h2>
        <div class="head-r">
	        <a href="/user/index.html" class="z-Member"></a>
        </div>
    </header> --%>
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>绑定手机</h1>
	</div>

     <section class="secrityBg">
            <input name="hidMobile" type="hidden" id="hidMobile" value="${user.phone}" />
            <div class="authentication-con clearfix">
                <ul>
                    <li class="pd20 gray3">
                        验证手机号: <c:if test="${user.phone!=null}">
				${fn:substring(user.phone,0,3)}****${fn:substring(user.phone,7,11)}
				</c:if></li>
                    <li class="enter-word">
                        <input id="txtCode" maxlength="6" type="text" placeholder="请输入6位验证码" class="rText"><a id="btnSend" href="javascript:;" class="orgBtn">获取验证码</a></li>
                    <li><a id="btnSubmit" href="javascript:;" class="grayBtn">确认</a></li>
                    <li class="gray9">换了手机号或遗失？请致电客服申诉解除绑定
                </ul>
            </div>
        </section>

    <script language="javascript" type="text/javascript" src="/js/mobilemodify.js?data=20131121"></script>
</body>
</html>
