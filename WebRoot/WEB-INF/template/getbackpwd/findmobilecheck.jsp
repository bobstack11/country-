<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.egouos.util.ApplicationListenerImpl"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <meta name="decorator" content="index_template" />
    <title>忘记密码_<%=ApplicationListenerImpl.sysConfigureJson.getSaitName()%></title>
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
	        <a href="findpassword.html" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>验证身份</h2>
        <div class="head-r">
	        
        </div>
    </header>

        <section>
	        <div class="registerCon">
    	        <ul>
        	        <li><input id="mobileCode" type="text" placeholder="请输入手机验证码" class="rText"><s class="rs2"></s></li>
                    <li><a id="btnSubmitVerify" href="javascript:;" class="nextBtn orgBtn"><h2>确认，下一步</h2></a></li>
                    <li style="font-size:12px;">如未收到验证短信，请在150秒后点击重新发送。</li>
                    <li><a id="retrySend" class="resendBtn grayBtn">重新发送</a></li>
                </ul>
                <input name="hidRegMobile" type="hidden" id="hidRegMobile" value="${mail}" />
		        <input name="hidRegSN" type="hidden" id="hidRegSN" value="${sn }" />
		        <input name="checkSN" type="hidden" id="checkSN" value="${key }" />
	        </div>
        </section>
 </div>

    <script language="javascript" type="text/javascript" src="/js/findmobilecheck.js"></script>
  </body>
</html>
