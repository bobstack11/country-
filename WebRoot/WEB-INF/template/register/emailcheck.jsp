<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>会员注册_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%> 一元<%=ApplicationListenerImpl.sysConfigureJson.getShortName() %> <%=ApplicationListenerImpl.sysConfigureJson.getShortName() %>网</title>
 	<meta content="app-id=518966501" name="apple-itunes-app" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0" />
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
		    <link href="/css/login.css" rel="stylesheet" type="text/css" />
  </head>
  
  <body>
    <div class="h5-1yyg-v1" id="content">
        
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->

    <%-- <header class="g-header">
        <div class="head-l">
	        <a href="/register/mobilecheck.html?phone=${phone }" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>设置密码</h2>
        <div class="head-r">
	        
        </div>
    </header> --%>
    
    <div class="m-simpleHeader" id="dvHeader" style="z-index: 9999">
	    <a href="javascript:;" onclick="history.go(-1)" data-pro="back" data-back="true" class="m-simpleHeader-back"><i class="ico-back"></i></a>
	    <h1>设置密码</h1>
	</div>

        <section>
	        <div class="registerCon">
    	        <ul>
        	        <li>
        	            <input id="txtPassword" placeholder="请设置长度为6-20位的登录密码" class="rText" type="password" />
        	            <input id="txtPasswordObj2" placeholder="请设置长度为6-20位的登录密码" class="rText" style="display:none;" type="text" />
        	            <s class="rs3"></s>
        	        </li>
        	        <li><span id="isCheck" class="noCheck"><em></em>显示密码</span></li>
                    <li>
                        <input name="hidCodeStr" id="hidCodeStr" value="${isVerify }" type="hidden" />
                        <a href="javascript:void(0);" id="btnPostReg" class="nextBtn  orgBtn">完成注册</a>
                    </li>
                    <li>密码由6~20位半角字符（数字、字母、符号）组成，区分大小写</li>
                </ul>
	        </div>
        </section>
    <script language="javascript" type="text/javascript" src="/js/mobilesave.js"></script>
  </body>
</html>
