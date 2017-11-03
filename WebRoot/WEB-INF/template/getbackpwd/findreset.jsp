<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>设置新密码_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></title>
	<link rel="stylesheet" type="text/css" href="/css/login.css" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0" />
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
  </head>
  
  <body>
  	<div class="h5-1yyg-v1" id="content">
        
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->

    <header class="g-header">
        <div class="head-l">
	        <a href="javascript:;" onclick="history.go(-1)" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>设置新密码</h2>
        <div class="head-r">
	        
        </div>
    </header>

        <section>
	        <div class="registerCon">
    	        <ul>
        	        <li>
        	            <input type="password" id="txtPasswordObj" placeholder="请设置新密码">
        	            <input type="text" id="txtPasswordObj2" placeholder="请设置新密码" style="display:none">
        	        </li>
        	        <li><span id="isCheck" class="noCheck"><em></em>显示密码</span></li>
                    <li><a id="btnPostSet" href="javascript:;" class="nextBtn  orgBtn">下一步</a></li>
                    <li>密码由6~20位半角字符（数字、字母、符号）组成，区分大小写</li>
                </ul>
                <input name="hidUserName" type="hidden" id="hidUserName" value="${mail}" />
                <input name="hidKey" type="hidden" id="hidKey" value="${key}" />
	        </div>
        </section>
	</div>
    <script language="javascript" type="text/javascript" src="/js/findreset.js"></script>
  </body>
</html>
