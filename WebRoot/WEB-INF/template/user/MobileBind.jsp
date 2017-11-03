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

        <input name="hidAuth" type="hidden" id="hidAuth" <c:if test="${sn!=null}">value="${sn}" </c:if>/>
         <input name="hidForward" type="hidden" id="hidForward" />
        <section class="secrityBg">
            <div id="div_registercon" class="registerCon clearfix">
                <ul>
                    <li>
                        <input id="txtMobile" type="text" placeholder="请输入手机号" class="rText"></li>
                    <li><a href="javascript:;" id="btnSend" class="orgBtn">下一步</a></li>
                </ul>
            </div>
            <div id="div_securitycode" class="securityCode clearfix" style="display: none;">
                <ul>
                    <li class="gray3">验证码已发送到您的手机 <span id="span_mobile"></span></li>
                    <li>
                        <input id="txtCode" type="text" maxlength="6" placeholder="请输入6位验证码" class="rText"></li>
                    <li><a href="javascript:;" id="btnSubmit" class="orgBtn">确认</a></li>
                    <li><a href="javascript:;" id="btnAgain" class="againBtn">重新发送</a></li>
                </ul>
            </div>
        </section>


    <script language="javascript" type="text/javascript" src="/js/mobilebind.js?data=20131121"></script>
</body>
</html>
